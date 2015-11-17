package tv.acfun.adaptors;

import com.google.gson.Gson;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.jasig.cas.authentication.HandlerResult;
import org.jasig.cas.authentication.PreventedException;
import org.jasig.cas.authentication.UsernamePasswordCredential;
import org.jasig.cas.authentication.handler.support.AbstractUsernamePasswordAuthenticationHandler;
import org.jasig.cas.authentication.principal.SimplePrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import tv.acfun.im.cas.user.service.AcUserService;

import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.FailedLoginException;
import java.security.GeneralSecurityException;
import java.util.Map;

/**
 * Created by user on 15/10/28.
 */

public class QueryDubboServiceAuthenticationHandler extends AbstractUsernamePasswordAuthenticationHandler {

    @Autowired
    private AcUserService acUserService;


    /** {@inheritDoc} */
    @Override
    protected final HandlerResult authenticateUsernamePasswordInternal(final UsernamePasswordCredential credential)
            throws GeneralSecurityException, PreventedException {

        //String username = "admin";
        //String password="444AD18E8DE67810";

        final String username = credential.getUsername();
        //String pwd = acUserService.findAcUserPwByUserName(username);
        String password = acUserService.findAdUserPwByUserName(username);
        System.out.println("--------------------------------------------------");
        System.out.println("dubbo service return password:"+password);

        if (password == null) {
            logger.debug("{} was not found in the database.", username);
            throw new AccountNotFoundException(username + " not found in database.");
        }

        final String encodedPassword = this.getPasswordEncoder().encode(credential.getPassword());
        if (!password.equals(encodedPassword)) {
            throw new FailedLoginException();
        }

        System.out.println("cas login success.............");
        //从oauth处获取token
//        org.apache.commons.httpclient.HttpClient httpClient = new org.apache.commons.httpclient.HttpClient();
//        String reqUrl = "http://10.3.0.112:8088/oauth2-prueba/oauth/token?username=myuser&password=mypassword&client_id=mysupplycompany&client_secret=mycompanykey&grant_type=password&scope=read";
//        GetMethod getMethod = new GetMethod(reqUrl);
//        try{
//            int statusCode = httpClient.executeMethod(getMethod);
//            if(statusCode == HttpStatus.SC_OK){
//                byte[] responseBody = getMethod.getResponseBody();
//                getMethod.releaseConnection();
//                String responseStr = new String(responseBody);
//                System.out.println("oauth server response:"+responseStr);
//                Gson gson = new Gson();
//                Map<String,String> dataMap = gson.fromJson(responseStr,Map.class);
//                System.out.println(dataMap.get("access_token"));
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }


        return createHandlerResult(credential, new SimplePrincipal(username), null);
    }

}
