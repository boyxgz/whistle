package com.surelution.whistle.push.ibeacon;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by <a href="mailto:guangzong.xu@tqmall.com">Guangzong</a> on 2017/5/11.
 */
public class ShakeInfoContainer {

    @JSONField
    private ShakeInfo data;

    public ShakeInfo getData() {
        return data;
    }

    public void setData(ShakeInfo data) {
        this.data = data;
    }
}
