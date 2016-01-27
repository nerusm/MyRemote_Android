package com.irremote.bluetooth.project.suren.myremote.uiinterface;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.irremote.bluetooth.project.suren.myremote.R;

/**
 * Created by suren on 29/12/15.
 */
public class HeaderFragClass extends Fragment {
    private OnItemSelectedListener listener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fraglay_header,container,false);
        /*CheckBox checkBoxTV = (CheckBox) view.findViewById(R.id.checkBoxPower_TV);
        CheckBox checkBoxAVR = (CheckBox) view.findViewById(R.id.checkBoxPower_AVR);
        CheckBox checkBoxSet = (CheckBox) view.findViewById(R.id.checkBoxPower_Setup);
        checkBoxTV.setChecked(true);
        checkBoxAVR.setChecked(true);
        checkBoxSet.setChecked(true);*/
        final ToggleButton btnPower = (ToggleButton) view.findViewById(R.id.powerButton);
        btnPower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnPower.isChecked()){
                    mSendCommand("Is Checked");
//                    Toast.makeText(getActivity().getBaseContext(),"Power button on",Toast.LENGTH_SHORT).show();
                } else {
                    mSendCommand("not checked");
//                    Toast.makeText(getActivity().getBaseContext(),"Power button Off",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return  view;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (OnItemSelectedListener) activity;
        } catch (ClassCastException e){
            throw new ClassCastException(activity.toString()+ " must implement OnItemSelectedListner");
        }
    }

public void mSendCommand(String cmdString){
    listener.sendCommandInterface(cmdString, "POW",MainActivity.CMDTYPE_NONNUMBER);
}

}
