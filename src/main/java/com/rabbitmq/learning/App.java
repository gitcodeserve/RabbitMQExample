package com.rabbitmq.learning;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 * Hello world program to create queue and
 *
 */
public class App {
    public static void main( String[] args ) throws IOException, TimeoutException{

    	ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = null;
        Channel channel = null;
		try {
			connection = factory.newConnection();
			channel = connection.createChannel();
			
			channel.queueDeclare("my-queue", true, false, false, null);
			String message = "Hello World from a simple Java program ... !";
			channel.basicPublish("", "my-queue", null, message.getBytes());
			System.out.println(" [x] Sent '" + message + "'");
			
			Consumer consumer = new DefaultConsumer(channel) {
				  @Override
				  public void handleDelivery(String consumerTag, Envelope envelope,
				                             AMQP.BasicProperties properties, byte[] body)
				      throws IOException {
				    String message = new String(body, "UTF-8");
				    System.out.println(" [x] Received '" + message + "'");
				  }
				};
				channel.basicConsume("my-queue", true, consumer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
