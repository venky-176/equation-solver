package com.Venkat.equationsolver.util;

import java.util.*;

public class Tokenizer {

    public static List<String> tokenize(String expression) {

        List<String> tokens = new ArrayList<>();

        int i = 0;

        while (i < expression.length()) {

            char ch = expression.charAt(i);

            // Ignore whitespace
            if (Character.isWhitespace(ch)) {
                i++;
                continue;
            }

            // Read numbers, including decimal numbers
            if (Character.isDigit(ch) || ch == '.') {

                int start = i;
                boolean hasDecimal = false;

                while (i < expression.length()) {

                    char current = expression.charAt(i);

                    if (Character.isDigit(current)) {
                        i++;
                    } else if (current == '.' && !hasDecimal) {
                        hasDecimal = true;
                        i++;
                    } else {
                        break;
                    }
                }

                String number = expression.substring(start, i);

                if (number.equals(".")) {
                    throw new IllegalArgumentException("Invalid number");
                }

                tokens.add(number);
            }

            // Read variable names
            else if (Character.isLetter(ch) || ch == '_') {

                int start = i;

                while (i < expression.length() &&
                        (Character.isLetterOrDigit(expression.charAt(i))
                                || expression.charAt(i) == '_')) {
                    i++;
                }

                tokens.add(expression.substring(start, i));
            }

            // Read operators and parentheses
            else if ("+-*/^()".indexOf(ch) >= 0) {

                tokens.add(String.valueOf(ch));
                i++;
            }

            // Reject unsupported characters
            else {
                throw new IllegalArgumentException(
                        "Invalid character: " + ch
                );
            }
        }

        return insertImplicitMultiplication(tokens);
    }

    private static List<String> insertImplicitMultiplication(
            List<String> tokens) {

        List<String> result = new ArrayList<>();

        for (int i = 0; i < tokens.size(); i++) {

            String current = tokens.get(i);

            if (!result.isEmpty()) {

                String previous = result.get(result.size() - 1);

                boolean previousIsNumber = previous.matches(
                        "\\d+(\\.\\d+)?"
                );

                boolean currentIsVariable = current.matches(
                        "[a-zA-Z_][a-zA-Z0-9_]*"
                );

                boolean currentIsOpeningBracket =
                        current.equals("(");

                boolean previousIsClosingBracket =
                        previous.equals(")");

                if ((previousIsNumber && currentIsVariable)
                        || currentIsOpeningBracket
                        && (previousIsNumber
                        || previousIsClosingBracket)
                        || previousIsClosingBracket
                        && currentIsVariable) {

                    result.add("*");
                }
            }

            result.add(current);
        }

        return result;
    }
}

