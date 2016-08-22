package com.labulaka.aidldemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.labulaka.aidldemo.aidl.IMyAidlInterface;

public class BluetoothService extends Service {
    public BluetoothService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mBinder;
    }

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
    };

    private int time;

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
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }


}
