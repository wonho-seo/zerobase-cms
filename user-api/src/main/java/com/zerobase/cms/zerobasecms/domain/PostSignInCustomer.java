package com.zerobase.cms.zerobasecms.domain;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

public class PostSignInCustomer {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request{
        @Email
        @NotBlank(message = "이메일은 필수 입력 값입니다.")
        private String email;
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,20}$",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 각각 1개 이상 포함된 8 ~ 20자리 입니다.")
        private String password;
    }

}
