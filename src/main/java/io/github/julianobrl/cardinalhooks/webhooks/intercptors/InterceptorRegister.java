package io.github.julianobrl.cardinalhooks.webhooks.intercptors;

import javax.swing.event.EventListenerList;

import io.github.julianobrl.events.exeptions.EventNotExists;
import io.github.julianobrl.cardinalhooks.webhooks.WebHook;
import io.github.julianobrl.cardinalhooks.webhooks.exceptions.WebhookNotExists;

public class InterceptorRegister {
	
	private static EventListenerList listenerList = new EventListenerList();
	
	public static void registerInterceptor(Winterceptor interceptor) {
		listenerList.add(Winterceptor.class,interceptor);
	}
	
	protected static void fireRegister(WebHook data) throws EventNotExists {
		
		Object[] listeners = listenerList.getListenerList();
	    for (int i = 0; i < listeners.length; i = i+2) {
	      if (listeners[i] == Winterceptor.class) {
	        ((Winterceptor) listeners[i+1]).onRegister(data);
	      }
	    }
	    
	}
	
	protected static void fireUnregister(int Webhook_id) throws EventNotExists, WebhookNotExists {
		Object[] listeners = listenerList.getListenerList();
	    for (int i = 0; i < listeners.length; i = i+2) {
	      if (listeners[i] == Winterceptor.class) {
	        ((Winterceptor) listeners[i+1]).onUnregister(Webhook_id);
	      }
	    }
	}

}
