package cc.zody.hubble.rpc.launcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * rpc提供者的启动类
 *
 * @author zody
 * @since 2020-09-11 18:07
 */
@SpringBootApplication
public class HubbleProviderRpcApplication {

    public static void main(String[] args) {
        SpringApplication.run(HubbleProviderRpcApplication.class, args);
    }

}
