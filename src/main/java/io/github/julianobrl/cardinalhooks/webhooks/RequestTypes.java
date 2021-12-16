package io.github.julianobrl.cardinalhooks.webhooks;

/**
* 
* Types of calls this library can make to a registered webhook.
* <p>
* Types of calls that a webhook can register.
* 
*/
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