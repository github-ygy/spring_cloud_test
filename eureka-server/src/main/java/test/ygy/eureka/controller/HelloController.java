package test.ygy.eureka.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by guoyao on 2017/8/8.
 */

@RestController
public class HelloController {

    private static final Logger log=LoggerFactory.getLogger(HelloController.class);

    @RequestMapping("/helloworld")
    public String helloworld(@RequestParam String key) {
        log.warn( "  this is server master !");
        return " hello " + key ;
    }
}
