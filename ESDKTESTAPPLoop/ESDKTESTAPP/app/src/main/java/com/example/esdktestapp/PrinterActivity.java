package com.example.esdktestapp;


import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.evolute.printservice.AidlPrint;
import com.evolute.printservice.BarcodeType;
import com.evolute.sdkservice.ESDKConstant;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrinterActivity extends AppCompatActivity {

    private static final String TAG = "Printer Activity";

    Button btn_poweron, btn_poweroff, btn_clearLog, btn_getversion, btn_checkpaper, btn_printerstatus, btn_returncode, btn_flushbuff, btn_testprint;

    Button btn_printtext, btn_print, btn_rawdata, btn_justification, btn_printQR, btn_printimage, btn_printbarcode128, btn_receiptprint;

    Spinner spn_FontType, spn_Alignment, spn_ImageAlign;


    EditText edt_textenter, edt_LeftText, edt_CenterText, edt_RightText, edt_FontSize, edt_QRcodeData, edt_barcodeData, edt_FontSizebarcode;

    byte[] Rawdata = {'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E',
            'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E',
            'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E',
            'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E',
            'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E',
            'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', +'\n'};
    private TextView txtViewlogs;

    private AidlPrint PrinterService = null;


    final ServiceConnection con_printer = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            PrinterService = AidlPrint.Stub.asInterface(service);
            Log.e(TAG, "onServiceConnected: to PrinterSevice");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    protected void PrinterServiceBind() {
        try {
            Intent intent = new Intent();
            intent.setAction("com.evolute.service.printservice");
            intent.setPackage("com.evolute.sdkservice");
            bindService(intent, con_printer, Context.BIND_AUTO_CREATE);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void fontSize() {
        String[] FontsType = {

                "Width_x_Height_16x16_Small_24",
                "Width_x_Height_8x16_Small_42",
                "Width_x_Height_8x16_Small_32",
                "Width_x_Height_Double"

        };
        ArrayAdapter<String> font = new ArrayAdapter<>(this,
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item, FontsType);
        font.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spn_FontType.setAdapter(font);

    }

    protected void textAlignment() {
        String[] Align = {"Left",
                "Center",
                "Right"
        };
        ArrayAdapter<String> Alignmenttext = new ArrayAdapter<>(this,
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item, Align);
        Alignmenttext.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spn_Alignment.setAdapter(Alignmenttext);

    }

    protected void imageAlign() {
        String[] ImageAlignment = {"ALIGN_LEFT", "ALIGN_CENTER", "ALIGN_RIGHT"};
        ArrayAdapter<String> AilgnList = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, ImageAlignment);
        AilgnList.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spn_ImageAlign.setAdapter(AilgnList);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_printer);
        PrinterServiceBind();

        txtViewlogs = findViewById(R.id.txtViewlogs);

        btn_poweron = findViewById(R.id.btn_poweron);
        btn_poweroff = findViewById(R.id.btn_poweroff);
        btn_clearLog = findViewById(R.id.btn_clearLog);
        btn_getversion = findViewById(R.id.btn_getversion);
        btn_checkpaper = findViewById(R.id.btn_checkpaper);
        btn_printerstatus = findViewById(R.id.btn_printerstatus);
        btn_returncode = findViewById(R.id.btn_returncode);
        btn_flushbuff = findViewById(R.id.btn_flushbuff);
        btn_testprint = findViewById(R.id.btn_testprint);
        edt_textenter = findViewById(R.id.edt_textenter);
        btn_printtext = findViewById(R.id.btn_printtext);
        spn_FontType = findViewById(R.id.spn_FontType);
        fontSize();
        spn_Alignment = findViewById(R.id.spn_Alignment);
        textAlignment();
        btn_print = findViewById(R.id.btn_print);
        btn_rawdata = findViewById(R.id.btn_rawdata);
        edt_LeftText = findViewById(R.id.edt_LeftText);
        edt_RightText = findViewById(R.id.edt_RightText);
        edt_CenterText = findViewById(R.id.edt_CenterText);
        edt_FontSize = findViewById(R.id.edt_FontSize);
        btn_justification = findViewById(R.id.btn_justification);
        spn_ImageAlign = findViewById(R.id.spn_ImageAlign);
        imageAlign();
        edt_QRcodeData = findViewById(R.id.edt_QRcodeData);
        btn_printQR = findViewById(R.id.btn_printQR);
        btn_printimage = findViewById(R.id.btn_printimage);
        edt_FontSizebarcode = findViewById(R.id.edt_FontSizebarcode);
        edt_barcodeData = findViewById(R.id.edt_barcodeData);
        btn_printbarcode128 = findViewById(R.id.btn_printbarcode128);
        btn_receiptprint = findViewById(R.id.btn_receiptprint);

        btn_clearLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtViewlogs.setText("");
            }
        });
        btn_poweron.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PoweronAsync poweronAsync = new PoweronAsync();
                poweronAsync.execute();
            }
        });

        btn_poweroff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PoweroffAsync poweroffAsync = new PoweroffAsync();
                poweroffAsync.execute();
            }
        });

        btn_getversion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetversionAsync getversionAsync = new GetversionAsync();
                getversionAsync.execute();

            }
        });
        btn_checkpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckpaperAsync checkpaperAsync = new CheckpaperAsync();
                checkpaperAsync.execute();
            }
        });

        btn_printerstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrinterstatusAsync printerstatusAsync = new PrinterstatusAsync();
                printerstatusAsync.execute();

            }
        });

        btn_returncode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReturncodeAsync returncodeAsync = new ReturncodeAsync();
                returncodeAsync.execute();

            }
        });

        btn_flushbuff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlushbuffAsync flushbuffAsync = new FlushbuffAsync();
                flushbuffAsync.execute();

            }
        });

        btn_testprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestprintAsync testprintAsync = new TestprintAsync();
                testprintAsync.execute();

            }
        });

        btn_printtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextprintAsyc textprintAsyc = new TextprintAsyc();
                textprintAsyc.execute();
            }
        });
        btn_print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrintAsync printAsyc = new PrintAsync();
                printAsyc.execute();
            }
        });
        btn_rawdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RawdataAsync rawdataAsync = new RawdataAsync();
                rawdataAsync.execute();
            }
        });
        btn_justification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JustificationAsync justificationAsync = new JustificationAsync();
                justificationAsync.execute();
            }
        });
        btn_printQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrintqrAsyc printqrAsyc = new PrintqrAsyc();
                printqrAsyc.execute();
            }
        });
        btn_printimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrintimageAsyc printimageAsyc = new PrintimageAsyc();
                printimageAsyc.execute();
            }
        });
        btn_printbarcode128.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Printbarcode128Async printbarcode128Async = new Printbarcode128Async();
                printbarcode128Async.execute();
            }
        });
        btn_receiptprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReceiptprintAsync receiptprintAsync = new ReceiptprintAsync();
                receiptprintAsync.execute();
            }
        });
    }

    private class PoweronAsync extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer... integers) {
            try {
                int a = 0;

                PrinterService.printerPowerON();
                a = PrinterService.iGetReturnCode();
                Log.e(TAG, "Printer power on : " + a);
                int finalA = a;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtViewlogs.setText("Printer power is on successfully : " + finalA);
                    }
                });
                new Handler(Looper.getMainLooper()).post(new Runnable() {

                    @Override
                    public void run() {

                        Toast.makeText(PrinterActivity.this, "Printer power is on successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            return null;
        }
    }

    private class PoweroffAsync extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer... integers) {
            try {
                int a = 0;
                PrinterService.printerPowerOFF();
                a = PrinterService.iGetReturnCode();
                Log.e(TAG, "Printer power is off successfully : " + a);
                int finalA = a;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtViewlogs.setText("Printer power is off successfully : " + finalA);
                    }
                });
                new Handler(Looper.getMainLooper()).post(new Runnable() {

                    @Override
                    public void run() {

                        Toast.makeText(PrinterActivity.this, "Printer power is off successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            return null;
        }
    }

    private class GetversionAsync extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer... integers) {
            try {
                String get = PrinterService.getVersion();
                Log.e(TAG, "Get version is  : " + get);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtViewlogs.setText("Device Printer firmware version is  : " + get);
                    }
                });
                new Handler(Looper.getMainLooper()).post(new Runnable() {

                    @Override
                    public void run() {

                        Toast.makeText(PrinterActivity.this, "Device Printer firmware version is  : " + get, Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            return null;
        }
    }

    private class CheckpaperAsync extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer... integers) {
            try {
                boolean bvalue = PrinterService.checkPaper();
                Log.e(TAG, "check paper is : " + bvalue);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtViewlogs.setText("check paper is : " + bvalue);
                    }
                });
                new Handler(Looper.getMainLooper()).post(new Runnable() {

                    @Override
                    public void run() {

                        Toast.makeText(PrinterActivity.this, "check paper is : " + bvalue, Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            return null;
        }
    }

    private class PrinterstatusAsync extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer... integers) {
            try {
                int a = PrinterService.iGetPrinterStatus();
                Log.e(TAG, "Device printer status is : " + a);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtViewlogs.setText("Device printer status is : " + a);
                    }
                });
                new Handler(Looper.getMainLooper()).post(new Runnable() {

                    @Override
                    public void run() {

                        Toast.makeText(PrinterActivity.this, "Device printer status is : " + a, Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            return null;
        }
    }

    private class ReturncodeAsync extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer... integers) {
            try {
                int b = PrinterService.iGetReturnCode();
                Log.e(TAG, "iGetReturnCode is : " + b);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtViewlogs.setText("iGetReturnCode is : " + b);
                    }
                });
                new Handler(Looper.getMainLooper()).post(new Runnable() {

                    @Override
                    public void run() {

                        Toast.makeText(PrinterActivity.this, "iGetReturnCode is : " + b, Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            return null;
        }
    }

    private class FlushbuffAsync extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer... integers) {
            try {
                int a = PrinterService.iFlushBuffer();
                Log.e(TAG, "iFlushBuffer is : " + a);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtViewlogs.setText("iFlushBuffer is : " + a);
                    }
                });
                new Handler(Looper.getMainLooper()).post(new Runnable() {

                    @Override
                    public void run() {

                        Toast.makeText(PrinterActivity.this, "iFlushBuffer is : " + a, Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            return null;
        }
    }

    private class TestprintAsync extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer... integers) {
//            int loopVariable = 0;
//            while (loopVariable < 100) {
            try {
                int b = 0;
                b = PrinterService.TestPrint();
                Log.e(TAG, "TestPrint is : " + b);
                int finalB = b;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtViewlogs.setText("TestPrint is : " + finalB);
                    }
                });
