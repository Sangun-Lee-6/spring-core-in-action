package com.example.spring_playground.envmessage;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
public class ProdMessageProvider implements MessageProvider {
    @Override
    public String getMessage() {
        return "[PROD] 운영 환경입니다.";
    }
}
