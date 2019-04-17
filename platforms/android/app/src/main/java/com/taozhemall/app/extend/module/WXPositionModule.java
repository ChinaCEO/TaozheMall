package com.taozhemall.app.extend.module;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;
import com.taobao.weex.utils.WXLogUtils;

import java.util.ArrayList;
import java.util.HashMap;

import static com.alibaba.baichuan.trade.common.utils.AlarmUtils.getCurrentActivity;


public class WXPositionModule extends WXModule {
    private static final String RESULT = "result";
    private static final String DATA = "data";

    private static final String RESULT_OK = "success";
    private static final String RESULT_FAILED = "failed";

    private Context mContext = WXEnvironment.getApplication().getApplicationContext();
    /**
     * get user location.
     */


    @JSMethod (uiThread = false)
    public void getUserLocation(@Nullable final JSCallback callback) {

        //判断是否为android6.0系统版本，如果是，需要动态添加权限
        if(Build.VERSION.SDK_INT >= 23) {
            if(!checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) &&
                    !checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)) {

                ActivityCompat.requestPermissions(getCurrentActivity(),new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                },0);

            }
        }
        //获取定位服务
//        final LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
//        //获取所有可用的位置提供器
//        List<String> providers = locationManager.getProviders(true);
//        System.out.println(providers);

//        Boolean ok = false;
//        if (providers.contains(LocationManager.GPS_PROVIDER)) {
//            //如果是GPS
//            ok = true;
//        } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
//            //如果是Network
//            ok = true;
//        } else {
//            Intent i = new Intent();
//            i.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//            mContext.startActivity(i);
//        }
//
//        if(ok) {
//
//        }

        final AMapLocationClient client = new AMapLocationClient(mContext);
        final AMapLocationClientOption clientOption = new AMapLocationClientOption();
        //设置定位监听
        client.setLocationListener(new AMapLocationListener() {
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
                    if (callback != null) {
                        HashMap map = new HashMap(2);
                        HashMap data = new HashMap(2);
                        ArrayList position = new ArrayList();
                        position.add(aMapLocation.getLongitude());
                        position.add(aMapLocation.getLatitude());

                        String positionCity = aMapLocation.getCity();

                        data.put("position", position);
                        data.put("positionCity",positionCity);
                        map.put(DATA, data);
                        map.put(RESULT, aMapLocation.getLongitude() > 0 && aMapLocation.getLatitude() > 0 ? RESULT_OK : RESULT_FAILED);
                        callback.invoke(map);
                    }
                } else {
                    String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
                    WXLogUtils.e("WXMapModule", errText);
                    callback.invoke(errText);
                }
                if (client != null) {
                    client.stopLocation();
                    client.onDestroy();
                }
            }
        });
        //设置为高精度定位模式
        clientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        clientOption.setOnceLocation(true);
        clientOption.setMockEnable(true);
        clientOption.setNeedAddress(true);
        clientOption.setHttpTimeOut(10000);
        //设置定位参数
        client.setLocationOption(clientOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        client.startLocation();

    }

    private boolean checkPermission(String permission){
        //检查权限（NEED_PERMISSION）是否被授权 PackageManager.PERMISSION_GRANTED表示同意授权
        if (ActivityCompat.checkSelfPermission(mContext, permission)
                == PackageManager.PERMISSION_GRANTED)
            return true;
        else
            return false;
    }

}
