package test.ygy.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import test.ygy.query.TestQuery;

/**
 * Created by guoyao on 2017/8/13.
 */
@FeignClient("client")   //feign 注解调用client服务
public interface FeignService {

    @RequestMapping("/clientService1")
    String clientService1();

    @RequestMapping("/clientService2")
    String clientService2(@RequestParam("key")String key);

    @RequestMapping("/clientService3")
    String clientService3(@RequestBody TestQuery query);
}
