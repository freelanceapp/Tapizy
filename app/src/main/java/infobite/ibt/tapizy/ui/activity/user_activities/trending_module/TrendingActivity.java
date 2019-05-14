package infobite.ibt.tapizy.ui.activity.user_activities.trending_module;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import infobite.ibt.tapizy.R;
import infobite.ibt.tapizy.adapter.TimelineListAdapter;
import infobite.ibt.tapizy.constant.Constant;
import infobite.ibt.tapizy.model.timeline_modal.DailyNewsFeedMainModal;
import infobite.ibt.tapizy.model.timeline_modal.UserFeed;
import infobite.ibt.tapizy.pagination_listener.PaginationScrollListener;
import infobite.ibt.tapizy.retrofit_provider.RetrofitService;
import infobite.ibt.tapizy.retrofit_provider.WebResponse;
import infobite.ibt.tapizy.utils.Alerts;
import infobite.ibt.tapizy.utils.AppPreference;
import infobite.ibt.tapizy.utils.BaseActivity;
import infobite.ibt.tapizy.utils.TimeUtils;
import retrofit2.Response;

import static android.widget.LinearLayout.VERTICAL;

public class TrendingActivity extends BaseActivity implements View.OnClickListener {

    private List<UserFeed> mInfoList = new ArrayList<>();
    private TimelineListAdapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayoutManager linearLayoutManager;
    private static final int PAGE_START = 1;
    private int currentPage = PAGE_START;
    private static int TOTAL_PAGES;
    private boolean isLoading = false;
    private boolean isLastPage = false;

    /***********************************************/
    //private NewsFeedAdapter newPostAdapter;
    private DailyNewsFeedMainModal dailyNewsFeedMainModal;
    private RecyclerView recyclerViewFeed;
    private String strId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trending);

        init();

        myCoinsApiTrending("Trending");
    }

    private void init() {
        strId = AppPreference.getStringPreference(mContext, Constant.USER_ID);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);
        recyclerViewFeed = findViewById(R.id.recyclerViewFeed);
        mAdapter = new TimelineListAdapter(mContext, this, retrofitApiClient);
        recyclerViewFeed.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(mContext, VERTICAL, false);
        recyclerViewFeed.setLayoutManager(linearLayoutManager);
        recyclerViewFeed.setItemAnimator(new DefaultItemAnimator());
        recyclerViewFeed.setAdapter(mAdapter);
        recyclerViewFeed.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                loadNextPage();
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        mAdapter.notifyDataSetChanged();

        findViewById(R.id.fabNewPost).setOnClickListener(this);
        findViewById(R.id.imgBack).setOnClickListener(this);

        timelineApi();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.getTripList().clear();
                mInfoList.clear();
                mAdapter.notifyDataSetChanged();
                timelineApi();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void timelineApi() {
        currentPage = PAGE_START;
        String userType = AppPreference.getStringPreference(mContext, Constant.USER_TYPE);
        if (cd.isNetworkAvailable()) {
            RetrofitService.showPostTimeLine(new Dialog(mContext), retrofitApiClient.showPostTimeLine(strId,
                    userType, "1", TimeUtils.getSecondDateTime()), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    dailyNewsFeedMainModal = (DailyNewsFeedMainModal) result.body();
                    if (dailyNewsFeedMainModal != null) {
                        if (dailyNewsFeedMainModal.getError()) {
                            Alerts.show(mContext, dailyNewsFeedMainModal.getMessage());
                        } else {
                            TOTAL_PAGES = dailyNewsFeedMainModal.getPageCount();
                            mAdapter.addAll(dailyNewsFeedMainModal.getUserFeed());
                            if (currentPage < TOTAL_PAGES) {
                                mAdapter.addLoadingFooter();
                                isLastPage = false;
                            } else if (currentPage == TOTAL_PAGES) {
                                mAdapter.removeLoadingFooter();
                                isLastPage = true;
                            }
                        }
                        mAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    mInfoList.addAll(mAdapter.getTripList());
                }

                @Override
                public void onResponseFailed(String error) {
                    Alerts.show(mContext, error);
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        }
    }

    public void loadNextPage() {
        String userType = AppPreference.getStringPreference(mContext, Constant.USER_TYPE);
        if (cd.isNetworkAvailable()) {
            RetrofitService.showPostTimeLine(new Dialog(mContext), retrofitApiClient.showPostTimeLine(strId,
                    userType, currentPage + "", TimeUtils.getSecondDateTime()), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    dailyNewsFeedMainModal = (DailyNewsFeedMainModal) result.body();
                    if (dailyNewsFeedMainModal != null) {
                        if (dailyNewsFeedMainModal.getError()) {
                            Alerts.show(mContext, dailyNewsFeedMainModal.getMessage());
                        } else {
                            TOTAL_PAGES = dailyNewsFeedMainModal.getPageCount();
                            mAdapter.removeLoadingFooter();
                            isLoading = false;
                            mAdapter.addAll(dailyNewsFeedMainModal.getUserFeed());
                            if (currentPage != TOTAL_PAGES) mAdapter.addLoadingFooter();
                            else isLastPage = true;
                        }
                        mAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                        mInfoList.addAll(mAdapter.getTripList());
                    }
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
                String data = gson.toJson(mAdapter.getTripList().get(position));
                AppPreference.setBooleanPreference(mContext, Constant.POST_CLICK, true);
                AppPreference.setStringPreference(mContext, Constant.POST_DETAIL, data);
                Intent intent = new Intent(mContext, PostDetailActivity.class);
                intent.putExtra("get_from", "timeline");
                intent.putExtra("post_id", mAdapter.getTripList().get(position).getPostId());
                startActivity(intent);
                break;
            case R.id.fabNewPost:
                //startActivity(new Intent(mContext, NewPostActivity.class));
                break;
            case R.id.imgBack:
                finish();
                break;
            case R.id.llSharePost:
                int post = Integer.parseInt(v.getTag().toString());
                String postId = mAdapter.getTripList().get(post).getPostId();
                ShareCompat.IntentBuilder.from(this)
                        .setType("text/plain")
                        .setChooserTitle("Share Tapizy post")
                        .setText(Constant.SHARE_APP)
                        .startChooser();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AppPreference.getBooleanPreference(mContext, Constant.POST_CLICK)) {
            myCoinsApiTrending("Trending");
            AppPreference.setBooleanPreference(mContext, Constant.POST_CLICK, false);
        }
    }
}
