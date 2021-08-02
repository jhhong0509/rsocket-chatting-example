package jhhong.example.rsocketchatting.domain.view.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class HtmlController {

    @GetMapping("/")
    public String index() {
        return "chatrs";
    }
    @GetMapping("/ch")
    public String page() {
        return "chat";
    }
}
