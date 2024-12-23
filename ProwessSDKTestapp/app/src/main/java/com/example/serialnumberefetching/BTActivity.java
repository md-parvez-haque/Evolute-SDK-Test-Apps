package com.example.serialnumberefetching;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aratek.trustfinger.sdk.MultiFingerIndex;
import com.leopard.api.FPS;
import com.leopard.api.FpsConfig;
import com.leopard.api.HexString;
import com.mydevice.sdk.FpsImageAPI;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import bluetooth.BluetoothComm;


public class BTActivity extends AppCompatActivity {

    private static final String TAG = "BT Activity";

    EditText edt_AddtextB, edt_AddtextBC;

    Button btn_setbt;

    String sAddData;

    Context context = this;

    int iRetVal;

    int DEVICE_NOTCONNECTED = 100;

    private TextView logText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btconfig);
        btn_setbt = findViewById(R.id.setbt_btn);
        edt_AddtextB = (EditText) findViewById(R.id.AddtextB);
        edt_AddtextBC = (EditText) findViewById(R.id.AddtextBC);
        logText = findViewById(R.id.logText);

        btn_setbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    SetBtnameAsync setBtnameAsync = new SetBtnameAsync();
                    setBtnameAsync.execute();
                }
        });
    }


            private class SetBtnameAsync extends AsyncTask<Integer, Integer, Integer> {

                @Override
                protected Integer doInBackground(Integer... params) {
                    try {
                        sAddData = edt_AddtextB.getText().toString();
                        Log.e("testlog", "Enter BT name is : " + sAddData);
                        String address = edt_AddtextBC.getText().toString();
                        Log.e("testlog", "Entered BT MAC is : " + address);
                        iRetVal = MainActivity.insSetup.iSetBtNameAdd(MainActivity.output,MainActivity.input,sAddData,address);
                        Log.e("testlog","isetBtname is :" + iRetVal);


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                logText.setText("Entered BT name is : " + sAddData +"\n" + "Entered BT MAC is : " + address + "\n" + "isetBtname is :" + iRetVal  );

                            }
                        });
                    } catch (NullPointerException e) {
                        iRetVal = DEVICE_NOTCONNECTED;
                        return iRetVal;
                    }
                    return iRetVal;
                }
            }}

