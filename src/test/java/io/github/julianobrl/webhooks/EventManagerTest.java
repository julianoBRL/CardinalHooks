package io.github.julianobrl.webhooks;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.InvalidClassException;

import javax.management.ListenerNotFoundException;

import org.junit.jupiter.api.Test;

import io.github.julianobrl.events.EventManager;
import io.github.julianobrl.events.Listener;
import io.github.julianobrl.events.exeptions.EventNameExists;
import io.github.julianobrl.events.exeptions.EventNotExists;

class EventManagerTest {
	
	boolean ok1 = false;
	boolean ok2 = false;

	@Test
	void registerAndCallEvent() throws EventNotExists, EventNameExists, InvalidClassException {
		
		EventManager.registerEvent("DataCreatedEvent", String.class);
		EventManager.registerListener("DataCreatedEvent", new Listener() {
			@Override
			public void EventOccurred(Object evt) {
				ok1 = true;
			}
		});
		
		EventManager.callEvent("DataCreatedEvent", "Called over EventManager");
		EventManager.unregisterEvent("DataCreatedEvent");
		
		assertTrue(ok1);
		
	}
	
	@Test
	void registerAndCallOtherEvent()  throws EventNotExists, EventNameExists, InvalidClassException {
		
		EventManager.registerEvent("DataCreatedEvent", String.class);
		EventManager.registerEvent("DataCreatedEvent2", String.class);
		
		EventManager.registerListener("DataCreatedEvent", new Listener() {
			@Override
			public void EventOccurred(Object evt) {
				ok2 = true;
			}
		});
		
		EventManager.callEvent("DataCreatedEvent2", "Called over EventManager");
		
		EventManager.unregisterEvent("DataCreatedEvent");
		EventManager.unregisterEvent("DataCreatedEvent2");
		
		assertFalse(ok2);
		
	}
	
	@Test
	void registerAndCallEventAfterDelete() throws EventNotExists, EventNameExists, InvalidClassException, ListenerNotFoundException {
		
		EventManager.registerEvent("DataCreatedEvent", String.class);
		
		int eventId = EventManager.registerListener("DataCreatedEvent", new Listener() {
			@Override
			public void EventOccurred(Object evt) {
				System.out.println(evt);
				System.out.println("DataCreatedEvent");
			}
		});
		
		EventManager.callEvent("DataCreatedEvent", "Called over EventManager");
		EventManager.unregisterListener("DataCreatedEvent", eventId);
		EventManager.callEvent("DataCreatedEvent", "Called over EventManager after delete");
		EventManager.unregisterEvent("DataCreatedEvent");
		
	}
	
	@Test
	void registerAndCallEventAfterDeleteEvent() throws EventNameExists, InvalidClassException, EventNotExists{
		
		EventManager.registerEvent("DataCreatedEvent", String.class);
		
		EventManager.registerListener("DataCreatedEvent", new Listener() {
			@Override
			public void EventOccurred(Object evt) {
				System.out.println(evt);
				System.out.println("DataCreatedEvent");
			}
		});
		
		EventManager.callEvent("DataCreatedEvent", "Called over EventManager");
		EventManager.unregisterEvent("DataCreatedEvent");
		
		try {
			EventManager.callEvent("DataCreatedEvent", "Called over EventManager after delete");
		}catch (EventNotExists e) {
			assertFalse(false);
		}
	}

	@Test
	void registerWithSameName() throws EventNotExists, EventNameExists {
		
		try {
			EventManager.registerEvent("DataChangeEvenet", String.class);
			EventManager.registerEvent("DataChangeEvenet", String.class);
			EventManager.unregisterEvent("DataChangeEvenet");
		}catch (EventNameExists e) {
			assertFalse(false);
		}
		
	}
	
	@Test
	void registerWithSameNameDiferentType() throws EventNotExists, EventNameExists {
		
		try {
			EventManager.registerEvent("DataChangeEvenet", String.class);
			EventManager.registerEvent("DataChangeEvenet", Integer.class);
			EventManager.unregisterEvent("DataChangeEvenet");
		}catch (EventNameExists e) {
			assertFalse(false);
		}
		
	}
	
}
