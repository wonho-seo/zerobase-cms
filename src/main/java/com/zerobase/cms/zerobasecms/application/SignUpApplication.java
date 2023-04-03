package com.zerobase.cms.zerobasecms.application;

import com.zerobase.cms.zerobasecms.domain.PostSignUp.PostSignUpRequest;
import com.zerobase.cms.zerobasecms.domain.model.Customer;
import com.zerobase.cms.zerobasecms.domain.model.Seller;
import com.zerobase.cms.zerobasecms.exception.CustomException;
import com.zerobase.cms.zerobasecms.exception.ErrorCode;
import com.zerobase.cms.zerobasecms.service.EmailSendService;
import com.zerobase.cms.zerobasecms.service.customer.SignUpCustomerService;
import com.zerobase.cms.zerobasecms.service.seller.SellerService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SignUpApplication {

    private final SignUpCustomerService signUpCustomerService;
    private final EmailSendService emailSendService;
    private final SellerService sellerService;

    public String customerVerify(String email, String code) {
        signUpCustomerService.verifyEmail(email, code);
        return "인증이 완료되었습니다.";
    }

    public String sellerVerify(String email, String code) {
        sellerService.verifyEmail(email, code);
        return "인증이 완료되었습니다.";
    }

    @Transactional
    public String customerSignUp(PostSignUpRequest form) {
        if (signUpCustomerService.isEmailExist(form.getEmail())) {
            throw new CustomException(ErrorCode.ALREADY_REGISTER_ACCOUNT);
        } else {
            Customer customer = signUpCustomerService.signUp(form);
            LocalDateTime now = LocalDateTime.now();

            String code = getRandomCode();
//            emailSendService.sendEmail(form.getEmail(), "Verification Email",
//                getVerificationEmailBody(form.getEmail(), form.getName(),"customer", code));
            customer.setVerificationCode(code);
            customer.setVerifyExpiredAt(now.plusDays(1));
            return "회원 가입에 성공했습니다.";
        }
    }

    @Transactional
    public String sellerSignUp(PostSignUpRequest form) {
        if (sellerService.isEmailExist(form.getEmail())) {
            throw new CustomException(ErrorCode.ALREADY_REGISTER_ACCOUNT);
        } else {
            Seller seller = sellerService.signUp(form);
            LocalDateTime now = LocalDateTime.now();

            String code = getRandomCode();
//            emailSendService.sendEmail(form.getEmail(), "Verification Email",
//                getVerificationEmailBody(form.getEmail(), form.getName(),"seller", code));
            seller.setVerificationCode(code);
            seller.setVerifyExpiredAt(now.plusDays(1));
            return "회원 가입에 성공했습니다.";
        }
    }

    private String getRandomCode() {
        return RandomStringUtils.random(10, true, true);
    }

    private String getVerificationEmailBody(String email, String name, String type, String code) {
        StringBuilder sb = new StringBuilder();
        return sb.append("Hello ").append(name).append("! Please Click Link for verification. \n\n")
            .append("http://localhost:8081/signup/")
            .append(type)
            .append("verify/?email=")
            .append(email)
            .append("&code=")
            .append(code).toString();
    }

}
