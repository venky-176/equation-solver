package com.Venkat.equationsolver.service;

import com.Venkat.equationsolver.repository.EquationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

//import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

class EquationServiceTest {

    private EquationService equationService;

    @BeforeEach
    void setUp() {
        EquationRepository repository = new EquationRepository();
        equationService = new EquationService(repository);
    }

    @Test
    void shouldStoreEquationSuccessfully() {

        String id = equationService.storeEquation("3x + 2y - z");

        assertNotNull(id);
        assertEquals("1", id);
    }

    @Test
    void shouldRetrieveStoredEquations() {

        equationService.storeEquation("3x + 2y - z");

        var equations = equationService.getAllEquations();

        assertEquals(1, equations.size());
        assertEquals("1", equations.get(0).getEquationId());
    }

    @Test
    void shouldEvaluateEquationCorrectly() {

        String id = equationService.storeEquation("3x + 2y - z");

        double result = equationService.evaluateEquation(
                id,
                Map.of("x", 2.0, "y", 3.0, "z", 1.0)
        );

        assertEquals(11.0, result, 0.0001);
    }

    @Test
    void shouldRejectMissingVariable() {

        String id = equationService.storeEquation("3x + 2y - z");

        assertThrows(
                IllegalArgumentException.class,
                () -> equationService.evaluateEquation(
                        id,
                        Map.of("x", 2.0, "y", 3.0)
                )
        );
    }

    @Test
    void shouldRejectInvalidEquationId() {

        assertThrows(
                EquationNotFoundException.class,
                () -> equationService.evaluateEquation(
                        "999",
                        Map.of("x", 2.0)
                )
        );
    }

    @Test
    void shouldRejectDivisionByZero() {

        String id = equationService.storeEquation("10 / x");

        assertThrows(
                ArithmeticException.class,
                () -> equationService.evaluateEquation(
                        id,
                        Map.of("x", 0.0)
                )
        );
    }
}