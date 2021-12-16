package io.github.julianobrl.cardinalhooks.webhooks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.julianobrl.cardinalhooks.webhooks.interceptors.InterceptorRegister;
import io.github.julianobrl.easyevents.EventManager;
import io.github.julianobrl.easyevents.Listener;
import io.github.julianobrl.easyevents.events.exeptions.EventNotExists;
import lombok.Getter;


/**
* 
* Class responsible for managing (registering, calling and unregistering) the webhooks
* 
*/
public class WebhookManager {

	private static boolean exists = false;
	
	private static Logger logger = Logger.getLogger(WebhookManager.class.getName());
	
	@Getter
	private static Map<String,List<WebHook>> registredHooks = new HashMap<String,List<WebHook>>();
	
	static ObjectMapper mapper = new ObjectMapper();
	
	/**
	* 
	* This method is used to register a webhook on a given event passed within its parameter.<br>
	* When the event that the webhook was registered is triggered, a thread is started where each 
	* webhook will be called in the sequence in which they were registered
	* 
	* @param  hookInfo  Webhook to register.
	* @return {@code ResponseEntity<String>} Response to be sent to the request.
	* 
	*/
	public static ResponseEntity<String> registerHook (WebHook hookInfo) {
		
		if(registredHooks.containsKey(hookInfo.getEventName())) {
			
			hookInfo.setId(UUID.randomUUID());
			registredHooks.get(hookInfo.getEventName()).add(hookInfo);
			logger.info("New WebHook registred(1)");
			
		}else {
			
			registredHooks.put(hookInfo.getEventName(), new ArrayList<WebHook>());
			hookInfo.setId(UUID.randomUUID());
			registredHooks.get(hookInfo.getEventName()).add(hookInfo);
			
			try {
				
				EventManager.registerListener(hookInfo.getEventName(),
					new Listener() {
						@Override
						public void EventOccurred(Object evt) {
							
							InterceptorRegister.fireOnEventTriggered(hookInfo.getEventName(),evt);
							
							logger.warning("Event -> "+hookInfo.getEventName()+" triggered");
							
							new Thread() {
								@Override
								public void run() {
									
									for (WebHook webHook : WebhookManager.getRegistredHooks().get(hookInfo.getEventName())) {
										
										try {logger.warning("Calling: "+mapper.writeValueAsString(webHook));
										} catch (JsonProcessingException e) {e.printStackTrace();}
										
										InterceptorRegister.fireOnSendingMensage(webHook,evt);
										
										try {HookCaller.call(webHook, evt);
										} catch (RestClientException | JsonProcessingException e) {e.printStackTrace();}
										
									}
								}
							}.start();
							
						}
					}
				);
				
			} catch (EventNotExists e) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event <"+hookInfo.getEventName()+"> not exists!");
			}
			
			logger.info("New WebHook registred(2)");
			
		}
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.contentType(MediaType.APPLICATION_JSON)
				.body("{\"status\":\"Done\",\"id\":\""+hookInfo.getId()+"\"}");
	}
	
	/**
	* 
	* This method is used to unregister a webhook from an event
	*
	* @param  event Event to remove the webhook
	* @param  hookId  Webhook id to remove.
	* @return {@code ResponseEntity<String>} Response to be sent to the request.
	* 
	*/
	public static ResponseEntity<String> unregisterHook (String event,String hookId) {
		
		try {
			registredHooks.get(event).removeIf((hook) -> {
				if(hook.getId().toString().equals(hookId))
					exists = true;
				return hook.getId().toString().equals(hookId);
			});
		}catch (NullPointerException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hook <"+hookId+"> not found on event "+event+"!");
		}
		
		if(exists) 
			return ResponseEntity
					.status(HttpStatus.OK)
					.contentType(MediaType.APPLICATION_JSON)
					.body("{\"status\":\"Deleted\",\"id\":\""+hookId+"\",\"event\":\""+event+"\"}");
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hook <"+hookId+"> not found on event "+event+"!");
		
	}
		
}
