package ibt.com.tapizy.ui.activity.user_activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.LinearLayout;

import ibt.com.tapizy.R;
import ibt.com.tapizy.constant.Constant;
import ibt.com.tapizy.ui.fragment.user_fragment.profile_account.MyProfileFragment;
import ibt.com.tapizy.ui.fragment.user_fragment.profile_account.UserAccountFragment;
import ibt.com.tapizy.utils.AppPreference;
import ibt.com.tapizy.utils.BaseActivity;
import ibt.com.tapizy.utils.FragmentUtils;

public class UserProfileAccountActivity extends BaseActivity implements View.OnClickListener {

    private TabLayout tabLayout;
    private LinearLayout container;

    public static FragmentUtils fragmentUtilsProfile;
    private FragmentManager fragmentManagerProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_account);

        init();
    }

    private void init() {
        findViewById(R.id.imgBack).setOnClickListener(this);
        tabLayout = findViewById(R.id.tab_layout);
        container = findViewById(R.id.fragment_container);
        myCoinsApiTrending("Profile/Account");

        fragmentManagerProfile = getSupportFragmentManager();
        fragmentUtilsProfile = new FragmentUtils(fragmentManagerProfile);

        tabLayout.addTab(tabLayout.newTab().setText("Profile"));
        tabLayout.addTab(tabLayout.newTab().setText("Account"));

        fragmentUtilsProfile.replaceFragment(new MyProfileFragment(), Constant.MyProfileFragment, R.id.fragment_container);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    fragmentUtilsProfile.replaceFragment(new MyProfileFragment(), Constant.MyProfileFragment, R.id.fragment_container);
                } else if (tab.getPosition() == 1) {
                    fragmentUtilsProfile.replaceFragment(new UserAccountFragment(), Constant.UserAccountFragment, R.id.fragment_container);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AppPreference.getBooleanPreference(mContext, Constant.IS_PROFILE_UPDATE)) {
            fragmentUtilsProfile.replaceFragment(new MyProfileFragment(), Constant.MyProfileFragment, R.id.fragment_container);
            AppPreference.setBooleanPreference(mContext, Constant.IS_PROFILE_UPDATE, false);
        }
    }
}
