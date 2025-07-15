package com.example.spring_playground.notification;

import com.example.spring_playground.domain.Grade;
import com.example.spring_playground.domain.Member;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class NotificationDispatcher {

    private final Map<String, NotificationPolicy> policyMap;

    public NotificationDispatcher(Map<String, NotificationPolicy> policyMap) {
        this.policyMap = policyMap;
    }

    public void dispatch(Member member) {
        NotificationPolicy policy = selectPolicy(member);
        policy.notify(member);
    }

    // 📌 전략패턴
    private NotificationPolicy selectPolicy(Member member) {
        if (member.getGrade() == Grade.VIP) {
            return policyMap.get("emailNotificationPolicy");
        }
        else {
            return policyMap.get("consoleNotificationPolicy");
        }
    }
}
