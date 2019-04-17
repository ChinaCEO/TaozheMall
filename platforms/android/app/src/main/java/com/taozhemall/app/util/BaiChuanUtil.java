package com.taozhemall.app.util;

import android.app.Activity;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.page.AlibcDetailPage;
import com.alibaba.baichuan.android.trade.page.AlibcPage;
import com.alibaba.baichuan.trade.biz.AlibcConstants;
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult;
import com.alibaba.baichuan.trade.biz.core.taoke.AlibcTaokeParams;

import java.util.HashMap;
import java.util.Map;

import static com.alimama.tunion.trade.TUnionTradeSDK.getContext;

public class BaiChuanUtil {


    public static void showItemDetailPage(Activity activity, String adzoneid, String taokeAppkey, String taokeUrl) {
        //提供给三方传递配置参数
        Map<String, String> exParams = new HashMap<>();
        exParams.put(AlibcConstants.ISV_CODE, "appisvcode");

        //实例化URL打开page
        AlibcBasePage page = new AlibcPage(taokeUrl);

        //Android构造淘客参数
        AlibcTaokeParams alibcTaokeParams = new AlibcTaokeParams(); // 若非淘客taokeParams设置为null即可
        alibcTaokeParams.adzoneid = adzoneid;
        alibcTaokeParams.extraParams = new HashMap<>();
        alibcTaokeParams.extraParams.put("taokeAppkey", taokeAppkey);

        //设置页面打开方式
        AlibcShowParams showParams = new AlibcShowParams(OpenType.Native, false);

        /**
         * 打开电商组件,支持使用外部webview
         *
         * @param activity             必填
         * @param webView              外部 webView
         * @param webViewClient        webview的webViewClient
         * @param webChromeClient      webChromeClient客户端
         * @param tradePage            页面类型,必填，不可为null，详情见下面tradePage类型介绍
         * @param showParams           show参数
         * @param taokeParams          淘客参数
         * @param trackParam           yhhpass参数
         * @param tradeProcessCallback 交易流程的回调，必填，不允许为null；
         * @return 0标识跳转到手淘打开了,1标识用h5打开,-1标识出错
         */
        WebView webView = new WebView(getContext());
        webView.getSettings().setJavaScriptEnabled(true);
//        WebView webView = new WebView(getContext());
        AlibcTrade.show(activity, webView, null, null, page, showParams, alibcTaokeParams, exParams ,
        new AlibcTradeCallback() {
            @Override
            public void onTradeSuccess(AlibcTradeResult tradeResult) {
                //打开电商组件，用户操作中成功信息回调。tradeResult：成功信息（结果类型：加购，支付；支付结果）
                System.out.println(tradeResult);
            }

            @Override
            public void onFailure(int code, String msg) {
                //打开电商组件，用户操作中错误信息回调。code：错误码；msg：错误信息
                System.out.println("淘客"+code+" "+msg);
            }
        });
    }
}
