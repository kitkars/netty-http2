package com.http2.server;


public class Request {

    private String message;

    public Request() {}

    public Request(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
