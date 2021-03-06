/*******************************************************************************

"FreePastry" Peer-to-Peer Application Development Substrate

Copyright 2002-2007, Rice University. Copyright 2006-2007, Max Planck Institute 
for Software Systems.  All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are
met:

- Redistributions of source code must retain the above copyright
notice, this list of conditions and the following disclaimer.

- Redistributions in binary form must reproduce the above copyright
notice, this list of conditions and the following disclaimer in the
documentation and/or other materials provided with the distribution.

- Neither the name of Rice  University (RICE), Max Planck Institute for Software 
Systems (MPI-SWS) nor the names of its contributors may be used to endorse or 
promote products derived from this software without specific prior written 
permission.

This software is provided by RICE, MPI-SWS and the contributors on an "as is" 
basis, without any representations or warranties of any kind, express or implied 
including, but not limited to, representations or warranties of 
non-infringement, merchantability or fitness for a particular purpose. In no 
event shall RICE, MPI-SWS or contributors be liable for any direct, indirect, 
incidental, special, exemplary, or consequential damages (including, but not 
limited to, procurement of substitute goods or services; loss of use, data, or 
profits; or business interruption) however caused and on any theory of 
liability, whether in contract, strict liability, or tort (including negligence
or otherwise) arising in any way out of the use of this software, even if 
advised of the possibility of such damage.

*******************************************************************************/ 
package rice.p2p.glacier.v2.messaging;

import java.io.IOException;

import rice.*;
import rice.p2p.commonapi.*;
import rice.p2p.commonapi.rawserialization.*;
import rice.p2p.glacier.VersionKey;

public class GlacierRefreshPatchMessage extends GlacierMessage {
  public static final short TYPE = 10;


  protected VersionKey[] keys;
  protected long[] lifetimes;
  protected byte[][] signatures;

  public GlacierRefreshPatchMessage(int uid, VersionKey[] keys, long[] lifetimes, byte[][] signatures, NodeHandle source, Id dest, char tag) {
    super(uid, source, dest, false, tag);

    this.keys = keys;
    this.lifetimes = lifetimes;
    this.signatures = signatures;
  }

  public int numKeys() {
    return keys.length;
  }

  public VersionKey getKey(int index) {
    return keys[index];
  }

  public VersionKey[] getAllKeys() {
    return keys;
  }

  public long getLifetime(int index) {
    return lifetimes[index];
  }

  public byte[] getSignature(int index) {
    return signatures[index];
  }

  public String toString() {
    return "[GlacierRefreshPatch for " + keys[0] + " ("+(numKeys()-1)+" more keys)]";
  }
  
  /***************** Raw Serialization ***************************************/
  public short getType() {
    return TYPE; 
  }
  
  public void serialize(OutputBuffer buf) throws IOException {
    buf.writeByte((byte)0); // version    
    super.serialize(buf); 
    
    buf.writeInt(lifetimes.length);
    for (int i = 0; i < lifetimes.length; i++) {
      buf.writeLong(lifetimes[i]); 
    }
    
    buf.writeInt(keys.length);
    for (int i = 0; i < keys.length; i++) {
      keys[i].serialize(buf); 
    }
    
    buf.writeInt(signatures.length);
    for (int i = 0; i < signatures.length; i++) {
      buf.writeInt(signatures[i].length);
      buf.write(signatures[i], 0, signatures[i].length);
    }    
  }

  public static GlacierRefreshPatchMessage build(InputBuffer buf, Endpoint endpoint) throws IOException {
    byte version = buf.readByte();
    switch(version) {
      case 0:
        return new GlacierRefreshPatchMessage(buf, endpoint);
      default:
        throw new IOException("Unknown Version: "+version);
    }
  }
    
  private GlacierRefreshPatchMessage(InputBuffer buf, Endpoint endpoint) throws IOException {
    super(buf, endpoint);
    lifetimes = new long[buf.readInt()];
    for (int i = 0; i < lifetimes.length; i++) {
      lifetimes[i] = buf.readLong(); 
    }
    keys = new VersionKey[buf.readInt()];
    for (int i = 0; i < keys.length; i++) {
      keys[i] = new VersionKey(buf, endpoint); 
    }    
    signatures = new byte[buf.readInt()][];
    for (int i = 0; i < signatures.length; i++) {
      signatures[i] = new byte[buf.readInt()];       
      buf.read(signatures[i]);
    }
  }
}

