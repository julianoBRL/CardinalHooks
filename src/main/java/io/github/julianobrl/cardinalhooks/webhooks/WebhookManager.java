package io.github.julianobrl.cardinalhooks.webhooks;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.management.ListenerNotFoundException;

import io.github.julianobrl.cardinalhooks.webhooks.exceptions.WebhookNotExists;
import io.github.julianobrl.events.EventManager;
import io.github.julianobrl.events.Listener;
import io.github.julianobrl.events.exeptions.EventNotExists;
import lombok.Getter;

public class WebhookManager {
	
	@Getter
	private static Map<Integer,WebHook> webhookList = new HashMap<>();
	private static Logger logger = Logger.getLogger(WebhookManager.class.getName());
	
	public static int registerHook (WebHook hookInfo) throws EventNotExists {
		
		int index = EventManager.registerListener(hookInfo.getEventName(),
			new Listener() {
				@Override
				public void EventOccurred(Object evt) {
					HookCaller.call(hookInfo, evt);
				}
			}
		);
		
		webhookList.put(index,hookInfo);
		logger.info("New webhook <"+hookInfo.getRequestType()+","+hookInfo.getEndpoint()+"> registred for event <"+hookInfo.getEventName()+"> ");
		return index;
		
	}
	
	public static void unregisterHook (int hookId, String eventName) throws EventNotExists, WebhookNotExists {
		
		try {
			EventManager.unregisterListener(eventName, hookId);
			webhookList.remove(hookId);
			logger.info("Webhook with id <"+hookId+"> registred for event <"+eventName+"> ");
		}catch (ListenerNotFoundException | NullPointerException e) {
			throw new WebhookNotExists("Webhook with index <"+hookId+"> does not exist in event <"+eventName+">.");
		}
		
	}

	public static boolean webhookExists(int index) {
		return webhookList.containsKey(index);
	}
	
}
