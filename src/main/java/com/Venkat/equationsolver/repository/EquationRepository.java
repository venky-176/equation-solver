
package com.Venkat.equationsolver.repository;

import com.Venkat.equationsolver.model.Equation;

import com.Venkat.equationsolver.model.ExpressionNode;
import org.springframework.stereotype.Repository;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Repository
public class EquationRepository {

    private final Map<String, Equation> equations =
            new ConcurrentHashMap<>();

    private final Map<String, ExpressionNode> expressionTrees =
            new ConcurrentHashMap<>();

    public void save(String id, String expression, ExpressionNode root) {
        equations.put(id, new Equation(id, expression));
        expressionTrees.put(id, root);
    }

    public Equation findById(String id) {
        return equations.get(id);
    }

    public ExpressionNode findTreeById(String id) {
        return expressionTrees.get(id);
    }

    public Map<String, Equation> findAll() {
        return equations;
    }
}
