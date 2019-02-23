package infobite.com.tapizy.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import infobite.com.tapizy.R;
import infobite.com.tapizy.adapter.CityListAdapter;
import infobite.com.tapizy.utils.BaseActivity;

public class CityListActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView rclvCityList;
    private ImageView ivbackActivity;
    private CityListAdapter cityListAdapter;
    private List<String> cityList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);

        rclvCityList = findViewById(R.id.rclv_cityList);
        ivbackActivity = findViewById(R.id.ic_back_city);
        ivbackActivity.setOnClickListener(this);

        rclvCityList.setHasFixedSize(true);
        rclvCityList.setLayoutManager(new LinearLayoutManager(this));

        cityList.add("Ujjain");
        cityList.add("Indore");
        cityList.add("Bhopal");
        cityList.add("Gwalior");
        cityList.add("JabalPur");
        cityList.add("Shahdol");
        cityList.add("AvantikaPuri");
        cityList.add("HoshangaBad");
        cityList.add("Khandwa");
        cityList.add("Khargone");

        cityListAdapter = new CityListAdapter(mContext,cityList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        rclvCityList.setItemAnimator(new DefaultItemAnimator());
        rclvCityList.setLayoutManager(layoutManager);
        rclvCityList.setAdapter(cityListAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ic_back_city:
                finish();
                break;
        }
    }
}

