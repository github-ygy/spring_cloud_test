package test.ygy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by guoyao on 2017/8/7.
 */
@RestController
public class HelloController {


    @GetMapping("/helloworld")
    public String helloworld() {

        return " hello world ";
    }
}
