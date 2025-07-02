package com.authMS.Auth.microsservice.services;

import com.authMS.Auth.microsservice.dtos.UserLogDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, UserLogDto> authLogTemplate;

    public void authLogsSender(UserLogDto LogMessageDto, String key) {
        authLogTemplate.send("auth-logs", key, LogMessageDto);
    }
}
