package cc.zody.hubble.rpc.core.pool;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author zody
 */
public class ThreadPoolUtils {

    private static final Logger log = LoggerFactory.getLogger(ThreadPoolUtils.class);

    public static void stop(ExecutorService service) {
        if (null != service) {
            int tryTime = 3;
            service.shutdown();
            while (tryTime-- > 0) {
                try {
                    service.awaitTermination(1, TimeUnit.SECONDS);
                    if (service.isTerminated()) {
                        break;
                    }
                } catch (InterruptedException e) {
                    log.error("terminated error.", e);
                }
            }
        }
    }
}
