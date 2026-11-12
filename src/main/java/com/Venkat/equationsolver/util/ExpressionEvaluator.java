package com.Venkat.equationsolver.util;


import com.Venkat.equationsolver.model.ExpressionNode;

import java.util.Map;

public class ExpressionEvaluator {

    public static double evaluate(
            ExpressionNode root,
            Map<String, Double> variables) {

        if (root == null) {
            throw new IllegalArgumentException(
                    "Expression tree cannot be empty"
            );
        }

        String value = root.getValue();

        // If the node is a number
        if (isNumber(value)) {
            return Double.parseDouble(value);
        }

        // If the node is a variable
        if (!isOperator(value)) {

            if (!variables.containsKey(value)
                    || variables.get(value) == null) {

                throw new IllegalArgumentException(
                        "Missing value for variable: " + value
                );
            }

            return variables.get(value);
        }

        // Evaluate left and right subtrees first
        double left = evaluate(root.getLeft(), variables);
        double right = evaluate(root.getRight(), variables);

        // Apply the operator
        return switch (value) {

            case "+" -> left + right;

            case "-" -> left - right;

            case "*" -> left * right;

            case "/" -> {
                if (right == 0) {
                    throw new ArithmeticException(
                            "Division by zero is not allowed"
                    );
                }
                yield left / right;
            }

            case "^" -> Math.pow(left, right);

            default -> throw new IllegalArgumentException(
                    "Unsupported operator: " + value
            );
        };
    }

    private static boolean isNumber(String value) {

        return value.matches(
                "\\d+(\\.\\d+)?"
        );
    }

    private static boolean isOperator(String value) {

        return value.equals("+")
                || value.equals("-")
                || value.equals("*")
                || value.equals("/")
                || value.equals("^");
    }

}
