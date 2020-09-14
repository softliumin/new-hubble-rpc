package cc.zody.hubble.rpc.core;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * 总的解析自己定义的hubble标签！
 * @author zody
 */
public class HubbleNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("registry", new HubbleRegistryParser());

        registerBeanDefinitionParser("provider", new HubbleProviderParser());

        //调用端 第一步
        registerBeanDefinitionParser("consumer", new HubbleConsumerParser());

        //server是必须要的
        registerBeanDefinitionParser("server", new HubbleServerParser());
    }
}
