package com.infosisarg.producer.mq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;

import com.infosisarg.api.PlatformMessage;
import com.infosisarg.api.Producer;

public class MqProducer implements Producer {

	public boolean send(PlatformMessage message) {
		try {
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61617");
			Destination destination = new ActiveMQTopic(message.getDestination());
			Connection connection;
			connection = connectionFactory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			try {
				Message msg = session.createTextMessage(message.getPayload());
				MessageProducer producer = session.createProducer(destination);
				producer.send(msg);
				session.close();
				return true;
			} finally {
				if (connection != null) {
					connection.close();
				}
			}
		} catch (JMSException e) {
			e.printStackTrace();
			return false;
		}
	}

}
