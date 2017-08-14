package test.ygy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.ygy.query.TestQuery;
import test.ygy.service.FeignService;

/**
 * Created by guoyao on 2017/8/13.
 */
@RestController
public class FeignController {

    @Autowired
    private FeignService feignService ;

    @RequestMapping("/feignService1")
    public String feignService1() {
       return feignService.clientService1();
    }

    @RequestMapping("/feignService2")
    public String feignService2() {
       return feignService.clientService2("hello");
    }
    @RequestMapping("/feignService3")
    public String feignService3() {
        TestQuery testQuery = new TestQuery();
        testQuery.setKey("hello");
       return feignService.clientService3(testQuery);
    }
}
