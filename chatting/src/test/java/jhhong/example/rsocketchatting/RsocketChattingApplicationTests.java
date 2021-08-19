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

import java.util.concurrent.CountDownLatch;

@SpringBootTest
class RsocketChattingApplicationTests {

    @Autowired
    private RSocketRequester requester;         // rsocket 요청 보내줌

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Receiver receiver;

    @Test
    void requestTest() throws InterruptedException {

        MimeType authenticationMimeType =
                MimeTypeUtils.parseMimeType(WellKnownMimeType.MESSAGE_RSOCKET_AUTHENTICATION.getString());
        String token = JWT.create()
                .withSubject("test@dsm.hs.kr")
                .sign(Algorithm.HMAC256("test"));
        CountDownLatch countDownLatch = new CountDownLatch(1);


        // createChatroom
        ObjectNode node = objectMapper.createObjectNode();

//        requester
//                .route("create.chatroom")
//                .metadata(token, authenticationMimeType)
//                .data(node.put("roomName", "roomName"))
//                .send()
//                .block();
//
//        System.out.println("getChatRoom");
//        // getChatRoom
//        Flux<ChatRoomResponse> responseFlux = requester
//                .route("chatroom")
//                .metadata(token, authenticationMimeType)
//                .retrieveFlux(ChatRoomResponse.class)
//                .doOnNext(System.out::println);


        requester
                .route("join.chatroom." + "611ca8e607dcc54031a4862d")
                .retrieveFlux(ChatResponse.class)
                .doOnComplete(() -> System.out.println("끝났다고??"))
                .doOnError(throwable -> System.out.println("에러???"))
                .log()
                .subscribe();

        requester.route("611ca8e607dcc54031a4862d.send")
                .metadata(token, authenticationMimeType)
                .data(objectMapper.createObjectNode().put("content", "test"))
                .send()
                .block();

//        responseFlux
//                .flatMap(res -> Mono.just(requester.route(res.getChatRoomId() + ".send")))
//                .flatMap(requestSpec -> requestSpec
//                        .metadata(token, authenticationMimeType)
//                        .data(objectMapper.createObjectNode().put("message", "test"))
//                        .send())
//                .subscribe();

//        System.out.println("stream");
//        responseFlux
//                .flatMap(res -> Mono.just(requester.route(res.getChatRoomId() + ".stream")))
//                .flatMap(requestSpec -> requestSpec.metadata(token, authenticationMimeType).retrieveFlux(ChatResponse.class))
//                .doOnNext(chatResponse -> System.out.println(chatResponse.getMessage()))
//                .subscribe();
//                .route("send")
//                .metadata(token, authenticationMimeType)
//                .data("test message2")
//                .send()
//                .block();
//
//        Thread.sleep(3000);
//
//        requester.route("message")
//                .metadata(token, authenticationMimeType)
//                .retrieveFlux(ChatResponse.class)
//                .doOnNext(chat -> System.out.println(chat.getMessage()))
//                .subscribe();
//
//        StepVerifier verifier = requester.route("message")
//                .metadata(token, authenticationMimeType)
//                .retrieveFlux(Chat.class)
//                .log().as(StepVerifier::create)
//                .consumeNextWith(it -> assertThat(it.getMessage()).isEqualTo("test message"))
//                .consumeNextWith(it -> assertThat(it.getMessage()).isEqualTo("test message2"))
//                .thenCancel()
//                .verifyLater();


//        requester
//        verifier.verify(Duration.ofSeconds(5));
    }

}
