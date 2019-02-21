package infobite.com.tapizy.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import infobite.com.tapizy.R;
import infobite.com.tapizy.adapter.TapiziLinearListAdapter;
import infobite.com.tapizy.adapter.TapizyListAdapter;
import infobite.com.tapizy.model.TapizyLinearListModel;
import infobite.com.tapizy.model.TapizyLinearListModel;
import infobite.com.tapizy.utils.BaseActivity;

public class NewActivity extends BaseActivity {
    private TapiziLinearListAdapter adapter;
    private ArrayList<TapizyLinearListModel> tapizyListModels = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        RecyclerView rclvll = findViewById(R.id.rv_lllayout);

        TapizyLinearListModel tapizyListModel1 = new TapizyLinearListModel( R.drawable.daily_fun,"Indore Live","Is is Subtitle");
        tapizyListModels.add(tapizyListModel1);
        TapizyLinearListModel tapizyListModel2 = new TapizyLinearListModel(R.drawable.daily_fun,"Indore Live","Is is Subtitle");
        tapizyListModels.add(tapizyListModel2);
        TapizyLinearListModel tapizyListModel3 = new TapizyLinearListModel(R.drawable.daily_fun,"Indore Live","Is is Subtitle");
        tapizyListModels.add(tapizyListModel3);
        TapizyLinearListModel tapizyListModel4 = new TapizyLinearListModel(R.drawable.daily_fun,"Indore Live","Is is Subtitle");
        tapizyListModels.add(tapizyListModel4);
        TapizyLinearListModel tapizyListModel5 = new TapizyLinearListModel(R.drawable.daily_fun,"Indore Live","Is is Subtitle");
        tapizyListModels.add(tapizyListModel5);

        adapter = new TapiziLinearListAdapter(mContext, tapizyListModels);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        rclvll.setLayoutManager(mLayoutManager);
        rclvll.setItemAnimator(new DefaultItemAnimator());
        rclvll.setAdapter(adapter);
    }
}
