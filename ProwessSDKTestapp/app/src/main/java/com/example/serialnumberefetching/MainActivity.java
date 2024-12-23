package com.example.serialnumberefetching;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.leopard.api.Clscr;
import com.leopard.api.SAM;
import com.leopard.api.SerialPort1;
import com.leopard.api.SerialPort2;
import com.mydevice.sdk.MagCard;
import com.mydevice.sdk.SmartCard;
import com.mydevice.sdk.FPS;
import com.mydevice.sdk.HexString;
import com.mydevice.sdk.Printer;
import com.mydevice.sdk.Setup;
import com.prowess.sdk.ble.UartService;
import com.prowess.sdk.usb.USBService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import bluetooth.BluetoothComm;

public class MainActivity extends AppCompatActivity {

    Button btn_Connect, btn_disconnect, btn_fpsActivity, btn_printerActivity, btn_MagCardActivity, btn_SmartCardActivity, btn_Btconfig;
    public static Setup insSetup;
    UartService mService;
    public static Printer insPrinter = null;

    public static Setup bluetooth = null;
    public static FPS intFps = null;
    String btName = "";
    int BTMode = 1, BLEMode =2, USBIdenti5 = 3, USBComm =4;

    int commMode = BTMode;
    public static SmartCard insSmartcard = null;
    public static MagCard insMagcard = null;

//    public static LegendprinterActivity inslegend = null;
    RadioGroup radioGroupComm;
    private USBService usbService;


    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private static final int REQUEST_STOREGE_PERMISSION = 2;


    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH_PRIVILEGED
    };
    private static String[] PERMISSIONS_LOCATION = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH_PRIVILEGED
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mService = new UartService(getApplicationContext());
        radioGroupComm = findViewById(R.id.radioGroup);
        btn_fpsActivity = findViewById(R.id.fpsActivity_btn);
        btn_printerActivity = findViewById(R.id.PrinterActivity_btn);
        btn_SmartCardActivity = findViewById(R.id.SmartCardActivity_btn);
        btn_MagCardActivity = findViewById(R.id.MagCArdActivity_btn);
        btn_Btconfig = findViewById(R.id.Btconfig_btn);
        verifyStoragePermissions();

        try {
            insSetup = new Setup();
            boolean activate = insSetup.blActivateLibrary(MainActivity.this, R.raw.licence);
            if (activate == true) {
                Log.v("MainActivity", "Library Activated......");
            } else if (activate == false) {
                Log.v("MainActivity", "Library Not Activated...");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        btn_Connect = findViewById(R.id.connect_btn);
        btn_disconnect = findViewById(R.id.disconnect_btn);

        btn_Connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commMode == BTMode || commMode == BLEMode){
                    Intent intent = new Intent(MainActivity.this,Act_BTDiscovery.class);
                    startActivityForResult(intent,1);
                } else if (commMode == USBIdenti5) {
                    Init init = new Init();
                    init.execute();
                }else {
                    try {
                        usbService = new USBService(MainActivity.this);
                        usbService.connectToUSB();
                        try {
                            input = usbService.getUsbInputStream();
                            output = usbService.getUsbOutputStream();
                            intFps = new FPS( insSetup, output, input);
                            insPrinter = new Printer(insSetup,output,input);
                            insSmartcard = new SmartCard(insSetup,output,input);
                            insMagcard = new MagCard(insSetup,output,input);
                            Toast.makeText(MainActivity.this, "Connection Successful", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Connection UnSuccessful", Toast.LENGTH_SHORT).show();

                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        btn_disconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input = null;
                output = null;
                intFps = null;
                insPrinter = null;
                insSmartcard = null;
                insMagcard = null;
                if (commMode == BTMode){
                    BluetoothComm.mosOut = null;
                    BluetoothComm.misIn = null;
                    btcomm.closeConn();
                    Toast.makeText(MainActivity.this, "Bluetooth Disconnected", Toast.LENGTH_SHORT).show();
                } else if (commMode == BLEMode) {
                    mService.close();
                    Toast.makeText(MainActivity.this, "BLE Disconnected", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "Not Supported", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_fpsActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,FPSActivity.class);
                startActivity(intent);
            }
        });

        btn_printerActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,PrinterActivity.class);
                startActivity(intent);
            }
        });

        btn_SmartCardActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SmartCardActivity.class);
                startActivity(intent);
            }
        });

        btn_MagCardActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MagCardActivity.class);
                startActivity(intent);
            }
        });
        btn_Btconfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,BTActivity.class);
                startActivity(intent);
            }
        });

