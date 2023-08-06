package com.zerobase.cms.order.client.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChangeBalanceForm {

  private String form;
  private String message;
  private Integer money;
}
