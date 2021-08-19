package jhhong.example.rsocketchatting.global.requester;

import io.rsocket.RSocket;
import io.rsocket.metadata.RoutingMetadata;
import io.rsocket.transport.netty.client.WebsocketClientTransport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.cbor.Jackson2CborDecoder;
import org.springframework.http.codec.cbor.Jackson2CborEncoder;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.security.rsocket.metadata.SimpleAuthenticationEncoder;
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
                .rsocketConnector(rSocketConnector -> rSocketConnector.reconnect(Retry.fixedDelay(3, Duration.ofSeconds(2))))
                .transport(WebsocketClientTransport.create(URI.create("ws://localhost:7000/rsocket")));
    }

    @Bean
    RSocketStrategies rSocketStrategies() {
        return RSocketStrategies.builder()
                .decoders(decoders -> decoders.add(new Jackson2JsonDecoder()))
                .decoders(decoders -> decoders.add(new Jackson2CborDecoder()))
                .encoders(encoders -> encoders.add(new Jackson2CborEncoder()))
                .encoders(encoders -> encoders.add(new Jackson2JsonEncoder()))
                .encoders(encoders -> encoders.add(new SimpleAuthenticationEncoder()))
                .build();
    }

}
