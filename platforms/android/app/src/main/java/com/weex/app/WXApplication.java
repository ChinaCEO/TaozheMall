package com.weex.app;

import android.app.Application;

//import com.weex.app.extend.ImageAdapter;
import com.alibaba.android.bindingx.plugin.weex.BindingX;
import com.alibaba.android.bindingx.plugin.weex.WXBindingXModule;
import com.alibaba.android.bindingx.plugin.weex.WXExpressionBindingModule;
import com.weex.app.extend.WXEventModule;
import com.alibaba.weex.plugin.loader.WeexPluginContainer;
import com.weex.app.extend.adapter.CommentSDKWeeXImageAdapter;
import com.weex.app.extend.component.WXWebpri;
import com.weex.app.extend.component.WxEditText;
import com.weex.app.extend.module.GetIMEIMoudule;
import com.weex.app.extend.module.NavigatorModulePri;
import com.weex.app.extend.module.WXPositionModule;
import com.weex.app.util.AppConfig;
import com.taobao.weex.InitConfig;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.common.WXException;

public class WXApplication extends Application {

  @Override
  public void onCreate() {

    super.onCreate();
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
    
    AppConfig.init(this);
    WeexPluginContainer.loadAll(this);

  }
}
