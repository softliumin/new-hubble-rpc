package cc.zody.hubble.rpc.core.bean;

import cc.zody.hubble.rpc.core.pool.ExecutorPool;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.reflect.FastClass;
import org.springframework.cglib.reflect.FastMethod;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.InvocationTargetException;

/**
 * @author zody
 */
public class RpcServerHandler extends SimpleChannelInboundHandler<HubbleRequest> {
    private static final Logger log = LoggerFactory.getLogger(RpcServerHandler.class);

    private transient ApplicationContext applicationContext;

    public RpcServerHandler(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }


    /**
     * 接受请求来的消息
     *
     * @param ctx
     * @param request
     * @throws Exception
     */
    @Override
    public void channelRead0(final ChannelHandlerContext ctx, HubbleRequest request) throws Exception {
        log.info("request come");
        HubbleResponse response = new HubbleResponse();
        response.setRequestId(request.getRequestId());
        try {
            ExecutorPool.BIZ_EXECUTOR.submit(() -> {
                try {
                    log.error(Thread.currentThread().getName()+":业务线程开始处理:");
                    Object result = handle(request);
                    response.setResult(result);
                    log.error(Thread.currentThread().getName()+":业务线程执行完毕:");
                    //这里的作用是 TODO
                    ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
                } catch (Exception e) {
                    log.error("执行业务逻辑中发生异常:", e);
                }
            });
        } catch (Throwable t) {
            response.setError(t);
        }

    }

    private Object handle(HubbleRequest request) throws InvocationTargetException {
        String className = request.getClassName();
        Object serviceBean = applicationContext.getBean(ContainProvider.allProvider.get(className));
//        Object serviceBean = tem.getRealRef();
        Class<?> serviceClass = serviceBean.getClass();
        String methodName = request.getMethodName();
        Class<?>[] parameterTypes = request.getParameterTypes();
        Object[] parameters = request.getParameters();

        // Method method = serviceClass.getMethod(methodName, parameterTypes);
        // method.setAccessible(true);
        // return method.invoke(serviceBean, parameters);

        FastClass serviceFastClass = FastClass.create(serviceClass);
        FastMethod serviceFastMethod = serviceFastClass.getMethod(methodName, parameterTypes);
        return serviceFastMethod.invoke(serviceBean, parameters);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("server caught exception", cause);
        ctx.close();
    }
}
