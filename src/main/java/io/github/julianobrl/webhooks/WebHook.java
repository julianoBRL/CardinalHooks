package io.github.julianobrl.webhooks;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebHook {
	
	private String endpoint;
	private RequestTypes requestType;
	private String eventName;
}
