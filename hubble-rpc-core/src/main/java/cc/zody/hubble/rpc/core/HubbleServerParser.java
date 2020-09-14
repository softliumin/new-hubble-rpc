package cc.zody.hubble.rpc.core;

import cc.zody.hubble.rpc.core.bean.HubbleServer;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * 解析服务器
 * @author zody
 */
public class HubbleServerParser implements BeanDefinitionParser {

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        String id = element.getAttribute("id");
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(HubbleServer.class);
        beanDefinition.setLazyInit(false);
        parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);
        return beanDefinition;

    }
}
