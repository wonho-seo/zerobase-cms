package com.zerobase.cms.zerobasecms.application;

import com.zerobase.cms.zerobasecms.domain.model.Customer;
import com.zerobase.cms.zerobasecms.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignInApplication {

    private final CustomerService customerService;

    public String customerLoginToken(String email, String password){
        Customer customer = customerService.findValidCustomerCustomer(email, password);



        return null;
    }
}
