package com.taozhemall.app.util;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.WXRenderStrategy;
import com.taobao.weex.devtools.common.LogUtil;
import com.taozhemall.app.R;

import java.util.HashMap;
import java.util.Map;


public class Network extends AppCompatActivity implements IWXRenderListener {

    private WXSDKInstance mWXSDKInstance;
    private FrameLayout mContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);

        //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
        com.taozhemall.app.util.StatusBarUtil.setRootViewFitsSystemWindows(this,false);
        //设置状态栏透明
        com.taozhemall.app.util.StatusBarUtil.setTranslucentStatus(this);
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        if (!com.taozhemall.app.util.StatusBarUtil.setStatusBarDarkTheme(this, true)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
            com.taozhemall.app.util.StatusBarUtil.setStatusBarColor(this,0x55000000);
        }
        mContainer = (FrameLayout) findViewById(R.id.container);

        mWXSDKInstance = new WXSDKInstance(this);
        mWXSDKInstance.registerRenderListener(this);

        String RenderPageUrl = "";
        if (getIntent().getData() != null) {
//            Intent intent = getIntent();
            String navUrl = getIntent().getData().toString();
            if (null != navUrl) {
                
                if(navUrl.contains("local://")){
                    navUrl = navUrl.replace("local://","");
                }
                
                LogUtil.e(navUrl);
                RenderPageUrl = navUrl;
            } else {
                LogUtil.e("a is null");
            }
        } else {
            LogUtil.e("get data is null");
        }
        Map<String, Object> options = new HashMap<>();
        options.put(WXSDKInstance.BUNDLE_URL, RenderPageUrl);
        mWXSDKInstance.renderByUrl("WXSample", RenderPageUrl, options, null, WXRenderStrategy.APPEND_ONCE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityStart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityDestroy();
        }
    }

    @Override
    public void onViewCreated(WXSDKInstance instance, View view) {
        if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
        mContainer.addView(view);
    }

    @Override
    public void onRenderSuccess(WXSDKInstance instance, int width, int height) {

    }

    @Override
    public void onRefreshSuccess(WXSDKInstance instance, int width, int height) {

    }

    @Override
    public void onException(WXSDKInstance instance, String errCode, String msg) {

    }
}