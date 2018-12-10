package com.infosisarg.consumer;

import com.infosisarg.api.Consumer;
import com.infosisarg.consumer.mq.MqConsumer;

public class ConsumerFactory {

	public static Consumer getConsumer() {
		return new MqConsumer();
	}
}
