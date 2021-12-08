package io.github.julianobrl.webhooks.webhooks;

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
public class HookDTO {
	
	private long id;
	private String endpoint;
	private RequestTypes requestType;
	
	public enum RequestTypes {
		GET("get"),
		POST("post"),
		PUT("put"),
		DELETE("delete");
		
		String descricao;
		
		RequestTypes(String descricao){
			this.descricao = descricao;
		}
		
		@Override
		public String toString() {
			return descricao.toUpperCase();
		}
		
	}	
	/*public enum HookType {
		
		EXTRACTION_DONE("extration_done");
		
		String descricao;
		
		HookType(String descricao){
			this.descricao = descricao;
		}
		
		@Override
		public String toString() {
			return descricao.toUpperCase();
		}
	}*/
	
}
