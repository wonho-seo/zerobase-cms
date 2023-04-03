package com.zerobase.cms.zerobasecms.application;

import com.zerobase.cms.config.JwtAuthenticationProvider;
import com.zerobase.cms.domain.common.UserType;
import com.zerobase.cms.zerobasecms.domain.model.Customer;
import com.zerobase.cms.zerobasecms.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignInApplication {

    private final CustomerService customerService;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    public String customerLoginToken(String email, String password){
        Customer customer = customerService.findValidCustomerCustomer(email, password);



        return jwtAuthenticationProvider.createToken(customer.getEmail(),customer.getId(), UserType.CUSTOMER);
    }
}
