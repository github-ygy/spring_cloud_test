package test.ygy;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by guoyao on 2017/8/13.
 */
@FeignClient("client")   //feign 注解调用client服务
public interface FeignService {

    @RequestMapping("/clientService")
    String clientService();

}
