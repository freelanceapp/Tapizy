package ibt.com.tapizy.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import ibt.com.tapizy.R;
import ibt.com.tapizy.adapter.TapizyListAdapter;
import ibt.com.tapizy.constant.Constant;
import ibt.com.tapizy.model.User;
import ibt.com.tapizy.model.api_bot_list.BotList;
import ibt.com.tapizy.model.favourite_bot.FavoriteBot;
import ibt.com.tapizy.model.favourite_bot.FavouriteBotMainModal;
import ibt.com.tapizy.model.login_data_modal.UserDataMainModal;
import ibt.com.tapizy.retrofit_provider.RetrofitService;
import ibt.com.tapizy.retrofit_provider.WebResponse;
import ibt.com.tapizy.ui.activity.chatbot_activity.ChatActivity;
import ibt.com.tapizy.ui.activity.chatbot_activity.CreateConversationActivity;
import ibt.com.tapizy.ui.activity.community_module.CommunityActivity;
import ibt.com.tapizy.ui.activity.explore.ExploreActivity;
import ibt.com.tapizy.ui.activity.recent_chat.RecentChatActivity;
import ibt.com.tapizy.ui.activity.trending_module.TrendingActivity;
import ibt.com.tapizy.utils.Alerts;
import ibt.com.tapizy.utils.AppPreference;
import ibt.com.tapizy.utils.BaseActivity;
import retrofit2.Response;

public class HomeActivity extends BaseActivity implements View.OnClickListener {

    private ArrayList<FavoriteBot> favoriteBotList = new ArrayList<>();
    private TapizyListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        init();
        setUserData();
        navigationItem();
    }

    private void setUserData() {
        //View header = navigationView.getHeaderView(0);
        findViewById(R.id.llProfile).setOnClickListener(this);
        findViewById(R.id.llCreateConversation).setOnClickListener(this);
        findViewById(R.id.ll24_7).setOnClickListener(this);
        findViewById(R.id.llRewards).setOnClickListener(this);
        findViewById(R.id.llSettings).setOnClickListener(this);
        Glide.with(mContext)
                .load(Constant.PROFILE_IMAGE_BASE_URL + User.getUser().getUser().getUProfile())
                .into(((CircleImageView) findViewById(R.id.profile_image)));

        ((TextView) findViewById(R.id.tvUserName)).setText(User.getUser().getUser().getUName());
        ((TextView) findViewById(R.id.tvEmail)).setText(User.getUser().getUser().getUEmail());

        handleNavItem();
    }

    private void init() {
        findViewById(R.id.llexplore).setOnClickListener(this);
        findViewById(R.id.llTrending).setOnClickListener(this);
        findViewById(R.id.llCommunity).setOnClickListener(this);
        findViewById(R.id.llchat).setOnClickListener(this);

        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("");
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
                    //findViewById(R.id.imgAppLogo).setVisibility(View.VISIBLE);
                    //findViewById(R.id.backdrop).setVisibility(View.GONE);
                    isShow = true;
                } else if (isShow) {
                    //findViewById(R.id.imgAppLogo).setVisibility(View.GONE);
                    //findViewById(R.id.backdrop).setVisibility(View.VISIBLE);
                    isShow = false;
                }
            }
        });
    }

    private void navigationItem() {
        RecyclerView rv_tapizy_list = findViewById(R.id.rv_tapizy_list);

        adapter = new TapizyListAdapter(mContext, favoriteBotList, this);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mContext, 4);
        rv_tapizy_list.setLayoutManager(mLayoutManager);
        rv_tapizy_list.setItemAnimator(new DefaultItemAnimator());
        rv_tapizy_list.setAdapter(adapter);
        favouriteBotListApi();
    }

    private void favouriteBotListApi() {
        String strUserId = User.getUser().getUser().getUid();
        if (cd.isNetworkAvailable()) {
            RetrofitService.getFavBotList(new Dialog(mContext), retrofitApiClient.favBotList(strUserId), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    AppPreference.setBooleanPreference(mContext, Constant.ADD_TO_FAV, false);
                    FavouriteBotMainModal favouriteBotMainModal = (FavouriteBotMainModal) result.body();
                    favoriteBotList.clear();
                    if (favouriteBotMainModal != null) {
                        if (favouriteBotMainModal.getFavoriteBot() != null) {
                            if (favouriteBotMainModal.getFavoriteBot().size() > 0) {
                                favoriteBotList.addAll(favouriteBotMainModal.getFavoriteBot());
                            }
                        }
                    }
                    adapter.notifyDataSetChanged();
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
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llexplore:
                startActivity(new Intent(mContext, ExploreActivity.class));
                break;
            case R.id.llCommunity:
                startActivity(new Intent(mContext, CommunityActivity.class));
                break;
            case R.id.llTrending:
                startActivity(new Intent(mContext, TrendingActivity.class));
                break;
            case R.id.llchat:
                startActivity(new Intent(mContext, RecentChatActivity.class));
                break;
            case R.id.llProfile:
                startActivity(new Intent(mContext, MyProfileActivity.class));
                break;
            case R.id.llCreateConversation:
                Intent intent = new Intent(mContext, CreateConversationActivity.class);
                startActivity(intent);
                break;
            case R.id.ll24_7:
                Alerts.show(mContext, "Under development !!!");
                break;
            case R.id.llRewards:
                Alerts.show(mContext, "Under development !!!");
                break;
            case R.id.llSettings:
                startActivity(new Intent(mContext, SettingActivity.class));
                break;
            case R.id.llayout:
                int pos = Integer.parseInt(v.getTag().toString());
                BotList botData = new BotList();
                botData.setAvtar(favoriteBotList.get(pos).getAvtar());
                botData.setBotName(favoriteBotList.get(pos).getBotName());
                botData.setUid(favoriteBotList.get(pos).getBotId());
                Intent intentA = new Intent(mContext, ChatActivity.class);
                intentA.putExtra("bot_data", (Parcelable) botData);
                startActivity(intentA);
                break;
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AppPreference.getBooleanPreference(mContext, "update")) {
            getPreferenceData();
        }

        if (AppPreference.getBooleanPreference(mContext, Constant.ADD_TO_FAV)) {
            favouriteBotListApi();
        }
    }

    private void getPreferenceData() {
        Gson gson = new Gson();
        String json = AppPreference.getStringPreference(mContext, Constant.USER_DATA);
        UserDataMainModal loginUserModel = gson.fromJson(json, UserDataMainModal.class);
        User.setUser(loginUserModel);
        AppPreference.setBooleanPreference(mContext, "update", false);
        setUserData();
    }

    private void handleNavItem() {
        if (User.getUser().getUser().getIsBot().equalsIgnoreCase("1")) {
            findViewById(R.id.viewCreateConversation).setVisibility(View.VISIBLE);
            findViewById(R.id.llCreateConversation).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.viewCreateConversation).setVisibility(View.GONE);
            findViewById(R.id.llCreateConversation).setVisibility(View.GONE);
        }
    }
}
