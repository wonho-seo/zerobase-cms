package com.zerobase.cms.zerobasecms.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.cms.zerobasecms.application.SignUpApplication;
import com.zerobase.cms.zerobasecms.domain.PostSignUp.PostSignUpRequest;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(SignUpControllerTest.class)
@MockBean(JpaMetamodelMappingContext.class)
class SignUpControllerTest {

    @MockBean
    private SignUpApplication signUpApplication;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void customerSignUp() throws Exception {
        PostSignUpRequest postSignUpRequest = PostSignUpRequest.builder()
            .email("sjdlrnen0507@naver.com")
            .name("wseo")
            .password("QweEArty123!")
            .birth(LocalDate.now())
            .phone("0104187-7580")
            .build();
        given(signUpApplication.customerSignUp(any()))
            .willReturn("성공");

        mockMvc.perform(post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postSignUpRequest)))
            .andExpectAll(
                status().isOk()
            )
            .andDo(print());

    }
}