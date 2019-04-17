package com.taozhemall.app.extend.module;

import android.Manifest;
import android.content.Context;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;

import com.taobao.weex.WXEnvironment;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

import java.lang.reflect.Method;

import static android.content.Context.TELEPHONY_SERVICE;


public class GetIMEIMoudule extends WXModule {

    @JSMethod(uiThread = false)
    public void getIMEI(@Nullable final JSCallback callback) {

        callback.invoke(getImei(0));

    }

    /**
     * 通过卡槽位置拿 IMEI
     *
     * @param slotId (0, 1卡槽位置）
     * @return
     */
    public static String getImei(int slotId) {
        if (slotId != 0 && slotId != 1) {
            return null;
        }

        Context context = WXEnvironment.getApplication().getApplicationContext();

        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            return tm.getDeviceId(slotId);

        } else if (slotId == 0){
            return tm.getDeviceId();

        } else {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);// 取得相关系统服务
            Class<?> telephonyManagerClass = null;
            String imei = null;

            try {
                telephonyManagerClass = Class.forName("android.telephony.TelephonyManager");
                Method method = telephonyManagerClass.getMethod("getImei", int.class);
                imei = (String) method.invoke(telephonyManager, slotId);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return imei;
        }
    }
}
