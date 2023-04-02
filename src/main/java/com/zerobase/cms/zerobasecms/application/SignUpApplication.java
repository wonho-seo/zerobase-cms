package com.zerobase.cms.zerobasecms.application;

import com.zerobase.cms.zerobasecms.domain.PostSignUp.PostSignUpRequest;
import com.zerobase.cms.zerobasecms.domain.model.Customer;
import com.zerobase.cms.zerobasecms.exception.CustomException;
import com.zerobase.cms.zerobasecms.exception.ErrorCode;
import com.zerobase.cms.zerobasecms.service.EmailSendService;
import com.zerobase.cms.zerobasecms.service.SignUpCustomerService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpApplication {

    private final SignUpCustomerService signUpCustomerService;
    private final EmailSendService emailSendService;

    public String customerSignUp(PostSignUpRequest form) {
        if(signUpCustomerService.isEmailExist(form.getEmail())){
            throw new CustomException(ErrorCode.ALREADY_REGISTER_ACCOUNT);
        }else {
            Customer customer = signUpCustomerService.signUp(form);
            LocalDateTime now = LocalDateTime.now();

            String code = getRandomCode();
            emailSendService.sendEmail(form.getEmail(), "Verification Email",
                getVerificationEmailBody(form.getEmail(), form.getName(), code));

            return "회원 가입에 성공했습니다.";
        }
    }

    private String getRandomCode(){
        return RandomStringUtils.random(10, true, true);
    }

    private String getVerificationEmailBody(String email, String name, String code){
        StringBuilder sb = new StringBuilder();
        return sb.append("Hello ").append(name).append("! Please Click Link for verification. \n\n")
            .append("http://localhost:8080/customer/signup/verify?email=")
            .append(email)
            .append("&code=")
            .append(code).toString();
    }
}
