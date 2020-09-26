package cc.zody.hubble.rpc.core.bean;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 发送消息的编码
 * @author zody
 */
public class HubbleEncoder  extends MessageToByteEncoder
{
    private Class<?> genericClass;

    public HubbleEncoder(Class<?> genericClass)
    {
        this.genericClass = genericClass;
    }

    @Override
    public void encode(ChannelHandlerContext ctx, Object in, ByteBuf out) throws Exception
    {
        if (genericClass.isInstance(in))
        {
            byte[] data = HubbleSerializationUtil.serialize(in);
            // 测试请求头的解析
            out.writeInt(110);
            out.writeInt(data.length);
            out.writeBytes(data);
        }else
        {
            //异常
        }
    }
}
