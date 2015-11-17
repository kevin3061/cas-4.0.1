package tv.acfun.util;


import com.google.gson.Gson;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.IOException;
import java.util.Map;

/**
 * Created by user on 15/11/2.
 */
public class Test {
    public static void main(String[] args) throws IOException {
        HttpClient httpClient = new HttpClient();
        String reqUrl = "http://10.3.0.112:8088/oauth2-prueba/oauth/token?username=myuser&password=mypassword&client_id=mysupplycompany&client_secret=mycompanykey&grant_type=password&scope=read";
        GetMethod getMethod = new GetMethod(reqUrl);
        int statusCode = httpClient.executeMethod(getMethod);
        if(statusCode == HttpStatus.SC_OK){
            byte[] responseBody = getMethod.getResponseBody();
            getMethod.releaseConnection();
            String responseStr = new String(responseBody);
            System.out.println("oauth server response:"+responseStr);
            Gson gson = new Gson();
            Map<String,String> dataMap = gson.fromJson(responseStr,Map.class);
            System.out.println(dataMap.get("access_token"));
        }
    }
}
