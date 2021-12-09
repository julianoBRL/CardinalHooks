package io.github.julianobrl.events;

import java.util.Map;

import javax.swing.event.EventListenerList;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Event {
	
	private Class<?> eventType;
	private Map<Integer,Listener> listeners;
	private EventListenerList listenerList;
	
}
