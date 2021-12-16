package io.github.julianobrl.cardinalhooks;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@Configuration
@ConfigurationProperties(prefix = "cardinalhooks")
public class CardinalHooksConfig {

	private Proxy proxy = new Proxy();
	
	@Getter
	@Setter
	private int callRetryCount = 0;
	
	@Getter
	@Setter
	private boolean removeAfterRetry = false;
	
	@Getter
	@Setter
	public class Proxy {
		
		private boolean enabled = false;
		private String address;
		private int port;
		private Auth auth = new Auth();
		
	}
	
	@Getter
	@Setter
	public class Auth {
		
		private boolean enabled = false;
		private String username;
		private String password;
		
	}
	
}