//                    if (b != ESDKConstant.PRINTER_SUCCESS) {
//                        break;
//                    }
//                    new Handler(Looper.getMainLooper()).post(new Runnable() {
//
//                        @Override
//                        public void run() {
//
//                            Toast.makeText(PrinterActivity.this, "Testprint is : " + b, Toast.LENGTH_SHORT).show();
//                        }
//                    });

//                    loopVariable++;
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            return null;
        }

    }


    private class TextprintAsyc extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer... integers) {

            int loopVariable = 0;
            while (loopVariable < 100) {

                try {

                    PrinterService.iFlushBuffer();
                    int c = PrinterService.iUpdateBuffer(ESDKConstant.FontSize.valueOf(spn_FontType.getSelectedItem().toString()));
                    int d = PrinterService.iUpdateBuffer(ESDKConstant.TextAlignment.valueOf(spn_Alignment.getSelectedItem().toString()));
                    String text = edt_textenter.getText().toString();
//                    int ret = PrinterService.iUpdateBuffer(text + "\n");
                    int ret1 = PrinterService.iUpdateBuffer("EvoluteFintechInnovationsPvtLtd1WETY!@#$%^" + "\n");
                    int ret2 = PrinterService.iUpdateBuffer("EvoluteFintechInnovationsPvtLtd1WETY!@#$%^" + "\n");
                    int ret3 = PrinterService.iUpdateBuffer("EvoluteFintechInnovationsPvtLtd1WETY!@#$%^" + "\n");
                    int ret4 = PrinterService.iUpdateBuffer("EvoluteFintechInnovationsPvtLtd1WETY!@#$%^" + "\n");
                    int ret5 = PrinterService.iUpdateBuffer("EvoluteFintechInnovationsPvtLtd1WETY!@#$%^" + "\n");
                    int ret6 = PrinterService.iUpdateBuffer("EvoluteFintechInnovationsPvtLtd1WETY!@#$%^" + "\n");
                    int ret7 = PrinterService.iUpdateBuffer("EvoluteFintechInnovationsPvtLtd1WETY!@#$%^" + "\n");
                    int ret8 = PrinterService.iUpdateBuffer("EvoluteFintechInnovationsPvtLtd1WETY!@#$%^" + "\n");
                    int ret9 = PrinterService.iUpdateBuffer("EvoluteFintechInnovationsPvtLtd1WETY!@#$%^" + "\n");
                    int ret10 = PrinterService.iUpdateBuffer("EvoluteFintechInnovationsPvtLtd1WETY!@#$%^" + "\n");
                    int ret11 = PrinterService.iUpdateBuffer("EvoluteFintechInnovationsPvtLtd1WETY!@#$%^" + "\n");
                    int ret12 = PrinterService.iUpdateBuffer("EvoluteFintechInnovationsPvtLtd1WETY!@#$%^" + "\n");
                    int ret13 = PrinterService.iUpdateBuffer("EvoluteFintechInnovationsPvtLtd1WETY!@#$%^" + "\n");
                    int ret14 = PrinterService.iUpdateBuffer("EvoluteFintechInnovationsPvtLtd1WETY!@#$%^" + "\n");
                    int ret15 = PrinterService.iUpdateBuffer("EvoluteFintechInnovationsPvtLtd1WETY!@#$%^" + "\n");
                    int ret16 = PrinterService.iUpdateBuffer("EvoluteFintechInnovationsPvtLtd1WETY!@#$%^" + "\n");
                    int ret17 = PrinterService.iUpdateBuffer("EvoluteFintechInnovationsPvtLtd1WETY!@#$%^" + "\n");
                    int ret18 = PrinterService.iUpdateBuffer("EvoluteFintechInnovationsPvtLtd1WETY!@#$%^" + "\n");
                    int ret19 = PrinterService.iUpdateBuffer("EvoluteFintechInnovationsPvtLtd1WETY!@#$%^" + "\n");
                    int ret20 = PrinterService.iUpdateBuffer("EvoluteFintechInnovationsPvtLtd1WETY!@#$%^" + "\n");
                    int ret21 = PrinterService.iUpdateBuffer("EvoluteFintechInnovationsPvtLtd1WETY!@#$%^" + "\n");
                    int ret22 = PrinterService.iUpdateBuffer("EvoluteFintechInnovationsPvtLtd1WETY!@#$%^" + "\n");
                    int ret23 = PrinterService.iUpdateBuffer("EvoluteFintechInnovationsPvtLtd1WETY!@#$%^" + "\n");
                    int ret24 = PrinterService.iUpdateBuffer("EvoluteFintechInnovationsPvtLtd1WETY!@#$%^" + "\n");
                    int ret25 = PrinterService.iUpdateBuffer("EvoluteFintechInnovationsPvtLtd1WETY!@#$%^" + "\n");
                    int ret26 = PrinterService.iUpdateBuffer("EvoluteFintechInnovationsPvtLtd1WETY!@#$%^" + "\n");
                    int ret27 = PrinterService.iUpdateBuffer("EvoluteFintechInnovationsPvtLtd1WETY!@#$%^" + "\n");
                    int ret28 = PrinterService.iUpdateBuffer("EvoluteFintechInnovationsPvtLtd1WETY!@#$%^" + "\n");
                    int ret29 = PrinterService.iUpdateBuffer("EvoluteFintechInnovationsPvtLtd1WETY!@#$%^" + "\n");
                    int ret30 = PrinterService.iUpdateBuffer("EvoluteFintechInnovationsPvtLtd1WETY!@#$%^" + "\n");
                    int ret31 = PrinterService.iUpdateBuffer("EvoluteFintechInnovationsPvtLtd1WETY!@#$%^" + "\n");
                    int ret32 = PrinterService.iUpdateBuffer("EvoluteFintechInnovationsPvtLtd1WETY!@#$%^" + "\n");
                    int ret33 = PrinterService.iUpdateBuffer("EvoluteFintechInnovationsPvtLtd1WETY!@#$%^" + "\n");
                    int ret34 = PrinterService.iUpdateBuffer("EvoluteFintechInnovationsPvtLtd1WETY!@#$%^" + "\n");
                    int ret35 = PrinterService.iUpdateBuffer("EvoluteFintechInnovationsPvtLtd1WETY!@#$%^" + "\n");
                    int ret37 = PrinterService.iUpdateBuffer("EvoluteFintechInnovationsPvtLtd1WETY!@#$%^" + "\n");
                    int ret38 = PrinterService.iUpdateBuffer("EvoluteFintechInnovationsPvtLtd1WETY!@#$%^" + "\n");
                    int ret39 = PrinterService.iUpdateBuffer("EvoluteFintechInnovationsPvtLtd1WETY!@#$%^" + "\n");
                    int a = PrinterService.iStartPrinting();
                    Log.e(TAG, "Print By Font size is : " + ret1);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            txtViewlogs.setText("Print By Font size is : " + ret1 + "\n"
                                    + "Return Code =" + a + "\n" + "Return code for Font type =" + c + "\n" + "Return code for alignment =" + d + "\n");
                        }
                    });
                    new Handler(Looper.getMainLooper()).post(new Runnable() {

                        @Override
                        public void run() {

                            Toast.makeText(PrinterActivity.this, "Print By Font size is :  " + ret1, Toast.LENGTH_SHORT).show();
                            Toast.makeText(PrinterActivity.this, "Text Print is =" + a, Toast.LENGTH_SHORT).show();
                            Toast.makeText(PrinterActivity.this, "Return code Font type =" + c, Toast.LENGTH_SHORT).show();
                            Toast.makeText(PrinterActivity.this, "Return code for alignment =" + d, Toast.LENGTH_SHORT).show();
                        }
                    });

                    loopVariable++;
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }

            return null;
        }

    }

    private class PrintAsync extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer... integers) {
            int loopVariable = 0;
            while (loopVariable < 100) {
                try {

                    String text = edt_textenter.getText().toString();
                    int b = PrinterService.printText(text + "\n");
                    Log.e(TAG, "Print Text is : " + b);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            txtViewlogs.setText("Print Text is : " + b);
                        }
                    });
                    new Handler(Looper.getMainLooper()).post(new Runnable() {

                        @Override
                        public void run() {

                            Toast.makeText(PrinterActivity.this, "Print Text is : " + b, Toast.LENGTH_SHORT).show();
                        }
                    });
                    loopVariable++;
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
            return null;
        }
    }

    private class RawdataAsync extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer... integers) {

            int loopVariable = 0;
            while (loopVariable < 100) {
                try {
                    int a = 0;


                    PrinterService.iUpdateBuffer(ESDKConstant.FontSize.Width_x_Height_16x16_Small_24);
                    PrinterService.sendRAWData(Rawdata);
                    a = PrinterService.iGetReturnCode();
                    Log.e(TAG, "sendRAWDATA is : " + a);
                    int finalA = a;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            txtViewlogs.setText("send RAWDATA is : " + finalA);
                        }
                    });
                    if (a != ESDKConstant.PRINTER_SUCCESS) {
                        break;
                    }
                    int finalA1 = a;
                    new Handler(Looper.getMainLooper()).post(new Runnable() {

                        @Override
                        public void run() {

                            Toast.makeText(PrinterActivity.this, "send RAWDATA is : " + finalA1, Toast.LENGTH_SHORT).show();
                        }
                    });
                    loopVariable++;

                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
            return null;
        }
    }


    private class JustificationAsync extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer... integers) {
            int loopVariable = 0;
            while (loopVariable < 100) {
                try {

                    String left = edt_LeftText.getText().toString();
                    String center = edt_CenterText.getText().toString();
                    String right = edt_RightText.getText().toString();
                    int Fontsize = Integer.parseInt(edt_FontSize.getText().toString());
                    List<List<String>> rows = new ArrayList<>();
                    rows.add(Arrays.asList(left, center, right));
                    boolean[] isbold = {true};
                    boolean[] isitalic = {false};
                    int ret = PrinterService.printTextJustification(rows, Fontsize, isbold, isitalic);
                    Log.e(TAG, "Print Text Justification is : " + ret);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            txtViewlogs.setText("Print Text Justification is : " + ret);
                        }
                    });
                    new Handler(Looper.getMainLooper()).post(new Runnable() {

                        @Override
                        public void run() {

                            Toast.makeText(PrinterActivity.this, "Print Text Justification is : " + ret, Toast.LENGTH_SHORT).show();
                        }
                    });

                    loopVariable++;
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }

            }
            return null;
        }

    }

    private class PrintqrAsyc extends AsyncTask<Integer, Integer, Integer> {

//        int iRetValue = 0;

        @Override
        protected Integer doInBackground(Integer... integers) {
            int loopVariable = 0;
            int iRetValue = 0;
            while (loopVariable < 100) {
                try {
                    String align = spn_ImageAlign.getSelectedItem().toString();
                    if (align.equalsIgnoreCase("ALIGN_LEFT")) {
                        iRetValue = PrinterService.printQRCode(edt_QRcodeData.getText().toString(), 1, ESDKConstant.ImageAlignment.ALIGN_LEFT);
                        Log.e(TAG, "print QR code left is : " + iRetValue);

                    } else if (align.equalsIgnoreCase("ALIGN_CENTER")) {
                        iRetValue = PrinterService.printQRCode(edt_QRcodeData.getText().toString(), 1, ESDKConstant.ImageAlignment.ALIGN_CENTER);
                        Log.e(TAG, "print QR code center is : " + iRetValue);

                    } else if (align.equalsIgnoreCase("ALIGN_RIGHT")) {
                        iRetValue = PrinterService.printQRCode(edt_QRcodeData.getText().toString(), 1, ESDKConstant.ImageAlignment.ALIGN_RIGHT);
                        Log.e(TAG, "print QR code right is : " + iRetValue);

                    }


                    int finalIRetValue = iRetValue;
                    new Handler(Looper.getMainLooper()).post(new Runnable() {

                        @Override
                        public void run() {

                            Toast.makeText(PrinterActivity.this, "print QR code Toast is : " + finalIRetValue, Toast.LENGTH_SHORT).show();
//                        Toast.makeText(PrinterActivity.this, "Return Code =" + a, Toast.LENGTH_SHORT).show();
//                        Toast.makeText(PrinterActivity.this, "Return code Font type =" + c, Toast.LENGTH_SHORT).show();
//                        Toast.makeText(PrinterActivity.this, "Return code for alignment =" + d, Toast.LENGTH_SHORT).show();

                        }
                    });
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            txtViewlogs.setText("print QR code is : " + finalIRetValue);
                        }
                    });


                    if (iRetValue != ESDKConstant.PRINTER_SUCCESS) {
                        break;
                    }
                    loopVariable++;
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
            return null;
        }
    }

