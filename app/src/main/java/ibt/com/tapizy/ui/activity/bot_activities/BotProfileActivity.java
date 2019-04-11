package ibt.com.tapizy.ui.activity.bot_activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import ibt.com.tapizy.R;
import ibt.com.tapizy.constant.Constant;
import ibt.com.tapizy.ui.fragment.bot_fragment.BotProfileFragment;
import ibt.com.tapizy.utils.BaseActivity;
import ibt.com.tapizy.utils.FragmentUtils;

public class BotProfileActivity extends BaseActivity implements View.OnClickListener {

    public static FragmentUtils fragmentUtilsBot;
    private FragmentManager fragmentManagerBot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bot_activity_profile);
        init();
    }

    private void init() {
        fragmentManagerBot = getSupportFragmentManager();
        fragmentUtilsBot = new FragmentUtils(fragmentManagerBot);
        fragmentUtilsBot.replaceFragment(new BotProfileFragment(), Constant.BotProfileFragment, R.id.frameLayout);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Fragment fragment = fragmentManagerBot.findFragmentById(R.id.frameLayout);
        fragment.onActivityResult(requestCode, resultCode, data);
    }
}
