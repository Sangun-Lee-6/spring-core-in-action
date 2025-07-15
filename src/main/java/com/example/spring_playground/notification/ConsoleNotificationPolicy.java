package com.example.spring_playground.notification;

import com.example.spring_playground.domain.Grade;
import com.example.spring_playground.domain.Member;
import org.springframework.stereotype.Component;

@Component
public class ConsoleNotificationPolicy implements NotificationPolicy {
    @Override
    public void notify(Member member) {
        if (member.getGrade() == Grade.VIP) {
            System.out.println("VIP 회원님, 특별 혜택이 준비되어 있습니다.");
        } else {
            System.out.println("가입을 축하드립니다!");
        }
    }
}
