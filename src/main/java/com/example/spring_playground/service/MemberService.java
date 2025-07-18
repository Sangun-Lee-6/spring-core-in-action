package com.example.spring_playground.service;

import com.example.spring_playground.domain.Grade;
import com.example.spring_playground.domain.Member;
import com.example.spring_playground.log.LogService;
import com.example.spring_playground.notification.ConsoleNotificationPolicy;
import com.example.spring_playground.notification.EmailNotificationPolicy;
import com.example.spring_playground.notification.NotificationDispatcher;
import com.example.spring_playground.notification.NotificationPolicy;
import com.example.spring_playground.repository.MemberRepository;
import com.example.spring_playground.service.point.PointService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final LogService logService;
    private final NotificationDispatcher notificationDispatcher;
    private final PointService pointService;

    public MemberService(MemberRepository memberRepository, LogService logService, NotificationDispatcher notificationDispatcher, PointService pointService) {
        this.memberRepository = memberRepository;
        this.logService = logService;
        this.notificationDispatcher = notificationDispatcher;
        this.pointService = pointService;
    }

    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);

        // 알림
        notificationDispatcher.dispatch(member);
        if (member.getGrade() == Grade.VIP) {
            logService.log("[VIP가입] " + member.getName());
        }

        // 포인트 적립
        int point = pointService.accumulatePoint(member);
        memberRepository.savePoint(member.getId(), point);

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
