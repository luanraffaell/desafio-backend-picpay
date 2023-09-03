package com.picpaysimplificado2.services;

import com.picpaysimplificado2.domain.user.User;

public interface NotificationService {
    void sendNotification(User user, String message);
}
