package io.github.julianobrl.cardinalhooks;

import lombok.Getter;
import lombok.Setter;

public class CardinalHooksStaticConfig {

	private static CardinalHooksStaticConfig instance;
	
	@Getter
	@Setter
	private CardinalHooksConfig config;
	
	public static CardinalHooksStaticConfig getInstance() {
		if(instance == null)
			instance = new CardinalHooksStaticConfig();
		return instance;
	}
	
}
