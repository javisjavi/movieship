package com.onemore.msnaves.common;

import org.springframework.stereotype.Service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

@Service
public class MensajesService implements IMensajesService {
	
	private final static String HOST = "localhost";
	private final static String QUEUE_INFO = "QUEUE_INFO";

	public void send(String mensaje) {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(HOST);
		try (
			Connection connection = factory.newConnection(); 
			Channel channel = connection.createChannel()) {
			channel.queueDeclare(QUEUE_INFO, false, false, false, null);
			channel.basicPublish("", QUEUE_INFO, null, mensaje.getBytes());
			System.out.println(" [o] Enviado a QUEUE_INFO: '" + mensaje + "'");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

}
