package com.irremote.bluetooth.project.suren.myremote.uiinterface;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.irremote.bluetooth.project.suren.myremote.R;
import com.irremote.bluetooth.project.suren.myremote.models.BluetoothConnectModule;

/**
 * Created by suren on 29/12/15.
 */
public class FooterFragClass extends Fragment {
    private OnItemSelectedListener listener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fraglay_footer, container, false);
        final ImageView imageViewStatus = (ImageView) view.findViewById(R.id.imageView_bluetooth_status);


        Button btnVolUp = (Button) view.findViewById(R.id.btn_vol_up);
        Button btnVolDown = (Button) view.findViewById(R.id.btn_vol_down);
        Button btnChnlUp = (Button) view.findViewById(R.id.btn_chn_up);
        Button btnChnlDown = (Button) view.findViewById(R.id.btn_chn_down);
        Button btnMute = (Button)view.findViewById(R.id.btn_mute);

        View.OnClickListener avrOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button btn = (Button) view;
                listener.sendCommandInterface(btn.getTag().toString(),MainActivity.ID_AVR,MainActivity.CMDTYPE_NONNUMBER);
            }
        };
        btnVolUp.setOnClickListener(avrOnClickListener);
        btnVolDown.setOnClickListener(avrOnClickListener);


        View.OnClickListener setUpOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button btn = (Button) view;
                listener.sendCommandInterface(btn.getTag().toString(),MainActivity.ID_SETUP,MainActivity.CMDTYPE_NONNUMBER);
            }
        };
        btnChnlUp.setOnClickListener(setUpOnClickListener);
        btnChnlDown.setOnClickListener(setUpOnClickListener);
        btnMute.setOnClickListener(setUpOnClickListener);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (OnItemSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnItemSelectedListner");
        }
    }

    public void updateBTStatus(boolean status) {
        final ImageView imageViewStatus = (ImageView) getView().findViewById(R.id.imageView_bluetooth_status);
        Log.d(MainActivity.TAG,"from updateBTStatus");
        if (status) {
            imageViewStatus.setImageResource(R.drawable.status_green);
        } else {
            imageViewStatus.setImageResource(R.drawable.status_red);
        }
    }

    /*private void mSendCommand(String cmdString) {
        listener.sendCommandInterface(cmdString);
    }*/

    /*@Override
    public void onClick(View view) {

    }*/
}
