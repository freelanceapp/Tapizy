package infobite.com.tapizy.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import infobite.com.tapizy.R;
import infobite.com.tapizy.utils.AppPreference;
import infobite.com.tapizy.utils.BaseActivity;

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout llSwitchAccount, llLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        llSwitchAccount = findViewById(R.id.ll_switch_account);
        llSwitchAccount.setOnClickListener(this);
        llLogout = findViewById(R.id.ll_logout);
        llLogout.setOnClickListener(this);
        findViewById(R.id.imgBack).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                finish();
                break;
            case R.id.ll_logout:
                AppPreference.clearAllPreferences(mContext);
                Intent intent = new Intent(mContext, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
                break;

        }
    }
}
