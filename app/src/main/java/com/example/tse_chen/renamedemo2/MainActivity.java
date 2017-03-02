package com.example.tse_chen.renamedemo2;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.v4.provider.DocumentFile;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import java.io.File;

public class MainActivity extends AppCompatActivity {
    public String TAG = "DIT_TUE";
    public File file;
    public Button Btn_tue, Btn_Camera;
    public String Str_MainTUE, Str_SubTUE, Str_TUEphone, Str_Group, Str_K, Str_Lux;
    public String getStr_MainTUE, getStr_SubTUE, getStr_TUEphone, getStr_Group, getStr_K, getStr_Lux;
    public Intent intent;
    public DocumentFile pickedDoc;
    public Spinner SPMain, SPSub;
    public EditText phoneModelEdit, KEdit, LuxEdit, GroupEdit;
    public String[] SA_TUEItem;
    public String[][] SAA_mTUEItem, SAA_oTUEItem;
    public String[] SA_mTUESubItem;
    public String[] SA_oriTUESubItem;
    public String Str_getNewItem;
    public int ImaOrMov, getImaOrMov, getposition, getposition_2;
    public boolean repeat;

    private String temp;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        final Context context = this.getApplicationContext();
        if (savedInstanceState != null) {
            temp = savedInstanceState.getString("temp");
            System.out.println("onCreate: temp = " + temp);
        }

        arraysInit();


