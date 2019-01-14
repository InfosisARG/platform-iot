package com.infosisarg;

import java.util.List;

import com.infosisarg.api.Consumer;

public interface Broker {
	
	public String getName();
	
	public Consumer getConsumer(List<String> topic);
	
}
