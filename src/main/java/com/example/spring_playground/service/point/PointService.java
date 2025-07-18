package com.example.spring_playground.service.point;

import com.example.spring_playground.domain.Grade;
import com.example.spring_playground.domain.Member;
import org.springframework.stereotype.Component;

@Component
public class PointService {

    // ❌ 문제의 원인 : 공유 상태 저장 필드
//    private int point;

    public int accumulatePoint(Member member) {
        if (member.getGrade() == Grade.VIP) {
            return 1000;
        } else {
            return 100;
        }
    }
}
