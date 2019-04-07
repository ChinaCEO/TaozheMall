package com.weex.app.extend.module;

import com.taobao.weex.WXSDKManager;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.common.WXModule;
import com.taobao.weex.ui.component.WXComponent;
import com.weex.app.extend.component.WXWebpri;

public class WXWebViewModulePri extends WXModule {
    private enum Action {
        reload,
        goBack,
        goForward,
        postMessage
    }

    @JSMethod(uiThread = true)
    public void goBack(String ref) {
        action(Action.goBack, ref);
    }

    @JSMethod(uiThread = true)
    public void goForward(String ref) {
        action(Action.goForward, ref);
    }

    @JSMethod(uiThread = true)
    public void reload(String ref) {
        action(Action.reload, ref);
    }

    @JSMethod(uiThread = true)
    public void postMessage(String ref, Object msg) {
        action(Action.postMessage, ref, msg);
    }

    private void action(Action action, String ref, Object data) {
        WXComponent webComponent =
                WXSDKManager.getInstance()
                        .getWXRenderManager()
                        .getWXComponent(mWXSDKInstance.getInstanceId(), ref);
        if(webComponent instanceof WXWebpri) {
            ((WXWebpri) webComponent).setAction(action.name(), data);
        }
    }

    private void action(Action action, String ref) {
        action(action, ref, null);
    }

}
