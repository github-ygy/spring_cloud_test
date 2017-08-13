package test.ygy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient  // 开启eureka消费端
@EnableFeignClients
public class FeignMasterApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeignMasterApplication.class, args);
	}
}
