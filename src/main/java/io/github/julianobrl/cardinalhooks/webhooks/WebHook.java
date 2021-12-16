package io.github.julianobrl.cardinalhooks.webhooks;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
* 
* Object referring to the webhook log and which is used to 
* receive the log data through the POST endpoint: /webhooks
* 
*/
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebHook {
	
	@JsonIgnore
	private UUID id;
	private String endpoint;
	private RequestTypes requestType;
	private String eventName;
	
}
