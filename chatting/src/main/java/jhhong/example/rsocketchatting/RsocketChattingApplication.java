package jhhong.example.rsocketchatting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;

@EnableReactiveMongoAuditing
@SpringBootApplication
public class RsocketChattingApplication {

    public static void main(String[] args) {
        SpringApplication.run(RsocketChattingApplication.class, args);
    }

}
