package com.example.testcarmanagement.global;

import lombok.Data;

@Data
public class ResponseObject {
    private String message;
    private String status;
    private Object data;

    public ResponseObject(String message, String status, Object data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }
}
