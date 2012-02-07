package org.jboss.netty.handler.codec.serialization;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBufferInputStream;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.LengthFieldBasedFrameDecoder;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * User: duke
 * Date: 16/11/11
 * Time: 19:51
 * To change this template use File | Settings | File Templates.
 */
public class KevoreeObjectDecoder extends LengthFieldBasedFrameDecoder {

    private ClassLoader classLoader;

    public KevoreeObjectDecoder(ClassLoader classLoader,int maxObjectSize) {
		super(maxObjectSize, 0, 4, 0, 4);
        this.classLoader= classLoader;

    }

    public KevoreeObjectDecoder(ClassLoader classLoader) {
        this(classLoader, 1048576);

    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
        ChannelBuffer frame = (ChannelBuffer) super.decode(ctx, channel, buffer);
        if (frame == null) {
            return null;
        }
        return new KevoreeCompactObjectInputStream(new ChannelBufferInputStream(frame)).readObject();

    }

    class KevoreeCompactObjectInputStream extends CompactObjectInputStream {

		KevoreeCompactObjectInputStream(InputStream in) throws IOException {
            super(in);
        }

		KevoreeCompactObjectInputStream(InputStream in, ClassLoader classLoader) throws IOException {
            super(in, classLoader);
        }

        @Override
        protected Class<?> loadClass(String className) throws ClassNotFoundException {
            return classLoader.loadClass(className);
            //return super.loadClass(className);
        }
    }

    @Override
    protected ChannelBuffer extractFrame(ChannelBuffer buffer, int index, int length) {
        return buffer.slice(index, length);
    }

}