package test.ygy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by guoyao on 2017/8/7.
 */
@RestController
public class HelloController {

    private static final Logger log=LoggerFactory.getLogger(HelloController.class);

    @GetMapping("/getClient")
    public String getClient() {
        log.info("  this is eureka client server ");
        return " hello ,i am eureka client";
    }
}
