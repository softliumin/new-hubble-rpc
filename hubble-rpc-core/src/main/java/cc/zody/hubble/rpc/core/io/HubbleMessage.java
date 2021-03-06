package cc.zody.hubble.rpc.core.io;

/**
 * hubble的消息结构
 * @author zody
 */
public class HubbleMessage
{
    private HubbleMessageHeader header;
    private Object data;

    public HubbleMessage()
    {
    }

    public HubbleMessage(HubbleMessageHeader header)
    {
        this.header = header;
    }

    public HubbleMessage(HubbleMessageHeader header, Object data)
    {
        this.header = header;
        this.data = data;
    }

    public HubbleMessageHeader getHeader()
    {
        return header;
    }

    public void setHeader(HubbleMessageHeader header)
    {
        this.header = header;
    }

    public Object getData()
    {
        return data;
    }

    public void setData(Object data)
    {
        this.data = data;
    }
}
