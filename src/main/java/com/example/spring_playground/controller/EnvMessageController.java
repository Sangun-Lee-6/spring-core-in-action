package com.example.spring_playground.controller;

import com.example.spring_playground.envmessage.MessageProvider;
import org.springframework.web.bind.annotation.GetMapping;

public class EnvMessageController {

    private final MessageProvider messageProvider;

    public EnvMessageController(MessageProvider messageProvider) {
        this.messageProvider = messageProvider;
    }

    @GetMapping("/env/message")
    public String getEnvMessage() {
        return messageProvider.getMessage();
    }
}
