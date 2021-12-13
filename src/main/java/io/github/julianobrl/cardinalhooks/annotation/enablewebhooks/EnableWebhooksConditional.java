package io.github.julianobrl.cardinalhooks.annotation.enablewebhooks;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class EnableWebhooksConditional implements Condition {
	
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {    	
       return EnableWebhooksEnabler.getInstance().isWebhooksEnabled();
    }
    
}
