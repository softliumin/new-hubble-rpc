package cc.zody.hubble.rpc.starter;

import org.springframework.beans.factory.DisposableBean;

/**
 * @author zody
 * @since 2020-09-11 11:57
 */
public final class HubbleServer implements DisposableBean {

    private volatile boolean stopAwait = false;

    private volatile Thread awaitThread = null;

    public void await() {
        try {
            this.awaitThread = Thread.currentThread();
            while (!this.stopAwait) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ex) {
                    // continue and check the flag
                }
            }
        } finally {
            this.awaitThread = null;
        }
    }

    public void stopAwait() {
        this.stopAwait = true;
    }

    @Override
    public void destroy() throws Exception {
        this.stopAwait();
        Thread t = this.awaitThread;
        if (t != null) {
            t.interrupt();
            try {
                t.join(1000);
            } catch (InterruptedException e) {
                // Ignored
            }
        }
    }
}
