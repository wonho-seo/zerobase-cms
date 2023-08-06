package com.zerobase.cms.zerobasecms.service;

import static com.zerobase.cms.zerobasecms.exception.ErrorCode.*;

import com.zerobase.cms.zerobasecms.domain.PostSignUp.PostSignUpRequest;
import com.zerobase.cms.zerobasecms.domain.model.Customer;
import com.zerobase.cms.zerobasecms.domain.repository.CustomerRepository;
import com.zerobase.cms.zerobasecms.exception.CustomException;
import com.zerobase.cms.zerobasecms.exception.ErrorCode;
import java.time.LocalDateTime;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SignUpCustomerService {

    private final CustomerRepository customerRepository;

    public Customer signUp(PostSignUpRequest form) {
        return customerRepository.save(Customer.form(form));
    }

    public boolean isEmailExist(String email) {
        return customerRepository.findByEmail(email.toLowerCase(Locale.ROOT)).isPresent();

    }

    @Transactional
    public void verifyEmail(String email, String code){
        Customer customer = customerRepository.findByEmail(email)
            .orElseThrow(() -> new CustomException(NOT_FOUND_USER));

        if(customer.isVerify()){
            throw new CustomException(ALREADY_VERIFY);
        } else if (!customer.getVerificationCode().equals(code)){
            throw new CustomException(ErrorCode.WRONG_VERIFICATION);
        } else if (customer.getVerifyExpiredAt().isBefore(LocalDateTime.now())){
            throw new CustomException(ErrorCode.EXPIRE_CODE);
        }
        customer.setVerify(true);
    }

    @Transactional
    public LocalDateTime ChangeCustomerValidateEmail(Long customerId, String verificationCode) {
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new CustomException(NOT_FOUND_USER));

        customer.setVerificationCode(verificationCode);
        customer.setVerifyExpiredAt(LocalDateTime.now().plusDays(1));

        return customer.getVerifyExpiredAt();
    }
}
