package io.github.julianobrl.cardinalhooks;

import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import io.github.julianobrl.cardinalhooks.annotation.enablewebhooks.EnableWebhooks;
import io.github.julianobrl.cardinalhooks.annotation.enablewebhooks.EnableWebhooksEnabler;
import io.github.julianobrl.cardinalhooks.annotation.webhookinterceptor.WebhookInterceptor;
import io.github.julianobrl.cardinalhooks.webhooks.WebhookManager;
import io.github.julianobrl.cardinalhooks.webhooks.intercptors.InterceptorRegister;
import io.github.julianobrl.cardinalhooks.webhooks.intercptors.Winterceptor;

@Component
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class ApplicationStartupSetup implements
ApplicationListener<ContextRefreshedEvent> {
	
	@Autowired
	RequestMappingHandlerMapping requestMappingHandlerMapping;
	
	private ApplicationContext context;
	
	private static Logger logger = Logger.getLogger(WebhookManager.class.getName());

	public ApplicationStartupSetup(ApplicationContext context) {
		this.context = context;
		logger.info("CardinalHooks ApplicationStartupListener Initialized");
	}
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		
		initEnableWebhooks();
		try {
			initWebhooksInterceptors();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void initEnableWebhooks() {
		Optional<EnableWebhooks> annotation =
			      context.getBeansWithAnnotation(EnableWebhooks.class).keySet().stream()
			      .map(key -> context.findAnnotationOnBean(key, EnableWebhooks.class))
			      .findFirst();
			
			annotation.ifPresent(enableIstio -> {
				EnableWebhooksEnabler.getInstance().setWebhooksEnabled(true);
				logger.info("Webhooks enabled!!");
			});
	}
	
	private void initWebhooksInterceptors() throws ClassNotFoundException {
		
		Optional<WebhookInterceptor> annotation =
			      context.getBeansWithAnnotation(WebhookInterceptor.class).keySet().stream()
			      .map(key -> context.findAnnotationOnBean(key, WebhookInterceptor.class))
			      .findFirst();

		annotation.ifPresent(enableIstio -> {
			
			context.getBeansWithAnnotation(WebhookInterceptor.class).forEach((key,value) -> {
				InterceptorRegister.registerInterceptor((Winterceptor) value);
			});
			
			logger.info("WebhookInterceptor enabled!!");
			
		});
	}
	
	
}