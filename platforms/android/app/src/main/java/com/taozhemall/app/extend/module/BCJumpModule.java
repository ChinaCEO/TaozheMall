package com.taozhemall.app.extend.module;

import android.support.annotation.Nullable;


import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;
import com.taozhemall.app.util.BaiChuanUtil;
import com.taozhemall.app.util.Network;

public class BCJumpModule extends WXModule {

    @JSMethod(uiThread = true)
    public void jump(String url, @Nullable final JSCallback callback) {
        Network mActivity = new Network();
        BaiChuanUtil.showItemDetailPage(mActivity,"91627500240","25635637",url);
    }
}
