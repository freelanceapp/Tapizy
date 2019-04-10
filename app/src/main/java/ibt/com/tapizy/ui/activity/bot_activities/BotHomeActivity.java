package ibt.com.tapizy.ui.activity.bot_activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;

import ibt.com.tapizy.R;
import ibt.com.tapizy.ui.activity.user_activities.CreateBotActivity;
import ibt.com.tapizy.ui.activity.user_activities.community_module.CommunityActivity;
import ibt.com.tapizy.ui.activity.user_activities.trending_module.TrendingActivity;
import ibt.com.tapizy.utils.BaseActivity;

public class BotHomeActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bot_activity_home);
        init();
    }

    private void init() {
        findViewById(R.id.llBotProfile).setOnClickListener(this);
        findViewById(R.id.llTrending).setOnClickListener(this);
        findViewById(R.id.llCommunity).setOnClickListener(this);
        findViewById(R.id.llChatA).setOnClickListener(this);
        findViewById(R.id.llChatB).setOnClickListener(this);
        findViewById(R.id.llChatC).setOnClickListener(this);
        findViewById(R.id.llChatD).setOnClickListener(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

      /*  DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();*/

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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llBotProfile:
                startActivity(new Intent(mContext, CreateBotActivity.class));
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
