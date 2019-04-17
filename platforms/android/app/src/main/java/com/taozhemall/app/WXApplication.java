package com.taozhemall.app;

import android.app.Application;

//import com.taozhemall.app.extend.ImageAdapter;
import com.alibaba.android.bindingx.plugin.weex.BindingX;
import com.alibaba.android.bindingx.plugin.weex.WXBindingXModule;
import com.alibaba.android.bindingx.plugin.weex.WXExpressionBindingModule;
import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeInitCallback;
import com.taozhemall.app.extend.WXEventModule;
import com.alibaba.weex.plugin.loader.WeexPluginContainer;
import com.taozhemall.app.extend.adapter.CommentSDKWeeXImageAdapter;
import com.taozhemall.app.extend.component.WXWebpri;
import com.taozhemall.app.extend.component.WxEditText;
import com.taozhemall.app.extend.module.BCJumpModule;
import com.taozhemall.app.extend.module.BCLoginModule;
import com.taozhemall.app.extend.module.BCOrdersModule;
import com.taozhemall.app.extend.module.CacheModule;
import com.taozhemall.app.extend.module.GetIMEIMoudule;
import com.taozhemall.app.extend.module.NavigatorModulePri;
import com.taozhemall.app.extend.module.WXPositionModule;
import com.taozhemall.app.extend.module.WXWebViewModulePri;
import com.taozhemall.app.util.AppConfig;
import com.taobao.weex.InitConfig;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.common.WXException;

public class WXApplication extends Application {

  @Override
  public void onCreate() {

    super.onCreate();
    //    百川初始化
    AlibcTradeSDK.asyncInit(this, new AlibcTradeInitCallback() {
      @Override
      public void onSuccess() {
        System.out.println("----------------------------baichun Success----------------------------");
      }

      @Override
      public void onFailure(int code, String msg) {
        //初始化失败，可以根据code和msg判断失败原因，详情参见错误说明
        System.out.println("--------------------------"+code + msg+"----------------------------");
      }
    });

    WXSDKEngine.addCustomOptions("appName", "WXSample");
    WXSDKEngine.addCustomOptions("appGroup", "WXApp");

//    WXSDKEngine.initialize(this,
//        new InitConfig.Builder().setImgAdapter(new ImageAdapter()).build()
//    );
    WXSDKEngine.initialize(this,
            new InitConfig.Builder().setImgAdapter(new CommentSDKWeeXImageAdapter()).build()
    );
    try {
      WXSDKEngine.registerModule("event", WXEventModule.class);
    } catch (WXException e) {
      e.printStackTrace();
    }

    try {
      WXSDKEngine.registerModule("position", WXPositionModule.class);
    } catch (WXException e) {
      e.printStackTrace();
    }

    try {
      WXSDKEngine.registerModule("getImei", GetIMEIMoudule.class);
    } catch (WXException e) {
      e.printStackTrace();
    }

    try {
      WXSDKEngine.registerComponent("webpri", WXWebpri.class);
    } catch (WXException e) {
      e.printStackTrace();
    }

    try {
      WXSDKEngine.registerComponent("wxedit", WxEditText.class);
    } catch (WXException e) {
      e.printStackTrace();
    }

    try {
      WXSDKEngine.registerModule("navigator-pri", NavigatorModulePri.class);
      WXSDKEngine.registerModule("webview-pri", WXWebViewModulePri.class);
    } catch (WXException e) {
      e.printStackTrace();
    }


    try {
      WXSDKEngine.registerModule("expressionBinding", WXExpressionBindingModule.class);
      WXSDKEngine.registerModule("binding", WXBindingXModule.class);
      WXSDKEngine.registerModule("bindingx", WXBindingXModule.class);
    } catch (WXException e) {
      e.printStackTrace();
    }

    try {
      WXSDKEngine.registerModule("bcJump", BCJumpModule.class);
    } catch (WXException e) {
      e.printStackTrace();
    }

    try {
      WXSDKEngine.registerModule("login", BCLoginModule.class);
    } catch (WXException e) {
      e.printStackTrace();
    }

    try {
      WXSDKEngine.registerModule("order", BCOrdersModule.class);
    } catch (WXException e) {
      e.printStackTrace();
    }

    try {
      WXSDKEngine.registerModule("cache", CacheModule.class);
    } catch (WXException e) {
      e.printStackTrace();
    }
    
    AppConfig.init(this);
    WeexPluginContainer.loadAll(this);

  }
}
