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
    public String service(String key) {
        return restTemplate.getForEntity("http://client/getClient?key=" + key, String.class).getBody();
    }

    public String serviceFallBack() {
        return " service is error ";
    }

    //异步获取
    @HystrixCommand
    public Future<String> serviceAsyn(final String key) {
        return  new AsyncResult<String>() {
            @Override
            public String invoke() {
                return  restTemplate.getForEntity("http://client/getClientDelay?key=" + key, String.class).getBody();
            }
        };
    }
    @HystrixCommand  //同步获取 用来比较异步获取
    public String serviceSyn(String key) {
        return restTemplate.getForEntity("http://client/getClientDelay?key="+ key, String.class).getBody();
    }

    //自定义customerCommand 来实现容错机制  同步模式
    public String customerSyn(String key) {
        // return customerASyn(key).get();
        CustomerCommand customerCommand=new CustomerCommand(restTemplate,"http://client/getClientDelay?key=", key);
        return  customerCommand.execute();
    }

    //自定义customerCommand 来实现容错机制   future模式
    public Future<String> customerASyn(String key) {
        CustomerCommand customerCommand=new CustomerCommand(restTemplate,"http://client/getClientDelay?key=", key);
        return  customerCommand.queue();
    }



}
