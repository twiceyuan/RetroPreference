# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/twiceYuan/AndroidSDK/tools/proguard/proguard-android.txt
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

# 两种方式保证混淆不影响 RetroPreference 的使用
# 第一种是下面这样 keep 掉相关的类（定义的类即可）
#-keepclassmembers class com.twiceyuan.retropreference.sample.Settings {
#    public *;
#}
# 第二种是使用上次的 mapping.txt 文件来生成新的。并在每次混淆之后保存新的 mapping 文件
-applymapping mapping.txt
