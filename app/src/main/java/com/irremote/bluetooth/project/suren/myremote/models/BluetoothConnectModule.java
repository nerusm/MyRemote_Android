package com.irremote.bluetooth.project.suren.myremote.models;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.irremote.bluetooth.project.suren.myremote.uiinterface.MainActivity;
import com.irremote.bluetooth.project.suren.myremote.R;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

/**
 * Created by suren on 29/12/15.
 */
public class BluetoothConnectModule {

    Context appContext;
    BluetoothAdapter mBluetoothAdapter;
    BluetoothDevice bluetoothDevice;
    BluetoothSocket bluetoothSocket;
    OutputStream bluetoothOutputStream;
    Set<BluetoothDevice> pairedDevices;
    private UUID DEFAULT_SPP_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");


    public BluetoothConnectModule(Context context) {
        this.appContext = context;
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mBluetoothAdapter.cancelDiscovery();


    }

    public void disconnect(){
        /*for (BluetoothDevice btDevice : pairedDevices) {
            if (btDevice.getName().equals(appContext.getResources().getString(R.string.bluetoothModuleName))) {
                Log.d(MainActivity.TAG,"During Destroy");
                btDevice.
            }
        }*/
        Log.d(MainActivity.TAG, "During Destroy");

    }

    public boolean connectBluetooth() throws IOException,Exception {
        boolean statusFlag = false;
       /* if (bluetoothSocket != null && bluetoothSocket.isConnected()) {
            Log.d(MainActivity.TAG, "Socket connected");
            bluetoothOutputStream = bluetoothSocket.getOutputStream();
            statusFlag = true;
        }*/
        if (bluetoothSocket != null) {

            if (bluetoothSocket.isConnected()) {
                Log.d(MainActivity.TAG, "Socket connected");
                bluetoothOutputStream = bluetoothSocket.getOutputStream();
                statusFlag = true;
            } else {
                Log.d(MainActivity.TAG,"*** BT NOT CONNECTED "+bluetoothSocket.toString());
                for (BluetoothDevice btDevice : pairedDevices) {
                    if (btDevice.getName().equals(appContext.getResources().getString(R.string.bluetoothModuleName))) {
                        Log.d(MainActivity.TAG,"dfgdfg");
                        bluetoothSocket = btDevice.createInsecureRfcommSocketToServiceRecord(DEFAULT_SPP_UUID);

                    }
                }

                bluetoothOutputStream = bluetoothSocket.getOutputStream();
                statusFlag = true;
            }
        }
        else {
            Log.d(MainActivity.TAG,"BS IS NULL");
            Log.d(MainActivity.TAG, "Size of paired set :" + pairedDevices.size());
//        bluetoothSocket.close();
            for (BluetoothDevice btDevice : pairedDevices) {
                if (btDevice.getName().equals(appContext.getResources().getString(R.string.bluetoothModuleName))) {
                    Log.d(MainActivity.TAG,"BT device found");
                    bluetoothSocket = btDevice.createInsecureRfcommSocketToServiceRecord(DEFAULT_SPP_UUID);
Log.d(MainActivity.TAG,"from blues: "+bluetoothSocket.toString());
//                    bluetoothSocket = btDevice.createInsecureRfcommSocketToServiceRecord(btDevice.getUuids()[0].getUuid());
                    if (bluetoothSocket != null) {
                        Log.d(MainActivity.TAG,"Socket not Null");

                        if (bluetoothSocket.isConnected()) {
                            Log.d(MainActivity.TAG, "Already Connected: " + bluetoothSocket.isConnected());
                            bluetoothOutputStream = bluetoothSocket.getOutputStream();
                            statusFlag = true;
                        } else {
                            Log.d(MainActivity.TAG,"Socket Not Connected");
                            bluetoothSocket.connect();
                            if (!bluetoothSocket.isConnected()){
                                Log.d(MainActivity.TAG,"Not connected");
                            }
                            Log.d(MainActivity.TAG, "Connected: " + bluetoothSocket.isConnected());
                            bluetoothOutputStream = bluetoothSocket.getOutputStream();
                            Log.d(MainActivity.TAG, "2 Connected: " + bluetoothSocket.isConnected());

                            statusFlag = true;
                        }
                    } else if (bluetoothSocket.isConnected()) {
                        Log.d(MainActivity.TAG,"1 else if");
                        bluetoothOutputStream = bluetoothSocket.getOutputStream();
                        statusFlag = true;
                    }
                } else {
                    Log.d(MainActivity.TAG,"2 else");
                }
            }
       }
        Log.d(MainActivity.TAG, "*************");
        return statusFlag;
    }

