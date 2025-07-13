package com.example.spring_playground.notification;

import com.example.spring_playground.domain.Grade;
import com.example.spring_playground.domain.Member;

public class ConsoleNotifier {

    public void send(Member member) {
        if (member.getGrade() == Grade.BASIC) {
            System.out.println("가입을 축하드립니다!");
        } else {
            System.out.println("VIP 회원님, 가입을 축하드립니다!");
        }
    }
}
