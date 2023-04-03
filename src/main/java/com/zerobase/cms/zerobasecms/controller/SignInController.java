package com.zerobase.cms.zerobasecms.controller;

import com.zerobase.cms.zerobasecms.application.SignInApplication;
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

    private final SignInApplication signInApplication;
    @PostMapping("/customer")
    public ResponseEntity<String> signInCustomer(@RequestBody @Validated PostSignInCustomer.Request request){
        return ResponseEntity.ok(signInApplication.customerLoginToken(request.getEmail(), request.getPassword()));
    }
    @PostMapping("/seller")
    public ResponseEntity<String> signInSeller(@RequestBody @Validated PostSignInCustomer.Request request){
        return ResponseEntity.ok(signInApplication.sellerLoginToken(request.getEmail(), request.getPassword()));
    }
}
