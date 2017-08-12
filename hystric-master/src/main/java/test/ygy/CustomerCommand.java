package test.ygy;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.springframework.web.client.RestTemplate;

/**
 * Created by guoyao on 2017/8/12.
 */
public class CustomerCommand extends HystrixCommand<String> {

    private RestTemplate restTemplate ;

    private String key ;

    private String url ;

    public CustomerCommand(RestTemplate restTemplate ,String url, String key) {
        this(HystrixCommandGroupKey.Factory.asKey("stringGroup"),restTemplate,url,key);
    }

    public CustomerCommand(HystrixCommandGroupKey groupKey , RestTemplate restTemplate,String url, String key) {
        super(groupKey);
        this.restTemplate = restTemplate ;
        this.url=url;
        this.key=key;
    }

    @Override    // 命令模式中真正的业务实现方法
    protected String run() throws Exception {
        return restTemplate.getForEntity(url + key, String.class).getBody();
    }

    @Override    //非注解的方法回调
    protected String getFallback() {
        return  null ;
    }
}
