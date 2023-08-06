package com.zerobase.cms.zerobasecms.controller;

import com.zerobase.cms.config.JwtAuthenticationProvider;
import com.zerobase.cms.zerobasecms.client.mailgun.SendMailForm;
import com.zerobase.cms.zerobasecms.service.EmailSendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class MailOrderController {

  private final JwtAuthenticationProvider provider;
  private final EmailSendService emailSendService;

  @PostMapping
  public ResponseEntity<String> sendMail(@RequestHeader(name = "X-Auth-Token") String token,
      @RequestBody SendMailForm form) {
    return ResponseEntity.ok(
        emailSendService.sendEmail(form.getTo(), form.getSubject(), form.getText()));
  }
}
