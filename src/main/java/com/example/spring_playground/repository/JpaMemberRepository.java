package com.example.spring_playground.repository;

import com.example.spring_playground.domain.Member;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {

    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(em.find(Member.class, id));
    }

    public Optional<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList()
                .stream()
                .findAny();
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    @Override
    public void savePoint(Long memberId, int point) {
        Member member = em.find(Member.class, memberId);
        int oldPoint = member.getPoint();
        if (member != null) {
            member.setPoint(member.getPoint() + point);
        } else {
            throw new IllegalArgumentException("존재하지 않는 회원 ID입니다: " + memberId);
        }
    }
}
