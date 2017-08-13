package test.ygy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
//@SpringCloudApplication
@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker  // 开启容错处理
@ServletComponentScan   //开启扫描filter
@EnableHystrixDashboard   //开启仪表
public class HystricMasterApplication {

	@Bean
	@LoadBalanced     //负载均衡
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(HystricMasterApplication.class, args);
	}
}
