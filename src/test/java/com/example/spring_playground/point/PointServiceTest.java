package com.example.spring_playground.point;

import com.example.spring_playground.config.SpringConfig;
import com.example.spring_playground.domain.Grade;
import com.example.spring_playground.domain.Member;
import com.example.spring_playground.repository.MemberRepository;
import com.example.spring_playground.service.point.PointService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class PointServiceTest {

    @Autowired
    ApplicationContext ac;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void point_test() {
        // given
        Member memberA = new Member();
        memberA.setName("Tom");
        memberA.setGrade(Grade.BASIC);

        Member memberB = new Member();
        memberB.setName("Marry");
        memberB.setGrade(Grade.VIP);

        // 회원 저장 (ID 부여)
        memberRepository.save(memberA);
        memberRepository.save(memberB);

        // when
        PointService pointService1 = ac.getBean(PointService.class);
        PointService pointService2 = ac.getBean(PointService.class);

        int pointA = pointService1.accumulatePoint(memberA);
        int pointB = pointService2.accumulatePoint(memberB);

        // 누적
        memberRepository.savePoint(memberA.getId(), pointA);
        memberRepository.savePoint(memberB.getId(), pointB);

        // then
        Member foundA = memberRepository.findById(memberA.getId()).get();
        Member foundB = memberRepository.findById(memberB.getId()).get();

        Assertions.assertThat(foundA.getPoint()).isEqualTo(100);
        Assertions.assertThat(foundB.getPoint()).isEqualTo(1000);
    }
}

