package jhhong.example.rsocketchatting.global.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue chatRoomQueue() {
        return new Queue("topic.room", true);
    }

    @Bean
    public Binding bindingTopicExchange() {
        return BindingBuilder.bind(topicExchange())
                .to(topicExchange())
                .with("topic.public.*");
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("chatroom-id");
    }

}
