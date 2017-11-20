# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /home/newbiechen/Android/Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more detail, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn retrofit2.**
-dontwarn com.android.internal.**
-dontwarn sun.misc.Unsafe.**
-dontwarn org.apache.http.**
-dontwarn android.webki.**
-dontwarn rx.**
-dontwarn android.net.http.**
-dontwarn org.greenrobot.greendao.**
-dontwarn net.sqlcipher.database.**
-dontwarn cn.pedant.**
-dontwarn com.liulishuo.**

-keep class cn.pedant.SweetAlert.**{*;}
-keep class com.omg.ireader.widget.**{*;}
-keep class com.omg.ireader.model.**{*;}
-keep class com.omg.ireader.event.**{*;}
-keep class com.omg.ireader.presenter.**{*;}
#友盟统计start
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
-keep public class com.omg.ireader.R$*{
public static final int *;
}
-keep class com.umeng.commonsdk.** {*;}
-keep class com.umeng.analytics.** {*;}
#友盟统计end