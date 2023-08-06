package com.zerobase.cms.zerobasecms.client.mailgun;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SendMailForm {
    private String from;
    private String to;
    private String subject;
    private String text;
}
