package test.ygy.service.impl;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import test.ygy.query.TestQuery;
import test.ygy.service.FeignService;

/**
 * Created by guoyao on 2017/8/14.
 */
@Component
public class FeignServiceImpl implements FeignService {

    @Override
    public String clientService1(){
        return  "error clientService1" ;
    }

    @Override
    public String clientService2(@RequestParam("key")String key){
        return " error clientService2 ";
    }

    @Override
    public String clientService3(@RequestBody TestQuery query){
        return " error clientService3 ";
    }
}
