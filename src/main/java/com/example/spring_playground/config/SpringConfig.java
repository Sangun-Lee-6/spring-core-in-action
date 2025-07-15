package com.example.spring_playground.config;

import com.example.spring_playground.controller.LogController;
import com.example.spring_playground.log.InMemoryLogRepository;
import com.example.spring_playground.log.LogRepository;
import com.example.spring_playground.log.LogService;
import com.example.spring_playground.notification.ConsoleNotificationPolicy;
import com.example.spring_playground.notification.NotificationDispatcher;
import com.example.spring_playground.notification.NotificationPolicy;
import com.example.spring_playground.repository.MemberRepository;
import com.example.spring_playground.repository.JpaMemberRepository;
import com.example.spring_playground.service.MemberService;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    private final EntityManager em;

    public SpringConfig(EntityManager em) {
        this.em = em;
    }

    @Bean
    public MemberRepository memberRepository() {
        return new JpaMemberRepository(em);
    }

    // 📌 @Component를 사용해서 빈 자동 등록
//    @Bean
//    public NotificationPolicy notificationPolicy() {
//        return new ConsoleNotificationPolicy();
//    }

    @Bean
    public LogRepository logRepository() {
        return new InMemoryLogRepository();
    }

    @Bean
    public LogController logController() {
        return new LogController(logService());
    }

    @Bean
    public LogService logService() {
        return new LogService(logRepository());
    }

    @Bean
    public MemberService memberService(
            MemberRepository memberRepository,
            LogService logService,
            NotificationDispatcher notificationDispatcher
    ) {
        return new MemberService(memberRepository, logService, notificationDispatcher);
    }

}
