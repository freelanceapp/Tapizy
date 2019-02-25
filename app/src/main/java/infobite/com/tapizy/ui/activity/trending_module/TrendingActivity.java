package infobite.com.tapizy.ui.activity.trending_module;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import infobite.com.tapizy.R;
import infobite.com.tapizy.adapter.TimelineListAdapter;
import infobite.com.tapizy.constant.Constant;
import infobite.com.tapizy.model.timeline_modal.DailyNewsFeedMainModal;
import infobite.com.tapizy.model.timeline_modal.UserFeed;
import infobite.com.tapizy.retrofit_provider.RetrofitService;
import infobite.com.tapizy.retrofit_provider.WebResponse;
import infobite.com.tapizy.utils.Alerts;
import infobite.com.tapizy.utils.AppPreference;
import infobite.com.tapizy.utils.BaseActivity;
import retrofit2.Response;

import static android.widget.LinearLayout.VERTICAL;

public class TrendingActivity extends BaseActivity implements View.OnClickListener {

    private TimelineListAdapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    /***********************************************/
    //private NewsFeedAdapter newPostAdapter;
    private List<UserFeed> feedList = new ArrayList<>();
    private DailyNewsFeedMainModal dailyNewsFeedMainModal;
    private RecyclerView recyclerViewFeed;
    private String strId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trending);

        init();
    }

    private void init() {
        strId = AppPreference.getStringPreference(mContext, Constant.USER_ID);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);
        recyclerViewFeed = findViewById(R.id.recyclerViewFeed);
        mAdapter = new TimelineListAdapter(mContext, feedList, this, retrofitApiClient);
        recyclerViewFeed.setLayoutManager(new LinearLayoutManager(mContext, VERTICAL, false));

        recyclerViewFeed.setItemAnimator(new DefaultItemAnimator());
        recyclerViewFeed.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        findViewById(R.id.fabNewPost).setOnClickListener(this);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                timelineApi();
            }
        });
        timelineApi();
    }

    public void timelineApi() {
        if (cd.isNetworkAvailable()) {
            RetrofitService.showPostTimeLine(new Dialog(mContext), retrofitApiClient.showPostTimeLine(strId), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    dailyNewsFeedMainModal = (DailyNewsFeedMainModal) result.body();
                    feedList.clear();
                    if (dailyNewsFeedMainModal.getError()) {
                        Alerts.show(mContext, dailyNewsFeedMainModal.getMessage());
                    } else {
                        Gson gson = new GsonBuilder().setLenient().create();
                        String data = gson.toJson(dailyNewsFeedMainModal);
                        AppPreference.setStringPreference(mContext, Constant.TIMELINE_DATA, data);

                        if (dailyNewsFeedMainModal.getFeed().size() > 0) {
                            feedList.addAll(dailyNewsFeedMainModal.getFeed());
                        }
                        //init();
                    }
                    mAdapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                }

                @Override
                public void onResponseFailed(String error) {
                    Alerts.show(mContext, error);
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlPost:
            case R.id.llPostComment:
                int position = Integer.parseInt(v.getTag().toString());
                Gson gson = new GsonBuilder().setLenient().create();
                String data = gson.toJson(feedList.get(position));
                AppPreference.setStringPreference(mContext, Constant.POST_DETAIL, data);
                Intent intent = new Intent(mContext, PostDetailActivity.class);
                intent.putExtra("get_from", "timeline");
                intent.putExtra("post_id", feedList.get(position).getFeedId());
                startActivity(intent);
                break;
            case R.id.fabNewPost:
                startActivity(new Intent(mContext, NewPostActivity.class));
                break;
        }
    }

}