    private String getIRCommandString(String channelNumber, String unitName, String cmdType){
        int len = channelNumber.length();
        String keyNum="";
        String irCommand = unitName;
        Log.d(MainActivity.TAG,"-"+channelNumber +","+unitName+","+cmdType+","+MainActivity.ID_SETUP);

        if (unitName.equalsIgnoreCase(MainActivity.ID_SETUP)) {
            Log.d(MainActivity.TAG,"yyyyyyyyyyyyyyyy");
            if (cmdType.equalsIgnoreCase(MainActivity.CMDTYPE_NUMBER)) {
                Log.d(MainActivity.TAG,"hjgjhgj");
                for (int i = 0; i < len; i++) {
                    Log.d(MainActivity.TAG,"uuuuuuuuuuuuuuu");
                    keyNum = unitName;
//                    keyNum = String.valueOf(unitName.charAt(0));
                    keyNum = keyNum.concat(String.valueOf(channelNumber.charAt(i)));
                    irCommand = irCommand.concat("|").concat(appContext.getString(getResourceID(keyNum, "string")));
                    Log.d(MainActivity.TAG, "k"+irCommand);
                }
                if (unitName.equalsIgnoreCase("SET")) {
                    irCommand = irCommand.concat("|").concat(appContext.getString(getResourceID("select", "string")));
                }
            }
        }




        /*append the end of command string*/
        irCommand = irCommand.concat("|~");
        Log.d(MainActivity.TAG, irCommand);
        return irCommand;
    }

    public void sendIRCommand(String irCommand, String unitName, String cmdType) throws Exception {
//        int resourceID = appContext.getResources().getIdentifier(irCommand, "string", appContext.getPackageName());
        Log.d(MainActivity.TAG,irCommand);
        int resourceID = getResourceID(irCommand,"string");
        String channelNumber = appContext.getString(resourceID);
        String irCodes = getIRCommandString(channelNumber,unitName,cmdType);
        Toast.makeText(appContext,"Ircommand "+irCommand+":"+channelNumber+":"+irCodes,Toast.LENGTH_SHORT).show();

        bluetoothOutputStream.write(irCodes.getBytes());
        if(bluetoothOutputStream == null){
            Log.d(MainActivity.TAG,"******");
        }
    }

    private int getResourceID(String resourceName,String resourceType){
        return appContext.getResources().getIdentifier(resourceName, resourceType, appContext.getPackageName());
    }

    public boolean checkBluetooth() {
        if (checkBluetoothAvailable(mBluetoothAdapter)) {
            Toast.makeText(appContext, "Bluetooth Supported on Device", Toast.LENGTH_SHORT).show();
            Toast.makeText(appContext, "Checking for Bluetooth Enabled...", Toast.LENGTH_SHORT).show();
            if (checkBluetoothEnabled(mBluetoothAdapter)) {
                Toast.makeText(appContext, "Bluetooth is Enabled", Toast.LENGTH_SHORT).show();
                pairedDevices = mBluetoothAdapter.getBondedDevices();
                /*for (BluetoothDevice btDevice : pairedDevices) {
                    String btname = btDevice.getName();
                    String btadd = btDevice.getAddress();
                    Log.d(MainActivity.TAG,"Bt Name: "+btname);
                }*/
                return true;
            } else {
                Toast.makeText(appContext, "Bluetooth is Not Enabled", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Toast.makeText(appContext, "Bluetooth Not Supported on Device", Toast.LENGTH_LONG).show();
            return true;
        }
    }

    private boolean checkBluetoothAvailable(BluetoothAdapter btAdapter) {
        if (btAdapter == null) {
            return false;
        } else {
            return true;
        }
    }

    private boolean checkBluetoothEnabled(BluetoothAdapter btAdapter) {
        return btAdapter.isEnabled();
    }

    public void streamFlush(){
        try {
            bluetoothOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void socketClose(){
        try {
            bluetoothSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
