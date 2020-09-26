package cc.zody.hubble.rpc.consumer.launcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * @author zody
 */
@ImportResource({"classpath*:/spring-hubble.xml"})
@SpringBootApplication
@ComponentScan(basePackages = {"cc.zody.hubble.rpc"})
public class HubbleRpcConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HubbleRpcConsumerApplication.class, args);
    }

}
