package cc.zody.hubble.rpc.consumer.controller;

import cc.zody.hubble.rpc.demo.provider.IProvider;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @author zody
 */
@RestController
public class TestController {

    @Resource
    IProvider iProvider;


//    @HubbleConsumer
//    IProvider iProvider;

    @RequestMapping("/demo")
    public String test() {
//        String result = iProvider.testMethod(Arrays.asList("hello"));
//        return result;
        return null;
    }


    @RequestMapping("/")
    public String test001() {
        return "嘉佳爱胖胖！很爱！";
    }
}
