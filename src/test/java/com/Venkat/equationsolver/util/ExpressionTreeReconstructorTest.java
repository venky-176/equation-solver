
package com.Venkat.equationsolver.util;

import com.Venkat.equationsolver.model.ExpressionNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExpressionTreeReconstructorTest {

    @Test
    void shouldReconstructSimpleExpression() {

        ExpressionNode root =
                new ExpressionNode(
                        "+",
                        new ExpressionNode("a"),
                        new ExpressionNode("b")
                );

        String result =
                ExpressionTreeReconstructor.reconstruct(root);

        assertEquals("(a + b)", result);
    }

    @Test
    void shouldReconstructComplexExpression() {

        ExpressionNode multiplication =
                new ExpressionNode(
                        "*",
                        new ExpressionNode("b"),
                        new ExpressionNode("c")
                );

        ExpressionNode root =
                new ExpressionNode(
                        "+",
                        new ExpressionNode("a"),
                        multiplication
                );

        String result =
                ExpressionTreeReconstructor.reconstruct(root);

        assertEquals("(a + (b * c))", result);
    }

    @Test
    void shouldReconstructSingleOperand() {

        ExpressionNode root =
                new ExpressionNode("x");

        assertEquals(
                "x",
                ExpressionTreeReconstructor.reconstruct(root)
        );
    }

    @Test
    void shouldRejectNullTree() {

        assertThrows(
                IllegalArgumentException.class,
                () -> ExpressionTreeReconstructor.reconstruct(null)
        );
    }
}
