package su.android.test.jni;

/**
 * Created by Administrator on 2016-3-18.
 */
public class HelloJni {

    static {
        //System.loadLibrary("hello-jni");
    }

    public static  native String  stringFromJNI();

}
