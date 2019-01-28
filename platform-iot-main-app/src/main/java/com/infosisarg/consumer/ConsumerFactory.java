package com.infosisarg.consumer;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.infosisarg.PluginManagerHolder;
import com.infosisarg.api.Consumer;

public class ConsumerFactory {
	
	private static final Logger logger = LoggerFactory.getLogger(com.infosisarg.consumer.ConsumerFactory.class);

	public static Consumer getConsumer() {
		logger.debug("Creando consumer topico *");
		List<String> topics = new ArrayList<String>();
		topics.add("*");
		PluginManagerHolder manager = PluginManagerHolder.getInstance();
		return manager.getBroker().getConsumer(topics);
	}
	
	public static Consumer getConsumer(List<String> topics) {
		logger.debug("Creando consumer con topicos: " + topics.toArray());
		PluginManagerHolder manager = PluginManagerHolder.getInstance();
		return manager.getBroker().getConsumer(topics);
	}
	
}
