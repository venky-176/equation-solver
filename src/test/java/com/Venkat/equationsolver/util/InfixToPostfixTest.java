package com.Venkat.equationsolver.util;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InfixToPostfixTest {

    @Test
    void shouldConvertSimpleExpression() {

        List<String> tokens =
                Tokenizer.tokenize("a + b * c - d");

        List<String> result =
                InfixToPostfix.convert(tokens);

        assertEquals(
                List.of("a", "b", "c", "*", "+", "d", "-"),
                result
        );
    }

    @Test
    void shouldRespectParentheses() {

        List<String> tokens =
                Tokenizer.tokenize("(a + b) * c");

        List<String> result =
                InfixToPostfix.convert(tokens);

        assertEquals(
                List.of("a", "b", "+", "c", "*"),
                result
        );
    }

    @Test
    void shouldHandleExponentiation() {

        List<String> tokens =
                Tokenizer.tokenize("a ^ b ^ c");

        List<String> result =
                InfixToPostfix.convert(tokens);

        assertEquals(
                List.of("a", "b", "c", "^", "^"),
                result
        );
    }

    @Test
    void shouldRejectMismatchedParentheses() {

        List<String> tokens =
                Tokenizer.tokenize("(a + b");

        assertThrows(
                IllegalArgumentException.class,
                () -> InfixToPostfix.convert(tokens)
        );
    }

}
