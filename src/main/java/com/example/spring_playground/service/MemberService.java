package com.example.spring_playground.service;

import com.example.spring_playground.domain.Grade;
import com.example.spring_playground.domain.Member;
import com.example.spring_playground.log.LogService;
import com.example.spring_playground.notification.NotificationPolicy;
import com.example.spring_playground.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final NotificationPolicy notificationPolicy;
    private final LogService logService; // 스프링 빈이 같은 객체를 주입


    public MemberService(MemberRepository memberRepository, NotificationPolicy notificationPolicy, LogService logService) {
        this.memberRepository = memberRepository;
        this.notificationPolicy = notificationPolicy;
        this.logService = logService;
    }

    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        notificationPolicy.notify(member);

        // 📌 VIP만 로그 기록
        if (member.getGrade() == Grade.VIP) {
            logService.log("[VIP가입] " + member.getName());
        }

        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> { throw new IllegalStateException("이미 존재하는 회원입니다."); });
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
