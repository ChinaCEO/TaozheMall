package com.taozhemall.app.util;

import android.content.Context;


public class AppConfig {
  private static final String TAG = "AppConfig";
  private static com.taozhemall.app.util.AppPreferences sPreferences = new com.taozhemall.app.util.AppPreferences();

  public static void init(Context context) {
    loadAppSetting(context);
  }

  public static String getLaunchUrl() {
    if (isLaunchLocally()) {
      return sPreferences.getString("local_url", "file://assets/index.js");
    }
    return sPreferences.getString("launch_url", "http://127.0.0.1:8080/dist/index.js");
  }

  public static Boolean isLaunchLocally() {
    return sPreferences.getBoolean("launch_locally", false);
  }

  private static void loadAppSetting(Context context) {
    com.taozhemall.app.util.AppConfigXmlParser parser = new com.taozhemall.app.util.AppConfigXmlParser();
    parser.parse(context);
    sPreferences = parser.getPreferences();
  }
}
