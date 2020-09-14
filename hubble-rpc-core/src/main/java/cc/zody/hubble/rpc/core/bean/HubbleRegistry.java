package cc.zody.hubble.rpc.core.bean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.io.Serializable;

/**
 * @author zody
 */
public class HubbleRegistry implements InitializingBean, DisposableBean, Serializable {
    private static final long serialVersionUID = -2508213661321690225L;

    private String id;

    private String protocol;

    private String address;

    public HubbleRegistry() {
    }

    @Override
    public void destroy() throws Exception {

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //System.out.println(1111);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
