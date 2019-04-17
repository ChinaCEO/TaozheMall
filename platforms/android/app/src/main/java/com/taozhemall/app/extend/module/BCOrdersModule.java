package com.taozhemall.app.extend.module;

import android.content.Intent;

import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.common.WXModule;
import com.taozhemall.app.AlibcJumpToItemActivity;

public class BCOrdersModule extends WXModule {

    @JSMethod(uiThread = false)
    public void showCart() {
        Intent intent = new Intent(mWXSDKInstance.getContext(), AlibcJumpToItemActivity.class);
        intent.putExtra("showOrder", "showCart");
        mWXSDKInstance.getContext().startActivity(intent);
    }

    @JSMethod(uiThread = false)
    public void showOrders() {
        Intent intent = new Intent(mWXSDKInstance.getContext(), AlibcJumpToItemActivity.class);
        intent.putExtra("showOrder", "showOrders");
        mWXSDKInstance.getContext().startActivity(intent);
    }
}
