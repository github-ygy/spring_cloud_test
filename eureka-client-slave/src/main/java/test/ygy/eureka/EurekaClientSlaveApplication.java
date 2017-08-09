package test.ygy.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EurekaClientSlaveApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaClientSlaveApplication.class, args);
	}
}
