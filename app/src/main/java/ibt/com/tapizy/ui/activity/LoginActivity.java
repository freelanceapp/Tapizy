package ibt.com.tapizy.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import ibt.com.tapizy.R;
import ibt.com.tapizy.otp_verification.OtpVerificationActivity;
import ibt.com.tapizy.retrofit_provider.RetrofitService;
import ibt.com.tapizy.retrofit_provider.WebResponse;
import ibt.com.tapizy.utils.Alerts;
import ibt.com.tapizy.utils.BaseActivity;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    private void init() {
        findViewById(R.id.loginBtn).setOnClickListener(this);
    }

    private void loginApi() {
        final String strPhone = ((EditText) findViewById(R.id.etPhone)).getText().toString();
        if (strPhone.isEmpty()) {
            Alerts.show(mContext, "Please enter Mobile no. first to login " + ("\ud83d\ude05"));
        } else {
            if (cd.isNetworkAvailable()) {
                RetrofitService.getloginData(new Dialog(mContext), retrofitApiClient.signIn(strPhone), new WebResponse() {
                    @Override
                    public void onResponseSuccess(Response<?> result) {
                        ResponseBody responseBody = (ResponseBody) result.body();
                        try {
                            JSONObject jsonObject = new JSONObject(responseBody.string());
                            if (!jsonObject.getBoolean("error")) {
                                Intent intent = new Intent(LoginActivity.this, OtpVerificationActivity.class);
                                intent.putExtra("from", "login");
                                intent.putExtra("phone", strPhone);
                                startActivity(intent);
                                finish();
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
        loginApi();
    }
}
