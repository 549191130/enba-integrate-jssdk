package com.enba.integrate.jssdk.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "enba.mp")
@Data
public class EnbaMpProperties {

  /**
   * 用户唯一凭证
   */
  private String appId;

  /**
   * 用户唯一凭证密钥
   */
  private String appSecret;

}
