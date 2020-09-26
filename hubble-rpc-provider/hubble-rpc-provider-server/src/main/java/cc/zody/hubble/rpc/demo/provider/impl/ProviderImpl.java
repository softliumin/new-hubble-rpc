package cc.zody.hubble.rpc.demo.provider.impl;

import cc.zody.hubble.rpc.demo.provider.IProvider;

import java.util.List;

/**
 * @author zody
 */
public class ProviderImpl implements IProvider {

    @Override
    public String testMethod(List<String> ss) {
        System.out.println(ss);

        return "Test------>" + ss;
    }
}
