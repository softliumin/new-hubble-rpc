package cc.zody.hubble.rpc.core;

//import org.apache.zookeeper.CreateMode;
//import org.apache.zookeeper.KeeperException;
//import org.apache.zookeeper.Watcher;
//import org.apache.zookeeper.ZooDefs;
//import org.apache.zookeeper.ZooKeeper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.IOException;
//import java.util.concurrent.CountDownLatch;

/**
 * 注册中心服务工具类
 * 2.0 版本使用的是zk原生客户端，下一版本会换成第三方客户端
 *
 * @author zody
 */
public class ZookeeperUtil {
//    private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperUtil.class);
//
//    private CountDownLatch latch = new CountDownLatch(1);
//    private String registryAddress;
//
//
//    public ZookeeperUtil(String registryAddress) {
//        this.registryAddress = registryAddress;
//    }
//
//    public void register(String iname, String ip) {
//        if (iname != null) {
//            ZooKeeper zk = connectServer();
//            if (zk != null) {
//                registerService(zk, iname, ip);
//            }
//        }
//    }
//
//
//    //
//
//    /**
//     * 连接注册中心
//     *
//     * @return
//     */
//    private ZooKeeper connectServer() {
//        ZooKeeper zk = null;
//        try {
//            zk = new ZooKeeper(registryAddress, 5000, event -> {
//                if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
//                    latch.countDown();
//                }
//            });
//            latch.await();
//        } catch (IOException e) {
//            LOGGER.error("注册中心发生IO异常", e);
//        } catch (InterruptedException e) {
//            LOGGER.error("获取注册服务中断异常", e);
//        }
//        return zk;
//    }
//
//
//    //注册服务 路径是/hubble/provider/接口名称  内容是IP地址
//
//    private void registerService(ZooKeeper zk, String iname, String ip) {
//        try {
//            byte[] bytes = ip.getBytes();
//            //临时序列节点 EPHEMERAL_SEQUENTIAL
//            String path = zk.create("/hubble/provider/" + iname, bytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
//            LOGGER.info("hubble注册服务成功{},{}", path, iname);
//        } catch (KeeperException e) {
//            LOGGER.error("KeeperException 异常", e);
//        } catch (InterruptedException e) {
//            LOGGER.error("InterruptedException 异常", e);
//        }
//    }

}
