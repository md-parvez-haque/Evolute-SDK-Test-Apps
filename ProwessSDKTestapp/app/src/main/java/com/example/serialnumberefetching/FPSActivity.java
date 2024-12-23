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
import android.os.Message;
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


public class FPSActivity extends AppCompatActivity {
    // Step 1
    Button btn_FpsConfig, btn_capture, btn_verify, btn_verifymanyminutiae, btn_Verifymatch, btn_compressedWSQ, btn_compressed, btn_Minutiae, btn_Returncode, btn_uncompressed;
    Button btn_BmpfileData, btn_FpsImageBmpdata, btn_Fingerquality, btn_Fingerposition, btn_bgetimagedata, fingerlist, btn_PKMAT, btn_fpsserialno, btn_VerifyManyNorm;
    EditText edtCaptureCount;
    ImageView img_bmp, img_jpeg;
    EditText edtffd;
    Button Morphoffd;

    Context context;
    FpsConfig fpsconfig = new FpsConfig();
    public static Dialog dlgCustomdialog;
    private byte[] bRespBuff = {};

    private boolean bffdlinternalfag = false;
    private byte[] bufvalue = null;

    byte[][] bArrcapList;
    byte[] bMinutiaeArr;
    byte[] bImageData;
    byte[] bUnCmpData;
    byte[] bBmpData;
    byte[] bJpegData;
    byte[] bBMPdata1;
    byte[] bFingerToMatch = null;
    byte[][] bArrCapList = null;
    byte[] fingerdata;