        SPMain.setAdapter(new SpinnerAdapter(this, SA_TUEItem));
        SPMain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Str_MainTUE = "_00" + (position + 1);
                SA_oriTUESubItem = SAA_oTUEItem[position];
                SA_mTUESubItem = SAA_mTUEItem[position];
                SPSub.setAdapter(new SubSpinnerAdapter(context, SAA_mTUEItem[position]));
                SPSub.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        getposition = position;
                        if (SA_mTUESubItem[position].startsWith("(錄影)") || SA_mTUESubItem[position].startsWith("*(錄影)")) {
                            ImaOrMov = 0;
                        } else {
                            ImaOrMov = 1;
                        }
                        if (position < 10) {
                            Str_SubTUE = "_00" + (position);
                        } else {
                            Str_SubTUE = "_0" + (position);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.e("TAG", "error");
            }
        });

        intent = new Intent();
        Btn_tue.setOnClickListener(btn_clicklistener);
        Btn_Camera.setOnClickListener(btn_clicklistener);

    }


    private Button.OnClickListener btn_clicklistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.tue:
                    Str_TUEphone = phoneModelEdit.getText().toString();
                    Str_Group = GroupEdit.getText().toString();
                    Str_K = KEdit.getText().toString();
                    Str_Lux = LuxEdit.getText().toString();
                    System.out.println(Str_TUEphone);
                    Log.v(TAG, "test have phone model?");
                    if (phoneModelEdit.getText().toString().trim().equals("") || GroupEdit.getText().toString().trim().equals("") ||
                            KEdit.getText().toString().trim().equals("") || LuxEdit.getText().toString().trim().equals("")) {
                        new AlertDialog.Builder(MainActivity.this).setTitle("TUE").setMessage("專案及分組及色溫照度不可為空")
                                .setPositiveButton("確定", null).show();
                    } else {
                        if (SA_mTUESubItem[getposition].startsWith("*")) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this).setTitle("TUE")
                                    .setMessage("已存在此場景的照片是否繼續?");
                            DialogInterface.OnClickListener clickListener = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case Dialog.BUTTON_POSITIVE:
                                            intentData();
                                            break;
                                        case Dialog.BUTTON_NEGATIVE:
                                            break;
                                    }
                                }
                            };
                            builder.setPositiveButton("確定", clickListener);
                            builder.setNegativeButton("取消", clickListener);
                            builder.show();
                        } else {
                            intentData();
                        }
                    }
                    break;
                case R.id.openCamera:
                    Intent CameraIntent = new Intent();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                    CameraIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE_SECURE);
                    File file = new File(Environment.getExternalStorageDirectory(), "jpeg.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, file);
                    startActivityForResult(CameraIntent, 1);
                    break;
            }
        }
    };

    private void arraysInit(){
        SA_TUEItem = getResources().getStringArray(R.array.aMainTUE);
        SAA_mTUEItem = new String[][]{
                getResources().getStringArray(R.array.SubTUE01),
                getResources().getStringArray(R.array.SubTUE02),
                getResources().getStringArray(R.array.SubTUE03),
                getResources().getStringArray(R.array.SubTUE04),
                getResources().getStringArray(R.array.SubTUE05),
                getResources().getStringArray(R.array.SubTUE06),
                getResources().getStringArray(R.array.SubTUE07)
        };
        SAA_oTUEItem = new String[][]{
                getResources().getStringArray(R.array.SubTUE01),
                getResources().getStringArray(R.array.SubTUE02),
                getResources().getStringArray(R.array.SubTUE03),
                getResources().getStringArray(R.array.SubTUE04),
                getResources().getStringArray(R.array.SubTUE05),
                getResources().getStringArray(R.array.SubTUE06),
                getResources().getStringArray(R.array.SubTUE07)
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK && requestCode == 0) {

            getStr_MainTUE = intent.getStringExtra("TUE");
            getStr_SubTUE = intent.getStringExtra("subTUE");
            getStr_TUEphone = intent.getStringExtra("phone");
            getImaOrMov = intent.getIntExtra("jpgORmp4", 1);
            getposition_2 = intent.getIntExtra("position", 99);
            getStr_Group = intent.getStringExtra("group");
            getStr_K = intent.getStringExtra("K");
            getStr_Lux = intent.getStringExtra("Lux");
            Uri uriTree = data.getData();
            //System.out.println(uriTree);
            pickedDoc = DocumentFile.fromTreeUri(this, uriTree);
            for (DocumentFile file : pickedDoc.listFiles()) {

                if (getImaOrMov == 1) {
                    for (int i = 1; i <= pickedDoc.listFiles().length; i++) {
                        if (file.getName().endsWith(".jpg")) {
                            reNameJPG(i, file);
                        } else if (file.getName().endsWith(".JPG")) {
                            reNameJPG(i, file);
                        } else {
                            Log.v(TAG, "not found jpg");
                        }
                        if (file.getName().startsWith("TUE" + "_" + getStr_Group + "_" + getStr_TUEphone + getStr_MainTUE + getStr_SubTUE)) {
                            repeat = true;
                            Log.v(TAG, "repeat is true");
                        } else {
                            Log.v(TAG, "repeat is false");
                        }
                    }
                } else if (getImaOrMov == 0) {
                    for (int i = 1; i <= pickedDoc.listFiles().length; i++) {
                        if (file.getName().endsWith(".mp4")) {
                            reNamerMP4(i, file);
                        } else {
                            Log.v(TAG, "not found mp4");
                        }
                        if (file.getName().startsWith("TUE" + "_" + getStr_Group + "_" + getStr_TUEphone + getStr_MainTUE + getStr_SubTUE)) {
                            repeat = true;
                            Log.v(TAG, "repeat is true");
                        } else {
                            Log.v(TAG, "repeat is false");
                        }
                    }
                }
            }
            changeItem(repeat);
        }

    }

    public void reNameJPG(int i, DocumentFile file){
        if (file.getName().startsWith("TUE")) {
            Log.v(TAG, "Have TUE");
        } else {
            if (i < 10) {
                boolean re = file.renameTo(
                        "TUE" + "_" + getStr_Group + "_" + getStr_TUEphone + getStr_MainTUE + getStr_SubTUE + "_00" + i + "(" + Str_Lux + "lux" + "," + Str_K + "k" + ")" + ".jpg");
                Log.v(TAG, "have rename" + re);
            } else {
                boolean re = file.renameTo(
                        "TUE" + "_" + getStr_Group + "_" + getStr_TUEphone + getStr_MainTUE + getStr_SubTUE + "_0" + i + "(" + Str_Lux + "lux" + "," + Str_K + "k" + ")" + ".jpg");
                Log.v(TAG, "have rename" + re);
            }
        }
        Log.v(TAG, "found jpg");
    }

    public void reNamerMP4(int i, DocumentFile file){
        if (file.getName().startsWith("TUE")) {
            Log.v(TAG, "Have TUE Name");
        } else {
            if (i < 10) {
                boolean re = file.renameTo(
                        "TUE" + "_" + getStr_Group + "_" + getStr_TUEphone + getStr_MainTUE + getStr_SubTUE + "_00" + i + "(" + Str_Lux + "lux" + "," + Str_K + "k" + ")" + ".mp4");
                Log.v(TAG, "have rename" + re);
            } else {
                boolean re = file.renameTo(
                        "TUE" + "_" + getStr_Group + "_" + getStr_TUEphone + getStr_MainTUE + getStr_SubTUE + "_0" + i + "(" + Str_Lux + "lux" + "," + Str_K + "k" + ")" + ".mp4");
                Log.v(TAG, "have rename" + re);
            }
        }
        Log.v(TAG, "found mp4");
    }

    public boolean changeItem(boolean repeatItem){
        if (repeatItem) {
            if (SA_mTUESubItem[getposition_2].startsWith("*")) {
                repeat = false;
            } else {
                Str_getNewItem = "*" + SA_mTUESubItem[getposition_2];
                SA_mTUESubItem[getposition_2] = Str_getNewItem;
                repeat = false;
                Log.v(TAG, "change String[]");
            }
        } else {
            Str_getNewItem = SA_oriTUESubItem[getposition_2];
            SA_mTUESubItem[getposition_2] = Str_getNewItem;
        }
        return repeat;
    }

    public void init() {
        Btn_tue = (Button) findViewById(R.id.tue);
        Btn_Camera = (Button) findViewById(R.id.openCamera);
        phoneModelEdit = (EditText) findViewById(R.id.editText);
        SPMain = (Spinner) findViewById(R.id.MainSP);
        SPSub = (Spinner) findViewById(R.id.SubSP);
        KEdit = (EditText) findViewById(R.id.Ktext);
        LuxEdit = (EditText) findViewById(R.id.LuxText);
        GroupEdit = (EditText) findViewById(R.id.GroupText);


        KEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    KEdit.setText("");
                }
            }
        });
        LuxEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    LuxEdit.setText("");
                }
            }
        });
    }

    public void intentData() {
        intent = new Intent();
        intent.putExtra("TUE", Str_MainTUE);
        intent.putExtra("subTUE", Str_SubTUE);
        intent.putExtra("phone", Str_TUEphone);
        intent.putExtra("jpgORmp4", ImaOrMov);
        intent.putExtra("get", Str_getNewItem);
        intent.putExtra("position", getposition);
        intent.putExtra("group", Str_Group);
        intent.putExtra("K", Str_K);
        intent.putExtra("Lux", Str_Lux);
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT_TREE);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        temp = "xing";
        // 切换屏幕方向会导致activity的摧毁和重建
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            System.out.println("屏幕切换");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putString("temp", temp);
    }
}
