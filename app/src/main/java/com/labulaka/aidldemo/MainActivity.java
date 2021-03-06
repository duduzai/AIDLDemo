package com.labulaka.aidldemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.labulaka.aidldemo.aidl.ForClientAidl;
import com.labulaka.aidldemo.aidl.IMyAidlInterface;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static String mainString = "hello world";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(this);
        Intent intent = new Intent(this, BluetoothService.class);
        bindService(intent, serviceConnection,BIND_AUTO_CREATE);

        Intent second = new Intent(this, SecondActivity.class);
        startActivity(second);

    }

    private IMyAidlInterface aidlInterface;
    private ForClientAidl.Stub callBack = new ForClientAidl.Stub() {
        @Override
        public void invokeCallBack(String time) throws RemoteException {
            Log.d("MainActivity", "invokeCallBack: "+time);
        }

        @Override
        public void invoke2() throws RemoteException {
            Log.d("MainActivity", "invoke2: ");
        }

        @Override
        public void invoke3() throws RemoteException {
            Log.d("MainActivity", "invoke3: ");
        }
    };

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

    @Override
    public void onClick(View v) {
        if (aidlInterface != null) {
            try {
                String res = aidlInterface.response();
                Toast.makeText(MainActivity.this, res, Toast.LENGTH_SHORT).show();
                Log.d("MainActivity", "onClick: " + mainString);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
