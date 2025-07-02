package com.authMS.Auth.microsservice.dtos;

import java.time.LocalDateTime;

public record UserLogDto(LocalDateTime timestamp, Long userId, String email, String username) {
}
