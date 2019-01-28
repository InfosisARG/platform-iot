package com.infosisarg.mq;

import java.util.List;

import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.infosisarg.PluginManagerInitializer;
import com.infosisarg.api.Broker;
import com.infosisarg.api.Consumer;
import com.infosisarg.api.Producer;
import com.infosisarg.consumer.mq.MqConsumer;
import com.infosisarg.producer.mq.MqProducer;

public class JmsBroker implements Broker{

	private BrokerService broker;
	private final String NAME = "JMS";
	
	private static final Logger logger = LoggerFactory.getLogger(com.infosisarg.mq.JmsBroker.class);

	public JmsBroker() {
		try {
			broker = BrokerFactory.createBroker("xbean:activeMqServer.xml");
			//broker.setPersistenceAdapter(new MemoryPersistenceAdapter());
			//broker.setDataDirectory("target/activemq-data");
			//broker.addConnector("tcp://localhost:61616");
			broker.start();
			logger.debug("Broker " + broker.getBrokerName() + " creado.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public BrokerService getBroker() {
		return broker;
	}

	@Override
	public String getName() {
		return this.NAME;
	}

	@Override
	public Consumer getConsumer(List<String> filter) {
		return new MqConsumer(filter);
	}

	@Override
	public Producer getProducer() {
		Producer result = new MqProducer();
		return result;
	}
}