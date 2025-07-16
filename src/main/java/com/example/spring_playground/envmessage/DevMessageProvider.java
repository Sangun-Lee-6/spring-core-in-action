package com.example.spring_playground.envmessage;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class DevMessageProvider implements MessageProvider{
    @Override
    public String getMessage() {
        return "[DEV] 개발 환경입니다.";
    }
}
