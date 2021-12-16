package io.github.julianobrl.cardinalhooks.webhooks.interceptors;

import java.util.EventListener;

import io.github.julianobrl.cardinalhooks.webhooks.WebHook;

/**
* 
* This interface can be used to receive objects and information when this event occurs.
* 
*/
public interface Winterceptor extends EventListener {
	
	/**
	* 
	* This method is called when a Webhook is trying to register.
	*
	* @param  evt  The Webhook trying register.
	* 
	*/
	void onRegister(WebHook evt);
	
	/**
	* 
	* This method is called when a webhook is going to be unregistered.
	*
	* @param  id  Webhook id to remove.
	* @param  event Event to remove the webhook
	* 
	*/
	void onUnregister(String id,String event);
	
	/**
	* 
	* This method is called when an event is fired.
	*
	* @param  eventname Name of the event that was fired.
	* @param  evt Object the event is broadcasting.
	* 
	*/
	void onEventTriggered(String eventname, Object evt);
	
	/**
	* 
	* This method is called when an event is fired.
	*
	* @param  webhook Webhook being called.
	* @param  msg Object the webhook will recive.
	* 
	*/
	void onSendingMensage(WebHook webhook,Object msg);
	
	/**
	* 
	* This method is called when a webhook has been called successfully returning 200
	*
	* @param  webhook Webhook that was called successfully
	* 
	*/
	void onCallerSuccess(WebHook webhook);
	
	/**
	* 
	* This method is called when a webhook was called and failed returning other than 200
	*
	* @param  webhook Webhook that was called and failed
	* 
	*/
	void onCallerFail(WebHook webhook);
	
}
