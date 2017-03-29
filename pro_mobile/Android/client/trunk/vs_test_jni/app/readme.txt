
-----NDK 编译步骤|START|------------------------------
1. Android Studio DEBUG模式编译生成的.class 文件在
 D:\workspace\sync_github\repository\trunk\pro\Sy_Android_Test_Jni\trunk\Alpha-0.00.001\app\build\intermediates\classes\debug
 \sy\android\test\jni\HelloJni.class
 目录下。
2. 编译生成 .h 头文件。
进入 classes 文件夹下执行： javah sy.android.test.jni.HelloJni 生成 sy_android_test_jni_HelloJni.h文件
2.1 复制 sy_android_test_jni_HelloJni.h 到 Android项目的jni目录下;
2.2 新建 sy_android_test_jni_HelloJni.c C源文件，编写JNI层实现。
3.使用CMD下的ndk-build 编译
3.1 打开CMD
3.2 进入 目录 D:\workspace\sync_github\repository\trunk\pro\Sy_Android_Test_Jni\trunk\Alpha-0.00.001\app\src\main\jni
3.3 > ndk-build


---NDK修改编译后.so文件的默认位置-------------------
1. 修改 D:\SoftSetup\code\android\AndroidNDK\android-ndk64-r10-windows-x86_64\android-ndk-r10\build\core\build-local.mk
第187 行。    NDK_APP_LIBS_OUT := $(NDK_PROJECT_PATH)/libs 改为   NDK_APP_LIBS_OUT := $(NDK_PROJECT_PATH)/libss 即可 




介绍下在Android中的两种定位方式
1、gps定位：内置到手机当中(早期版本的Android手机可能没有此模块)，
已成为手机的一个模块了，定位比较准确
2、基于网络的定位：这种定位方式需要手机联网，此种方式又分为基于基站定位(WI-FI)和GPRS定位(手机SIM卡)，
其中基于     基站定位比较准     确，而基于GPRS定位一般不是很准确


##Author for ADT tools |START|-----------------------------
/**
 *
* <BR> Copyright (c) 2012-2016 by SaduStephen. ALL RIGHTS RESERVED.
* <BR> Consult your license regarding permissions and restrictions.
* <BR> >>>> Add flow here:
* <BR> SINCE 2016年5月18日 上午10:50:12
 */

