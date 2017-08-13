package test.ygy.command;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by guoyao on 2017/8/12.
 */
public class CustomerObserverCommand extends HystrixObservableCommand<String> {

    private RestTemplate restTemplate;

    private String key;

    private String url;

    public CustomerObserverCommand(RestTemplate restTemplate, String url, String key) {
        this(HystrixCommandGroupKey.Factory.asKey("stringGroup"), restTemplate, url, key);
    }

    public CustomerObserverCommand(HystrixCommandGroupKey groupKey, RestTemplate restTemplate, String url, String key) {
        super(groupKey);
        this.restTemplate=restTemplate;
        this.url=url;
        this.key=key;
    }

    @Override
    protected Observable<String> construct() {

        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    if(!subscriber.isUnsubscribed()){
                        String value =  restTemplate.getForEntity(url + key, String.class).getBody();
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
}
