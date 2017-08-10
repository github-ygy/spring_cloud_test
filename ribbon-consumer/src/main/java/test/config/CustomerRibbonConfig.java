package test.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by guoyao on 2017/8/10.
 */
@Configuration
public class CustomerRibbonConfig {

    @Bean
    public IRule ribbonRule() {
        return new RandomRule();
    }
}
