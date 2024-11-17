package com.enba.integrate.jssdk.controller;

import com.enba.integrate.jssdk.properties.EnbaMpProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

/**
 * @author: enba
 * @description: 恩爸整合JSSDK
 */
@RequestMapping(value = "/enba-jssdk")
@Controller
@Slf4j
public class IndexController {

  private final RestTemplate restTemplate;

  private final EnbaMpProperties enbaMpProperties;

  public IndexController(RestTemplate restTemplate, EnbaMpProperties enbaMpProperties) {
    this.restTemplate = restTemplate;
    this.enbaMpProperties = enbaMpProperties;
  }

  @GetMapping("/index")
  public String show(Model model) {

    return "index";
  }
}
