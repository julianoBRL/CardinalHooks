package io.github.julianobrl.cardinalhooks;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class CardinalHooksAutoConfig {

	@Bean
    ApplicationStartupSetup enableWebhooksStartup(ApplicationContext context) {
        return new ApplicationStartupSetup(context);
    }
	
	@Bean
	CardinalHooksConfig initConfigs() {
		return new CardinalHooksConfig();
	}
	
}
