package cc.zody.hubble.rpc.core;

import cc.zody.hubble.rpc.core.bean.HubbleConsumer;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * 解析调用者  半成品
 *
 * @author zody
 */
public class HubbleConsumerParser implements BeanDefinitionParser {

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        String id = element.getAttribute("id");
        String inter = element.getAttribute("interface");
        String alias = element.getAttribute("alias");
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(HubbleConsumer.class);
        beanDefinition.setLazyInit(false);
        beanDefinition.getPropertyValues().add("id", id);
        beanDefinition.getPropertyValues().add("inter", inter);
        beanDefinition.getPropertyValues().add("alias", alias);
        //去容器注册bean
        parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);

        return beanDefinition;
    }
}
