package com.example.spring_playground.log;

import java.util.ArrayList;
import java.util.List;

public class InMemoryLogRepository implements LogRepository{

    // 로그를 저장하는 자료구조
    private final List<String> store = new ArrayList<>();

    @Override
    public void save(String log) {
        store.add(log); // 로그를 ArrayList에 저장
    }

    @Override
    public List<String> findAll() {
        return new ArrayList<>(store); // 복사본 반환
    }
}
