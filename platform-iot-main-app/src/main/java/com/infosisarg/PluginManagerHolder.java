package com.infosisarg;

import java.io.File;
import java.util.Iterator;
import java.util.List;

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

import com.infosisarg.api.Broker;
import com.infosisarg.api.PlatformPlugin;
import com.infosisarg.api.PlatformPropertiesPluginDescriptorFinder;
import com.infosisarg.api.PluginInterface;

public class PluginManagerHolder {
	
	private final Logger logger = LoggerFactory.getLogger(com.infosisarg.PluginManagerHolder.class);

	private PluginManager pluginManager = null;
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

	/**
	public PluginManager getPluginManager() {
		if (pluginManager == null) {
			logger.error("pf4j-web: you must add the PluginManagerInitializer in web.xml.");
			throw new RuntimeException("You must add the PluginManagerInitializer in web.xml.");
		}
		return pluginManager;
	}
	**/

	public void initPlugins() {
		this.pluginManager.loadPlugins();
		logger.debug("Plugins loaded.");
	
		//pluginManager.getExtensions(PluginInterface.class).forEach(extension -> {init(extension)});
		
		this.pluginManager.startPlugins();
		pluginManager.getPlugins().forEach(plugin -> initPlugin(plugin));
		//pluginManager.getExtensions(PluginInterface.class).forEach(extension -> init(extension));
		logger.debug("Plugins started.");
		logger.debug("contextInitialized end.");
		
	}

	public void initPlugin(PluginWrapper plugin) {
		((PlatformPlugin)plugin.getPlugin()).init(broker);
	}
	
	public void initPlugin(String  pluginId) {
		PluginWrapper p = pluginManager.getPlugin(pluginId);
		initPlugin(p);
	}

	/**
	 * private void init(PluginInterface extension, PlatformPluginDescriptor descriptor) {
	
		extension.setProducer(ProducerFactory.getProducer());
		extension.setSendTopic(descriptor.getSendTopic());
		extension.setReceiveTopics(descriptor.getReceiveTopics());

		if (extension.register() != null) {
			new MqConsumer().addConsumer(extension);
		}
**/

	public Broker getBroker() {
		return broker;
	}

	public void setBroker(Broker broker) {
		this.broker = broker;
	}

	public PluginManager getPluginManager() {
		return pluginManager;
	}
	
	
	
	

}
