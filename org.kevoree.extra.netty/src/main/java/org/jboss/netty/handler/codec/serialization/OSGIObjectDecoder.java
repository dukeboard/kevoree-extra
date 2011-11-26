package org.jboss.netty.handler.codec.serialization;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBufferInputStream;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.LengthFieldBasedFrameDecoder;
import org.osgi.framework.Bundle;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * User: duke
 * Date: 16/11/11
 * Time: 19:51
 * To change this template use File | Settings | File Templates.
 */
public class OSGIObjectDecoder extends LengthFieldBasedFrameDecoder {

    private Bundle bundle;

    public OSGIObjectDecoder(Bundle _bundle,int maxObjectSize) {
        this(maxObjectSize,null);
        bundle = _bundle;

    }

    public OSGIObjectDecoder(Bundle _bundle) {
        this(1048576,null);
        bundle = _bundle;

    }

    public OSGIObjectDecoder(int maxObjectSize, ClassLoader classLoader) {
        super(maxObjectSize, 0, 4, 0, 4);
      //  this.classLoader = classLoader;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
        ChannelBuffer frame = (ChannelBuffer) super.decode(ctx, channel, buffer);
        if (frame == null) {
            return null;
        }
        return new OSGICompactObjectInputStream(new ChannelBufferInputStream(frame)).readObject();

    }

    class OSGICompactObjectInputStream extends CompactObjectInputStream {

        OSGICompactObjectInputStream(InputStream in) throws IOException {
            super(in);
        }

        OSGICompactObjectInputStream(InputStream in, ClassLoader classLoader) throws IOException {
            super(in, classLoader);
        }

        @Override
        protected Class<?> loadClass(String className) throws ClassNotFoundException {
            return bundle.loadClass(className);
            //return super.loadClass(className);
        }
    }

    @Override
    protected ChannelBuffer extractFrame(ChannelBuffer buffer, int index, int length) {
        return buffer.slice(index, length);
    }

}