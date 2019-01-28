package com.infosisarg.api;

public interface Consumer {
	
	public void addConsumer(PluginInterface plugin);
	
	public void removeConsumer();
	
	public void stop();
	
	

}