
package com.Venkat.equationsolver.util;

import com.Venkat.equationsolver.model.ExpressionNode;

public class ExpressionTreeReconstructor {

    public static String reconstruct(ExpressionNode root) {

        if (root == null) {
            throw new IllegalArgumentException(
                    "Expression tree cannot be empty"
            );
        }

        // Leaf node: number or variable
        if (root.getLeft() == null &&
                root.getRight() == null) {

            return root.getValue();
        }

        // Reconstruct left subtree
        String left = reconstruct(root.getLeft());

        // Reconstruct right subtree
        String right = reconstruct(root.getRight());

        // Combine both subtrees with the operator
        return "(" + left + " "
                + root.getValue() + " "
                + right + ")";
    }
}
