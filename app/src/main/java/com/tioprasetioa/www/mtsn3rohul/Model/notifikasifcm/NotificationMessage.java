package com.tioprasetioa.www.mtsn3rohul.Model.notifikasifcm;

public class NotificationMessage {
    private final NotificationData notification;
    private final String topic;

    public NotificationMessage(String title, String body, String topic) {
        this.notification = new NotificationData(title, body);
        this.topic = topic;
    }

    public NotificationData getNotification() {
        return notification;
    }

    public String getTopic() {
        return topic;
    }
}

