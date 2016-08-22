package com.labulaka.aidldemo;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

import com.labulaka.aidldemo.aidl.ForClientAidl;
import com.labulaka.aidldemo.aidl.IMyAidlInterface;

public class BluetoothService extends Service {
    public BluetoothService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mBinder;
    }

    private ForClientAidl mCallBack;
    private final IMyAidlInterface.Stub mBinder = new IMyAidlInterface.Stub(){

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void connect(String uuid) throws RemoteException {

        }

        @Override
        public String response() throws RemoteException {
            return "返回的数据="+String.valueOf(time)+"秒";
        }

        @Override
        public void registerCallBack(ForClientAidl callBack) throws RemoteException {
//            mCallBack = callBack;
            mCallBacks.register(callBack);
        }

    };

    private int time;
    private RemoteCallbackList<ForClientAidl> mCallBacks = new RemoteCallbackList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        MainActivity.mainString = "hello from remote service";
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                do{
                    time ++;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }while (true);
            }
        });
        thread.start();


        Thread callBackThread = new Thread(new Runnable() {
            @Override
            public void run() {
                do{
                    final int len = mCallBacks.beginBroadcast();
                    for (int i = 0; i < len; i++) {
                        try {
                            // 通知回调
                            mCallBacks.getBroadcastItem(i).invokeCallBack("callback:"+time);
                            mCallBacks.getBroadcastItem(i).invoke2();
                            mCallBacks.getBroadcastItem(i).invoke3();

                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                     }
//                    try {
//                        mCallBack.invokeCallBack("callback"+time);
//                    } catch (RemoteException e) {
//                        e.printStackTrace();
//                    }

                    mCallBacks.finishBroadcast();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }while (true);
            }
        });

        callBackThread.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

}
