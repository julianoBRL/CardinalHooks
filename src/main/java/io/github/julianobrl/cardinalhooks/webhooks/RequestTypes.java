package io.github.julianobrl.cardinalhooks.webhooks;

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