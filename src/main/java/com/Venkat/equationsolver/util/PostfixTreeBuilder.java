package com.Venkat.equationsolver.util;

import com.Venkat.equationsolver.model.ExpressionNode;
import java.util.*;

public class PostfixTreeBuilder {
    public static ExpressionNode build(List<String> postfix) {

        Deque<ExpressionNode> stack = new ArrayDeque<>();

        for (String token : postfix) {

            // If token is a number or variable
            if (isOperand(token)) {

                ExpressionNode node =
                        new ExpressionNode(token);

                stack.push(node);
            }

            // If token is an operator
            else if (isOperator(token)) {

                // Check whether two operands are available
                if (stack.size() < 2) {
                    throw new IllegalArgumentException(
                            "Invalid postfix expression"
                    );
                }

                // First pop: right child
                ExpressionNode right = stack.pop();

                // Second pop: left child
                ExpressionNode left = stack.pop();

                // Create operator node
                ExpressionNode operatorNode =
                        new ExpressionNode(token, left, right);

                // Push the new subtree
                stack.push(operatorNode);
            }

            else {
                throw new IllegalArgumentException(
                        "Invalid postfix token: " + token
                );
            }
        }

        // A valid expression must produce exactly one root
        if (stack.size() != 1) {
            throw new IllegalArgumentException(
                    "Invalid postfix expression"
            );
        }

        return stack.pop();
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
}
