package infobite.com.tapizy.ui.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import infobite.com.tapizy.ui.Fragment.TapizyListFragment;
import infobite.com.tapizy.R;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Fragment fragment;
    LinearLayout menuLayout;
    FloatingActionButton menu_btn;
    boolean menuCondition = true;
    RelativeLayout container;
    TextView titleName;
    ImageView searchBtn;
    FrameLayout frame_container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        titleName = (TextView) findViewById(R.id.titleName);
        titleName.setText("Tapizy");

        frame_container = (FrameLayout) findViewById(R.id.frame_container);
        searchBtn = (ImageView) findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this,"Search", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(HomeActivity.this, TapizyActivity.class);
                startActivity(intent);
            }
        });

        fragment = new TapizyListFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

     /* BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);*/
        //toolbar.setTitle("Tapizy");

        menuLayout = (LinearLayout) findViewById(R.id.menuLayout);
        container = (RelativeLayout) findViewById(R.id.container);
        menu_btn = (FloatingActionButton) findViewById(R.id.menu_btn);
        menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (menuCondition == true) {
                    menuLayout.setVisibility(View.VISIBLE);
                    menuCondition = false;
                    menu_btn.setImageResource(R.drawable.ic_cancle);
                    container.setBackgroundResource(R.color.black_trans1);
                    frame_container.setVisibility(View.GONE);
                }else {
                    menuLayout.setVisibility(View.GONE);
                    menuCondition = true;
                    menu_btn.setImageResource(R.drawable.ic_menu);
                    container.setBackgroundResource(R.color.white);
                    frame_container.setVisibility(View.VISIBLE);
                }
            }
        });

    }


   /* private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_shop:
                    //toolbar.setTitle("Shop");
                    fragment = new TapizyListFragment();
                    loadFragment(fragment);
                    menuLayout.setVisibility(View.GONE);
                    menuCondition = true;
                    menu_btn.setImageResource(R.drawable.ic_menu);
                    container.setBackgroundResource(R.color.white);
                    return true;
                case R.id.navigation_gifts:
                    // toolbar.setTitle("My Gifts");
                    fragment = new TapizyListFragment();
                    loadFragment(fragment);
                    menuLayout.setVisibility(View.GONE);
                    menuCondition = true;
                    menu_btn.setImageResource(R.drawable.ic_menu);
                    container.setBackgroundResource(R.color.white);
                    return true;
                case R.id.navigation_cart:
                    //  toolbar.setTitle("Cart");
                  *//*  fragment = new TapizyMessageFragment();
                    loadFragment(fragment);
                    menuLayout.setVisibility(View.GONE);
                    menuCondition = true;
                    menu_btn.setImageResource(R.drawable.ic_menu);
                    container.setBackgroundResource(R.color.white);*//*
                  Intent intent = new Intent(HomeActivity.this, ChatActivity.class);
                  startActivity(intent);
                    return true;
            }

            return false;
        }
    };*/

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
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
    private void logoutbutton(){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.logout_arrow){
        logoutbutton();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
