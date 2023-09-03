package com.picpaysimplificado2.services;

import com.picpaysimplificado2.domain.user.User;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationService implements  NotificationService {
    @Override
    public void sendNotification(User user, String message) {
        System.out.println("Email notification:");
        System.out.println("User:"+user.getEmail());
        System.out.println("Message:"+message);
    }
}
