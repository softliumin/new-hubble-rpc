# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project

`hubble-rpc` is a learning-grade Java RPC framework (parent `cc.zody:hubble-all:1.0-SNAPSHOT`, JDK 1.8) targeting Spring Boot 2.3.3 with both XML (`<hubble:*>`) and starter-style configuration. Core transport is Netty 4.1, default serialization is Protostuff, and ZooKeeper is the intended registry (mostly stubbed out today — see README's "最紧急要修复的功能" list for what is still mock/not-wired).

## Build / Run

Multi-module Maven build, no wrapper:

```bash
mvn -DskipTests clean install                       # build all modules from root
mvn -pl hubble-rpc-core -am clean install           # build a single module + deps
```

Run the demo end-to-end (provider then consumer, separate terminals):

```bash
mvn -pl hubble-rpc-provider/hubble-rpc-provider-server spring-boot:run   # HTTP 8889, RPC 8080
mvn -pl hubble-rpc-consumer spring-boot:run                              # HTTP 8888 -> calls 8080
# then GET http://localhost:8888/demo
```

There is no test suite; `mvn test` is a no-op. Lint/format tooling is not configured.

## Module layout (the "why")

- `hubble-rpc-core` — every reusable piece lives here: Netty server/client, codecs, Spring XML namespace handler, registry/provider/consumer beans, thread pool, protocol structs. Both provider and consumer apps depend on this.
- `hubble-rpc-provider/hubble-rpc-provider-client` — **shared API jar** (only interfaces, e.g. `IProvider`). Consumer depends on it to get the interface; server depends on it to implement.
- `hubble-rpc-provider/hubble-rpc-provider-server` — Spring Boot launcher that imports `spring-hubble.xml` and wires the implementation bean.
- `hubble-rpc-consumer` — Spring Boot HTTP app that imports `spring-hubble.xml` and exposes a controller calling the proxied interface.
- `hubble-spring-boot-starter` — alternative annotation-style entry point (`@EnableHubbleConfiguration` + `HubbleAutoConfiguration`); largely scaffolding, not feature-complete. Note this module's POM still pulls Spring Boot 1.5.9 and is **not** a child of any other module's classpath; the demo apps configure RPC via XML, not via the starter.

## Wiring & call flow

1. Spring discovers `cc.zody.hubble.rpc.core.HubbleNamespaceHandler` via `hubble-rpc-core/src/main/resources/META-INF/spring.handlers` + `spring.schemas` (XSD at `META-INF/hubble.xsd`, namespace `http://www.sharper.cc/hubble`). The handler registers four `BeanDefinitionParser`s: `registry`, `provider`, `consumer`, `server`.
2. **Server side**: `HubbleServer.afterPropertiesSet()` boots a Netty `NioEventLoopGroup` on port 8080 (the XML `port` attr is currently ignored — `int port = Integer.parseInt("8080")` is hardcoded in `HubbleServer.java:71`). Pipeline: `HubbleDecoder(HubbleRpcRequest)` → `HubbleEncoder(HubbleRpcResponse)` → `RpcServerHandler`.
3. **Provider registration** (`HubbleProvider.afterPropertiesSet`): puts `interfaceName → springBeanName` into the static map `ContainProvider.allProvider`. ZooKeeper registration is commented out. `RpcServerHandler` looks up the impl by interface name from this map and dispatches via cglib `FastClass`/`FastMethod` on `ExecutorPool.BIZ_EXECUTOR` (`cpuNum`..`cpuNum*2` threads).
4. **Consumer side**: `HubbleConsumer` is a `FactoryBean`; `getObject()` returns a `HubbleProxy.create(...)` JDK dynamic proxy. On each method call the proxy builds a `HubbleRpcRequest` (UUID, className, methodName, paramTypes, args) and instantiates a **new** `HubbleClient(host, 8080)` per call (host = `NetUtils.getIpAdd()`, **not** the registry address — service discovery via `ServiceDiscovery` is stubbed). The client opens a Netty channel, sends one request, blocks on `obj.wait()` until the response arrives, then closes. There is no connection pool or long-lived channel.
5. **Wire format** (`HubbleEncoder`/`HubbleDecoder`): `int magic(=110) | int length | protostuff bytes`. Both encoder and decoder are typed to a single class (`HubbleRpcRequest` on server-inbound / client-outbound, `HubbleRpcResponse` on the reverse), so do not reuse a pipeline for both directions of the same role.
6. **Serialization** (`HubbleSerializationUtil`): Protostuff with `RuntimeSchema` cached in a `ConcurrentHashMap`, instances created via Objenesis (no-arg constructor not required). The `HubbleConstant.CodecType` enum lists protobuf/fastjson/java/msgpack/hessian as planned alternatives but only protostuff is wired.

## Conventions to keep in mind when editing

- Package root is `cc.zody.hubble.rpc.*`. New core types go under `cc.zody.hubble.rpc.core.bean` (or `.codec` / `.io` / `.pool` / `.error` if the existing split applies).
- The XML namespace `http://www.sharper.cc/hubble` is referenced from both demo apps' `spring-hubble.xml` and from `META-INF/spring.schemas`. Changing the namespace requires updating all four files.
- Demo XML uses `address="sharper.cc"` for the registry — this is a placeholder and is not actually contacted (ZK calls in `HubbleProvider`/`HubbleProxy` are commented out). Do not assume the registry path is exercised.
- `@author zody` is the standing author tag in javadoc headers.
- README and code comments are in Chinese; preserve that when editing in-place.
