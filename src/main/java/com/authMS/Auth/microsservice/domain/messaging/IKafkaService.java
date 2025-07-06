package com.authMS.Auth.microsservice.domain.messaging;

import com.authMS.Auth.microsservice.application.dtos.UserLogDto;

public interface IKafkaService {
    void sendAuthLog(UserLogDto userLogDto, String key);
}
