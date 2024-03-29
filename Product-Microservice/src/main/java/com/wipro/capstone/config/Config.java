package com.wipro.capstone.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class Config {

	@Bean
	public NewTopic myTopic() {
		return TopicBuilder.name("ProductMicro").build();
	}

}
