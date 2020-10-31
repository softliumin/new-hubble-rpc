package cc.zody.hubble.rpc.core.bean;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 解码
 *
 * @author zody
 */
public class HubbleDecoder extends ByteToMessageDecoder {
    private Class<?> genericClass;

    public HubbleDecoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    @Override
    public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 4) {
            return;
        }
        in.markReaderIndex();
        int tt = in.readInt();
//        System.out.println("ttttttttttttttttttttttttttt:" + tt);

        int dataLength = in.readInt();
        if (dataLength < 0) {
            ctx.close();
        }
        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
            return;
        }
        //请求来的字节数组
        byte[] data = new byte[dataLength];
        in.readBytes(data);

        Object obj = HubbleSerializationUtil.deserialize(data, genericClass);
        out.add(obj);
    }
}
