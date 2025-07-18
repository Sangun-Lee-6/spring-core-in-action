package com.example.spring_playground.point;

import com.example.spring_playground.config.SpringConfig;
import com.example.spring_playground.domain.Grade;
import com.example.spring_playground.domain.Member;
import com.example.spring_playground.service.point.PointService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootTest
public class PointServiceTest {

    @Autowired
    ApplicationContext ac;

    @Test
    void point_test() {
        Member memberA = new Member();
        memberA.setName("Tom");
        memberA.setGrade(Grade.BASIC);

        Member memberB = new Member();
        memberB.setName("Marry");
        memberB.setGrade(Grade.VIP);

        PointService pointService1 = ac.getBean(PointService.class);
        PointService pointService2 = ac.getBean(PointService.class);

        pointService1.accumulatePoint(memberA);
        pointService2.accumulatePoint(memberB);

        int pointA = pointService1.getPoint();
        Assertions.assertThat(pointA).isEqualTo(100);
        /**
         * Tests failed
         *
         * Expected :100
         * Actual   :1000
         */
    }
}
