package test.ygy;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.ObservableExecutionMode;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

import java.util.concurrent.Future;

/**
 * Created by guoyao on 2017/8/10.
 */
@Service("consumerService")
public class ConsumerService {

    private static final Logger log=LoggerFactory.getLogger(ConsumerService.class);

    @Autowired
    private RestTemplate restTemplate ;

    @HystrixCommand(fallbackMethod = "serviceFallBack")  //错误回调方法
    public String service(String key) {
        return restTemplate.getForEntity("http://client/getClient?key=" + key, String.class).getBody();
    }

    public String serviceFallBack(String key) {
        return " service is error " + key;
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

    public String observer(String key) {
        StringBuilder result=new StringBuilder();
        //CustomerObserverCommand observerCommand = new CustomerObserverCommand(restTemplate,"http://client/getClient?key=", key);
        //return observerDo(observerCommand.observe(),result);
        return observerDo(observerAnnotion(key),result);

    }

    @HystrixCommand(observableExecutionMode = ObservableExecutionMode.EAGER)
    public Observable<String> observerAnnotion(String key) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    if(!subscriber.isUnsubscribed()){
                        String value =  restTemplate.getForEntity( "http://client/getClient?key="+ key, String.class).getBody();
                        subscriber.onNext(value);   //订阅两次此事件
                        subscriber.onNext(value);
                        subscriber.onCompleted();
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }

            }
        });
    }

    public String observerDo(Observable<String> observer,StringBuilder result) {
        observer.subscribe(new Observer<String>(){
            @Override
            public void onCompleted() {
                log.info(" on completed");
            }

            @Override
            public void onError(Throwable e) {
                log.info(" on error ");
                e.printStackTrace();
            }

            @Override
            public void onNext(String s) {
                result.append(s);
                log.info(" on next :" + s );
            }
        });
        return result.toString();
    }


}
