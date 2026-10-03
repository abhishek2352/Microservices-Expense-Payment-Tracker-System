package com.example.notificationservice.service;

import com.example.notificationservice.model.Notification;
import com.example.notificationservice.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public Notification sendNotification(Notification notification) {
        // In a real implementation, this would actually send the notification
        // For now, we'll just save it and mark as sent
        notification.setStatus("SENT");
        return notificationRepository.save(notification);
    }
}