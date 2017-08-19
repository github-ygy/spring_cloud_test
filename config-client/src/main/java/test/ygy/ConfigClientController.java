package test.ygy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by guoyao on 2017/8/18.
 */
@RestController
@RefreshScope
public class ConfigClientController {


    @Value("${from}")
    private String from ;

    @RequestMapping("/getConfig")
    public String getConfig() {

        return this.from;
    }
}
