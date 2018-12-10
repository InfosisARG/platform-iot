package com.infosisarg.api;

import org.pf4j.ExtensionPoint;

public abstract class PluginInterface implements ExtensionPoint {
	private Producer producer;
	private String name;
	
	public PluginInterface(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setProducer(Producer p) {
		this.producer = p;
	}

	public boolean send(String topic, String message) {
		return producer.send(topic, message);
	}

	/**
	 * Return name of topic to subscribe
	 * 
	 * @return String
	 */
	public String register() {
		return null;
	}

	/**
	 * Method to do on listener of MessageListener
	 * 
	 * @param message
	 * @param obj
	 */
	public void receive(Object message) {
		// do nothing
	}

}
