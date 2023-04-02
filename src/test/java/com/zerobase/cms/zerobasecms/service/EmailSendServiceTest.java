package com.zerobase.cms.zerobasecms.service;

import static org.junit.jupiter.api.Assertions.*;

import com.zerobase.cms.zerobasecms.client.MailgunClient;
import com.zerobase.cms.zerobasecms.client.mailgun.SendMailForm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class EmailSendServiceTest {

    @Autowired
    private MailgunClient mailgunClient;

    @Test
    void sendEmail() {
        SendMailForm form = SendMailForm.builder()
            .from("sjdlrnen-test@naver.com")
            .to("sjdlrnen0507@naver.com")
            .subject("test1")
            .text("text1")
            .build();

        ResponseEntity response = mailgunClient.sendEmail(form);
        assertTrue(response.getBody().toString().contains("Thank you."));
    }
}