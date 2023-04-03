package com.zerobase.cms.zerobasecms.service;

import com.zerobase.cms.zerobasecms.domain.customer.ChangeBalanceForm;
import com.zerobase.cms.zerobasecms.domain.model.CustomerBalanceHistory;
import com.zerobase.cms.zerobasecms.domain.repository.CustomerBalanceHistoryRepository;
import com.zerobase.cms.zerobasecms.domain.repository.CustomerRepository;
import com.zerobase.cms.zerobasecms.exception.CustomException;
import com.zerobase.cms.zerobasecms.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerBalanceService {
    private final CustomerBalanceHistoryRepository customerBalanceHistoryRepository;
    private final CustomerRepository customerRepository;

    @Transactional(noRollbackFor = {CustomException.class})
    public CustomerBalanceHistory changeBalance(Long customerId, ChangeBalanceForm form) throws CustomException{
        CustomerBalanceHistory customerBalanceHistory = customerBalanceHistoryRepository.findFirstByCustomer_IdOrderByIdDesc(customerId)
            .orElse(CustomerBalanceHistory.builder()
                .changeMoney(0)
                .currentMoney(0)
                .customer(customerRepository.findById(customerId)
                    .orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND_USER)))
                .build());

        if(customerBalanceHistory.getChangeMoney() + form.getMoney() < 0){
            throw new CustomException(ErrorCode.NOT_ENOUGH_BALANCE);
        }

        customerBalanceHistory = customerBalanceHistory.builder()
            .changeMoney(form.getMoney())
            .currentMoney(customerBalanceHistory.getCurrentMoney() + form.getMoney())
            .description(form.getMessage())
        .fromMessage(form.getMessage())
            .customer(customerBalanceHistory.getCustomer())
            .build();
        customerBalanceHistory.getCustomer().setBalance(customerBalanceHistory.getCurrentMoney());

        return customerBalanceHistory;
    }
}
