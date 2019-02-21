package infobite.com.tapizy.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import infobite.com.tapizy.R;
import infobite.com.tapizy.adapter.RecyclerViewDataAdapter;
import infobite.com.tapizy.model.SectionDataModel;
import infobite.com.tapizy.model.SingleItemModel;

public class TrandingActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private TextView descoverBtn, trendingBtn;
    private LinearLayout layout1;
    private RecyclerView my_recycler_view;
    private ArrayList<SectionDataModel> allSampleData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tranding);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        layout1 = (LinearLayout) findViewById(R.id.layout1);
        trendingBtn = (TextView) findViewById(R.id.trendingBtn);
        descoverBtn = (TextView) findViewById(R.id.descoverBtn);
        descoverBtn.setOnClickListener(this);
        trendingBtn.setOnClickListener(this);

        allSampleData = new ArrayList<SectionDataModel>();
        setSupportActionBar(toolbar);
        toolbar.setTitle("Tapizy");
        createDummyData();

        my_recycler_view = (RecyclerView) findViewById(R.id.my_recycler_view);
        my_recycler_view.setHasFixedSize(true);
        RecyclerViewDataAdapter adapter = new RecyclerViewDataAdapter(this, allSampleData);
        my_recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        my_recycler_view.setAdapter(adapter);
    }

    public void createDummyData() {
        for (int i = 1; i <= 5; i++) {
            SectionDataModel dm = new SectionDataModel();
            ArrayList<SingleItemModel> singleItem = new ArrayList<SingleItemModel>();
            for (int j = 0; j <= 5; j++) {
                singleItem.add(new SingleItemModel("Item " + j, "URL " + j, "Description " + j));
            }
            dm.setAllItemsInSection(singleItem);
            allSampleData.add(dm);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.trendingBtn:
                layout1.setVisibility(View.GONE);
                my_recycler_view.setVisibility(View.VISIBLE);
                trendingBtn.setTextColor(Color.WHITE);
                descoverBtn.setTextColor(Color.BLACK);

                break;
            case R.id.descoverBtn:
                layout1.setVisibility(View.VISIBLE);
                my_recycler_view.setVisibility(View.GONE);
                trendingBtn.setTextColor(Color.BLACK);
                descoverBtn.setTextColor(Color.WHITE);
                break;
        }
    }
}

