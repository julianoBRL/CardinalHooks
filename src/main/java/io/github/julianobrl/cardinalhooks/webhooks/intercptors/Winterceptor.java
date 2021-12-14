package io.github.julianobrl.cardinalhooks.webhooks.intercptors;

import java.util.EventListener;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import io.github.julianobrl.cardinalhooks.webhooks.WebHook;
import io.github.julianobrl.cardinalhooks.webhooks.WebhookManager;
import io.github.julianobrl.cardinalhooks.webhooks.exceptions.WebhookNotExists;
import io.github.julianobrl.events.EventManager;
import io.github.julianobrl.events.exeptions.EventNotExists;

public interface Winterceptor extends EventListener {
	
	default ResponseEntity<?> onRegister(WebHook evt) throws EventNotExists {
		
		if(EventManager.eventExists(evt.getEventName())) {
			int id = WebhookManager.registerHook(evt);
			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body("{ \"status\": \"Done\", \"id\": \""+id+"\"}");
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
				.body("{ \"status\": \"Not Found\", \"details\": \"Event with the name <"+evt.getEventName()+
						"> does not appear in our records. \"}");
		}
		
	}
	
	
	default ResponseEntity<String> onUnregister(int id) throws EventNotExists, WebhookNotExists {
		if(WebhookManager.webhookExists(id)) {
			WebHook data = WebhookManager.getWebhookList().get(id);
			WebhookManager.unregisterHook(id, data.getEventName());
			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body("{ \"status\": \"Done\", \"id\": \""+id+"\"}");
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
				.body("{ \"status\": \"Not Found\", \"details\": \"Webhook with the id <"+id+
						"> does not appear in our records. \"}");
		}
	}
	
	 
}
