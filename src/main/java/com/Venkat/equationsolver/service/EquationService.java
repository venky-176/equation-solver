package com.Venkat.equationsolver.service;

import com.Venkat.equationsolver.model.Equation;
import com.Venkat.equationsolver.util.ExpressionTreeReconstructor;
import com.Venkat.equationsolver.model.ExpressionNode;
import com.Venkat.equationsolver.repository.EquationRepository;
import com.Venkat.equationsolver.util.*;
import org.springframework.stereotype.Service;
import com.Venkat.equationsolver.util.ExpressionEvaluator;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class EquationService {

    private final EquationRepository equationRepository;

    private final AtomicLong idGenerator = new AtomicLong(1);

    public EquationService(EquationRepository equationRepository) {
        this.equationRepository = equationRepository;
    }

    public String storeEquation(String equation) {

        if (equation == null || equation.isBlank()) {
            throw new IllegalArgumentException("Equation cannot be empty");
        }

        // Step 1: Convert the equation into tokens
        List<String> tokens = Tokenizer.tokenize(equation);

        // Step 2: Convert infix tokens into postfix notation
        List<String> postfix = InfixToPostfix.convert(tokens);

        // Step 3: Construct the expression tree
        ExpressionNode root = PostfixTreeBuilder.build(postfix);

        // Step 4: Generate a unique ID
        String id = String.valueOf(idGenerator.getAndIncrement());

        // Step 5: Store the expression tree
        equationRepository.save(id, equation,root);

        return id;
    }

    public List<Equation> getAllEquations() {

        return new ArrayList<>(
                equationRepository.findAll().values()
        );
    }

    public ExpressionNode getExpressionTree(String id) {

        ExpressionNode root = equationRepository.findTreeById(id);

        if (root == null) {
            throw new EquationNotFoundException(
                    "Equation not found with ID: " + id
            );
        }

        return root;
    }



    public double evaluateEquation(
            String id,
            Map<String, Double> variables) {

        // Step 1: Find the expression tree
        ExpressionNode root = equationRepository.findTreeById(id);

        // Step 2: Check whether the equation exists
        if (root == null) {
            throw new EquationNotFoundException(
                    "Equation not found with ID: " + id);
        }

        // Step 3: Evaluate the expression tree
        return ExpressionEvaluator.evaluate(root, variables);
    }

}