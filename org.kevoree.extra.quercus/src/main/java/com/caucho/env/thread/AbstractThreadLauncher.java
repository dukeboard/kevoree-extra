/*
 * Copyright (c) 1998-2011 Caucho Technology -- all rights reserved
 *
 * This file is part of Resin(R) Open Source
 *
 * Each copy or derived work must preserve the copyright notice and this
 * notice unmodified.
 *
 * Resin Open Source is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * Resin Open Source is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE, or any warranty
 * of NON-INFRINGEMENT.  See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Resin Open Source; if not, write to the
 *
 *   Free Software Foundation, Inc.
 *   59 Temple Place, Suite 330
 *   Boston, MA 02111-1307  USA
 *
 * @author Scott Ferguson
 */

package com.caucho.env.thread;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import com.caucho.config.ConfigException;
import com.caucho.inject.Module;
import com.caucho.lifecycle.Lifecycle;
import com.caucho.util.L10N;

@Module
abstract public class AbstractThreadLauncher extends AbstractTaskWorker {
  private static final L10N L = new L10N(AbstractThreadLauncher.class);
  
  private static final long LAUNCHER_TIMEOUT = 60000L;

  private static final int DEFAULT_THREAD_MAX = 8192;
  private static final int DEFAULT_IDLE_MIN = 1;
  private static final int DEFAULT_IDLE_MAX = DEFAULT_THREAD_MAX;

  private static final long DEFAULT_IDLE_TIMEOUT = 120000L;
  
  // configuration items

  private int _threadMax = DEFAULT_THREAD_MAX;
  private int _idleMin = DEFAULT_IDLE_MIN;
  private int _idleMax = DEFAULT_IDLE_MAX;
  private long _idleTimeout = DEFAULT_IDLE_TIMEOUT;

  //
  // thread max and thread lifetime counts
  //

  private final AtomicInteger _threadCount = new AtomicInteger();
  private final AtomicInteger _idleCount = new AtomicInteger();
  
  // number of threads which are in the process of starting
  private final AtomicInteger _startingCount = new AtomicInteger();
  
  private final AtomicLong _createCountTotal = new AtomicLong();
  // private final AtomicLong _overflowCount = new AtomicLong();

  // next time when an idle thread can expire
  private final AtomicLong _threadIdleExpireTime = new AtomicLong();
  
  private final AtomicInteger _gId = new AtomicInteger();
  
  private final Lifecycle _lifecycle;

  protected AbstractThreadLauncher()
  {
    this(Thread.currentThread().getContextClassLoader());
  }

  protected AbstractThreadLauncher(ClassLoader loader)
  {
    super(loader);
    
    setWorkerIdleTimeout(LAUNCHER_TIMEOUT);
    
    _lifecycle = new Lifecycle();
  }
  
  //
  // abstract to be implemented by children
  //

  abstract protected void launchChildThread(int id);

  //
  // Configuration properties
  //

  /**
   * Sets the maximum number of threads.
   */
  public void setThreadMax(int max)
  {
    if (max < _idleMin)
      throw new ConfigException(L.l("IdleMin ({0}) must be less than ThreadMax ({1})", _idleMin, max));
    if (max < 1)
      throw new ConfigException(L.l("ThreadMax ({0}) must be greater than zero", 
                                    max));

    _threadMax = max;

    update();
  }

  /**
   * Gets the maximum number of threads.
   */
  public int getThreadMax()
  {
    return _threadMax;
  }

  /**
   * Sets the minimum number of idle threads.
   */
  public void setIdleMin(int min)
  {
    if (_threadMax < min)
      throw new ConfigException(L.l("IdleMin ({0}) must be less than ThreadMax ({1})", min, _threadMax));
    if (min <= 0)
      throw new ConfigException(L.l("IdleMin ({0}) must be greater than 0.", min));

    _idleMin = min;

    update();
  }

  /**
   * Gets the minimum number of idle threads.
   */
  public int getIdleMin()
  {
    return _idleMin;
  }

  /**
   * Sets the maximum number of idle threads.
   */
  public void setIdleMax(int max)
  {
    if (_threadMax < max)
      throw new ConfigException(L.l("IdleMax ({0}) must be less than ThreadMax ({1})", max, _threadMax));
    if (max <= 0)
      throw new ConfigException(L.l("IdleMax ({0}) must be greater than 0.", max));

    _idleMax = max;

    update();
  }

