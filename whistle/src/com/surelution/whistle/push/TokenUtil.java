package com.surelution.whistle.push;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class TokenUtil {

    public static String getToken(String appId, String appSecret) throws IOException {
        String u = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=";
        u += appId;
        u += "&secret=";
        u += appSecret;
        URL url = new URL(u);
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        return "";
    }
}
