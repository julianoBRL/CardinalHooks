package io.github.julianobrl.cardinalhooks.annotation.enablewebhooks;

import lombok.Getter;
import lombok.Setter;

public class EnableWebhooksEnabler {

	private static EnableWebhooksEnabler instance;
	
	@Getter
	@Setter
	private boolean webhooksEnabled = false;
	
	public static EnableWebhooksEnabler getInstance() {
		if(instance == null)
			instance = new EnableWebhooksEnabler();
		return instance;
	}
	
}
