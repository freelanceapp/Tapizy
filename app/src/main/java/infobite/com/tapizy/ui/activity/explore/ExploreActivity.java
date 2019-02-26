package infobite.com.tapizy.ui.activity.explore;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import infobite.com.tapizy.R;
import infobite.com.tapizy.adapter.TapiziLinearListAdapter;
import infobite.com.tapizy.model.TapizyLinearListModel;
import infobite.com.tapizy.ui.activity.HomeActivity;
import infobite.com.tapizy.utils.BaseActivity;

public class ExploreActivity extends BaseActivity implements View.OnClickListener {

    private TapiziLinearListAdapter adapter;
    private ArrayList<TapizyLinearListModel> tapizyListModels = new ArrayList<>();
    private ImageView ivback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        RecyclerView rclvll = findViewById(R.id.rv_lllayout);
        ivback = findViewById(R.id.ic_back_newactivity);

        ivback.setOnClickListener(this);

        TapizyLinearListModel tapizyListModel4 = new TapizyLinearListModel(R.drawable.my_travel, "My Travel", "Best fight, Cab,Hotel guide");
        tapizyListModels.add(tapizyListModel4);
        TapizyLinearListModel tapizyListModel3 = new TapizyLinearListModel(R.drawable.daily_news, "Daily News", "Get Trending News");
        tapizyListModels.add(tapizyListModel3);
        TapizyLinearListModel tapizyListModel5 = new TapizyLinearListModel(R.drawable.my_money, "My Money", "Get Loans,Mutual Funds, Market Updates");
        tapizyListModels.add(tapizyListModel5);
        TapizyLinearListModel tapizyListModel6 = new TapizyLinearListModel(R.drawable.my_health, "My Health", "Get Fitness an Trainign Sessions");
        tapizyListModels.add(tapizyListModel6);
        TapizyLinearListModel tapizyListModel7 = new TapizyLinearListModel(R.drawable.my_shopping, "My Shopping", "Get Best Prices and Discount");
        tapizyListModels.add(tapizyListModel7);
        TapizyLinearListModel tapizyListModel8 = new TapizyLinearListModel(R.drawable.near_my_plans, "Near By Plans", "Best local events, Restaurants, Movies");
        tapizyListModels.add(tapizyListModel8);
        TapizyLinearListModel tapizyListModel9 = new TapizyLinearListModel(R.drawable.atoz_home_service, "A to Z Home Services", "Get Trusted and Quick home services");
        tapizyListModels.add(tapizyListModel9);
        TapizyLinearListModel tapizyListModel10 = new TapizyLinearListModel(R.drawable.ic_home_service, "My Property", "Buy, Sell and Rent Property");
        tapizyListModels.add(tapizyListModel10);

        adapter = new TapiziLinearListAdapter(mContext, tapizyListModels, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        rclvll.setLayoutManager(mLayoutManager);
        rclvll.setItemAnimator(new DefaultItemAnimator());
        rclvll.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ic_back_newactivity:
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                break;
            case R.id.lllayout:
                int pos = Integer.parseInt(v.getTag().toString());
                int CatId = pos + 1;
                Intent intentA = new Intent(this, BotListActivity.class);
                intentA.putExtra("cat_id", CatId + "");
                startActivity(intentA);
                break;
        }
    }
}