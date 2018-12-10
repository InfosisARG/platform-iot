package com.infosisarg.api;

public interface Producer {
	
	public boolean send(String topic, String myMessage);

}
