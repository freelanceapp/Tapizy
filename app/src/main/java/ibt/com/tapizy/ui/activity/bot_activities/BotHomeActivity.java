package ibt.com.tapizy.ui.activity.bot_activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.view.View;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bot_activity_home);
        init();
    }

    private void init() {
        AppPreference.setStringPreference(mContext, Constant.USER_TYPE, "bot");

        strBotId = getIntent().getStringExtra("bot_id");

        findViewById(R.id.llBotProfile).setOnClickListener(this);
        findViewById(R.id.llTrending).setOnClickListener(this);
        findViewById(R.id.llCommunity).setOnClickListener(this);
        findViewById(R.id.llChatA).setOnClickListener(this);
        findViewById(R.id.llChatB).setOnClickListener(this);
        findViewById(R.id.llChatC).setOnClickListener(this);
        findViewById(R.id.llChatD).setOnClickListener(this);

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
            case R.id.llChatA:
            case R.id.llChatB:
            case R.id.llChatC:
            case R.id.llChatD:
                startActivity(new Intent(mContext, BotChatConversationActivity.class));
                break;
        }
    }
}
