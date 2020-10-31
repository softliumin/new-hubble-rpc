package cc.zody.hubble.rpc.starter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zody
 * @since 2020-09-10 20:28
 */
@Configuration
@ConditionalOnClass({HubbleProperties.class})
@EnableConfigurationProperties(HubbleProperties.class)
public class HubbleAutoConfiguration {

    @Autowired
    private HubbleProperties hubbleProperties;

    @Bean
    @ConditionalOnMissingBean
    public HubbleServer hubbleServer() {
        HubbleServer hubbleServer = new HubbleServer();
        return hubbleServer;
    }
}
