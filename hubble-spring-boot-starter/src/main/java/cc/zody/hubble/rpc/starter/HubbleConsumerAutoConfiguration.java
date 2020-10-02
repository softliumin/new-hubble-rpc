package cc.zody.hubble.rpc.starter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author zody
 * @since 2020-09-11 11:12
 */
@Configuration
@ConditionalOnBean(annotation = EnableHubbleConfiguration.class)
@AutoConfigureAfter(HubbleAutoConfiguration.class)
@EnableConfigurationProperties(HubbleProperties.class)
public class HubbleConsumerAutoConfiguration {

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void init() throws Exception {
        System.out.println("========hubble_rpc_is_enable========");
    }
}
