package com.infosisarg;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.pf4j.PluginManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.infosisarg.consumer.ConsumerFactory;
import com.infosisarg.exceptions.BrokerNotDefinedException;
import com.infosisarg.mq.JmsBroker;

public class PluginManagerInitializer implements ServletContextListener {
	private static final Logger logger = LoggerFactory.getLogger(PluginManagerInitializer.class);
	private static final String CONST_CONFIG_PLUGINSDIR = "pf4j.pluginsDir";
	private static final String CONST_CONFIG_BROKER = "platform.broker";

	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		logger.debug("contextDestroyed start.");
		logger.debug("get plugin manager");
		final PluginManager pluginManager = PluginManagerHolder.getInstance().getPluginManager();

		logger.info("Stopping plugins...");
		pluginManager.stopPlugins();
		logger.info("Plugins stopped.");
		logger.debug("contextDestroyed end.");
	}

	public void contextInitialized(ServletContextEvent servletContextEvent) {
		logger.info("Initializing broker.");
		Broker broker;
		try {
			broker = initializeBroker(servletContextEvent);
			logger.info("Initializing plugin manager.");
			PluginManagerHolder manager = PluginManagerHolder.getInstance();
			manager.init(getPluginsHome(servletContextEvent), broker);
			logger.info("Plugin Manager initialized.");
			manager.getPluginManager().loadPlugins();
			logger.debug("Plugins loaded.");
			manager.getPluginManager().startPlugins();
			logger.debug("Plugins started.");
			logger.debug("contextInitialized end.");

			logger.info("***********************************************");
			logger.info("Plugins cargados: " + manager.getPluginManager().getPlugins().size());
			manager.getPluginManager().getPlugins()
					.forEach(c -> logger.info("-> " + c.getClass().getName() + " : \"" + c.getPluginId() + "\""));
			logger.info("***********************************************");

			manager.initPlugins();

		} catch (BrokerNotDefinedException e) {
			logger.error(e.getMessage());
		}

		// ConsumerFactory.getConsumer();
	}

	private Broker initializeBroker(ServletContextEvent servletContextEvent) throws BrokerNotDefinedException {
		ServletContext context = servletContextEvent.getServletContext();
		String brokerProperty = "";
		Broker broker = null;
		if (context != null) {
			brokerProperty = context.getInitParameter(CONST_CONFIG_BROKER);
		}
		switch (brokerProperty) {
		case "JMS":
			broker = new JmsBroker();
			break;
		default:
			throw new BrokerNotDefinedException("Please set platform.broker variable.");
		}
		return broker;
	}

	private String getPluginsHome(ServletContextEvent servletContextEvent) {
		ServletContext context = servletContextEvent.getServletContext();
		if (context != null) {
			return context.getInitParameter(CONST_CONFIG_PLUGINSDIR);
		}
		return null;
	}

}
