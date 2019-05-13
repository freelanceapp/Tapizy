package infobite.ibt.tapizy.ui.activity.user_activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import infobite.ibt.tapizy.R;
import infobite.ibt.tapizy.model.app_content.AppContentModal;
import infobite.ibt.tapizy.retrofit_provider.RetrofitService;
import infobite.ibt.tapizy.retrofit_provider.WebResponse;
import infobite.ibt.tapizy.ui.activity.TermsPolicyActivity;
import infobite.ibt.tapizy.utils.Alerts;
import infobite.ibt.tapizy.utils.AppPreference;
import infobite.ibt.tapizy.utils.BaseActivity;
import retrofit2.Response;

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private AppContentModal appContentModal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        
        init();
    }

    private void init() {
        updateWebViewCoins();
        ((TextView) findViewById(R.id.txtTitle)).setText("Setting");
        findViewById(R.id.imgBack).setOnClickListener(this);
        findViewById(R.id.ll_logout).setOnClickListener(this);
        findViewById(R.id.llTerms).setOnClickListener(this);
        findViewById(R.id.llPrivacy).setOnClickListener(this);
        findViewById(R.id.llAboutUs).setOnClickListener(this);

        termsPrivacyApi();
    }

    private void termsPrivacyApi() {
        if (cd.isNetworkAvailable()) {
            RetrofitService.getAppContent(new Dialog(mContext), retrofitApiClient.appContent(), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    appContentModal = (AppContentModal) result.body();
                    if (appContentModal != null) {
                        if (appContentModal.getError()) {
                            Alerts.show(mContext, appContentModal.getMessage());
                        }
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

    @Override

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                finish();
                break;
            case R.id.ll_logout:
                logout();
                break;
            case R.id.llTerms:
                Intent intent = new Intent(mContext, TermsPolicyActivity.class);
                intent.putExtra("title", "Terms & Condition");
                intent.putExtra("data", appContentModal.getContent().get(0).getContent());
                startActivity(intent);
                break;
            case R.id.llPrivacy:
                Intent intentP = new Intent(mContext, TermsPolicyActivity.class);
                intentP.putExtra("title", "Privacy Policy");
                intentP.putExtra("data", appContentModal.getContent().get(1).getContent());
                startActivity(intentP);
                break;
            case R.id.llAboutUs:
                Intent intentA = new Intent(mContext, TermsPolicyActivity.class);
                intentA.putExtra("title", "About Us");
                intentA.putExtra("data", appContentModal.getContent().get(2).getContent());
                startActivity(intentA);
                break;
        }
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
