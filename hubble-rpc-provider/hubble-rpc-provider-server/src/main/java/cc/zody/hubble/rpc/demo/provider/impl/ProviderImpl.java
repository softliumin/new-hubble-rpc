package cc.zody.hubble.rpc.demo.provider.impl;

import cc.zody.hubble.rpc.demo.provider.IProvider;

/**
 * @author zody
 */
public class ProviderImpl implements IProvider {

    @Override
    public String testMethod(String ss) {
        System.out.println(ss);

        return "Test------>" + ss;
    }
}
