package com.irremote.bluetooth.project.suren.myremote.uiinterface;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.TextView;

import com.irremote.bluetooth.project.suren.myremote.R;
import com.irremote.bluetooth.project.suren.myremote.models.DynamicGridAdapter;

/**
 * Created by suren on 29/12/15.
 */
public class BodyFragClass extends Fragment {

    public static String [] prgmNameList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
/*        int numOfChannels = 30;
        int numOfCols = 4;
        View view = inflater.inflate(R.layout.fraglay_body, container, false);
        GridLayout gl = (GridLayout) view.findViewById(R.id.channelsGrid);
        // gl.setOrientation(0);
        // gl.setRowCount(3);
        gl.setColumnCount(numOfCols);

        TextView[] text = new TextView[numOfChannels];

        for (int i = 0; i < numOfChannels; i++) {
            text[i] = new TextView(getActivity().getApplicationContext());
            text[i].setLayoutParams(new ViewGroup.LayoutParams
                    (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            text[i].setText(String.valueOf(i));
            text[i].setTextSize(25);
            text[i].setTextColor(getResources().getColor(R.color.colorPrimary));
            text[i].setPadding(50, 25, 10, 25);
            gl.addView(text[i]);
        }*/
        View view = inflater.inflate(R.layout.fraglay_body, container, false);
        prgmNameList = getResources().getStringArray(R.array.channelListArray);
        GridView gridView = (GridView) view.findViewById(R.id.channelsGrid);
        gridView.setAdapter(new DynamicGridAdapter(getActivity().getApplicationContext(),
                prgmNameList,
                getActivity(),"SET"));

        return view;
    }

}

