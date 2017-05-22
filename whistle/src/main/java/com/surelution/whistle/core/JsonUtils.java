package com.surelution.whistle.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * Created by <a href="mailto:guangzong.xu@tqmall.com">Guangzong</a> on 2017/5/11.
 */
public class JsonUtils {
    public static Map<String, String> toMap(String jsonContent) {

        Map<String, String> map = JSON.parseObject(jsonContent,
                new TypeReference<Map<String, String>>(String.class, String.class) {
                });
        return map;
    }
}
