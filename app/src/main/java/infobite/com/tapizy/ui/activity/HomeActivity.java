package infobite.com.tapizy.ui.activity;

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

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import infobite.com.tapizy.R;
import infobite.com.tapizy.adapter.TapizyListAdapter;
import infobite.com.tapizy.constant.Constant;
import infobite.com.tapizy.model.TapizyListModel;
import infobite.com.tapizy.model.User;
import infobite.com.tapizy.ui.activity.community_module.CommunityActivity;
import infobite.com.tapizy.ui.activity.recent_chat.RecentChatActivity;
import infobite.com.tapizy.ui.activity.trending_module.TrendingActivity;
import infobite.com.tapizy.utils.BaseActivity;

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
    }

    private void setUserData() {
        View header = navigationView.getHeaderView(0);
        Glide.with(mContext)
                .load(Constant.PROFILE_IMAGE_BASE_URL + User.getUser().getUser().getUProfile())
                .into(((CircleImageView) header.findViewById(R.id.profile_image)));

        ((TextView) header.findViewById(R.id.tvUserName)).setText(User.getUser().getUser().getUName());
        ((TextView) header.findViewById(R.id.tvEmail)).setText(User.getUser().getUser().getUEmail());
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

        } else if (id == R.id.logout_arrow) {
            logoutbutton();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llexplore:
                startActivity(new Intent(mContext, NewActivity.class));
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
        }
    }
}
