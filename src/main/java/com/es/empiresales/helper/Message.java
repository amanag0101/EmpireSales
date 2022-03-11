package com.es.empiresales.helper;

public class Message {
    private String type;
    private String message;

    public Message() {
        super();
    }

    public Message(String type, String message) {
        this.type = type;
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message [message=" + message + ", type=" + type + "]";
    }    
}
