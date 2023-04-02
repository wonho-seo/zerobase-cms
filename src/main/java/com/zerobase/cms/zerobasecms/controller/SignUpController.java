package com.zerobase.cms.zerobasecms.controller;

import com.zerobase.cms.zerobasecms.application.SignUpApplication;
import com.zerobase.cms.zerobasecms.domain.PostSignUp.PostSignUpRequest;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/signup")
@Validated
public class SignUpController {

    private final SignUpApplication signUpApplication;

    @PostMapping
    public ResponseEntity<String> customerSignUp(@RequestBody @Valid PostSignUpRequest postSignUpRequest) {
        return ResponseEntity.ok(signUpApplication.customerSignUp(postSignUpRequest));

    }

    @PutMapping("/verify/customer")
    public ResponseEntity<String>  verifyCustomer(@RequestParam("email") @Email @NotBlank String email, @RequestParam("code") @NotBlank String code){
        return ResponseEntity.ok(signUpApplication.customerVerify(email, code));
    }
}
