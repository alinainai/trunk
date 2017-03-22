# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\sdk/tools/proguard/proguard-android.txt
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



#混淆 ProGuard常用语法
#-libraryjars class_path 应用的依赖包，如android-support-v4
#-keep [,modifier,...] class_specification 不混淆某些类
#-keepclassmembers [,modifier,...] class_specification 不混淆类的成员
#-keepclasseswithmembers [,modifier,...] class_specification 不混淆类及其成员
#-keepnames class_specification 不混淆类及其成员名
#-keepclassmembernames class_specification 不混淆类的成员名
#-keepclasseswithmembernames class_specification 不混淆类及其成员名
#-assumenosideeffects class_specification 假设调用不产生任何影响，在proguard代码优化时会将该调用remove掉。如system.out.println和Log.v等等
#-dontwarn [class_filter] 不提示warnning


#Android 混淆原则:
#
#反射用到的类不混淆
#JNI方法不混淆
#AndroidMainfest中的类不混淆，四大组件和Application的子类和Framework层下所有的类默认不会进行混淆
#Parcelable的子类和Creator静态成员变量不混淆，否则会产生android.os.BadParcelableException异常
#使用GSON、fastjson等框架时，所写的JSON对象类不混淆，否则无法将JSON解析成对应的对象
#使用第三方开源库或者引用其他第三方的SDK包时，需要在混淆文件中加入对应的混淆规则
#有用到WEBView的JS调用也需要保证写的接口方法不混淆



#如果用到了反射需要加入 :
#-keepattributes Signature
#-keepattributes EnclosingMethod

#如果想让一些bean 对象不混淆, 里 com.czy.bean 包下面的全是 Json框架生成的bean对象, 那么只需加入:
#-keep class czy.**{*;}//不混淆所有的com.czy.bean包下的类和这些类的所有成员变量

#继承了Serializable接口的类，需要加上: 不混淆Serializable接口的子类中指定的某些成员变量和方法
#-keepclassmembers class * implements java.io.Serializable {
#    static final long serialVersionUID;
#    private static final java.io.ObjectStreamField[] serialPersistentFields;
#    private void writeObject(java.io.ObjectOutputStream);
#    private void readObject(java.io.ObjectInputStream);
#    java.lang.Object writeReplace();
#    java.lang.Object readResolve();
#}

#不混淆某个类:
#-keep class com.czy.**//不混淆所有com.czy包下的类，** 换成具体的类名则表示不混淆某个具体的类
#不混淆某个类和成员变量:
#-keep class com.clock.**{*;}//不混淆所有com.clock包下的类和类中的所有成员变量，**可以换成具体类名，*可以换成具体的字段，可参照Serialzable的混淆
#移除一些log代码:
#移除Log类打印各个等级日志的代码，打正式包的时候可以做为禁log使用，这里可以作为禁止log打印的功能使用，另外的一种实现方案是通过BuildConfig.DEBUG的变量来控制
#-assumenosideeffects class android.util.Log {
#    public static *** v(...);
#    public static *** i(...);
#    public static *** d(...);
#    public static *** w(...);
#    public static *** e(...);
#}





#Android 混淆原则:
#
#反射用到的类不混淆
#JNI方法不混淆
#AndroidMainfest中的类不混淆，四大组件和Application的子类和Framework层下所有的类默认不会进行混淆
#Parcelable的子类和Creator静态成员变量不混淆，否则会产生android.os.BadParcelableException异常
#使用GSON、fastjson等框架时，所写的JSON对象类不混淆，否则无法将JSON解析成对应的对象
#使用第三方开源库或者引用其他第三方的SDK包时，需要在混淆文件中加入对应的混淆规则
#有用到WEBView的JS调用也需要保证写的接口方法不混淆
#
#先看看google默认混淆文件: \sdk\tools\proguard\proguard-android.txt

#-keepattributes *Annotation*//使用注解需要添加
#-keep public class com.google.vending.licensing.ILicensingService
#-keep public class com.android.vending.licensing.ILicensingService
#
## For native methods, see http://proguard.sourceforge.net/manual/examples.html#native
#-keepclasseswithmembernames class * {//指定不混淆所有的JNI方法
#    native <methods>;
#}
#
## keep setters in Views so that animations can still work.
## see http://proguard.sourceforge.net/manual/examples.html#beans
#-keepclassmembers public class * extends android.view.View {//所有View的子类及其子类的get、set方法都不进行混淆
#   void set*(***);
#   *** get*();
#}
#
## We want to keep methods in Activity that could be used in the XML attribute onClick
#-keepclassmembers class * extends android.app.Activity {//不混淆Activity中参数类型为View的所有方法
#   public void *(android.view.View);
#}
#
## For enumeration classes, see http://proguard.sourceforge.net/manual/examples.html#enumerations
#-keepclassmembers enum * {//不混淆Enum类型的指定方法
#    public static **[] values();
#    public static ** valueOf(java.lang.String);
#}
#//不混淆Parcelable和它的子类，还有Creator成员变量
#-keep class * implements android.os.Parcelable {
#  public static final android.os.Parcelable$Creator *;
#}
#//不混淆R类里及其所有内部static类中的所有static变量字段
#-keepclassmembers class **.R$* {
#    public static <fields>;
#}
#
## The support library contains references to newer platform versions.
## Don't warn about those in case this app is linking against an older
## platform version.  We know about them, and they are safe.
#-dontwarn android.support.**//不提示兼容库的错误警告

#-keep class android.support.v8.renderscript.**{*;}