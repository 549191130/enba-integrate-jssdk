package com.enba.integrate.jssdk.manage;

import com.alibaba.fastjson.JSONObject;
import com.enba.integrate.jssdk.dto.AccessTokenDTO;
import com.enba.integrate.jssdk.dto.GetTicketDTO;
import com.enba.integrate.jssdk.dto.InitJsapiSignatureDTO;
import com.enba.integrate.jssdk.enums.WxMpApiUrl.GetTokenEnum;
import com.enba.integrate.jssdk.properties.EnbaMpProperties;
import com.enba.integrate.jssdk.utils.SignUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WechatApiAdapter {

  private static final Logger log = org.slf4j.LoggerFactory.getLogger(WechatApiAdapter.class);

  private final RestTemplate restTemplate;

  private final EnbaMpProperties enbaMpProperties;

  public WechatApiAdapter(RestTemplate restTemplate, EnbaMpProperties enbaMpProperties) {
    this.restTemplate = restTemplate;
    this.enbaMpProperties = enbaMpProperties;
  }

  /**
   * 获取access_token（有效期7200秒，开发者必须在自己的服务全局缓存access_token）
   *
   * @return accessTokenDTO
   */
  public AccessTokenDTO getAccessToken() {

    String ret =
        restTemplate.getForObject(
            GetTokenEnum.GET_ACCESS_TOKEN.formatUrl(
                enbaMpProperties.getAppId(), enbaMpProperties.getAppSecret()),
            String.class);

    log.info("getAccessToken ret:{}", ret);

    // {"access_token":"84_KOZta4Dunr25RHxRbFLR8DlVUEuonpn8qd2YQUZ33pFxNayB1Fn5BEmBBNZ5CbG0wqIbCKblBU74cT0twAa4lyXl6jFOSLD_DbXkQQmfUvVmKX4WEb-bg3QHCTAPNJbAFAFDZ","expires_in":7200}

    /*
        FIXME 待完善，根据官方文档可知，获取access_token接口每天有频率限制，所以每次获取到token之后应该进行缓存，如redis中，并定期刷新。
    */

    return JSONObject.parseObject(ret, AccessTokenDTO.class);
  }

  /**
   * 获得jsapi_ticket（有效期7200秒，开发者必须在自己的服务全局缓存jsapi_ticket）
   *
   * @return getTicketDTO
   */
  public GetTicketDTO getJsapiTicket() {

    AccessTokenDTO accessToken = getAccessToken();

    // 请求
    String ret =
        restTemplate.getForObject(
            GetTokenEnum.GET_JSAPI_TICKET.formatUrl(accessToken.getAccess_token()), String.class);

    log.info("getJsapiTicket ret:{}", ret);

    // {"errcode":0,"errmsg":"ok","ticket":"bxLdikRXVbTPdHSM05e5u5sUoZ_Mh9Q4pr7MUo9q8VtQ","expires_in":7200}

    return JSONObject.parseObject(ret, GetTicketDTO.class);
  }

  /**
   * 通过config接口注入权限验证配置
   *
   * @param url 当前网页的URL，不包含#及其后面部分
   * @return r
   */
  public InitJsapiSignatureDTO getJsapiSignature(String url) {

    GetTicketDTO jsapiTicket = getJsapiTicket();

    InitJsapiSignatureDTO sign = SignUtil
        .sign(jsapiTicket.getTicket(), url, enbaMpProperties.getAppId());

    log.info("getJsapiSignature sign:{}", sign);

    return sign;
  }
}
