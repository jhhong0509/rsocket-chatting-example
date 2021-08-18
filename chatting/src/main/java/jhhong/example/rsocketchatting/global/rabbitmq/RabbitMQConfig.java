package jhhong.example.rsocketchatting.global.rabbitmq;

import com.rabbitmq.client.Connection;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.*;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "chat.room.exchange";

    @Bean
    public Mono<Connection> connectionMono(CachingConnectionFactory connectionFactory) {    // RabbitConnection 생성
        return Mono.fromCallable(() -> connectionFactory
                .getRabbitConnectionFactory().newConnection());
    }

    @Bean
    public Sender sender(Mono<Connection> mono) {
        return RabbitFlux.createSender(new SenderOptions().connectionMono(mono));           // RabbitConnection으로 Sender 생성
    }

    @Bean
    public Receiver receiver(Mono<Connection> connectionMono) {
        return RabbitFlux.createReceiver(new ReceiverOptions().connectionMono(connectionMono)); // Receiver 생성
    }

}
