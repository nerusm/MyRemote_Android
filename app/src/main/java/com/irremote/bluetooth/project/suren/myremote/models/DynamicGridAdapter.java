package com.irremote.bluetooth.project.suren.myremote.models;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.irremote.bluetooth.project.suren.myremote.R;
import com.irremote.bluetooth.project.suren.myremote.uiinterface.MainActivity;
import com.irremote.bluetooth.project.suren.myremote.uiinterface.OnItemSelectedListener;

/**
 * Created by suren on 30/12/15.
 */
public class DynamicGridAdapter extends BaseAdapter {

    Context context;
    String[] result;
    private static LayoutInflater inflater=null;
    String unitName;
private OnItemSelectedListener listener;


    public class Holder{
        Button btn;
    }

    public DynamicGridAdapter(Context context, String[] btnList ,Activity activity, String unitName) {
        this.context = context;
        this.unitName = unitName;
        result = btnList;
        listener = (OnItemSelectedListener) activity;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    @Override

    public int getCount() {
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder = new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.justbutton, null);
        holder.btn = (Button) rowView.findViewById(R.id.jstButton);
        // holder.btn.setText(result[i]);
//        String x= "aadithiya";

        int resourceID = context.getResources().getIdentifier(result[i], "drawable", context.getPackageName());
        if(resourceID ==0 ){
//            resourceID = context.getResources().getIdentifier("default_channel","drawable",context.getPackageName());
//            Log.d(MainActivity.TAG,result[i]);
            holder.btn.setText(result[i].toString());
        } else {
            Drawable drawable = (Drawable) context.getResources().getDrawable(resourceID);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                holder.btn.setBackground(drawable);
            } else {
                holder.btn.setBackgroundDrawable(drawable);
            }
        }

        holder.btn.setTag(result[i]);
        /*Log.d(MainActivity.TAG,":"+resourceID);
        Log.d(MainActivity.TAG, "~"+drawable);*/


//        holder.btn.setBackground
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button btnj = (Button) view;
                mSendCommand(btnj.getTag().toString());

//                Toast.makeText(context, "You clicked :" + btnj.getTag(), Toast.LENGTH_SHORT).show();
            }
        });
        return rowView;
    }

    private void mSendCommand(String s) {
//        Toast.makeText(context,"sfsdfsdfsdf",Toast.LENGTH_SHORT).show();
        listener.sendCommandInterface(s,unitName, MainActivity.CMDTYPE_NUMBER);
    }
}
//context.getResources().getIdentifier(x, "drawable", context.getPackageName())