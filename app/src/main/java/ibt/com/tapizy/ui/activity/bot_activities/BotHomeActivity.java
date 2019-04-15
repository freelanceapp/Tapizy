package ibt.com.tapizy.ui.activity.bot_activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import ibt.com.tapizy.R;
import ibt.com.tapizy.constant.Constant;
import ibt.com.tapizy.model.User;
import ibt.com.tapizy.model.bot_profile_data.BotDetailMainModal;
import ibt.com.tapizy.retrofit_provider.RetrofitService;
import ibt.com.tapizy.retrofit_provider.WebResponse;
import ibt.com.tapizy.ui.activity.user_activities.community_module.CommunityActivity;
import ibt.com.tapizy.ui.activity.user_activities.trending_module.TrendingActivity;
import ibt.com.tapizy.utils.Alerts;
import ibt.com.tapizy.utils.AppPreference;
import ibt.com.tapizy.utils.BaseActivity;
import retrofit2.Response;

public class BotHomeActivity extends BaseActivity implements View.OnClickListener {

    private String strBotId = "";
    private CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bot_activity_home);
        init();
    }

    private void init() {
        strBotId = getIntent().getStringExtra("bot_id");
        AppPreference.setStringPreference(mContext, Constant.USER_TYPE, "bot");
        AppPreference.setStringPreference(mContext, Constant.USER_ID, strBotId);

        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        findViewById(R.id.imgBack).setOnClickListener(this);
        findViewById(R.id.llBotProfile).setOnClickListener(this);
        findViewById(R.id.llTrending).setOnClickListener(this);
        findViewById(R.id.llCommunity).setOnClickListener(this);
        findViewById(R.id.llChatA).setOnClickListener(this);
        findViewById(R.id.llChatB).setOnClickListener(this);
        findViewById(R.id.llChatC).setOnClickListener(this);
        findViewById(R.id.llChatD).setOnClickListener(this);
        findViewById(R.id.llChatE).setOnClickListener(this);
        findViewById(R.id.llChatF).setOnClickListener(this);
        findViewById(R.id.llChatG).setOnClickListener(this);
        findViewById(R.id.llChatH).setOnClickListener(this);

        AppBarLayout appBarLayout = findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {

            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = true;
                } else if (isShow) {
                    isShow = false;
                }
            }
        });

        botDetailApi();
    }

    private void botDetailApi() {
        if (cd.isNetworkAvailable()) {
            RetrofitService.getBotDetail(new Dialog(mContext), retrofitApiClient.botDetail(strBotId), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    BotDetailMainModal detailMainModal = (BotDetailMainModal) result.body();
                    if (detailMainModal == null)
                        return;

                    if (!detailMainModal.getError()) {
                        User.setBotDetail(detailMainModal.getBot());

                        ((TextView) findViewById(R.id.txtBotName)).setText(User.getBotDetail().getBotName());
                        Glide.with(mContext)
                                .load(Constant.BOT_PROFILE_IMAGE + User.getBotDetail().getBotAvtar())
                                .placeholder(R.drawable.img_chatbot)
                                .into((ImageView) findViewById(R.id.imgBotProfile));

                        if (User.getBotDetail().getBotColor().equalsIgnoreCase("Blue")) {
                            collapsingToolbarLayout.setBackgroundColor(getResources().getColor(R.color.bot_blue));
                            collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.bot_blue));
                        } else if (User.getBotDetail().getBotColor().equalsIgnoreCase("Green")) {
                            collapsingToolbarLayout.setBackgroundColor(getResources().getColor(R.color.bot_green));
                            collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.bot_green));
                        } else if (User.getBotDetail().getBotColor().equalsIgnoreCase("Teal")) {
                            collapsingToolbarLayout.setBackgroundColor(getResources().getColor(R.color.bot_teal));
                            collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.bot_teal));
                        } else if (User.getBotDetail().getBotColor().equalsIgnoreCase("Purple")) {
                            collapsingToolbarLayout.setBackgroundColor(getResources().getColor(R.color.bot_purple));
                            collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.bot_purple));
                        } else if (User.getBotDetail().getBotColor().equalsIgnoreCase("Olive")) {
                            collapsingToolbarLayout.setBackgroundColor(getResources().getColor(R.color.bot_olive));
                            collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.bot_olive));
                        } else if (User.getBotDetail().getBotColor().equalsIgnoreCase("Maroon")) {
                            collapsingToolbarLayout.setBackgroundColor(getResources().getColor(R.color.bot_maroon));
                            collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.bot_maroon));
                        }

                        myCoinsApi();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                setCoins();
                            }
                        }, 500);
                    } else {
                        Alerts.show(mContext, detailMainModal.getMessage());
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
            case R.id.llBotProfile:
                Intent intent = new Intent(mContext, BotProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.llTrending:
                startActivity(new Intent(mContext, TrendingActivity.class));
                break;
            case R.id.llCommunity:
                startActivity(new Intent(mContext, CommunityActivity.class));
                break;
            case R.id.imgBack:
                finish();
                break;
            case R.id.llChatA:
            case R.id.llChatB:
            case R.id.llChatC:
            case R.id.llChatD:
            case R.id.llChatE:
            case R.id.llChatF:
            case R.id.llChatG:
            case R.id.llChatH:
                startActivity(new Intent(mContext, BotChatConversationActivity.class));
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCoins();
    }
}
