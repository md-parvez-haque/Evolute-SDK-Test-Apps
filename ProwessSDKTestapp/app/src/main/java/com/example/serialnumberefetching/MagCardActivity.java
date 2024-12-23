package com.example.serialnumberefetching;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.leopard.api.MagCard;
import com.leopard.api.Printer;

public class MagCardActivity extends AppCompatActivity {

    Button btn_readCard, btn_tracka, btn_trackb, btn_trackc, btn_returncode;
    int iRetVal, DEVICE_NOTCONNECTED = 100;

    private TextView logText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mag_card);
        logText = findViewById(R.id.logText);
        btn_readCard = findViewById(R.id.readMagCard_btn);
        btn_tracka = findViewById(R.id.tracka_btn);
        btn_trackb = findViewById(R.id.trackb_btn);
        btn_trackc = findViewById(R.id.trackc_btn);
        btn_returncode = findViewById(R.id.returncode_btn);


        btn_readCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReadMagcardAsyc readMagcardAsyc = new ReadMagcardAsyc();
                readMagcardAsyc.execute();

            }
        });
        btn_tracka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrackaAsyc trackaAsyc = new TrackaAsyc();
                trackaAsyc.execute();

            }
        });
        btn_trackb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrackbAsyc trackbAsyc = new TrackbAsyc();
                trackbAsyc.execute();

            }
        });
        btn_trackc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrackcAsyc trackcAsyc = new TrackcAsyc();
                trackcAsyc.execute();

            }
        });
        btn_returncode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              ReturncodeAsyn returncodeAsyn = new ReturncodeAsyn();
              returncodeAsyn.execute();

            }
        });
    }


    public class ReadMagcardAsyc extends AsyncTask<Integer, Integer, Integer> {
        /* displays the progress dialog untill background task is completed*/
        private ProgressDialog tmp = null;

        @Override
        protected void onPreExecute() {
            this.tmp = new ProgressDialog(MagCardActivity.this);
            this.tmp.setMessage("Printing....");
            this.tmp.setCancelable(false);
            this.tmp.setCanceledOnTouchOutside(false);
            this.tmp.show();
            super.onPreExecute();
        }

        /* Task of ReadMagcard data performing in the background*/
        @Override
        protected Integer doInBackground(Integer... params) {
            try {
                MainActivity.insMagcard.vReadMagCardData(10000);
                iRetVal = MainActivity.insMagcard.iGetReturnCode();
                Log.e("readmag", "vReadMagCardData,:" + iRetVal);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        logText.setText("vReadMagCardData,:" + iRetVal);
                    }
                });
            } catch (NullPointerException e) {
                iRetVal = DEVICE_NOTCONNECTED;
                return iRetVal;
            }
            return iRetVal;
        }

        /* This sends message to handler to display the status messages
         * of Diagnose in the dialog box */
        @Override
        protected void onPostExecute(Integer result) {
            this.tmp.dismiss();
            if (iRetVal == DEVICE_NOTCONNECTED) {
                Toast.makeText(MagCardActivity.this, "Device Not Connected", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == MagCard.MAG_SUCCESS) {
                Toast.makeText(MagCardActivity.this, "Success", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == MagCard.MAG_FAIL) {
                Toast.makeText(MagCardActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == MagCard.MAG_LRC_ERROR) {
                Toast.makeText(MagCardActivity.this, "LRC Error", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == MagCard.MAG_NO_DATA) {
                Toast.makeText(MagCardActivity.this, "Magnetic No Data", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == MagCard.MAG_ILLEGAL_LIBRARY) {
                Toast.makeText(MagCardActivity.this, "Illegal Library", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == MagCard.MAG_DEMO_VERSION) {
                Toast.makeText(MagCardActivity.this, "Demo Version", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == MagCard.MAG_TIME_OUT) {
                Toast.makeText(MagCardActivity.this, "Time Out", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == MagCard.MAG_INACTIVE_PERIPHERAL) {
                Toast.makeText(MagCardActivity.this, "Inactive Peripheral", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == MagCard.MAG_PARAM_ERROR) {
                Toast.makeText(MagCardActivity.this, "Parameter Error", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_DEMO_VERSION) {
                Toast.makeText(MagCardActivity.this, "Demo Version", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_INVALID_DEVICE_ID) {
                Toast.makeText(MagCardActivity.this, "Invalid Device Id", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_ILLEGAL_LIBRARY) {
                Toast.makeText(MagCardActivity.this, "Illegal Library", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }

    public class TrackaAsyc extends AsyncTask<Integer, Integer, Integer> {
        /* displays the progress dialog untill background task is completed*/
        private ProgressDialog tmp = null;

        @Override
        protected void onPreExecute() {
            this.tmp = new ProgressDialog(MagCardActivity.this);
            this.tmp.setMessage("Printing....");
            this.tmp.setCancelable(false);
            this.tmp.setCanceledOnTouchOutside(false);
            this.tmp.show();
            super.onPreExecute();
        }

        /* Task of ReadMagcard data performing in the background*/
        @Override
        protected Integer doInBackground(Integer... params) {
            try {
                MainActivity.insMagcard.sGetTrack1Data();
                Log.e("Track 1", "iRetvalue,:" + MainActivity.insMagcard.iGetReturnCode());
                Log.e("Track 1 : ", " sGetTrack1Data,: is" + MainActivity.insMagcard.sGetTrack1Data());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        logText.setText("iRetvalue,:" + MainActivity.insMagcard.iGetReturnCode() + "\n"
                                + " sGetTrack1Data,: is" + MainActivity.insMagcard.sGetTrack1Data());
                    }
                });
            } catch (NullPointerException e) {
                iRetVal = DEVICE_NOTCONNECTED;
                return iRetVal;
            }
            return iRetVal;
        }

        /* This sends message to handler to display the status messages
         * of Diagnose in the dialog box */
        @Override
        protected void onPostExecute(Integer result) {
            this.tmp.dismiss();
            if (iRetVal == DEVICE_NOTCONNECTED) {
                Toast.makeText(MagCardActivity.this, "Device Not Connected", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == MagCard.MAG_SUCCESS) {
                Toast.makeText(MagCardActivity.this, "Success", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == MagCard.MAG_FAIL) {
                Toast.makeText(MagCardActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == MagCard.MAG_LRC_ERROR) {
                Toast.makeText(MagCardActivity.this, "LRC Error", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == MagCard.MAG_NO_DATA) {
                Toast.makeText(MagCardActivity.this, "Magnetic No Data", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == MagCard.MAG_ILLEGAL_LIBRARY) {
                Toast.makeText(MagCardActivity.this, "Illegal Library", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == MagCard.MAG_DEMO_VERSION) {
                Toast.makeText(MagCardActivity.this, "Demo Version", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == MagCard.MAG_TIME_OUT) {
                Toast.makeText(MagCardActivity.this, "Time Out", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == MagCard.MAG_INACTIVE_PERIPHERAL) {
                Toast.makeText(MagCardActivity.this, "Inactive Peripheral", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == MagCard.MAG_PARAM_ERROR) {
                Toast.makeText(MagCardActivity.this, "Parameter Error", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_DEMO_VERSION) {
                Toast.makeText(MagCardActivity.this, "Demo Version", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_INVALID_DEVICE_ID) {
                Toast.makeText(MagCardActivity.this, "Invalid Device Id", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_ILLEGAL_LIBRARY) {
                Toast.makeText(MagCardActivity.this, "Illegal Library", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }

    public class TrackbAsyc extends AsyncTask<Integer, Integer, Integer> {
        /* displays the progress dialog untill background task is completed*/
        private ProgressDialog tmp = null;

        @Override
        protected void onPreExecute() {
            this.tmp = new ProgressDialog(MagCardActivity.this);
            this.tmp.setMessage("Printing....");
            this.tmp.setCancelable(false);
            this.tmp.setCanceledOnTouchOutside(false);
            this.tmp.show();
            super.onPreExecute();
        }

        /* Task of ReadMagcard data performing in the background*/
        @Override
        protected Integer doInBackground(Integer... params) {
            try {
                MainActivity.insMagcard.sGetTrack2Data();
                Log.e("Track 2", "iRetvalue,:" + MainActivity.insMagcard.iGetReturnCode());
                Log.e("Track 2 : ", " sGetTrack2Data,: is" + MainActivity.insMagcard.sGetTrack2Data());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        logText.setText("iRetvalue,:" + MainActivity.insMagcard.iGetReturnCode() + "\n"
                                + " sGetTrack2Data,: is" + MainActivity.insMagcard.sGetTrack2Data());
                    }
                });
            } catch (NullPointerException e) {
                iRetVal = DEVICE_NOTCONNECTED;
                return iRetVal;
            }
            return iRetVal;
        }

        /* This sends message to handler to display the status messages
         * of Diagnose in the dialog box */
        @Override
        protected void onPostExecute(Integer result) {
            this.tmp.dismiss();
            if (iRetVal == DEVICE_NOTCONNECTED) {
                Toast.makeText(MagCardActivity.this, "Device Not Connected", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == MagCard.MAG_SUCCESS) {
                Toast.makeText(MagCardActivity.this, "Success", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == MagCard.MAG_FAIL) {
                Toast.makeText(MagCardActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == MagCard.MAG_LRC_ERROR) {
                Toast.makeText(MagCardActivity.this, "LRC Error", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == MagCard.MAG_NO_DATA) {
                Toast.makeText(MagCardActivity.this, "Magnetic No Data", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == MagCard.MAG_ILLEGAL_LIBRARY) {
                Toast.makeText(MagCardActivity.this, "Illegal Library", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == MagCard.MAG_DEMO_VERSION) {
                Toast.makeText(MagCardActivity.this, "Demo Version", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == MagCard.MAG_TIME_OUT) {
                Toast.makeText(MagCardActivity.this, "Time Out", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == MagCard.MAG_INACTIVE_PERIPHERAL) {
                Toast.makeText(MagCardActivity.this, "Inactive Peripheral", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == MagCard.MAG_PARAM_ERROR) {
                Toast.makeText(MagCardActivity.this, "Parameter Error", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_DEMO_VERSION) {
                Toast.makeText(MagCardActivity.this, "Demo Version", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_INVALID_DEVICE_ID) {
                Toast.makeText(MagCardActivity.this, "Invalid Device Id", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_ILLEGAL_LIBRARY) {
                Toast.makeText(MagCardActivity.this, "Illegal Library", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }

    public class TrackcAsyc extends AsyncTask<Integer, Integer, Integer> {
        /* displays the progress dialog untill background task is completed*/
        private ProgressDialog tmp = null;

        @Override
        protected void onPreExecute() {
            this.tmp = new ProgressDialog(MagCardActivity.this);
            this.tmp.setMessage("Printing....");
            this.tmp.setCancelable(false);
            this.tmp.setCanceledOnTouchOutside(false);
            this.tmp.show();
            super.onPreExecute();
        }

        /* Task of ReadMagcard data performing in the background*/
        @Override
        protected Integer doInBackground(Integer... params) {
            try {
                MainActivity.insMagcard.sGetTrack3Data();
                Log.e("Track 3", "iRetvalue,:" + MainActivity.insMagcard.iGetReturnCode());
                Log.e("Track 3: ", " sGetTrack3Data,: is" + MainActivity.insMagcard.sGetTrack3Data());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        logText.setText("iRetvalue,:" + MainActivity.insMagcard.iGetReturnCode() + "\n"
                                + " sGetTrack3Data,: is" + MainActivity.insMagcard.sGetTrack3Data());
                    }
                });
            } catch (NullPointerException e) {
                iRetVal = DEVICE_NOTCONNECTED;
                return iRetVal;
            }
            return iRetVal;
        }

        /* This sends message to handler to display the status messages
         * of Diagnose in the dialog box */
        @Override
        protected void onPostExecute(Integer result) {
            this.tmp.dismiss();
            if (iRetVal == DEVICE_NOTCONNECTED) {
                Toast.makeText(MagCardActivity.this, "Device Not Connected", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == MagCard.MAG_SUCCESS) {
                Toast.makeText(MagCardActivity.this, "Success", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == MagCard.MAG_FAIL) {
                Toast.makeText(MagCardActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == MagCard.MAG_LRC_ERROR) {
                Toast.makeText(MagCardActivity.this, "LRC Error", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == MagCard.MAG_NO_DATA) {
                Toast.makeText(MagCardActivity.this, "Magnetic No Data", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == MagCard.MAG_ILLEGAL_LIBRARY) {
                Toast.makeText(MagCardActivity.this, "Illegal Library", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == MagCard.MAG_DEMO_VERSION) {
                Toast.makeText(MagCardActivity.this, "Demo Version", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == MagCard.MAG_TIME_OUT) {
                Toast.makeText(MagCardActivity.this, "Time Out", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == MagCard.MAG_INACTIVE_PERIPHERAL) {
                Toast.makeText(MagCardActivity.this, "Inactive Peripheral", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == MagCard.MAG_PARAM_ERROR) {
                Toast.makeText(MagCardActivity.this, "Parameter Error", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_DEMO_VERSION) {
                Toast.makeText(MagCardActivity.this, "Demo Version", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_INVALID_DEVICE_ID) {
                Toast.makeText(MagCardActivity.this, "Invalid Device Id", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == Printer.PR_ILLEGAL_LIBRARY) {
                Toast.makeText(MagCardActivity.this, "Illegal Library", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }

    public class ReturncodeAsyn extends AsyncTask<Integer, Integer, Integer> {
        int iRetVal;

        // displays the progress dialog until background task is completed
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        // Task of CaptureFinger performing in the background
        @Override
        protected Integer doInBackground(Integer... params) {
            try {
                iRetVal = MainActivity.insMagcard.iGetReturnCode();
                Log.e("API RETURN VALUES", "iRetValue,:" + iRetVal);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        logText.setText( "API RETURN VALUES,:" + iRetVal);
                    }
                });
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            return iRetVal;
        }
    }
}