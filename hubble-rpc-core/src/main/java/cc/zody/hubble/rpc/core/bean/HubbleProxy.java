package cc.zody.hubble.rpc.core.bean;

import cc.zody.hubble.rpc.core.NetUtils;

import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * 动态代理的实际处理场所
 *
 * @author zody
 */
public class HubbleProxy {
    private String serverAddress;
    private ServiceDiscovery serviceDiscovery;

    public HubbleProxy(String serverAddress) {
        this.serverAddress = serverAddress;
        //this.serviceDiscovery = new ServiceDiscovery(serverAddress);
    }

    public HubbleProxy(ServiceDiscovery serviceDiscovery) {
        this.serviceDiscovery = serviceDiscovery;
    }

    @SuppressWarnings("unchecked")
    public <T> T create(Class<?> interfaceClass) {
        return (T) Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass},
                (proxy, method, args) -> {
                    //这里是发现服务和发送请求和返回信息
                    HubbleRpcRequest request = new HubbleRpcRequest(); // 创建并初始化 RPC 请求
                    request.setRequestId(UUID.randomUUID().toString());//
                    request.setClassName(method.getDeclaringClass().getName());// 类名
                    request.setMethodName(method.getName());//方法名
                    request.setParameterTypes(method.getParameterTypes());// 类型
                    request.setParameters(args);//参数
                    if (serviceDiscovery != null) {
                        //serverAddress = serviceDiscovery.discover(interfaceClass.getName()); // 发现服务
                    }
                    String host = NetUtils.getIpAdd();
                    int port = 8080;
                    // 初始化 RPC 客户端 其实可以在本地留存一份信息
                    HubbleClient client = new HubbleClient(host, port);
                    // 通过 RPC 客户端发送 RPC 请求并获取 RPC 响应
                    HubbleRpcResponse response = client.send(request);
                    //发现异常
                    if (response.getError() != null) {
                        throw response.getError();
                    } else {
                        return response.getResult();
                    }
                }
        );
    }

}
