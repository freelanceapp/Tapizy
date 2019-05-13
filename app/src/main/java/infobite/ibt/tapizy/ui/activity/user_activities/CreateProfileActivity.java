package infobite.ibt.tapizy.ui.activity.user_activities;

import android.Manifest;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import infobite.ibt.tapizy.R;
import infobite.ibt.tapizy.constant.Constant;
import infobite.ibt.tapizy.model.User;
import infobite.ibt.tapizy.model.login_data_modal.UserDataMainModal;
import infobite.ibt.tapizy.retrofit_provider.RetrofitService;
import infobite.ibt.tapizy.retrofit_provider.WebResponse;
import infobite.ibt.tapizy.utils.Alerts;
import infobite.ibt.tapizy.utils.AppPreference;
import infobite.ibt.tapizy.utils.BaseActivity;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

public class CreateProfileActivity extends BaseActivity implements View.OnClickListener {

    private int mYear = 0, mMonth = 0, mDay = 0;
    private String strDob = "";

    private static final int LOAD_IMAGE_GALLERY = 123;
    private static int PICK_IMAGE_CAMERA = 124;
    private static int PERMISSION_REQUEST_CODE = 456;
    private File finalFile = null;

    private EditText edtName, edtEmail, edtPhone, edtDob, edtCity, edtState, edtCountry;
    private RadioGroup radioGroupGender;
    private String strPhone, strUserId, strFrom, strGender;

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
        radioGroupGender = findViewById(R.id.radioGroupGender);
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhone);
        edtDob = findViewById(R.id.edtDob);
        edtCity = findViewById(R.id.edtCity);
        edtState = findViewById(R.id.edtState);
        edtCountry = findViewById(R.id.edtCountry);

        findViewById(R.id.iv_user_profile).setOnClickListener(this);
        findViewById(R.id.btn_create_profile).setOnClickListener(this);
        findViewById(R.id.ic_back_profile).setOnClickListener(this);
        edtDob.setOnClickListener(this);

        Intent intent = getIntent();
        strFrom = intent.getStringExtra("from");
        strUserId = intent.getStringExtra("uid");
        strPhone = intent.getStringExtra("phone");
        edtPhone.setText(strPhone);

        /*
         * User detail
         * */
        if (strFrom.equalsIgnoreCase("myProfile")) {
            if (User.getUser().getUser().getUProfile() != null) {
                Glide.with(mContext)
                        .load(Constant.PROFILE_IMAGE_BASE_URL + User.getUser().getUser().getUProfile())
                        .placeholder(R.drawable.ic_default_profile)
                        .into((CircleImageView) findViewById(R.id.iv_user_profile));
            }
            edtPhone.setText(User.getUser().getUser().getUContact());
            edtName.setText(User.getUser().getUser().getUName());
            edtEmail.setText(User.getUser().getUser().getUEmail());
            edtDob.setText(User.getUser().getUser().getDob());
            edtCity.setText(User.getUser().getUser().getCity());
            edtState.setText(User.getUser().getUser().getState());
            edtCountry.setText(User.getUser().getUser().getCountry());

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
        RequestBody _id = RequestBody.create(MediaType.parse("text/plain"), User.getUser().getUser().getUid());
        RequestBody imageBodyFile = RequestBody.create(MediaType.parse("image/*"), imageFile);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("u_profile", imageFile.getName(), imageBodyFile);

        RetrofitService.updateUserProfile(new Dialog(mContext), retrofitApiClient.updateProfileImage(_id, fileToUpload), new WebResponse() {
            @Override
            public void onResponseSuccess(Response<?> result) {
                getUserDetailApi();
            }

            @Override
            public void onResponseFailed(String error) {
                Alerts.show(mContext, error);
            }
        });
    }

    private void updateApi() {
        String strName = edtName.getText().toString();
        String strMail = edtEmail.getText().toString();
        String strDob = edtDob.getText().toString();
        String strCity = edtCity.getText().toString();
        String strState = edtState.getText().toString();
        String strCountry = edtCountry.getText().toString();

        if (strName.isEmpty()) {
            edtName.setError("Name Required");
        } else if (strMail.isEmpty()) {
            edtEmail.setError("Email Required");
        } else if (!strMail.matches(Constant.EmailPattern)) {
            edtEmail.setError("Enter valid email");
        } else if (strDob.isEmpty()) {
            Alerts.show(mContext, "Select Date of Birth...!!!");
        } else if (strCity.isEmpty()) {
            Alerts.show(mContext, "Enter city...!!!");
        } else if (strState.isEmpty()) {
            Alerts.show(mContext, "Enter state...!!!");
        } else if (strCountry.isEmpty()) {
            Alerts.show(mContext, "Enter Country...!!!");
        } else {
            if (cd.isNetworkAvailable()) {

                RetrofitService.updateData(new Dialog(mContext), retrofitApiClient.updateProfile(strName,
                        strMail, strGender, strUserId, strDob, strCity, strState, strCountry), new WebResponse() {
                    @Override
                    public void onResponseSuccess(Response<?> result) {
                        UserDataMainModal responseBody = (UserDataMainModal) result.body();
                        if (!responseBody.getError()) {
                            Alerts.show(mContext, "Update success!!!");
                            if (strFrom.equalsIgnoreCase("otp")) {
                                AppPreference.setBooleanPreference(mContext, Constant.IS_LOGIN, true);
                                Gson gson = new GsonBuilder().setLenient().create();
                                String data = gson.toJson(responseBody);
                                AppPreference.setStringPreference(mContext, Constant.USER_DATA, data);
                                User.setUser(responseBody);
                                startActivity(new Intent(mContext, HomeActivity.class));
                                finish();
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
                finish();
                break;
            case R.id.edtDob:
                dateDialogue();
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
                    selectGender();
                    updateApi();
                }
                break;
        }
    }

    private void dateDialogue() {
        Calendar calendar = Calendar.getInstance();
        if (mYear == 0 || mMonth == 0 || mDay == 0) {
            mYear = calendar.get(Calendar.YEAR);
            mMonth = calendar.get(Calendar.MONTH);
            mDay = calendar.get(Calendar.DAY_OF_MONTH);
        }
        DatePickerDialog dialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mYear = year;
                mMonth = month;
                mDay = dayOfMonth;
                strDob = dayOfMonth + "-" + (month + 1) + "-" + year;
                edtDob.setText(strDob);
            }
        }, mYear, mMonth, mDay);
        dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        dialog.show();
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
                    AppPreference.setBooleanPreference(mContext, Constant.IS_PROFILE_UPDATE, true);
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