package test.ygy;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringCloudApplication   //开启消费 与 熔断
@EnableZuulProxy    //zuul路由
public class ZuulMasterApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZuulMasterApplication.class, args);
	}
}
