/*package com.example.serialnumberefetching;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.leopard.api.Printer;

public class LegendprinterActivity extends AppCompatActivity {

    Button btn_testPrint, btn_Logoprint, btn_paperfeed, btn_Printeradddata, btn_Printeradddataul, btn_Printeraddline, btn_Printeraddlineul, btn_Bmpprint;
    int iRetVal;
    int DEVICE_NOTCONNECTED = 100;

    Button btn_Bmpgreyscaleprint, btn_Printerdiagnostics, btn_battery, btn_Barcode, btn_Bmppackets, btn_Returncode, btn_Firmware, btn_Serialno, btn_Peripherals;

    EditText edt_Addtext, edt_AddtextUL, edt_AddLinetext, edt_AddLineultext, edt_Barcodetext;
    Printer ptr;
    String sAddData;

    Context context = this;

    byte[][] bBmpPackets = {};

    private TextView logText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legendprinter);
        btn_testPrint = findViewById(R.id.testPrint_btn);
        btn_Logoprint = findViewById(R.id.Logoprint_btn);
        btn_paperfeed = findViewById(R.id.paperfeed_btn);
        btn_Printeradddata = findViewById(R.id.Printeradddata_btn);
        edt_Addtext = (EditText) findViewById(R.id.Addtext);
        btn_Printeradddataul = findViewById(R.id.Printeradddataul_btn);
        edt_AddtextUL = (EditText) findViewById(R.id.AddtextUL);
        btn_Printeraddline = findViewById(R.id.Printeraddline_btn);
        edt_AddLinetext = (EditText) findViewById(R.id.AddLinetext);
        btn_Printeraddlineul = findViewById(R.id.Printeraddlineul_btn);
        edt_AddLineultext = (EditText) findViewById(R.id.AddLineultext);
        btn_Bmpprint = findViewById(R.id.Bmpprint_btn);
        btn_Bmpgreyscaleprint = findViewById(R.id.Bmpgreyscaleprint_btn);
        btn_Printerdiagnostics = findViewById(R.id.Printerdiagnostics_btn);
        btn_battery = findViewById(R.id.battery_btn);
        btn_Barcode = findViewById(R.id.Barcode_btn);
        edt_Barcodetext = (EditText) findViewById(R.id.Barcodetext);
        btn_Bmppackets = findViewById(R.id.Bmppackets_btn);
        btn_Returncode = findViewById(R.id.Returncode_btn);
        btn_Firmware = findViewById(R.id.Firmware_btn);
        btn_Serialno = findViewById(R.id.Serialno_btn);
        btn_Peripherals = findViewById(R.id.Peripherals_btn);
        logText = findViewById(R.id.logText);

        btn_testPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrinterActivity.TestPrintAsyc testPrintAsyc = new PrinterActivity.TestPrintAsyc();
                testPrintAsyc.execute();
            }
        });
        btn_Logoprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrinterActivity.LogoPrintAsyc logoPrintAsyc = new PrinterActivity.LogoPrintAsyc();
                logoPrintAsyc.execute();
            }
        });
        btn_paperfeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrinterActivity.PaperfeedAsyc paperfeedAsyc = new PrinterActivity.PaperfeedAsyc();
                paperfeedAsyc.execute();
            }
        });
        btn_Printeradddata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edt_Addtext.getText().toString().isEmpty()) {

                    Toast.makeText(context, "Empty Data", Toast.LENGTH_SHORT).show();

                } else{
                    PrinterActivity.PrinteradddataAsyc printeradddataAsyc = new PrinterActivity.PrinteradddataAsyc();
                    printeradddataAsyc.execute();

                }
            }
        });
        btn_Printeradddataul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_AddtextUL.getText().toString().isEmpty()) {
                    Toast.makeText(context, "Empty Data", Toast.LENGTH_SHORT).show();

                } else {
                    PrinterActivity.PrinteradddataulAsyc printeradddataulAsyc = new PrinterActivity.PrinteradddataulAsyc();
                    printeradddataulAsyc.execute();
                }
            }
        });
        btn_Printeraddline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edt_AddLinetext.getText().toString().isEmpty()){
                    Toast.makeText(context, "Empty Data", Toast.LENGTH_SHORT).show();
                } else {
                    PrinterActivity.PrinteraddlineAsync printeraddlineAsync = new PrinterActivity.PrinteraddlineAsync();
                    printeraddlineAsync.execute();
                }
            }
        });
        btn_Printeraddlineul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_AddLineultext.getText().toString().isEmpty()){
                    Toast.makeText(context, "Empty Data", Toast.LENGTH_SHORT).show();
                } else {
                    PrinterActivity.PrinteraddlineulAsync printeraddlineulAsync = new PrinterActivity.PrinteraddlineulAsync();
                    printeraddlineulAsync.execute();
                }
            }
        });
        btn_Bmpprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrinterActivity.BmpprintAsyc bmpprintAsyc = new PrinterActivity.BmpprintAsyc();
                bmpprintAsyc.execute();
            }
        });
        btn_Bmpgreyscaleprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrinterActivity.BmpgreyscaleprintAsyc bmpgreyscaleprintAsyc = new PrinterActivity.BmpgreyscaleprintAsyc();
                bmpgreyscaleprintAsyc.execute();
            }
        });
        btn_Printerdiagnostics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrinterActivity.PrinterdiagnosticsAsyc printerdiagnosticsAsyc = new PrinterActivity.PrinterdiagnosticsAsyc();
                printerdiagnosticsAsyc.execute();
            }
        });
        btn_battery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrinterActivity.BatteryAsyc batteryAsyc = new PrinterActivity.BatteryAsyc();
                batteryAsyc.execute();
            }
        });
        btn_Barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edt_Barcodetext.getText().toString().isEmpty()){
                    Toast.makeText(context, "Empty Data", Toast.LENGTH_SHORT).show();
                } else {
                    PrinterActivity.BarcodeAsyc barcodeAsyc = new PrinterActivity.BarcodeAsyc();
                    barcodeAsyc.execute();
                }
            }
        });
        btn_Bmppackets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrinterActivity.BmppacketsAsyc bmppacketsAsyc = new PrinterActivity.BmppacketsAsyc();
                bmppacketsAsyc.execute();
            }
        });
        btn_Returncode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrinterActivity.ReturncodeAsyc returncodeAsyc = new PrinterActivity.ReturncodeAsyc();
                returncodeAsyc.execute();
            }
        });
        btn_Firmware.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrinterActivity.FirmwareAsyc firmwareAsyc = new PrinterActivity.FirmwareAsyc();
                firmwareAsyc.execute();
            }
        });
        btn_Serialno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrinterActivity.SerialnoAsyc serialnoAsyc = new PrinterActivity.SerialnoAsyc();
                serialnoAsyc.execute();
            }
        });
        btn_Peripherals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrinterActivity.PeripheralsAsyc peripheralsAsyc = new PrinterActivity.PeripheralsAsyc();
                peripheralsAsyc.execute();
            }
        });


    }

    private class TestPrintAsyc extends AsyncTask<Integer, Integer, Integer> {
        private ProgressDialog tmp = null;

        @Override
        protected void onPreExecute() {

            this.tmp = new ProgressDialog(LegendprinterActivity.this);
            this.tmp.setMessage("Printing....");
            this.tmp.setCancelable(false);
            this.tmp.setCanceledOnTouchOutside(false);
            this.tmp.show();
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            try {
                iRetVal = MainActivity.insPrinter.iTestPrint();
                Log.e("Test Print Task", "Test print value is " + iRetVal);
                iRetVal = MainActivity.insPrinter.iPaperFeed();
                Log.e("paper feed", "paper feeed is " + iRetVal);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        logText.setText("Test print value is " + iRetVal + "\n"
                                + "paper feed is " + iRetVal);
                    }
                });

            } catch (NullPointerException e) {
                iRetVal = DEVICE_NOTCONNECTED;
                Log.e("Test Print Task", "Test print value is " + iRetVal);

                return iRetVal;
            }
            return iRetVal;
        }

        @Override
        protected void onPostExecute(Integer result) {
            tmp.dismiss();
            Log.e("Test Print", "+++++LEOPARD Printer onpostExcute TestPrint+++++" + iRetVal);
            if (iRetVal == DEVICE_NOTCONNECTED) {
                Toast.makeText(LegendprinterActivity.this, "Device Not Connected", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_SUCCESS) {
                Toast.makeText(LegendprinterActivity.this, "Success", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_PLATEN_OPEN) {
                Toast.makeText(LegendprinterActivity.this, "Time Out", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_PAPER_OUT) {
                Toast.makeText(LegendprinterActivity.this, "Paper Out", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_IMPROPER_VOLTAGE) {
                Toast.makeText(LegendprinterActivity.this, "Improper Voltage", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_FAIL) {
                Toast.makeText(LegendprinterActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_PARAM_ERROR) {
                Toast.makeText(LegendprinterActivity.this, "Parameter Error", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_NO_RESPONSE) {
                Toast.makeText(LegendprinterActivity.this, "No Response", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_DEMO_VERSION) {
                Toast.makeText(LegendprinterActivity.this, "Demo Version", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_INVALID_DEVICE_ID) {
                Toast.makeText(LegendprinterActivity.this, "Invalid Device Id", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_HIGH_HEADTEMP) {
                Toast.makeText(LegendprinterActivity.this, "High Head temp", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_LOW_HEADTEMP) {
                Toast.makeText(LegendprinterActivity.this, "Low Head Temp", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_PACKET_ERROR) {
                Toast.makeText(LegendprinterActivity.this, "Packet Error", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LegendprinterActivity.this, "Failed", Toast.LENGTH_SHORT).show();

            }
            super.onPostExecute(result);
        }
    }

    private class LogoPrintAsyc extends AsyncTask<Integer, Integer, Integer> {
        private ProgressDialog tmp = null;

        @Override
        protected void onPreExecute() {

            this.tmp = new ProgressDialog(LegendprinterActivity.this);
            this.tmp.setMessage("Printing....");
            this.tmp.setCancelable(false);
            this.tmp.setCanceledOnTouchOutside(false);
            this.tmp.show();
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            try {
                iRetVal = MainActivity.insPrinter.iLogoPrint(Logopackets.EVOLUTER);
                Log.e("Test Print Task", "iLogo print value is " + iRetVal);
//                iRetVal = MainActivity.insPrinter.iPaperFeed();
//                Log.e("paper feed", "paper feeed is " + iRetVal);
            } catch (NullPointerException e) {
                iRetVal = DEVICE_NOTCONNECTED;
                Log.e("Test Print Task", "iLogo value is " + iRetVal);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        logText.setText("iLogo print value is " + iRetVal + "\n"
                                + "iLogo value is " + iRetVal);
                    }
                });
                return iRetVal;
            }
            return iRetVal;
        }

        @Override
        protected void onPostExecute(Integer result) {
            tmp.dismiss();
            Log.e("Test Print", "+++++LEOPARD Printer onpostExcute TestPrint+++++" + iRetVal);
            if (iRetVal == DEVICE_NOTCONNECTED) {
                Toast.makeText(LegendprinterActivity.this, "Device Not Connected", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_SUCCESS) {
                Toast.makeText(LegendprinterActivity.this, "Success", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_PLATEN_OPEN) {
                Toast.makeText(LegendprinterActivity.this, "Time Out", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_PAPER_OUT) {
                Toast.makeText(LegendprinterActivity.this, "Paper Out", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_IMPROPER_VOLTAGE) {
                Toast.makeText(LegendprinterActivity.this, "Improper Voltage", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_FAIL) {
                Toast.makeText(LegendprinterActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_PARAM_ERROR) {
                Toast.makeText(LegendprinterActivity.this, "Parameter Error", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_NO_RESPONSE) {
                Toast.makeText(LegendprinterActivity.this, "No Response", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_DEMO_VERSION) {
                Toast.makeText(LegendprinterActivity.this, "Demo Version", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_INVALID_DEVICE_ID) {
                Toast.makeText(LegendprinterActivity.this, "Invalid Device Id", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_HIGH_HEADTEMP) {
                Toast.makeText(LegendprinterActivity.this, "High Head temp", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_LOW_HEADTEMP) {
                Toast.makeText(LegendprinterActivity.this, "Low Head Temp", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_PACKET_ERROR) {
                Toast.makeText(LegendprinterActivity.this, "Packet Error", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LegendprinterActivity.this, "Failed", Toast.LENGTH_SHORT).show();

            }
            super.onPostExecute(result);
        }
    }

    private class PaperfeedAsyc extends AsyncTask<Integer, Integer, Integer> {
        private ProgressDialog tmp = null;

        @Override
        protected void onPreExecute() {

            this.tmp = new ProgressDialog(LegendprinterActivity.this);
            this.tmp.setMessage("Printing....");
            this.tmp.setCancelable(false);
            this.tmp.setCanceledOnTouchOutside(false);
            this.tmp.show();
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            try {
                iRetVal = MainActivity.insPrinter.iPaperFeed();
                Log.e("paper feed", "paper feeed is " + iRetVal);
            } catch (NullPointerException e) {
                iRetVal = DEVICE_NOTCONNECTED;
                Log.e("Test Print Task", "iLogo value is " + iRetVal);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        logText.setText("paper feed is " + iRetVal + "\n"
                                + "iLogo value is " + iRetVal);
                    }
                });
                return iRetVal;
            }
            return iRetVal;
        }

        @Override
        protected void onPostExecute(Integer result) {
            tmp.dismiss();
            Log.e("Test Print", "+++++LEOPARD Printer onpostExcute TestPrint+++++" + iRetVal);
            if (iRetVal == DEVICE_NOTCONNECTED) {
                Toast.makeText(LegendprinterActivity.this, "Device Not Connected", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_SUCCESS) {
                Toast.makeText(LegendprinterActivity.this, "Success", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_PLATEN_OPEN) {
                Toast.makeText(LegendprinterActivity.this, "Time Out", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_PAPER_OUT) {
                Toast.makeText(LegendprinterActivity.this, "Paper Out", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_IMPROPER_VOLTAGE) {
                Toast.makeText(LegendprinterActivity.this, "Improper Voltage", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_FAIL) {
                Toast.makeText(LegendprinterActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_PARAM_ERROR) {
                Toast.makeText(LegendprinterActivity.this, "Parameter Error", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_NO_RESPONSE) {
                Toast.makeText(LegendprinterActivity.this, "No Response", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_DEMO_VERSION) {
                Toast.makeText(LegendprinterActivity.this, "Demo Version", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_INVALID_DEVICE_ID) {
                Toast.makeText(LegendprinterActivity.this, "Invalid Device Id", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_HIGH_HEADTEMP) {
                Toast.makeText(LegendprinterActivity.this, "High Head temp", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_LOW_HEADTEMP) {
                Toast.makeText(LegendprinterActivity.this, "Low Head Temp", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_PACKET_ERROR) {
                Toast.makeText(LegendprinterActivity.this, "Packet Error", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LegendprinterActivity.this, "Failed", Toast.LENGTH_SHORT).show();

            }
            super.onPostExecute(result);
        }
    }

    private class PrinteradddataAsyc extends AsyncTask<Integer, Integer, Integer> {
        private ProgressDialog tmp = null;


        @Override
        protected void onPreExecute() {

            this.tmp = new ProgressDialog(LegendprinterActivity.this);
            this.tmp.setMessage("Printing....");
            this.tmp.setCancelable(false);
            this.tmp.setCanceledOnTouchOutside(false);
            this.tmp.show();
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            try {
                sAddData = edt_Addtext.getText().toString();
                Log.e("testlog","Entered data is : "+sAddData);
                String empty = sAddData+"\n"+"\n"+"\n";
                Log.e("testlog","empty : "+empty);

                iRetVal = MainActivity.insPrinter.iFlushBuf();
                Log.e("Flush", "iFlushbuffvalue is" + iRetVal);

                iRetVal = MainActivity.insPrinter.iPrinterAddData(com.mydevice.sdk.Printer.PR_FONTLARGEBOLD,empty);
                Log.e("Printadddata", "FONTLARGENORMAL is" + iRetVal);

                iRetVal = MainActivity.insPrinter.iPrinterAddData(com.mydevice.sdk.Printer.PR_FONTLARGENORMAL,empty);
                Log.e("Printadddata", "FONTLARGEBOLD is" + iRetVal);

                iRetVal = MainActivity.insPrinter.iPrinterAddData(com.mydevice.sdk.Printer.PR_FONTSMALLNORMAL,empty);
                Log.e("Printadddata", "FONTSMALLNORMALis" + iRetVal);

                iRetVal = MainActivity.insPrinter.iPrinterAddData(com.mydevice.sdk.Printer.PR_FONTSMALLBOLD,empty);
                Log.e("Printadddata", "FONTSMALLBOLD is" + iRetVal);

                iRetVal = MainActivity.insPrinter.iPrinterAddData(com.mydevice.sdk.Printer.PR_FONTULLARGEBOLD,empty);
                Log.e("Printadddata", "PR_FONTULLARGEBOLD is" + iRetVal);

                iRetVal = MainActivity.insPrinter.iPrinterAddData(com.mydevice.sdk.Printer.PR_FONTULLARGENORMAL,empty);
                Log.e("Printadddata", "PR_FONTULLARGENormal is" + iRetVal);

                iRetVal = MainActivity.insPrinter.iPrinterAddData(com.mydevice.sdk.Printer.PR_FONTULSMALLNORMAL,empty);
                Log.e("Printadddata", "PR_FONTULSMALLNORMAL is" + iRetVal);

                iRetVal = MainActivity.insPrinter.iPrinterAddData(com.mydevice.sdk.Printer.PR_FONTULSMALLBOLD,empty);
                Log.e("Printadddata", "PR_FONTULSMALLBOLD is" + iRetVal);

                iRetVal = MainActivity.insPrinter.iStartPrinting(1);
                Log.e("startprinting", "istartprinting is " + iRetVal);

                iRetVal = MainActivity.insPrinter.iPaperFeed();
                Log.e("paper feed", "paper feeed is " + iRetVal);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        logText.setText("Entered data is : "+sAddData + "\n" +
                                "iFlushbuffvalue is" + iRetVal + "\n"
                                + "FONTLARGENORMAL is" + iRetVal + "\n" + "FONTLARGEBOLD is" + iRetVal + "\n" + "FONTSMALLNORMALis" + iRetVal + "\n"
                                + "FONTSMALLBOLD is" + iRetVal + "\n" + "PR_FONTULLARGEBOLD is" + iRetVal + "\n" + "PR_FONTULLARGENormal is" + iRetVal + "\n"
                                +  "PR_FONTULSMALLNORMAL is" + iRetVal + "\n" +  "PR_FONTULSMALLBOLD is" + iRetVal + "\n" +  "istartprinting is " + iRetVal + "\n" + "paper feed is " + iRetVal );
                    }
                });
            } catch (NullPointerException e) {
                iRetVal = DEVICE_NOTCONNECTED;
                Log.e("Test Print Task", "iprinteradddata is " + iRetVal);
                return iRetVal;
            }
            return iRetVal;
        }

        @Override
        protected void onPostExecute(Integer result) {
            tmp.dismiss();
            Log.e("Test Print", "+++++LEOPARD Printer onpostExcute TestPrint+++++" + iRetVal);
            if (iRetVal == DEVICE_NOTCONNECTED) {
                Toast.makeText(LegendprinterActivity.this, "Device Not Connected", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_SUCCESS) {
                Toast.makeText(LegendprinterActivity.this, "Success", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_PLATEN_OPEN) {
                Toast.makeText(LegendprinterActivity.this, "Time Out", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_PAPER_OUT) {
                Toast.makeText(LegendprinterActivity.this, "Paper Out", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_IMPROPER_VOLTAGE) {
                Toast.makeText(LegendprinterActivity.this, "Improper Voltage", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_FAIL) {
                Toast.makeText(LegendprinterActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_PARAM_ERROR) {
                Toast.makeText(LegendprinterActivity.this, "Parameter Error", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_NO_RESPONSE) {
                Toast.makeText(LegendprinterActivity.this, "No Response", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_DEMO_VERSION) {
                Toast.makeText(LegendprinterActivity.this, "Demo Version", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_INVALID_DEVICE_ID) {
                Toast.makeText(LegendprinterActivity.this, "Invalid Device Id", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_HIGH_HEADTEMP) {
                Toast.makeText(LegendprinterActivity.this, "High Head temp", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_LOW_HEADTEMP) {
                Toast.makeText(LegendprinterActivity.this, "Low Head Temp", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_PACKET_ERROR) {
                Toast.makeText(LegendprinterActivity.this, "Packet Error", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LegendprinterActivity.this, "Failed", Toast.LENGTH_SHORT).show();

            }
            super.onPostExecute(result);
        }
    }

    private class PrinteradddataulAsyc extends AsyncTask<Integer, Integer, Integer> {
        private ProgressDialog tmp = null;


        @Override
        protected void onPreExecute() {

            this.tmp = new ProgressDialog(LegendprinterActivity.this);
            this.tmp.setMessage("Printing....");
            this.tmp.setCancelable(false);
            this.tmp.setCanceledOnTouchOutside(false);
            this.tmp.show();
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            try {
                sAddData = edt_AddtextUL.getText().toString();
                String empty = sAddData+"\n"+"\n"+"\n" ;
                Log.e("testlog","Entered data is  : "+empty);

                iRetVal = MainActivity.insPrinter.iFlushBuf();
                Log.e("Flush", "iFlushbuffvalue is" + iRetVal);

                iRetVal = MainActivity.insPrinter.iPrinterAddData(com.mydevice.sdk.Printer.PR_FONT180ULLARGENORMAL,empty);
                Log.e("Printadddata", "PR_FONT180ULLARGENORMAL is" + iRetVal);

                iRetVal = MainActivity.insPrinter.iPrinterAddData(com.mydevice.sdk.Printer.PR_FONT180ULLARGEBOLD,empty);
                Log.e("Printadddata", "PR_FONT180ULLARGEBOLD is" + iRetVal);

                iRetVal = MainActivity.insPrinter.iPrinterAddData(com.mydevice.sdk.Printer.PR_FONT180ULLARGEBOLD,empty);
                Log.e("Printadddata", "PR_FONT180ULLARGEBOLD is" + iRetVal);

                iRetVal = MainActivity.insPrinter.iPrinterAddData(com.mydevice.sdk.Printer.PR_FONT180ULSMALLNORMAL,empty);

                Log.e("Printadddata", "PR_FONT180ULSMALLNORMAL is" + iRetVal);

                iRetVal = MainActivity.insPrinter.iPrinterAddData(com.mydevice.sdk.Printer.PR_FONT180ULSMALLBOLD,empty);
                Log.e("Printadddata", "PR_FONT180ULSMALLBOLD is" + iRetVal);

                iRetVal = MainActivity.insPrinter.iPrinterAddData(com.mydevice.sdk.Printer.PR_FONT180SMALLBOLD,empty);
                Log.e("Printadddata", "PR_FONT180SMALLBOLD is" + iRetVal);

                iRetVal = MainActivity.insPrinter.iPrinterAddData(com.mydevice.sdk.Printer.PR_FONT180SMALLNORMAL,empty);
                Log.e("Printadddata", "PR_FONT180SMALLNORMALis" + iRetVal);

                iRetVal = MainActivity.insPrinter.iPrinterAddData(com.mydevice.sdk.Printer.PR_FONT180LARGEBOLD,empty);
                Log.e("Printadddata", "PR_FONT180LARGEBOLD is" + iRetVal);

                iRetVal = MainActivity.insPrinter.iPrinterAddData(com.mydevice.sdk.Printer.PR_FONT180LARGENORMAL,empty);
                Log.e("Printadddata", "PR_FONT180LARGENORML is" + iRetVal);

                iRetVal = MainActivity.insPrinter.iStartPrinting(1);
                Log.e("startprinting", "istartprinting is " + iRetVal);

                iRetVal = MainActivity.insPrinter.iPaperFeed();
                Log.e("paper feed", "paper feeed is " + iRetVal);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        logText.setText("Entered data is  : "+empty + "\n" + "iFlushbuffvalue is" + iRetVal + "\n" + "PR_FONT180ULLARGENORMAL is" + iRetVal + "\n" +
                                "PR_FONT180ULLARGEBOLD is" + iRetVal + "\n" + "PR_FONT180ULLARGEBOLD is" + iRetVal + "\n" + "PR_FONT180ULSMALLNORMAL is" + iRetVal + "\n"
                                + "PR_FONT180ULSMALLBOLD is" + iRetVal + "\n" + "PR_FONT180SMALLBOLD is" + iRetVal + "\n" + "PR_FONT180SMALLNORMALis" + iRetVal + "\n" +
                                "PR_FONT180LARGEBOLD is" + iRetVal + "\n" + "PR_FONT180LARGENORML is" + iRetVal + "\n" + "istartprinting is " + iRetVal + "\n"
                                +"paper feed is " + iRetVal);

                    }
                });
            } catch (NullPointerException e) {
                iRetVal = DEVICE_NOTCONNECTED;
                Log.e("Test Print Task", "iprinteradddata is " + iRetVal);
                return iRetVal;
            }
            return iRetVal;
        }

        @Override
        protected void onPostExecute(Integer result) {
            tmp.dismiss();
            Log.e("Test Print", "+++++LEOPARD Printer onpostExcute TestPrint+++++" + iRetVal);
            if (iRetVal == DEVICE_NOTCONNECTED) {
                Toast.makeText(LegendprinterActivity.this, "Device Not Connected", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_SUCCESS) {
                Toast.makeText(LegendprinterActivity.this, "Success", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_PLATEN_OPEN) {
                Toast.makeText(LegendprinterActivity.this, "Time Out", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_PAPER_OUT) {
                Toast.makeText(LegendprinterActivity.this, "Paper Out", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_IMPROPER_VOLTAGE) {
                Toast.makeText(LegendprinterActivity.this, "Improper Voltage", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_FAIL) {
                Toast.makeText(LegendprinterActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_PARAM_ERROR) {
                Toast.makeText(LegendprinterActivity.this, "Parameter Error", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_NO_RESPONSE) {
                Toast.makeText(LegendprinterActivity.this, "No Response", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_DEMO_VERSION) {
                Toast.makeText(LegendprinterActivity.this, "Demo Version", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_INVALID_DEVICE_ID) {
                Toast.makeText(LegendprinterActivity.this, "Invalid Device Id", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_HIGH_HEADTEMP) {
                Toast.makeText(LegendprinterActivity.this, "High Head temp", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_LOW_HEADTEMP) {
                Toast.makeText(LegendprinterActivity.this, "Low Head Temp", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_PACKET_ERROR) {
                Toast.makeText(LegendprinterActivity.this, "Packet Error", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LegendprinterActivity.this, "Failed", Toast.LENGTH_SHORT).show();

            }
            super.onPostExecute(result);
        }
    }

    private class PrinteraddlineAsync extends AsyncTask<Integer, Integer, Integer> {
        private ProgressDialog tmp = null;

        @Override
        protected void onPreExecute() {

            this.tmp = new ProgressDialog(LegendprinterActivity.this);
            this.tmp.setMessage("Printing....");
            this.tmp.setCancelable(false);
            this.tmp.setCanceledOnTouchOutside(false);
            this.tmp.show();
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            try {
                String addlinestr = edt_AddLinetext.getText().toString();
                Character ch = addlinestr.charAt(0);
                Log.e("testlog","Entered data is:" + ch);

                iRetVal = MainActivity.insPrinter.iFlushBuf();
                Log.e("Flush", "iFlushbuffvalue is" + iRetVal);

                iRetVal = MainActivity.insPrinter.iPrinterAddLine(com.mydevice.sdk.Printer.PR_FONTLARGENORMAL,ch);
                Log.e("Printadddata", "FONTLARGENORMAL is" + iRetVal);

                iRetVal = MainActivity.insPrinter.iPrinterAddLine(com.mydevice.sdk.Printer.PR_FONTLARGEBOLD,ch);
                Log.e("Printadddata", "FONTLARGEBOLD is" + iRetVal);

                iRetVal = MainActivity.insPrinter.iPrinterAddLine(com.mydevice.sdk.Printer.PR_FONTSMALLNORMAL,ch);
                Log.e("Printadddata", "FONTSMALLNORMALis" + iRetVal);

                iRetVal = MainActivity.insPrinter.iPrinterAddLine(com.mydevice.sdk.Printer.PR_FONTSMALLBOLD,ch);
                Log.e("Printadddata", "FONTSMALLBOLD is" + iRetVal);

                iRetVal = MainActivity.insPrinter.iPrinterAddLine(com.mydevice.sdk.Printer.PR_FONTULLARGEBOLD,ch);
                Log.e("Printadddata", "PR_FONTULLARGEBOLD is" + iRetVal);

                iRetVal = MainActivity.insPrinter.iPrinterAddLine(com.mydevice.sdk.Printer.PR_FONTULLARGENORMAL,ch);
                Log.e("Printadddata", "PR_FONTULLARGENormal is" + iRetVal);

                iRetVal = MainActivity.insPrinter.iPrinterAddLine(com.mydevice.sdk.Printer.PR_FONTULSMALLNORMAL,ch);
                Log.e("Printadddata", "PR_FONTULSMALLNORMAL is" + iRetVal);

                iRetVal = MainActivity.insPrinter.iPrinterAddLine(com.mydevice.sdk.Printer.PR_FONTULSMALLBOLD,ch);
                Log.e("Printadddata", "PR_FONTULSMALLBOLD is" + iRetVal);

                iRetVal = MainActivity.insPrinter.iStartPrinting(1);
                Log.e("startprinting", "istartprinting is " + iRetVal);

                iRetVal = MainActivity.insPrinter.iPaperFeed();
                Log.e("paper feed", "paper feed is " + iRetVal);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        logText.setText("Entered data is:" + ch + "\n" + "iFlushbuffvalue is" + iRetVal + "\n" + "FONTLARGENORMAL is" + iRetVal + "\n"+
                                "FONTLARGEBOLD is" + iRetVal + "\n" + "FONTSMALLNORMALis" + iRetVal + "\n" + "FONTSMALLBOLD is" + iRetVal + "\n" + "PR_FONTULLARGEBOLD is" + iRetVal + "\n"
                                + "PR_FONTULLARGENormal is" + iRetVal + "\n" + "PR_FONTULSMALLNORMAL is" + iRetVal + "\n" + "PR_FONTULSMALLBOLD is" + iRetVal + "\n" +
                                "istartprinting is " + iRetVal + "\n" + "paper feed is " + iRetVal );
                    }
                });

            } catch (NullPointerException e) {
                iRetVal = DEVICE_NOTCONNECTED;
                Log.e("Test Print Task", "iprinteraddline is " + iRetVal);
                return iRetVal;
            }
            return iRetVal;
        }

        @Override
        protected void onPostExecute(Integer result) {
            tmp.dismiss();
            Log.e("Test Print", "+++++LEOPARD Printer onpostExcute TestPrint+++++" + iRetVal);
            if (iRetVal == DEVICE_NOTCONNECTED) {
                Toast.makeText(LegendprinterActivity.this, "Device Not Connected", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_SUCCESS) {
                Toast.makeText(LegendprinterActivity.this, "Success", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_PLATEN_OPEN) {
                Toast.makeText(LegendprinterActivity.this, "Time Out", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_PAPER_OUT) {
                Toast.makeText(LegendprinterActivity.this, "Paper Out", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_IMPROPER_VOLTAGE) {
                Toast.makeText(LegendprinterActivity.this, "Improper Voltage", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_FAIL) {
                Toast.makeText(LegendprinterActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_PARAM_ERROR) {
                Toast.makeText(LegendprinterActivity.this, "Parameter Error", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_NO_RESPONSE) {
                Toast.makeText(LegendprinterActivity.this, "No Response", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_DEMO_VERSION) {
                Toast.makeText(LegendprinterActivity.this, "Demo Version", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_INVALID_DEVICE_ID) {
                Toast.makeText(LegendprinterActivity.this, "Invalid Device Id", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_HIGH_HEADTEMP) {
                Toast.makeText(LegendprinterActivity.this, "High Head temp", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_LOW_HEADTEMP) {
                Toast.makeText(LegendprinterActivity.this, "Low Head Temp", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_PACKET_ERROR) {
                Toast.makeText(LegendprinterActivity.this, "Packet Error", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LegendprinterActivity.this, "Failed", Toast.LENGTH_SHORT).show();

            }
            super.onPostExecute(result);
        }
    }

    private class PrinteraddlineulAsync extends AsyncTask<Integer, Integer, Integer> {
        private ProgressDialog tmp = null;
        protected void onPreExecute() {

            this.tmp = new ProgressDialog(LegendprinterActivity.this);
            this.tmp.setMessage("Printing....");
            this.tmp.setCancelable(false);
            this.tmp.setCanceledOnTouchOutside(false);
            this.tmp.show();
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Integer... params) {

            try {
                String addlinestr = edt_AddLineultext.getText().toString();
                Character ch = addlinestr.charAt(0);
                Log.e("testlog","Entered data is:" + ch);

                iRetVal = MainActivity.insPrinter.iFlushBuf();
                Log.e("Flush", "iFlushbuffvalue is" + iRetVal);

                iRetVal = MainActivity.insPrinter.iPrinterAddLine(com.mydevice.sdk.Printer.PR_FONT180ULLARGENORMAL, ch);
                Log.e("Printadddata", "PR_FONT180ULLARGENORMAL is" + iRetVal);

                iRetVal = MainActivity.insPrinter.iPrinterAddLine(com.mydevice.sdk.Printer.PR_FONT180ULLARGEBOLD, ch);
                Log.e("Printadddata", "PR_FONT180ULLARGEBOLD is" + iRetVal);

                iRetVal = MainActivity.insPrinter.iPrinterAddLine(com.mydevice.sdk.Printer.PR_FONT180ULLARGEBOLD, ch);
                Log.e("Printadddata", "PR_FONT180ULLARGEBOLD is" + iRetVal);

                iRetVal = MainActivity.insPrinter.iPrinterAddLine(com.mydevice.sdk.Printer.PR_FONT180ULSMALLNORMAL, ch);
                Log.e("Printadddata", "PR_FONT180ULSMALLNORMAL is" + iRetVal);

                iRetVal = MainActivity.insPrinter.iPrinterAddLine(com.mydevice.sdk.Printer.PR_FONT180ULSMALLBOLD, ch);
                Log.e("Printadddata", "PR_FONT180ULSMALLBOLD is" + iRetVal);

                iRetVal = MainActivity.insPrinter.iPrinterAddLine(com.mydevice.sdk.Printer.PR_FONT180SMALLBOLD, ch);
                Log.e("Printadddata", "PR_FONT180SMALLBOLD is" + iRetVal);

                iRetVal = MainActivity.insPrinter.iPrinterAddLine(com.mydevice.sdk.Printer.PR_FONT180SMALLNORMAL, ch);
                Log.e("Printadddata", "PR_FONT180SMALLNORMALis" + iRetVal);

                iRetVal = MainActivity.insPrinter.iPrinterAddLine(com.mydevice.sdk.Printer.PR_FONT180LARGEBOLD, ch);
                Log.e("Printadddata", "PR_FONT180LARGEBOLD is" + iRetVal);

                iRetVal = MainActivity.insPrinter.iPrinterAddLine(com.mydevice.sdk.Printer.PR_FONT180LARGENORMAL, ch);
                Log.e("Printadddata", "PR_FONT180LARGENORML is" + iRetVal);

                iRetVal = MainActivity.insPrinter.iStartPrinting(1);
                Log.e("startprinting", "istartprinting is " + iRetVal);

                iRetVal = MainActivity.insPrinter.iPaperFeed();
                Log.e("paper feed", "paper feeed is " + iRetVal);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        logText.setText("Entered data is:" + ch + "\n" + "iFlushbuffvalue is" + iRetVal + "\n" + "PR_FONT180ULLARGENORMAL is" + iRetVal + "\n"
                                + "PR_FONT180ULLARGEBOLD is" + iRetVal + "\n" + "PR_FONT180ULLARGEBOLD is" + iRetVal + "\n" + "PR_FONT180ULSMALLNORMAL is" + iRetVal + "\n"
                                +  "PR_FONT180ULSMALLBOLD is" + iRetVal + "\n" + "PR_FONT180SMALLBOLD is" + iRetVal + "\n" + "PR_FONT180SMALLNORMALis" + iRetVal + "\n"
                                + "PR_FONT180LARGEBOLD is" + iRetVal + "\n" + "PR_FONT180LARGENORML is" + iRetVal + "\n" + "istartprinting is " + iRetVal );
                    }
                });

            } catch (NullPointerException e) {
                iRetVal = DEVICE_NOTCONNECTED;
                Log.e("Test Print Task", "iprinteradddata is " + iRetVal);
                return iRetVal;
            }
            return iRetVal;
        }

        @Override
        protected void onPostExecute(Integer result) {
            tmp.dismiss();
            Log.e("Test Print", "+++++LEOPARD Printer onpostExcute TestPrint+++++" + iRetVal);
            if (iRetVal == DEVICE_NOTCONNECTED) {
                Toast.makeText(LegendprinterActivity.this, "Device Not Connected", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_SUCCESS) {
                Toast.makeText(LegendprinterActivity.this, "Success", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_PLATEN_OPEN) {
                Toast.makeText(LegendprinterActivity.this, "Time Out", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_PAPER_OUT) {
                Toast.makeText(LegendprinterActivity.this, "Paper Out", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_IMPROPER_VOLTAGE) {
                Toast.makeText(LegendprinterActivity.this, "Improper Voltage", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_FAIL) {
                Toast.makeText(LegendprinterActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_PARAM_ERROR) {
                Toast.makeText(LegendprinterActivity.this, "Parameter Error", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_NO_RESPONSE) {
                Toast.makeText(LegendprinterActivity.this, "No Response", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_DEMO_VERSION) {
                Toast.makeText(LegendprinterActivity.this, "Demo Version", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_INVALID_DEVICE_ID) {
                Toast.makeText(LegendprinterActivity.this, "Invalid Device Id", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_HIGH_HEADTEMP) {
                Toast.makeText(LegendprinterActivity.this, "High Head temp", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_LOW_HEADTEMP) {
                Toast.makeText(LegendprinterActivity.this, "Low Head Temp", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_PACKET_ERROR) {
                Toast.makeText(LegendprinterActivity.this, "Packet Error", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LegendprinterActivity.this, "Failed", Toast.LENGTH_SHORT).show();

            }
            super.onPostExecute(result);
        }
    }


    private class BmpprintAsyc extends AsyncTask<Integer, Integer, Integer> {
        private ProgressDialog tmp = null;

        @Override
        protected void onPreExecute() {

            this.tmp = new ProgressDialog(LegendprinterActivity.this);
            this.tmp.setMessage("Printing....");
            this.tmp.setCancelable(false);
            this.tmp.setCanceledOnTouchOutside(false);
            this.tmp.show();
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            try {
                iRetVal = MainActivity.insPrinter.iBmpPrint(context, R.drawable.img);
                Log.e("Bmp Print Task", "Bmp print value is " + iRetVal);
                iRetVal = MainActivity.insPrinter.iBmpPrint(context, R.drawable.fish);
                Log.e("Bmp Print Task", "Bmp print value is " + iRetVal);
                iRetVal = MainActivity.insPrinter.iBmpPrint(context, R.drawable.panda);
                Log.e("Bmp Print Task", "Bmp print value is " + iRetVal);
                iRetVal = MainActivity.insPrinter.iBmpPrint(context, R.drawable.shipconverted);
                Log.e("Bmp Print Task", "Bmp print value is " + iRetVal);
                iRetVal = MainActivity.insPrinter.iBmpPrint(context, R.drawable.wolf);
                Log.e("Bmp Print Task", "Bmp print value is " + iRetVal);
                iRetVal = MainActivity.insPrinter.iBmpPrint(context, R.drawable.n1);
                Log.e("Bmp Print Task", "Bmp print value is " + iRetVal);

                iRetVal = MainActivity.insPrinter.iPaperFeed();
                Log.e("paper feed", "paper feeed is " + iRetVal);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        logText.setText("Bmp print Evolute img is " + iRetVal + "\n" + "Bmp print fish img is " + iRetVal + "\n"
                                + "Bmp print panda img is " + iRetVal + "\n" + "Bmp print ship img is " + iRetVal + "\n" + "Bmp print wolf img is " + iRetVal + "\n"
                                + "Bmp print N1 img is " + iRetVal + "\n" + "paper feeed is " + iRetVal);
                    }
                });
            } catch (NullPointerException e) {
                iRetVal = DEVICE_NOTCONNECTED;
                Log.e("Test Print Task", "Bmp print value is  " + iRetVal);

                return iRetVal;
            }
            return iRetVal;
        }

        @Override
        protected void onPostExecute(Integer result) {
            tmp.dismiss();
            Log.e("Test Print", "+++++LEOPARD Printer onpostExcute TestPrint+++++" + iRetVal);
            if (iRetVal == DEVICE_NOTCONNECTED) {
                Toast.makeText(LegendprinterActivity.this, "Device Not Connected", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_SUCCESS) {
                Toast.makeText(LegendprinterActivity.this, "Success", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_PLATEN_OPEN) {
                Toast.makeText(LegendprinterActivity.this, "Time Out", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_PAPER_OUT) {
                Toast.makeText(LegendprinterActivity.this, "Paper Out", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_IMPROPER_VOLTAGE) {
                Toast.makeText(LegendprinterActivity.this, "Improper Voltage", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_FAIL) {
                Toast.makeText(LegendprinterActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_PARAM_ERROR) {
                Toast.makeText(LegendprinterActivity.this, "Parameter Error", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_NO_RESPONSE) {
                Toast.makeText(LegendprinterActivity.this, "No Response", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_DEMO_VERSION) {
                Toast.makeText(LegendprinterActivity.this, "Demo Version", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_INVALID_DEVICE_ID) {
                Toast.makeText(LegendprinterActivity.this, "Invalid Device Id", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_HIGH_HEADTEMP) {
                Toast.makeText(LegendprinterActivity.this, "High Head temp", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_LOW_HEADTEMP) {
                Toast.makeText(LegendprinterActivity.this, "Low Head Temp", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_PACKET_ERROR) {
                Toast.makeText(LegendprinterActivity.this, "Packet Error", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LegendprinterActivity.this, "Failed", Toast.LENGTH_SHORT).show();

            }
            super.onPostExecute(result);
        }
    }

    private class BmpgreyscaleprintAsyc extends AsyncTask<Integer, Integer, Integer> {
        private ProgressDialog tmp = null;

        @Override
        protected void onPreExecute() {

            this.tmp = new ProgressDialog(LegendprinterActivity.this);
            this.tmp.setMessage("Printing....");
            this.tmp.setCancelable(false);
            this.tmp.setCanceledOnTouchOutside(false);
            this.tmp.show();
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            try {
                iRetVal = MainActivity.insPrinter.iGreyscalePrint(context, R.drawable.img);
                Log.e("Bmp Print Task", "BmpGREY print value is " + iRetVal);
                iRetVal = MainActivity.insPrinter.iGreyscalePrint(context, R.drawable.fish);
                Log.e("Bmp Print Task", "BmpGREY print value is " + iRetVal);
                iRetVal = MainActivity.insPrinter.iGreyscalePrint(context, R.drawable.panda);
                Log.e("Bmp Print Task", "BmpGREY print value is " + iRetVal);
                iRetVal = MainActivity.insPrinter.iGreyscalePrint(context, R.drawable.shipconverted);
                Log.e("Bmp Print Task", "BmpGREY print value is " + iRetVal);
                iRetVal = MainActivity.insPrinter.iGreyscalePrint(context, R.drawable.wolf);
                Log.e("Bmp Print Task", "BmpGREY print value is " + iRetVal);
                iRetVal = MainActivity.insPrinter.iGreyscalePrint(context, R.drawable.n1);
                Log.e("Bmp Print Task", "BmpGREY print value is " + iRetVal);

                iRetVal = MainActivity.insPrinter.iPaperFeed();
                Log.e("paper feed", "paper feeed is " + iRetVal);


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        logText.setText("Bmpgreyscale print Evolute img is " + iRetVal + "\n" + "Bmpgreyscale print fish img is " + iRetVal + "\n"
                                + "Bmpgreyscale print panda img is " + iRetVal + "\n" + "Bmpgreyscale print ship img is " + iRetVal + "\n" + "Bmpgreyscale print wolf img is " + iRetVal + "\n"
                                + "Bmpgreyscale print N1 img is " + iRetVal + "\n" + "paper feeed is " + iRetVal);
                    }

                });
            } catch (NullPointerException e) {
                iRetVal = DEVICE_NOTCONNECTED;
                Log.e("Test Print Task", "Greysacle print value is  " + iRetVal);

                return iRetVal;
            }
            return iRetVal;
        }

        @Override
        protected void onPostExecute(Integer result) {
            tmp.dismiss();
            Log.e("Test Print", "+++++LEOPARD Printer onpostExcute TestPrint+++++" + iRetVal);
            if (iRetVal == DEVICE_NOTCONNECTED) {
                Toast.makeText(LegendprinterActivity.this, "Device Not Connected", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_SUCCESS) {
                Toast.makeText(LegendprinterActivity.this, "Success", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_PLATEN_OPEN) {
                Toast.makeText(LegendprinterActivity.this, "Time Out", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_PAPER_OUT) {
                Toast.makeText(LegendprinterActivity.this, "Paper Out", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_IMPROPER_VOLTAGE) {
                Toast.makeText(LegendprinterActivity.this, "Improper Voltage", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_FAIL) {
                Toast.makeText(LegendprinterActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_PARAM_ERROR) {
                Toast.makeText(LegendprinterActivity.this, "Parameter Error", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_NO_RESPONSE) {
                Toast.makeText(LegendprinterActivity.this, "No Response", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_DEMO_VERSION) {
                Toast.makeText(LegendprinterActivity.this, "Demo Version", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_INVALID_DEVICE_ID) {
                Toast.makeText(LegendprinterActivity.this, "Invalid Device Id", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_HIGH_HEADTEMP) {
                Toast.makeText(LegendprinterActivity.this, "High Head temp", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_LOW_HEADTEMP) {
                Toast.makeText(LegendprinterActivity.this, "Low Head Temp", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_PACKET_ERROR) {
                Toast.makeText(LegendprinterActivity.this, "Packet Error", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LegendprinterActivity.this, "Failed", Toast.LENGTH_SHORT).show();

            }
            super.onPostExecute(result);
        }
    }

    private class PrinterdiagnosticsAsyc extends AsyncTask<Integer, Integer, Integer> {
        private ProgressDialog tmp = null;

        @Override
        protected void onPreExecute() {

            this.tmp = new ProgressDialog(LegendprinterActivity.this);
            this.tmp.setMessage("Printing....");
            this.tmp.setCancelable(false);
            this.tmp.setCanceledOnTouchOutside(false);
            this.tmp.show();
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            try {
                iRetVal = MainActivity.insPrinter.iPrinterDiagnostics();
                Log.e("paper feed", "printerdiagnostics is " + iRetVal);
            } catch (NullPointerException e) {
                iRetVal = DEVICE_NOTCONNECTED;
                Log.e("Test Print Task", "iprinterdiagnostics value is " + iRetVal);
                return iRetVal;
            }
            return iRetVal;
        }
        @Override
        protected void onPostExecute(Integer result) {
            tmp.dismiss();
            Log.e("Test Print", "+++++LEOPARD Printer onpostExcute TestPrint+++++" + iRetVal);
            if (iRetVal == DEVICE_NOTCONNECTED) {
                Toast.makeText(LegendprinterActivity.this, "Device Not Connected", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_SUCCESS) {
                Toast.makeText(LegendprinterActivity.this, "Success", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_PLATEN_OPEN) {
                Toast.makeText(LegendprinterActivity.this, "Time Out", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_PAPER_OUT) {
                Toast.makeText(LegendprinterActivity.this, "Paper Out", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_IMPROPER_VOLTAGE) {
                Toast.makeText(LegendprinterActivity.this, "Improper Voltage", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_FAIL) {
                Toast.makeText(LegendprinterActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_PARAM_ERROR) {
                Toast.makeText(LegendprinterActivity.this, "Parameter Error", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_NO_RESPONSE) {
                Toast.makeText(LegendprinterActivity.this, "No Response", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_DEMO_VERSION) {
                Toast.makeText(LegendprinterActivity.this, "Demo Version", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_INVALID_DEVICE_ID) {
                Toast.makeText(LegendprinterActivity.this, "Invalid Device Id", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_HIGH_HEADTEMP) {
                Toast.makeText(LegendprinterActivity.this, "High Head temp", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_LOW_HEADTEMP) {
                Toast.makeText(LegendprinterActivity.this, "Low Head Temp", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_PACKET_ERROR) {
                Toast.makeText(LegendprinterActivity.this, "Packet Error", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LegendprinterActivity.this, "Failed", Toast.LENGTH_SHORT).show();

            }
            super.onPostExecute(result);
        }
    }

    private class BatteryAsyc extends AsyncTask<Integer, Integer, Integer> {
        private ProgressDialog tmp = null;

        @Override
        protected void onPreExecute() {

            this.tmp = new ProgressDialog(LegendprinterActivity.this);
            this.tmp.setMessage("Printing....");
            this.tmp.setCancelable(false);
            this.tmp.setCanceledOnTouchOutside(false);
            this.tmp.show();
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            try {
                iRetVal = MainActivity.insPrinter.iGetBatteryStatus();
                Log.e("paper feed", "Batterystatus is " + iRetVal);
            } catch (NullPointerException e) {
                iRetVal = DEVICE_NOTCONNECTED;
                Log.e("Test Print Task", "iGetBatteryStatus value is " + iRetVal);
                return iRetVal;
            }
            return iRetVal;
        }
        @Override
        protected void onPostExecute(Integer result) {
            tmp.dismiss();
            Log.e("Test Print", "+++++LEOPARD Printer onpostExcute TestPrint+++++" + iRetVal);
            if (iRetVal == DEVICE_NOTCONNECTED) {
                Toast.makeText(LegendprinterActivity.this, "Device Not Connected", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_SUCCESS) {
                Toast.makeText(LegendprinterActivity.this, "Success", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_PLATEN_OPEN) {
                Toast.makeText(LegendprinterActivity.this, "Time Out", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_PAPER_OUT) {
                Toast.makeText(LegendprinterActivity.this, "Paper Out", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_IMPROPER_VOLTAGE) {
                Toast.makeText(LegendprinterActivity.this, "Improper Voltage", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_FAIL) {
                Toast.makeText(LegendprinterActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_PARAM_ERROR) {
                Toast.makeText(LegendprinterActivity.this, "Parameter Error", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_NO_RESPONSE) {
                Toast.makeText(LegendprinterActivity.this, "No Response", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_DEMO_VERSION) {
                Toast.makeText(LegendprinterActivity.this, "Demo Version", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_INVALID_DEVICE_ID) {
                Toast.makeText(LegendprinterActivity.this, "Invalid Device Id", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_HIGH_HEADTEMP) {
                Toast.makeText(LegendprinterActivity.this, "High Head temp", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_LOW_HEADTEMP) {
                Toast.makeText(LegendprinterActivity.this, "Low Head Temp", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_PACKET_ERROR) {
                Toast.makeText(LegendprinterActivity.this, "Packet Error", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LegendprinterActivity.this, "Failed", Toast.LENGTH_SHORT).show();

            }
            super.onPostExecute(result);
        }
    }

    private class BarcodeAsyc extends AsyncTask<Integer, Integer, Integer> {
        private ProgressDialog tmp = null;

        @Override
        protected void onPreExecute() {

            this.tmp = new ProgressDialog(LegendprinterActivity.this);
            this.tmp.setMessage("Printing....");
            this.tmp.setCancelable(false);
            this.tmp.setCanceledOnTouchOutside(false);
            this.tmp.show();
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            try {
                sAddData = edt_Barcodetext.getText().toString();
                String empty = sAddData;
                Log.e("testlog","Entered data is:" + empty);

                iRetVal = MainActivity.insPrinter.iFlushBuf();
                Log.e("Flush", "iFlushbuffvalue is" + iRetVal);

                iRetVal = MainActivity.insPrinter.iPrintBarcode(com.mydevice.sdk.Printer.BARCODE2OF5COMPRESSED, sAddData);
                Log.e("Printadddata", "BARCODE2OF5COMPRESSED is" + iRetVal);

                iRetVal = MainActivity.insPrinter.iPrintBarcode(com.mydevice.sdk.Printer.BARCODE2OF5UNCOMPRESSED, sAddData);
                Log.e("Printadddata", "BARCODE2OF5UNCOMPRESSED is" + iRetVal);

                iRetVal = MainActivity.insPrinter.iPrintBarcode(com.mydevice.sdk.Printer.BARCODE3OF9COMPRESSED, sAddData);
                Log.e("Printadddata", "BARCODE3OF9COMPRESSED is" + iRetVal);

                iRetVal = MainActivity.insPrinter.iPrintBarcode(com.mydevice.sdk.Printer.BARCODE3OF9UNCOMPRESSED, sAddData);
                Log.e("Printadddata", "BARCODE3OF9UNCOMPRESSED is" + iRetVal);

                iRetVal = MainActivity.insPrinter.iPrintBarcode(com.mydevice.sdk.Printer.BARCODEEAN13STANDARD, sAddData);
                Log.e("Printadddata", "BARCODEEAN13STANDARD is" + iRetVal);

                iRetVal = MainActivity.insPrinter.iPrintBarcode(com.mydevice.sdk.Printer.BARCODE_2D_QRCODE_PD417, sAddData);
                Log.e("Printadddata", "BARCODE_2D_QRCODE_PD417is" + iRetVal);

                iRetVal = MainActivity.insPrinter.iPaperFeed();
                Log.e("paper feed", "paper feeed is " + iRetVal);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        logText.setText("Entered data is:" + empty + "\n" + "iFlushbuffvalue is" + iRetVal + "\n" + "BARCODE2OF5COMPRESSED is" + iRetVal + "\n"
                                + "BARCODE2OF5UNCOMPRESSED is" + iRetVal + "\n" + "BARCODE3OF9COMPRESSED is" + iRetVal + "\n" + "BARCODE3OF9UNCOMPRESSED is" + iRetVal + "\n"
                                + "BARCODEEAN13STANDARD is" + iRetVal + "\n" + "BARCODE_2D_QRCODE_PD417is" + iRetVal + "\n" + "paper feeed is " + iRetVal);
                    }
                });
            } catch (NullPointerException e) {
                iRetVal = DEVICE_NOTCONNECTED;
                Log.e("Test Print Task", "Barcode print value is  " + iRetVal);

                return iRetVal;
            }
            return iRetVal;
        }

        @Override
        protected void onPostExecute(Integer result) {
            tmp.dismiss();
            Log.e("Test Print", "+++++LEOPARD Printer onpostExcute TestPrint+++++" + iRetVal);
            if (iRetVal == DEVICE_NOTCONNECTED) {
                Toast.makeText(LegendprinterActivity.this, "Device Not Connected", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_SUCCESS) {
                Toast.makeText(LegendprinterActivity.this, "Success", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_PLATEN_OPEN) {
                Toast.makeText(LegendprinterActivity.this, "Time Out", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_PAPER_OUT) {
                Toast.makeText(LegendprinterActivity.this, "Paper Out", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_IMPROPER_VOLTAGE) {
                Toast.makeText(LegendprinterActivity.this, "Improper Voltage", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_FAIL) {
                Toast.makeText(LegendprinterActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_PARAM_ERROR) {
                Toast.makeText(LegendprinterActivity.this, "Parameter Error", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_NO_RESPONSE) {
                Toast.makeText(LegendprinterActivity.this, "No Response", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_DEMO_VERSION) {
                Toast.makeText(LegendprinterActivity.this, "Demo Version", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_INVALID_DEVICE_ID) {
                Toast.makeText(LegendprinterActivity.this, "Invalid Device Id", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_HIGH_HEADTEMP) {
                Toast.makeText(LegendprinterActivity.this, "High Head temp", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_LOW_HEADTEMP) {
                Toast.makeText(LegendprinterActivity.this, "Low Head Temp", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_PACKET_ERROR) {
                Toast.makeText(LegendprinterActivity.this, "Packet Error", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LegendprinterActivity.this, "Failed", Toast.LENGTH_SHORT).show();

            }
            super.onPostExecute(result);
        }
    }
    private class BmppacketsAsyc extends AsyncTask<Integer, Integer, Integer> {
        private ProgressDialog tmp = null;

        @Override
        protected void onPreExecute() {

            this.tmp = new ProgressDialog(LegendprinterActivity.this);
            this.tmp.setMessage("Printing....");
            this.tmp.setCancelable(false);
            this.tmp.setCanceledOnTouchOutside(false);
            this.tmp.show();
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            try {
                bBmpPackets = MainActivity.insPrinter.bGetBmpPackets(context, R.drawable.evoimg);
                Log.e("paper feed", "Bmppandapackets  is " + bBmpPackets);
                iRetVal = MainActivity.insPrinter.iGetReturnCode();
                Log.e("Flush", " iGetReturnCode for panda packet is " + iRetVal);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        logText.setText(" iGetReturnCode for panda packet is " + iRetVal + "\n" + "Bmppandapackets  is " + bBmpPackets);
                    }
                });

//                bBmpPackets = MainActivity.insPrinter.bGetBmpPackets(context, R.drawable.n1);
//                Log.e("paper feed", "Bmpn1imageprint  is " + bBmpPackets);
//                iRetVal = MainActivity.insPrinter.iGetReturnCode();
//                Log.e("Flush", " iGetReturnCode for n1 image  packet is " + iRetVal);

            } catch (NullPointerException e) {
                iRetVal = DEVICE_NOTCONNECTED;
                Log.e("Test Print Task", "bGetBmpPackets is " + iRetVal);
                return iRetVal;
            }
            return iRetVal;
        }

        @Override
        protected void onPostExecute(Integer result) {
            tmp.dismiss();
            Log.e("Test Print", "+++++LEOPARD Printer onpostExcute TestPrint+++++" + iRetVal);
            if (iRetVal == DEVICE_NOTCONNECTED) {
                Toast.makeText(LegendprinterActivity.this, "Device Not Connected", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_SUCCESS) {
                Toast.makeText(LegendprinterActivity.this, "Success", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_PLATEN_OPEN) {
                Toast.makeText(LegendprinterActivity.this, "Time Out", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_PAPER_OUT) {
                Toast.makeText(LegendprinterActivity.this, "Paper Out", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_IMPROPER_VOLTAGE) {
                Toast.makeText(LegendprinterActivity.this, "Improper Voltage", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_FAIL) {
                Toast.makeText(LegendprinterActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_PARAM_ERROR) {
                Toast.makeText(LegendprinterActivity.this, "Parameter Error", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_NO_RESPONSE) {
                Toast.makeText(LegendprinterActivity.this, "No Response", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_DEMO_VERSION) {
                Toast.makeText(LegendprinterActivity.this, "Demo Version", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_INVALID_DEVICE_ID) {
                Toast.makeText(LegendprinterActivity.this, "Invalid Device Id", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_HIGH_HEADTEMP) {
                Toast.makeText(LegendprinterActivity.this, "High Head temp", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_LOW_HEADTEMP) {
                Toast.makeText(LegendprinterActivity.this, "Low Head Temp", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_PACKET_ERROR) {
                Toast.makeText(LegendprinterActivity.this, "Packet Error", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LegendprinterActivity.this, "Failed", Toast.LENGTH_SHORT).show();

            }
            super.onPostExecute(result);
        }
    }

    private class ReturncodeAsyc extends AsyncTask<Integer, Integer, Integer> {
        private ProgressDialog tmp = null;

        @Override
        protected Integer doInBackground(Integer... params) {
            try {

                iRetVal = MainActivity.insPrinter.iGetReturnCode();
                Log.e("Flush", " iGetReturnCode is" + iRetVal);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        logText.setText(" iGetReturnCode is" + iRetVal);
                    }
                });

            } catch (NullPointerException e) {
                iRetVal = DEVICE_NOTCONNECTED;
                Log.e("Test Print Task", "iGetReturnCode for bgetbmppacket is " + iRetVal);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        logText.setText("iGetReturnCode for bgetbmppacket is " + iRetVal);
                    }
                });
                return iRetVal;

            }
            return iRetVal;
        }
    }

    private class FirmwareAsyc extends AsyncTask<Integer, Integer, Integer> {
        private ProgressDialog tmp = null;

        @Override
        protected Integer doInBackground(Integer... params) {
            try {

                String sSerno = MainActivity.insPrinter.sGetFirmwareVersion();
                Log.e("Flush", "sGetFirmwareVersion of the device is  is" + sSerno);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        logText.setText("sGetFirmwareVersion of the device is  is" + sSerno);
                    }
                });

            } catch (NullPointerException e) {
                iRetVal = DEVICE_NOTCONNECTED;
                Log.e("Test Print Task", "sGetFirmwareVersion is " + iRetVal);
                return iRetVal;
            }
            return iRetVal;
        }
    }

    private class SerialnoAsyc extends AsyncTask<Integer, Integer, Integer> {
        private ProgressDialog tmp = null;

        @Override
        protected Integer doInBackground(Integer... params) {
            try {

                String sSerno = MainActivity.insPrinter.sGetSerialNumber();
                Log.e("Flush", "sGetSerialNumber of the device is " + sSerno);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        logText.setText("sGetSerialNumber of the device is " + sSerno);
                    }
                });

            } catch (NullPointerException e) {
                iRetVal = DEVICE_NOTCONNECTED;
                Log.e("Test Print Task", "sGetSerialNumber is " + iRetVal);
                return iRetVal;
            }
            return iRetVal;
        }
    }

    private class PeripheralsAsyc extends AsyncTask<Integer, Integer, Integer> {
        private ProgressDialog tmp = null;



        @Override
        protected Integer doInBackground(Integer... params) {
            try {

                iRetVal = MainActivity.insPrinter.iIdentifyPeripherals();
                Log.e("Flush", "Peripherals available in the devices are " + iRetVal);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        logText.setText("Peripherals available in the devices are " + iRetVal);
                    }
                });


            } catch (NullPointerException e) {
                iRetVal = DEVICE_NOTCONNECTED;
                Log.e("Test Print Task", "iIdentifyPeripherals is " + iRetVal);
                return iRetVal;
            }
            return iRetVal;
        }
    }
}

*/
