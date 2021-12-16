package io.github.julianobrl.cardinalhooks.webhooks.intercptors;

import io.github.julianobrl.cardinalhooks.webhooks.WebHook;

public interface Winterceptor {
	
	void onRegister(WebHook webhook);
	void onUnregister(String id,String event);
	void onCallerSuccess(WebHook webhook);
	void onCallerFail(WebHook webhook);
	void onEventTriggered(String eventname, Object evt);
	void onSendingMensage(WebHook webhook, Object msg);
	
}
