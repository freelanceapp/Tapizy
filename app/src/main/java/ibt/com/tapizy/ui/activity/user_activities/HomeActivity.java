package ibt.com.tapizy.ui.activity.user_activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import ibt.com.tapizy.R;
import ibt.com.tapizy.constant.Constant;
import ibt.com.tapizy.ui.fragment.user_fragment.AvailabilityFragment;
import ibt.com.tapizy.ui.fragment.user_fragment.HomeFragment;
import ibt.com.tapizy.ui.fragment.user_fragment.MyBotListFragment;
import ibt.com.tapizy.ui.fragment.user_fragment.MyProfileFragment;
import ibt.com.tapizy.ui.fragment.user_fragment.RewardsFragment;
import ibt.com.tapizy.utils.BaseActivity;
import ibt.com.tapizy.utils.FragmentUtils;
import ibt.com.tapizy.utils.custom_navigation_drawer.MenuItem;
import ibt.com.tapizy.utils.custom_navigation_drawer.SNavigationDrawer;

public class HomeActivity extends BaseActivity implements View.OnClickListener, SNavigationDrawer.OnMenuItemClickListener, SNavigationDrawer.DrawerListener {

    public static HomeActivity homeActivity;
    private SNavigationDrawer sNavigationDrawer;

    public static FragmentUtils fragmentUtilsHome;
    private FragmentManager fragmentManager;
    private Class fragmentClass;
    private Fragment fragment;
    private String fragmentTag = Constant.HomeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        homeActivity = this;

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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*case R.id.llProfile:
                startActivity(new Intent(mContext, MyProfileActivity.class));
                break;
            case R.id.llCreateBot:
                Intent intent = new Intent(mContext, CreateBotActivity.class);
                startActivity(intent);
                break;
            case R.id.llMyBot:
                Intent intentB = new Intent(mContext, MyBotListFragment.class);
                startActivity(intentB);
                break;
            case R.id.ll24_7:
                Alerts.show(mContext, "Under development !!!");
                break;
            case R.id.llRewards:
                Alerts.show(mContext, "Under development !!!");
                break;
            case R.id.llSettings:
                startActivity(new Intent(mContext, SettingActivity.class));
                break;*/
        }
    }

    @Override
    public void onMenuItemClicked(int position) {
        switch (position) {
            case 0:
                fragmentTag = Constant.HomeFragment;
                fragmentClass = HomeFragment.class;
                break;
            case 1:
                fragmentTag = Constant.MyProfileFragment;
                fragmentClass = MyProfileFragment.class;
                break;
            case 2:
                fragmentTag = Constant.MyBotListFragment;
                fragmentClass = MyBotListFragment.class;
                break;
            case 3:
                Intent intent = new Intent(mContext, CreateBotActivity.class);
                startActivity(intent);
                break;
            case 4:
                fragmentTag = Constant.AvailabilityFragment;
                fragmentClass = AvailabilityFragment.class;
                break;
            case 5:
                fragmentTag = Constant.RewardsFragment;
                fragmentClass = RewardsFragment.class;
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
}

/*  private void getPreferenceData() {
        Gson gson = new Gson();
        String json = AppPreference.getStringPreference(mContext, Constant.USER_DATA);
        UserDataMainModal loginUserModel = gson.fromJson(json, UserDataMainModal.class);
        User.setUser(loginUserModel);
        AppPreference.setBooleanPreference(mContext, "update", false);
        setUserData();
    }*/
