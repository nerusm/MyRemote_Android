package com.irremote.bluetooth.project.suren.myremote.uiinterface;

/**
 * Created by suren on 30/12/15.
 */
public interface OnItemSelectedListener {
    //public void onItemSelected(String link);
    public void sendCommandInterface(String commandString, String unitName, String comandType);
}
