package com.authMS.Auth.microsservice.adapters.outbound.messaging;

import com.authMS.Auth.microsservice.application.dtos.UserLogDto;
import com.authMS.Auth.microsservice.domain.messaging.IKafkaService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaServiceImpl implements IKafkaService {

    private final KafkaTemplate<String, UserLogDto> authLogTemplate;

    @Override
    public void sendAuthLog(UserLogDto userLogDto, String key) {
        authLogTemplate.send("auth-logs", key, userLogDto);
    }
}
