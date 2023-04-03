package com.zerobase.cms.zerobasecms.controller;

import com.zerobase.cms.config.JwtAuthenticationProvider;
import com.zerobase.cms.domain.common.UserVo;
import com.zerobase.cms.zerobasecms.domain.customer.ChangeBalanceForm;
import com.zerobase.cms.zerobasecms.domain.customer.CustomerDto;
import com.zerobase.cms.zerobasecms.service.CustomerBalanceService;
import com.zerobase.cms.zerobasecms.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final CustomerService customerService;
    private final CustomerBalanceService customerBalanceService;
    @GetMapping("/getInfo")
    public ResponseEntity<CustomerDto> getInfo(@RequestHeader(name = "X-AUTH-TOKEN") String token) {
        UserVo userVo = jwtAuthenticationProvider.getUserVo(token);

        return ResponseEntity.ok(CustomerDto.from(
            customerService.findByIdAndEmail(userVo.getId(),
                userVo.getEmail())
        ));
    }

    @PostMapping("/balance")
    public ResponseEntity<Integer> changeBalance(@RequestHeader(name = "X-AUTH-TOKEN") String token,
        @RequestBody ChangeBalanceForm form){
        UserVo userVo = jwtAuthenticationProvider.getUserVo(token);
        return ResponseEntity.ok(customerBalanceService.changeBalance(userVo.getId(), form).getChangeMoney());
    }
}
