package jhhong.example.rsocketchatting;

import io.rsocket.metadata.WellKnownMimeType;
import jhhong.example.rsocketchatting.domain.chat.entity.Chat;
import jhhong.example.rsocketchatting.domain.chat.entity.ChatRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.security.rsocket.metadata.UsernamePasswordMetadata;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import reactor.test.StepVerifier;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RsocketChattingApplicationTests {

    @Autowired
    private RSocketRequester requester;         // rsocket 요청 보내줌

    @Autowired
    private ChatRepository chatRepository;

    @Test
    void requestTest() throws InterruptedException {

        UsernamePasswordMetadata credentials = new UsernamePasswordMetadata("user", "pass");
        MimeType authenticationMimeType =
                MimeTypeUtils.parseMimeType(WellKnownMimeType.MESSAGE_RSOCKET_AUTHENTICATION.getString());

        requester
                .route("send")
                .metadata(credentials, authenticationMimeType)
                .data("test message")
                .send()
                .block();

        requester
                .route("send")
                .metadata(credentials, authenticationMimeType)
                .data("test message2")
                .send()
                .block();

        Thread.sleep(3000);

        requester.route("message")
                .metadata(credentials, authenticationMimeType)
                .retrieveFlux(Chat.class)
                .doOnNext(chat -> System.out.println(chat.getMessage()))
                .subscribe();

        StepVerifier verifier = requester.route("message")
                .metadata(credentials, authenticationMimeType)
                .retrieveFlux(Chat.class)
                .log().as(StepVerifier::create)
                .consumeNextWith(it -> assertThat(it.getMessage()).isEqualTo("test message"))
                .consumeNextWith(it -> assertThat(it.getMessage()).isEqualTo("test message2"))
                .thenCancel()
                .verifyLater();

        verifier.verify(Duration.ofSeconds(5));
    }

}
