package com.irremote.bluetooth.project.suren.myremote.uiinterface;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.irremote.bluetooth.project.suren.myremote.R;
import com.irremote.bluetooth.project.suren.myremote.models.BluetoothConnectModule;

import java.io.IOException;
import java.net.PortUnreachableException;
import java.security.PublicKey;

public class MainActivity extends FragmentActivity implements OnItemSelectedListener, OnBluetoothConnClick{

    public static String TAG = "BTIR";
    public static String CMDTYPE_NUMBER = "CMDTYPE_NUMBER";
    public static String CMDTYPE_NONNUMBER = "CMDTYPE_NONNUMBER";
    public static String ID_SETUP = "SET";
    public static String ID_AVR = "AVR";
    public static String ID_TV = "TV";
    BluetoothConnectModule bluetoothConnectModule;

    int REQUEST_ENABLE_BT = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"************ OnCreate");
        setContentView(R.layout.main_remote_activity);
        bluetoothConnectModule = new BluetoothConnectModule(getApplicationContext());

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"************ OnStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"************ OnResume");
        if (!bluetoothConnectModule.checkBluetooth()){
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else {
            try {
                if (bluetoothConnectModule.connectBluetooth()){
                    Log.d(TAG,"Bluetooth connected....");
                    updateBluetoothStatus(true);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,"************ OnRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        bluetoothConnectModule.streamFlush();
        bluetoothConnectModule.socketClose();
        Log.d(TAG,"************ OnPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"************ OnStop");
        bluetoothConnectModule.disconnect();
        new BluetoothConnectModule(getApplicationContext());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "************ OnDestroy");
bluetoothConnectModule.disconnect();
        new BluetoothConnectModule(getApplicationContext());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if(requestCode == REQUEST_ENABLE_BT){
                Toast.makeText(getApplicationContext(), "Bluetooth Enabled",Toast.LENGTH_SHORT).show();
            }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

/*    @Override
    public void onItemSelected(String cmdString) {
        try {
            bluetoothConnectModule.sendIRCommand("SET|12582919|12582912|12582914|12583004|~");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }*/

    private void updateBluetoothStatus(boolean status){
        Log.d(TAG,"Main updateBluetoothStatus 1"+status);
        FooterFragClass footerFragClass = (FooterFragClass) getFragmentManager().findFragmentById(R.id.fragment2);

        Log.d(TAG,"Main updateBluetoothStatus 2"+status);
        footerFragClass.updateBTStatus(status);
    }



    @Override
    public void connBT() {
        Log.d(TAG,"in ConnBT 1");
        //bluetoothConnectModule = new BluetoothConnectModule(getApplicationContext());
        Log.d(TAG, "in ConnBT 2");

        try {
            Log.d(TAG, " TTTTTTTTTTT: " + bluetoothConnectModule.connectBluetooth());
            if(bluetoothConnectModule.connectBluetooth()){
                Log.d(TAG,"Conected again ***");
            } else {
                Log.d(TAG,"Notthin :( ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendCommandInterface(String commandString, String unitName, String cmdType) {
        try {
            Log.d(TAG,"from Mainactivity sendCommandInterface");
            bluetoothConnectModule.sendIRCommand(commandString,unitName,cmdType);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
