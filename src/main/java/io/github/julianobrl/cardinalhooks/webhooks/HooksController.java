package io.github.julianobrl.cardinalhooks.webhooks;

import org.springframework.context.annotation.Conditional;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.julianobrl.cardinalhooks.annotation.enablewebhooks.EnableWebhooksConditional;
import io.github.julianobrl.cardinalhooks.webhooks.exceptions.WebhookNotExists;
import io.github.julianobrl.cardinalhooks.webhooks.intercptors.InterceptorRegister;
import io.github.julianobrl.cardinalhooks.webhooks.intercptors.Winterceptor;
import io.github.julianobrl.events.EventManager;
import io.github.julianobrl.events.exeptions.EventNotExists;

@RestController
@RequestMapping("/webhooks")
class HooksController extends InterceptorRegister implements Winterceptor{
	
	@Override
	@PostMapping
	@Conditional(EnableWebhooksConditional.class)
	public ResponseEntity<?> onRegister(@RequestBody WebHook data) throws EventNotExists {
		if(EventManager.eventExists(data.getEventName())) {
			InterceptorRegister.fireRegister(data);
			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body("{ \"status\": \"Done\"}");
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
				.body("{ \"status\": \"Not Found\", \"details\": \"Event with the name <"+data.getEventName()+
						"> does not appear in our records. \"}");
		}
	}
	
	@Override
	@DeleteMapping
	@Conditional(EnableWebhooksConditional.class)
	public ResponseEntity<String> onUnregister(@RequestParam(name = "id") int id) throws EventNotExists, WebhookNotExists {
		if(WebhookManager.webhookExists(id)) {
			InterceptorRegister.fireUnregister(id);
			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body("{ \"status\": \"Done\"}");
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
				.body("{ \"status\": \"Not Found\", \"details\": \"Webhook with the id <"+id+
						"> does not appear in our records. \"}");
		}
	}

}
