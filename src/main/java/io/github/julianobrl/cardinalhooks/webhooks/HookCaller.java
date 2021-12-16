package io.github.julianobrl.cardinalhooks.webhooks;

import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.util.Arrays;
import java.util.logging.Logger;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.julianobrl.cardinalhooks.CardinalHooksConfig;
import io.github.julianobrl.cardinalhooks.CardinalHooksStaticConfig;
import io.github.julianobrl.cardinalhooks.webhooks.interceptors.InterceptorRegister;

class HookCaller {
	
	private static Logger logger = Logger.getLogger(HookCaller.class.getName());
	private static ObjectMapper mapper = new ObjectMapper();
	private static ResponseEntity<?> response = null;
	private static CardinalHooksConfig config = CardinalHooksStaticConfig.getInstance().getConfig();

    public static void call(WebHook client,Object data) throws RestClientException, JsonProcessingException {
    	
    	
    	 HttpHeaders headers = new HttpHeaders();
         headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
         
         HttpEntity<Object> objects = new HttpEntity<Object>(data,headers);
         
         if(config.getProxy().getAuth().isEnabled()) {
        	 Authenticator authenticator = new Authenticator() {
        	        public PasswordAuthentication getPasswordAuthentication() {
        	            return (new PasswordAuthentication(config.getProxy().getAuth().getUsername(),
        	            		config.getProxy().getAuth().getPassword().toCharArray()));
        	        }
        	 };
        	 Authenticator.setDefault(authenticator);
         }

         RestTemplate template = new RestTemplate();
         if(config.getProxy().isEnabled()) {

             Proxy proxy = new Proxy(
            		 Type.HTTP, 
            		 new InetSocketAddress( 
            				 	config.getProxy().getAddress(), 
            				 	config.getProxy().getPort()
            				 )
            		 );
             
             SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
             requestFactory.setProxy(proxy);
             template = new RestTemplate(requestFactory);
             
         }
         
         
        RequestTypes type = client.getRequestType();
        
        makeRequest(template, type, client, objects, config.getCallRetryCount(),config.getCallRetryCount());
         
    }
    
    private static void makeRequest(RestTemplate template,RequestTypes type,WebHook client, HttpEntity<Object> objects,int trys,int trysTotal) throws RestClientException, JsonProcessingException {
    	
    	try {
        	
        	response = template.exchange( 
               		 client.getEndpoint(),
               		 	(type == RequestTypes.GET)? HttpMethod.GET :
               		    (type == RequestTypes.POST)? HttpMethod.POST :
               		    (type == RequestTypes.PUT)? HttpMethod.PUT :
               		    (type == RequestTypes.DELETE)? HttpMethod.DELETE :
               		    	HttpMethod.POST
               		 , objects, Object.class);

            if(response.getStatusCodeValue() >= 200 || response.getStatusCodeValue() <= 299) {
            	logger.info("Successfuly called webhook <"+client.getEndpoint()+"> with response code "+response.getStatusCodeValue());
            	InterceptorRegister.fireOnCallerSuccess(client);
            	return;
            }
            
        }catch (Exception e) {
        	
        	int statusCode = -1;
        	if (e instanceof HttpClientErrorException) {
        		HttpClientErrorException error = (HttpClientErrorException) e;
        		statusCode = error.getRawStatusCode();
        	}
        	if (e instanceof HttpServerErrorException) {
        		HttpServerErrorException error = (HttpServerErrorException) e;
        		statusCode = error.getRawStatusCode();
        	}
        	
        	if(trys == 0 && config.isRemoveAfterRetry()) {
        		logger.info("Falied calling webhook <"+client.getEndpoint()+"> with response code "+statusCode);
        		logger.info("Unregister config set to true, unregistrering webhook: "+mapper.writeValueAsString(client));
        		//unregister n pode ser chamado por aqui, criar metodo para disparar ação
        		InterceptorRegister.fireOnCallerFaill(client);
        	}
			
        	if(trys > 0 && !config.isRemoveAfterRetry()) {
        		logger.info("Falied calling webhook <"+client.getEndpoint()+"> with response code "+statusCode+" attempts remaning "+trys+"/"+trysTotal);
        		makeRequest(template, type, client, objects, trys-1,trysTotal);
        	}else {
        		logger.info("Falied calling webhook <"+client.getEndpoint()+"> with response code "+statusCode);
            	InterceptorRegister.fireOnCallerFaill(client);  
        	}
        	
        	 
		}
    }
    
}
