package cc.zody.hubble.rpc.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * @author zody
 */
public class NetUtils {
    private final static Logger logger = LoggerFactory.getLogger(NetUtils.class);

    /**
     * 得到本机IPv4地址
     *
     * @return ip地址
     */
    @Deprecated
    public static String getLocalHost() {
        InetAddress address = getLocalAddress();
        return address == null ? null : address.getHostAddress();
    }


    public static String getIpAdd() {
        String ip = "";
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                String name = intf.getName();
                if (!name.contains("docker") && !name.contains("lo")) {
                    for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                        //获得IP
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress()) {
                            String ipaddress = inetAddress.getHostAddress().toString();
                            if (!ipaddress.contains("::") && !ipaddress.contains("0:0:") && !ipaddress.contains("fe80")) {
                                if (!"127.0.0.1".equals(ip)) {
                                    ip = ipaddress;
                                }
                            }
                        }
                    }
                }
            }
            return ip;
        } catch (Exception e) {
            logger.error("Get ip has_error, will use 127.0.0.1 instead.", e);
            return "127.0.0.1";
        }

    }

    public static InetAddress getLocalAddress() {
        InetAddress localAddress = null;
        try {
            localAddress = InetAddress.getLocalHost();
            if (isValidAddress(localAddress)) {
                return localAddress;
            }
        } catch (Throwable e) {
            logger.warn("Error when retriving ip address: " + e.getMessage(), e);
        }

        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            if (interfaces != null) {
                while (interfaces.hasMoreElements()) {
                    try {
                        NetworkInterface network = interfaces.nextElement();
                        Enumeration<InetAddress> addresses = network.getInetAddresses();
                        if (addresses != null) {
                            while (addresses.hasMoreElements()) {
                                try {
                                    InetAddress address = addresses.nextElement();
                                    if (isValidAddress(address)) {
                                        return address;
                                    }
                                } catch (Throwable e) {
                                    logger.warn("Error when retriving ip address: " + e.getMessage(), e);
                                }
                            }
                        }
                    } catch (Throwable e) {
                        logger.warn("Error when retriving ip address: " + e.getMessage(), e);
                    }
                }
            }
        } catch (Throwable e) {
            logger.warn("Error when retriving ip address: " + e.getMessage(), e);
        }
        logger.error("Can't get valid host, will use 127.0.0.1 instead.");
        return localAddress;
    }


    /**
     * 是否合法地址（非本地，非默认的IPv4地址）
     *
     * @param address InetAddress
     * @return 是否合法
     */
    private static boolean isValidAddress(InetAddress address) {
//        if (address == null || address.isLoopbackAddress())
//            return false;
//        String name = address.getHostAddress();
//        return (name != null
//                && !isAnyHost(name)
//                && !isLocalHost(name)
//                && isIPv4Host(name));

        return true;
    }

    public static void main(String[] args) {
        System.out.println(getLocalHost());
        System.out.println(getIpAdd());
    }


}
