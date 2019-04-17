package com.taozhemall.app.extend.component;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;

import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.ui.action.BasicComponentData;
import com.taobao.weex.ui.component.AbstractEditComponent;
import com.taobao.weex.ui.component.WXVContainer;
import com.taobao.weex.ui.view.WXEditText;



public class WxEditText extends AbstractEditComponent {

    public WxEditText(WXSDKInstance instance, WXVContainer parent, boolean isLazy, BasicComponentData basicComponentData) {
        super(instance, parent, isLazy, basicComponentData);
    }

    @JSMethod
    public void clear() {
        WXEditText host = getHostView();
        if (host != null && host.getText().length() > 0) {
            host.getText().clear();
        }
    }

    @JSMethod
    public void show(boolean isShowPassWord){
        WXEditText editText = getHostView();
        if (editText != null){
            if (isShowPassWord){
                editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }else {
                editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
    }
}
