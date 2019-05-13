package infobite.ibt.tapizy.ui.activity.user_activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;

import infobite.ibt.tapizy.R;
import infobite.ibt.tapizy.constant.Constant;
import infobite.ibt.tapizy.model.User;
import infobite.ibt.tapizy.model.login_data_modal.UserDataMainModal;
import infobite.ibt.tapizy.utils.AppPreference;
import io.fabric.sdk.android.Fabric;

public class SplashScreen extends AppCompatActivity {

    public Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash_screen);
        mContext = this;

        Glide.with(mContext)
                .load(R.drawable.splash_image)
                .placeholder(R.drawable.splash_image)
                .into((ImageView) findViewById(R.id.imgSplash));

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
       /* Crashlytics.getInstance().crash();
        Fabric.with(this, new Crashlytics());*/
    }
}
