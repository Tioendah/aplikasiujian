package com.tioprasetioa.www.mtsn3rohul.Model.notifikasifcm;

public class NotificationData {
    private final String title;
    private final String body;

    public NotificationData(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}