//        requestLocationPermission();
        CheckForCoarseLocationPermission();

        radioGroupComm.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radioGroup1){
                    commMode = BTMode;
                } else if (checkedId == R.id.radioGroup2) {
                    commMode = BLEMode;
                } else if (checkedId == R.id.radioGroup3) {
                    commMode = USBIdenti5;
                }else if (checkedId == R.id.radioGroup4){
                    commMode = USBComm;
                }else {
                    commMode = BTMode;
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("OnActivityResult", "onActivity result... requestcode" + requestCode + "...resultcode..." + resultCode + " intentdata...." + data);
        switch (requestCode) {
            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    btName = data.getExtras().getString("MAC");
                    if (commMode == BTMode){
                        new connSocketTask1().execute("");
                    } else if (commMode == BLEMode) {
                        new BLE_BluetoothAutoConnect().execute("");
                    }
                }
                break;
            default:
                break;
        }
    }
//    private static final int REQUEST_LOCATION_PERMISSION = 1;
//    private static final int REQUEST_STOREGE_PERMISSION = 2;

//    private static String[] PERMISSIONS_STORAGE = {
//            Manifest.permission.READ_EXTERNAL_STORAGE,
//            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            Manifest.permission.ACCESS_FINE_LOCATION,
//            Manifest.permission.ACCESS_COARSE_LOCATION,
//            Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
//            Manifest.permission.BLUETOOTH_SCAN,
//            Manifest.permission.BLUETOOTH_CONNECT,
//            Manifest.permission.BLUETOOTH_PRIVILEGED
//    };
//    private static String[] PERMISSIONS_LOCATION = {
//            Manifest.permission.ACCESS_FINE_LOCATION,
//            Manifest.permission.ACCESS_COARSE_LOCATION,
//            Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
//            Manifest.permission.BLUETOOTH_SCAN,
//            Manifest.permission.BLUETOOTH_CONNECT,
//            Manifest.permission.BLUETOOTH_PRIVILEGED
//    };
    private static final int REQUEST_EXTERNAL_STORAGE = 1;


    private void verifyStoragePermissions(){
        int permission1 = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permission2 = ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN);
        if (permission1 != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_STORAGE,
                    1
            );
        } else if (permission2 != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_LOCATION,
                    1
            );
        }
    }

