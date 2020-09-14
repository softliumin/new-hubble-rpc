package cc.zody.hubble.rpc.demo.provider.impl;

import cc.zody.hubble.rpc.demo.provider.IProvider2;

/**
 * @author zody
 */
public class ProviderImpl2 implements IProvider2 {

    public String testMethod(String ss) {
        System.out.println(ss);
        return "Test------>" + ss;
    }
}
