package cc.zody.hubble.rpc.consumer.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zody
 */
@RequestMapping("/test")
@RestController
public class TestController {

    @RequestMapping("/demo")
    public String test() {
        return "111";
    }
}
