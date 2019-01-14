package com.infosisarg.consumer;

import java.util.ArrayList;
import java.util.List;

import com.infosisarg.PluginManagerHolder;
import com.infosisarg.api.Consumer;

public class ConsumerFactory {

	public static Consumer getConsumer() {
		List<String> topics = new ArrayList<String>();
		topics.add("*");
		PluginManagerHolder manager = PluginManagerHolder.getInstance();
		return manager.getBroker().getConsumer(topics);
	}
	
	public static Consumer getConsumer(List<String> topics) {
		PluginManagerHolder manager = PluginManagerHolder.getInstance();
		return manager.getBroker().getConsumer(topics);
	}
	
}
