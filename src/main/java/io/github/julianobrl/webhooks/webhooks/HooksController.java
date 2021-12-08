package io.github.julianobrl.webhooks.webhooks;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.julianobrl.webhooks.events.Cevent;
import io.github.julianobrl.webhooks.events.Cevent.CeventListener;
import io.github.julianobrl.webhooks.events.Cevent.CeventObject;
import io.github.julianobrl.webhooks.events.EventLinker;

public interface HooksController <Event extends Cevent, EventListener extends CeventListener> {
	
	List<EventLinker> registredEvents = new ArrayList<EventLinker>();
	ObjectMapper mapper = new ObjectMapper();
	
	@SuppressWarnings("unchecked")
	@PostMapping
	default ResponseEntity<?> register(@RequestBody HookDTO data) throws JsonProcessingException {
		EventListener event = (EventListener) new CeventListener() {
			
			@Override
			public void EventOccurred(CeventObject evt) {
				//log.info("Extraction done event occurred!! -> "+ evt.getSource().toString()+" sending for: "+mapper.writeValueAsString(data));
				HookCaller.call(data,data,evt);
			}
			
		};
		
		data.setId(registredEvents.size());
		registredEvents.add(new EventLinker(data,event));
		int index = registredEvents.size() - 1;
		
		Cevent.addListener(event);
		return ResponseEntity.ok("Registrado com sucesso no evento seu id: "+index);
	}
	
	@DeleteMapping
	default void unregister(@RequestParam(name = "id") int id) throws JsonProcessingException {
		Cevent.removeListener((CeventListener) registredEvents.get(id).getEvent());
		registredEvents.remove(id);
		//log.info("Removido event "+id+", total: "+ registredEvents.size());
	}
	
	@GetMapping
	default List<HookDTO> getRegistredEvents() {
		List<HookDTO> hooks = new ArrayList<HookDTO>();
		
		for (int i = 0; i<registredEvents.size();i++) {
			hooks.add(registredEvents.get(i).getHook());
		}
		
		return hooks;
	}

}
