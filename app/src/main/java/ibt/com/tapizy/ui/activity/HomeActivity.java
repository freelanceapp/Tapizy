package ibt.com.tapizy.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ibt.com.tapizy.R;
import ibt.com.tapizy.adapter.NavigationItemListAdapter;
import ibt.com.tapizy.adapter.TapizyListAdapter;
import ibt.com.tapizy.constant.Constant;
import ibt.com.tapizy.model.TapizyListModel;
import ibt.com.tapizy.model.User;
import ibt.com.tapizy.model.login_data_modal.UserDataMainModal;
import ibt.com.tapizy.model.navigation_item_modal.NavItemList;
import ibt.com.tapizy.ui.activity.chatbot_activity.CreateConversationActivity;
import ibt.com.tapizy.ui.activity.community_module.CommunityActivity;
import ibt.com.tapizy.ui.activity.explore.ExploreActivity;
import ibt.com.tapizy.ui.activity.recent_chat.RecentChatActivity;
import ibt.com.tapizy.ui.activity.trending_module.TrendingActivity;
import ibt.com.tapizy.utils.Alerts;
import ibt.com.tapizy.utils.AppPreference;
import ibt.com.tapizy.utils.BaseActivity;

public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private Fragment fragment;
    private LinearLayout menuLayout;

    private boolean menuCondition = true;
    private RelativeLayout container;
    private TextView titleName;
    private ImageView searchBtn;
    private FrameLayout frame_container;
    private ArrayList<TapizyListModel> tapizyListModels = new ArrayList<>();
    private TapizyListAdapter adapter;
    private RecyclerView rv_tapizy_list;
    private NavigationView navigationView;
    private List<NavItemList> navItemLists = new ArrayList<>();
    private String strUserId;

    private String[] strItems = {"Profile", "Create conversation", "24 * 7", "Rewards", "Settings"};
    private Integer[] strImages = {R.drawable.profile_image, R.drawable.profile_image, R.drawable.profile_image,
            R.drawable.profile_image, R.drawable.profile_image};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        frame_container = (FrameLayout) findViewById(R.id.frame_container);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        menuLayout = (LinearLayout) findViewById(R.id.menuLayout);
        container = (RelativeLayout) findViewById(R.id.container);

        addproduct();
        init();
        setUserData();
        //addNavItem();
    }

    private void addNavItem() {
        strUserId = AppPreference.getStringPreference(mContext, Constant.USER_ID);

        for (int i = 0; i < strItems.length; i++) {
            NavItemList itemList = new NavItemList();
            itemList.setName(strItems[i]);
            itemList.setImage(strImages[i]);
            navItemLists.add(itemList);
        }
        setRecyclerViewNavigationItem();
    }

    private void setRecyclerViewNavigationItem() {

        RecyclerView recyclerViewNavigationItem = findViewById(R.id.recyclerViewNavigationItem);

        NavigationItemListAdapter itemListAdapter = new NavigationItemListAdapter(mContext, navItemLists, this);

        recyclerViewNavigationItem.setHasFixedSize(true);
        recyclerViewNavigationItem.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewNavigationItem.setAdapter(itemListAdapter);
        itemListAdapter.notifyDataSetChanged();
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
    }

    private void init() {
        findViewById(R.id.llexplore).setOnClickListener(this);
        findViewById(R.id.llTrending).setOnClickListener(this);
        findViewById(R.id.llCommunity).setOnClickListener(this);
        findViewById(R.id.llchat).setOnClickListener(this);

        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    findViewById(R.id.imgAppLogo).setVisibility(View.VISIBLE);
                    findViewById(R.id.backdrop).setVisibility(View.GONE);
                    isShow = true;
                } else if (isShow) {
                    findViewById(R.id.imgAppLogo).setVisibility(View.GONE);
                    findViewById(R.id.backdrop).setVisibility(View.VISIBLE);
                    isShow = false;
                }
            }
        });
    }

    private void addproduct() {
        rv_tapizy_list = findViewById(R.id.rv_tapizy_list);
        for (int i = 0; i <= 35; i++) {
            TapizyListModel tapizyListModel1 = new TapizyListModel("A", R.drawable.daily_fun);
            tapizyListModels.add(tapizyListModel1);
        }

        adapter = new TapizyListAdapter(mContext, tapizyListModels);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mContext, 4);
        rv_tapizy_list.setLayoutManager(mLayoutManager);
        rv_tapizy_list.setItemAnimator(new DefaultItemAnimator());
        rv_tapizy_list.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void logoutbutton() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.my_profile) {
            startActivity(new Intent(mContext, MyProfileActivity.class));

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_settng) {
            startActivity(new Intent(mContext, SettingActivity.class));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
                intent.putExtra("name", strUserId + "chatbot");
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
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AppPreference.getBooleanPreference(mContext, "update")) {
            getPreferenceData();
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
}
