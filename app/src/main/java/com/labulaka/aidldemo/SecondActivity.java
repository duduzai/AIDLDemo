package com.labulaka.aidldemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.labulaka.aidldemo.aidl.ForClientAidl;
import com.labulaka.aidldemo.aidl.IMyAidlInterface;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent intent = new Intent(this, BluetoothService.class);
        bindService(intent, serviceConnection,BIND_AUTO_CREATE);
    }

    private ForClientAidl.Stub callBack = new ForClientAidl.Stub() {
        @Override
        public void invokeCallBack(String time) throws RemoteException {
            Log.d("SecondActivity", "invokeCallBack: "+time);
        }

        @Override
        public void invoke2() throws RemoteException {
            Log.d("SecondActivity", "invoke2: ");
        }

        @Override
        public void invoke3() throws RemoteException {
            Log.d("SecondActivity", "invoke3: ");
        }
    };
    private IMyAidlInterface aidlInterface;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            aidlInterface = IMyAidlInterface.Stub.asInterface(service);
            try {
                aidlInterface.registerCallBack(callBack);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}
