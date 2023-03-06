package com.venuprasath.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@SpringBootApplication
public class RedisApplication {

	public static final Logger logger = LoggerFactory.getLogger(RedisApplication.class);

	@Bean
	RedisMessageListenerContainer container(RedisConnectionFactory factory,
											MessageListenerAdapter adapter) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(factory);
		container.addMessageListener(adapter, new PatternTopic("chat"));

		return container;
	}

	@Bean
	MessageListenerAdapter getMessageAdapter(Receiver receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}

	@Bean
	public Receiver receive() {
		return new Receiver();
	}

	@Bean
	public StringRedisTemplate template(RedisConnectionFactory factory) {
		return new StringRedisTemplate(factory);
	}

	public static void main(String[] args) throws InterruptedException {
		ApplicationContext ctx = SpringApplication.run(RedisApplication.class, args);
		StringRedisTemplate template = ctx.getBean(StringRedisTemplate.class);
		Receiver receiver = ctx.getBean(Receiver.class);

		while(receiver.getCounter() == 0) {
			logger.info("Sending Message...");
			template.convertAndSend("chat", "Hello from redis!");
			Thread.sleep(500L);

		}
		System.exit(0);
	}

}
