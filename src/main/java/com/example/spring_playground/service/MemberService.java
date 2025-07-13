package com.example.spring_playground.service;

import com.example.spring_playground.domain.Member;
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
    // ✅ DIP 만족
    private final NotificationPolicy notificationPolicy;


    public MemberService(MemberRepository memberRepository, NotificationPolicy notificationPolicy) {
        this.memberRepository = memberRepository;
        this.notificationPolicy = notificationPolicy;
    }

    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        // ✅ SRP 만족
        notificationPolicy.notify(member);
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
