package com.Venkat.equationsolver.dto;

import com.Venkat.equationsolver.model.Equation;
import java.util.List;

public class EquationListResponse {

    private List<Equation> equations;

    public EquationListResponse(List<Equation> equations) {
        this.equations = equations;
    }

    public List<Equation> getEquations() {
        return equations;
    }
}