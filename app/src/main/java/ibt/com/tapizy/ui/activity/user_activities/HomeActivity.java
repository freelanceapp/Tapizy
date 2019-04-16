package ibt.com.tapizy.ui.activity.user_activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

import ibt.com.tapizy.R;
import ibt.com.tapizy.constant.Constant;
import ibt.com.tapizy.model.User;
import ibt.com.tapizy.ui.fragment.user_fragment.AvailabilityFragment;
import ibt.com.tapizy.ui.fragment.user_fragment.HomeFragment;
import ibt.com.tapizy.ui.fragment.user_fragment.MyBotListFragment;
import ibt.com.tapizy.ui.fragment.user_fragment.RewardsFragment;
import ibt.com.tapizy.ui.fragment.user_fragment.profile_account.MyProfileFragment;
import ibt.com.tapizy.utils.AppPreference;
import ibt.com.tapizy.utils.BaseActivity;
import ibt.com.tapizy.utils.FragmentUtils;
import ibt.com.tapizy.utils.custom_navigation_drawer.MenuItem;
import ibt.com.tapizy.utils.custom_navigation_drawer.SNavigationDrawer;

public class HomeActivity extends BaseActivity implements SNavigationDrawer.OnMenuItemClickListener, SNavigationDrawer.DrawerListener {

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
        menuItems.add(new MenuItem("My Bot", R.drawable.nav_feed_bg));
        menuItems.add(new MenuItem("Create Bot", R.drawable.nav_message_bg));
        menuItems.add(new MenuItem("24*7", R.drawable.nav_music_bg));
        menuItems.add(new MenuItem("Rewards", R.drawable.nav_news_bg));
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
                //strTitle = "Profile";
                /*fragmentTag = Constant.MyProfileFragment;
                fragmentClass = MyProfileFragment.class;
                sNavigationDrawer.setAppbarTitleTV(strTitle);*/
                startActivity(new Intent(mContext, UserProfileAccountActivity.class));
                break;
            case 2:
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
                break;
            case 4:
                strTitle = "24*7";
                fragmentTag = Constant.AvailabilityFragment;
                fragmentClass = AvailabilityFragment.class;
                sNavigationDrawer.setAppbarTitleTV(strTitle);
                break;
            case 5:
                strTitle = "Rewards";
                fragmentTag = Constant.RewardsFragment;
                fragmentClass = RewardsFragment.class;
                sNavigationDrawer.setAppbarTitleTV(strTitle);
                break;
            case 6:
                sNavigationDrawer.setAppbarTitleTV(strTitle);
                Intent intentS = new Intent(mContext, SettingActivity.class);
                startActivity(intentS);
                break;
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
