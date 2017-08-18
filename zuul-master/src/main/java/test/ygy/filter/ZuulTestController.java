package test.ygy.filter;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by guoyao on 2017/8/18.
 */
@RestController
@RequestMapping("/local")
public class ZuulTestController {


    @RequestMapping("/serviceAsyn")
    public String serviceAsyn(@RequestParam String key) {

        return " this is local controller  " + key;
    }
}
