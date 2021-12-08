package io.github.julianobrl.webhooks.events;

import java.util.EventListener;
import java.util.EventObject;

import javax.swing.event.EventListenerList;

public class Cevent {
	
	 protected static EventListenerList listenerList = new EventListenerList();
	 
	 @SuppressWarnings("serial")
	public class CeventObject extends EventObject {
	 	  public CeventObject(Object source) {
	 		    super(source);
	 	  }
	 }
	 
	 public interface CeventListener extends EventListener {
		  public void EventOccurred(CeventObject evt);
	 }

	 public static void addListener(CeventListener listener) {
	    listenerList.add(CeventListener.class, listener);
	 }
	  
	 public static void removeListener(CeventListener listener) {
	    listenerList.remove(CeventListener.class, listener);
	 }
	  
	 protected void fireCevent(CeventObject evt) {
	    Object[] listeners = listenerList.getListenerList();
	    for (int i = 0; i < listeners.length; i = i+2) {
	      if (listeners[i] == CeventListener.class) {
	        ((CeventListener) listeners[i+1]).EventOccurred(evt);
	      }
	    }
	 }
	  
}

