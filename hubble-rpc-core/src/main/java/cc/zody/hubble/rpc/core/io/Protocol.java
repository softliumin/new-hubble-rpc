package cc.zody.hubble.rpc.core.io;

import io.netty.buffer.ByteBuf;

/**
 * @author zody
 */
public interface Protocol {

    Object decode(ByteBuf datas, Class clazz);


    Object decode(ByteBuf datas, String classTypeName);

    ByteBuf encode(Object obj, ByteBuf buffer);
}
