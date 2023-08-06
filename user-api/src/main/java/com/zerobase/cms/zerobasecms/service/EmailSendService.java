package com.zerobase.cms.zerobasecms.service;

import com.zerobase.cms.zerobasecms.client.MailgunClient;
import com.zerobase.cms.zerobasecms.client.mailgun.SendMailForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSendService {
    private final MailgunClient mailgunClient;

    public String sendEmail(String toMail, String title, String message){
        SendMailForm form = SendMailForm.builder()
            .from("sjdlrnen-test@naver.com")
            .to(toMail)
            .subject(title)
            .text(message)
            .build();

        return mailgunClient.sendEmail(form).getBody();
    }
}
