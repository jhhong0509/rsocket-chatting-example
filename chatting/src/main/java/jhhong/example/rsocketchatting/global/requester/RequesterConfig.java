package jhhong.example.rsocketchatting.global.requester;

import io.rsocket.RSocket;
import io.rsocket.metadata.RoutingMetadata;
import io.rsocket.transport.netty.client.WebsocketClientTransport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.util.MimeTypeUtils;
import reactor.util.retry.Retry;

import java.net.URI;
import java.time.Duration;

@Configuration
public class RequesterConfig {

    @Bean
    RSocketRequester requester(RSocketRequester.Builder builder, RSocketStrategies strategies) {
        return builder.dataMimeType(MimeTypeUtils.APPLICATION_JSON)
                .rsocketStrategies(strategies)
                .rsocketConnector(rSocketConnector -> rSocketConnector.reconnect(Retry.fixedDelay(2, Duration.ofSeconds(2))))
                .setupRoute("create.chatroom")
                .transport(WebsocketClientTransport.create(URI.create("ws://localhost:7000/rsocket")));
    }

}
