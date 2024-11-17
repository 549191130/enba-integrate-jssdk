package com.enba.integrate.jssdk.controller;

import com.enba.integrate.jssdk.manage.WechatApiAdapter;
import com.enba.integrate.jssdk.response.EnbaResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: enba
 * @description: JSSDK权限验证
 */
@RequestMapping(value = "/enba-jssdk")
@Controller
@Slf4j
public class WechatJssdkInitController {

  private final WechatApiAdapter wechatApiAdapter;

  public WechatJssdkInitController(WechatApiAdapter wechatApiAdapter) {
    this.wechatApiAdapter = wechatApiAdapter;
  }

  /**
   * 测试页面
   *
   * @param model m
   * @return r
   */
  @GetMapping("/demo")
  public String show(Model model) {

    return "demo";
  }

  /**
   * 获取JS-SDK权限验证签名
   *
   * @param url 当前页面的URL，不包含#及其后面部分
   * @return r
   */
  @ResponseBody
  @GetMapping("/jsapi-signature")
  public EnbaResult init(@RequestParam String url) {
    log.info("url: {}", url);

    return EnbaResult.success(wechatApiAdapter.getJsapiSignature(url));

    /*
     * {
     * "success":true,
     * "code":200,
     * "message":"成功",
     * "result":{"appId":"wx232da6f537b9dae3","timestamp":"1727337972","nonceStr":"98236493-91c1-483b-8a72-0aaecac9d8bc","signature":"017042b408be89b206d411972ce695e8d1ad6e0d"}
     * }
     * */
  }
}
