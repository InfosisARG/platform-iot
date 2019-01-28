package com.infosisarg.api;

import java.util.Iterator;
import java.util.List;

import org.pf4j.Plugin;
import org.pf4j.PluginException;
import org.pf4j.PluginManager;
import org.pf4j.PluginWrapper;

import com.infosisarg.api.Broker;

public class PlatformPlugin extends Plugin {
	
	protected PluginManager manager;
	private Broker broker;

	public void setBroker(Broker broker) {
		this.broker = broker;
	}

	public PlatformPlugin(PluginWrapper wrapper) {
		super(wrapper);
		manager = wrapper.getPluginManager();
	}

	@Override
	public void start() throws PluginException {
		super.start();
		//manager.getExtensions(PluginInterface.class, this.getWrapper().getPluginId()).forEach(extension -> init(extension));
	}

	private void init(PluginInterface extension) {
		PlatformPluginDescriptor descriptor = (PlatformPluginDescriptor)this.wrapper.getDescriptor(); 
		extension.setProducer(broker.getProducer());
		extension.setSendTopic(descriptor.getSendTopic());
		extension.setReceiveTopics(descriptor.getReceiveTopics());
		Consumer consumer = broker.getConsumer(descriptor.getReceiveTopics());
		extension.setProducer(broker.getProducer());
		extension.setConsumer(consumer);
	}

	@Override
	public void stop() throws PluginException {
		List<PluginInterface> list = manager.getExtensions(PluginInterface.class, this.getWrapper().getPluginId());
		for (Iterator<PluginInterface> iterator = list.iterator(); iterator.hasNext();) {
			PluginInterface extension = iterator.next();
			extension.getConsumer().stop();
		}
		//manager.getExtensions(PluginInterface.class, this.getWrapper().getPluginId()).forEach(extension -> extension.getConsumer().stop());
		super.stop();		
	}

	public void init(Broker broker) {
		this.broker = broker;
		List<PluginInterface> list = manager.getExtensions(PluginInterface.class, this.getWrapper().getPluginId());
		for (Iterator<PluginInterface> iterator = list.iterator(); iterator.hasNext();) {
			PluginInterface extension = iterator.next();
			this.init(extension);
		}
		
	}

}
