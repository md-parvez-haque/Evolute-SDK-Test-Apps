package com.example.serialnumberefetching;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.leopard.api.HexString;
import com.leopard.api.SmartCard;

public class SmartCardActivity extends AppCompatActivity {
    Button btn_powerUp, btn_powerupsec, btn_scpowerup, btn_powerdownpr, btn_scpowerupsec, btn_powerdownsec, btn_cardstatuspr, btn_cardstatussec;

    Button btn_apdupr,btn_apdusec;
    int iRetVal, DEVICE_NOTCONNECTED = 100;
    String TAG = "SmartCard Activity";

    byte[] responsebuf=new byte[260];

    private TextView logText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_card);

        logText = findViewById(R.id.logText);
        btn_scpowerup = findViewById(R.id.scpowerup_btn);
        btn_powerdownpr = findViewById(R.id.powerdownpr_btn);
        btn_scpowerupsec = findViewById(R.id.scpowerupsec_btn);
        btn_powerdownsec = findViewById(R.id.powerdownsec_btn);
        btn_cardstatuspr = findViewById(R.id.cardstatuspr_btn);
        btn_cardstatussec = findViewById(R.id.cardstatussec_btn);
        btn_apdupr = findViewById(R.id.apdupr_btn);
        btn_apdusec = findViewById(R.id.apdusec_btn);

        btn_scpowerup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //step 5
                ScPowerupAsyc scPowerupAsyc = new ScPowerupAsyc();
                scPowerupAsyc.execute(0);
            }
        });
        btn_powerdownpr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //step 5
                PowerdownAsyc powerdownAsyc = new PowerdownAsyc();
                powerdownAsyc.execute(0);
            }
        });
        btn_scpowerupsec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //step 5
                ScPowerupsecAsyc scPowerupsecAsyc = new ScPowerupsecAsyc();
                scPowerupsecAsyc.execute(0);
            }
        });
        btn_powerdownsec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //step 5
                PowerdownsecAsyc powerdownsecAsyc = new PowerdownsecAsyc();
                powerdownsecAsyc.execute(0);
            }
        });
        btn_cardstatuspr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //step 5
                CardstatusprAsyc cardstatusprAsyc = new CardstatusprAsyc();
                cardstatusprAsyc.execute(0);
            }
        });
        btn_cardstatussec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //step 5
                CardstatussecAsyc cardstatussecAsyc = new CardstatussecAsyc();
                cardstatussecAsyc.execute(0);
            }
        });
        btn_apdupr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //step 5
             ApduprAsyc apduprAsyc = new ApduprAsyc();
               apduprAsyc.execute(0);
            }
        });
        btn_apdusec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //step 5
               ApdusecAsyc apdusecAsyc= new ApdusecAsyc();
              apdusecAsyc.execute(0);
            }
        });

    }

    byte[] bAtrResp1 = new byte[300];


    private class PowerupAsyc extends AsyncTask<Integer, Integer, Integer> {
        /* displays the progress dialog until background task is completed*/
        private ProgressDialog tmp = null;

        @Override
        protected void onPreExecute() {
            this.tmp = new ProgressDialog(SmartCardActivity.this);
            this.tmp.setMessage("Processing....");
            this.tmp.setCancelable(false);
            this.tmp.setCanceledOnTouchOutside(false);
            this.tmp.show();
            super.onPreExecute();
        }

        /* Task of SmartCardAsyc performing in the background*/
        @Override
        protected Integer doInBackground(Integer... params) {
            try {
                iRetVal = MainActivity.insSmartcard.iSelectSCReader(SmartCard.SC_SecondarySCReader);
                Log.e("power up ", "secondary power up" + iRetVal);
                if (iRetVal == SmartCard.SC_SUCCESS) {
                    iRetVal = MainActivity.insSmartcard.iSCPowerUpCommand((byte) 0x27, bAtrResp1);
                } else {
                    return iRetVal;
                }

            } catch (Exception e) {
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
            Log.e("power up ", "secondary card power up" + iRetVal);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    logText.setText("secondary card power up" + iRetVal);
                }
            });
            if (iRetVal > 0) {

            } else if (iRetVal == DEVICE_NOTCONNECTED) {
            } else if (iRetVal == SmartCard.SC_FAILURE) {
            } else if (iRetVal == SmartCard.NOT_IN_SMARTCARD_MODE) {
            } else if (iRetVal == SmartCard.READ_TIME_OUT) {
            } else if (iRetVal == SmartCard.PARAM_ERROR) {
            } else if (iRetVal == SmartCard.UNKNOWN_DRIVER) {
            } else if (iRetVal == SmartCard.IMPOSSIBLE_OP_DRIVER) {
            } else if (iRetVal == SmartCard.INCORRECT_ARGUMENTS) {
            } else if (iRetVal == SmartCard.UNKNOWN_READER_CMD) {
            } else if (iRetVal == SmartCard.RESP_BUFFER_OVERFLOW) {
            } else if (iRetVal == SmartCard.WRONG_RES_UPON_RESET) {
            } else if (iRetVal == SmartCard.MSG_LEN_EXCEEDS) {
            } else if (iRetVal == SmartCard.BYTE_READING_ERR) {
            } else if (iRetVal == SmartCard.CARD_POWERED_DOWN) {
            } else if (iRetVal == SmartCard.CMD_INCORRECT_PARAM) {
            } else if (iRetVal == SmartCard.INCORRECT_TCK_BYTE) {
            } else if (iRetVal == SmartCard.CARD_RESET_ERROR) {
            } else if (iRetVal == SmartCard.PROTOCOL_ERROR) {
            } else if (iRetVal == SmartCard.PARITY_ERROR) {
            } else if (iRetVal == SmartCard.CARD_ABORTED) {
            } else if (iRetVal == SmartCard.READER_ABORTED) {
            } else if (iRetVal == SmartCard.RESYNCH_SUCCESS) {
            } else if (iRetVal == SmartCard.PROTOCOL_PARAM_ERR) {
            } else if (iRetVal == SmartCard.ALREADY_CARD_POWERED_DOWN) {
            } else if (iRetVal == SmartCard.PCLINK_CMD_NOT_SUPPORTED) {
            } else if (iRetVal == SmartCard.INVALID_PROCEDUREBYTE) {
            } else if (iRetVal == SmartCard.SC_NOT_INSERTED) {
            } else if (iRetVal == SmartCard.SC_NOT_INSERTED) {
            } else if (iRetVal == SmartCard.SC_DEMO_VERSION) {
            } else if (iRetVal == SmartCard.SC_INVALID_DEVICE_ID) {
            } else if (iRetVal == SmartCard.SC_ILLEGAL_LIBRARY) {
            } else if (iRetVal == DEVICE_NOTCONNECTED) {
            } else if (iRetVal == SmartCard.SC_INACTIVE_PERIPHERAL) {
            }
            super.onPostExecute(result);
        }
    }

    private class ScPowerupAsyc extends AsyncTask<Integer, Integer, Integer> {
        /* displays the progress dialog until background task is completed*/
        private ProgressDialog tmp = null;

        @Override
        protected void onPreExecute() {
            this.tmp = new ProgressDialog(SmartCardActivity.this);
            this.tmp.setMessage("Processing....");
            this.tmp.setCancelable(false);
            this.tmp.setCanceledOnTouchOutside(false);
            this.tmp.show();
            super.onPreExecute();
        }

        /* Task of SmartCardAsyc performing in the background*/
        @Override
        protected Integer doInBackground(Integer... params) {
            try {
                iRetVal = MainActivity.insSmartcard.iSelectSCReader(SmartCard.SC_PrimarySCReader);
                Log.e("power up ", "primary power up" + iRetVal);
                if (iRetVal == SmartCard.SC_SUCCESS) {
                    iRetVal = MainActivity.insSmartcard.iSCPowerUpCommand((byte) 0x27, bAtrResp1);
                } else {
                    return iRetVal;
                }

            } catch (Exception e) {
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
            Log.e("power up ", "primary card power up is " + iRetVal);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    logText.setText("primary card power up is " + iRetVal);
                }
            });
            if (iRetVal > 0) {

            } else if (iRetVal == DEVICE_NOTCONNECTED) {
            } else if (iRetVal == SmartCard.SC_FAILURE) {
            } else if (iRetVal == SmartCard.NOT_IN_SMARTCARD_MODE) {
            } else if (iRetVal == SmartCard.READ_TIME_OUT) {
            } else if (iRetVal == SmartCard.PARAM_ERROR) {
            } else if (iRetVal == SmartCard.UNKNOWN_DRIVER) {
            } else if (iRetVal == SmartCard.IMPOSSIBLE_OP_DRIVER) {
            } else if (iRetVal == SmartCard.INCORRECT_ARGUMENTS) {
            } else if (iRetVal == SmartCard.UNKNOWN_READER_CMD) {
            } else if (iRetVal == SmartCard.RESP_BUFFER_OVERFLOW) {
            } else if (iRetVal == SmartCard.WRONG_RES_UPON_RESET) {
            } else if (iRetVal == SmartCard.MSG_LEN_EXCEEDS) {
            } else if (iRetVal == SmartCard.BYTE_READING_ERR) {
            } else if (iRetVal == SmartCard.CARD_POWERED_DOWN) {
            } else if (iRetVal == SmartCard.CMD_INCORRECT_PARAM) {
            } else if (iRetVal == SmartCard.INCORRECT_TCK_BYTE) {
            } else if (iRetVal == SmartCard.CARD_RESET_ERROR) {
            } else if (iRetVal == SmartCard.PROTOCOL_ERROR) {
            } else if (iRetVal == SmartCard.PARITY_ERROR) {
            } else if (iRetVal == SmartCard.CARD_ABORTED) {
            } else if (iRetVal == SmartCard.READER_ABORTED) {
            } else if (iRetVal == SmartCard.RESYNCH_SUCCESS) {
            } else if (iRetVal == SmartCard.PROTOCOL_PARAM_ERR) {
            } else if (iRetVal == SmartCard.ALREADY_CARD_POWERED_DOWN) {
            } else if (iRetVal == SmartCard.PCLINK_CMD_NOT_SUPPORTED) {
            } else if (iRetVal == SmartCard.INVALID_PROCEDUREBYTE) {
            } else if (iRetVal == SmartCard.SC_NOT_INSERTED) {
            } else if (iRetVal == SmartCard.SC_NOT_INSERTED) {
            } else if (iRetVal == SmartCard.SC_DEMO_VERSION) {
            } else if (iRetVal == SmartCard.SC_INVALID_DEVICE_ID) {
            } else if (iRetVal == SmartCard.SC_ILLEGAL_LIBRARY) {
            } else if (iRetVal == DEVICE_NOTCONNECTED) {
            } else if (iRetVal == SmartCard.SC_INACTIVE_PERIPHERAL) {
            }
            super.onPostExecute(result);
        }
    }

    private class ScPowerupsecAsyc extends AsyncTask<Integer, Integer, Integer> {
        /* displays the progress dialog until background task is completed*/
        private ProgressDialog tmp = null;

        @Override
        protected void onPreExecute() {
            this.tmp = new ProgressDialog(SmartCardActivity.this);
            this.tmp.setMessage("Processing....");
            this.tmp.setCancelable(false);
            this.tmp.setCanceledOnTouchOutside(false);
            this.tmp.show();
            super.onPreExecute();
        }

        /* Task of SmartCardAsyc performing in the background*/
        @Override
        protected Integer doInBackground(Integer... params) {
            try {
                iRetVal = MainActivity.insSmartcard.iSelectSCReader(SmartCard.SC_SecondarySCReader);
                Log.e("power up ", "iSelectSCReader secondary" + iRetVal);
                if (iRetVal == SmartCard.SC_SUCCESS) {
                    iRetVal = MainActivity.insSmartcard.iSCPowerUpCommand((byte) 0x27, bAtrResp1);
                } else {
                    return iRetVal;
                }

            } catch (Exception e) {
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
            Log.e("power up ", "secondary card power up is " + iRetVal);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    logText.setText("secondary card power up is " + iRetVal);
                }
            });
            if (iRetVal > 0) {

            } else if (iRetVal == DEVICE_NOTCONNECTED) {
            } else if (iRetVal == SmartCard.SC_FAILURE) {
            } else if (iRetVal == SmartCard.NOT_IN_SMARTCARD_MODE) {
            } else if (iRetVal == SmartCard.READ_TIME_OUT) {
            } else if (iRetVal == SmartCard.PARAM_ERROR) {
            } else if (iRetVal == SmartCard.UNKNOWN_DRIVER) {
            } else if (iRetVal == SmartCard.IMPOSSIBLE_OP_DRIVER) {
            } else if (iRetVal == SmartCard.INCORRECT_ARGUMENTS) {
            } else if (iRetVal == SmartCard.UNKNOWN_READER_CMD) {
            } else if (iRetVal == SmartCard.RESP_BUFFER_OVERFLOW) {
            } else if (iRetVal == SmartCard.WRONG_RES_UPON_RESET) {
            } else if (iRetVal == SmartCard.MSG_LEN_EXCEEDS) {
            } else if (iRetVal == SmartCard.BYTE_READING_ERR) {
            } else if (iRetVal == SmartCard.CARD_POWERED_DOWN) {
            } else if (iRetVal == SmartCard.CMD_INCORRECT_PARAM) {
            } else if (iRetVal == SmartCard.INCORRECT_TCK_BYTE) {
            } else if (iRetVal == SmartCard.CARD_RESET_ERROR) {
            } else if (iRetVal == SmartCard.PROTOCOL_ERROR) {
            } else if (iRetVal == SmartCard.PARITY_ERROR) {
            } else if (iRetVal == SmartCard.CARD_ABORTED) {
            } else if (iRetVal == SmartCard.READER_ABORTED) {
            } else if (iRetVal == SmartCard.RESYNCH_SUCCESS) {
            } else if (iRetVal == SmartCard.PROTOCOL_PARAM_ERR) {
            } else if (iRetVal == SmartCard.ALREADY_CARD_POWERED_DOWN) {
            } else if (iRetVal == SmartCard.PCLINK_CMD_NOT_SUPPORTED) {
            } else if (iRetVal == SmartCard.INVALID_PROCEDUREBYTE) {
            } else if (iRetVal == SmartCard.SC_NOT_INSERTED) {
            } else if (iRetVal == SmartCard.SC_NOT_INSERTED) {
            } else if (iRetVal == SmartCard.SC_DEMO_VERSION) {
            } else if (iRetVal == SmartCard.SC_INVALID_DEVICE_ID) {
            } else if (iRetVal == SmartCard.SC_ILLEGAL_LIBRARY) {
            } else if (iRetVal == DEVICE_NOTCONNECTED) {
            } else if (iRetVal == SmartCard.SC_INACTIVE_PERIPHERAL) {
            }
            super.onPostExecute(result);
        }
    }

    private class PowerdownAsyc extends AsyncTask<Integer, Integer, Integer> {
        /* displays the progress dialog until background task is completed*/
        private ProgressDialog tmp = null;

        @Override
        protected void onPreExecute() {
            this.tmp = new ProgressDialog(SmartCardActivity.this);
            this.tmp.setMessage("Processing....");
            this.tmp.setCancelable(false);
            this.tmp.setCanceledOnTouchOutside(false);
            this.tmp.show();
            super.onPreExecute();
        }

        /* Task of SmartCardAsyc performing in the background*/
        @Override
        protected Integer doInBackground(Integer... params) {
            try {
                iRetVal = MainActivity.insSmartcard.iSelectSCReader(SmartCard.SC_PrimarySCReader);
                Log.e("power down", "iSelectSCprimaryReader" + iRetVal);
                if (iRetVal == SmartCard.SC_SUCCESS) {
                    iRetVal = MainActivity.insSmartcard.iSCPowerDown();
                    Log.e("power down", "iSCPowerDown " + iRetVal);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            logText.setText("iSelectSCprimaryReader" + iRetVal + "\n"
                                    + "iSCPowerDown " + iRetVal);
                        }
                    });
                } else {
                    return iRetVal;
                }

            } catch (Exception e) {
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
            Log.e(TAG, "LEOPARD FPS SMART  onPostExecute Val" + iRetVal);
            if (iRetVal > 0) {

            } else if (iRetVal == DEVICE_NOTCONNECTED) {
            } else if (iRetVal == SmartCard.SC_FAILURE) {
            } else if (iRetVal == SmartCard.NOT_IN_SMARTCARD_MODE) {
            } else if (iRetVal == SmartCard.READ_TIME_OUT) {
            } else if (iRetVal == SmartCard.PARAM_ERROR) {
            } else if (iRetVal == SmartCard.UNKNOWN_DRIVER) {
            } else if (iRetVal == SmartCard.IMPOSSIBLE_OP_DRIVER) {
            } else if (iRetVal == SmartCard.INCORRECT_ARGUMENTS) {
            } else if (iRetVal == SmartCard.UNKNOWN_READER_CMD) {
            } else if (iRetVal == SmartCard.RESP_BUFFER_OVERFLOW) {
            } else if (iRetVal == SmartCard.WRONG_RES_UPON_RESET) {
            } else if (iRetVal == SmartCard.MSG_LEN_EXCEEDS) {
            } else if (iRetVal == SmartCard.BYTE_READING_ERR) {
            } else if (iRetVal == SmartCard.CARD_POWERED_DOWN) {
            } else if (iRetVal == SmartCard.CMD_INCORRECT_PARAM) {
            } else if (iRetVal == SmartCard.INCORRECT_TCK_BYTE) {
            } else if (iRetVal == SmartCard.CARD_RESET_ERROR) {
            } else if (iRetVal == SmartCard.PROTOCOL_ERROR) {
            } else if (iRetVal == SmartCard.PARITY_ERROR) {
            } else if (iRetVal == SmartCard.CARD_ABORTED) {
            } else if (iRetVal == SmartCard.READER_ABORTED) {
            } else if (iRetVal == SmartCard.RESYNCH_SUCCESS) {
            } else if (iRetVal == SmartCard.PROTOCOL_PARAM_ERR) {
            } else if (iRetVal == SmartCard.ALREADY_CARD_POWERED_DOWN) {
            } else if (iRetVal == SmartCard.PCLINK_CMD_NOT_SUPPORTED) {
            } else if (iRetVal == SmartCard.INVALID_PROCEDUREBYTE) {
            } else if (iRetVal == SmartCard.SC_NOT_INSERTED) {
            } else if (iRetVal == SmartCard.SC_NOT_INSERTED) {
            } else if (iRetVal == SmartCard.SC_DEMO_VERSION) {
            } else if (iRetVal == SmartCard.SC_INVALID_DEVICE_ID) {
            } else if (iRetVal == SmartCard.SC_ILLEGAL_LIBRARY) {
            } else if (iRetVal == DEVICE_NOTCONNECTED) {
            } else if (iRetVal == SmartCard.SC_INACTIVE_PERIPHERAL) {
            }
            super.onPostExecute(result);
        }
    }

    private class PowerdownsecAsyc extends AsyncTask<Integer, Integer, Integer> {
        /* displays the progress dialog until background task is completed*/
        private ProgressDialog tmp = null;

        @Override
        protected void onPreExecute() {
            this.tmp = new ProgressDialog(SmartCardActivity.this);
            this.tmp.setMessage("Processing....");
            this.tmp.setCancelable(false);
            this.tmp.setCanceledOnTouchOutside(false);
            this.tmp.show();
            super.onPreExecute();
        }

        /* Task of SmartCardAsyc performing in the background*/
        @Override
        protected Integer doInBackground(Integer... params) {
            try {
                iRetVal = MainActivity.insSmartcard.iSelectSCReader(SmartCard.SC_SecondarySCReader);
                Log.e("power down", "iSelectSCReader secondary" + iRetVal);
                if (iRetVal == SmartCard.SC_SUCCESS) {
                    iRetVal = MainActivity.insSmartcard.iSCPowerDown();
                    Log.e("power down", "iSCPowerDown" + iRetVal);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            logText.setText("iSelectSCReader secondary" + iRetVal + "\n"
                            + "iSCPowerDown" + iRetVal);
                        }
                    });
                } else {
                    return iRetVal;
                }

            } catch (Exception e) {
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
            Log.e(TAG, "LEOPARD FPS SMART  onPostExecute Val" + iRetVal);
            if (iRetVal > 0) {

            } else if (iRetVal == DEVICE_NOTCONNECTED) {
            } else if (iRetVal == SmartCard.SC_FAILURE) {
            } else if (iRetVal == SmartCard.NOT_IN_SMARTCARD_MODE) {
            } else if (iRetVal == SmartCard.READ_TIME_OUT) {
            } else if (iRetVal == SmartCard.PARAM_ERROR) {
            } else if (iRetVal == SmartCard.UNKNOWN_DRIVER) {
            } else if (iRetVal == SmartCard.IMPOSSIBLE_OP_DRIVER) {
            } else if (iRetVal == SmartCard.INCORRECT_ARGUMENTS) {
            } else if (iRetVal == SmartCard.UNKNOWN_READER_CMD) {
            } else if (iRetVal == SmartCard.RESP_BUFFER_OVERFLOW) {
            } else if (iRetVal == SmartCard.WRONG_RES_UPON_RESET) {
            } else if (iRetVal == SmartCard.MSG_LEN_EXCEEDS) {
            } else if (iRetVal == SmartCard.BYTE_READING_ERR) {
            } else if (iRetVal == SmartCard.CARD_POWERED_DOWN) {
            } else if (iRetVal == SmartCard.CMD_INCORRECT_PARAM) {
            } else if (iRetVal == SmartCard.INCORRECT_TCK_BYTE) {
            } else if (iRetVal == SmartCard.CARD_RESET_ERROR) {
            } else if (iRetVal == SmartCard.PROTOCOL_ERROR) {
            } else if (iRetVal == SmartCard.PARITY_ERROR) {
            } else if (iRetVal == SmartCard.CARD_ABORTED) {
            } else if (iRetVal == SmartCard.READER_ABORTED) {
            } else if (iRetVal == SmartCard.RESYNCH_SUCCESS) {
            } else if (iRetVal == SmartCard.PROTOCOL_PARAM_ERR) {
            } else if (iRetVal == SmartCard.ALREADY_CARD_POWERED_DOWN) {
            } else if (iRetVal == SmartCard.PCLINK_CMD_NOT_SUPPORTED) {
            } else if (iRetVal == SmartCard.INVALID_PROCEDUREBYTE) {
            } else if (iRetVal == SmartCard.SC_NOT_INSERTED) {
            } else if (iRetVal == SmartCard.SC_NOT_INSERTED) {
            } else if (iRetVal == SmartCard.SC_DEMO_VERSION) {
            } else if (iRetVal == SmartCard.SC_INVALID_DEVICE_ID) {
            } else if (iRetVal == SmartCard.SC_ILLEGAL_LIBRARY) {
            } else if (iRetVal == DEVICE_NOTCONNECTED) {
            } else if (iRetVal == SmartCard.SC_INACTIVE_PERIPHERAL) {
            }
            super.onPostExecute(result);
        }
    }

    private class CardstatusprAsyc extends AsyncTask<Integer, Integer, Integer> {
        /* displays the progress dialog until background task is completed*/
        private ProgressDialog tmp = null;

        @Override
        protected void onPreExecute() {
            this.tmp = new ProgressDialog(SmartCardActivity.this);
            this.tmp.setMessage("Processing....");
            this.tmp.setCancelable(false);
            this.tmp.setCanceledOnTouchOutside(false);
            this.tmp.show();
            super.onPreExecute();
        }

        /* Task of SmartCardAsyc performing in the background*/
        @Override
        protected Integer doInBackground(Integer... params) {
            try {
                iRetVal = MainActivity.insSmartcard.iSelectSCReader(SmartCard.SC_PrimarySCReader);
                Log.e("card status", "iSelectSCReader primary" + iRetVal);
                if (iRetVal == SmartCard.SC_SUCCESS) {
                    iRetVal = MainActivity.insSmartcard.iSCGetCardStatus();
                    Log.e("card status", "iSCGetCardStatus" + iRetVal);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            logText.setText("iSelectSCReader primary" + iRetVal + "\n"
                                    + "iSCGetCardStatus" + iRetVal);
                        }
                    });
                } else {
                    return iRetVal;
                }

            } catch (Exception e) {
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
            Log.e(TAG, "LEOPARD FPS SMART  onPostExecute Val" + iRetVal);
            if (iRetVal > 0) {

            } else if (iRetVal == DEVICE_NOTCONNECTED) {
            } else if (iRetVal == SmartCard.SC_FAILURE) {
            } else if (iRetVal == SmartCard.NOT_IN_SMARTCARD_MODE) {
            } else if (iRetVal == SmartCard.READ_TIME_OUT) {
            } else if (iRetVal == SmartCard.PARAM_ERROR) {
            } else if (iRetVal == SmartCard.UNKNOWN_DRIVER) {
            } else if (iRetVal == SmartCard.IMPOSSIBLE_OP_DRIVER) {
            } else if (iRetVal == SmartCard.INCORRECT_ARGUMENTS) {
            } else if (iRetVal == SmartCard.UNKNOWN_READER_CMD) {
            } else if (iRetVal == SmartCard.RESP_BUFFER_OVERFLOW) {
            } else if (iRetVal == SmartCard.WRONG_RES_UPON_RESET) {
            } else if (iRetVal == SmartCard.MSG_LEN_EXCEEDS) {
            } else if (iRetVal == SmartCard.BYTE_READING_ERR) {
            } else if (iRetVal == SmartCard.CARD_POWERED_DOWN) {
            } else if (iRetVal == SmartCard.CMD_INCORRECT_PARAM) {
            } else if (iRetVal == SmartCard.INCORRECT_TCK_BYTE) {
            } else if (iRetVal == SmartCard.CARD_RESET_ERROR) {
            } else if (iRetVal == SmartCard.PROTOCOL_ERROR) {
            } else if (iRetVal == SmartCard.PARITY_ERROR) {
            } else if (iRetVal == SmartCard.CARD_ABORTED) {
            } else if (iRetVal == SmartCard.READER_ABORTED) {
            } else if (iRetVal == SmartCard.RESYNCH_SUCCESS) {
            } else if (iRetVal == SmartCard.PROTOCOL_PARAM_ERR) {
            } else if (iRetVal == SmartCard.ALREADY_CARD_POWERED_DOWN) {
            } else if (iRetVal == SmartCard.PCLINK_CMD_NOT_SUPPORTED) {
            } else if (iRetVal == SmartCard.INVALID_PROCEDUREBYTE) {
            } else if (iRetVal == SmartCard.SC_NOT_INSERTED) {
            } else if (iRetVal == SmartCard.SC_NOT_INSERTED) {
            } else if (iRetVal == SmartCard.SC_DEMO_VERSION) {
            } else if (iRetVal == SmartCard.SC_INVALID_DEVICE_ID) {
            } else if (iRetVal == SmartCard.SC_ILLEGAL_LIBRARY) {
            } else if (iRetVal == DEVICE_NOTCONNECTED) {
            } else if (iRetVal == SmartCard.SC_INACTIVE_PERIPHERAL) {
            }
            super.onPostExecute(result);
        }
    }

    private class CardstatussecAsyc extends AsyncTask<Integer, Integer, Integer> {
        /* displays the progress dialog until background task is completed*/
        private ProgressDialog tmp = null;

        @Override
        protected void onPreExecute() {
            this.tmp = new ProgressDialog(SmartCardActivity.this);
            this.tmp.setMessage("Processing....");
            this.tmp.setCancelable(false);
            this.tmp.setCanceledOnTouchOutside(false);
            this.tmp.show();
            super.onPreExecute();
        }

        /* Task of SmartCardAsyc performing in the background*/
        @Override
        protected Integer doInBackground(Integer... params) {
            try {
                iRetVal = MainActivity.insSmartcard.iSelectSCReader(SmartCard.SC_SecondarySCReader);
                Log.e("card status", "iSelectSCReader secondary" + iRetVal);
                if (iRetVal == SmartCard.SC_SUCCESS) {
                    iRetVal = MainActivity.insSmartcard.iSCGetCardStatus();
                    Log.e("card status", "iSCGetCardStatus" + iRetVal);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            logText.setText("iSelectSCReader secondary" + iRetVal + "\n"
                            + "iSCGetCardStatus" + iRetVal);
                        }
                    });
                } else {
                    return iRetVal;
                }

            } catch (Exception e) {
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
            Log.e(TAG, "LEOPARD FPS SMART  onPostExecute Val" + iRetVal);
            if (iRetVal > 0) {

            } else if (iRetVal == DEVICE_NOTCONNECTED) {
            } else if (iRetVal == SmartCard.SC_FAILURE) {
            } else if (iRetVal == SmartCard.NOT_IN_SMARTCARD_MODE) {
            } else if (iRetVal == SmartCard.READ_TIME_OUT) {
            } else if (iRetVal == SmartCard.PARAM_ERROR) {
            } else if (iRetVal == SmartCard.UNKNOWN_DRIVER) {
            } else if (iRetVal == SmartCard.IMPOSSIBLE_OP_DRIVER) {
            } else if (iRetVal == SmartCard.INCORRECT_ARGUMENTS) {
            } else if (iRetVal == SmartCard.UNKNOWN_READER_CMD) {
            } else if (iRetVal == SmartCard.RESP_BUFFER_OVERFLOW) {
            } else if (iRetVal == SmartCard.WRONG_RES_UPON_RESET) {
            } else if (iRetVal == SmartCard.MSG_LEN_EXCEEDS) {
            } else if (iRetVal == SmartCard.BYTE_READING_ERR) {
            } else if (iRetVal == SmartCard.CARD_POWERED_DOWN) {
            } else if (iRetVal == SmartCard.CMD_INCORRECT_PARAM) {
            } else if (iRetVal == SmartCard.INCORRECT_TCK_BYTE) {
            } else if (iRetVal == SmartCard.CARD_RESET_ERROR) {
            } else if (iRetVal == SmartCard.PROTOCOL_ERROR) {
            } else if (iRetVal == SmartCard.PARITY_ERROR) {
            } else if (iRetVal == SmartCard.CARD_ABORTED) {
            } else if (iRetVal == SmartCard.READER_ABORTED) {
            } else if (iRetVal == SmartCard.RESYNCH_SUCCESS) {
            } else if (iRetVal == SmartCard.PROTOCOL_PARAM_ERR) {
            } else if (iRetVal == SmartCard.ALREADY_CARD_POWERED_DOWN) {
            } else if (iRetVal == SmartCard.PCLINK_CMD_NOT_SUPPORTED) {
            } else if (iRetVal == SmartCard.INVALID_PROCEDUREBYTE) {
            } else if (iRetVal == SmartCard.SC_NOT_INSERTED) {
            } else if (iRetVal == SmartCard.SC_NOT_INSERTED) {
            } else if (iRetVal == SmartCard.SC_DEMO_VERSION) {
            } else if (iRetVal == SmartCard.SC_INVALID_DEVICE_ID) {
            } else if (iRetVal == SmartCard.SC_ILLEGAL_LIBRARY) {
            } else if (iRetVal == DEVICE_NOTCONNECTED) {
            } else if (iRetVal == SmartCard.SC_INACTIVE_PERIPHERAL) {
            }
            super.onPostExecute(result);
        }
    }

    public class ApduprAsyc extends AsyncTask<Integer, Integer, Integer> {
        /* displays the progress dialog until background task is completed*/
        private ProgressDialog tmp = null;

        @Override
        protected void onPreExecute() {
            this.tmp = new ProgressDialog(SmartCardActivity.this);
            this.tmp.setMessage("Processing....");
            this.tmp.setCancelable(false);
            this.tmp.setCanceledOnTouchOutside(false);
            this.tmp.show();
            super.onPreExecute();
        }

        /* Task of SmartCardAsyc performing in the background*/
        @Override
        protected Integer doInBackground(Integer... params) {
            byte[] responsedata=new byte[500];
            try {
                iRetVal = MainActivity.insSmartcard.iSelectSCReader(SmartCard.SC_PrimarySCReader);
                Log.e("apdu", "iSelectSCReader primary" + iRetVal);
                    if(iRetVal==SmartCard.SC_SUCCESS) {
                        iRetVal = MainActivity.insSmartcard.iSendReceiveApduCommand("00A40004023F00", responsedata);
                        Log.e("apdu", "iSendReceiveApduCommand primary" + iRetVal);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                logText.setText("iSelectSCReader primary" + iRetVal + "\n"
                               +  "iSendReceiveApduCommand primary" + iRetVal);
                            }
                        });

                    }
            } catch (Exception e) {
                return iRetVal;
            }
            return iRetVal;
        }

        /* This sends message to handler to display the status messages
         * of Diagnose in the dialog box */
        @Override
        protected void onPostExecute(Integer result) {
            Log.e(TAG, "onPostEx"+result );
            this.tmp.dismiss();
            if(iRetVal>0){
                smartcard();
            }
            Log.e("SmartCardActivity", "LEOPARD Smart onPostExecute Val" + iRetVal);

        }
    }
    void smartcard(){
        String str="";
        int iReturnvalue=0;
        int len=0;
        byte[] responsebuf = new byte[500];
        try{
            iReturnvalue = MainActivity.insSmartcard.iSendReceiveApduCommand("00A40004023F00",responsebuf);
            Log.e(TAG, "first apdu command"+iReturnvalue );
            len = iReturnvalue;
            if(len>0){
                str = HexString.bufferToHex(responsebuf,0,len);
                Log.e(TAG,"Apdu Response code "+str );
                String finalStr = str;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        logText.setText("Apdu Response code "+ finalStr);
                    }
                });

            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public class ApdusecAsyc extends AsyncTask<Integer, Integer, Integer> {
        /* displays the progress dialog until background task is completed*/
        private ProgressDialog tmp = null;

        @Override
        protected void onPreExecute() {
            this.tmp = new ProgressDialog(SmartCardActivity.this);
            this.tmp.setMessage("Processing....");
            this.tmp.setCancelable(false);
            this.tmp.setCanceledOnTouchOutside(false);
            this.tmp.show();
            super.onPreExecute();
        }

        /* Task of SmartCardAsyc performing in the background*/
        @Override
        protected Integer doInBackground(Integer... params) {
            try {
                iRetVal = MainActivity.insSmartcard.iSelectSCReader(SmartCard.SC_SecondarySCReader);
                Log.e("apdu", "iSelectSCReader secondary" + iRetVal);
                if (iRetVal == SmartCard.SC_SUCCESS) {
                    iRetVal = MainActivity.insSmartcard.iSendReceiveApduCommand("00A40004023F00", responsebuf);
                    Log.e ("apdu ", "iSendReceiveApduCommand" + iRetVal);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            logText.setText("iSelectSCReader secondary" + iRetVal + "\n"
                            + "iSendReceiveApduCommand" + iRetVal);
                        }
                    });
                } else {
                    return iRetVal;
                }

            } catch (Exception e) {
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
            if(iRetVal>0){
                smartcard();
            }
            Log.e("SmartCardActivity", "LEOPARD Smart onPostExecute Val" + iRetVal);

        }
    }
}

