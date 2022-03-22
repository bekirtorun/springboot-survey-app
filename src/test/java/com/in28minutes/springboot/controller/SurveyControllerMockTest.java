package com.in28minutes.springboot.controller;

import com.in28minutes.springboot.Application;
import com.in28minutes.springboot.service.SurveyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
//@WebMvcTest(SurveyService.class)
//@SpringBootTest(classes = Application.class)
public class SurveyControllerMockTest {

    @MockBean
    private SurveyService surveyService;

    @Test
    public void testMethod(){

    }
}
