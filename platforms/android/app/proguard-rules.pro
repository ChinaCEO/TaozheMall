# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/lixinke/Tool/android-eclipse/adt-bundle-mac-x86_64-20140702/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
##weex
-keep class com.taobao.weex.bridge.**{*;}
-keep class com.taobao.weex.dom.**{*;}
-keep class com.taobao.weex.adapter.**{*;}
-keep class com.taobao.weex.common.**{*;}
-keep class * implements com.taobao.weex.IWXObject{*;}
-keep class com.taobao.weex.ui.**{*;}
-keep class com.taobao.weex.ui.component.**{*;}
-keep class com.taobao.weex.utils.**{
    public <fields>;
    public <methods>;
    }
-keep class com.taobao.weex.view.**{*;}
-keep class com.taobao.weex.module.**{*;}
-keep public class * extends com.taobao.weex.common.WXModule{*;}
-keep public class com.taobao.weex.WXDebugTool{*;}

-keepattributes Signature
    -keep class sun.misc.Unsafe { *; }
    -keep class com.alibaba.** {*;}
    -dontwarn com.alibaba.**
    -keep class com.alipay.** {*;}
    -dontwarn com.alipay.**
    -keep class com.taobao.** {*;}
    -dontwarn com.taobao.**
    -keep class com.ut.** {*;}
    -dontwarn com.ut.**
    -keep class com.ta.** {*;}
    -dontwarn com.ta.**
    -keep class mtopsdk.** {*;}
    -dontwarn mtopsdk.**
    -keep class org.json.** {*;}
    -keep class com.ali.auth.**  {*;}

-dontwarn com.alimama.**
-keep class com.alimama.** \{\*;}
-eeppackagenames com.alimama.tunion.sdk.**
-keeppackagenames com.alimama.tunion.sdk.**
-keep class com.alimama.tunion.sdk.** {
         public <fields>;
         public <methods>;
     }