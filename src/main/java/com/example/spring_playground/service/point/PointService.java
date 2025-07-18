package com.example.spring_playground.service.point;

import com.example.spring_playground.domain.Grade;
import com.example.spring_playground.domain.Member;
import org.springframework.stereotype.Component;

@Component
public class PointService {

    // 공유 상태 저장 필드
    private int point;

    public void accumulatePoint(Member member) {
        if (member.getGrade() == Grade.VIP) {
            this.point = 1000;
        } else {
            this.point = 100;
        }
    }

    public int getPoint() {
        return this.point;
    }
}