//        @Override
//        protected void onPostExecute(Integer integer) {
//            txtViewlogs.setText("print QR code is : " + iRetValue + "\n");
//        }
//    }


    private class PrintimageAsyc extends AsyncTask<Integer, Integer, Integer> {

        @SuppressLint("ResourceType")
        @Override
        protected Integer doInBackground(Integer... integers) {


            int loopVariable = 0;
            while (loopVariable < 100) {
                try {
                    PrinterService.vSetLogEnable(true);
                    Bitmap bitmap;
                    InputStream isFileInput;
//                    isFileInput = getApplicationContext().getResources().openRawResource(R.drawable.panda);
//                    bitmap = BitmapFactory.decodeStream(isFileInput);
//                    PrinterService.printImg(bitmap);
//                    if (PrinterService.iGetReturnCode() != ESDKConstant.PRINTER_SUCCESS) {
//                        Log.e(TAG, "Bmp print return value is " + PrinterService.iGetReturnCode());
//                        break;
////                    Toast.makeText(this, "Bmp print return value is :" +PrinterService.iGetReturnCode(), Toast.LENGTH_LONG).show();
//                    }
//                    isFileInput = getApplicationContext().getResources().openRawResource(R.drawable.fish);
//                    bitmap = BitmapFactory.decodeStream(isFileInput);
//                    PrinterService.printImg(bitmap);
//                    if (PrinterService.iGetReturnCode() != ESDKConstant.PRINTER_SUCCESS) {
//                        Log.e(TAG, "Bmp print return value is " + PrinterService.iGetReturnCode());
//                        break;
////                    Toast.makeText(this, "Bmp print return value is  "+printerService.iGetReturnCode(), Toast.LENGTH_LONG).show();
//                    }
                    isFileInput = getApplicationContext().getResources().openRawResource(R.drawable.panda);
                    bitmap = BitmapFactory.decodeStream(isFileInput);
                    PrinterService.printImg(bitmap);
                    if (PrinterService.iGetReturnCode() != ESDKConstant.PRINTER_SUCCESS) {
                        Log.e(TAG, "Bmp print return value is " + PrinterService.iGetReturnCode());
                        break;
                    }


//                    isFileInput = getApplicationContext().getResources().openRawResource(R.drawable.shipconverted);
//                    bitmap = BitmapFactory.decodeStream(isFileInput);
//                    PrinterService.printImg(bitmap);
//                    if (PrinterService.iGetReturnCode() != ESDKConstant.PRINTER_SUCCESS) {
//                        Log.e(TAG, "Bmp print return value is " + PrinterService.iGetReturnCode());
//                        break;
////                    Toast.makeText(this, "Bmp print return value is  "+printerService.iGetReturnCode(), Toast.LENGTH_LONG).show();
//                    }
//                    isFileInput = getApplicationContext().getResources().openRawResource(R.drawable.wolf);
//                    bitmap = BitmapFactory.decodeStream(isFileInput);
//                    PrinterService.printImg(bitmap);
//                    if (PrinterService.iGetReturnCode() != ESDKConstant.PRINTER_SUCCESS) {
//                        Log.e(TAG, "Bmp print return value is " + PrinterService.iGetReturnCode());
//                        break;
////                    Toast.makeText(this, "Bmp print return value is  "+printerService.iGetReturnCode(), Toast.LENGTH_LONG).show();
//                    }
//                    isFileInput = getApplicationContext().getResources().openRawResource(R.drawable.n1);
//                    bitmap = BitmapFactory.decodeStream(isFileInput);
//                    PrinterService.printImg(bitmap);
//                    if (PrinterService.iGetReturnCode() != ESDKConstant.PRINTER_SUCCESS){
//                        Log.e(TAG, "Bmp print return value is " + PrinterService.iGetReturnCode());
//
//                        break;
//                    }
//                    Toast.makeText(this, "Bmp print return value is  "+printerService.iGetReturnCode(), Toast.LENGTH_LONG).show();


                    loopVariable++;

                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
//            new Handler(Looper.getMainLooper()).post(new Runnable() {
//
//                @Override
//                public void run() {
//
//                    try {
//                        Toast.makeText(PrinterActivity.this, "Bmp print return value is " + PrinterService.iGetReturnCode(), Toast.LENGTH_SHORT).show();
//                    } catch (RemoteException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//            });

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        txtViewlogs.setText("Bmp print return value is " + PrinterService.iGetReturnCode() + "\n"
                                + "Bmp print return value is " + PrinterService.iGetReturnCode() + "\n" + "Bmp print return value is " + PrinterService.iGetReturnCode() + "\n" +
                                "Bmp print return value is " + PrinterService.iGetReturnCode() + "\n" + "Bmp print return value is " + PrinterService.iGetReturnCode() + "\n" + "Bmp print return value is " + PrinterService.iGetReturnCode() + "\n");

                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {

            try {
                Toast.makeText(PrinterActivity.this, "Bmp print return value is  " + PrinterService.iGetReturnCode(), Toast.LENGTH_LONG).show();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            super.onPostExecute(integer);
        }
    }

    private class Printbarcode128Async extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer... integers) {
            int loopVariable = 0;
            while (loopVariable < 100) {
                try {
                    String data = edt_barcodeData.getText().toString();
                    int height = Integer.parseInt(edt_FontSizebarcode.getText().toString());
                    int l = PrinterService.iPrintCode128_Barcode(data, height);
                    Log.e(TAG, "iprintcode128barcode is : " + l);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            txtViewlogs.setText("iprintcode128barcode is : " + l);
                        }
                    });

                    if (l != ESDKConstant.PRINTER_SUCCESS) {
                        break;
                    }
                    new Handler(Looper.getMainLooper()).post(new Runnable() {

                        @Override
                        public void run() {

                            Toast.makeText(PrinterActivity.this, "iprintcode128barcode is : " + l, Toast.LENGTH_SHORT).show();
                        }
                    });
                    loopVariable++;
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }

            return null;
        }

    }

    private class ReceiptprintAsync extends AsyncTask<Integer, Integer, Integer> {

        @SuppressLint("ResourceType")
        @Override
        protected Integer doInBackground(Integer... integers) {
            int loopVariable = 0;
            while (loopVariable < 100) {
                try {
                    int a = 0;
                    Bitmap bitmap;
                    InputStream isFileInput;
                    PrinterService.iFlushBuffer();
                    isFileInput = getApplicationContext().getResources().openRawResource(R.drawable.img);
                    bitmap = BitmapFactory.decodeStream(isFileInput);
                    PrinterService.printImg(bitmap);
                    isFileInput = getApplicationContext().getResources().openRawResource(R.drawable.img);
                    bitmap = BitmapFactory.decodeStream(isFileInput);
                    PrinterService.printImg(bitmap);
                    PrinterService.iSetPrinterProperties(ESDKConstant.FontSize.Width_x_Height_8x16_Small_42, ESDKConstant.TextAlignment.Center);
                    PrinterService.iUpdateBuffer(ESDKConstant.TextAlignment.Center);
                    PrinterService.iUpdateBuffer("EvoluteFintechINNOVATIONSPVTltd12345!@#$&*\n");
                    PrinterService.iUpdateBuffer("Al- lathef tower !@#$%unioun strffeet\n");
                    PrinterService.iUpdateBuffer("Hello,world!Let’stry sASDomething fun.\n");
                    PrinterService.iUpdateBuffer("A-Z, a-z,0-9,!@#$%^&*()_+{}[]|:;'<>?,./`~\n");
                    PrinterService.iUpdateBuffer("L1k3_Th1s, 1t’s_K1nd_0f_FUnnY, R1ght?\n");
                    PrinterService.iUpdateBuffer(ESDKConstant.TextAlignment.Left);
                    PrinterService.iUpdateBuffer("1t’s_K1nd_0f_FUnnY, 123R1ffffght?\n");
                    PrinterService.iUpdateBuffer("Have fun !@#$%^&experimenffvgting!\n");
                    PrinterService.iUpdateBuffer("L1k3_Th1s, ASDFGHJK1t’s_K1fvvfnd_0f\n");
                    PrinterService.iUpdateBuffer("1t’s_K1nd_0f_FUnnY, !@#$%^&FR1ght?\n");
                    PrinterService.iUpdateBuffer(ESDKConstant.TextAlignment.Left);
                    PrinterService.iUpdateBuffer("1t’s_asdfghK1nd_0f_FUnnY, FFFR1ght?\n");
                    PrinterService.iUpdateBuffer("Have fun experfff!@#$%^123iFFFmenting!\n");
                    PrinterService.iUpdateBuffer("L1k3_Th1s,/./..// 1t’s_K1nd_0f\n");
                    PrinterService.iUpdateBuffer("1t’s_K1nd_0f_FUnnY, ][[][][]dFFfhfR1ght?\n");
                    a = PrinterService.iStartPrinting();
                    int ret = PrinterService.printQRCode("Hello I am Parvez!123asdfghjhkjdsfgh4567890asdfghjkl!@#$%^&*()!@#$%^&*!@#$%^&*9!@#$%^789!@#$%^78");
                    int b = PrinterService.iPrintCode128_Barcode("1234567890",150);
                    PrinterService.iSetPrinterProperties(ESDKConstant.FontSize.Width_x_Height_8x16_Small_32, ESDKConstant.TextAlignment.Center);
                    PrinterService.iUpdateBuffer(ESDKConstant.TextAlignment.Center);
                    PrinterService.iUpdateBuffer("EvoluteFintechINNOVATIONSPVTltd1\n");
                    PrinterService.iUpdateBuffer("Al- lathe1234567!@#niounstreet\n");
                    PrinterService.iUpdateBuffer("Hello,world!Let’strysomethingf\n");
                    PrinterService.iUpdateBuffer("A-Z, -z,0-9,!@#$%^&*()_+{}[]`~\n");
                    PrinterService.iUpdateBuffer("L1k3_Th1s, 1t’s_K1nd_0f_FUnnYR\n");
                    PrinterService.iUpdateBuffer("L1k3_Th1s, 1t’s_K1nd_0f_FUnn R\n");
                    PrinterService.iUpdateBuffer("Al- lathe1234567!@#niounstreet\n");
                    PrinterService.iUpdateBuffer("Hello,world!Let’strysomethinn.\n");
                    PrinterService.iUpdateBuffer("A-Z, -z,0-9,!@#$%^&*()_+{}[]`~\n");
                    a = PrinterService.iStartPrinting();


//                    PrinterService.iSetPrinterProperties(ESDKConstant.FontSize.Width_x_Height_8x16_Small_32, ESDKConstant.TextAlignment.Center);
//                    PrinterService.iUpdateBuffer(ESDKConstant.TextAlignment.Center);
//                    PrinterService.iUpdateBuffer(" TEST \n");
//                    PrinterService.iUpdateBuffer(" -------------------------------\n");
//                    PrinterService.iUpdateBuffer(" 2024-12-13 16:55:18\n");
//                    PrinterService.iUpdateBuffer(" -------------------------------\n");
//                    PrinterService.iUpdateBuffer("Sale\n");
//                    PrinterService.iUpdateBuffer(ESDKConstant.TextAlignment.Left);
//                    PrinterService.iUpdateBuffer("MERCHANT NAME:\n");
//                    PrinterService.iUpdateBuffer(ESDKConstant.TextAlignment.Right);
//                    PrinterService.iUpdateBuffer("001420183990573\n");
//                    PrinterService.iUpdateBuffer(ESDKConstant.TextAlignment.Left);
//                    PrinterService.iUpdateBuffer("TERMINAL NO:\n");
//                    PrinterService.iUpdateBuffer(ESDKConstant.TextAlignment.Right);
//                    PrinterService.iUpdateBuffer("00026715\n");
//                    PrinterService.iUpdateBuffer(ESDKConstant.TextAlignment.Left);
//                    PrinterService.iUpdateBuffer("OPERATOR NO:\n");
//                    PrinterService.iUpdateBuffer(ESDKConstant.TextAlignment.Right);
//                    PrinterService.iUpdateBuffer("12345678\n");
//                    PrinterService.iUpdateBuffer(ESDKConstant.TextAlignment.Left);
//                    PrinterService.iUpdateBuffer("ISSUER:\n");
//                    PrinterService.iUpdateBuffer(ESDKConstant.TextAlignment.Right);
//                    PrinterService.iUpdateBuffer("01020001\n");
//                    PrinterService.iUpdateBuffer(ESDKConstant.TextAlignment.Left);
//                    PrinterService.iUpdateBuffer("CARD NO:\n");
//                    PrinterService.iUpdateBuffer(ESDKConstant.TextAlignment.Right);
//                    PrinterService.iUpdateBuffer(" 9558803602109503920\n");
//                    PrinterService.iUpdateBuffer(ESDKConstant.TextAlignment.Left);
//                    PrinterService.iUpdateBuffer("ACQUIRER:\n");
//                    PrinterService.iUpdateBuffer(ESDKConstant.TextAlignment.Right);
//                    PrinterService.iUpdateBuffer(" 03050011\n");
//                    PrinterService.iUpdateBuffer(ESDKConstant.TextAlignment.Left);
//                    PrinterService.iUpdateBuffer("TXN. TYPE:\n");
//                    PrinterService.iUpdateBuffer(ESDKConstant.TextAlignment.Right);
//                    PrinterService.iUpdateBuffer(" SALE\n");
//                    PrinterService.iUpdateBuffer(ESDKConstant.TextAlignment.Left);
//                    PrinterService.iUpdateBuffer("EXP. DATE:\n");
//                    PrinterService.iUpdateBuffer(ESDKConstant.TextAlignment.Right);
//                    PrinterService.iUpdateBuffer("2023/08\n");
//                    PrinterService.iUpdateBuffer(ESDKConstant.TextAlignment.Left);
//                    PrinterService.iUpdateBuffer("REF NO:\n");
//                    PrinterService.iUpdateBuffer(ESDKConstant.TextAlignment.Right);
//                    PrinterService.iUpdateBuffer("202412137664\n");
//                    PrinterService.iUpdateBuffer(ESDKConstant.TextAlignment.Left);
//                    PrinterService.iUpdateBuffer(" -------------------------------\n");
//                    PrinterService.iUpdateBuffer(" AMOUNT:RMB:2.55 \n");
//                    PrinterService.iUpdateBuffer(" -------------------------------\n");
//                    PrinterService.iUpdateBuffer(" CARDHOLDER SIGNATURE\n");
//                    PrinterService.iUpdateBuffer(" CARDHOLDER SIGNATURE\n");
//                    a = PrinterService.iStartPrinting();


                    Log.e(TAG, "Receipt print is : " + a);

                    int finalA = a;
                    int finalA1 = a;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                           txtViewlogs.setText("Receipt print is : " + finalA + "\n" + "printQRCode is : " + ret + "\n" +
                                   "iPrintCode128_Barcode is : " + b +"\n"+  "iStartPrinting :" + finalA1 + "\n"  );

                        }
                    });
                    if (a != ESDKConstant.PRINTER_SUCCESS) {
                        break;
                    }
                    new Handler(Looper.getMainLooper()).post(new Runnable() {

                        @Override
                        public void run() {

                            Toast.makeText(PrinterActivity.this, "Receipt print is : " + finalA, Toast.LENGTH_SHORT).show();

                        }
                    });

                    loopVariable++;
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
            return null;

        }
    }
}


























