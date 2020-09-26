package cc.zody.hubble.rpc.core.pool;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 业务专用线程池
 * @author zody
 */
public class ExecutorPool {
    private static final Logger log = LoggerFactory.getLogger(ExecutorPool.class);


    private static final int cpuNum = Runtime.getRuntime().availableProcessors();


    public static final ThreadPoolExecutor BIZ_EXECUTOR = new ThreadPoolExecutor(cpuNum, cpuNum * 2, 60, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(20000), new ThreadFactory() {
        private AtomicInteger count = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            int index = count.incrementAndGet();
            return new Thread(r, "hubble-biz-pool-" + index);
        }
    }, (r, executor) -> {
        log.error("biz_pool_exhaust. {}, {}, {}", executor.getActiveCount(), executor.getTaskCount(), executor.getQueue().size());
    });

    /**
     * 异步检查通知状态任务
     */
    public static final ScheduledExecutorService CHECK_TASK = java.util.concurrent.Executors.newScheduledThreadPool(cpuNum, new ThreadFactory() {
        private AtomicInteger count = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            int index = count.incrementAndGet();
            return new Thread(r, "hubble-check-executor-" + String.valueOf(index));
        }
    });

    static {
        CHECK_TASK.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                log.info("async service counter: BIZ_EXECUTOR {}, {}, {}",
                        BIZ_EXECUTOR.getActiveCount(), BIZ_EXECUTOR.getTaskCount(), BIZ_EXECUTOR.getQueue().size());
            }
        }, 10, 10, TimeUnit.SECONDS);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            ThreadPoolUtils.stop(CHECK_TASK);
        }));
    }

}
