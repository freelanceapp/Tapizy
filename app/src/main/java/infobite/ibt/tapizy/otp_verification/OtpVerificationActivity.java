package infobite.ibt.tapizy.otp_verification;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import infobite.ibt.tapizy.R;
import infobite.ibt.tapizy.constant.Constant;
import infobite.ibt.tapizy.model.User;
import infobite.ibt.tapizy.model.login_data_modal.UserDataMainModal;
import infobite.ibt.tapizy.retrofit_provider.RetrofitService;
import infobite.ibt.tapizy.retrofit_provider.WebResponse;
import infobite.ibt.tapizy.ui.activity.user_activities.CreateProfileActivity;
import infobite.ibt.tapizy.ui.activity.user_activities.HomeActivity;
import infobite.ibt.tapizy.ui.activity.user_activities.SettingActivity;
import infobite.ibt.tapizy.utils.Alerts;
import infobite.ibt.tapizy.utils.AppPreference;
import infobite.ibt.tapizy.utils.BaseActivity;
import infobite.ibt.tapizy.utils.pinview.Pinview;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class OtpVerificationActivity extends BaseActivity implements View.OnClickListener {

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private String strPhone = "";
    private boolean isAdding = false;
    private Pinview pinview1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_verification);

        Button submitotp = findViewById(R.id.submit_otp);
        submitotp.setOnClickListener(this);
        findViewById(R.id.tvResend).setOnClickListener(this);
        pinview1 = (Pinview) findViewById(R.id.pinview1);
        isAdding = AppPreference.getBooleanPreference(mContext, Constant.MULTI_ACCOUNT);
        /*if (checkAndRequestPermissions()) {

        }
        readOtp();*/
        otptime();
        getIntentData();
    }

    private void otptime() {
        ((TextView) findViewById(R.id.tvResend)).setClickable(false);
        ((TextView) findViewById(R.id.tvResend)).setTextColor(getResources().getColor(R.color.gray_e));
        new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                ((TextView) findViewById(R.id.tvRemaining)).setText("Remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                ((TextView) findViewById(R.id.tvResend)).setTextColor(getResources().getColor(R.color.blueB));
                ((TextView) findViewById(R.id.tvResend)).setClickable(true);
                ((TextView) findViewById(R.id.tvRemaining)).setText("Didn't get The OTP ?");
            }
        }.start();
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
            String message = extras.getString("message");
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
        String strOtp = pinview1.getValue();

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
                                intent.putExtra("from", "otp");
                                startActivity(intent);
                                finish();

                            } else {
                                if (!isAdding) {
                                    saveUserData(mainModal);
                                    AppPreference.setBooleanPreference(mContext, Constant.IS_LOGIN, true);
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
        pinview1.setValue("");

        if (strPhone.isEmpty()) {
            Alerts.show(mContext, "Please enter mobile number " + getString(R.string.emoji));
        } else {
            if (cd.isNetworkAvailable()) {

                RetrofitService.getloginData(new Dialog(mContext), retrofitApiClient.resendOTP(strPhone), new WebResponse() {
                    @Override
                    public void onResponseSuccess(Response<?> result) {
                        ResponseBody responseBody = (ResponseBody) result.body();
                        try {
                            otptime();
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
