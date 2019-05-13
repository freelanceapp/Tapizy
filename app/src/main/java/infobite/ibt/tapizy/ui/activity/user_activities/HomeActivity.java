package infobite.ibt.tapizy.ui.activity.user_activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

import infobite.ibt.tapizy.R;
import infobite.ibt.tapizy.constant.Constant;
import infobite.ibt.tapizy.model.User;
import infobite.ibt.tapizy.retrofit_provider.RetrofitApiClient;
import infobite.ibt.tapizy.retrofit_provider.RetrofitService;
import infobite.ibt.tapizy.ui.fragment.user_fragment.AvailabilityFragment;
import infobite.ibt.tapizy.ui.fragment.user_fragment.HomeFragment;
import infobite.ibt.tapizy.ui.fragment.user_fragment.MyProfileFragment;
import infobite.ibt.tapizy.ui.fragment.user_fragment.ShareAppFragment;
import infobite.ibt.tapizy.ui.fragment.user_fragment.UserAccountFragment;
import infobite.ibt.tapizy.utils.AppPreference;
import infobite.ibt.tapizy.utils.BaseActivity;
import infobite.ibt.tapizy.utils.ConnectionDetector;
import infobite.ibt.tapizy.utils.FragmentUtils;
import infobite.ibt.tapizy.utils.custom_navigation_drawer.MenuItem;
import infobite.ibt.tapizy.utils.custom_navigation_drawer.SNavigationDrawer;

public class HomeActivity extends BaseActivity implements SNavigationDrawer.OnMenuItemClickListener, SNavigationDrawer.DrawerListener {

    public static RetrofitApiClient retrofitApiClient;
    public static ConnectionDetector cd;
    public static Context mContext;

    public static HomeActivity homeActivity;
    public static SNavigationDrawer sNavigationDrawer;

    public static FragmentUtils fragmentUtilsHome;
    private FragmentManager fragmentManager;
    private Class fragmentClass;
    private Fragment fragment;
    private String fragmentTag = Constant.HomeFragment;
    private String strTitle = "Home";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        homeActivity = this;

        mContext = this;
        cd = new ConnectionDetector(mContext);
        retrofitApiClient = RetrofitService.getRetrofit();

        AppPreference.setStringPreference(mContext, Constant.USER_TYPE, "user");
        AppPreference.setStringPreference(mContext, Constant.USER_ID, User.getUser().getUser().getUid());

        fragmentManager = getSupportFragmentManager();
        fragmentUtilsHome = new FragmentUtils(fragmentManager);
        fragmentClass = HomeFragment.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (fragment != null) {
            fragmentUtilsHome.replaceFragment(fragment, fragmentTag, R.id.frameLayout);
        }

        myCoinsApi();

        sNavigationDrawer = findViewById(R.id.navigationDrawer);
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("Home", R.drawable.nav_music_bg));
        menuItems.add(new MenuItem("Profile", R.drawable.nav_music_bg));
        /*menuItems.add(new MenuItem("My Bot", R.drawable.nav_feed_bg));*/
        menuItems.add(new MenuItem("Account", R.drawable.nav_message_bg));
        menuItems.add(new MenuItem("24*7", R.drawable.nav_music_bg));
        menuItems.add(new MenuItem("Share App", R.drawable.nav_news_bg));
        menuItems.add(new MenuItem("Setting", R.drawable.nav_message_bg));
        sNavigationDrawer.setMenuItemList(menuItems);
        sNavigationDrawer.setOnMenuItemClickListener(this);
        sNavigationDrawer.setDrawerListener(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sNavigationDrawer.setHeaderData();
            }
        }, 1000);
    }

    @Override
    public void onMenuItemClicked(int position) {
        switch (position) {
            case 0:
                strTitle = "Home";
                fragmentTag = Constant.HomeFragment;
                fragmentClass = HomeFragment.class;
                sNavigationDrawer.setAppbarTitleTV(strTitle);
                break;
            case 1:
                strTitle = "Profile";
                fragmentTag = Constant.MyProfileFragment;
                fragmentClass = MyProfileFragment.class;
                sNavigationDrawer.setAppbarTitleTV(strTitle);
                break;
            case 2:
                strTitle = "Account";
                fragmentTag = Constant.UserAccountFragment;
                fragmentClass = UserAccountFragment.class;
                sNavigationDrawer.setAppbarTitleTV(strTitle);
                break;
            case 3:
                strTitle = "24*7";
                fragmentTag = Constant.AvailabilityFragment;
                fragmentClass = AvailabilityFragment.class;
                sNavigationDrawer.setAppbarTitleTV(strTitle);
                break;
            case 4:
                strTitle = "Rewards";
                fragmentTag = Constant.RewardsFragment;
                fragmentClass = ShareAppFragment.class;
                sNavigationDrawer.setAppbarTitleTV(strTitle);
                break;
            case 5:
                sNavigationDrawer.setAppbarTitleTV(strTitle);
                Intent intentS = new Intent(mContext, SettingActivity.class);
                startActivity(intentS);
                break;
           /*case 2:
                strTitle = "Bot List";
                fragmentTag = Constant.MyBotListFragment;
                fragmentClass = MyBotListFragment.class;
                sNavigationDrawer.setAppbarTitleTV(strTitle);
                break;
            case 3:
                sNavigationDrawer.setAppbarTitleTV(strTitle);
                Intent intent = new Intent(mContext, CreateBotActivity.class);
                intent.putExtra("from", "user");
                startActivity(intent);
                break;*/
        }
    }

    @Override
    public void onDrawerOpening() {

    }

    @Override
    public void onDrawerClosing() {
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (fragment != null) {
            fragmentUtilsHome.replaceFragment(fragment, fragmentTag, R.id.frameLayout);
        }
    }

    @Override
    public void onDrawerOpened() {

    }

    @Override
    public void onDrawerClosed() {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        AppPreference.setStringPreference(mContext, Constant.USER_ID, User.getUser().getUser().getUid());
        AppPreference.setStringPreference(mContext, Constant.USER_TYPE, "user");
        myCoinsApi();
    }
}
