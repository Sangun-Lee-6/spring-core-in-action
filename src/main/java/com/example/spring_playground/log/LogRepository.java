package com.example.spring_playground.log;

import java.util.List;

public interface LogRepository {
    void save(String log); // 로그 저장
    List<String> findAll(); // 전체 로그 조회
}
