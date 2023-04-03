package com.zerobase.cms.zerobasecms.domain.customer;

import com.zerobase.cms.zerobasecms.domain.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Getter
@Service
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    private Long id;
    private String email;
    private Integer balance;

    public static CustomerDto from(Customer customer) {
        return new CustomerDto(customer.getId(), customer.getEmail(),
            customer.getBalance() == null ? 0 : customer.getBalance());
    }

}
