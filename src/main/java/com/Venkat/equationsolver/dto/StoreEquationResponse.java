package com.Venkat.equationsolver.dto;

public class StoreEquationResponse {

    private String message;
    private String equationId;

    public StoreEquationResponse(String message, String equationId) {
        this.message = message;
        this.equationId = equationId;
    }

    public String getMessage() {
        return message;
    }

    public String getEquationId() {
        return equationId;
    }
}