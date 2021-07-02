package com.example.model;

public class MailModel {
    private String to;
    private String body;
    private String topic;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public String toString() {
        return "MailModel [body=" + body + ", to=" + to + ", topic=" + topic + "]";
    }
}
