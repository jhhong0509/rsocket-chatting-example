package jhhong.example.rsocketchatting.global.requester;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.util.MimeTypeUtils;
import reactor.util.retry.Retry;

import java.net.URI;
import java.time.Duration;

@Configuration
public class RequesterConfig {

    @Bean
    RSocketRequester requester(RSocketRequester.Builder builder) {
        return builder.dataMimeType(MimeTypeUtils.APPLICATION_JSON)
                .dataMimeType(MimeTypeUtils.TEXT_PLAIN)
                .rsocketConnector(rSocketConnector -> rSocketConnector.reconnect(Retry.fixedDelay(2, Duration.ofSeconds(2))))
                .websocket(URI.create("ws://localhost:8081/rsocket"));
    }

}
