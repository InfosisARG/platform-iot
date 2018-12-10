package com.infosisarg;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.pf4j.PluginManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.infosisarg.consumer.ConsumerFactory;
import com.infosisarg.mq.JmsBroker;

public class PluginManagerInitializer implements ServletContextListener {
	private static final Logger logger = LoggerFactory.getLogger(PluginManagerInitializer.class);
	private static final String CONST_CONFIG_PARAM_NAME = "pf4j.pluginsDir";

	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		logger.debug("contextDestroyed start.");
		logger.debug("get plugin manager");
		final PluginManager pluginManager = PluginManagerHolder.getPluginManager();

		logger.info("Stopping plugins...");
		pluginManager.stopPlugins();
		logger.info("Plugins stopped.");
		logger.debug("contextDestroyed end.");
	}

	public void contextInitialized(ServletContextEvent servletContextEvent) {
		logger.debug("contextInitialized start.");
		logger.info("Initializing plugin manager.");
		PluginManagerHolder.init(getPluginsHome(servletContextEvent));
		logger.info("Plugin Manager initialized.");
		logger.debug("Plugin Manager initialized.");
		PluginManagerHolder.getPluginManager().loadPlugins();
		logger.debug("Plugins loaded.");
		PluginManagerHolder.getPluginManager().startPlugins();
		logger.debug("Plugins started.");
		logger.debug("contextInitialized end.");

		new JmsBroker();

		logger.info("***********************************************");
		logger.info("Plugins cargados: " + PluginManagerHolder.getPluginManager().getPlugins().size());
		PluginManagerHolder.getPluginManager().getPlugins().forEach(c -> logger.info("-> " + c.getClass().getName() + " : \"" + c.getPluginId() + "\""));
		logger.info("***********************************************");

		PluginManagerHolder.initPlugins();

		ConsumerFactory.getConsumer();
	}

	private String getPluginsHome(ServletContextEvent servletContextEvent) {
		ServletContext context = servletContextEvent.getServletContext();
		if (context != null) {
			return context.getInitParameter(CONST_CONFIG_PARAM_NAME);
		}
		return null;
	}

}
