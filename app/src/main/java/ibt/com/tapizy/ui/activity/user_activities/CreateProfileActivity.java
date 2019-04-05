package ibt.com.tapizy.ui.activity.user_activities;

import android.Manifest;
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
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ibt.com.tapizy.R;
import ibt.com.tapizy.adapter.SpinnerBotCategoryAdapter;
import ibt.com.tapizy.constant.Constant;
import ibt.com.tapizy.model.SubCatList;
import ibt.com.tapizy.model.User;
import ibt.com.tapizy.model.login_data_modal.UserDataMainModal;
import ibt.com.tapizy.retrofit_provider.RetrofitService;
import ibt.com.tapizy.retrofit_provider.WebResponse;
import ibt.com.tapizy.utils.Alerts;
import ibt.com.tapizy.utils.AppPreference;
import ibt.com.tapizy.utils.BaseActivity;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class CreateProfileActivity extends BaseActivity implements View.OnClickListener {

    private static final int LOAD_IMAGE_GALLERY = 123;
    private static int PICK_IMAGE_CAMERA = 124;
    private static int PERMISSION_REQUEST_CODE = 456;
    private File finalFile = null;

    private EditText etName, etMail, username;
    private RadioGroup radioGroupGender;
    private String strPhone, strUserId, strFrom, strGender, strBot,
            strBotCategoryId = "", strBotSubCategoryId = "0", strIsBot = "";
    private boolean isAdding = false;

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
        isAdding = AppPreference.getBooleanPreference(mContext, Constant.MULTI_ACCOUNT);

        findViewById(R.id.iv_user_profile).setOnClickListener(this);
        findViewById(R.id.btn_create_profile).setOnClickListener(this);
        findViewById(R.id.ic_back_profile).setOnClickListener(this);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        etName = findViewById(R.id.tv_user_name);
        etMail = findViewById(R.id.tv_user_email);
        username = findViewById(R.id.tv_user_username);

        Intent intent = getIntent();
        strFrom = intent.getStringExtra("from");
        strUserId = intent.getStringExtra("uid");
        strPhone = intent.getStringExtra("phone");
        username.setText(strPhone);
        CheckBox cbBot = findViewById(R.id.cb_chatbox_confirm);

        if (isAdding) {
            strBot = intent.getStringExtra("isBot");
            strIsBot = intent.getStringExtra("isBot");
        } else {
            strBot = User.getUser().getUser().getIsBot();
            strIsBot = User.getUser().getUser().getIsBot();
        }
        cbBot.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    findViewById(R.id.llBotDetail).setVisibility(View.VISIBLE);
                    strBot = "1";
                } else {
                    findViewById(R.id.llBotDetail).setVisibility(View.GONE);
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
            ((EditText) findViewById(R.id.tv_user_username)).setText(User.getUser().getUser().getUContact());
            ((TextView) findViewById(R.id.tv_user_name)).setText(User.getUser().getUser().getUName());
            ((TextView) findViewById(R.id.tv_user_email)).setText(User.getUser().getUser().getUEmail());

            if (User.getUser().getUser().getUGender().equalsIgnoreCase("male")) {
                ((RadioButton) findViewById(R.id.rb_male)).setChecked(true);
                ((RadioButton) findViewById(R.id.rb_female)).setChecked(false);
            } else {
                ((RadioButton) findViewById(R.id.rb_male)).setChecked(false);
                ((RadioButton) findViewById(R.id.rb_female)).setChecked(true);
            }

            if (User.getUser().getUser().getIsBot().equalsIgnoreCase("1")) {
                ((TextView) findViewById(R.id.tvIsBot)).setText("You are a Bot now !");
                findViewById(R.id.cb_chatbox_confirm).setVisibility(View.GONE);
            } else {
                ((TextView) findViewById(R.id.tvIsBot)).setText("Check it if you register for Bot.");
                findViewById(R.id.cb_chatbox_confirm).setVisibility(View.VISIBLE);
            }
        }
        selectGender();
        botCategorySpinner();
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
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
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
        int selectedId = radioGroupGender.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(selectedId);
        strGender = String.valueOf(radioButton.getText());
    }

    private void updateProfileImageApi(File imageFile) {
        RequestBody _id = null;
        if (isAdding) {
            _id = RequestBody.create(MediaType.parse("text/plain"), strUserId);
        } else {
            _id = RequestBody.create(MediaType.parse("text/plain"), User.getUser().getUser().getUid());
        }
        RequestBody imageBodyFile = RequestBody.create(MediaType.parse("image/*"), imageFile);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("u_profile", imageFile.getName(), imageBodyFile);

        RetrofitService.updateUserProfile(new Dialog(mContext), retrofitApiClient.updateProfileImage(_id, fileToUpload), new WebResponse() {
            @Override
            public void onResponseSuccess(Response<?> result) {
                Alerts.show(mContext, "Profile updated");
                if (!isAdding) {
                    getUserDetailApi();
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
        String strMail = etMail.getText().toString();

        if (etName.getText().toString().length() == 0) {
            etName.setError("Name Required");
        } else if (etMail.getText().toString().length() == 0) {
            etMail.setError("Email Required");
        } else if (!strMail.matches(Constant.EmailPattern)) {
            etMail.setError("Enter valid email");
        } else {
            if (cd.isNetworkAvailable()) {
                RetrofitService.updateData(new Dialog(mContext), retrofitApiClient.updateProfile(strPhone, "", strGender,
                        "", "", strBot, strUserId, strName, strMail, "red",
                        strBotCategoryId, strBotSubCategoryId), new WebResponse() {
                    @Override
                    public void onResponseSuccess(Response<?> result) {
                        UserDataMainModal responseBody = (UserDataMainModal) result.body();
                        if (!responseBody.getError()) {
                            Alerts.show(mContext, "Update success!!!");
                            if (strFrom.equalsIgnoreCase("otp")) {
                                if (isAdding) {
                                    Intent intent = new Intent(mContext, SettingActivity.class);
                                    intent.putExtra("u_name", responseBody.getUser().getUName());
                                    intent.putExtra("uid", responseBody.getUser().getUid());
                                    intent.putExtra("avatar", responseBody.getUser().getUProfile());
                                    startActivity(intent);
                                    finish();
                                } else {
                                    AppPreference.setBooleanPreference(mContext, Constant.IS_LOGIN, true);
                                    Gson gson = new GsonBuilder().setLenient().create();
                                    String data = gson.toJson(responseBody);
                                    AppPreference.setStringPreference(mContext, Constant.USER_DATA, data);
                                    User.setUser(responseBody);
                                    startActivity(new Intent(mContext, HomeActivity.class));
                                    getUserDetailApi();
                                    finish();
                                }
                            } else {
                                getUserDetailApi();
                            }
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
        if (requestCode == PICK_IMAGE_CAMERA) {
            try {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                ((CircleImageView) findViewById(R.id.iv_user_profile)).setImageBitmap(photo);
                Uri tempUri = getImageUri(getApplicationContext(), photo);
                finalFile = new File(getRealPathFromURI(tempUri));

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

                String imagePath2 = getPath(uriImage);
                File imageFile = new File(imagePath2);
                finalFile = imageFile;
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
            case R.id.ic_back_profile:
                Intent intent = new Intent(mContext, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
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
                String strImage = User.getUser().getUser().getUProfile();

                if (strImage == null) {
                    Alerts.show(mContext, "Please select profile image");
                } else {
                    onUpdateClick();
                }
                /*if (finalFile == null) {
                    Alerts.show(mContext, "Please select profile image");
                } else {
                    onUpdateClick();
                }*/
                break;
        }
    }

    private void onUpdateClick() {
        if (strIsBot.equalsIgnoreCase("1")) {
            selectGender();
            updateApi();
        } else {
            if (strBot.equalsIgnoreCase("1")) {
                if (strBotCategoryId.equalsIgnoreCase("0")) {
                    Alerts.show(mContext, "Please select category");
                } else if (strBotSubCategoryId.equalsIgnoreCase("0")) {
                    Alerts.show(mContext, "Please select sub category");
                } else {
                    selectGender();
                    updateApi();
                }
            } else {
                selectGender();
                updateApi();
            }
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

    /****************************************************************************************************/
    /*
     * Category and sub category spinner
     * */
    private void botCategorySpinner() {
        final List<SubCatList> items = new ArrayList<>();
        for (int i = 0; i < Constant.CATEGORY_LIST.length; i++) {
            String strName = Constant.CATEGORY_LIST[i];
            SubCatList subCatList = new SubCatList(String.valueOf(i), strName);
            items.add(subCatList);
        }

        SpinnerBotCategoryAdapter botCategoryAdapter = new SpinnerBotCategoryAdapter(mContext, R.layout.spinner_category_layout, items);
        Spinner spinnerList = findViewById(R.id.spinnerCategory);
        spinnerList.setAdapter(botCategoryAdapter);
        spinnerList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /*strBotCategory = items.get(position).getName();*/
                strBotCategoryId = String.valueOf(position);

                if (strBotCategoryId.equalsIgnoreCase("0")) {
                    //Alerts.show(mContext, "Select category");
                } else {
                    botSubCategoryApi(strBotCategoryId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void botSubCategoryApi(String strId) {
        if (cd.isNetworkAvailable()) {
            RetrofitService.subCatList(new Dialog(mContext), retrofitApiClient.subCategory(strId), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    ResponseBody responseBody = (ResponseBody) result.body();
                    try {
                        JSONObject jsonObject = new JSONObject(responseBody.string());
                        if (!jsonObject.getBoolean("error")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("bot_sub_cat");
                            if (jsonArray != null) {
                                List<SubCatList> items = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObj = jsonArray.getJSONObject(i);
                                    String name = jsonObj.getString("name");
                                    String id = jsonObj.getString("id");
                                    SubCatList subCatList = new SubCatList(id, name);
                                    items.add(subCatList);
                                }
                                botSubCategorySpinner(items);
                            }
                        } else {
                            Alerts.show(mContext, "No data");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onResponseFailed(String error) {
                    Alerts.show(mContext, error);
                }
            });
        } else {
            cd.show(mContext);
        }
    }

    private void botSubCategorySpinner(final List<SubCatList> items) {
        SpinnerBotCategoryAdapter botCategoryAdapter = new SpinnerBotCategoryAdapter(mContext, R.layout.spinner_category_layout, items);
        Spinner spinnerList = findViewById(R.id.spinnerSubCategory);
        spinnerList.setAdapter(botCategoryAdapter);
        spinnerList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //strBotCategory = items.get(position).getName();
                strBotSubCategoryId = items.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}