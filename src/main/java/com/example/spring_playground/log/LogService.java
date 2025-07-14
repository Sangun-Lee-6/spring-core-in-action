package com.example.spring_playground.log;

import java.util.List;

public class LogService {
    private final LogRepository logRepository = new InMemoryLogRepository();

    /**
     * 로그 남기기
     */
    public void log(String message) {
        logRepository.save(message);
    }

    /**
     * 전체 로그 조회
     */
    public List<String> getLogs() {
        return logRepository.findAll();
    }
}
