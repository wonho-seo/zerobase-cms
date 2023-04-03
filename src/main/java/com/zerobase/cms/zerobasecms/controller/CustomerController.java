package com.zerobase.cms.zerobasecms.controller;

import com.zerobase.cms.config.JwtAuthenticationProvider;
import com.zerobase.cms.domain.common.UserVo;
import com.zerobase.cms.zerobasecms.domain.customer.CustomerDto;
import com.zerobase.cms.zerobasecms.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final CustomerService customerService;

    @GetMapping("/getInfo")
    public ResponseEntity<CustomerDto> getInfo(@RequestHeader(name = "X-AUTH-TOKEN") String token) {
        UserVo userVo = jwtAuthenticationProvider.getUserVo(token);

        return ResponseEntity.ok(CustomerDto.from(
            customerService.findByIdAndEmail(userVo.getId(),
                userVo.getEmail())
        ));
    }
}
