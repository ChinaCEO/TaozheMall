package com.taozhemall.app.extend.module;

import android.support.annotation.Nullable;

import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;
import com.taozhemall.app.util.CacheOS;

public class CacheModule extends WXModule {

    @JSMethod(uiThread = false)
    public void getCacheSize(@Nullable final JSCallback callback) throws Exception{
        callback.invoke(CacheOS.getTotalCacheSize(mWXSDKInstance.getContext()));
    }

    @JSMethod(uiThread = false)
    public void clearCache(@Nullable final JSCallback callback) throws Exception{
        CacheOS.clearAllCache(mWXSDKInstance.getContext());
    }

}
