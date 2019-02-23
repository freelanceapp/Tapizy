package infobite.com.tapizy.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import infobite.com.tapizy.R;
import infobite.com.tapizy.constant.Constant;
import infobite.com.tapizy.model.login_data_modal.UserData;
import infobite.com.tapizy.model.login_data_modal.UserDataMainModal;
import infobite.com.tapizy.retrofit_provider.RetrofitService;
import infobite.com.tapizy.retrofit_provider.WebResponse;
import infobite.com.tapizy.utils.Alerts;
import infobite.com.tapizy.utils.AppPreference;
import infobite.com.tapizy.utils.BaseActivity;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class CreateProfileActivity extends BaseActivity implements View.OnClickListener {

    private static int LOAD_IMAGE_GALLERY = 123;
    private static int PICK_IMAGE_CAMERA = 124;
    private static int PERMISSION_REQUEST_CODE = 456;
    private Bitmap bitmap;
    private File destination = null;
    private InputStream inputStreamImg;
    private String imgPath = null;
    private EditText etName,etMail,username;
    private CheckBox cbChatbot,cbBot;
    private RadioGroup radioGroup;
    private RadioButton  radioButton;
    private String strPhone,strUseId;
    private String strgenderValue, strGender,strBot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
        init();

        if (checkPermission()) {
            Alerts.show(mContext, "Permission granted");
        } else {
            requestPermission();
        }
    }

    private void init() {
        ((ImageView) findViewById(R.id.user_profile)).setOnClickListener(this);
        ((Button) findViewById(R.id.btn_create_profile)).setOnClickListener(this);

        etName = findViewById(R.id.user_name);
        etMail = findViewById(R.id.user_email);
        username = findViewById(R.id.user_username);
        selectGender();
        selectBot();
        getIntentData();
    }
    private void getIntentData(){
        Intent intent = getIntent();
        strPhone = intent.getStringExtra("phone");
         strUseId = intent.getStringExtra("uid");
    }
    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            Alerts.show(mContext, "Permission not granted");
            return false;
        }
        return true;
    }
    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE);
    }
    private void selectImage() {
        try {
            PackageManager pm = getPackageManager();
            int permission = pm.checkPermission(Manifest.permission.CAMERA, getPackageName());
            if (permission == PackageManager.PERMISSION_GRANTED) {
                final CharSequence[] choose = {
                        "Pick From Camera"
                        , "Choose From Gallery"
                        , "Cancel"};
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(mContext);
                builder.setTitle("Select Option");
                builder.setItems(choose, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (choose[which].equals("Pick From Camera")) {
                            dialog.dismiss();
                            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, PICK_IMAGE_CAMERA);
                        } else if (choose[which].equals("Choose From Gallery")) {
                            dialog.dismiss();
                            Intent i = new Intent(
                                    Intent.ACTION_PICK,
                                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(i, LOAD_IMAGE_GALLERY);
                        } else if (choose[which].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            } else
                Toast.makeText(this, "Camera Permission error", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Camera Permission error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    private void selectBot(){
        cbBot = (CheckBox) findViewById(R.id.cb_chatbox_confirm);
        cbBot.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                strBot = "1";
                }else {
                    strBot = "0";
                }
            }
        });
    }
    private void selectGender(){
        radioGroup = findViewById(R.id.rg_select_gender);
        radioGroup.setOnCheckedChangeListener((new RadioGroup.OnCheckedChangeListener() {

            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    strGender = rb.getText().toString();
                }
            }
        }));
    }
    private void updateApi(){
        String strName = etName.getText().toString();
        String strUserName = username.getText().toString();
        String strMail = etMail.getText().toString();
        // String strCity = etCity.getText().toString();

        if (etName.getText().toString().length() == 0){
            etName.setError("Name Required");
        }else if (etMail.getText().toString().length() == 0 ){
            etMail.setError("Number Required");
        }else {
            if (cd.isNetworkAvailable()){
                RetrofitService.updateData(new Dialog(mContext), retrofitApiClient.updateProfile(strPhone, strUserName,strGender,
                        "", "",strBot, strUseId, strName, strMail), new WebResponse() {
                    @Override
                    public void onResponseSuccess(Response<?> result) {
                        ResponseBody responseBody = (ResponseBody) result.body();

                        try {
                            JSONObject jsonObject = new JSONObject(responseBody.string());
                            if (!jsonObject.getBoolean("error")) {
                                Alerts.show(mContext, jsonObject.getString("message"));
                                startActivity(new Intent(mContext,HomeActivity.class));
                                 finish();
                            }else {
                                Alerts.show(mContext,jsonObject.getString("message"));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onResponseFailed(String error) {
                        Alerts.show(mContext,error);
                    }
                });
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        inputStreamImg = null;
        if (requestCode == PICK_IMAGE_CAMERA) {
            try {
                Uri selectedImage = data.getData();
                bitmap = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);

                Log.e("Activity", "Pick from Camera::>>> ");

                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                destination = new File(Environment.getExternalStorageDirectory() + "/" +
                        getString(R.string.app_name), "IMG_" + timeStamp + ".jpg");
                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                imgPath = destination.getAbsolutePath();
                ((ImageView) findViewById(R.id.user_profile)).setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == LOAD_IMAGE_GALLERY && resultCode == RESULT_OK && null != data) {
            final Uri uriImage = data.getData();
            final InputStream inputStream;
            try {
                inputStream = getContentResolver().openInputStream(uriImage);
                final Bitmap imageMap = BitmapFactory.decodeStream(inputStream);
                ((ImageView)findViewById(R.id.user_profile)).setImageBitmap(imageMap);

            } catch (FileNotFoundException e) {
                Toast.makeText(this, "Image not found", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_profile:
                selectImage();
                break;
            case R.id.btn_create_profile:
                selectGender();
                updateApi();
        //    createProfile();
                break;
        }
    }
}
