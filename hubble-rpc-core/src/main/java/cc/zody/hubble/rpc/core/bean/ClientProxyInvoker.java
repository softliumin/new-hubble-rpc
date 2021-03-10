//package cc.zody.hubble.rpc.core.bean;
//
//import com.sun.corba.se.impl.protocol.giopmsgheaders.RequestMessage;
//import org.aopalliance.intercept.Invocation;
//
//import java.util.Map;
//
///**
// *
// * @since 2020-10-31 14:59
// */
//public class ClientProxyInvoker implements Invoker {
//
//    @Override
//    public HubbleRpcResponse invoke(BaseMessage request) {
//        HubbleRpcRequest requestMessage = (HubbleRpcRequest) request;
//        String methodName = requestMessage.getMethodName();
//
//        requestMessage.setAlias(consumerConfig.getAlias());
//        requestMessage.setClassName(consumerConfig.getInterfaceId());
//
//        MessageHeader header = requestMessage.getMsgHeader();
//        // 是否缓存，减少valueof操作？
//        header.setProtocolType(ProtocolType.valueOf(consumerConfig.getProtocol()).value());
//        header.setCodecType(CodecType.valueOf(consumerConfig.getSerialization()).value());
//        String compress = (String) consumerConfig.getMethodConfigValue(methodName,
//                Constants.CONFIG_KEY_COMPRESS, consumerConfig.getCompress());
//        if (compress != null) {
//            header.setCompressType(CompressType.valueOf(compress).value());
//        }
//        header.addHeadKey(Constants.HeadKey.timeout, consumerConfig.getMethodTimeout(methodName));
//
//        // 将接口的<jsf:param />的配置复制到invocation
//        Map params = consumerConfig.getParameters();
//        if (params != null) {
//            requestMessage.getInvocationBody().addAttachments(params);
//        }
//        // 将方法的<jsf:param />的配置复制到invocation
//        params = (Map) consumerConfig.getMethodConfigValue(methodName, Constants.CONFIG_KEY_PARAMS);
//        if (params != null) {
//            requestMessage.getInvocationBody().addAttachments(params);
//        }
//
//        //TODO for jst 1) TODO the same in JVM?
//        TraceUtils.startRpc(requestMessage);
//
//        // 调用
//        HubbleRpcResponse response = filterChain.invoke(requestMessage);
//
//        //TODO for jst
//        try {
//            //非异步调用
//            Invocation invocation = requestMessage.getInvocationBody();
//            boolean async = consumerConfig.getMethodAsync(methodName);
//            Boolean genericAsync = (Boolean) invocation.getAttachment(Constants.INTERNAL_KEY_ASYNC);
//            async = async || CommonUtils.isTrue(genericAsync);
//            if (response != null && !async) {
//                TraceUtils.rpcClientRecv(requestMessage, response);
//            }
//        } catch (Throwable e) {
//            LOGGER.error("Trace-C-rpcClientRecv-0", e.getMessage());
//        }
//
//        // 通知ResponseListener
//        // 异步的改到msgfuture处返回才是真正的异步
//        if (!consumerConfig.getMethodAsync(methodName)) {
//            notifyResponseListener(methodName, response);
//        }
//
//        // 得到结果
//        return response;
//    }
//}
