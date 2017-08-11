package test.ygy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by guoyao on 2017/8/8.
 */
@RestController
public class ConsumerController {

    private static final Logger log=LoggerFactory.getLogger(ConsumerController.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ConsumerService consumerService;

    @RequestMapping("/consumerServer")
    public String consumerServer() {

        return restTemplate.getForEntity("http://SERVER/helloworld?key=ygy", String.class).getBody();
    }

    @RequestMapping("/consumerClient")
    public String consumerClient() {
        return consumerService.service();
    }

    @RequestMapping("/serviceSyn")  //同步调用
    public String serviceSyn() {
        Date date1=new Date();
        String str=consumerService.serviceSyn();
        Date date2=new Date();
        log.info(" data2 - date 1 = " + ConcurrentDateUtil.diffDate(date1, date2, ConcurrentDateUtil.Type.MILL));
        return str;
    }

    @RequestMapping("/serviceAsyn")   //异步调用
    public String serviceAsyn() {
        Date date1=new Date();
        Future<String> stringFuture=consumerService.serviceAsyn();
        Date date2=new Date();
        String key=null;
        try {
            key=stringFuture.get(4000, TimeUnit.MILLISECONDS);
            Date date3=new Date();
            log.info(" data2 - date 1 = " + ConcurrentDateUtil.diffDate(date1, date2, ConcurrentDateUtil.Type.MILL));
            log.info(" data3 - date 2 = " + ConcurrentDateUtil.diffDate(date2, date3, ConcurrentDateUtil.Type.MILL));

        } catch (Exception e) {
            log.error(" error ", e);
        }
        return key;
    }
}