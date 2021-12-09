package io.github.julianobrl.webhooks;

import java.util.Arrays;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

class HookCaller {

    public static boolean call(WebHook client,Object... data) throws RestClientException {
    	
    	RequestTypes type = client.getRequestType();
    	
    	 HttpHeaders headers = new HttpHeaders();
         headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
         
         HttpEntity<Object> entity = new HttpEntity<Object>(data,headers);         
         RestTemplate template = new RestTemplate();
         
         ResponseEntity<String> response = template.exchange(
        		 client.getEndpoint(),
        		 	(type == RequestTypes.GET)? HttpMethod.GET :
        		    (type == RequestTypes.POST)? HttpMethod.POST :
        		    (type == RequestTypes.PUT)? HttpMethod.PUT :
        		    (type == RequestTypes.DELETE)? HttpMethod.DELETE :
        		    	HttpMethod.POST
        		 , entity, String.class);
         
         return response.getStatusCodeValue() == 200;
         
    }

}
