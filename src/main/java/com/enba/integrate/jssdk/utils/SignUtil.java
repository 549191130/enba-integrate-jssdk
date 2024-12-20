package com.enba.integrate.jssdk.utils;

import com.enba.integrate.jssdk.dto.InitJsapiSignatureDTO;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SignUtil {
  public static void main(String[] args) {
    String jsapi_ticket = "jsapi_ticket";

    // 注意 URL 一定要动态获取，不能 hardcode
    String url = "https://example.com";
    InitJsapiSignatureDTO ret = sign(jsapi_ticket, url, "appId");
    System.out.println(ret);
  }

  public static InitJsapiSignatureDTO sign(String jsapi_ticket, String url, String appId) {
    Map<String, String> ret = new HashMap<>();
    String nonce_str = create_nonce_str();
    String timestamp = create_timestamp();
    String string1;
    String signature = "";

    // 注意这里参数名必须全部小写，且必须有序
    string1 =
        "jsapi_ticket="
            + jsapi_ticket
            + "&noncestr="
            + nonce_str
            + "&timestamp="
            + timestamp
            + "&url="
            + url;
    System.out.println(string1);

    try {
      MessageDigest crypt = MessageDigest.getInstance("SHA-1");
      crypt.reset();
      crypt.update(string1.getBytes(StandardCharsets.UTF_8));
      signature = byteToHex(crypt.digest());
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }

    ret.put("appId", appId);
    ret.put("timestamp", timestamp);
    ret.put("nonceStr", nonce_str);
    ret.put("signature", signature);

    InitJsapiSignatureDTO dto = new InitJsapiSignatureDTO();
    dto.setAppId(appId);
    dto.setTimestamp(timestamp);
    dto.setNonceStr(nonce_str);
    dto.setSignature(signature);

    return dto;
  }

  private static String byteToHex(final byte[] hash) {
    Formatter formatter = new Formatter();
    for (byte b : hash) {
      formatter.format("%02x", b);
    }
    String result = formatter.toString();
    formatter.close();
    return result;
  }

  private static String create_nonce_str() {
    return UUID.randomUUID().toString();
  }

  private static String create_timestamp() {
    return Long.toString(System.currentTimeMillis() / 1000);
  }
}
