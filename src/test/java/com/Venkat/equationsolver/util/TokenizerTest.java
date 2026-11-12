package com.Venkat.equationsolver.util;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TokenizerTest {

    @Test
    void shouldTokenizeSimpleExpression() {

        List<String> tokens =
                Tokenizer.tokenize("3x + 2y - z");

        assertEquals(
                List.of("3", "*", "x", "+",
                        "2", "*", "y", "-", "z"),
                tokens
        );
    }

    @Test
    void shouldTokenizeParentheses() {

        List<String> tokens =
                Tokenizer.tokenize("(x + 2) * 3");

        assertEquals(
                List.of("(", "x", "+", "2", ")", "*", "3"),
                tokens
        );
    }

    @Test
    void shouldRejectInvalidCharacters() {

        assertThrows(
                IllegalArgumentException.class,
                () -> Tokenizer.tokenize("x + @")
        );
    }
}
