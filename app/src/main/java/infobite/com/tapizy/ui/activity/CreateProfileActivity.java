package infobite.com.tapizy.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

import de.hdodenhof.circleimageview.CircleImageView;
import infobite.com.tapizy.R;
import infobite.com.tapizy.constant.Constant;
import infobite.com.tapizy.model.User;
import infobite.com.tapizy.model.login_data_modal.UserDataMainModal;
import infobite.com.tapizy.retrofit_provider.RetrofitService;
import infobite.com.tapizy.retrofit_provider.WebResponse;
import infobite.com.tapizy.utils.Alerts;
import infobite.com.tapizy.utils.AppPreference;
import infobite.com.tapizy.utils.BaseActivity;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class CreateProfileActivity extends BaseActivity implements View.OnClickListener {

    private static int LOAD_IMAGE_GALLERY = 123;
    private static int PICK_IMAGE_CAMERA = 124;
    private static int PERMISSION_REQUEST_CODE = 456;
    private Bitmap bitmap,imageMap;
    private File destination = null;
    private InputStream inputStreamImg;
    private String imgPath = null,imgPath1,imagePath2;
    private EditText etName, etMail, username;
    private CheckBox cbChatbot, cbBot;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private String strPhone, strUserId,strFrom;
    private String  strGender, strBot;
    private File imageFile;


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
        ((CircleImageView) findViewById(R.id.iv_user_profile)).setOnClickListener(this);
        ((Button) findViewById(R.id.btn_create_profile)).setOnClickListener(this);

        etName = findViewById(R.id.tv_user_name);
        etMail = findViewById(R.id.tv_user_email);
        username = findViewById(R.id.tv_user_username);
        getIntentData();
        selectGender();
        selectBot();
        setUserData();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        strFrom = intent.getStringExtra("from");
        strUserId = intent.getStringExtra("uid");
        strPhone = intent.getStringExtra("phone");
    }

    private void setUserData(){
        if (strFrom.equalsIgnoreCase("myProfile")){

            Glide.with(mContext)
                    .load(Constant.PROFILE_IMAGE_BASE_URL + User.getUser().getUser().getUProfile())
                    .into((CircleImageView)findViewById(R.id.iv_user_profile));
            ((EditText)findViewById(R.id.tv_user_username)).setText(User.getUser().getUser().getUUsername());
            ((TextView)findViewById(R.id.tv_user_name)).setText(User.getUser().getUser().getUName());
            ((TextView)findViewById(R.id.tv_user_email)).setText(User.getUser().getUser().getUEmail());

            if (User.getUser().getUser().getUGender().equalsIgnoreCase("male")){
                ((RadioButton)findViewById(R.id.rb_male)).setChecked(true);
                ((RadioButton)findViewById(R.id.rb_female)).setChecked(false);
            }else {
                ((RadioButton)findViewById(R.id.rb_male)).setChecked(false);
                ((RadioButton)findViewById(R.id.rb_female)).setChecked(true);
            }
        }
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

    private void selectBot() {
        cbBot = (CheckBox) findViewById(R.id.cb_chatbox_confirm);
        cbBot.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    strBot = "1";
                } else {
                    strBot = "0";
                }
            }
        });
    }

    private void selectGender() {
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

    private void updateProfileImageApi() {
        RequestBody _id = RequestBody.create(MediaType.parse("text/plain"), User.getUser().getUser().getUid());

        RequestBody imageBodyFile = RequestBody.create(MediaType.parse("image/*"), imageFile);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("u_profile", imageFile.getName(), imageBodyFile);

        RetrofitService.updateUserProfile(new Dialog(mContext), retrofitApiClient.updateProfileImage(_id, fileToUpload), new WebResponse() {
            @Override
            public void onResponseSuccess(Response<?> result) {
                ResponseBody responseBody = (ResponseBody) result.body();
                if (responseBody != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(responseBody.string());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Alerts.show(mContext, "Error in submit");
                }
            }

            @Override
            public void onResponseFailed(String error) {
                Alerts.show(mContext, error);
            }
        });
    }


    private void updateApi() {
        String strName = etName.getText().toString();
        String strUserName = username.getText().toString();
        String strMail = etMail.getText().toString();
        // String strCity = etCity.getText().toString();

        if (etName.getText().toString().length() == 0) {
            etName.setError("Name Required");
        } else if (etMail.getText().toString().length() == 0) {
            etMail.setError("Number Required");
        } else {
            if (cd.isNetworkAvailable()) {
                RetrofitService.updateData(new Dialog(mContext), retrofitApiClient.updateProfile(strPhone, strUserName, strGender,
                        "", "", strBot, strUserId, strName, strMail), new WebResponse() {
                    @Override
                    public void onResponseSuccess(Response<?> result) {
                        UserDataMainModal responseBody = (UserDataMainModal) result.body();
                        if (!responseBody.getError()) {
                            Gson gson = new GsonBuilder().setLenient().create();
                            String data = gson.toJson(responseBody);
                            AppPreference.setStringPreference(mContext, Constant.USER_DATA, data);

                            User.setUser(responseBody);
                            Alerts.show(mContext, responseBody.getMessage());
                            startActivity(new Intent(mContext, HomeActivity.class));
                            finish();
                        } else {
                            Alerts.show(mContext, responseBody.getMessage());
                        }
                    }

                    @Override
                    public void onResponseFailed(String error) {
                        Alerts.show(mContext, error);
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
                ((CircleImageView) findViewById(R.id.iv_user_profile)).setImageBitmap(bitmap);

                imgPath1 = destination.getAbsolutePath();
                imageFile =  new File(imgPath1);

                //api hit
                updateProfileImageApi();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == LOAD_IMAGE_GALLERY && resultCode == RESULT_OK && null != data) {
            final Uri uriImage = data.getData();
            final InputStream inputStream;
            try {
                inputStream = mContext.getContentResolver().openInputStream(uriImage);
                final Bitmap imageMap = BitmapFactory.decodeStream(inputStream);
                ((CircleImageView) findViewById(R.id.iv_user_profile)).setImageBitmap(imageMap);

                imagePath2 = getPath(uriImage);
                imageFile = new File(imagePath2);

                //api hit
                updateProfileImageApi();
            } catch (FileNotFoundException e) {
                Toast.makeText(mContext, "Image not found", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
        else {
            ((CircleImageView)findViewById(R.id.iv_user_profile)).setImageDrawable(getResources().getDrawable(R.drawable.ic_profile_img));
        }
    }
    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = mContext.getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String strPath = cursor.getString(column_index);
        cursor.close();
        return strPath;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_user_profile:
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
