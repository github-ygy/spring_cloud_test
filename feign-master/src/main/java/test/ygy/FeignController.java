package test.ygy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by guoyao on 2017/8/13.
 */
@RestController
public class FeignController {

    @Autowired
    private FeignService feignService ;

    @RequestMapping("/helloService")
    public String helloService() {
       return feignService.clientService();
    }
}
