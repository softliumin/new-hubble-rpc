package cc.zody.hubble.rpc.core.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Hubble的服务提供者
 *
 * @author zody
 */
public class HubbleProvider<T> extends ProviderConfig
        implements InitializingBean, DisposableBean, ApplicationContextAware, ApplicationListener, BeanNameAware {
    private static final long serialVersionUID = -2508213661321690225L;

    private transient ApplicationContext applicationContext;

    private String id;

    private String ref;

    private String alias;

    private String inter;

    protected transient T realRef;

    public T getRealRef() {
        return realRef;
    }

    public void setRealRef(T realRef) {
        this.realRef = realRef;
    }

    @Override
    public void setBeanName(String name) {

    }

    @Override
    public void destroy() throws Exception {

    }

    /**
     * 属性加载完成之后的执行，去注册中心注册的服务
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        HubbleRegistry re = this.applicationContext.getBean(HubbleRegistry.class);

        HubbleServer server = this.applicationContext.getBean(HubbleServer.class);

//        ZookeeperUtil zku = new ZookeeperUtil(re.getAddress());
//
//        zku.register(this.getInter(), NetUtils.getLocalHost() + ":" + server.getPort());//存放的是接口地址和端口地址

        this.getRealRef().toString();
        ContainProvider.allProvider.put(this.getInter(), this.getRef());
    }

    /**
     * 未知
     *
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        //spring加载完毕
        if (applicationEvent instanceof ContextRefreshedEvent) {
            create();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getInter() {
        return inter;
    }

    public void setInter(String inter) {
        this.inter = inter;
    }
}
