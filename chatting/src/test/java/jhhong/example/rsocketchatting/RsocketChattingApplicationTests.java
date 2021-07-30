package jhhong.example.rsocketchatting;

import io.rsocket.metadata.WellKnownMimeType;
import jhhong.example.rsocketchatting.domain.chat.entity.Chat;
import jhhong.example.rsocketchatting.domain.user.entity.User;
import jhhong.example.rsocketchatting.domain.user.entity.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.security.rsocket.metadata.BearerTokenMetadata;
import org.springframework.security.rsocket.metadata.UsernamePasswordMetadata;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RsocketChattingApplicationTests {

    @Autowired
    @Lazy
    private RSocketRequester requester;         // rsocket 요청 보내줌

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userRepository.save(
                User.builder()
                        .email("email@dsm.hs.kr")
                        .password("pwd")
                        .nickname("nick")
                        .build()
        );
    }

    @AfterEach
    void cleanUp() {
        userRepository.deleteAll();
    }

    @Test
    void requestTest() {

        UsernamePasswordMetadata credentials = new BearerToken("email@dsm.hs.kr", "password", new ArrayList<>());
        MimeType authenticationMimeType =
                MimeTypeUtils.parseMimeType(WellKnownMimeType.MESSAGE_RSOCKET_AUTHENTICATION.getString());

        requester
                .route("send")
                .metadata(credentials, authenticationMimeType)
                .data("test message")
                .send().block();

        requester
                .route("send")
                .metadata(credentials, authenticationMimeType)
                .data("test message2")
                .send().block();

        StepVerifier verifier = requester.route("message")
                .retrieveFlux(Chat.class)
                .log().as(StepVerifier::create)
                .consumeNextWith(it -> assertThat(it.getMessage()).isEqualTo("test message"))
                .consumeNextWith(it -> assertThat(it.getMessage()).isEqualTo("test message2"))
                .thenCancel()
                .verifyLater();

        verifier.verify(Duration.ofSeconds(5));
    }

}
