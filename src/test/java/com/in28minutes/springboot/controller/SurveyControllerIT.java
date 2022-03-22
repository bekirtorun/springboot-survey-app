package com.in28minutes.springboot.controller;

import com.in28minutes.springboot.Application;
import com.in28minutes.springboot.model.Question;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SurveyControllerIT {


    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

   static HttpHeaders headers = new HttpHeaders();

    @BeforeAll
   static public void before() {

        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

    }

    @Test
    public void testRetrieveSurveyQuestion() throws JSONException {

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/surveys/Survey1/questions/Question1"),
                HttpMethod.GET, entity, String.class);

        String expected = "{id:'Question1',description:'Largest Country in the World',correctAnswer:'Russia'}";

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void retrieveAllSurveyQuestions() throws Exception {

        ResponseEntity<List<Question>> response = restTemplate.exchange(
                createURLWithPort("/surveys/Survey1/questions"),
                HttpMethod.GET, new HttpEntity<String>("DUMMY_DOESNT_MATTER",
                        headers),
                new ParameterizedTypeReference<List<Question>>() {
                });

        Question sampleQuestion = new Question("Question1",
                "Largest Country in the World", "Russia", Arrays.asList(
                "India", "Russia", "United States", "China"));

        assertTrue(response.getBody().contains(sampleQuestion));
    }

    @Test
    public void addQuestion() {

        Question question = new Question("DOESNTMATTER", "Question1", "Russia",
                Arrays.asList("India", "Russia", "United States", "China"));

        HttpEntity entity = new HttpEntity<Question>(question, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/surveys/Survey1/questions"),
                HttpMethod.POST, entity, String.class);

        String actual = response.getHeaders().get(HttpHeaders.LOCATION).get(0);

        assertTrue(actual.contains("/surveys/Survey1/questions/"));

    }

    private String createURLWithPort(final String uri) {
        return "http://localhost:" + port + uri;
    }
}
