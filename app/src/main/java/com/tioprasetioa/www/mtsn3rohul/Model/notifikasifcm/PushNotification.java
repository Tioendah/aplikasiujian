package com.tioprasetioa.www.mtsn3rohul.Model.notifikasifcm;

public class PushNotification {
    private NotificationMessage message;

    public PushNotification(NotificationMessage message) {
        this.message = message;
    }

    public NotificationMessage getMessage() {
        return message;
    }

    public void setMessage(NotificationMessage message) {
        this.message = message;
    }
}
