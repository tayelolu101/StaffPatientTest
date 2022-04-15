package com.example.hospitalcasestudy.controller;

import com.example.hospitalcasestudy.dto.staff.CreateStaffReqDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
@AutoConfigureMockMvc
public class StaffControllerIt {
    @Autowired
    private MockMvc mockMvc;

    @Value("${app.mock.authorization}")
    private String mockAuthorisation;

    @Test
    public void saveStaff() throws Exception {
        final String sampleStaffName  = "Queen Doe";
        final CreateStaffReqDto createStaffReqDto = new CreateStaffReqDto();
        createStaffReqDto.setName(sampleStaffName);

        mockMvc.perform(post("/staff")
                .content(new ObjectMapper().writeValueAsString(createStaffReqDto))
                .header(HttpHeaders.AUTHORIZATION, mockAuthorisation)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is(sampleStaffName)))
                .andExpect(jsonPath("$.registration_date", notNullValue()))
                .andExpect(jsonPath("$.uuid").doesNotExist());
    }
}
