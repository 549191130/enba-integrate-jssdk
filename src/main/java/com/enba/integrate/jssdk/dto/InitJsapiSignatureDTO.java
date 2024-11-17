package com.enba.integrate.jssdk.dto;

import lombok.Data;

@Data
public class InitJsapiSignatureDTO {

  /** 微信公众号appId */
  private String appId;

  /** 时间戳 */
  private String timestamp;

  /** 随机字符串 */
  private String nonceStr;

  /** 签名 */
  private String signature;
}
