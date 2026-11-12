
package com.Venkat.equationsolver.controller;

import com.Venkat.equationsolver.service.EquationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class EquationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldRetrieveAllEquations() throws Exception {

        mockMvc.perform(get("/api/equations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.equations").isArray());
    }

    @Test
    void shouldEvaluateEquationSuccessfully() throws Exception {

        String storeRequest = """
            {
                "equation": "3x + 2y - z"
            }
            """;

        String response = mockMvc.perform(post("/api/equations/store")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(storeRequest))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String equationId = com.fasterxml.jackson.databind.json.JsonMapper
                .builder()
                .build()
                .readTree(response)
                .get("equationId")
                .asText();

        String evaluateRequest = """
            {
                "variables": {
                    "x": 2,
                    "y": 3,
                    "z": 1
                }
            }
            """;

        mockMvc.perform(post("/api/equations/" + equationId + "/evaluate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(evaluateRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(11.0));
    }

    @Test
    void shouldReturn404ForInvalidEquationId() throws Exception {

        String request = """
            {
                "variables": {
                    "x": 2
                }
            }
            """;

        mockMvc.perform(post("/api/equations/999/evaluate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn400ForMissingVariable() throws Exception {

        String storeRequest = """
            {
                "equation": "3x + 2y - z"
            }
            """;

        String response = mockMvc.perform(post("/api/equations/store")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(storeRequest))
                .andReturn()
                .getResponse()
                .getContentAsString();

        String equationId = com.fasterxml.jackson.databind.json.JsonMapper
                .builder()
                .build()
                .readTree(response)
                .get("equationId")
                .asText();

        String evaluateRequest = """
            {
                "variables": {
                    "x": 2,
                    "y": 3
                }
            }
            """;

        mockMvc.perform(post("/api/equations/" + equationId + "/evaluate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(evaluateRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldStoreEquationSuccessfully() throws Exception {

        String request = """
                {
                    "equation": "3x + 2y - z"
                }
                """;

        mockMvc.perform(post("/api/equations/store")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message")
                        .value("Equation stored successfully"))
                .andExpect(jsonPath("$.equationId").exists());
    }
}
