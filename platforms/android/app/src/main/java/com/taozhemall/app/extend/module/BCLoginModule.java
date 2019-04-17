package com.taozhemall.app.extend.module;

import android.support.annotation.Nullable;

import com.ali.auth.third.core.model.Session;
import com.alibaba.baichuan.trade.biz.login.AlibcLogin;
import com.alibaba.baichuan.trade.biz.login.AlibcLoginCallback;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

import java.util.HashMap;
import java.util.Map;

public class BCLoginModule extends WXModule {


    @JSMethod(uiThread = false)
    public void login(@Nullable final JSCallback callback) {
        final AlibcLogin alibcLogin = AlibcLogin.getInstance();

        alibcLogin.showLogin(new AlibcLoginCallback() {

            @Override
            public void onSuccess(int i) {
//                Toast.makeText(getCurrentActivity(), "登录成功 ",
//                        Toast.LENGTH_LONG).show();

                //获取淘宝用户信息
                Session session = AlibcLogin.getInstance().getSession();
                callback.invoke(session);
            }
            @Override
            public void onFailure(int code, String msg) {
//                Toast.makeText(getCurrentActivity(), "登录失败 ",
//                        Toast.LENGTH_LONG).show();
            }
        });
    }

    @JSMethod(uiThread = false)
    public void logout(@Nullable final JSCallback callback) {
        AlibcLogin alibcLogin = AlibcLogin.getInstance();

        alibcLogin.logout(new AlibcLoginCallback() {
            @Override
            public void onSuccess(int i) {
//                Toast.makeText(getCurrentActivity(), "退出登录成功",
//                        Toast.LENGTH_LONG).show();
                Map<String, Object> resp = new HashMap<>();
                resp.put("ok", true);
                callback.invoke(resp);
            }

            @Override
            public void onFailure(int i, String s) {
//                Toast.makeText(getCurrentActivity(), "退出登录失败",
//                        Toast.LENGTH_LONG).show();
                Map<String, Object> resp = new HashMap<>();
                resp.put("ok", false);
                callback.invoke(resp);
            }
        });
    }
}
