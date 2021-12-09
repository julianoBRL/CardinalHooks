package io.github.julianobrl.events.exeptions;

public class EventNameExists extends Exception {
	public EventNameExists(String string) {
		super(string);
	}

	private static final long serialVersionUID = 1L;
}