    private TextView logText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fpsactivity);
        logText = findViewById(R.id.logText);
        btn_FpsConfig = findViewById(R.id.btn_SetFpsConfigDlg);
        img_bmp = findViewById(R.id.Bmp_btn);
        img_jpeg = findViewById(R.id.jpeg_btn);
        btn_FpsConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vSetFpsConigDlg(new com.mydevice.sdk.FpsConfig(0, (byte) 0x0F));
            }

        });

        // Step 2
        btn_capture = findViewById(R.id.capture_btn);
        btn_verify = findViewById(R.id.VERIFY_btn);
        btn_verifymanyminutiae = findViewById(R.id.Verifmanyminutiae_btn);
        btn_compressedWSQ = findViewById(R.id.compressedWSQ_btn);
        btn_compressed = findViewById(R.id.compressed_btn);
        btn_Minutiae = findViewById(R.id.Minutiae_btn);
        btn_Returncode = findViewById(R.id.Returncode_btn);
        btn_uncompressed = findViewById(R.id.uncompressed_btn);
        btn_BmpfileData = findViewById(R.id.BmpfileData_btn);
        btn_FpsImageBmpdata = findViewById(R.id.FpsImageBmpdata_btn);
        btn_Fingerquality = findViewById(R.id.Fingerquality_btn);
        btn_Fingerposition = findViewById(R.id.Fingerposition_btn);
        btn_Verifymatch = findViewById(R.id.Verifymatch_btn);
        edtCaptureCount = (EditText) findViewById(R.id.edtCaptureCount);
        btn_bgetimagedata = findViewById(R.id.bgetimagedata_btn);
        fingerlist = findViewById(R.id.CaptureList);
        edtffd = (EditText) findViewById(R.id.edtffd);
        Morphoffd = findViewById(R.id.ffdlevels);
        btn_PKMAT = findViewById(R.id.PKMAT_btn);
        btn_fpsserialno = findViewById(R.id.fpsserialno_btn);
        btn_VerifyManyNorm = findViewById(R.id.VerifyManyNorm_btn);

        // Step 3
        btn_capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //step 5
                CaptureFingerAsyn captureFingerAsyn = new CaptureFingerAsyn();
                captureFingerAsyn.execute(0);
            }
        });

        btn_Minutiae.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MinutiaeAsyn minutiaeAsyn = new MinutiaeAsyn();
                minutiaeAsyn.execute(0);
            }

        });
        btn_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VerifyFingerAsync verifyFingerAsync = new VerifyFingerAsync();
                verifyFingerAsync.execute(0);
            }
        });

        btn_verifymanyminutiae.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VerifymanyfingerAsync verifymanyfingerAsync = new VerifymanyfingerAsync();
                verifymanyfingerAsync.execute(0);
            }
        });
        btn_VerifyManyNorm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VerifymanynormAsync verifymanynormAsync = new VerifymanynormAsync();
                verifymanynormAsync.execute(0);
            }
        });

        btn_compressedWSQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CompressedWSQAsync compressedWSQAsync = new CompressedWSQAsync();
                compressedWSQAsync.execute(0);
            }
        });

        btn_Returncode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReturnAsyn returnAsyn = new ReturnAsyn();
                returnAsyn.execute(0);
            }
        });
        btn_compressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CompressedAsync compressedAsync = new CompressedAsync();
                compressedAsync.execute(0);
            }
        });
        btn_uncompressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UncompressedAsync uncompressedAsync = new UncompressedAsync();
                uncompressedAsync.execute(0);
            }
        });
        btn_bgetimagedata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BgetimagedataAsyn bgetimagedataAsyn = new BgetimagedataAsyn();
                bgetimagedataAsyn.execute(0);
            }
        });
        fingerlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fingerlist List = new fingerlist();
                List.execute(0);
            }
        });
        btn_Verifymatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VerifymatchAsync verifymatchAsync = new VerifymatchAsync();
                verifymatchAsync.execute(0);
            }
        });
        btn_bgetimagedata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BgetimagedataAsyn bgetimagedataAsyn = new BgetimagedataAsyn();
                bgetimagedataAsyn.execute(0);
            }
        });

        btn_Fingerquality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FingerQualityAsyn fingerqualityAsyn = new FingerQualityAsyn();
                fingerqualityAsyn.execute(0);

            }
        });
        btn_Fingerposition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FingerPositionAsync fingerpositionAsyn = new FingerPositionAsync();
                fingerpositionAsyn.execute(0);

            }
        });
        btn_BmpfileData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmpfiledataAsync bmpfiledataAsync = new BmpfiledataAsync();
                bmpfiledataAsync.execute(0);

            }
        });
        btn_PKMAT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VerifyPKMATAsync verifyPKMATAsync = new VerifyPKMATAsync();
                verifyPKMATAsync.execute(0);

            }
        });
        btn_FpsImageBmpdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FpsimagebmpdataAsync fpsimagebmpdataAsync = new FpsimagebmpdataAsync();
                fpsimagebmpdataAsync.execute(0);

            }
        });
        btn_fpsserialno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FpsserialnoAsync fpsserialnoAsync = new FpsserialnoAsync();
                fpsserialnoAsync.execute(0);

            }
        });
        Morphoffd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int level = 0;

                try {
                    String ffd_level = String.valueOf(edtffd.getText());
                    level = Integer.parseInt(ffd_level);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    int ret = MainActivity.intFps.iSetFFDSecurityLevel(level);
                    Log.e("ffd", "ret value" + ret);
//                    Toast.makeText(context, "FFD Response : " + ret, Toast.LENGTH_LONG).show();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            logText.setText("iSetFFDSecurityLevel" + ret);
                        }
                    });
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    private void store_TrustKey_ToExternal1(byte[] filedata) throws IOException {
        String filename = "Prowess.bmp";
        String fileContents = "Hello, world!";
        File file = new File(Environment.getExternalStorageDirectory() + "/sdcard/Download", filename);
        FileOutputStream outputStream = new FileOutputStream(file);
        outputStream.write(filedata);
        outputStream.close();
    }

    String sFileName;

    @SuppressLint("SuspiciousIndentation")
    private void vSavetoFile(byte[] bdata, String fileName) {
        try {
            File root = Environment.getExternalStorageDirectory();
            File dir = new File(root.getAbsolutePath() + "/", fileName);
            dir.createNewFile();
            FileOutputStream fos = new FileOutputStream(dir);
            fos.write(bdata);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void store_TrustKey_ToExternal(byte[] bdata, String _trust_key) {
        File sdcard = Environment.getExternalStorageDirectory();

        File dir = new File(sdcard.getAbsolutePath() + "/", _trust_key);
        File dir1 = new File(dir, _trust_key);
        dir.mkdir();
        dir1.mkdir();
        File file = new File(dir1, _trust_key);
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(file);
            os.write(bdata);
            os.close();
            /*encrypt trust key*/
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    com.mydevice.sdk.FpsConfig.TemplateType tempType = null;
    com.mydevice.sdk.FpsConfig glbFpsConfig = new com.mydevice.sdk.FpsConfig(com.mydevice.sdk.FpsConfig.TemplateType.ISO_FMR, 0, (byte) 0x0f, 20);


    enum currentInput {timeout, thresold, nooffing, unassigned}

    ;
    static currentInput glbCurrentInput = currentInput.unassigned;

    public void vSetFpsConigDlg(com.mydevice.sdk.FpsConfig fpsConfig) {
        String TAG = "FpsConfig";
        Log.e("FpsConfig", "AddTemplate");

        String[] sTemplateTypes = {
                "ISO_FMR", "PK_COMP_NORM", "PK_MAT", "PK_MAT_NORM", "ANSI_378",
                "MINEX_A", "PK_COMP_V2", "ISO_FMC_NS", "ISO_FMC_CS", "NONE"
        };
//        tt arrtt[] = tt.values();


//        final Dialog addTemplate_dailog = new Dialog(context);
        final Dialog addTemplate_dailog = new Dialog(FPSActivity.this, R.style.Base_Theme_AppCompat_Light_Dialog);
        addTemplate_dailog.setTitle("FpsConfig Setup");
        addTemplate_dailog.setContentView(R.layout.dialog_template);
        addTemplate_dailog.setCanceledOnTouchOutside(false);

        final Button btnCancel = (Button) addTemplate_dailog.findViewById(R.id.btn_CancelFpsConfig);
        btnCancel.setText("Default");
        final Button btnSetFpsConfig = (Button) addTemplate_dailog.findViewById(R.id.btn_SetFpsConfig);
        final EditText edtTimeOut = (EditText) addTemplate_dailog.findViewById(R.id.edtDlgTimeOut);
        final EditText edtThreshold = (EditText) addTemplate_dailog.findViewById(R.id.edtDlgThreshold);
        final EditText edtNoOfFingers = (EditText) addTemplate_dailog.findViewById(R.id.edtDlgNoOfFing);
        final TextView tvFpsConfigUpdate = (TextView) addTemplate_dailog.findViewById(R.id.tvDlgFpsConfigUpdate);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("FpsConfig", "btnCancel");
                glbFpsConfig = new com.mydevice.sdk.FpsConfig(com.mydevice.sdk.FpsConfig.TemplateType.ISO_FMR, 0, (byte) 0x0f, 20);
                Log.e(TAG, "Setting default FpsConfig(FpsConfig.TemplateType.ISO_FMR,0,(byte)0x0f,20) = " + glbFpsConfig.toString());
                String sMsg = "FpsConfig Set as :\n" + glbFpsConfig.toString().replace("FpsConfig", "")
                        .replace(",", "\n").replace("{", "").replace("}", "").trim();
                tvFpsConfigUpdate.setText(sMsg);
                Log.e(TAG, sMsg);
                toastOnUI("Confirm to Default");
//                addTemplate_dailog.dismiss();
            }
        });

        btnSetFpsConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "btnSetFpsConfig");
                if (btnSetFpsConfig.getText().equals("Confirm")) {
                    Log.e(TAG, "Confirmed Final FpsConfig : " + glbFpsConfig.toString().trim());
                    addTemplate_dailog.dismiss();
                } else {
                    Log.e(TAG, "Update UI for reference");
                    String sMsg = "FpsConfig Set as :\n" + glbFpsConfig.toString().replace("FpsConfig", "")
                            .replace(",", "\n").replace("{", "").replace("}", "")
                            .replace("<", "").replace(">", "").trim();
                    tvFpsConfigUpdate.setText(sMsg);
                    Log.e(TAG, sMsg);
                    btnSetFpsConfig.setText("Confirm");
                    toastOnUI("Confirm to Finalize");
                }
            }
        });
        class GenericTextWatcher implements TextWatcher {
            int _gView;
            TextView _tv;

            public GenericTextWatcher(int v, TextView v2) {
                this._gView = v;
                this._tv = v2;
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.e(TAG, "CharSequence s " + s + ", start " + start + ", count " + count + ", after " + after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e(TAG, "CharSequence s " + s + ", start " + start + ", before " + before + ", count " + count);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.e(TAG, "editable = " + editable.toString());
                btnSetFpsConfig.setText("Set FpsConfig");
                String stime = editable.toString();
                int iInput = -12;
                try {
                    iInput = Integer.parseInt(stime);
                } catch (NumberFormatException e) {
                    Log.e(TAG, "NumberFormatException e " + e.getMessage());
                    e.printStackTrace();
                    iInput = -11;
                }
                if (_gView == R.id.edtDlgTimeOut) {
                    glbFpsConfig.setTimeOut((byte) (0x000000FF & iInput));
                    _tv.setText("setTimeOut = " + glbFpsConfig.getTimeOut());
                    Log.e(TAG, "Input setTimeOut = " + glbFpsConfig.getTimeOut());
                } else if (_gView == R.id.edtDlgThreshold) {
                    glbFpsConfig.setThreshold(iInput);
                    _tv.setText("setThreshold = " + glbFpsConfig.getThreshold());
                    Log.e(TAG, "Input setThreshold = " + glbFpsConfig.getThreshold());
                } else if (_gView == R.id.edtDlgNoOfFing) {
                    glbFpsConfig.setNoOfFinger(iInput);
                    _tv.setText("setNoOfFinger = " + glbFpsConfig.getNoOfFinger());
                    Log.e(TAG, "Input setNoOfFinger = " + glbFpsConfig.getNoOfFinger());
                } else {
                    Log.e(TAG, "Invalid Edit Box Input");
                }
            }
        }
        ;
        edtTimeOut.addTextChangedListener(new GenericTextWatcher(R.id.edtDlgTimeOut, tvFpsConfigUpdate));
        edtThreshold.addTextChangedListener(new GenericTextWatcher(R.id.edtDlgThreshold, tvFpsConfigUpdate));
        edtNoOfFingers.addTextChangedListener(new GenericTextWatcher(R.id.edtDlgNoOfFing, tvFpsConfigUpdate));

        Spinner sp_TemplateSelections = (Spinner) addTemplate_dailog.findViewById(R.id.spinner_template);
//        ArrayAdapter<String> arrTempTypes = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, sTemplateTypes);
        ArrayAdapter<String> arrTempTypes = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, sTemplateTypes);
        arrTempTypes.setDropDownViewResource(android.R.layout.simple_spinner_item);
        sp_TemplateSelections.setAdapter(arrTempTypes);
        sp_TemplateSelections.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Log.e(TAG, "onItemSelected position = " + position);
                if (position == 0) {
                    tempType = com.mydevice.sdk.FpsConfig.TemplateType.ISO_FMR;
                } else if (position == 1) {
                    tempType = com.mydevice.sdk.FpsConfig.TemplateType.PK_COMP_NORM;
                } else if (position == 2) {
                    tempType = com.mydevice.sdk.FpsConfig.TemplateType.PK_MAT;
                } else if (position == 3) {
                    tempType = com.mydevice.sdk.FpsConfig.TemplateType.PK_MAT_NORM;
                } else if (position == 4) {
                    tempType = com.mydevice.sdk.FpsConfig.TemplateType.ANSI_378;
                } else if (position == 5) {
                    tempType = com.mydevice.sdk.FpsConfig.TemplateType.MINEX_A;
                } else if (position == 6) {
                    tempType = com.mydevice.sdk.FpsConfig.TemplateType.PK_COMP_V2;
                } else if (position == 7) {
                    tempType = com.mydevice.sdk.FpsConfig.TemplateType.ISO_FMC_NS;
                } else if (position == 8) {
                    tempType = com.mydevice.sdk.FpsConfig.TemplateType.ISO_FMC_CS;
                } else {
                    Log.e(TAG, "Defaulting to TemplateType.ISO_FMR");
                    tempType = com.mydevice.sdk.FpsConfig.TemplateType.ISO_FMR;
                }
                Log.e(TAG, "onItemSelected position = " + position + " assigned " + tempType);
                glbFpsConfig.setTemplate(tempType);
                tvFpsConfigUpdate.setText("setTemplate = " + glbFpsConfig.getTemplate().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                Log.e(TAG, "onNothingSelected");
            }
        });
        addTemplate_dailog.show();
        return;
    }

    private void toastOnUI(String msg) {
        Toast.makeText(FPSActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

// Step 4

    public class CaptureFingerAsyn extends AsyncTask<Integer, Integer, Integer> {
        int iRetVal;

        private ProgressDialog tmp = null;

        // displays the progress dialog until background task is completed
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            img_bmp.setVisibility(View.INVISIBLE);
            img_jpeg.setVisibility(View.INVISIBLE);
            this.tmp = new ProgressDialog(FPSActivity.this);
            this.tmp.setMessage("Processing....");
            this.tmp.setCancelable(false);
            this.tmp.setCanceledOnTouchOutside(false);
            this.tmp.show();
            super.onPreExecute();
        }

        // Task of CaptureFinger performing in the background
        @Override
        protected Integer doInBackground(Integer... params) {
            try {
                bMinutiaeArr = new byte[3500];
                FpsConfig fpsconfig = new FpsConfig(0, (byte) 0x0F);
                bMinutiaeArr = MainActivity.intFps.bFpsCaptureMinutiae(glbFpsConfig);
                iRetVal = MainActivity.intFps.iGetReturnCode();
                if (iRetVal == FPS.SUCCESS) {
                    if (bMinutiaeArr != null) {
                        Log.e("Capture Task", "Finger Minutiae data is : " + HexString.bufferToHex(bMinutiaeArr));
                        Log.e("capture task ", "iRetValue,:" + MainActivity.intFps.iGetReturnCode());
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            logText.setText("Finger Minutiae data is : " + HexString.bufferToHex(bMinutiaeArr) + "\n"
                                    + "i getreturn code is :" + MainActivity.intFps.iGetReturnCode());
                        }
                    });


                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            return iRetVal;
        }

        //* This function sends message to handler to display the status messages of Diagnose in the dialog box
        @Override
        protected void onPostExecute(Integer result) {
            this.tmp.dismiss();

            Log.e("Capture Task ", "LEOPARD FPS Capture onPostExecute Val" + iRetVal);

            if (iRetVal == FPS.SUCCESS) {
                Toast.makeText(FPSActivity.this, "Capture Success", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == FPS.FPS_INACTIVE_PERIPHERAL) {
                Toast.makeText(FPSActivity.this, "Inactive Peripheral", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == FPS.TIME_OUT) {
                Toast.makeText(FPSActivity.this, "Capture Time Out", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == FPS.FAILURE) {
                Toast.makeText(FPSActivity.this, "Capture Failure", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == FPS.PARAMETER_ERROR) {
                Toast.makeText(FPSActivity.this, "Parameter Error", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == FPS.FPS_INVALID_DEVICE_ID) {
                Toast.makeText(FPSActivity.this, "Invalid Device ID", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == FPS.FPS_ILLEGAL_LIBRARY) {
                Toast.makeText(FPSActivity.this, "Illegal Library", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == FPS.NO_RESPONSE) {
                Toast.makeText(FPSActivity.this, "No Response", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == FPS.FPS_FFD) {
                Toast.makeText(FPSActivity.this, "Fake Finger Detection", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == FPS.INVALID_OPERATION || iRetVal == FPS.FPS_ERROR) {
                Toast.makeText(FPSActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }

    @SuppressLint("HandlerLeak")
    Handler fpsHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    try {
                        Toast.makeText(FPSActivity.this, "NO FINGER", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                    }
                    break;
                case 2:
                case 3:
//                    tv_FinPosUpdate.setText(""+msg.obj);

                default:
                    break;
            }
        }

        ;
    };

    public class FingerQualityAsyn extends AsyncTask<Integer, Integer, Integer> {
        int iRetVal;

        private ProgressDialog tmp = null;

        // displays the progress dialog until background task is completed
        @Override
        protected void onPreExecute() {
        }

        // Task of CaptureFinger performing in the background
        @Override
        protected Integer doInBackground(Integer... params) {
            try {

                iRetVal = MainActivity.intFps.iGetCurrentFingerQuality();
                Log.e("Capture Task", "iGetCurrentFingerQuality is : " + iRetVal);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        logText.setText("iGetCurrentFingerQuality is : " + iRetVal);
                    }
                });
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            return iRetVal;
        }
    }

    public class VerifyFingerAsync extends AsyncTask<Integer, Integer, Integer> {
        int iRetVal;
        byte[] bmpData = null;
        byte[] data1 = null;
        private ProgressDialog tmp = null;

        // displays the progress dialog until background task is completed
        @Override
        protected void onPreExecute() {
            img_bmp.setVisibility(View.INVISIBLE);
            img_jpeg.setVisibility(View.INVISIBLE);
            this.tmp = new ProgressDialog(FPSActivity.this);
            this.tmp.setMessage("Processing....");
            this.tmp.setCancelable(false);
            this.tmp.setCanceledOnTouchOutside(false);
            this.tmp.show();
            super.onPreExecute();
        }

        // Task of CaptureFinger performing in the background
        @Override
        protected Integer doInBackground(Integer... params) {
            try {
//                byte[] Morpho = HexString.hexToBuffer("81D8007000410300B7F8447200F490822A0122F8813B0145F841BF015FF043DA018C884294018CF0413B01B0E843DA01C994806601EDEC444E01EDA882C70206EC41B50210E442F5024DDC43550257604355029F5084AF02C2B0842B03092C43230323488392032D3841B50360D48066041C58819B041CE8824D041C3080AD0486DC43B604C39C822004E704435505002042AE05248C4117058EE8422A05B2048280061D0842C7066480813B06A1F0844406AB9441A6075DF042C707760041BF078068");
////              byte[][] multi= new byte[5][];//1:N usage for verify many minutiae use this and change fps instance to ifpsverifymanyminutiae;

                MainActivity.intFps.setVerificationImageReturn(true);
                iRetVal = MainActivity.intFps.iFpsVerifyMinutiae(bMinutiaeArr, glbFpsConfig);
                Log.e("Verify minutiae return value", "ifpsverifyminutiae,:" + iRetVal);
                iRetVal = MainActivity.intFps.iGetReturnCode();
                Log.e("bGetImageData RETURN VALUE", "Igetreturncodeis,:" + iRetVal);
                if (iRetVal == FPS.SUCCESS) {
                    byte[] data = MainActivity.intFps.bGetImageData();
                    Log.e("bGetImageData RETURN VALUE", "iRetValue,:" + MainActivity.intFps.iGetReturnCode());
                    Log.e("bGetImageData : ", "bGetimagedatais " + com.mydevice.sdk.HexString.bufferToHex(data));
                    data1 = MainActivity.intFps.bGetJpgImageData();
                    Log.e("bGetJpegImageData RETURN VALUE", "iRetValue,:" + MainActivity.intFps.iGetReturnCode());
                    Log.e("bGetJpegData : ", "bGetJpegDatais  " + com.mydevice.sdk.HexString.bufferToHex(data1));
                    bmpData = com.mydevice.sdk.FpsImageAPI.bConvertRaw2bmp(data);
                    Log.e("bConvertRaw2bmp RETURN VALUE", "iRetValue,:" + MainActivity.intFps.iGetReturnCode());
                    Log.e("bconvertRaw2BmpData : ", "bconvertRaw2BmpDatais" + com.mydevice.sdk.HexString.bufferToHex(bmpData));
                    Log.e("Capture Task", "Verified Minutiae data is ,:" + iRetVal);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            logText.setText("bGetImageData RETURN VALUE,:" + MainActivity.intFps.iGetReturnCode() + "\n"
//                                  + "bGetimagedatais " + com.mydevice.sdk.HexString.bufferToHex(data) + "\n"
                                    + "bGetJpegImageData RETURN VALUE,:" + MainActivity.intFps.iGetReturnCode() + "\n"
//                                  + "bGetJpegData is  " + com.mydevice.sdk.HexString.bufferToHex(data1) + "\n"
                                    + "bConvertRaw2bmp RETURN VALUE,:" + MainActivity.intFps.iGetReturnCode() + "\n"
                                    + "Verified Minutiae data is ,:" + iRetVal);
//                                    + "bconvertRaw2BmpData is" + com.mydevice.sdk.HexString.bufferToHex(bmpData) );
                        }
                    });


                } else {
                    byte[] data = MainActivity.intFps.bGetImageData();
                    Log.e("bGetImageData RETURN VALUE", "iRetValue,:" + MainActivity.intFps.iGetReturnCode());
                    Log.e("bGetImageData : ", "bGetimagedatais  " + (data));
                    data1 = MainActivity.intFps.bGetJpgImageData();
                    Log.e("bGetJpegImageData RETURN VALUE", "iRetValue,:" + MainActivity.intFps.iGetReturnCode());
                    Log.e("bGetJpegData : ", "bGetJpegData" + (data1));
                    bmpData = com.mydevice.sdk.FpsImageAPI.bConvertRaw2bmp(data);
                    Log.e("bConvertRaw2bmp RETURN VALUE", "iRetValue,:" + MainActivity.intFps.iGetReturnCode());
                    Log.e("bconvertRaw2Bmp: ", "bconvertRaw2Bmpdatais" + (bmpData));
                    Log.e("Capture Task", "Verified Minutiae data is ,:" + iRetVal);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            logText.setText("bGetImageData RETURN VALUE,:" + MainActivity.intFps.iGetReturnCode() + "\n"
                                    + "bGetimagedatais " + com.mydevice.sdk.HexString.bufferToHex(data) + "\n"
                                    + "bGetJpegImageData RETURN VALUE,:" + MainActivity.intFps.iGetReturnCode() + "\n"
                                    + "bGetJpegData is  " + com.mydevice.sdk.HexString.bufferToHex(data1) + "\n"
                                    + "bConvertRaw2bmp RETURN VALUE,:" + MainActivity.intFps.iGetReturnCode() + "\n"
                                    + "bconvertRaw2BmpData is" + com.mydevice.sdk.HexString.bufferToHex(bmpData) + "\n"
                                    + "Verified Minutiae data is ,:" + iRetVal);
                        }
                    });

                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            return iRetVal;
        }

        //* This function sends message to handler to display the status messages of Diagnose in the dialog box
        @Override
        protected void onPostExecute(Integer result) {
            this.tmp.dismiss();
            Log.e("Capture Task ", "LEOPARD FPS Capture onPostExecute Val" + iRetVal);
            if (iRetVal == -5) {
                img_bmp.setVisibility(View.VISIBLE);
                img_jpeg.setVisibility(View.VISIBLE);
                try {
                    Bitmap bmpfinalnew = BitmapFactory.decodeByteArray(bmpData, 0, bmpData.length);
                    img_bmp.setImageBitmap(bmpfinalnew);
                    Bitmap bmpfinalnew1 = BitmapFactory.decodeByteArray(data1, 0, data1.length);
                    img_jpeg.setImageBitmap(bmpfinalnew1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (iRetVal == FPS.SUCCESS) {
                    Toast.makeText(FPSActivity.this, "Capture Success", Toast.LENGTH_SHORT).show();
                } else if (iRetVal == FPS.FPS_INACTIVE_PERIPHERAL) {
                    Toast.makeText(FPSActivity.this, "Inactive Peripheral", Toast.LENGTH_SHORT).show();
                } else if (iRetVal == FPS.TIME_OUT) {
                    Toast.makeText(FPSActivity.this, "Capture Time Out", Toast.LENGTH_SHORT).show();
                } else if (iRetVal == FPS.FAILURE) {
                    Toast.makeText(FPSActivity.this, "Capture Failure", Toast.LENGTH_SHORT).show();
                } else if (iRetVal == FPS.PARAMETER_ERROR) {
                    Toast.makeText(FPSActivity.this, "Parameter Error", Toast.LENGTH_SHORT).show();
                } else if (iRetVal == FPS.FPS_INVALID_DEVICE_ID) {
                    Toast.makeText(FPSActivity.this, "Invalid Device ID", Toast.LENGTH_SHORT).show();
                } else if (iRetVal == FPS.FPS_ILLEGAL_LIBRARY) {
                    Toast.makeText(FPSActivity.this, "Illegal Library", Toast.LENGTH_SHORT).show();
                } else if (iRetVal == FPS.NO_RESPONSE) {
                    Toast.makeText(FPSActivity.this, "No Response", Toast.LENGTH_SHORT).show();
                } else if (iRetVal == FPS.FPS_FFD) {
                    Toast.makeText(FPSActivity.this, "Fake Finger Detection", Toast.LENGTH_SHORT).show();
                } else if (iRetVal == FPS.INVALID_OPERATION || iRetVal == FPS.FPS_ERROR) {
                    Toast.makeText(FPSActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
                super.onPostExecute(result);
            }
        }


    }


    public class VerifymanyfingerAsync extends AsyncTask<Integer, Integer, Integer> {
        int iRetVal;
        byte[] bmpData;

        byte[] data1;
        private ProgressDialog tmp = null;

        // displays the progress dialog until background task is completed
        @Override
        protected void onPreExecute() {
            img_bmp.setVisibility(View.INVISIBLE);
            img_jpeg.setVisibility(View.INVISIBLE);
            this.tmp = new ProgressDialog(FPSActivity.this);
            this.tmp.setMessage("Processing....");
            this.tmp.setCancelable(false);
            this.tmp.setCanceledOnTouchOutside(false);
            this.tmp.show();
            super.onPreExecute();
        }

        // Task of CaptureFinger performing in the background
        @Override
        protected Integer doInBackground(Integer... params) {
            try {
                MainActivity.intFps.setVerificationImageReturn(true);
//                byte[] bufvalue = new byte[3500];
//                FpsConfig fpsconfig = new FpsConfig(0, (byte) 0x0F);
                iRetVal = MainActivity.intFps.iFpsVerifyManyMinutiae(bArrcapList, glbFpsConfig);
                Log.e("Verify minutiae return value", "ifpsverifymanyminutiae,:" + iRetVal);
                MainActivity.intFps.iGetReturnCode();
                Log.e("bGetImageData RETURN VALUE", "Igetreturncode,:" + iRetVal);
//                if (iRetVal > 0) {
                if(MainActivity.intFps.isVerificationImageReturned()){
                    Log.e("isVerificationImageReturned", "doInBackground: success" );
                }else{
                    Log.e("isVerificationImageReturned", "doInBackground: failure" );

                }
                    byte[] data = MainActivity.intFps.bGetImageData();
                    Log.e("bGetImageData RETURN VALUE", "iRetValue,:" + MainActivity.intFps.iGetReturnCode());
                    Log.e("bGetImageData : ", "bGetimagedatais " + com.mydevice.sdk.HexString.bufferToHex(data));
                    data1 = MainActivity.intFps.bGetJpgImageData();
                    Log.e("bGetJpegImageData RETURN VALUE", "iRetValue,:" + MainActivity.intFps.iGetReturnCode());
                    Log.e("bGetJpegData : ", "bGetJpegDatais  " + com.mydevice.sdk.HexString.bufferToHex(data1));
                    bmpData = com.mydevice.sdk.FpsImageAPI.bConvertRaw2bmp(data);
                    Log.e("bConvertRaw2bmp RETURN VALUE", "iRetValue,:" + MainActivity.intFps.iGetReturnCode());
                    Log.e("bconvertRaw2BmpData : ", "bconvertRaw2BmpDatais" + com.mydevice.sdk.HexString.bufferToHex(bmpData));
                    Log.e("Verify minutiae return value", "Verified index finger is  ,:" + iRetVal);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            logText.setText("bGetImageData RETURN VALUE,:" + MainActivity.intFps.iGetReturnCode() + "\n"
                                    + "bGetJpegImageData RETURN VALUE,:" + MainActivity.intFps.iGetReturnCode() + "\n"
                                    + "bConvertRaw2bmp RETURN VALUE,:" + MainActivity.intFps.iGetReturnCode() + "\n"
                                    + "Verified index finger is  ,:" + iRetVal);
                        }
                    });


//                } else {
//                    byte[] data = MainActivity.intFps.bGetImageData();
//                    Log.e("bGetImageData RETURN VALUE", "iRetValue,:" + MainActivity.intFps.iGetReturnCode());
//                    Log.e("bGetImageData : ", "bGetimagedatais  " + (data));
//                    data1 = MainActivity.intFps.bGetJpgImageData();
//                    Log.e("bGetJpegImageData RETURN VALUE", "iRetValue,:" + MainActivity.intFps.iGetReturnCode());
//                    Log.e("bGetJpegData : ", "bGetJpegData" + (data1));
//                    bmpData = com.mydevice.sdk.FpsImageAPI.bConvertRaw2bmp(data);
//                    Log.e("bConvertRaw2bmp RETURN VALUE", "iRetValue,:" + MainActivity.intFps.iGetReturnCode());
//                    Log.e("bconvertRaw2Bmp: ", "bconvertRaw2Bmpdatais" + (bmpData));
//                    Log.e("Verify minutiae return value", "Verified index finger is  ,:" + iRetVal);
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            logText.setText("bGetImageData RETURN VALUE,:" + MainActivity.intFps.iGetReturnCode() + "\n"
//                                    + "bGetJpegImageData RETURN VALUE,:" + MainActivity.intFps.iGetReturnCode() + "\n"
//                                    + "bConvertRaw2bmp RETURN VALUE,:" + MainActivity.intFps.iGetReturnCode() + "\n"
//                                    + "Verified index finger is  ,:" + iRetVal);
//                        }
//                    });
//
//                }

            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            return iRetVal;
        }


        //* This function sends message to handler to display the status messages of Diagnose in the dialog box
        @Override
        protected void onPostExecute(Integer result) {
            this.tmp.dismiss();
            Log.e("Capture Task ", "LEOPARD FPS Capture onPostExecute Val" + iRetVal);
            if (iRetVal > 0) {
                img_bmp.setVisibility(View.VISIBLE);
                img_jpeg.setVisibility(View.VISIBLE);
                try {
                    Bitmap bmpfinalnew = BitmapFactory.decodeByteArray(bmpData, 0, bmpData.length);
                    img_bmp.setImageBitmap(bmpfinalnew);
                    Bitmap bmpfinalnew1 = BitmapFactory.decodeByteArray(data1, 0, data1.length);
                    img_jpeg.setImageBitmap(bmpfinalnew1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (iRetVal == FPS.SUCCESS) {
                    Toast.makeText(FPSActivity.this, "Capture Success", Toast.LENGTH_SHORT).show();
                } else if (iRetVal == FPS.FPS_INACTIVE_PERIPHERAL) {
                    Toast.makeText(FPSActivity.this, "Inactive Peripheral", Toast.LENGTH_SHORT).show();
                } else if (iRetVal == FPS.TIME_OUT) {
                    Toast.makeText(FPSActivity.this, "Capture Time Out", Toast.LENGTH_SHORT).show();
                } else if (iRetVal == FPS.FAILURE) {
                    Toast.makeText(FPSActivity.this, "Capture Failure", Toast.LENGTH_SHORT).show();
                } else if (iRetVal == FPS.PARAMETER_ERROR) {
                    Toast.makeText(FPSActivity.this, "Parameter Error", Toast.LENGTH_SHORT).show();
                } else if (iRetVal == FPS.FPS_INVALID_DEVICE_ID) {
                    Toast.makeText(FPSActivity.this, "Invalid Device ID", Toast.LENGTH_SHORT).show();
                } else if (iRetVal == FPS.FPS_ILLEGAL_LIBRARY) {
                    Toast.makeText(FPSActivity.this, "Illegal Library", Toast.LENGTH_SHORT).show();
                } else if (iRetVal == FPS.NO_RESPONSE) {
                    Toast.makeText(FPSActivity.this, "No Response", Toast.LENGTH_SHORT).show();
                } else if (iRetVal == FPS.FPS_FFD) {
                    Toast.makeText(FPSActivity.this, "Fake Finger Detection", Toast.LENGTH_SHORT).show();
                } else if (iRetVal == FPS.INVALID_OPERATION || iRetVal == FPS.FPS_ERROR) {
                    Toast.makeText(FPSActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
                super.onPostExecute(result);
            }
        }


    }


    public class fingerlist extends AsyncTask<Integer, Integer, Integer> {

        int iRetVal;

        @Override
        protected Integer doInBackground(Integer... integers) {
            int Size = 0;
            try {


                String capturecount = edtCaptureCount.getText().toString();

                Size = Integer.parseInt(capturecount);
                Log.e("testlog", "Size :  " + Size);
                int finalSize = Size;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        logText.setText("Size ,:" + finalSize);
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (Size >= 1 && Size <= 20) {
                    bArrcapList = new byte[Size][];
                    for (int i = 0; i < Size; i++) {
                        bArrcapList[i] = MainActivity.intFps.bFpsCaptureMinutiae(glbFpsConfig);
                        //bMinutiaeArr = MainActivity.intFps.bGetMinutiaeData();
                        //Log.e("minutiae", "captured finger data is,:" + HexString.bufferToHex(bMinutiaeArr));
                        Log.e("bGetMinutiaedata : ", "Capyutred finger data stotred in " + bArrcapList[i]);
                        Log.e("bGetMinutiaedata: ", "capturelist " + i);
                        iRetVal = MainActivity.intFps.iGetReturnCode();
                        Log.e("testlog", "Capture list return code is  " + iRetVal);
                        SystemClock.sleep(1000);
                        int finalI = i;
                        int finalI1 = i;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                logText.setText("Capyutred finger data stotred in " + bArrcapList[finalI] + "\n"
                                        + "capturelist " + finalI1 + "\n"
                                        + "Capture list return code is  " + iRetVal);
                            }
                        });

                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return iRetVal;
        }
    }

    public class CompressedWSQAsync extends AsyncTask<Integer, Integer, Integer> {
        int iRetVal;
        byte[] bmpData = null;


        // displays the progress dialog until background task is completed
        private ProgressDialog tmp = null;

        @Override
        protected void onPreExecute() {
            img_bmp.setVisibility(View.INVISIBLE);
            this.tmp = new ProgressDialog(FPSActivity.this);
            this.tmp.setMessage("Processing....");
            this.tmp.setCancelable(false);
            this.tmp.setCanceledOnTouchOutside(false);
            this.tmp.show();
            super.onPreExecute();
            // Task of CaptureFinger performing in the background
        }


        @Override
        protected Integer doInBackground(Integer... params) {
            try {
                byte[] bufvalue = new byte[3500];
                FpsConfig fpsconfig = new FpsConfig(0, (byte) 0x0F);
                iRetVal = MainActivity.intFps.iGetFingerImageCompressedWSQ(bufvalue, glbFpsConfig, 0);
                Log.e("compressed WSQ", "compresssed WSQ data return value is,:" + MainActivity.intFps.iGetReturnCode());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        logText.setText("compresssed WSQ data return value is,:" + MainActivity.intFps.iGetReturnCode());
                    }
                });
//                iRetVal = MainActivity.intFps.iGetReturnCode();
//                Log.e("CompressedWSQ", "Igetreturncode,:" + iRetVal);
                if (iRetVal > 0) {
                    if (bufvalue != null) {
                        bMinutiaeArr = MainActivity.intFps.bGetMinutiaeData();
                        Log.e("compressed WSQ", "compresssed WSQ data return value is,:" + com.mydevice.sdk.HexString.bufferToHex(bMinutiaeArr));
                        byte[] data = MainActivity.intFps.bGetImageData();
                        Log.e("bGetImageData RETURN VALUE", "iRetValue,:" + MainActivity.intFps.iGetReturnCode());
                        Log.e("bGetImageData : ", "bgetimagedatais " + com.mydevice.sdk.HexString.bufferToHex(data));
                        byte[] data1 = MainActivity.intFps.bGetJpgImageData();
                        Log.e("bGetJpegImageData RETURN VALUE", "iRetValue,:" + MainActivity.intFps.iGetReturnCode());
                        Log.e("bGetJpegImageData : ", "bgetjpegdatais " + com.mydevice.sdk.HexString.bufferToHex(data1));
                        bmpData = com.mydevice.sdk.FpsImageAPI.bConvertWsq2Bmp(data);
                        Log.e("bbConvertWsq2Bmp RETURN VALUE", "iRetValue,:" + MainActivity.intFps.iGetReturnCode());
                        Log.e("bGetBmpdata : ", "bConvertWsq2Bmp " + com.mydevice.sdk.HexString.bufferToHex(bmpData));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                logText.setText("bGetImageData RETURN VALUE,:" + MainActivity.intFps.iGetReturnCode() + "\n"
                                        + "bGetJpegImageData RETURN VALUE,:" + MainActivity.intFps.iGetReturnCode() + "\n"
                                        + "bConvertWsq2Bmp RETURN VALUE,:" + MainActivity.intFps.iGetReturnCode() + "\n"
                                        + "bConvertWsq2Bmp " + MainActivity.intFps.iGetReturnCode());
                            }
                        });
                    }
                } else {
                    byte[] data = MainActivity.intFps.bGetImageData();
                    Log.e("bGetImageData RETURN VALUE", "iRetValue,:" + MainActivity.intFps.iGetReturnCode());
                    Log.e("bGetImageData : ", "bgetimagedatais " + (data));
                    byte[] data1 = MainActivity.intFps.bGetJpgImageData();
                    Log.e("bGetJpegImageData RETURN VALUE", "iRetValue,:" + MainActivity.intFps.iGetReturnCode());
                    Log.e("bGetJpegImageData : ", "bgetjpegdatais " + (data1));
                    bmpData = com.mydevice.sdk.FpsImageAPI.bConvertWsq2Bmp(data);
                    Log.e("bbConvertWsq2Bmp RETURN VALUE", "iRetValue,:" + MainActivity.intFps.iGetReturnCode());
                    Log.e("bGetBmpdata : ", "bConvertWsq2Bmp" + (bmpData));
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            logText.setText("bGetImageData RETURN VALUE,:" + MainActivity.intFps.iGetReturnCode() + "\n"
                                    + "bGetJpegImageData RETURN VALUE,:" + MainActivity.intFps.iGetReturnCode() + "\n"
                                    + "bConvertWsq2Bmp RETURN VALUE,:" + MainActivity.intFps.iGetReturnCode());
                        }
                    });

                }


            } catch (
                    NullPointerException e) {
                e.printStackTrace();
            }
            return iRetVal;
        }


        //* This function sends message to handler to display the status messages of Diagnose in the dialog box
        @Override
        protected void onPostExecute(Integer result) {
            this.tmp.dismiss();

            Log.e("Capture Task ", "LEOPARD FPS Capture onPostExecute Val" + iRetVal);
            if (iRetVal > 0) {
                img_bmp.setVisibility(View.VISIBLE);
                Bitmap bmpfinalnew = BitmapFactory.decodeByteArray(bmpData, 0, bmpData.length);
                img_bmp.setImageBitmap(bmpfinalnew);
                Toast.makeText(FPSActivity.this, "Capture Success", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == FPS.FPS_INACTIVE_PERIPHERAL) {
                Toast.makeText(FPSActivity.this, "Inactive Peripheral", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == FPS.TIME_OUT) {
                Toast.makeText(FPSActivity.this, "Capture Time Out", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == FPS.FAILURE) {
                Toast.makeText(FPSActivity.this, "Capture Failure", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == FPS.PARAMETER_ERROR) {
                Toast.makeText(FPSActivity.this, "Parameter Error", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == FPS.FPS_INVALID_DEVICE_ID) {
                Toast.makeText(FPSActivity.this, "Invalid Device ID", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == FPS.FPS_ILLEGAL_LIBRARY) {
                Toast.makeText(FPSActivity.this, "Illegal Library", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == FPS.NO_RESPONSE) {
                Toast.makeText(FPSActivity.this, "No Response", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == FPS.FPS_FFD) {
                Toast.makeText(FPSActivity.this, "Fake Finger Detection", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == FPS.INVALID_OPERATION || iRetVal == FPS.FPS_ERROR) {
                Toast.makeText(FPSActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }


    }


    public class ReturnAsyn extends AsyncTask<Integer, Integer, Integer> {
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
                //bMinutiaeArr = new byte[3500];
                //FpsConfig fpsconfig = new FpsConfig(0, (byte) 0x0F);
                iRetVal = MainActivity.intFps.iGetReturnCode();
                Log.e("API RETURN VALUES", "iRetValue,:" + iRetVal);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        logText.setText("API RETURN VALUES,:" + iRetVal);
                    }
                });
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            return iRetVal;
        }
    }

    public class MinutiaeAsyn extends AsyncTask<Integer, Integer, Integer> {
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
                bMinutiaeArr = new byte[3500];
                FpsConfig fpsconfig = new FpsConfig(0, (byte) 0x0F);
                bMinutiaeArr = MainActivity.intFps.bGetMinutiaeData();
                Log.e("buffvalue", "captured finger data is,: " + bMinutiaeArr);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        logText.setText("captured finger data is,: " + bMinutiaeArr);
                    }
                });
                if (bMinutiaeArr != null) {
                    Log.e("minutiae", "captured finger data is,:" + HexString.bufferToHex(bMinutiaeArr));
                    iRetVal = MainActivity.intFps.iGetReturnCode();
                    Log.e("API RETURN VALUES", "igeturncodeis,:" + iRetVal);
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            logText.setText("igeturncodeis,:" + iRetVal + "\n"
//                            + "captured finger data is,:" + HexString.bufferToHex(bMinutiaeArr));
//                        }
//                    });
                } else {
                    Log.e("minutiae", "captured finger data is,:" + HexString.bufferToHex(bMinutiaeArr));
                    iRetVal = MainActivity.intFps.iGetReturnCode();
                    Log.e("API RETURN VALUES", "igetreturncodeis,:" + iRetVal);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            logText.setText("captured finger data is,:" + HexString.bufferToHex(bMinutiaeArr) + "\n"
                                    + "igetreturncodeis,:" + iRetVal);
                        }
                    });
                }

            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            return iRetVal;
        }
    }


    public class CompressedAsync extends AsyncTask<Integer, Integer, Integer> {
        int iRetVal;
        byte[] bmpData = null;


        // displays the progress dialog until background task is completed
        private ProgressDialog tmp = null;

        @Override
        protected void onPreExecute() {
            img_bmp.setVisibility(View.INVISIBLE);
            this.tmp = new ProgressDialog(FPSActivity.this);
            this.tmp.setMessage("Processing....");
            this.tmp.setCancelable(false);
            this.tmp.setCanceledOnTouchOutside(false);
            this.tmp.show();
            super.onPreExecute();
            // Task of CaptureFinger performing in the background
        }


        @Override
        protected Integer doInBackground(Integer... params) {
            try {
                byte[] bufvalue = new byte[3500];
                FpsConfig fpsconfig = new FpsConfig(0, (byte) 0x0F);
                iRetVal = MainActivity.intFps.iGetFingerImageCompressed(bufvalue, glbFpsConfig);
                Log.e("compressed", "compresssed data return value is,:" + MainActivity.intFps.iGetReturnCode());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        logText.setText("compresssed data return value is,:" + MainActivity.intFps.iGetReturnCode());
                    }
                });
//                MainActivity.intFps.iGetReturnCode();
//                Log.e("bGetImageData RETURN VALUE", "Igetreturncode,:" + iRetVal);
                if (iRetVal > 0) {
                    if (bufvalue != null) {
                        bMinutiaeArr = MainActivity.intFps.bGetMinutiaeData();
                        Log.e("compressed", "compresssed data return value is,:" + com.mydevice.sdk.HexString.bufferToHex(bMinutiaeArr));
                        byte[] data = MainActivity.intFps.bGetImageData();
                        Log.e("bGetImageData RETURN VALUE", "iRetValue,:" + MainActivity.intFps.iGetReturnCode());
                        Log.e("bGetImageData : ", "bgetimagedatais " + com.mydevice.sdk.HexString.bufferToHex(data));
                        byte[] data1 = MainActivity.intFps.bGetJpgImageData();
                        Log.e("bGetJpegImageData RETURN VALUE", "iRetValue,:" + MainActivity.intFps.iGetReturnCode());
                        Log.e("bGetJpegImageData : ", "bgetjpegdatais " + com.mydevice.sdk.HexString.bufferToHex(data1));
                        byte[] cmpdata = FpsImageAPI.bGetUncompressedRawData(data);
                        Log.e("bGetUncompressedRawData RETURN VALUE", "iRetValue,:" + MainActivity.intFps.iGetReturnCode());
                        Log.e("bGetUncompressedRawData: ", "bGetUncompressedRawData " + com.mydevice.sdk.HexString.bufferToHex(cmpdata));
                        bmpData = com.mydevice.sdk.FpsImageAPI.bConvertRaw2bmp(cmpdata);
                        Log.e("bbConvertRaw2Bmp RETURN VALUE", "iRetValue,:" + MainActivity.intFps.iGetReturnCode());
                        Log.e("bGetBmpdata : ", "bConvertRaw2bmp" + com.mydevice.sdk.HexString.bufferToHex(bmpData));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                logText.setText("bGetimagedata RETURN VALUE,:" + MainActivity.intFps.iGetReturnCode() + "\n"
                                        + "bGetJpegImageData RETURN VALUE,:" + MainActivity.intFps.iGetReturnCode() + "\n"
                                        + "bGetUncompressedRawData RETURN VALUE,:" + MainActivity.intFps.iGetReturnCode() + "\n"
                                        + "bConvertRaw2Bmp RETURN VALUE,:" + MainActivity.intFps.iGetReturnCode());
                            }
                        });
                    }
                } else {
                    byte[] data = MainActivity.intFps.bGetImageData();
                    Log.e("bGetImageData RETURN VALUE", "iRetValue,:" + MainActivity.intFps.iGetReturnCode());
                    Log.e("bGetImageData : ", "bgetimagedatais " + (data));
                    byte[] data1 = MainActivity.intFps.bGetJpgImageData();
                    Log.e("bGetJpegImageData RETURN VALUE", "iRetValue,:" + MainActivity.intFps.iGetReturnCode());
                    Log.e("bGetJpegImageData : ", "bgetjpegdatais " + (data1));
                    byte[] cmpdata = FpsImageAPI.bGetUncompressedRawData(data);
                    Log.e("bGetUncompressedRawData RETURN VALUE", "iRetValue,:" + MainActivity.intFps.iGetReturnCode());
                    Log.e("bGetUncompressedRawData: ", "bGetUncompressedRawData " + (cmpdata));
                    bmpData = com.mydevice.sdk.FpsImageAPI.bConvertRaw2bmp(cmpdata);
                    Log.e("bbConvertRaw2Bmp RETURN VALUE", "iRetValue,:" + MainActivity.intFps.iGetReturnCode());
                    Log.e("bGetBmpdata : ", "bConvertRaw2bmp" + (bmpData));
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            logText.setText("bGetImageData RETURN VALUE,:" + MainActivity.intFps.iGetReturnCode() + "\n"
                                    + "bGetJpegImageData RETURN VALUE,:" + MainActivity.intFps.iGetReturnCode() + "\n"
                                    + "bGetUncompressedRawData RETURN VALUE,:" + MainActivity.intFps.iGetReturnCode() + "\n"
                                    + "bConvertRaw2Bmp RETURN VALUE,:" + MainActivity.intFps.iGetReturnCode());
                        }
                    });

                }


            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            return iRetVal;
        }

        //* This function sends message to handler to display the status messages of Diagnose in the dialog box
        @Override
        protected void onPostExecute(Integer result) {
            this.tmp.dismiss();

            Log.e("Capture Task ", "LEOPARD FPS Capture onPostExecute Val" + iRetVal);
            if (iRetVal > 0) {
                img_bmp.setVisibility(View.VISIBLE);
                try {
                    Bitmap bmpfinalnew = BitmapFactory.decodeByteArray(bmpData, 0, bmpData.length);
                    img_bmp.setImageBitmap(bmpfinalnew);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Toast.makeText(FPSActivity.this, "Capture Success", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == FPS.FPS_INACTIVE_PERIPHERAL) {
                Toast.makeText(FPSActivity.this, "Inactive Peripheral", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == FPS.TIME_OUT) {
                Toast.makeText(FPSActivity.this, "Capture Time Out", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == FPS.FAILURE) {
                Toast.makeText(FPSActivity.this, "Capture Failure", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == FPS.PARAMETER_ERROR) {
                Toast.makeText(FPSActivity.this, "Parameter Error", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == FPS.FPS_INVALID_DEVICE_ID) {
                Toast.makeText(FPSActivity.this, "Invalid Device ID", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == FPS.FPS_ILLEGAL_LIBRARY) {
                Toast.makeText(FPSActivity.this, "Illegal Library", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == FPS.NO_RESPONSE) {
                Toast.makeText(FPSActivity.this, "No Response", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == FPS.FPS_FFD) {
                Toast.makeText(FPSActivity.this, "Fake Finger Detection", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == FPS.INVALID_OPERATION || iRetVal == FPS.FPS_ERROR) {
                Toast.makeText(FPSActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }


    }

    public class UncompressedAsync extends AsyncTask<Integer, Integer, Integer> {
        int iRetVal;
        byte[] bmpData = null;


        // displays the progress dialog until background task is completed
        private ProgressDialog tmp = null;

        @Override
        protected void onPreExecute() {
            img_bmp.setVisibility(View.INVISIBLE);
            this.tmp = new ProgressDialog(FPSActivity.this);
            this.tmp.setMessage("Processing....");
            this.tmp.setCancelable(false);
            this.tmp.setCanceledOnTouchOutside(false);
            this.tmp.show();
            super.onPreExecute();
            // Task of CaptureFinger performing in the background
        }


        @Override
        protected Integer doInBackground(Integer... params) {
            try {
                byte[] bufvalue = new byte[3500];
                FpsConfig fpsconfig = new FpsConfig(0, (byte) 0x0F);
                iRetVal = MainActivity.intFps.iGetFingerImageUnCompressed(bufvalue, glbFpsConfig);
                Log.e("uncompressed", "uncompresssed data return value is,:" + MainActivity.intFps.iGetReturnCode());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        logText.setText("uncompresssed data return value is,:" + MainActivity.intFps.iGetReturnCode());
                    }
                });
//                MainActivity.intFps.iGetReturnCode();
//                Log.e("bGetImageData RETURN VALUE", "Igetreturncode,:" + iRetVal);
                if (iRetVal > 0) {
                    if (bufvalue != null) {
                        bMinutiaeArr = MainActivity.intFps.bGetMinutiaeData();
                        Log.e("uncompressed api", "uncompresssed data return value is,:" + com.mydevice.sdk.HexString.bufferToHex(bMinutiaeArr));
                        byte[] data = MainActivity.intFps.bGetImageData();
                        Log.e("bGetImageData RETURN VALUE", "iRetValue,:" + MainActivity.intFps.iGetReturnCode());
                        Log.e("bGetImageData : ", "bgetimagedatais " + com.mydevice.sdk.HexString.bufferToHex(data));
                        byte[] data1 = MainActivity.intFps.bGetJpgImageData();
                        Log.e("bGetJpegImageData RETURN VALUE", "iRetValue,:" + MainActivity.intFps.iGetReturnCode());
                        Log.e("bGetJpegImageData : ", "bgetjpegdatais " + com.mydevice.sdk.HexString.bufferToHex(data1));
                        bmpData = com.mydevice.sdk.FpsImageAPI.bConvertRaw2bmp(data);
                        Log.e("bbConvertWsq2Bmp RETURN VALUE", "iRetValue,:" + MainActivity.intFps.iGetReturnCode());
                        Log.e("bGetBmpdata : ", "bConvertRaw2bmp " + com.mydevice.sdk.HexString.bufferToHex(bmpData));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                logText.setText("bGetImageData RETURN VALUE,:" + MainActivity.intFps.iGetReturnCode() + "\n"
                                        + "bGetJpegImageData RETURN VALUE,:" + MainActivity.intFps.iGetReturnCode() + "\n"
                                        + "bConvertWsq2Bmp RETURN VALUE,:" + MainActivity.intFps.iGetReturnCode());
                            }
                        });
                    }
                } else {
                    byte[] data = MainActivity.intFps.bGetImageData();
                    Log.e("bGetImageData RETURN VALUE", "iRetValue,:" + MainActivity.intFps.iGetReturnCode());
                    Log.e("bGetImageData : ", "bgetimagedatais " + (data));
                    byte[] data1 = MainActivity.intFps.bGetJpgImageData();
                    Log.e("bGetJpegImageData RETURN VALUE", "iRetValue,:" + MainActivity.intFps.iGetReturnCode());
                    Log.e("bGetJpegImageData : ", "bgetjpegdatais " + (data1));
                    bmpData = com.mydevice.sdk.FpsImageAPI.bConvertWsq2Bmp(data);
                    Log.e("bbConvertWsq2Bmp RETURN VALUE", "iRetValue,:" + MainActivity.intFps.iGetReturnCode());
                    Log.e("bGetBmpdata : ", "bConvertRaw2bmp" + (bmpData));
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            logText.setText("bGetImageData RETURN VALUE:" + MainActivity.intFps.iGetReturnCode() + "\n"
                                    + "bGetJpegImageData RETURN VALUE,:" + MainActivity.intFps.iGetReturnCode() + "\n"
                                    + "bConvertWsq2Bmp RETURN VALUE,:" + MainActivity.intFps.iGetReturnCode());

                        }
                    });

                }


            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            return iRetVal;
        }

        //* This function sends message to handler to display the status messages of Diagnose in the dialog box
        @Override
        protected void onPostExecute(Integer result) {
            this.tmp.dismiss();

            Log.e("Capture Task ", "LEOPARD FPS Capture onPostExecute Val" + iRetVal);
            if (iRetVal > 0) {
                img_bmp.setVisibility(View.VISIBLE);
                try {
                    Bitmap bmpfinalnew = BitmapFactory.decodeByteArray(bmpData, 0, bmpData.length);
                    img_bmp.setImageBitmap(bmpfinalnew);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Toast.makeText(FPSActivity.this, "Capture Success", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == FPS.FPS_INACTIVE_PERIPHERAL) {
                Toast.makeText(FPSActivity.this, "Inactive Peripheral", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == FPS.TIME_OUT) {
                Toast.makeText(FPSActivity.this, "Capture Time Out", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == FPS.FAILURE) {
                Toast.makeText(FPSActivity.this, "Capture Failure", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == FPS.PARAMETER_ERROR) {
                Toast.makeText(FPSActivity.this, "Parameter Error", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == FPS.FPS_INVALID_DEVICE_ID) {
                Toast.makeText(FPSActivity.this, "Invalid Device ID", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == FPS.FPS_ILLEGAL_LIBRARY) {
                Toast.makeText(FPSActivity.this, "Illegal Library", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == FPS.NO_RESPONSE) {
                Toast.makeText(FPSActivity.this, "No Response", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == FPS.FPS_FFD) {
                Toast.makeText(FPSActivity.this, "Fake Finger Detection", Toast.LENGTH_SHORT).show();
            } else if (iRetVal == FPS.INVALID_OPERATION || iRetVal == FPS.FPS_ERROR) {
                Toast.makeText(FPSActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }


    }

    public class BgetimagedataAsyn extends AsyncTask<Integer, Integer, Integer> {
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
                //bMinutiaeArr = new byte[3500];
                FpsConfig fpsconfig = new FpsConfig(0, (byte) 0x0F);
                byte[] data = MainActivity.intFps.bGetImageData();
                Log.e("bGetImageData RETURN VALUE", "iRetValue,:" + MainActivity.intFps.iGetReturnCode());
                Log.e("bGetImageData : ", "value " + com.mydevice.sdk.HexString.bufferToHex(data));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        logText.setText("bGetImageData RETURN VALUE,:" + MainActivity.intFps.iGetReturnCode() + "\n");
                    }
                });
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            return iRetVal;
        }
    }

    public class VerifymatchAsync extends AsyncTask<Integer, Integer, Integer> {
        int iRetVal;
        byte[] bmpData;

        byte[] data1 = null;


        // displays the progress dialog until background task is completed
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Integer doInBackground(Integer... params) {
            MainActivity.intFps.setVerificationImageReturn(true);
            try {
//                byte[] pk_mat= HexString.hexToBuffer("000000000000000000000000000000002B007B7E635AFF057654000001000004FFFFFFFFFFFFFFFFFFFFFF00FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF000000000C313C8C08A449A008454E7104B8508408A95E9A0CE5636B0CD0697E0C656A680C1C6AA714456E7B10546F7214CD6F7E0C9D71A308E4725E08217495103875810CA8789014587C77089D7D9A041D819310BD82820CED896410698A6D0498909A089491A10C6892530C6893721061937B14609E7714F1A3820499A8A3049DAD9C04B0B29604ACB39E10A4B6AD0864B75A0459B8670C58B9740CD9BB580C55C1761461C3530458C45F05C4C599000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
//                FpsConfig fpsconfig = new FpsConfig(0, (byte) 0x0F);
//                iRetVal = MainActivity.intFps.iFpsVerifyPKMAT(pk_mat,glbFpsConfig);
                iRetVal = MainActivity.intFps.iFpsVerifyMatchMinutiae(bMinutiaeArr, bArrcapList);
                Log.e("verifymatch", "verifymatchindexfingeris,:" + iRetVal);
                iRetVal = MainActivity.intFps.iGetReturnCode();
                Log.e("verifymatch", "Verifymatchreturncodeis,:" + iRetVal);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        logText.setText("verifymatchindexfingeris,:" + iRetVal + "\n"
                                + "Verifymatchreturncodeis,:" + iRetVal);
                    }
                });


            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            return iRetVal;
        }
    }

    public class FingerPositionAsync extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer... params) {
            new Thread() {
                public void run() {
                    try {
                        MainActivity.intFps.vSetAsyncInstance();

                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                    do {
                        try {

                            int qual = MainActivity.intFps.iGetCurrentFingerQuality();
                            Log.e("Fingerquality", "run: finger current finger Quality" + qual);
                            for (int i = 0; i <= qual; i++) {
                                Thread.sleep(50);
                            }
                            // returns the current Finger Position
                            int pos = MainActivity.intFps.iGetCurrentFingerPosition();
                            Log.e("Return Code of Position", "finger position : " + pos);
                            switch (pos) {
                                case com.mydevice.sdk.FPS.NO_FINGER:
//                                    Toast.makeText(FPSActivity.this, "NO FINGER", Toast.LENGTH_SHORT).show();
                                    Log.e("no finger", "No finger:" + com.mydevice.sdk.FPS.NO_FINGER);
                                    break;
                                case com.mydevice.sdk.FPS.MOVE_FINGER_UP:
//                                    Toast.makeText(FPSActivity.this, "MOVE FINGER UP", Toast.LENGTH_SHORT).show();
                                    Log.e("move finger", "Move finger up:" + com.mydevice.sdk.FPS.MOVE_FINGER_UP);
                                    break;
                                case com.mydevice.sdk.FPS.MOVE_FINGER_DOWN:
//                                    Toast.makeText(FPSActivity.this, "MOVE FINGER DOWN", Toast.LENGTH_SHORT).show();
                                    Log.e("move finger", "Move finger down:" + com.mydevice.sdk.FPS.MOVE_FINGER_DOWN);
                                    break;
                                case com.mydevice.sdk.FPS.MOVE_FINGER_LEFT:
//                                    Toast.makeText(FPSActivity.this, "MOVE FINGER LEFT", Toast.LENGTH_SHORT).show();
                                    Log.e("move finger", "Move finger left:" + com.mydevice.sdk.FPS.MOVE_FINGER_LEFT);
                                    break;
                                case com.mydevice.sdk.FPS.MOVE_FINGER_RIGHT:
//                                    Toast.makeText(FPSActivity.this, "MOVE FINGER RIGHT", Toast.LENGTH_SHORT).show();
                                    Log.e("move finger", "Move finger RIGHT:" + com.mydevice.sdk.FPS.MOVE_FINGER_RIGHT);
                                    break;
                                case com.mydevice.sdk.FPS.PRESS_FINGER_HARDER:
//                                    Toast.makeText(FPSActivity.this, "PRESS FINGER HARDER", Toast.LENGTH_SHORT).show();
                                    Log.e("press finger", "Press finger harder:" + com.mydevice.sdk.FPS.PRESS_FINGER_HARDER);
                                    break;
                                case com.mydevice.sdk.FPS.LATENT:
//                                    Toast.makeText(FPSActivity.this, "LATENT", Toast.LENGTH_SHORT).show();
                                    Log.e("latent", "Latent:" + com.mydevice.sdk.FPS.LATENT);
                                    break;
                                case com.mydevice.sdk.FPS.REMOVE_FINGER:
//                                    Toast.makeText(FPSActivity.this, "REMOVE FINGER", Toast.LENGTH_SHORT).show();
                                    Log.e("remove", "Remove finger:" + com.mydevice.sdk.FPS.REMOVE_FINGER);
                                    break;
                                case com.mydevice.sdk.FPS.FINGER_OK:
//                                    Toast.makeText(FPSActivity.this, "Finger ok", Toast.LENGTH_SHORT).show();
                                    Log.e("Finger", "Finger ok:" + com.mydevice.sdk.FPS.FINGER_OK);
                                    break;
                                default:
                                    break;
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } while (MainActivity.intFps.blGetAsyncInstance());
                }

            }.start();

            new Thread() {
                public void run() {
                    fpsconfig = new FpsConfig(1, (byte) 0x0F);
                    bRespBuff = MainActivity.intFps.bFpsCaptureMinutiae(glbFpsConfig);
                    Log.e("fps", "FPSCaptureMinutiaedatais" + MainActivity.intFps.iGetReturnCode());
                    int response = MainActivity.intFps.iGetReturnCode();
                    if (response == FPS.SUCCESS) {

                        SystemClock.sleep(2000);

                    } else if (response == FPS.TIME_OUT) {

                        SystemClock.sleep(2000);


                    }

                }
            }.start();

            return null;
        }
    }


    public class BmpfiledataAsync extends AsyncTask<Integer, Integer, Integer> {
        int iRetVal;
        byte[] bmpData = null;


        // displays the progress dialog until background task is completed
        private ProgressDialog tmp = null;

        @Override
        protected void onPreExecute() {
            img_bmp.setVisibility(View.INVISIBLE);
            this.tmp = new ProgressDialog(FPSActivity.this);
            this.tmp.setMessage("Processing....");
            this.tmp.setCancelable(false);
            this.tmp.setCanceledOnTouchOutside(false);
            this.tmp.show();
            super.onPreExecute();
            // Task of CaptureFinger performing in the background
        }


        @Override
        protected Integer doInBackground(Integer... params) {
            try {
                byte[] bufvalue = new byte[3500];
                FpsConfig fpsconfig = new FpsConfig(0, (byte) 0x0F);
                iRetVal = MainActivity.intFps.iGetFingerImageUnCompressed(bufvalue, glbFpsConfig);
                Log.e("uncompressed", "uncompresssed data return value is,:" + MainActivity.intFps.iGetReturnCode());
//                MainActivity.intFps.iGetReturnCode();
                if (iRetVal > 0) {
                    if (bufvalue != null) {
                        bMinutiaeArr = MainActivity.intFps.bGetMinutiaeData();
                        Log.e("uncompressed", "uncompresssed data is,:" + com.mydevice.sdk.HexString.bufferToHex(bMinutiaeArr));
                        byte[] data = MainActivity.intFps.bGetImageData();
                        Log.e("bGetImageData RETURN VALUE", "iRetValue,:" + MainActivity.intFps.iGetReturnCode());
                        Log.e("bGetImageData : ", "bgetimagedatais " + com.mydevice.sdk.HexString.bufferToHex(data));
                        byte[] data1 = MainActivity.intFps.bGetJpgImageData();
                        Log.e("bGetJpegImageData RETURN VALUE", "iRetValue,:" + MainActivity.intFps.iGetReturnCode());
                        Log.e("bGetJpegImageData : ", "bgetjpegdatais " + com.mydevice.sdk.HexString.bufferToHex(data1));
                        bmpData = com.mydevice.sdk.FpsImageAPI.bConvertRaw2bmp(data);
                        Log.e("bbConvertWsq2Bmp RETURN VALUE", "iRetValue,:" + MainActivity.intFps.iGetReturnCode());
                        Log.e("bGetBmpdata : ", "bConvertRaw2bmp " + com.mydevice.sdk.HexString.bufferToHex(bmpData));
                    }
                } else {
                    byte[] data = MainActivity.intFps.bGetImageData();
                    Log.e("bGetImageData RETURN VALUE", "iRetValue,:" + MainActivity.intFps.iGetReturnCode());
                    Log.e("bGetImageData : ", "bgetimagedatais " + (data));
                    byte[] data1 = MainActivity.intFps.bGetJpgImageData();
                    Log.e("bGetJpegImageData RETURN VALUE", "iRetValue,:" + MainActivity.intFps.iGetReturnCode());
                    Log.e("bGetJpegImageData : ", "bgetjpegdatais " + (data1));
                    bmpData = com.mydevice.sdk.FpsImageAPI.bConvertRaw2bmp(data);
                    Log.e("bbConvertWsq2Bmp RETURN VALUE", "iRetValue,:" + MainActivity.intFps.iGetReturnCode());
                    Log.e("bGetBmpdata : ", "bConvertRaw2bmp" + (bmpData));

                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            return iRetVal;
        }

        //* This function sends message to handler to display the status messages of Diagnose in the dialog box
        @Override
        protected void onPostExecute(Integer result) {
            this.tmp.dismiss();

            Log.e("Capture Task ", "LEOPARD FPS Capture onPostExecute Val" + iRetVal);
            if (iRetVal > 0) {
                img_bmp.setVisibility(View.VISIBLE);
                Bitmap bmpfinalnew = BitmapFactory.decodeByteArray(bmpData, 0, bmpData.length);
                Bitmap bitmap = Bitmap.createScaledBitmap(bmpfinalnew, 384, bmpfinalnew.getHeight(), false);
                byte[] fileData = FpsImageAPI.bGetBmpFileData(bitmap);
                ByteArrayInputStream bis = new ByteArrayInputStream(fileData);
                Log.e("file data", "bGetBmpFileData is " + MainActivity.intFps.iGetReturnCode());
                Log.e("file data : ", "bGetBmpFileData is" + (fileData));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        logText.setText("bGetBmpFileData is " + MainActivity.intFps.iGetReturnCode() + "\n"
                                + "bGetBmpFileData is" + (fileData));
                    }
                });
                if (fileData != null) {
                    Log.e("file data", "bGetBmpFileData" + com.mydevice.sdk.HexString.bufferToHex(fileData));
                    try {
                        store_TrustKey_ToExternal1(fileData);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    img_bmp.setImageBitmap(bmpfinalnew);


                    Toast.makeText(FPSActivity.this, "Capture Success", Toast.LENGTH_SHORT).show();
                } else if (iRetVal == FPS.FPS_INACTIVE_PERIPHERAL) {
                    Toast.makeText(FPSActivity.this, "Inactive Peripheral", Toast.LENGTH_SHORT).show();
                } else if (iRetVal == FPS.TIME_OUT) {
                    Toast.makeText(FPSActivity.this, "Capture Time Out", Toast.LENGTH_SHORT).show();
                } else if (iRetVal == FPS.FAILURE) {
                    Toast.makeText(FPSActivity.this, "Capture Failure", Toast.LENGTH_SHORT).show();
                } else if (iRetVal == FPS.PARAMETER_ERROR) {
                    Toast.makeText(FPSActivity.this, "Parameter Error", Toast.LENGTH_SHORT).show();
                } else if (iRetVal == FPS.FPS_INVALID_DEVICE_ID) {
                    Toast.makeText(FPSActivity.this, "Invalid Device ID", Toast.LENGTH_SHORT).show();
                } else if (iRetVal == FPS.FPS_ILLEGAL_LIBRARY) {
                    Toast.makeText(FPSActivity.this, "Illegal Library", Toast.LENGTH_SHORT).show();
                } else if (iRetVal == FPS.NO_RESPONSE) {
                    Toast.makeText(FPSActivity.this, "No Response", Toast.LENGTH_SHORT).show();
                } else if (iRetVal == FPS.FPS_FFD) {
                    Toast.makeText(FPSActivity.this, "Fake Finger Detection", Toast.LENGTH_SHORT).show();
                } else if (iRetVal == FPS.INVALID_OPERATION || iRetVal == FPS.FPS_ERROR) {
                    Toast.makeText(FPSActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
                super.onPostExecute(result);
            }
        }
    }


    public class FpsimagebmpdataAsync extends AsyncTask<Integer, Integer, Integer> {
        int iRetVal;
        byte[] bmpData = null;


        // displays the progress dialog until background task is completed
        private ProgressDialog tmp = null;

        @Override
        protected void onPreExecute() {
            img_bmp.setVisibility(View.INVISIBLE);
            this.tmp = new ProgressDialog(FPSActivity.this);
            this.tmp.setMessage("Processing....");
            this.tmp.setCancelable(false);
            this.tmp.setCanceledOnTouchOutside(false);
            this.tmp.show();
            super.onPreExecute();
            // Task of CaptureFinger performing in the background
        }


        @Override
        protected Integer doInBackground(Integer... params) {
            try {
                byte[] bufvalue = new byte[3500];
                FpsConfig fpsconfig = new FpsConfig(0, (byte) 0x0F);
                iRetVal = MainActivity.intFps.iGetFingerImageUnCompressed(bufvalue, glbFpsConfig);
                Log.e("uncompressed", "uncompresssed data return value is,:" + MainActivity.intFps.iGetReturnCode());
//                MainActivity.intFps.iGetReturnCode();
                if (iRetVal > 0) {
                    if (bufvalue != null) {
                        bMinutiaeArr = MainActivity.intFps.bGetMinutiaeData();
                        Log.e("uncompressed", "uncompresssed data is,:" + com.mydevice.sdk.HexString.bufferToHex(bMinutiaeArr));
                        byte[] data = MainActivity.intFps.bGetImageData();
                        Log.e("bGetImageData RETURN VALUE", "iRetValue,:" + MainActivity.intFps.iGetReturnCode());
                        Log.e("bGetImageData : ", "bgetimagedatais " + com.mydevice.sdk.HexString.bufferToHex(data));
                        byte[] data1 = MainActivity.intFps.bGetJpgImageData();
                        Log.e("bGetJpegImageData RETURN VALUE", "iRetValue,:" + MainActivity.intFps.iGetReturnCode());
                        Log.e("bGetJpegImageData : ", "bgetjpegdatais " + com.mydevice.sdk.HexString.bufferToHex(data1));
                        bmpData = com.mydevice.sdk.FpsImageAPI.bConvertRaw2bmp(data);
                        Log.e("bbConvertWsq2Bmp RETURN VALUE", "iRetValue,:" + MainActivity.intFps.iGetReturnCode());
                        Log.e("bGetBmpdata : ", "bConvertRaw2bmp " + com.mydevice.sdk.HexString.bufferToHex(bmpData));
                    }
                } else {
                    byte[] data = MainActivity.intFps.bGetImageData();
                    Log.e("bGetImageData RETURN VALUE", "iRetValue,:" + MainActivity.intFps.iGetReturnCode());
                    Log.e("bGetImageData : ", "bgetimagedatais " + (data));
                    byte[] data1 = MainActivity.intFps.bGetJpgImageData();
                    Log.e("bGetJpegImageData RETURN VALUE", "iRetValue,:" + MainActivity.intFps.iGetReturnCode());
                    Log.e("bGetJpegImageData : ", "bgetjpegdatais " + (data1));
                    bmpData = com.mydevice.sdk.FpsImageAPI.bConvertRaw2bmp(data);
                    Log.e("bbConvertWsq2Bmp RETURN VALUE", "iRetValue,:" + MainActivity.intFps.iGetReturnCode());
                    Log.e("bGetBmpdata : ", "bConvertRaw2bmp" + (bmpData));

                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            return iRetVal;
        }

        //* This function sends message to handler to display the status messages of Diagnose in the dialog box
        @Override
        protected void onPostExecute(Integer result) {
            this.tmp.dismiss();

            Log.e("Capture Task ", "LEOPARD FPS Capture onPostExecute Val" + iRetVal);
            if (iRetVal > 0) {
                img_bmp.setVisibility(View.VISIBLE);
                Bitmap bmpfinalnew = BitmapFactory.decodeByteArray(bmpData, 0, bmpData.length);
                Bitmap bitmap1 = Bitmap.createScaledBitmap(bmpfinalnew, 256, 400, false);
                byte[] fileData = FpsImageAPI.bGetFpsImageBmpData(bitmap1);
                ByteArrayInputStream bis = new ByteArrayInputStream(fileData);
                Log.e("file data", "bGetFpsImageBmpData is " + MainActivity.intFps.iGetReturnCode());
                Log.e("file data : ", "bGetFpsImageBmpData is" + (fileData));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        logText.setText("bGetFpsImageBmpData is " + MainActivity.intFps.iGetReturnCode() + "\n"
                                + "bGetFpsImageBmpData is" + (fileData));
                    }
                });
                if (fileData != null) {
                    Log.e("file data 1", "bGetFpsImageBmpData is" + com.mydevice.sdk.HexString.bufferToHex(fileData));
                    img_bmp.setImageBitmap(bmpfinalnew);
                    vSavetoFile(fileData, "bGetFpsImageBmpData.bmp");
                    Toast.makeText(FPSActivity.this, "Capture Success", Toast.LENGTH_SHORT).show();
                } else if (iRetVal == FPS.FPS_INACTIVE_PERIPHERAL) {
                    Toast.makeText(FPSActivity.this, "Inactive Peripheral", Toast.LENGTH_SHORT).show();
                } else if (iRetVal == FPS.TIME_OUT) {
                    Toast.makeText(FPSActivity.this, "Capture Time Out", Toast.LENGTH_SHORT).show();
                } else if (iRetVal == FPS.FAILURE) {
                    Toast.makeText(FPSActivity.this, "Capture Failure", Toast.LENGTH_SHORT).show();
                } else if (iRetVal == FPS.PARAMETER_ERROR) {
                    Toast.makeText(FPSActivity.this, "Parameter Error", Toast.LENGTH_SHORT).show();
                } else if (iRetVal == FPS.FPS_INVALID_DEVICE_ID) {
                    Toast.makeText(FPSActivity.this, "Invalid Device ID", Toast.LENGTH_SHORT).show();
                } else if (iRetVal == FPS.FPS_ILLEGAL_LIBRARY) {
                    Toast.makeText(FPSActivity.this, "Illegal Library", Toast.LENGTH_SHORT).show();
                } else if (iRetVal == FPS.NO_RESPONSE) {
                    Toast.makeText(FPSActivity.this, "No Response", Toast.LENGTH_SHORT).show();
                } else if (iRetVal == FPS.FPS_FFD) {
                    Toast.makeText(FPSActivity.this, "Fake Finger Detection", Toast.LENGTH_SHORT).show();
                } else if (iRetVal == FPS.INVALID_OPERATION || iRetVal == FPS.FPS_ERROR) {
                    Toast.makeText(FPSActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
                super.onPostExecute(result);
            }
        }
    }

    public class VerifyPKMATAsync extends AsyncTask<Integer, Integer, Integer> {
        int iRetVal;
        byte[] bmpData = null;
        byte[] data1 = null;
        private ProgressDialog tmp = null;

        // displays the progress dialog until background task is completed
        @Override
        protected void onPreExecute() {
            img_bmp.setVisibility(View.INVISIBLE);
            img_jpeg.setVisibility(View.INVISIBLE);
            this.tmp = new ProgressDialog(FPSActivity.this);
            this.tmp.setMessage("Processing....");
            this.tmp.setCancelable(false);
            this.tmp.setCanceledOnTouchOutside(false);
            this.tmp.show();
            super.onPreExecute();
        }

        // Task of CaptureFinger performing in the background
        @Override
        protected Integer doInBackground(Integer... params) {
            try {
//                byte[] Morpho = HexString.hexToBuffer("3400757C4F5AFF055E5413060100FF03FFFFFFFFFFFFFFFFFFFFFF00FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF44C0387704AC38921835398304483B6804CD3C6F04B93D8104E0475604A8499844E14C67049C53AD101955A504E8585E04A15A9144C15C7E0C9861AC041162B8046C665644F96670041068C108F86C5044086C73049D75A6049575AC0415797E20947D9104817E4E049182780815836E049983850421868D0421897E04108C58041D8D7784D18DB0040D9244846894B1446C95BD04109750445D9BBC04919E4D04A1A7780498A95F04B0AB9F04A0BB5504A8BF6004A4C04D04ACC37F0CB1C86B04B5C88404B4CC790441CFAE19BDDB81");
                byte[] Morpho = HexString.hexToBuffer("000000000000000000000000000000002100817C635AFF058154000001000004FFFFFFFFFFFFFFFFFFFFFF00FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF0000000004D53D8E184D445704484E71043C516204D8519704DC54A204DC57960434606D042D635E0835637504C864850838657C0424677104206F6A0C78738B08D17A8F04E57B9504E87C9D04417F8504F182A30419846D046D85AB08359B8418109C5504BC9C9004D8A2A00421A36D0CE5A6AA04A1A86B0455A9960869ADA60428B4770540C38D00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
////              byte[][] multi= new byte[5][];//1:N usage for verify many minutiae use this and change fps instance to ifpsverifymanyminutiae;

                MainActivity.intFps.setVerificationImageReturn(true);
                iRetVal = MainActivity.intFps.iFpsVerifyPKMAT(Morpho, glbFpsConfig);
                Log.e("Verify minutiae return value", "iFpsverifyPKMAT,:" + iRetVal);
                iRetVal = MainActivity.intFps.iGetReturnCode();
                Log.e("bGetImageData RETURN VALUE", "Igetreturncodeis,:" + iRetVal);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        logText.setText("iFpsverifyPKMAT,:" + iRetVal + "\n" + "Igetreturncodeis,:" + iRetVal);
                    }
                });
                if (iRetVal == FPS.SUCCESS) {
                    byte[] data = MainActivity.intFps.bGetImageData();
                    Log.e("bGetImageData RETURN VALUE", "iRetValue,:" + MainActivity.intFps.iGetReturnCode());
                    Log.e("bGetImageData : ", "bGetimagedatais " + com.mydevice.sdk.HexString.bufferToHex(data));
                    data1 = MainActivity.intFps.bGetJpgImageData();
                    Log.e("bGetJpegImageData RETURN VALUE", "iRetValue,:" + MainActivity.intFps.iGetReturnCode());
                    Log.e("bGetJpegData : ", "bGetJpegDatais  " + com.mydevice.sdk.HexString.bufferToHex(data1));
                    bmpData = com.mydevice.sdk.FpsImageAPI.bConvertRaw2bmp(data);
                    Log.e("bConvertRaw2bmp RETURN VALUE", "iRetValue,:" + MainActivity.intFps.iGetReturnCode());
                    Log.e("bconvertRaw2BmpData : ", "bconvertRaw2BmpDatais" + com.mydevice.sdk.HexString.bufferToHex(bmpData));


                } else {
                    byte[] data = MainActivity.intFps.bGetImageData();
                    Log.e("bGetImageData RETURN VALUE", "iRetValue,:" + MainActivity.intFps.iGetReturnCode());
                    Log.e("bGetImageData : ", "bGetimagedatais  " + (data));
                    data1 = MainActivity.intFps.bGetJpgImageData();
                    Log.e("bGetJpegImageData RETURN VALUE", "iRetValue,:" + MainActivity.intFps.iGetReturnCode());
                    Log.e("bGetJpegData : ", "bGetJpegData" + (data1));
                    bmpData = com.mydevice.sdk.FpsImageAPI.bConvertRaw2bmp(data);
                    Log.e("bConvertRaw2bmp RETURN VALUE", "iRetValue,:" + MainActivity.intFps.iGetReturnCode());
                    Log.e("bconvertRaw2Bmp: ", "bconvertRaw2Bmpdatais" + (bmpData));
                    Log.e("Capture Task", "Verified Minutiae data is ,:" + iRetVal);

                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            return iRetVal;
        }

        //* This function sends message to handler to display the status messages of Diagnose in the dialog box
        @Override
        protected void onPostExecute(Integer result) {
            this.tmp.dismiss();
            Log.e("Capture Task ", "LEOPARD FPS Capture onPostExecute Val" + iRetVal);
            if (iRetVal == -5) {
                img_bmp.setVisibility(View.VISIBLE);
                img_jpeg.setVisibility(View.VISIBLE);
                try {
                    Bitmap bmpfinalnew = BitmapFactory.decodeByteArray(bmpData, 0, bmpData.length);
                    img_bmp.setImageBitmap(bmpfinalnew);
                    Bitmap bmpfinalnew1 = BitmapFactory.decodeByteArray(data1, 0, data1.length);
                    img_jpeg.setImageBitmap(bmpfinalnew1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (iRetVal == FPS.SUCCESS) {
                    Toast.makeText(FPSActivity.this, "Capture Success", Toast.LENGTH_SHORT).show();
                } else if (iRetVal == FPS.FPS_INACTIVE_PERIPHERAL) {
                    Toast.makeText(FPSActivity.this, "Inactive Peripheral", Toast.LENGTH_SHORT).show();
                } else if (iRetVal == FPS.TIME_OUT) {
                    Toast.makeText(FPSActivity.this, "Capture Time Out", Toast.LENGTH_SHORT).show();
                } else if (iRetVal == FPS.FAILURE) {
                    Toast.makeText(FPSActivity.this, "Capture Failure", Toast.LENGTH_SHORT).show();
                } else if (iRetVal == FPS.PARAMETER_ERROR) {
                    Toast.makeText(FPSActivity.this, "Parameter Error", Toast.LENGTH_SHORT).show();
                } else if (iRetVal == FPS.FPS_INVALID_DEVICE_ID) {
                    Toast.makeText(FPSActivity.this, "Invalid Device ID", Toast.LENGTH_SHORT).show();
                } else if (iRetVal == FPS.FPS_ILLEGAL_LIBRARY) {
                    Toast.makeText(FPSActivity.this, "Illegal Library", Toast.LENGTH_SHORT).show();
                } else if (iRetVal == FPS.NO_RESPONSE) {
                    Toast.makeText(FPSActivity.this, "No Response", Toast.LENGTH_SHORT).show();
                } else if (iRetVal == FPS.FPS_FFD) {
                    Toast.makeText(FPSActivity.this, "Fake Finger Detection", Toast.LENGTH_SHORT).show();
                } else if (iRetVal == FPS.INVALID_OPERATION || iRetVal == FPS.FPS_ERROR) {
                    Toast.makeText(FPSActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
                super.onPostExecute(result);
            }
        }

    }

    public class FpsserialnoAsync extends AsyncTask<Integer, Integer, String> {
        String iRetVal;

        // displays the progress dialog until background task is completed
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        // Task of CaptureFinger performing in the background
        @Override
        protected String doInBackground(Integer... params) {
            try {
                iRetVal = MainActivity.intFps.sGetFpsSensorSerialNumber();
                Log.e("API RETURN VALUES", "fps serial no ,:" + iRetVal);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        logText.setText("fps serial no is :" + iRetVal);
                    }
                });
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            return iRetVal;
        }
    }


    public class VerifymanynormAsync extends AsyncTask<Integer, Integer, Integer> {
        int iRetVal;
        byte[] bmpData = null;
        byte[] data1 = null;
        private ProgressDialog tmp = null;

        // displays the progress dialog until background task is completed
        @Override
        protected void onPreExecute() {
            img_bmp.setVisibility(View.INVISIBLE);
            img_jpeg.setVisibility(View.INVISIBLE);
            this.tmp = new ProgressDialog(FPSActivity.this);
            this.tmp.setMessage("Processing....");
            this.tmp.setCancelable(false);
            this.tmp.setCanceledOnTouchOutside(false);
            this.tmp.show();
            super.onPreExecute();
        }

        // Task of CaptureFinger performing in the background
        @Override
        protected Integer doInBackground(Integer... params) {
            try {
//                byte[] Morpho = HexString.hexToBuffer("0000000000000000000000000000000026007C7C535AFF058554000001000004FFFFFFFFFFFFFFFFFFFFFF00FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF0000000004C12E7F04B1359704AD44A20440497E04C84D8304C0598D14D85A5104AC5F9F044C667F04E1687304C4688B1469694D04606C68049C72AC04E8778904197DB204A97E9904F88285101D84920C19889214708E4C04F9916B049591990404957504F0975E0499A57B04A8AE8004A4AE961464B05914A8B3B4084DB46604B5B57F04A8B5A20849BC640CB4BFA108B0C19404B5C38C11C4D3940000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
              byte[][] multi= new byte[20][];//1:N usage for verify many minutiae use this and change fps instance to ifpsverifymanyminutiae;
//               For PKMATNORM TEMPLATE TYPE
                multi[0] = HexString.hexToBuffer("000000000000000000000000000000004300857F5D5AFF054654000001000004FFFFFFFFFFFFFFFFFFFFFF00FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF0000000018BD3D7E10393E7818314068181C419418B4428714B04470141C469D08294D8F149C4F9B08B551810CA5549410A4559F10C1566118555F5218405F6010C06E7204AC6E88104172680828738C04BC747C18AD788B08C8796A10307A8910A87B9114217BAF104C7C6014207F9F143C807818C1807E189C82AC145C835214C5847814DC8561142C8589100C85A1181886A818B0899014D9906814DC9072141590A6185C9668181897A4149198AA188D999E0CE49C6718E49C7B18889C9A18149EA618F1A38018FDAC7F0CF9AD711895ADA50CF8AE6A10F5B1621868B2581811B48C1879B67018F8B9601405BE531009BE741498BEAF147DBF571021BFA41490C28C1411C2931419C2981901C46E000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
                multi[1] = HexString.hexToBuffer("000000000000000000000000000000003C00867E605AFF056554000001000004FFFFFFFFFFFFFFFFFFFFFF00FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF0000000004493D6A04BC3D7F185D435B102447A718614B5804A84B96189D4DA608AD4FA108DC506708D0537A0821539D1074555314705B5310945BAF0C585D6F089C5DA210F05F5B08B95F8D10246295186C63520C3D65810CF1676A14A167990C6C735B0CEC756210217597107077660428799410447A7F10957AAF0C687B6A109D7BA40CA07C9C10DD7D7F145D7F78106583730C60857D10A4868F0CE1887E049D8EA60C618F7908A595A010E5987114B49E9914649F6D10B5A0A214C0A39A1064A55B1449A59410E0A6610C59A674105CAA6A0450AC7804C8B0A11854B16414B9B2AF1445B8A61058C26A0C51C27F1149C4A600000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
                multi[2] = HexString.hexToBuffer("000000000000000000000000000000004200827F5A5AFF056354000001000004FFFFFFFFFFFFFFFFFFFFFF00FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00000000102C338C182D36A40841376C04C13A7814293A9814A93B900448446804C0457E04D85167109D52B114695350141D5A9504645E5B0C9C619D189563B00C9D679408E069680CAD698A08596F710C68736118A074AE0C75755014E17667145D796014417B7914C97D7E14ED7E590C5D7E7114F18261148D85AE147486571049867614C188820CA589880C6D8D57186D8E6114F18F5D10E58F670C69946B149D948B109494A8141D978D109497A00C619B7414A89D850C459E7A0C9D9E8C149D9E930864A06C0471A1500C98A1A00465A7570439AA7B08E4AC5608B1AC851065B1570859B16510BDB1830C45B27814DCB661145DBB5B1458C15A0C54C7610CC4C77C1848CA6811B8CD9F00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
                multi[3] = HexString.hexToBuffer("0000000000000000000000000000000042008A7F575AFF053B54000001000004FFFFFFFFFFFFFFFFFFFFFF00FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF0000000018D03B6D1041447E10A94499141D47980CDC496410294B9414655659149957A414DC5863181058AC18785B4C14645B5D149C5E9B14955EAB14195F9A0C28648A149C64990C71654D149D6A9A18F96F5A14186F9414E97077180074B31871755D0CE8756E14757855148979AA10017C4F14907E9814F47F7714087F8B1411819A148D8689148D87A60CFC8866147589AF10798B4E180D8B9610F98CB41011939E10FC94691491949A1011957910919591148097AC149198A310F49C5D0C949D96089C9E90108C9E9D08ADA08D0C99A59C1471AA6018E8AA65186CAF651005B49B1448B66E14B9B7831064B8590C5DB8621448C0621445C0861458C15D0CD1C35F10CCC469154DC57200000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
                multi[4] = HexString.hexToBuffer("000000000000000000000000000000003000857F5B5AFF051A54000001000004FFFFFFFFFFFFFFFFFFFFFF00FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF0000000018D0497118394B7C18314C8A1469535618D8547018A9559318515A7010755B5118585C66149D5C9D143163871051656F18B86785149869A4180D6A9C185D6C6C18FC6C9418696D6118E06F6D14006F9A18FC755C140175A314757B8B18FD7B9D18FC7DA914F47E9714F07F881874878A18008C8114FC8CAF1880969B14F5979E147D98940CFD997010789D5114859E8418FD9F5618ECA1961879A1A91084A37E187CA4521870A5910C11A6711481A76D188DAD891495B28114E8B3A81571B7A000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
                multi[5] = HexString.hexToBuffer("000000000000000000000000000000004300857F5D5AFF054654000001000004FFFFFFFFFFFFFFFFFFFFFF00FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF0000000018BD3D7E10393E7818314068181C419418B4428714B04470141C469D08294D8F149C4F9B08B551810CA5549410A4559F10C1566118555F5218405F6010C06E7204AC6E88104172680828738C04BC747C18AD788B08C8796A10307A8910A87B9114217BAF104C7C6014207F9F143C807818C1807E189C82AC145C835214C5847814DC8561142C8589100C85A1181886A818B0899014D9906814DC9072141590A6185C9668181897A4149198AA188D999E0CE49C6718E49C7B18889C9A18149EA618F1A38018FDAC7F0CF9AD711895ADA50CF8AE6A10F5B1621868B2581811B48C1879B67018F8B9601405BE531009BE741498BEAF147DBF571021BFA41490C28C1411C2931419C2981901C46E000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
                multi[6] = HexString.hexToBuffer("000000000000000000000000000000003C00867E605AFF056554000001000004FFFFFFFFFFFFFFFFFFFFFF00FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF0000000004493D6A04BC3D7F185D435B102447A718614B5804A84B96189D4DA608AD4FA108DC506708D0537A0821539D1074555314705B5310945BAF0C585D6F089C5DA210F05F5B08B95F8D10246295186C63520C3D65810CF1676A14A167990C6C735B0CEC756210217597107077660428799410447A7F10957AAF0C687B6A109D7BA40CA07C9C10DD7D7F145D7F78106583730C60857D10A4868F0CE1887E049D8EA60C618F7908A595A010E5987114B49E9914649F6D10B5A0A214C0A39A1064A55B1449A59410E0A6610C59A674105CAA6A0450AC7804C8B0A11854B16414B9B2AF1445B8A61058C26A0C51C27F1149C4A600000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
                multi[7] = HexString.hexToBuffer("000000000000000000000000000000004200827F5A5AFF056354000001000004FFFFFFFFFFFFFFFFFFFFFF00FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00000000102C338C182D36A40841376C04C13A7814293A9814A93B900448446804C0457E04D85167109D52B114695350141D5A9504645E5B0C9C619D189563B00C9D679408E069680CAD698A08596F710C68736118A074AE0C75755014E17667145D796014417B7914C97D7E14ED7E590C5D7E7114F18261148D85AE147486571049867614C188820CA589880C6D8D57186D8E6114F18F5D10E58F670C69946B149D948B109494A8141D978D109497A00C619B7414A89D850C459E7A0C9D9E8C149D9E930864A06C0471A1500C98A1A00465A7570439AA7B08E4AC5608B1AC851065B1570859B16510BDB1830C45B27814DCB661145DBB5B1458C15A0C54C7610CC4C77C1848CA6811B8CD9F00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
                multi[8] = HexString.hexToBuffer("0000000000000000000000000000000042008A7F575AFF053B54000001000004FFFFFFFFFFFFFFFFFFFFFF00FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF0000000018D03B6D1041447E10A94499141D47980CDC496410294B9414655659149957A414DC5863181058AC18785B4C14645B5D149C5E9B14955EAB14195F9A0C28648A149C64990C71654D149D6A9A18F96F5A14186F9414E97077180074B31871755D0CE8756E14757855148979AA10017C4F14907E9814F47F7714087F8B1411819A148D8689148D87A60CFC8866147589AF10798B4E180D8B9610F98CB41011939E10FC94691491949A1011957910919591148097AC149198A310F49C5D0C949D96089C9E90108C9E9D08ADA08D0C99A59C1471AA6018E8AA65186CAF651005B49B1448B66E14B9B7831064B8590C5DB8621448C0621445C0861458C15D0CD1C35F10CCC469154DC57200000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
                multi[9] = HexString.hexToBuffer("000000000000000000000000000000003000857F5B5AFF051A54000001000004FFFFFFFFFFFFFFFFFFFFFF00FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF0000000018D0497118394B7C18314C8A1469535618D8547018A9559318515A7010755B5118585C66149D5C9D143163871051656F18B86785149869A4180D6A9C185D6C6C18FC6C9418696D6118E06F6D14006F9A18FC755C140175A314757B8B18FD7B9D18FC7DA914F47E9714F07F881874878A18008C8114FC8CAF1880969B14F5979E147D98940CFD997010789D5114859E8418FD9F5618ECA1961879A1A91084A37E187CA4521870A5910C11A6711481A76D188DAD891495B28114E8B3A81571B7A000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
                multi[10] = HexString.hexToBuffer("0000000000000000000000000000000042007F805C5AFF054954000001000004FFFFFFFFFFFFFFFFFFFFFF00FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF0000000014D0387714C93A9014453E88085144710CE04B6704D04B7214614F5C1044538E14F5585C146D595414BD599B14645A5D14615A6118415A8C14C05C92147960501030619E146C655C0CE165670C51657414306F981448717C14CD757A1878765414487A7704B97D8E14A47DA914718C5814558F7A0CD0907E1458926F042C948F1470955908A4959C0824979E1465986510749C55146C9C5C107DA15014E0A26A14B9A48B14ADA59414A1A6981020A79410C1AA831021AA9114DDAB71100CACAC1058AD711095B1A81064B2651414B3A11805B6930CE9B76410ACB78E0C68B85F0C8DBAAF14E5BE7E1015BE9B1405C08F1468C2661405C4931095C4A1108DC5B014E1C665158DC89900000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
                multi[11] = HexString.hexToBuffer("000000000000000000000000000000003A00827E5C5AFF052B54000001000004FFFFFFFFFFFFFFFFFFFFFF00FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF0000000014C9367A143D368C18A53DA11038407A189440AA14E8455D1825469618B048901821489F14544B6318584C5714B44E9314C15180182C528C141C539D14A1569C18605A5914305A81189D629310D16374189564AB10C5657F1010659A0C38697A18A06C8D10546D6B148D6EB110286F7F10946F9614AC70861035797514907AAA181C7B9614AD7D7E10E58063109C86A314A08884189C88971015889A107492521091939C149896B01471975D1094978810719F580C61A07310F5A150107CA15310A4A38C10A0A3A210C1A47F10ECA55C105CAA6E1468AB5B14A4AB9510E5AE5E189DB0AA1544CB73000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
                multi[12] = HexString.hexToBuffer("000000000000000000000000000000003A00827C575AFF053454000001000004FFFFFFFFFFFFFFFFFFFFFF00FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF000000001454337218A534A9102D3A9D14DC3B6914DD3B7410AC40A5105C436810D8437514684958085C496918E54A7514614E7414ED545B10C856891869585610695B6010715E590C595E7C0C6964610C616768107469510C216A9F10AC6C9D10D8728E181472B0149573AB0461767518747858185D7C8414687D6310797F5710D8818A10E9856B1869876710E8898A147D8B5414EC8D5E10E8918318E99688180997901068998110699B7B14F99C5910F19C8114089CA210719D6C181DA1851405A3AA14F9A67C1481B18218F9B27D1071B48A1805B574106CB99C18E9C2791469C29E1064C7901560CE86000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
                multi[13] = HexString.hexToBuffer("000000000000000000000000000000004200807F5A5AFF053A54000001000004FFFFFFFFFFFFFFFFFFFFFF00FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF0000000014C13190184934811441378D18853BA714C042971840458F14C14B8A148952AC148C55A2146556641461576E1440598C1444648D14F165A6147C67AA14616C5614446E85107D706E1475735010307480143874870C1976A0142978800C9C78A70C197A7310197CA210197DA714117F6F1019807A10FD897E14088A8E10158A93088C8AA614798B5904958D9C140591AF187D925014F8938D18FD965714F09B7A14749B830C859BB010F59E9408F89FAE10E9A0891470A27A0C7CA69A187CA7611069A7850CE8AA8D10E4AB8014E5AC881468AD6D14E0B1711465B373106CB58F1461BB6E1459BB861465BE8C0C61BFA40C58C19614CCC19E144DC26D0854C89E0CC9CAA01550CFA200000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
                multi[14] = HexString.hexToBuffer("0000000000000000000000000000000030007C7E5A5AFF051A54000001000004FFFFFFFFFFFFFFFFFFFFFF00FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF000000001865409618C0457D189C519218CC5678187157581861585D18415A79185D5C60183D5C731471615110CD62790C35628714B16390149863AF141465A4141C698E14996BA31868705E14497D7318207EB018157F92187480681880815A189184AC1809858B141D85A6109587961400885118808F5118788F630804916918FD9288100592980C7D946E0CF995661410959C147D978110799875147C9A5810FC9B8410019C701809A06714FDA662148CA66E1885A67F0C88AD811460C0861960C09000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
                multi[15] = HexString.hexToBuffer("0000000000000000000000000000000042007F805C5AFF054954000001000004FFFFFFFFFFFFFFFFFFFFFF00FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF0000000014D0387714C93A9014453E88085144710CE04B6704D04B7214614F5C1044538E14F5585C146D595414BD599B14645A5D14615A6118415A8C14C05C92147960501030619E146C655C0CE165670C51657414306F981448717C14CD757A1878765414487A7704B97D8E14A47DA914718C5814558F7A0CD0907E1458926F042C948F1470955908A4959C0824979E1465986510749C55146C9C5C107DA15014E0A26A14B9A48B14ADA59414A1A6981020A79410C1AA831021AA9114DDAB71100CACAC1058AD711095B1A81064B2651414B3A11805B6930CE9B76410ACB78E0C68B85F0C8DBAAF14E5BE7E1015BE9B1405C08F1468C2661405C4931095C4A1108DC5B014E1C665158DC89900000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
                multi[16] = HexString.hexToBuffer("000000000000000000000000000000003A00827E5C5AFF052B54000001000004FFFFFFFFFFFFFFFFFFFFFF00FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF0000000014C9367A143D368C18A53DA11038407A189440AA14E8455D1825469618B048901821489F14544B6318584C5714B44E9314C15180182C528C141C539D14A1569C18605A5914305A81189D629310D16374189564AB10C5657F1010659A0C38697A18A06C8D10546D6B148D6EB110286F7F10946F9614AC70861035797514907AAA181C7B9614AD7D7E10E58063109C86A314A08884189C88971015889A107492521091939C149896B01471975D1094978810719F580C61A07310F5A150107CA15310A4A38C10A0A3A210C1A47F10ECA55C105CAA6E1468AB5B14A4AB9510E5AE5E189DB0AA1544CB73000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
                multi[17] = HexString.hexToBuffer("000000000000000000000000000000003A00827C575AFF053454000001000004FFFFFFFFFFFFFFFFFFFFFF00FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF000000001454337218A534A9102D3A9D14DC3B6914DD3B7410AC40A5105C436810D8437514684958085C496918E54A7514614E7414ED545B10C856891869585610695B6010715E590C595E7C0C6964610C616768107469510C216A9F10AC6C9D10D8728E181472B0149573AB0461767518747858185D7C8414687D6310797F5710D8818A10E9856B1869876710E8898A147D8B5414EC8D5E10E8918318E99688180997901068998110699B7B14F99C5910F19C8114089CA210719D6C181DA1851405A3AA14F9A67C1481B18218F9B27D1071B48A1805B574106CB99C18E9C2791469C29E1064C7901560CE86000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
                multi[18] = HexString.hexToBuffer("000000000000000000000000000000004200807F5A5AFF053A54000001000004FFFFFFFFFFFFFFFFFFFFFF00FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF0000000014C13190184934811441378D18853BA714C042971840458F14C14B8A148952AC148C55A2146556641461576E1440598C1444648D14F165A6147C67AA14616C5614446E85107D706E1475735010307480143874870C1976A0142978800C9C78A70C197A7310197CA210197DA714117F6F1019807A10FD897E14088A8E10158A93088C8AA614798B5904958D9C140591AF187D925014F8938D18FD965714F09B7A14749B830C859BB010F59E9408F89FAE10E9A0891470A27A0C7CA69A187CA7611069A7850CE8AA8D10E4AB8014E5AC881468AD6D14E0B1711465B373106CB58F1461BB6E1459BB861465BE8C0C61BFA40C58C19614CCC19E144DC26D0854C89E0CC9CAA01550CFA200000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
                multi[19] = HexString.hexToBuffer("0000000000000000000000000000000030007C7E5A5AFF051A54000001000004FFFFFFFFFFFFFFFFFFFFFF00FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF000000001865409618C0457D189C519218CC5678187157581861585D18415A79185D5C60183D5C731471615110CD62790C35628714B16390149863AF141465A4141C698E14996BA31868705E14497D7318207EB018157F92187480681880815A189184AC1809858B141D85A6109587961400885118808F5118788F630804916918FD9288100592980C7D946E0CF995661410959C147D978110799875147C9A5810FC9B8410019C701809A06714FDA662148CA66E1885A67F0C88AD811460C0861960C09000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
//                multi[20] = HexString.hexToBuffer("000000000000000000000000000000004300857F5D5AFF054654000001000004FFFFFFFFFFFFFFFFFFFFFF00FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF0000000018BD3D7E10393E7818314068181C419418B4428714B04470141C469D08294D8F149C4F9B08B551810CA5549410A4559F10C1566118555F5218405F6010C06E7204AC6E88104172680828738C04BC747C18AD788B08C8796A10307A8910A87B9114217BAF104C7C6014207F9F143C807818C1807E189C82AC145C835214C5847814DC8561142C8589100C85A1181886A818B0899014D9906814DC9072141590A6185C9668181897A4149198AA188D999E0CE49C6718E49C7B18889C9A18149EA618F1A38018FDAC7F0CF9AD711895ADA50CF8AE6A10F5B1621868B2581811B48C1879B67018F8B9601405BE531009BE741498BEAF147DBF571021BFA41490C28C1411C2931419C2981901C46E000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
//                For PKCOMPNORM TEMPLATE TYPE

//                multi[0] = HexString.hexToBuffer("423900F25D5A867FC26EA68D439C7E9D40957B95BC88BE814D9F6A9C4A8F828985953F6A7A835E5F6C5975625D527A62895C7F529C7BB47192AEB9AFACA4B1ADB6904EA459A28C9C6FA4A6A14E9A569B7891829B6D8850827F84747B6C73485954606E60776B83688B738F7390699B67AC72928FAD6DB85FBB527E49926A35ADBFD71209AD8A66ED25FF");
//                multi[1] = HexString.hexToBuffer("523B00C75F5A8A8083946F915BA359A6619857AB3F77757DB5934966B38CBB726A6BB671665E92769C71B8659572AD67B3566D527658886581568D51916D8561595289AF67AD8998899F8BAD9BA2699E968975934194A59B56925F9CADADA996AD9D6F88B79BBFAC497CC19DB097B78F63745E616F5D9B6E845E6D567366BF9A1D672B4444FD0BD2C44475DD94FF");
//                multi[2] = HexString.hexToBuffer("623C00B15A5A7C7E7EA77FB1639369B1A6A14BA33589B47B4268BC795066C6797A6FCC5A88716759A673BD68C65BA96AB555BD5756519F6A9D5793579B64AD58AE51815089AA6CAE9AAE9EA8A1A0449E5CAF6B9D7395A08CA6A7A78CB2984B9D958944897389B5844674517CC482BB82947F5D65C061B757985DA9588756B59BDBB1BE96538814FA2585C8DCE8AB4DFD");
//                multi[3] = HexString.hexToBuffer("4249011B595A8B807B775DA36AAA90726F957D93848E8E9B7984459A9BA1A39941928F778C8541776771B56CBB62BA5BAF60AE595B56A25C756E705A965DA250AF6C765A80A780B190635E9F6EA6837E89A7949754A562957FA08091838A8B9498A78E8E91A294934F9B5C954F935498958D9787B3A03F92A585AF81B87AC177BB664660A86BA4546A6D6F6B7D5897639167A66F776C79728663D2B0C89EBBB514855302226955DD761589FF");
//                multi[4] = HexString.hexToBuffer("123000FD595A8F7D659D9E78A4809E804C8C50744977586F5D6F656977734D58847B9C999E63796681A08F9F56516F8D7F968466888C8C739151A45DB7A6719A9E90B262A28949895775708076916C927E7A8FA7996975977C6388858AAF9774856F8955907AB593B1FFF79B668800C6FDFF7D5B2884");
//                multi[5] = HexString.hexToBuffer("423900F25D5A867FC26EA68D439C7E9D40957B95BC88BE814D9F6A9C4A8F828985953F6A7A835E5F6C5975625D527A62895C7F529C7BB47192AEB9AFACA4B1ADB6904EA459A28C9C6FA4A6A14E9A569B7891829B6D8850827F84747B6C73485954606E60776B83688B738F7390699B67AC72928FAD6DB85FBB527E49926A35ADBFD71209AD8A66ED25FF");
//                multi[6] = HexString.hexToBuffer("523B00C75F5A8A8083946F915BA359A6619857AB3F77757DB5934966B38CBB726A6BB671665E92769C71B8659572AD67B3566D527658886581568D51916D8561595289AF67AD8998899F8BAD9BA2699E968975934194A59B56925F9CADADA996AD9D6F88B79BBFAC497CC19DB097B78F63745E616F5D9B6E845E6D567366BF9A1D672B4444FD0BD2C44475DD94FF");
//                multi[7] = HexString.hexToBuffer("623C00B15A5A7C7E7EA77FB1639369B1A6A14BA33589B47B4268BC795066C6797A6FCC5A88716759A673BD68C65BA96AB555BD5756519F6A9D5793579B64AD58AE51815089AA6CAE9AAE9EA8A1A0449E5CAF6B9D7395A08CA6A7A78CB2984B9D958944897389B5844674517CC482BB82947F5D65C061B757985DA9588756B59BDBB1BE96538814FA2585C8DCE8AB4DFD");
//                multi[8] = HexString.hexToBuffer("4249011B595A8B807B775DA36AAA90726F957D93848E8E9B7984459A9BA1A39941928F778C8541776771B56CBB62BA5BAF60AE595B56A25C756E705A965DA250AF6C765A80A780B190635E9F6EA6837E89A7949754A562957FA08091838A8B9498A78E8E91A294934F9B5C954F935498958D9787B3A03F92A585AF81B87AC177BB664660A86BA4546A6D6F6B7D5897639167A66F776C79728663D2B0C89EBBB514855302226955DD761589FF");
//                multi[9] = HexString.hexToBuffer("123000FD595A8F7D659D9E78A4809E804C8C50744977586F5D6F656977734D58847B9C999E63796681A08F9F56516F8D7F968466888C8C739151A45DB7A6719A9E90B262A28949895775708076916C927E7A8FA7996975977C6388858AAF9774856F8955907AB593B1FFF79B668800C6FDFF7D5B2884");
//                multi[10] = HexString.hexToBuffer("3245015F5C5A807FC1B0AAA4A6AE8D9E9F9A58AA6DA486954899477473865E816A743D7A8583B77F3B6C3D726A6E947F595BB27AC084A872AC5AAF72776C7E72B76AC98A55568B5EA658B87ABD5C4D50746A9C51896AACA0B4A1B7A99BA572A07F993778447C4E674B6D5B78808F977FC993777DAC9360666959C3898665B7657E609562B55A9D65A1566B678A68A764BFAAFD75AB9AA4101182D2FF66DBA7209148F4");
//                multi[11] = HexString.hexToBuffer("223B01085B5A767F3EB085A5649A6B997CA950916E8E8083478F7D80407D627A5776C26AB8624C6855625B5661684E545F5C6E667371AE6B5653B05D625089658A5CA652725085578E509B519A7B7951A8B18CA5739F9492649F848F779470947886547C657E56685E7957725A5C635C68696F69A3627F6B7254947A43ADBD55BCB24A060945BB55EAB74D2AB5FD");
//                multi[12] = HexString.hexToBuffer("323300E9595A7A7F7FB0918C6CAB6EA490948193529A61993D9C39785E7D7672466A6A674D5E5D6164627C64AC61AC78C27F9C6CA381625769508054B185575173549055B67F9F5A88AC90A5709C5E986496808F8569927E95839887C878625491899F88AC8F985EA97FB07BBA7A75DAEC3F4D8184D07BD7FBFE4888FE");
//                multi[13] = HexString.hexToBuffer("223900F5585A7A8282AC78A96E9C689C687B688354915D815176635AB966BAA9BD74BD9CC279B5A463536F60A776B068B38DAE81B29EB473B4795E529C888A819176A88F82599A9497A27BA83BAF7E9F7FAF45A84C97C0AAC67AB262B36FB493B880A36A706AA680A9786760928CA090999A8A749195868F91AEBB6BD73DA02054D4AE7CDF73689426FF");
//                multi[14] = HexString.hexToBuffer("123001065E5A777F8D8F96AA8A9996996A5E7D9379A5699962A058A25E8E4377438EBDA6C572B8A5B97E4C6CB3A7AEA1AA9AAB88B289525FA28682668B52A69596A88D964CAF4CA67BA35DA955A25D9B3C98529373885B8E42835683B777B693AD8BA28B8A58A3AFC959ED466945D77C95EBA2D8EBCF");
//                multi[15] = HexString.hexToBuffer("3245015F5C5A807FC1B0AAA4A6AE8D9E9F9A58AA6DA486954899477473865E816A743D7A8583B77F3B6C3D726A6E947F595BB27AC084A872AC5AAF72776C7E72B76AC98A55568B5EA658B87ABD5C4D50746A9C51896AACA0B4A1B7A99BA572A07F993778447C4E674B6D5B78808F977FC993777DAC9360666959C3898665B7657E609562B55A9D65A1566B678A68A764BFAAFD75AB9AA4101182D2FF66DBA7209148F4");
//                multi[16] = HexString.hexToBuffer("223B01085B5A767F3EB085A5649A6B997CA950916E8E8083478F7D80407D627A5776C26AB8624C6855625B5661684E545F5C6E667371AE6B5653B05D625089658A5CA652725085578E509B519A7B7951A8B18CA5739F9492649F848F779470947886547C657E56685E7957725A5C635C68696F69A3627F6B7254947A43ADBD55BCB24A060945BB55EAB74D2AB5FD");
//                multi[17] = HexString.hexToBuffer("323300E9595A7A7F7FB0918C6CAB6EA490948193529A61993D9C39785E7D7672466A6A674D5E5D6164627C64AC61AC78C27F9C6CA381625769508054B185575173549055B67F9F5A88AC90A5709C5E986496808F8569927E95839887C878625491899F88AC8F985EA97FB07BBA7A75DAEC3F4D8184D07BD7FBFE4888FE");
//                multi[18] = HexString.hexToBuffer("223900F5585A7A8282AC78A96E9C689C687B688354915D815176635AB966BAA9BD74BD9CC279B5A463536F60A776B068B38DAE81B29EB473B4795E529C888A819176A88F82599A9497A27BA83BAF7E9F7FAF45A84C97C0AAC67AB262B36FB493B880A36A706AA680A9786760928CA090999A8A749195868F91AEBB6BD73DA02054D4AE7CDF73689426FF");
//                multi[19] = HexString.hexToBuffer("123001065E5A777F8D8F96AA8A9996996A5E7D9379A5699962A058A25E8E4377438EBDA6C572B8A5B97E4C6CB3A7AEA1AA9AAB88B289525FA28682668B52A69596A88D964CAF4CA67BA35DA955A25D9B3C98529373885B8E42835683B777B693AD8BA28B8A58A3AFC959ED466945D77C95EBA2D8EBCF");


                MainActivity.intFps.setVerificationImageReturn(true);
                iRetVal = MainActivity.intFps.iFpsVerifyManyNorm(multi, glbFpsConfig);
                Log.e("Verify minutiae return value", "ifpsverifyminutiae,:" + iRetVal);
                iRetVal = MainActivity.intFps.iGetReturnCode();
                Log.e("bGetImageData RETURN VALUE", "Igetreturncodeis,:" + iRetVal);
                if (iRetVal == FPS.SUCCESS) {
                    byte[] data = MainActivity.intFps.bGetImageData();
                    Log.e("bGetImageData RETURN VALUE", "iRetValue,:" + MainActivity.intFps.iGetReturnCode());
                    Log.e("bGetImageData : ", "bGetimagedatais " + com.mydevice.sdk.HexString.bufferToHex(data));
                    data1 = MainActivity.intFps.bGetJpgImageData();
                    Log.e("bGetJpegImageData RETURN VALUE", "iRetValue,:" + MainActivity.intFps.iGetReturnCode());
                    Log.e("bGetJpegData : ", "bGetJpegDatais  " + com.mydevice.sdk.HexString.bufferToHex(data1));
                    bmpData = com.mydevice.sdk.FpsImageAPI.bConvertRaw2bmp(data);
                    Log.e("bConvertRaw2bmp RETURN VALUE", "iRetValue,:" + MainActivity.intFps.iGetReturnCode());
                    Log.e("bconvertRaw2BmpData : ", "bconvertRaw2BmpDatais" + com.mydevice.sdk.HexString.bufferToHex(bmpData));
                    Log.e("Capture Task", "Verified Minutiae data is ,:" + iRetVal);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            logText.setText("bGetImageData RETURN VALUE,:" + MainActivity.intFps.iGetReturnCode() + "\n"
//                                  + "bGetimagedatais " + com.mydevice.sdk.HexString.bufferToHex(data) + "\n"
                                    + "bGetJpegImageData RETURN VALUE,:" + MainActivity.intFps.iGetReturnCode() + "\n"
//                                  + "bGetJpegData is  " + com.mydevice.sdk.HexString.bufferToHex(data1) + "\n"
                                    + "bConvertRaw2bmp RETURN VALUE,:" + MainActivity.intFps.iGetReturnCode() + "\n"
                                    + "Verified Minutiae data is ,:" + iRetVal);
//                                    + "bconvertRaw2BmpData is" + com.mydevice.sdk.HexString.bufferToHex(bmpData) );
                        }
                    });


                } else {
                    byte[] data = MainActivity.intFps.bGetImageData();
                    Log.e("bGetImageData RETURN VALUE", "iRetValue,:" + MainActivity.intFps.iGetReturnCode());
                    Log.e("bGetImageData : ", "bGetimagedatais  " + (data));
                    data1 = MainActivity.intFps.bGetJpgImageData();
                    Log.e("bGetJpegImageData RETURN VALUE", "iRetValue,:" + MainActivity.intFps.iGetReturnCode());
                    Log.e("bGetJpegData : ", "bGetJpegData" + (data1));
                    bmpData = com.mydevice.sdk.FpsImageAPI.bConvertRaw2bmp(data);
                    Log.e("bConvertRaw2bmp RETURN VALUE", "iRetValue,:" + MainActivity.intFps.iGetReturnCode());
                    Log.e("bconvertRaw2Bmp: ", "bconvertRaw2Bmpdatais" + (bmpData));
                    Log.e("Capture Task", "Verified Minutiae data is ,:" + iRetVal);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            logText.setText("bGetImageData RETURN VALUE,:" + MainActivity.intFps.iGetReturnCode() + "\n"
                                    + "bGetimagedatais " + com.mydevice.sdk.HexString.bufferToHex(data) + "\n"
                                    + "bGetJpegImageData RETURN VALUE,:" + MainActivity.intFps.iGetReturnCode() + "\n"
                                    + "bGetJpegData is  " + com.mydevice.sdk.HexString.bufferToHex(data1) + "\n"
                                    + "bConvertRaw2bmp RETURN VALUE,:" + MainActivity.intFps.iGetReturnCode() + "\n"
                                    + "bconvertRaw2BmpData is" + com.mydevice.sdk.HexString.bufferToHex(bmpData) + "\n"
                                    + "Verified Minutiae data is ,:" + iRetVal);
                        }
                    });

                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            return iRetVal;
        }

        //* This function sends message to handler to display the status messages of Diagnose in the dialog box
        @Override
        protected void onPostExecute(Integer result) {
            this.tmp.dismiss();
            Log.e("Capture Task ", "LEOPARD FPS Capture onPostExecute Val" + iRetVal);
            if (iRetVal == -5) {
                img_bmp.setVisibility(View.VISIBLE);
                img_jpeg.setVisibility(View.VISIBLE);
                try {
                    Bitmap bmpfinalnew = BitmapFactory.decodeByteArray(bmpData, 0, bmpData.length);
                    img_bmp.setImageBitmap(bmpfinalnew);
                    Bitmap bmpfinalnew1 = BitmapFactory.decodeByteArray(data1, 0, data1.length);
                    img_jpeg.setImageBitmap(bmpfinalnew1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (iRetVal == FPS.SUCCESS) {
                    Toast.makeText(FPSActivity.this, "Capture Success", Toast.LENGTH_SHORT).show();
                } else if (iRetVal == FPS.FPS_INACTIVE_PERIPHERAL) {
                    Toast.makeText(FPSActivity.this, "Inactive Peripheral", Toast.LENGTH_SHORT).show();
                } else if (iRetVal == FPS.TIME_OUT) {
                    Toast.makeText(FPSActivity.this, "Capture Time Out", Toast.LENGTH_SHORT).show();
                } else if (iRetVal == FPS.FAILURE) {
                    Toast.makeText(FPSActivity.this, "Capture Failure", Toast.LENGTH_SHORT).show();
                } else if (iRetVal == FPS.PARAMETER_ERROR) {
                    Toast.makeText(FPSActivity.this, "Parameter Error", Toast.LENGTH_SHORT).show();
                } else if (iRetVal == FPS.FPS_INVALID_DEVICE_ID) {
                    Toast.makeText(FPSActivity.this, "Invalid Device ID", Toast.LENGTH_SHORT).show();
                } else if (iRetVal == FPS.FPS_ILLEGAL_LIBRARY) {
                    Toast.makeText(FPSActivity.this, "Illegal Library", Toast.LENGTH_SHORT).show();
                } else if (iRetVal == FPS.NO_RESPONSE) {
                    Toast.makeText(FPSActivity.this, "No Response", Toast.LENGTH_SHORT).show();
                } else if (iRetVal == FPS.FPS_FFD) {
                    Toast.makeText(FPSActivity.this, "Fake Finger Detection", Toast.LENGTH_SHORT).show();
                } else if (iRetVal == FPS.INVALID_OPERATION || iRetVal == FPS.FPS_ERROR) {
                    Toast.makeText(FPSActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
                super.onPostExecute(result);
            }
        }

    }
}










