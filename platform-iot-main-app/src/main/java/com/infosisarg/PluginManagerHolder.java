package com.infosisarg;

import java.io.File;

import org.pf4j.CompoundPluginDescriptorFinder;
import org.pf4j.DefaultPluginManager;
import org.pf4j.ExtensionFactory;
import org.pf4j.ManifestPluginDescriptorFinder;
import org.pf4j.PluginDescriptorFinder;
import org.pf4j.PluginManager;
import org.pf4j.PluginWrapper;
import org.pf4j.SingletonExtensionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.infosisarg.api.PluginInterface;
import com.infosisarg.consumer.mq.MqConsumer;
import com.infosisarg.producer.ProducerFactory;

public class PluginManagerHolder {
	private PluginManager pluginManager = null;
	private final Logger logger = LoggerFactory.getLogger(PluginManagerHolder.class);
	private Broker broker;
	
	private static PluginManagerHolder instance;
	
	private PluginManagerHolder() {
	}
	
	public static PluginManagerHolder getInstance() {
		if (null == instance) {
			instance = new PluginManagerHolder();
		}
		return instance;
	}

	public void init(String pluginsHome, Broker broker) {
		this.broker = broker;
		if (pluginsHome != null) {
			pluginManager = new DefaultPluginManager(new File(pluginsHome).toPath()) {
				@Override
				protected ExtensionFactory createExtensionFactory() {
					return new SingletonExtensionFactory();
				}
				
				@Override
			    protected PluginDescriptorFinder createPluginDescriptorFinder() {
			        return new CompoundPluginDescriptorFinder()
			            .add(new PlatformPropertiesPluginDescriptorFinder())
			            .add(new ManifestPluginDescriptorFinder());
			    }
			};
		} else {
			pluginManager = new DefaultPluginManager() {
				@Override
				protected ExtensionFactory createExtensionFactory() {
					return new SingletonExtensionFactory();
				}
				
				@Override
			    protected PluginDescriptorFinder createPluginDescriptorFinder() {
			        return new CompoundPluginDescriptorFinder()
			            .add(new PlatformPropertiesPluginDescriptorFinder())
			            .add(new ManifestPluginDescriptorFinder());
			    }
			};
		}

	}

	public PluginManager getPluginManager() {
		if (pluginManager == null) {
			logger.error("pf4j-web: you must add the PluginManagerInitializer in web.xml.");
			throw new RuntimeException("You must add the PluginManagerInitializer in web.xml.");
		}
		return pluginManager;
	}

	public void initPlugins() {
		//pluginManager.getExtensions(PluginInterface.class).forEach(extension -> {init(extension)});
		pluginManager.getPlugins().forEach(plugin -> initPlugin(plugin.getPluginId()));
	}

	public void initPlugin(String pluginId) {
		PluginWrapper p = pluginManager.getPlugin(pluginId);
		pluginManager.getExtensions(PluginInterface.class, pluginId).forEach(extension -> init(extension,(PlatformPluginDescriptor)p.getDescriptor()));
	}

	private void init(PluginInterface extension, PlatformPluginDescriptor descriptor) {
		extension.setProducer(ProducerFactory.getProducer());
		extension.setSendTopic(descriptor.getSendTopic());
		extension.setReceiveTopics(descriptor.getReceiveTopics());

		if (extension.register() != null) {
			new MqConsumer().addConsumer(extension);
		}

	}

	public Broker getBroker() {
		return broker;
	}

	public void setBroker(Broker broker) {
		this.broker = broker;
	}

}
