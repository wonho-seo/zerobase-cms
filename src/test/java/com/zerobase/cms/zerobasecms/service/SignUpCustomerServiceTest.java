package com.zerobase.cms.zerobasecms.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.zerobase.cms.zerobasecms.domain.PostSignUp.PostSignUpRequest;
import com.zerobase.cms.zerobasecms.domain.model.Customer;
import com.zerobase.cms.zerobasecms.service.customer.SignUpCustomerService;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SignUpCustomerServiceTest {

    @Autowired
    private SignUpCustomerService service;

    @Test
    void signUp() {
        PostSignUpRequest form = PostSignUpRequest.builder()
            .name("name")
            .birth(LocalDate.now())
            .email("abc@naver.com")
            .password("1")
            .phone("010000000")
            .build();
        Customer c = service.signUp(form);
        assertEquals(c.getName(), "name");
        assertNotNull(c.getBirth());
        assertEquals(c.getEmail(), "abc@naver.com");
        assertEquals(c.getPassword(), "1");
        assertEquals(c.getPhone(), "010000000");
    }
}