package io.github.julianobrl.cardinalhooks.webhooks;

import org.springframework.context.annotation.Conditional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.julianobrl.cardinalhooks.annotation.EnableWebhooksConditional;
import io.github.julianobrl.cardinalhooks.webhooks.interceptors.InterceptorRegister;

@RestController
@RequestMapping("/webhooks")
class HooksController{
	
	@PostMapping
	@Conditional(EnableWebhooksConditional.class)
	public ResponseEntity<?> onRegister(@RequestBody WebHook data) {
			InterceptorRegister.fireRegister(data);
			return WebhookManager.registerHook(data);
	}
	
	@DeleteMapping
	@Conditional(EnableWebhooksConditional.class)
	public ResponseEntity<String> onUnregister(@RequestParam(name = "id") String id,@RequestParam(name = "event") String event) {
			InterceptorRegister.fireUnregister(event,id);
			return WebhookManager.unregisterHook(event,id);
	}

}
