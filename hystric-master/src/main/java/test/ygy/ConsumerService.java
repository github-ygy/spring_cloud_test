package test.ygy;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
}
