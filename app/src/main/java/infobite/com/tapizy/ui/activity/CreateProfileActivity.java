package infobite.com.tapizy.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

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
import retrofit2.Response;

public class CreateProfileActivity extends BaseActivity implements View.OnClickListener {

    private static final int LOAD_IMAGE_GALLERY = 123;
    private static int PICK_IMAGE_CAMERA = 124;
    private static int PERMISSION_REQUEST_CODE = 456;
    private Bitmap bitmap, imageMap;
    private File destination = null;
    private InputStream inputStreamImg;
    private String imgPath = null, imgPath1, imagePath2;
    private EditText etName, etMail, username;
    private CheckBox cbChatbot, cbBot;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private String strPhone, strUserId, strFrom;
    private String strGender, strBot;

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

        Intent intent = getIntent();
        strFrom = intent.getStringExtra("from");
        strUserId = intent.getStringExtra("uid");
        strPhone = intent.getStringExtra("phone");

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

        /*
         * User detail
         * */
        if (strFrom.equalsIgnoreCase("myProfile")) {
            if (User.getUser().getUser().getUProfile() != null) {
                Glide.with(mContext)
                        .load(Constant.PROFILE_IMAGE_BASE_URL + User.getUser().getUser().getUProfile())
                        .into((CircleImageView) findViewById(R.id.iv_user_profile));
            }
            ((EditText) findViewById(R.id.tv_user_username)).setText(User.getUser().getUser().getUUsername());
            ((TextView) findViewById(R.id.tv_user_name)).setText(User.getUser().getUser().getUName());
            ((TextView) findViewById(R.id.tv_user_email)).setText(User.getUser().getUser().getUEmail());

            if (User.getUser().getUser().getUGender().equalsIgnoreCase("male")) {
                ((RadioButton) findViewById(R.id.rb_male)).setChecked(true);
                ((RadioButton) findViewById(R.id.rb_female)).setChecked(false);
            } else {
                ((RadioButton) findViewById(R.id.rb_male)).setChecked(false);
                ((RadioButton) findViewById(R.id.rb_female)).setChecked(true);
            }
        }
        selectGender();
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
                final CharSequence[] choose = {"Pick From Camera", "Choose From Gallery", "Cancel"};
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

    private void updateProfileImageApi(File imageFile) {
        RequestBody _id = RequestBody.create(MediaType.parse("text/plain"), User.getUser().getUser().getUid());
        RequestBody imageBodyFile = RequestBody.create(MediaType.parse("image/*"), imageFile);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("u_profile", imageFile.getName(), imageBodyFile);

        RetrofitService.updateUserProfile(new Dialog(mContext), retrofitApiClient.updateProfileImage(_id, fileToUpload), new WebResponse() {
            @Override
            public void onResponseSuccess(Response<?> result) {
                Alerts.show(mContext, "Profile updated");
                getUserDetailApi();
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
                            Alerts.show(mContext, responseBody.getMessage());
                            if (strFrom.equalsIgnoreCase("otp")) {
                                Gson gson = new GsonBuilder().setLenient().create();
                                String data = gson.toJson(responseBody);
                                AppPreference.setStringPreference(mContext, Constant.USER_DATA, data);

                                User.setUser(responseBody);
                                startActivity(new Intent(mContext, HomeActivity.class));
                                finish();
                            }
                            getUserDetailApi();
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOAD_IMAGE_GALLERY:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    selectImage();
                } else {
                    Alerts.show(mContext, "Please give permission");
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        inputStreamImg = null;
        if (requestCode == PICK_IMAGE_CAMERA) {
            try {

                Bitmap photo = (Bitmap) data.getExtras().get("data");
                ((CircleImageView) findViewById(R.id.iv_user_profile)).setImageBitmap(photo);
                Uri tempUri = getImageUri(getApplicationContext(), photo);
                File finalFile = new File(getRealPathFromURI(tempUri));

                //api hit
                updateProfileImageApi(finalFile);

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
                File imageFile = new File(imagePath2);

                //api hit
                updateProfileImageApi(imageFile);
            } catch (FileNotFoundException e) {
                Toast.makeText(mContext, "Image not found", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        } else {
            ((CircleImageView) findViewById(R.id.iv_user_profile)).setImageDrawable(getResources().getDrawable(R.drawable.ic_profile_img));
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
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
                try {
                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(CreateProfileActivity.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, LOAD_IMAGE_GALLERY);
                    } else {
                        selectImage();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_create_profile:
                selectGender();
                updateApi();
                break;
        }
    }

    /****************************************************************************************************/
    /*
     * Profile detail api
     * */
    private void getUserDetailApi() {
        RetrofitService.getOtpData(new Dialog(mContext), retrofitApiClient.getUserDetail(strUserId), new WebResponse() {
            @Override
            public void onResponseSuccess(Response<?> result) {
                UserDataMainModal mainModal = (UserDataMainModal) result.body();
                if (mainModal != null) {
                    AppPreference.setBooleanPreference(mContext, "update", true);
                    Gson gson = new GsonBuilder().setLenient().create();
                    String data = gson.toJson(mainModal);
                    AppPreference.setStringPreference(mContext, Constant.USER_DATA, data);
                    User.setUser(mainModal);
                }
            }

            @Override
            public void onResponseFailed(String error) {
                Alerts.show(mContext, error);
            }
        });
    }
}
