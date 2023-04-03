package com.zerobase.cms.zerobasecms.controller;

import com.zerobase.cms.zerobasecms.application.SignUpApplication;
import com.zerobase.cms.zerobasecms.domain.PostSignUp;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seller")
@Validated
public class SellerController {
    private final SignUpApplication signUpApplication;

    @PostMapping
    public ResponseEntity<String> customerSignUp(@RequestBody @Valid PostSignUp.PostSignUpRequest postSignUpRequest) {
        return ResponseEntity.ok(signUpApplication.customerSignUp(postSignUpRequest));

    }

    @GetMapping("/verify/customer")
    public ResponseEntity<String>  verifyCustomer(@RequestParam("email") @Email @NotBlank String email, @RequestParam("code") @NotBlank String code){
        return ResponseEntity.ok(signUpApplication.customerVerify(email, code));
    }
}
