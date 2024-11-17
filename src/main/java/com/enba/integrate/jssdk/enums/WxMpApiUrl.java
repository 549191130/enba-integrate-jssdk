package com.enba.integrate.jssdk.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** 微信公众号API接口地址 */
public interface WxMpApiUrl {

  @Getter
  @AllArgsConstructor
  enum GetTokenEnum {
    /** 获取 Access token */
    GET_ACCESS_TOKEN(
        "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s",
        "https://developers.weixin.qq.com/doc/offiaccount/Basic_Information/Get_access_token.html"),

    /** 获得jsapi_ticket */
    GET_JSAPI_TICKET(
        "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi",
        "https://developers.weixin.qq.com/doc/offiaccount/OA_Web_Apps/JS-SDK.html");

    /** 接口地址 */
    private final String url;

    /** 官方文档地址 */
    private final String docAddress;

    public String formatUrl(String... params) {
      return String.format(url, params);
    }
  }
}
