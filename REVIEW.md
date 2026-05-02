# 项目点评：new-hubble-rpc

定位很清楚 —— 这是一个**学习性质的 RPC 框架**，README 自己也列了"最紧急要修复"清单。从这个定位出发评分是合理的；如果当生产框架看，问题会非常多。

## 做得不错的地方

- **模块切分合理**：`core` 放公共逻辑，`provider-client` 单独抽出接口 jar 给两边共用，这是企业 RPC 项目的标准玩法。
- **Spring XML 扩展接得很正**：`META-INF/spring.handlers` + `spring.schemas` + `hubble.xsd` + `NamespaceHandlerSupport` 这一套写得规范，能学到东西。
- **业务线程池与 IO 线程分离**：`RpcServerHandler` 把请求扔到 `ExecutorPool.BIZ_EXECUTOR`，方向是对的。
- **私有协议有边界**：`magic(int) + length(int) + payload` 这种 length-prefix 框架避免了粘包/拆包，虽然简陋但正确。
- **用 cglib `FastClass`/`FastMethod`** 替代 `Method.invoke`，知道反射开销这一点说明动过脑子。

## 严重问题（功能性 / 性能层面）

按危害程度排：

1. **每次 RPC 调用都新建一条 TCP 连接**（`HubbleProxy.java:45` + `HubbleClient.send`）：每调用一次就 `new NioEventLoopGroup()`、connect、wait、close、shutdownGracefully。这是 RPC 框架的反面教材，QPS 上不去就是这里。README 里 "保持长链接" "客户端使用线程池" 都还没做。
2. **服务发现是装饰性的**：`HubbleProvider.afterPropertiesSet` 里 ZK 注册全部注释掉了；`HubbleProxy` 里 `serviceDiscovery.discover(...)` 也注释掉了，实际地址是 `NetUtils.getIpAdd()`（消费者自己的 IP）+ 硬编码 `8080`。registry 配的 `address="sharper.cc"` 完全没人读。
3. **`HubbleServer.java:71` 硬编码端口**：`int port = Integer.parseInt("8080")` —— XML 里配的 `port` 属性虽然 setter 收下了，但启动时根本没用，所有服务实际都跑在 8080。
4. **`HubbleClient` 的 `obj.wait()` 没有超时**：响应丢了线程就永久挂住，没有超时/重试/熔断。
5. **每次请求后 `ChannelFutureListener.CLOSE`**：服务端写完响应立刻关连接，即使客户端想复用也没机会。
6. **`ContainProvider.allProvider` 是 `static` 全局 Map**：同一 JVM 多 context、多次刷新会脏掉，不可隔离。

## 工程层面的小问题

- **starter 模块和主项目脱节**：`hubble-spring-boot-starter` 用的是 Spring Boot **1.5.9** + Java **1.7**，而其它模块是 Spring Boot **2.3.3** + Java 1.8。两个 demo 都没用这个 starter，它实际上是死代码。
- **父 POM 的 `<netty.boot.version>2.3.3.RELEASE</netty.boot.version>`** —— 这个属性名叫 netty 但值是 Spring Boot 版本，而且根本没人引用，Netty 真正版本 `4.1.52.Final` 是硬写在 core POM 里的。
- **大量注释掉的代码留在仓库里**：etcd/consul 依赖、ZK 注册逻辑、`LengthFieldBasedFrameDecoder`、`testMethod` 调用…… 应该删掉而不是注释。
- **`HubbleConstant.DEFAULT_CODEC_TYPE = msgpack`**，但 pom 里只有 protostuff，枚举里列的 fastjson/java/msgpack/hessian 都没接。
- **魔数无名**：`out.writeInt(110)` 没有常量，读代码的人不知道 110 是什么。
- **空生命周期方法到处都是**：`HubbleRegistry.afterPropertiesSet`、`destroy`、`HubbleConsumer.afterPropertiesSet` 全是空实现。
- **`TestController` 里有 `"嘉佳爱胖胖！很爱！"`** 这种调试串留在 `/` 路由上，提交前没清。
- **零测试**：`mvn test` 是空操作。RPC 框架最该有的就是端到端集成测试。
- **服务端 `EventLoopGroup` 不会优雅关闭**：`HubbleServer` 里 `shutdownGracefully()` 被注释了。

## 如果想继续推进，建议的优先级

1. 先把 **客户端连接复用** 做掉（每个 `host:port` 维护一个 `Channel`，请求用 requestId 关联响应） —— 这一项做完性能至少十倍起。
2. 把 **ZK 真正接进去**（`HubbleProvider` 注册、`HubbleProxy` 拉地址 + watch），`address` 字段才有意义。
3. **响应等待加超时**，补一个 `Future`/`Promise` 风格的同步原语，别再 `synchronized + wait`。
4. 删掉所有注释代码 + 把 `starter` 模块版本对齐（或者直接删掉，反正没人用）。
5. 最后再去碰 README 上 "SPI"、"负载均衡"、"序列化多样化" 这些大头。

## 总评

作为 Dubbo / JSF 的拆解学习项目很有价值 —— 把 Netty 编解码、Spring 自定义命名空间、动态代理这套串起来跑通了。但里面还有相当多 "把代码写到能跑" 之后没有回头收尾的地方，离生产框架的"骨架"还差一截，主要差在**网络资源管理**和**服务发现**这两块。
