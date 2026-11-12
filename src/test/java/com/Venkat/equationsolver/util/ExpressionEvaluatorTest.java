
package com.Venkat.equationsolver.util;

import com.Venkat.equationsolver.model.ExpressionNode;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ExpressionEvaluatorTest {

    @Test
    void shouldEvaluateAddition() {

        ExpressionNode root =
                new ExpressionNode(
                        "+",
                        new ExpressionNode("10"),
                        new ExpressionNode("20")
                );

        double result = ExpressionEvaluator.evaluate(
                root, Map.of()
        );

        assertEquals(30.0, result);
    }

    @Test
    void shouldEvaluateVariables() {

        List<String> postfix =
                Tokenizer.tokenize("a + b * c");

        postfix = InfixToPostfix.convert(postfix);

        ExpressionNode root =
                PostfixTreeBuilder.build(postfix);

        double result = ExpressionEvaluator.evaluate(
                root,
                Map.of("a", 10.0, "b", 5.0, "c", 2.0)
        );

        assertEquals(20.0, result);
    }

    @Test
    void shouldEvaluateExponentiation() {

        ExpressionNode root =
                new ExpressionNode(
                        "^",
                        new ExpressionNode("2"),
                        new ExpressionNode("3")
                );

        assertEquals(
                8.0,
                ExpressionEvaluator.evaluate(root, Map.of())
        );
    }

    @Test
    void shouldRejectMissingVariable() {

        ExpressionNode root =
                new ExpressionNode("x");

        assertThrows(
                IllegalArgumentException.class,
                () -> ExpressionEvaluator.evaluate(root, Map.of())
        );
    }

    @Test
    void shouldRejectDivisionByZero() {

        ExpressionNode root =
                new ExpressionNode(
                        "/",
                        new ExpressionNode("10"),
                        new ExpressionNode("0")
                );

        assertThrows(
                ArithmeticException.class,
                () -> ExpressionEvaluator.evaluate(root, Map.of())
        );
    }
}
