package com.infosisarg;

import java.io.File;

import org.pf4j.DefaultPluginManager;
import org.pf4j.ExtensionFactory;
import org.pf4j.PluginManager;
import org.pf4j.SingletonExtensionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.infosisarg.api.PluginInterface;
import com.infosisarg.consumer.mq.MqConsumer;
import com.infosisarg.producer.ProducerFactory;

public class PluginManagerHolder {
	private static PluginManager pluginManager = null;
	private static final Logger logger = LoggerFactory.getLogger(PluginManagerHolder.class);

	static void init(String pluginsHome) {
		if (pluginsHome != null) {
			pluginManager = new DefaultPluginManager(new File(pluginsHome).toPath()) {
				@Override
				protected ExtensionFactory createExtensionFactory() {
					return new SingletonExtensionFactory();
				}
			};
		} else {
			pluginManager = new DefaultPluginManager() {
				@Override
				protected ExtensionFactory createExtensionFactory() {
					return new SingletonExtensionFactory();
				}
			};
		}

	}

	public static PluginManager getPluginManager() {
		if (pluginManager == null) {
			logger.error("pf4j-web: you must add the PluginManagerInitializer in web.xml.");
			throw new RuntimeException("You must add the PluginManagerInitializer in web.xml.");
		}
		return pluginManager;
	}

	public static void initPlugins() {
		pluginManager.getExtensions(PluginInterface.class).forEach(plugin -> init(plugin));
	}

	public static void initPlugin(String pluginId) {
		pluginManager.getExtensions(PluginInterface.class, pluginId).forEach(plugin -> init(plugin));
	}

	private static void init(PluginInterface plugin) {
		plugin.setProducer(ProducerFactory.getProducer());

		if (plugin.register() != null) {
			new MqConsumer().addConsumer(plugin);
		}

	}

}