  /**
   * Gets the maximum number of idle threads.
   */
  public int getIdleMax()
  {
    return _idleMax;
  }
  
  /**
   * Sets the idle timeout
   */
  public void setIdleTimeout(long timeout)
  {
    _idleTimeout = timeout;
  }
  
  /**
   * Returns the idle timeout.
   */
  public long getIdleTimeout()
  {
    return _idleTimeout;
  }
  
  //
  // lifecycle method
  //
  
  public void start()
  {
    _lifecycle.toActive();
    
    wake();
  }
  
  @Override
  public void destroy()
  {
    super.destroy();
    
    _lifecycle.toDestroy();
  }
  
  //
  // child thread callbacks
  //

  public boolean isThreadMax()
  {
    return _threadMax <= _threadCount.get() && _startingCount.get() == 0;
  }
  
  /**
   * Thread activity management
   */
  public void onChildThreadBegin()
  {
    _threadCount.incrementAndGet();
    
    int startCount = _startingCount.decrementAndGet();

    if (startCount < 0) {
      _startingCount.set(0);
      new IllegalStateException().printStackTrace();
    }

    _createCountTotal.incrementAndGet();
  }
  
  /**
   * Resume a child, i.e. start an active thread from an external source.
   */
  public void onChildThreadResume()
  {
    _threadCount.incrementAndGet();
  }
  
  /**
   * Thread activity management
   */
  public void onChildThreadEnd()
  {
    if (_threadMax <= _threadCount.getAndDecrement()) {
      wake();
    }
  }
  
  //
  // idle management
  //
  
  /**
   * Returns true if the thread should expire instead of going to the idle state.
   */
  public boolean isIdleExpire()
  {
    if (! _lifecycle.isActive())
      return true;
    
    long now = getCurrentTimeActual();
    
    long idleExpire = _threadIdleExpireTime.get();

    // if idle queue is full and the expire is set, return and exit
    if (_idleMin < _idleCount.get()
        && (idleExpire < now || _idleMax < _idleCount.get())) {
      long nextIdleExpire = now + _idleTimeout;
      
      return _threadIdleExpireTime.compareAndSet(idleExpire, nextIdleExpire);
    }
    
    return false;
  }
  
  /**
   * Called by the thread before going into the idle state.
   */
  
  public void onChildIdleBegin()
  {
    _idleCount.incrementAndGet();
  }
  
  /**
   * Called by the thread after exiting the idle state.
   */
  public void onChildIdleEnd()
  {
    int idleCount = _idleCount.decrementAndGet();

    if (idleCount <= _idleMin) {
      updateIdleExpireTime(getCurrentTimeActual());

      wake();
    }
  }

  /**
   * updates the thread idle expire time.
   */
  protected void updateIdleExpireTime(long now)
  {
    _threadIdleExpireTime.set(now + _idleTimeout);
  }
   
  //
  // implementation methods
  //

  /**
   * Checks if the launcher should start another thread.
   */
  protected boolean doStart()
  {
    if (! _lifecycle.isActive())
      return false;
    
    int startingCount = _startingCount.getAndIncrement();

    int threadCount = _threadCount.get() + startingCount;

    if (_threadMax < threadCount) {
      _startingCount.decrementAndGet();
      
      return false;
    }
    else if (isIdleTooLow(startingCount)) {
      return true;
    }
    else {
      _startingCount.decrementAndGet();
      
      return false;
    }
  }
  
  protected boolean isIdleTooLow(int startingCount)
  {
    return (_idleCount.get() + startingCount < _idleMin);
  }
  
  @Override
  protected boolean isPermanent()
  {
    return true;
  }

  protected void update()
  {
    long now = getCurrentTimeActual();
    
    _threadIdleExpireTime.set(now + _idleTimeout);
    wake();
  }

  /**
   * Starts a new connection
   */
  private void startConnection()
  {
    while (doStart()) {
      long now = getCurrentTimeActual();
        
      updateIdleExpireTime(now);

      int id = _gId.incrementAndGet();
        
      launchChildThread(id);
    }
  }
  
  //
  // statistics
  //

  public int getThreadCount()
  {
    return _threadCount.get();
  }

  public int getIdleCount()
  {
    return _idleCount.get();
  }

  public int getStartingCount()
  {
    return _startingCount.get();
  }

  public long getCreateCountTotal()
  {
    return _createCountTotal.get();
  }
  
  @Override
  public long runTask()
  {
    startConnection();
    
    return -1;
  }
}
