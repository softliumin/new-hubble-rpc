package cc.zody.hubble.rpc.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 关于hubble的配置
 * @author zody
 * @since 2020-09-10 20:27
 */
@ConfigurationProperties(prefix = "hubble")
public class HubbleProperties {

    /**
     * 注册中心地址
     */
    private String registerHost;

    /**
     * 注册中心端口
     */
    private String registerPort;

    public String getRegisterHost() {
        return registerHost;
    }

    public void setRegisterHost(String registerHost) {
        this.registerHost = registerHost;
    }

    public String getRegisterPort() {
        return registerPort;
    }

    public void setRegisterPort(String registerPort) {
        this.registerPort = registerPort;
    }
}
