package com.Venkat.equationsolver.service;

public class EquationNotFoundException extends RuntimeException {

    public EquationNotFoundException(String message) {
        super(message);
    }
}