package com.zerobase.cms.zerobasecms.service;

import com.zerobase.cms.zerobasecms.domain.model.Customer;
import com.zerobase.cms.zerobasecms.domain.repository.CustomerRepository;
import com.zerobase.cms.zerobasecms.exception.CustomException;
import com.zerobase.cms.zerobasecms.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public Customer findValidCustomerCustomer(String email, String password){
        Customer customer = customerRepository.findByEmail(email)
            .orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND_USER));

        if (!customer.isVerify()){
            throw new CustomException(ErrorCode.NOT_YET_VERIFICATION);
        } else if (!customer.getPassword().equals(password)){
            throw new CustomException(ErrorCode.WRONG_PASSWORD);
        }

        return customer;
    }
}
