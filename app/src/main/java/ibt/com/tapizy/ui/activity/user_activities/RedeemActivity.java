package ibt.com.tapizy.ui.activity.user_activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.crashlytics.android.Crashlytics;

import ibt.com.tapizy.R;
import ibt.com.tapizy.constant.Constant;
import ibt.com.tapizy.model.User;
import ibt.com.tapizy.utils.BaseActivity;
import io.fabric.sdk.android.Fabric;

public class RedeemActivity extends BaseActivity implements View.OnClickListener {

    public Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_redeem);
        mContext = this;

        showCoins();
    }

    private void showCoins() {
        findViewById(R.id.imgBack).setOnClickListener(this);
        Glide.with(mContext)
                .asGif()
                .load(Constant.COIN_GIF)
                .useAnimationPool(true)
                .placeholder(R.drawable.coin_gif)
                .into(((ImageView) findViewById(R.id.imgCoin)));

        String strCoins = User.getCoins();
        if (strCoins == null || strCoins.isEmpty()) {
            strCoins = "0";
        }
        ((TextView) findViewById(R.id.txtCoinsCount)).setText(strCoins + " Coins");

        int totalCoins = Integer.parseInt(strCoins);
        int totalAmount = totalCoins / 100;
        String strTotalAmount = String.valueOf(totalAmount);

        ((TextView) findViewById(R.id.txtTotalAmount)).setText("Total Rs. " + strTotalAmount);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
