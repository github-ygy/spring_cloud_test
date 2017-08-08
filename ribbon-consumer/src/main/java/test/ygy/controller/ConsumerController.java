package test.ygy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by guoyao on 2017/8/8.
 */
@RestController
public class ConsumerController {

    @Autowired
    private RestTemplate restTemplate ;

    @RequestMapping("/consumerServer")
    public String consumerServer() {

        return  restTemplate.getForEntity("http://SERVER/helloworld?key=ygy",String.class).getBody();
    }

    @RequestMapping("/consumerClient")
    public String consumerClient() {

        return  restTemplate.getForEntity("http://client/getClient?key=client",String.class).getBody();
    }
}
