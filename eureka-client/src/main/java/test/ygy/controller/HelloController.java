package test.ygy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * Created by guoyao on 2017/8/7.
 */
@RestController
public class HelloController {

    private static final Logger log=LoggerFactory.getLogger(HelloController.class);

    @GetMapping("/clientService1")
    public String clientService1() {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            log.error(" thread sleep error",e);
        }
        log.info("  this is eureka client helloService ");
        return " hello ,i am eureka helloService";
    }

    @RequestMapping("/clientService2")
    public String clientService2(@RequestParam("key")String key){
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            log.error(" thread sleep error",e);
        }
        return  key;
    }


    @RequestMapping("/clientService3")
    public String clientService3(@RequestBody TestQuery query){
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            log.error(" thread sleep error",e);
        }
        return  "query  : " + query.getKey() ;
    }



    @GetMapping("/getClient")
    public String getClient(@RequestParam String key) {
        log.info("  this is eureka client server ");
        return " hello ,i am eureka client" + key;
    }

    @GetMapping("/getClientDelay")
    public String getClientDelay(@RequestParam String key) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("  this is eureka client asyn server");
        return " hello ,i am eureka client server asyn" + key;
    }

    @RequestMapping("/getAllClient")
    public List<String> getAllClient(String keys) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return Arrays.asList(keys.split(","));
    }
}