//    public void verifyStoragePermissions() {
//        // Check if we have write permission
//        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        int permission1=ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
//        if (permission != PackageManager.PERMISSION_GRANTED && permission1 != PackageManager.PERMISSION_GRANTED){
//            // We don't have permission so prompt the user
//            ActivityCompat.requestPermissions(
//                    this,
//                    PERMISSIONS_STORAGE,
//                    REQUEST_EXTERNAL_STORAGE
//
//
//            );
//        }
//    }

    private void CheckForCoarseLocationPermission() {
        Log.e("TAG", " +++ CheckForCoarseLocationPermission");
        if (android.os.Build.VERSION.SDK_INT > 23) {
            // ANDROID 6.0 AND UP!
            boolean accessCoarseLocationAllowed = false;
            try {
                // Invoke checkSelfPermission method from Android 6 (API 23 and UP)
                java.lang.reflect.Method methodCheckPermission = Activity.class.getMethod("checkSelfPermission", String.class);
                Object resultObj = methodCheckPermission.invoke(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
                int result = Integer.parseInt(resultObj.toString());
                if (result == PackageManager.PERMISSION_GRANTED) {
                    accessCoarseLocationAllowed = true;
                }
            } catch (Exception ex) {
            }
            if (accessCoarseLocationAllowed) {
                return;
            }
            try {
                // We have to invoke the method "void requestPermissions (Activity activity, String[] permissions, int requestCode) "
                // from android 6
                java.lang.reflect.Method methodRequestPermission = Activity.class.getMethod("requestPermissions", String[].class, int.class);
                methodRequestPermission.invoke(this, new String[]
                        {
                                android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.BLUETOOTH_CONNECT,
                                Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.NFC, Manifest.permission.MANAGE_EXTERNAL_STORAGE, Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.MANAGE_MEDIA,Manifest.permission.READ_PHONE_STATE

                        }, 0x12345);
            } catch (Exception ex) {
            }
        }
    }
//    public void requestLocationPermission(){
//        String [] permissions ={Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.WRITE_EXTERNAL_STORAGE};
////
//
//        if((ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED)){
//            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION_PERMISSION);
//            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_STOREGE_PERMISSION);
//            Log.d(TAG, "requestLocationPermission: Access permission success");
//        }else{
//            Log.d(TAG, "requestLocationPermission: Permission already ");
//        }
//
//    }
    BluetoothComm btcomm;
    public static OutputStream output;
    public static InputStream input;


    private class connSocketTask1 extends AsyncTask<String, String, Integer> {
        private ProgressDialog mpd = null;
        private ProgressDialog tmp = null;
        int iBatteryVal;
        String sFirmware;
        String sSerialnumber,sensorSerialNumber;
        private static final int CONN_FAIL = 0x01;
        private static final int CONN_SUCCESS = 0x02;
        //private ProgressDialog mpd = null;
        /**Constants: connection fails*/
        //private static final int CONN_FAIL = 0x01;
        /**
         * Constant: the connection is established
         */
        //private static final int CONN_SUCCESS = 0x02;
        private static final int CONN_NO_DEVICE = 0x03;
        private static final int IGNORECASE = 0x04;
        boolean bRetVal = false;
        String TAG = "Connect Task";

        /**
         * Thread start initialization
         */
        @Override
        public void onPreExecute() {
            this.tmp = new ProgressDialog(MainActivity.this);
            this.tmp.setMessage("Connecting....");
            this.tmp.setCancelable(false);
            this.tmp.setCanceledOnTouchOutside(false);
            this.tmp.show();
        }

        @SuppressLint("WrongThread")
        @Override
        protected Integer doInBackground(String... arg0) {
            Log.e(TAG, "ConnectSocketTask1....doinBackground..");
            Log.e(TAG, "connSocketTask Dev to Connect : " + arg0[0]);
            if (btcomm != null && btcomm.isConnect()) {
                // if already connected close the existing connection
                btcomm.closeConn();
            }
            btcomm = new BluetoothComm(btName);
            try {
                if (btcomm.equals(BluetoothDevice.ACTION_ACL_CONNECTED)) {
                    Log.e(TAG, "doInBackground: closeconn++++++++++");
                    btcomm.closeConn();
                } else {
                    Log.e(TAG, "doInBackground: createConn1+++++++++++++");
                    bRetVal = btcomm.createConn1();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "Exception Occuredd...." + e);
            }

            if (bRetVal) {
                Log.e(TAG, "Bluetoothcomm... bRetval......" + bRetVal);


//                this.tmp.dismiss();
                try {
                    Thread.sleep(2000);
                    output = BluetoothComm.mosOut;
                    input = BluetoothComm.misIn;
                    SystemClock.sleep(100);
                    insPrinter = new Printer(MainActivity.insSetup, output, input);
                    intFps = new FPS(MainActivity.insSetup, output, input);
                    insSmartcard = new SmartCard(insSetup, output, input);
                    insMagcard = new MagCard(insSetup, output, input);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return CONN_SUCCESS;


            } else {
                Log.e(TAG, "Bluetoothcomm...bRetval......" + bRetVal);
                return CONN_FAIL;
            }

        }

        @Override
        public void onPostExecute(Integer result) {

            if (CONN_SUCCESS == result) {
                this.tmp.dismiss();
                insSetup.iResetDevice(output, input);
                Toast.makeText(MainActivity.this, "Connection Successful", Toast.LENGTH_SHORT).show();

            } else if (CONN_FAIL == result) {
                Log.e(TAG, ">>>>> CONN_FAIL 11111Bletooth connection fail");
                this.tmp.dismiss();
                Toast.makeText(MainActivity.this, "Connection UnSuccessful", Toast.LENGTH_SHORT).show();

            } else if (IGNORECASE == result) {
                this.tmp.dismiss();
                Toast.makeText(MainActivity.this, "Connection UnSuccessful", Toast.LENGTH_SHORT).show();

            } else {
                this.tmp.dismiss();
                Toast.makeText(MainActivity.this, "Connection UnSuccessful", Toast.LENGTH_SHORT).show();

            }
        }
    }

    private class BLE_BluetoothAutoConnect extends AsyncTask<String, String, Integer> {
        /**
         * Process waits prompt box
         */ //TODO
        // private ProgressDialog mpd = null;
        private ProgressDialog tmp = null;

        @Override
        public void onPreExecute() {
            Log.e(TAG, "BLE Connection onPreExecute");
            SystemClock.sleep(10);
            this.tmp = new ProgressDialog(MainActivity.this);
            this.tmp.setMessage("Connecting....");
            this.tmp.setCancelable(false);
            this.tmp.setCanceledOnTouchOutside(false);
            this.tmp.show();

        }

        @Override
        protected Integer doInBackground(String... arg0) {

            if ((mService.BLEconnected) || BluetoothComm.BTconnected) {
                return 2;

            } else {
                Log.e(TAG, "doInBackground: Action connected");

                mService.connect(btName);
                input = mService.getInputStream();
                output = mService.getOutStream();
                SystemClock.sleep(2000);
                if (!mService.BLEconnected) {
                    return 4;
                }
                try {
                    intFps = new FPS( insSetup, output, input);
                    insPrinter = new Printer(insSetup, output, input);
                    insSmartcard = new SmartCard(insSetup, output, input);
                    insMagcard = new MagCard(insSetup, output, input);
                    return 0;
                } catch (Exception e) {
                    e.printStackTrace();
                    return 1;
                }


            }
        }

        @Override
        public void onPostExecute(Integer result) {
            this.tmp.dismiss();
            Log.e(TAG, "BLE Connection onPostExecute" + result);
            if (result == 0) {

                try {
                    insSetup.iResetDevice(output, input);
                    Toast.makeText(MainActivity.this, "Connection Successful", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Connection UnSuccessful", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "Connection UnSuccessful", Toast.LENGTH_SHORT).show();
            }
        }


    }

    public class Init extends AsyncTask<Integer, Integer, Integer> {
        private ProgressDialog mpd = null;

        @Override
        protected void onPreExecute() {
            this.mpd = new ProgressDialog(MainActivity.this);
            mpd = ProgressDialog.show(MainActivity.this, "Processing", "Processing.....");

        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            try {
                MainActivity.intFps = new FPS(insSetup, output, input, FPS.ConnectionMode.USB_OTG);
                int iRetVal = MainActivity.intFps.init_Usb_FPS();
                Log.e(TAG, "Init Response " + iRetVal);
                return iRetVal;

            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }

        }

        @Override
        protected void onPostExecute(Integer integer) {
            Log.e(TAG, "onPostExecute " + integer);
            try {
                if (integer == -5) {
                    this.mpd.dismiss();
                    Toast.makeText(MainActivity.this, "Init Successful", Toast.LENGTH_SHORT).show();
                } else {
                    this.mpd.dismiss();
                    Toast.makeText(MainActivity.this, "Init UnSuccessful", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            super.onPostExecute(integer);
        }
    }

}





