package com.infosisarg.mq;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.store.memory.MemoryPersistenceAdapter;

public class JmsBroker {

	private BrokerService broker;

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
}