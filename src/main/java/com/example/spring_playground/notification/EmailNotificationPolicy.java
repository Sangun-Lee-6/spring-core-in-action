package com.example.spring_playground.notification;

import com.example.spring_playground.domain.Member;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationPolicy implements NotificationPolicy {

    @Override
    public void notify(Member member) {
        System.out.println("📨[이메일 발송]: " + member.getName() + "님, 가입을 축하드립니다.");
    }
}
