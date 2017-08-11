package test.ygy;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Future;

/**
 * Created by guoyao on 2017/8/10.
 */
@Service("consumerService")
public class ConsumerService {

    @Autowired
    private RestTemplate restTemplate ;

    @HystrixCommand(fallbackMethod = "serviceFallBack")  //错误回调方法
    public String service() {
        return restTemplate.getForEntity("http://client/getClient?key=client", String.class).getBody();
    }

    public String serviceFallBack() {
        return " service is error ";
    }

    //异步获取
    @HystrixCommand
    public Future<String> serviceAsyn() {
        return  new AsyncResult<String>() {
            @Override
            public String invoke() {
                return  restTemplate.getForEntity("http://client/getClientDelay?key=client", String.class).getBody();
            }
        };
    }
    @HystrixCommand  //同步获取 用来比较异步获取
    public String serviceSyn() {
        return restTemplate.getForEntity("http://client/getClientDelay?key=client", String.class).getBody();
    }



}
