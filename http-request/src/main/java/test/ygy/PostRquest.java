package test.ygy;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

/**
 * Created by guoyao on 2017/8/19.
 */
public class PostRquest {

    public static void main(String[] args) throws Exception {

        HttpResponse<String> jsonResponse = Unirest.post("http://localhost:8006/refresh")
                .asString();
        System.out.println(jsonResponse.getBody());
    }
}
