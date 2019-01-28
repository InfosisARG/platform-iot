package com.infosisarg.api;

import java.util.List;

public interface Broker {
	
	public String getName();
	
	public Consumer getConsumer(List<String> topic);
	
	public Producer getProducer();
	
}
