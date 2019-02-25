package infobite.com.tapizy.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;

import infobite.com.tapizy.R;
import infobite.com.tapizy.constant.Constant;
import infobite.com.tapizy.model.User;
import infobite.com.tapizy.model.login_data_modal.UserDataMainModal;
import infobite.com.tapizy.utils.AppPreference;
import infobite.com.tapizy.utils.BaseActivity;
import io.fabric.sdk.android.Fabric;

public class SplashScreen extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //firebaseAnalytics();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (AppPreference.getBooleanPreference(mContext, Constant.IS_LOGIN)) {
                    getPreferenceData();
                    Intent i = new Intent(SplashScreen.this, HomeActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Intent i = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }, 3000);
    }

    private void getPreferenceData() {
        Gson gson = new Gson();
        String json = AppPreference.getStringPreference(mContext, Constant.USER_DATA);
        UserDataMainModal loginUserModel = gson.fromJson(json, UserDataMainModal.class);
        User.setUser(loginUserModel);
    }

    private void firebaseAnalytics() {
        Crashlytics.getInstance().crash();
        Fabric.with(this, new Crashlytics());
    }
}
