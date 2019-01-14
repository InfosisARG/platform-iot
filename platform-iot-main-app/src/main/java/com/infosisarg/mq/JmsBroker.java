package com.infosisarg.mq;

import java.util.List;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.store.memory.MemoryPersistenceAdapter;

import com.infosisarg.Broker;
import com.infosisarg.api.Consumer;
import com.infosisarg.consumer.mq.MqConsumer;

public class JmsBroker implements Broker{

	private BrokerService broker;
	private final String NAME = "JMS"; 

	public JmsBroker() {
		try {
			broker = new BrokerService();
			broker.setPersistenceAdapter(new MemoryPersistenceAdapter());
			broker.setDataDirectory("target/activemq-data");
			broker.addConnector("tcp://localhost:61616");
			broker.start();
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
}