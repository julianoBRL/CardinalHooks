package io.github.julianobrl.webhooks.events;

import io.github.julianobrl.webhooks.webhooks.HookDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventLinker {
	private HookDTO hook;
	private Object event;
}
