package com.Venkat.equationsolver.util;

import com.Venkat.equationsolver.model.ExpressionNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PostfixTreeBuilderTest {

    @Test
    void shouldBuildSimpleExpressionTree() {

        List<String> postfix =
                List.of("a", "b", "+");

        ExpressionNode root =
                PostfixTreeBuilder.build(postfix);

        assertEquals("+", root.getValue());
        assertEquals("a", root.getLeft().getValue());
        assertEquals("b", root.getRight().getValue());
    }

    @Test
    void shouldBuildComplexExpressionTree() {

        List<String> postfix =
                List.of("a", "b", "c", "*", "+");

        ExpressionNode root =
                PostfixTreeBuilder.build(postfix);

        assertEquals("+", root.getValue());
        assertEquals("a", root.getLeft().getValue());
        assertEquals("*", root.getRight().getValue());

        assertEquals(
                "b",
                root.getRight().getLeft().getValue()
        );

        assertEquals(
                "c",
                root.getRight().getRight().getValue()
        );
    }

    @Test
    void shouldRejectInvalidPostfixExpression() {

        List<String> postfix =
                List.of("a", "b", "+", "*");

        assertThrows(
                IllegalArgumentException.class,
                () -> PostfixTreeBuilder.build(postfix)
        );
    }

    @Test
    void shouldRejectMultipleRemainingNodes() {

        List<String> postfix =
                List.of("a", "b", "c", "+");

        assertThrows(
                IllegalArgumentException.class,
                () -> PostfixTreeBuilder.build(postfix)
        );
    }

}
