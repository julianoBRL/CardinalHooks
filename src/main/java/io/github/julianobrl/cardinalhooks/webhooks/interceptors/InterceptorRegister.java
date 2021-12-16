package io.github.julianobrl.cardinalhooks.webhooks.interceptors;

import javax.swing.event.EventListenerList;

import io.github.julianobrl.cardinalhooks.webhooks.WebHook;

public class InterceptorRegister {
	
	private static EventListenerList listenerList = new EventListenerList();
	
	public static void registerInterceptor(Winterceptor interceptor) {
		listenerList.add(Winterceptor.class,interceptor);
	}
	
	public static void fireRegister(WebHook data) {
		
		Object[] listeners = listenerList.getListenerList();
	    for (int i = 0; i < listeners.length; i = i+2) {
	      if (listeners[i] == Winterceptor.class) {
	        ((Winterceptor) listeners[i+1]).onRegister(data);
	      }
	    }
	    
	}
	
	public static void fireUnregister(String event, String Webhook_id) {
		
		Object[] listeners = listenerList.getListenerList();
	    for (int i = 0; i < listeners.length; i = i+2) {
	      if (listeners[i] == Winterceptor.class) {
	        ((Winterceptor) listeners[i+1]).onUnregister(Webhook_id,event);
	      }
	    }
	    
	}

	public static void fireOnCallerSuccess(WebHook data) {
		
		Object[] listeners = listenerList.getListenerList();
	    for (int i = 0; i < listeners.length; i = i+2) {
	      if (listeners[i] == Winterceptor.class) {
	        ((Winterceptor) listeners[i+1]).onCallerSuccess(data);
	      }
	    }
	    
	}

	public static void fireOnCallerFaill(WebHook data) {

		Object[] listeners = listenerList.getListenerList();
	    for (int i = 0; i < listeners.length; i = i+2) {
	      if (listeners[i] == Winterceptor.class) {
	        ((Winterceptor) listeners[i+1]).onCallerFail(data);
	      }
	    }
	    
	}

	public static void fireOnEventTriggered(String eventName, Object evt) {
		
		Object[] listeners = listenerList.getListenerList();
	    for (int i = 0; i < listeners.length; i = i+2) {
	      if (listeners[i] == Winterceptor.class) {
	        ((Winterceptor) listeners[i+1]).onEventTriggered(eventName,evt);
	      }
	    }
	    
	}

	public static void fireOnSendingMensage(WebHook webHook, Object evt) {
		
		Object[] listeners = listenerList.getListenerList();
	    for (int i = 0; i < listeners.length; i = i+2) {
	      if (listeners[i] == Winterceptor.class) {
	        ((Winterceptor) listeners[i+1]).onSendingMensage(webHook,evt);
	      }
	    }
	    
	}

}