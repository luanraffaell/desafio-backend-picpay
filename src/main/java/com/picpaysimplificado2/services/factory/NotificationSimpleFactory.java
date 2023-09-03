package com.picpaysimplificado2.services.factory;

import com.picpaysimplificado2.services.EmailNotificationService;
import com.picpaysimplificado2.services.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
@AllArgsConstructor
@Component
public class NotificationSimpleFactory {
    private EmailNotificationService emailNotificationService;

    public NotificationService getNotification(String type){
        if(type.equalsIgnoreCase("email")){
            return this.emailNotificationService;
        }
        return null;
    }
}
