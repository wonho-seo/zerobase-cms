package com.zerobase.cms.zerobasecms.controller;

import com.zerobase.cms.zerobasecms.domain.PostSignInCustomer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/signIn")
@RequiredArgsConstructor
public class SignInController {

    @PostMapping("/customer")
    public ResponseEntity<String> signInCustomer(@RequestBody @Validated PostSignInCustomer.Request request){

    }
}
