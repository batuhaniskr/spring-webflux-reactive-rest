package com.example.webflux.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.io.Serializable;

public class Error implements Serializable {

    private String code;
    private String message;

    @JsonCreator
    public Error(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
