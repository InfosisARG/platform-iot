package com.infosisarg.producer;

import com.infosisarg.api.Producer;
import com.infosisarg.producer.mq.MqProducer;

public class ProducerFactory {

	public static Producer getProducer() {
		Producer result = new MqProducer();
		return result;
	}

}
