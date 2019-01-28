package com.infosisarg.mbean;

import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.jms.TextMessage;

import com.infosisarg.api.Consumer;
import com.infosisarg.consumer.ConsumerFactory;
import com.infosisarg.consumer.mq.MqConsumer;

@ManagedBean(name = "MQBean", eager = true)
@ApplicationScoped
public class MQBean {
	Consumer consumer;

	public MQBean() {
		this.consumer = ConsumerFactory.getConsumer();
	}

	public List<TextMessage> getMessages() {
		return ((MqConsumer) consumer).getMessages();
	}

	public String msToTime(Long duration) {
		Long seconds = (duration / 1000) % 60;
		Long minutes = (duration / (1000 * 60)) % 60;
		Long hours = (duration / (1000 * 60 * 60)) % 24;

		return String.format("%02d", hours - 3) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds);
	}
}