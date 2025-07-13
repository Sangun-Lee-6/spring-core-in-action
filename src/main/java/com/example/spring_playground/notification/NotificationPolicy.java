package com.example.spring_playground.notification;

import com.example.spring_playground.domain.Member;

public interface NotificationPolicy {
    void notify(Member member);
}
