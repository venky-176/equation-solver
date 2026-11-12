package com.Venkat.equationsolver.controller;

import com.Venkat.equationsolver.dto.*;
import com.Venkat.equationsolver.service.EquationService;

import com.Venkat.equationsolver.util.ExpressionTreeReconstructor;
import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.Venkat.equationsolver.dto.EvaluateEquationRequest;
import com.Venkat.equationsolver.dto.EvaluateEquationResponse;
import com.Venkat.equationsolver.model.ExpressionNode;
//import com.Venkat.equationsolver.util.ExpressionTreeReconstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Equation Management",
        description = "APIs for storing, retrieving, and evaluating algebraic equations"
)
@RestController
@RequestMapping("/api/equations")
public class EquationController {

    private final EquationService equationService;

    public EquationController(EquationService equationService) {
        this.equationService = equationService;
    }

    @Operation(
            summary = "Retrieve all stored equations",
            description = "Retrieves equations by reconstructing their infix expressions from expression trees."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Equations retrieved successfully"
    )

    @GetMapping
    public EquationListResponse getAllEquations() {

        return new EquationListResponse(
                equationService.getAllEquations()
        );
    }

    @Operation(
            summary = "Evaluate a stored equation",
            description = "Evaluates an equation using the supplied variable values."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Equation evaluated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid variables or evaluation error"),
            @ApiResponse(responseCode = "404", description = "Equation ID not found")
    })

    @PostMapping("/{equationId}/evaluate")
    public EvaluateEquationResponse evaluateEquation(
            @PathVariable String equationId,
            @Valid @RequestBody EvaluateEquationRequest request) {

        double result = equationService.evaluateEquation(
                equationId,
                request.getVariables()
        );

        String expression = ExpressionTreeReconstructor.reconstruct(
                equationService.getExpressionTree(equationId)
        );

        return new EvaluateEquationResponse(
                equationId,
                expression,
                request.getVariables(),
                result
        );
    }


    @Operation(
            summary = "Store an algebraic equation",
            description = "Converts an infix equation into postfix notation, builds an expression tree, and stores it in memory."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Equation stored successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid equation syntax")
    })

    @PostMapping("/store")
    @ResponseStatus(HttpStatus.CREATED)
    public StoreEquationResponse storeEquation(
            @Valid @RequestBody StoreEquationRequest request) {

        String id = equationService.storeEquation(request.getEquation());

        return new StoreEquationResponse(
                "Equation stored successfully",
                id
        );
    }
}