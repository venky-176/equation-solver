package com.Venkat.equationsolver.util;

import java.util.*;

public class InfixToPostfix {

    public static List<String> convert(List<String> tokens) {

        List<String> output = new ArrayList<>();
        Deque<String> operators = new ArrayDeque<>();

        for (String token : tokens) {

            // 1. Operand: number or variable
            if (isOperand(token)) {
                output.add(token);
            }

            // 2. Opening parenthesis
            else if (token.equals("(")) {
                operators.push(token);
            }

            // 3. Closing parenthesis
            else if (token.equals(")")) {

                while (!operators.isEmpty()
                        && !operators.peek().equals("(")) {

                    output.add(operators.pop());
                }

                if (operators.isEmpty()) {
                    throw new IllegalArgumentException(
                            "Mismatched parentheses"
                    );
                }

                operators.pop();
            }

            // 4. Operator
            else if (isOperator(token)) {

                while (!operators.isEmpty()
                        && isOperator(operators.peek())) {

                    String top = operators.peek();

                    if (shouldPop(top, token)) {
                        output.add(operators.pop());
                    } else {
                        break;
                    }
                }

                operators.push(token);
            }

            else {
                throw new IllegalArgumentException(
                        "Invalid token: " + token
                );
            }
        }

        // 5. Pop remaining operators
        while (!operators.isEmpty()) {

            String operator = operators.pop();

            if (operator.equals("(")) {
                throw new IllegalArgumentException(
                        "Mismatched parentheses"
                );
            }

            output.add(operator);
        }

        return output;
    }

    private static boolean isOperand(String token) {

        return token.matches(
                "\\d+(\\.\\d+)?"
        ) || token.matches(
                "[a-zA-Z_][a-zA-Z0-9_]*"
        );
    }

    private static boolean isOperator(String token) {

        return token.equals("+")
                || token.equals("-")
                || token.equals("*")
                || token.equals("/")
                || token.equals("^");
    }

    private static int precedence(String operator) {

        return switch (operator) {
            case "^" -> 3;
            case "*", "/" -> 2;
            case "+", "-" -> 1;
            default -> -1;
        };
    }

    private static boolean isRightAssociative(String operator) {

        return operator.equals("^");
    }

    private static boolean shouldPop(
            String stackOperator,
            String currentOperator) {

        int topPrecedence = precedence(stackOperator);
        int currentPrecedence = precedence(currentOperator);

        if (isRightAssociative(currentOperator)) {
            return topPrecedence > currentPrecedence;
        }

        return topPrecedence >= currentPrecedence;
    }

}
