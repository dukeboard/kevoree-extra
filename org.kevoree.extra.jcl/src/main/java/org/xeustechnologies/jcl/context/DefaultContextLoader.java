/**
 *  JCL (Jar Class Loader)
 *
 *  Copyright (C) 2011  Kamran Zafar
 *
 *  This file is part of Jar Class Loader (JCL).
 *  Jar Class Loader (JCL) is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  JarClassLoader is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with JCL.  If not, see <http://www.gnu.org/licenses/>.
 *
 *  @author Kamran Zafar
 *
 *  Contact Info:
 *  Email:  xeus.man@gmail.com
 *  Web:    http://xeustech.blogspot.com
 */

package org.xeustechnologies.jcl.context;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.xeustechnologies.jcl.JarClassLoader;

/**
 * This class is builds the context from a single JCL instance. This should be
 * used if a single JarClassLoader is instantiated programmatically.
 * 
 * @author Kamran
 * 
 */
public class DefaultContextLoader implements JclContextLoader {
    private final JclContext jclContext;
    private final JarClassLoader jcl;

    private static Logger logger = Logger.getLogger( DefaultContextLoader.class.getName() );

    public DefaultContextLoader(JarClassLoader jcl) {
        jclContext = new JclContext();
        this.jcl = jcl;
    }

    /**
     * Loads a single JCL instance in context
     * 
     * @see org.xeustechnologies.jcl.context.JclContextLoader#loadContext()
     */
    @Override
    public void loadContext() {
        jclContext.addJcl( JclContext.DEFAULT_NAME, jcl );

        if (logger.isLoggable( Level.FINER ))
            logger.finer( "Default JarClassLoader loaded into context." );
    }

    @Override
    public void unloadContext() {
        JclContext.destroy();
    }
}
