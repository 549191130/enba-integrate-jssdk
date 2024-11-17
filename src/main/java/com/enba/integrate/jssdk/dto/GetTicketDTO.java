package com.enba.integrate.jssdk.dto;

import lombok.Data;

@Data
public class GetTicketDTO {

  /** 错误码 */
  private Integer errcode;

  /** 错误信息 */
  private String errmsg;

  /** 微信JS接口的临时票据 */
  private String ticket;

  /** 过期时间 */
  private Long expires_in;
}
