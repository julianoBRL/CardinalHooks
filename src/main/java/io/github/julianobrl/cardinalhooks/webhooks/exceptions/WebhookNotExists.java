package io.github.julianobrl.cardinalhooks.webhooks.exceptions;

public class WebhookNotExists extends Exception {
	private static final long serialVersionUID = 1L;
	public WebhookNotExists(String msg) {
		super(msg);
	}

}
