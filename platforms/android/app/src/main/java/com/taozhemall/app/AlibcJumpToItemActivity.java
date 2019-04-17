package com.taozhemall.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.page.AlibcMyCartsPage;
import com.alibaba.baichuan.android.trade.page.AlibcMyOrdersPage;
import com.alibaba.baichuan.android.trade.page.AlibcPage;
import com.alibaba.baichuan.trade.biz.AlibcConstants;
import com.alibaba.baichuan.trade.biz.context.AlibcResultType;
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult;
import com.alibaba.baichuan.trade.biz.core.taoke.AlibcTaokeParams;
import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.WXRenderStrategy;
import com.taobao.weex.devtools.common.LogUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JX.liu on 14/04/2019.
 */
public class AlibcJumpToItemActivity extends AppCompatActivity implements IWXRenderListener {
    private WXSDKInstance mWXSDKInstance;
    private FrameLayout mContainer;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alibcjump);

        initView();

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

        Map<String, String> exParams = new HashMap<>();
        exParams.put(AlibcConstants.ISV_CODE, "appisvcode");

        AlibcTaokeParams alibcTaokeParams = new AlibcTaokeParams();
        alibcTaokeParams.adzoneid = "91627500240";

        alibcTaokeParams.extraParams = new HashMap<>();
        alibcTaokeParams.extraParams.put("taokeAppkey","25635637");

        AlibcBasePage alibcBasePage = null;

        String RenderPageUrl = "";
        if (getIntent().getData() != null) {
            String navUrl = getIntent().getData().toString();
            if (null != navUrl) {

                if(navUrl.contains("tblinkto://")){
                    navUrl = navUrl.replace("tblinkto://","");
                }

                LogUtil.e(navUrl);
                RenderPageUrl = navUrl;
                alibcBasePage = new AlibcPage(RenderPageUrl);
            } else {
                LogUtil.e("a is null");
            }
        } else if(getIntent().getStringExtra("showOrder") != null) {
            String orderKey = getIntent().getStringExtra("showOrder");
            if(orderKey.equals("showCart")) {
                alibcBasePage = new AlibcMyCartsPage();
            } else if(orderKey.equals("showOrders")){
                alibcBasePage = new AlibcMyOrdersPage(0,true);
            }
        } else {
            LogUtil.e("get data is null");
        }
        

        Map<String, Object> options = new HashMap<>();
        options.put(WXSDKInstance.BUNDLE_URL, RenderPageUrl);
        mWXSDKInstance.renderByUrl("AlibcJump", RenderPageUrl, options, null, WXRenderStrategy.APPEND_ONCE);

        AlibcShowParams alibcShowParams = new AlibcShowParams(OpenType.Native, false);

        AlibcTrade.show(AlibcJumpToItemActivity.this,webView, null, null,alibcBasePage,alibcShowParams,alibcTaokeParams,exParams, new AlibcTradeCallback() {
            @Override
            public void onTradeSuccess(AlibcTradeResult tradeResult) {
                //打开电商组件，用户操作中成功信息回调。tradeResult：成功信息（结果类型：加购，支付；支付结果）
                System.out.println(tradeResult);
                if(tradeResult.resultType.equals(AlibcResultType.TYPECART)){
                    //加购成功
                    Toast.makeText(AlibcJumpToItemActivity.this, "加购成功", Toast.LENGTH_SHORT).show();
                }else if (tradeResult.resultType.equals(AlibcResultType.TYPEPAY)){
                    //支付成功
                    Toast.makeText(AlibcJumpToItemActivity.this, "支付成功,成功订单号为"+tradeResult.payResult.paySuccessOrders, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                //打开电商组件，用户操作中错误信息回调。code：错误码；msg：错误信息
                System.out.println("淘客"+code+" "+msg );

            }
        });
        if (mWXSDKInstance.getContext() instanceof Activity) {
            ((Activity) mWXSDKInstance.getContext()).finish();
        }

    }

    private void initView() {

        LinearLayout linearLayout = new LinearLayout(this, null);
        webView = new WebView(this);
        webView.getSettings().setJavaScriptEnabled(true);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        linearLayout.addView(webView, params);
        setContentView(linearLayout);

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
        //调用了AlibcTrade.show方法的Activity都需要调用AlibcTradeSDK.destory()
        AlibcTradeSDK.destory();
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
