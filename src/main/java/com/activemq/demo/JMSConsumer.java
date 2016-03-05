package com.activemq.demo;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 消息消费者
 * @author jackson
 *
 */
public class JMSConsumer {
	
	private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
	private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
	private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;
	private static ConnectionFactory connectionFactory;
	private static Connection connection;
	private static Session session;
	private static Destination destination;
	private static MessageConsumer messageConsumer;

	public static void main(String[] args) {
		createConsumer("myFirstMessageQueue");
		getMessage();
	}

	/**
	 * 创建消息消费者
	 * @param queueName
	 */
	private static void createConsumer(String queueName) {
		try {
			connectionFactory =  new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKEURL);
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue(queueName);
			messageConsumer = session.createConsumer(destination);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取消息
	 */
	private static void getMessage() {
		try {
			while (true) {
				TextMessage msg = (TextMessage) messageConsumer.receive(10000);
				if (msg != null) {
					System.out.println("消费者收到的消息: " + msg);
				} else {
					break;
				}
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
}



