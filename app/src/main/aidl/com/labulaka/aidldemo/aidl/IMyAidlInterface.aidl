// IMyAidlInterface.aidl
package com.labulaka.aidldemo.aidl;
import com.labulaka.aidldemo.aidl.ForClientAidl;
// Declare any non-default types here with import statements

interface IMyAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    void connect(String uuid);

    String response();
    void registerCallBack(ForClientAidl callBack);
}
