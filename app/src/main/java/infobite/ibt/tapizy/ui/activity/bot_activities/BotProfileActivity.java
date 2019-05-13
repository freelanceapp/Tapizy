package infobite.ibt.tapizy.ui.activity.bot_activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import infobite.ibt.tapizy.R;
import infobite.ibt.tapizy.constant.Constant;
import infobite.ibt.tapizy.model.User;
import infobite.ibt.tapizy.ui.fragment.bot_fragment.BotProfileFragment;
import infobite.ibt.tapizy.utils.AppPreference;
import infobite.ibt.tapizy.utils.BaseActivity;
import infobite.ibt.tapizy.utils.FragmentUtils;

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
        findViewById(R.id.imgBack).setOnClickListener(this);
        fragmentManagerBot = getSupportFragmentManager();
        fragmentUtilsBot = new FragmentUtils(fragmentManagerBot);
        fragmentUtilsBot.replaceFragment(new BotProfileFragment(), Constant.BotProfileFragment, R.id.frameLayout);

        setCoins();
    }

    private void setCoins() {
        Glide.with(mContext)
                .load(Constant.COIN_GIF)
                .useAnimationPool(true)
                .placeholder(R.drawable.coin_gif)
                .into(((ImageView) findViewById(R.id.imgToolbarCoinGif)));

        String userType = AppPreference.getStringPreference(mContext, Constant.USER_TYPE);
        if (userType.equalsIgnoreCase("user")) {
            String coins = User.getCoins();
            if (coins.isEmpty()) {
                coins = "0";
            }
            ((TextView) findViewById(R.id.txtCoinsCount)).setText(coins);
        } else {
            String coins = User.getCoins();
            if (coins.isEmpty()) {
                coins = "0";
            }
            ((TextView) findViewById(R.id.txtCoinsCount)).setText(coins);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Fragment fragment = fragmentManagerBot.findFragmentById(R.id.frameLayout);
        fragment.onActivityResult(requestCode, resultCode, data);
    }
}
