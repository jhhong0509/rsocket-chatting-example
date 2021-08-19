package jhhong.example.rsocketchatting;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.rsocket.metadata.WellKnownMimeType;
import jhhong.example.rsocketchatting.domain.chat.payload.ChatResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import reactor.rabbitmq.Receiver;
import reactor.test.StepVerifier;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class RsocketChattingApplicationTests {

    @Autowired
    private RSocketRequester requester;         // rsocket 요청 보내줌

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void requestTest() throws InterruptedException {

        MimeType authenticationMimeType =
                MimeTypeUtils.parseMimeType(WellKnownMimeType.MESSAGE_RSOCKET_AUTHENTICATION.getString());
        String token = JWT.create()
                .withSubject("test@dsm.hs.kr")
                .sign(Algorithm.HMAC256("testtesttesttesttesttesttesttest"));

        requester.route("create.chatroom")
                .metadata(token, authenticationMimeType)
                .data(objectMapper.createObjectNode().put("roomName", "test"))
                .send()
                .subscribe();
        new CountDownLatch(1).await(10, TimeUnit.SECONDS);

    }

}
