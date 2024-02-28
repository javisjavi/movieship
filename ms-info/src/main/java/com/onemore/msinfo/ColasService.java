package com.onemore.msinfo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

@Component
public class ColasService implements IColasService {

    private final String HOST;
    private final String QUEUE_INFO;

    public ColasService(@Value("${msinfo.host}") String host, 
                        @Value("${msinfo.queue.info}") String queueInfo) {
        this.HOST = host;
        this.QUEUE_INFO = queueInfo;
    }
    
    public void conectarColaInfo() {
        try {
            Channel channel = getChannelFromLocalhost();
            channel.queueDeclare(QUEUE_INFO, false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Recibido en QUEUE_INFO: '" + message + "'");
            };
            channel.basicConsume(QUEUE_INFO, true, deliverCallback, consumerTag -> { });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private Channel getChannelFromLocalhost() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        Connection connection;
        Channel channel = null;
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return channel;
    }
    
}