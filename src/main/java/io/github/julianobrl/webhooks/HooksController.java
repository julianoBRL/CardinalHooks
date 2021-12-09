package io.github.julianobrl.webhooks;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import io.github.julianobrl.events.EventManager;
import io.github.julianobrl.events.exeptions.EventNotExists;
import io.github.julianobrl.webhooks.exceptions.WebhookNotExists;

public interface HooksController {
	
	@PostMapping
	default ResponseEntity<?> register(@RequestBody WebHook data) throws EventNotExists {
		
		if(EventManager.eventExists(data.getEventName())) {
			int id = WebhookManager.registerHook(data);
			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body("{ \"status\": \"Done\", \"id\": \""+id+"\"}");
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
				.body("{ \"status\": \"Not Found\", \"details\": \"Event with the name <"+data.getEventName()+
						"> does not appear in our records. \"}");
		}
		
	}
	
	@DeleteMapping
	default ResponseEntity<?> unregister(@RequestParam(name = "id") int id) throws EventNotExists, WebhookNotExists {
		
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
