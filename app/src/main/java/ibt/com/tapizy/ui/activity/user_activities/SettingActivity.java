package ibt.com.tapizy.ui.activity.user_activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ibt.com.tapizy.R;
import ibt.com.tapizy.constant.Constant;
import ibt.com.tapizy.model.User;
import ibt.com.tapizy.model.login_data_modal.UserDataMainModal;
import ibt.com.tapizy.retrofit_provider.RetrofitService;
import ibt.com.tapizy.retrofit_provider.WebResponse;
import ibt.com.tapizy.ui.fragment.BottomsheetFragment;
import ibt.com.tapizy.utils.Alerts;
import ibt.com.tapizy.utils.AppPreference;
import ibt.com.tapizy.utils.BaseActivity;
import retrofit2.Response;

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private boolean isAdding = false;
    private boolean isAccountB = false;
    private String strUid = "", strUName = "", strUAvatar = "";
    private String strId_B, strName_B, strAvatar_B;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        findViewById(R.id.ll_switch_account).setOnClickListener(this);
        findViewById(R.id.ll_logout).setOnClickListener(this);
        findViewById(R.id.imgBack).setOnClickListener(this);
        init();
    }

    private void init() {
        isAccountB = AppPreference.getMultiBoolean(mContext, Constant.USER_B);
        isAdding = AppPreference.getBooleanPreference(mContext, Constant.MULTI_ACCOUNT);
        if (isAdding) {
            AppPreference.setBooleanPreference(mContext, Constant.MULTI_ACCOUNT, false);
            strUName = getIntent().getStringExtra("u_name");
            strUid = getIntent().getStringExtra("uid");
            strUAvatar = getIntent().getStringExtra("avatar");

            AppPreference.setMultiBoolean(mContext, Constant.USER_B, true);

            AppPreference.setMultiStringPreference(mContext, Constant.USER_ID_B, strUid);
            AppPreference.setMultiStringPreference(mContext, Constant.USER_NAME_B, strUName);
            AppPreference.setMultiStringPreference(mContext, Constant.USER_AVATAR_B, strUAvatar);
        }

        if (isAccountB) {
            strId_B = AppPreference.getMultiStringPreference(mContext, Constant.USER_ID_B);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                finish();
                break;
            case R.id.ll_switch_account:
                switchAccount();
                break;
            case R.id.ll_logout:
                if (isAccountB) {
                    getUserDetailApi();
                } else {
                    logout();
                }
                break;
        }
    }

    private void switchAccount() {
        new BottomsheetFragment().show(getSupportFragmentManager(), "Dialog");
    }

    private void getUserDetailApi() {
        RetrofitService.getOtpData(new Dialog(mContext), retrofitApiClient.getUserDetail(strId_B), new WebResponse() {
            @Override
            public void onResponseSuccess(Response<?> result) {
                UserDataMainModal mainModal = (UserDataMainModal) result.body();
                if (mainModal != null) {
                    AppPreference.setMultiBoolean(mContext, Constant.USER_B, false);
                    AppPreference.setBooleanPreference(mContext, Constant.IS_LOGIN, true);
                    AppPreference.setBooleanPreference(mContext, "update", true);
                    Gson gson = new GsonBuilder().setLenient().create();
                    String data = gson.toJson(mainModal);
                    AppPreference.setStringPreference(mContext, Constant.USER_DATA, data);
                    User.setUser(mainModal);

                    Intent intent = new Intent(mContext, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onResponseFailed(String error) {
                Alerts.show(mContext, error);
            }
        });
    }

    private void logout() {
        AppPreference.clearAllPreferences(mContext);
        Intent intent = new Intent(mContext, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

}
