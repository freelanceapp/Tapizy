package ibt.com.tapizy.otp_verification;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ibt.com.tapizy.R;
import ibt.com.tapizy.constant.Constant;
import ibt.com.tapizy.model.User;
import ibt.com.tapizy.model.login_data_modal.UserDataMainModal;
import ibt.com.tapizy.retrofit_provider.RetrofitService;
import ibt.com.tapizy.retrofit_provider.WebResponse;
import ibt.com.tapizy.ui.activity.CreateProfileActivity;
import ibt.com.tapizy.ui.activity.HomeActivity;
import ibt.com.tapizy.ui.activity.SettingActivity;
import ibt.com.tapizy.utils.Alerts;
import ibt.com.tapizy.utils.AppPreference;
import ibt.com.tapizy.utils.BaseActivity;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class OtpVerificationActivity extends BaseActivity implements View.OnClickListener {

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private String strPhone = "";
    private boolean isAdding = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_verification);

        Button submitotp = findViewById(R.id.submit_otp);
        submitotp.setOnClickListener(this);
        findViewById(R.id.tvResend).setOnClickListener(this);

        isAdding = AppPreference.getBooleanPreference(mContext, Constant.MULTI_ACCOUNT);
        /*if (checkAndRequestPermissions()) {

        }
        readOtp();*/
        getIntentData();
    }

    private void getIntentData() {
        String strFrom = getIntent().getStringExtra("from");
        if (strFrom.equalsIgnoreCase("login")) {
            strPhone = getIntent().getStringExtra("phone");
        }
    }

    private void readOtp() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //  String  number = extras.getString("mobilenumber");
            String message = extras.getString("message");
            //  ((TextView)findViewById(R.id.mobile)).setText(number);
            int a1 = Integer.parseInt(String.valueOf(message.charAt(0)));
            ((EditText) findViewById(R.id.et_otp_a)).setText(a1 + "");
            int a2 = message.charAt(1);
            ((EditText) findViewById(R.id.et_otp_b)).setText(a2 + "");
            int a3 = Integer.parseInt(String.valueOf(message.charAt(2)));
            ((EditText) findViewById(R.id.et_otp_c)).setText(a3 + "");
            int a4 = Integer.parseInt(String.valueOf(message.charAt(3)));
            ((EditText) findViewById(R.id.et_otp_d)).setText(a4 + "");
            int a5 = Integer.parseInt(String.valueOf(message.charAt(4)));
            ((EditText) findViewById(R.id.et_otp_e)).setText(a5 + "");
            int a6 = Integer.parseInt(String.valueOf(message.charAt(5)));
            ((EditText) findViewById(R.id.et_otp_f)).setText(a6 + "");
        }
    }

    private boolean checkAndRequestPermissions() {
        int permissionSendMessage = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        int receiveSMS = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);
        int readSMS = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (receiveSMS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECEIVE_MMS);
        }
        if (readSMS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_SMS);
        }
        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.SEND_SMS);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    private void otpVerificationApi() {
        String strOtp = ((EditText) findViewById(R.id.etOtp)).getText().toString();
        /*String strA = ((EditText) findViewById(R.id.et_otp_a)).getText().toString();
        String strB = ((EditText) findViewById(R.id.et_otp_b)).getText().toString();
        String strC = ((EditText) findViewById(R.id.et_otp_c)).getText().toString();
        String strD = ((EditText) findViewById(R.id.et_otp_d)).getText().toString();
        String strE = ((EditText) findViewById(R.id.et_otp_e)).getText().toString();
        String strF = ((EditText) findViewById(R.id.et_otp_f)).getText().toString();*/

        if (strOtp.isEmpty()) {
            Alerts.show(mContext, "Enter otp " + getString(R.string.emoji));
        } else {
            if (cd.isNetworkAvailable()) {
                RetrofitService.getOtpData(new Dialog(mContext), retrofitApiClient.otpVerification(strPhone, strOtp), new WebResponse() {
                    @Override
                    public void onResponseSuccess(Response<?> result) {
                        UserDataMainModal mainModal = (UserDataMainModal) result.body();
                        if (!mainModal.getError()) {
                            if (mainModal.getUserType().equalsIgnoreCase("new user")) {
                                if (!isAdding) {
                                    saveUserData(mainModal);
                                }
                                Intent intent = new Intent(mContext, CreateProfileActivity.class);
                                intent.putExtra("phone", mainModal.getUser().getUContact());
                                intent.putExtra("uid", mainModal.getUser().getUid());
                                intent.putExtra("isBot", mainModal.getUser().getIsBot());
                                intent.putExtra("from", "otp");
                                startActivity(intent);
                                finish();

                            } else {
                                if (!isAdding) {
                                    saveUserData(mainModal);
                                    AppPreference.setBooleanPreference(mContext, Constant.IS_LOGIN, true);
                                    AppPreference.setStringPreference(mContext, Constant.USER_ID, mainModal.getUser().getUid());
                                    AppPreference.setStringPreference(mContext, Constant.USER_NAME, mainModal.getUser().getUName());
                                    Intent intent = new Intent(mContext, HomeActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Intent intent = new Intent(mContext, SettingActivity.class);
                                    intent.putExtra("u_name", mainModal.getUser().getUName());
                                    intent.putExtra("uid", mainModal.getUser().getUid());
                                    intent.putExtra("avatar", mainModal.getUser().getUProfile());
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        } else {
                            Alerts.show(mContext, mainModal.getMessage());
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
    }

    private void saveUserData(UserDataMainModal mainModal) {
        Alerts.show(mContext, mainModal.getMessage());
        Gson gson = new GsonBuilder().setLenient().create();
        String data = gson.toJson(mainModal);
        AppPreference.setStringPreference(mContext, Constant.USER_DATA, data);
        User.setUser(mainModal);
    }

    private void otpResendApi() {
        ((EditText) findViewById(R.id.etOtp)).setText("");
        ((EditText) findViewById(R.id.et_otp_a)).setText("");
        ((EditText) findViewById(R.id.et_otp_b)).setText("");
        ((EditText) findViewById(R.id.et_otp_c)).setText("");
        ((EditText) findViewById(R.id.et_otp_d)).setText("");
        ((EditText) findViewById(R.id.et_otp_e)).setText("");
        ((EditText) findViewById(R.id.et_otp_f)).setText("");

        if (strPhone.isEmpty()) {
            Alerts.show(mContext, "Please enter mobile number " + getString(R.string.emoji));
        } else {
            if (cd.isNetworkAvailable()) {

                RetrofitService.getloginData(new Dialog(mContext), retrofitApiClient.resendOTP(strPhone), new WebResponse() {
                    @Override
                    public void onResponseSuccess(Response<?> result) {
                        ResponseBody responseBody = (ResponseBody) result.body();
                        try {
                            JSONObject jsonObject = new JSONObject(responseBody.string());
                            if (!jsonObject.getBoolean("error")) {
                                Alerts.show(mContext, jsonObject.getString("message"));
                            } else {
                                Alerts.show(mContext, jsonObject.getString("message"));
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_otp:
                otpVerificationApi();
                break;
            case R.id.tvResend:
                otpResendApi();
                break;
        }
    }
}
