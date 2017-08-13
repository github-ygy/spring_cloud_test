package test.ygy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import test.ygy.util.ConcurrentDateUtil;

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
    public String consumerClient(@RequestParam String key) {
        return consumerService.service(key);
    }

    @RequestMapping("/serviceSyn")  //同步调用
    public String serviceSyn(@RequestParam String key) {
        Date date1=new Date();
        String str=consumerService.serviceSyn(key);
        //String str=consumerService.customerSyn(key);
        Date date2=new Date();
        consumerService.serviceSyn(key);   //第二次调用检测cache
        Date date3=new Date();
        log.info(" data2 - date 1 = " + ConcurrentDateUtil.diffDate(date1, date2, ConcurrentDateUtil.Type.MILL));
        log.info(" data3 - date 2 = " + ConcurrentDateUtil.diffDate(date2, date3, ConcurrentDateUtil.Type.MILL));
        return str;
    }

    @RequestMapping("/serviceAsyn")   //异步调用
    public String serviceAsyn(@RequestParam String key) {
        Date date1=new Date();
        Future<String> stringFuture=consumerService.serviceAsyn(key);
        //Future<String> stringFuture=consumerService.customerASyn(key);
        Date date2=new Date();
        String value=null;
        try {
            value=stringFuture.get(4000, TimeUnit.MILLISECONDS);
            Date date3=new Date();
            log.info(" data2 - date 1 = " + ConcurrentDateUtil.diffDate(date1, date2, ConcurrentDateUtil.Type.MILL));
            log.info(" data3 - date 2 = " + ConcurrentDateUtil.diffDate(date2, date3, ConcurrentDateUtil.Type.MILL));

        } catch (Exception e) {
            log.error(" error ", e);
        }
        return value;
    }

    @RequestMapping("/observer")
    public String observer(@RequestParam String key) {
        return consumerService.observer(key);
    }

}
