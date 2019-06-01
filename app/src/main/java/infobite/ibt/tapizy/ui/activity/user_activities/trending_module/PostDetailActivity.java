package infobite.ibt.tapizy.ui.activity.user_activities.trending_module;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultAllocator;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import infobite.ibt.tapizy.R;
import infobite.ibt.tapizy.adapter.CommentListAdapter;
import infobite.ibt.tapizy.constant.Constant;
import infobite.ibt.tapizy.model.comment_list_modal.CommentMainModal;
import infobite.ibt.tapizy.model.timeline_modal.Comment;
import infobite.ibt.tapizy.model.timeline_modal.DailyNewsFeedMainModal;
import infobite.ibt.tapizy.model.timeline_modal.UserFeed;
import infobite.ibt.tapizy.retrofit_provider.RetrofitService;
import infobite.ibt.tapizy.retrofit_provider.WebResponse;
import infobite.ibt.tapizy.utils.Alerts;
import infobite.ibt.tapizy.utils.AppPreference;
import infobite.ibt.tapizy.utils.BaseActivity;
import infobite.ibt.tapizy.utils.exoplayer.VideoPlayerConfig;
import okhttp3.ResponseBody;
import retrofit2.Response;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class PostDetailActivity extends BaseActivity implements View.OnClickListener {

    private UserFeed newPostModel;

    private LinearLayout llPostComment;
    private RelativeLayout rlVideoView;
    private ImageView imgPostImage;
    private CircleImageView imgUserProfile;
    private TextView tvUserName, tvPostLikeCount, tvUnlikeCount, tvPostTime, tvPostDescription, tvHeadline;

    private RecyclerView recyclerViewCommentList;
    private CommentListAdapter commentListAdapter;
    private List<Comment> commentList = new ArrayList<>();

    private String strId = "";
    private String strFrom = "";
    private String postId = "";
    private SwipeRefreshLayout swipeRefreshLayout;

    private SimpleExoPlayer player;
    private PlayerView videoSurfaceView;
    private ProgressBar mProgressBar;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        init();
    }

    private void init() {
        frameLayout = findViewById(R.id.video_layout);
        strId = AppPreference.getStringPreference(mContext, Constant.USER_ID);
        mProgressBar = findViewById(R.id.mProgressBar);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);
        strFrom = getIntent().getStringExtra("get_from");
        postId = getIntent().getStringExtra("post_id");

        recyclerViewCommentList = findViewById(R.id.recyclerViewCommentList);
        recyclerViewCommentList.setHasFixedSize(true);
        recyclerViewCommentList.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerViewCommentList.setItemAnimator(new DefaultItemAnimator());

        llPostComment = findViewById(R.id.llPostComment);
        findViewById(R.id.llLikePost).setOnClickListener(this);
        findViewById(R.id.llUnlikePost).setOnClickListener(this);
        rlVideoView = findViewById(R.id.rlVideoView);
        imgUserProfile = findViewById(R.id.imgUserProfile);
        tvUserName = findViewById(R.id.tvUserName);
        tvHeadline = findViewById(R.id.tvHeadline);
        imgPostImage = findViewById(R.id.imgPostImage);
        tvPostLikeCount = findViewById(R.id.tvPostLikeCount);
        tvUnlikeCount = findViewById(R.id.tvUnlikeCount);
        tvPostTime = findViewById(R.id.tvPostTime);
        tvPostDescription = findViewById(R.id.tvPostDescription);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //postDetailApi();
            }
        });
        postDetailApi();
        initPlayer();
        myCoinsApi();
    }

    /*
     * Player initialise
     * */
    private void initPlayer() {
        videoSurfaceView = new PlayerView(mContext);
        frameLayout.addView(videoSurfaceView);
        videoSurfaceView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM);
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);
        LoadControl loadControl = new DefaultLoadControl(
                new DefaultAllocator(true, 16),
                VideoPlayerConfig.MIN_BUFFER_DURATION,
                VideoPlayerConfig.MAX_BUFFER_DURATION,
                VideoPlayerConfig.MIN_PLAYBACK_START_BUFFER,
                VideoPlayerConfig.MIN_PLAYBACK_RESUME_BUFFER, -1, true);
        player = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector, loadControl);
        videoSurfaceView.setUseController(false);
        videoSurfaceView.setPlayer(player);
    }

    /* Post detail api */
    private void postDetailApi() {
        String userType = AppPreference.getStringPreference(mContext, Constant.USER_TYPE);
        if (cd.isNetworkAvailable()) {
            RetrofitService.showPostTimeLine(new Dialog(mContext), retrofitApiClient.postDetail(postId, userType, strId), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    DailyNewsFeedMainModal dailyNewsFeedMainModal = (DailyNewsFeedMainModal) result.body();
                    if (dailyNewsFeedMainModal != null)
                        if (dailyNewsFeedMainModal.getUserFeed() != null)
                            newPostModel = dailyNewsFeedMainModal.getUserFeed().get(0);
                    setDataInModal();
                }

                @Override
                public void onResponseFailed(String error) {
                    Alerts.show(mContext, error);
                }
            });
        } else {
            cd.show(mContext);
        }
    }

    private void setDataInModal() {
        commentList.clear();
        if (newPostModel.getComment() != null) {
            commentList.addAll(newPostModel.getComment());
        }

        findViewById(R.id.post_comment_send).setOnClickListener(this);
        llPostComment.setOnClickListener(this);

        if (!newPostModel.getIsLike().isEmpty()) {
            if (newPostModel.getIsLike().equals("like")) {
                ((ImageView) findViewById(R.id.imgLike)).setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_hot));
                ((ImageView) findViewById(R.id.imgUnlike)).setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_cold_b));
            } else {
                ((ImageView) findViewById(R.id.imgLike)).setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_hot_b));
                ((ImageView) findViewById(R.id.imgUnlike)).setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_cold));
            }
        } else {
            ((ImageView) findViewById(R.id.imgLike)).setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_hot_b));
            ((ImageView) findViewById(R.id.imgUnlike)).setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_cold_b));
        }

        setData();
        setCommentList();
        myCoinsApiTrending("");
    }

    private void setData() {
        tvUserName.setText(newPostModel.getUName());
        tvPostDescription.setText(newPostModel.getPostDescription());

        Glide.with(mContext)
                .load(R.drawable.app_icon)
                .load(Constant.PROFILE_IMAGE_BASE_URL + newPostModel.getUProfile())
                .into(imgUserProfile);

        if (!newPostModel.getHeadline().isEmpty()) {
            tvHeadline.setVisibility(View.VISIBLE);
            imgPostImage.setVisibility(View.GONE);
            rlVideoView.setVisibility(View.GONE);
            tvHeadline.setText(newPostModel.getHeadline());
        } else if (!newPostModel.getImage().isEmpty()) {
            imgPostImage.setVisibility(View.VISIBLE);
            tvHeadline.setVisibility(View.GONE);
            rlVideoView.setVisibility(View.GONE);
            Glide.with(mContext)
                    .load(R.drawable.app_icon)
                    .load(Constant.IMAGE_BASE_URL + newPostModel.getImage())
                    .into(imgPostImage);
        } else if (!newPostModel.getVideo().isEmpty()) {
            rlVideoView.setVisibility(View.VISIBLE);
            tvHeadline.setVisibility(View.GONE);
            imgPostImage.setVisibility(View.GONE);
            String strVideoUrl = newPostModel.getVideo();
            initVideoView(strVideoUrl);
        }

        tvPostLikeCount.setText(newPostModel.getTotalLike());
        tvUnlikeCount.setText(newPostModel.getTotalUnlike());

        if (newPostModel.getEntryDate() == null || newPostModel.getEntryDate().isEmpty()) {
            tvPostTime.setText("");
        } else {
            tvPostTime.setText(newPostModel.getEntryDate());
        }
    }

    /*************************/
    /*Play video*/
    private void initVideoView(String strVideoUrl) {
        /****************************************/
        videoSurfaceView.requestFocus();
        videoSurfaceView.setPlayer(player);

        DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter();
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(mContext, Util.getUserAgent
                (mContext, "android_wave_list"), defaultBandwidthMeter);
        String uriString = Constant.VIDEO_BASE_URL + strVideoUrl;
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(uriString));
        player.prepare(videoSource);
        player.setPlayWhenReady(true);

        player.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                switch (playbackState) {

                    case Player.STATE_BUFFERING:
                        videoSurfaceView.setAlpha(0.5f);
                        if (mProgressBar != null) {
                            mProgressBar.setVisibility(VISIBLE);
                        }
                        break;
                    case Player.STATE_ENDED:
                        player.seekTo(0);
                        break;
                    case Player.STATE_IDLE:
                        break;
                    case Player.STATE_READY:
                        if (mProgressBar != null) {
                            mProgressBar.setVisibility(GONE);
                        }
                        videoSurfaceView.setVisibility(VISIBLE);
                        videoSurfaceView.setAlpha(1);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {

            }
        });
    }

    public void removePlayer() {
        player.release();
        player = null;
        videoSurfaceView.setVisibility(INVISIBLE);
        videoSurfaceView.removeAllViews();
    }

    private void setCommentList() {
        commentListAdapter = new CommentListAdapter(commentList, this);
        recyclerViewCommentList.setAdapter(commentListAdapter);
        commentListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llLikePost:
                likeApi(newPostModel, ((TextView) findViewById(R.id.tvPostLikeCount)), "1", "0");
                break;
            case R.id.llUnlikePost:
                likeApi(newPostModel, ((TextView) findViewById(R.id.tvPostLikeCount)), "0", "1");
                break;
            case R.id.llPostComment:
                ((CardView) findViewById(R.id.cardViewComment)).setVisibility(View.VISIBLE);
                break;
            case R.id.post_comment_send:
                postCommentApi();
                LinearLayout mainLayout = (LinearLayout) findViewById(R.id.myLinearLayout);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);
                break;
        }
    }

    private void postCommentApi() {
        String userType = AppPreference.getStringPreference(mContext, Constant.USER_TYPE);
        String strPostId = newPostModel.getPostId();
        String strComments = ((EditText) findViewById(R.id.edit_post_comment)).getText().toString();

        if (!strComments.isEmpty()) {
            RetrofitService.postCommentResponse(retrofitApiClient.newPostComment(strPostId, strId, strComments, userType),
                    new WebResponse() {
                        @Override
                        public void onResponseSuccess(Response<?> result) {
                            CommentMainModal commentResponseModal = (CommentMainModal) result.body();
                            commentList.clear();
                            if (strFrom.equals("user")) {
                                AppPreference.setBooleanPreference(mContext, Constant.IS_DATA_UPDATE, false);
                            } else {
                                AppPreference.setBooleanPreference(mContext, Constant.IS_DATA_UPDATE, true);
                            }
                            //timelineApi();
                            if (commentResponseModal == null)
                                return;
                            commentList.addAll(commentResponseModal.getComment());
                            commentListAdapter.notifyDataSetChanged();
                            ((EditText) findViewById(R.id.edit_post_comment)).setText("");
                        }

                        @Override
                        public void onResponseFailed(String error) {
                            AppPreference.setBooleanPreference(mContext, Constant.IS_DATA_UPDATE, false);
                            Alerts.show(mContext, error);
                        }
                    });
        } else {
            Alerts.show(mContext, "Enter some comments!!!");
        }
    }

    /***********************************************************************/
    /*
     * Like/Unlike function
     * */
    private void likeApi(final UserFeed feed, final TextView textView, String strLike, String strUnlike) {
        String userType = AppPreference.getStringPreference(mContext, Constant.USER_TYPE);
        if (cd.isNetworkAvailable()) {
            RetrofitService.getLikeResponse(retrofitApiClient.postLike(feed.getPostId(), strId, strLike, strUnlike,
                    userType), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    ResponseBody responseBody = (ResponseBody) result.body();
                    try {
                        JSONObject jsonObject = new JSONObject(responseBody.string());
                        if (!jsonObject.getBoolean("error")) {
                            postDetailApi();
                            textView.setText(jsonObject.getString("likes"));
                            textView.setText(jsonObject.getString("likes"));
                        } else {
                            Alerts.show(mContext, jsonObject.getString("message"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onResponseFailed(String error) {
                    Alerts.show(mContext, error);
                }
            });
        } else {
            cd.show(mContext);
        }
    }

    /*****************************************************************************************/
    /*
     *
     * */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (player != null) {
            removePlayer();
        } else {
            finish();
        }
    }
}
