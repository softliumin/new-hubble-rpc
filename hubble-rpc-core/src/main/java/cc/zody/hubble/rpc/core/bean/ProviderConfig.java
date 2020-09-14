package cc.zody.hubble.rpc.core.bean;

import cc.zody.hubble.rpc.core.error.InitErrorException;

import java.io.Serializable;

/**
 * @author zody
 */
public class ProviderConfig extends AbstractInterfaceConfig implements Serializable {
    private static final long serialVersionUID = -2910502986608678372L;

    public synchronized void create() throws InitErrorException {
        if (true)//直接加载，不延迟  以后可以配置
        {

        }
    }

}
