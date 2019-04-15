package ibt.com.tapizy.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ibt.com.tapizy.R;
import ibt.com.tapizy.constant.Constant;
import ibt.com.tapizy.model.timeline_modal.DailyNewsFeedMainModal;
import ibt.com.tapizy.model.timeline_modal.UserFeed;
import ibt.com.tapizy.retrofit_provider.RetrofitApiClient;
import ibt.com.tapizy.retrofit_provider.RetrofitService;
import ibt.com.tapizy.retrofit_provider.WebResponse;
import ibt.com.tapizy.utils.Alerts;
import ibt.com.tapizy.utils.AppPreference;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class TimelineListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_TEXT = 0;
    private static final int VIEW_TYPE_IMAGE = 1;
    private static final int VIEW_TYPE_VIDEO = 2;
    private static final int VIEW_TYPE_EMPTY = 3;

    private List<UserFeed> mInfoList;
    private Context mContext;
    private View.OnClickListener onClickListener;
    private RetrofitApiClient retrofitApiClient;

    public TimelineListAdapter(Context mContext, List<UserFeed> infoList, View.OnClickListener onClickListener,
                               RetrofitApiClient retrofitApiClient) {
        this.mContext = mContext;
        this.mInfoList = infoList;
        this.onClickListener = onClickListener;
        this.retrofitApiClient = retrofitApiClient;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_VIDEO:
                return new ViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.row_timeline_video, parent, false));
            case VIEW_TYPE_TEXT:
                return new HeadlineViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.row_timeline_text, parent, false));
            case VIEW_TYPE_IMAGE:
                return new ImageViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.row_timeline_image, parent, false));
            case VIEW_TYPE_EMPTY:
                return new EmptyViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.row_empty_data, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        /*holder.onBind(position);*/
        switch (holder.getItemViewType()) {
            case 0:
                final UserFeed feed;
                if (AppPreference.getBooleanPreference(mContext, "likedPost")) {
                    String strTimelineData = AppPreference.getStringPreference(mContext, Constant.TIMELINE_DATA);
                    Gson getGson = new Gson();
                    DailyNewsFeedMainModal dailyNewsFeedMainModal = getGson.fromJson(strTimelineData, DailyNewsFeedMainModal.class);
                    feed = dailyNewsFeedMainModal.getUserFeed().get(position);
                    int pos = position + 1;
                    if (mInfoList.size() == pos) {
                        AppPreference.setBooleanPreference(mContext, "likedPost", false);
                    }
                } else {
                    feed = mInfoList.get(position);
                }

                final HeadlineViewHolder viewHolder = (HeadlineViewHolder) holder;
                viewHolder.llSharePost.setTag(position);
                viewHolder.llSharePost.setOnClickListener(onClickListener);

                viewHolder.tvUserName.setText(feed.getUName());
                viewHolder.tvPostDescription.setText(feed.getPostDescription());
                viewHolder.tvHeadline.setText(feed.getHeadline());
                viewHolder.llPostComment.setTag(position);
                viewHolder.llPostComment.setOnClickListener(onClickListener);

                viewHolder.rlPost.setTag(position);
                viewHolder.rlPost.setOnClickListener(onClickListener);
                viewHolder.llViewUserProfile.setTag(position);
                viewHolder.llViewUserProfile.setOnClickListener(onClickListener);

                viewHolder.llLikePost.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        likeApi(feed, viewHolder.tvPostLikeCount, "1", "0");
                    }
                });

                viewHolder.imgLike.setTag(position);
                //viewHolder.viewData.setTag(position);

                if (!feed.getIsLike().isEmpty()) {
                    if (feed.getIsLike().equals("like")) {
                        viewHolder.imgLike.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_hot));
                    } else {
                        viewHolder.imgLike.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_hot_b));
                    }
                } else {
                    viewHolder.imgLike.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_hot_b));
                }

                viewHolder.tvPostLikeCount.setText(feed.getTotalLike());

                if (feed.getEntryDate() == null || feed.getEntryDate().isEmpty()) {
                    viewHolder.tvPostTime.setVisibility(View.GONE);
                } else {
                    viewHolder.tvPostTime.setVisibility(View.VISIBLE);
                    viewHolder.tvPostTime.setText(feed.getEntryDate());
                }

                Glide.with(viewHolder.itemView.getContext())
                        .load(Constant.PROFILE_IMAGE_BASE_URL + feed.getUProfile())
                        .apply(new RequestOptions().optionalCenterCrop())
                        .into(viewHolder.imgUserProfile);
                break;
            case 1:
                final UserFeed imageFeed;
                if (AppPreference.getBooleanPreference(mContext, "likedPost")) {
                    String strTimelineData = AppPreference.getStringPreference(mContext, Constant.TIMELINE_DATA);
                    Gson getGson = new Gson();
                    DailyNewsFeedMainModal dailyNewsFeedMainModal = getGson.fromJson(strTimelineData, DailyNewsFeedMainModal.class);
                    imageFeed = dailyNewsFeedMainModal.getUserFeed().get(position);
                    int pos = position + 1;
                    if (mInfoList.size() == pos) {
                        AppPreference.setBooleanPreference(mContext, "likedPost", false);
                    }
                } else {
                    imageFeed = mInfoList.get(position);
                }

                final ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
                imageViewHolder.llSharePost.setTag(position);
                imageViewHolder.llSharePost.setOnClickListener(onClickListener);

                imageViewHolder.tvUserName.setText(imageFeed.getUName());
                imageViewHolder.tvPostDescription.setText(imageFeed.getPostDescription());

                imageViewHolder.imgLike.setTag(position);
                //imageViewHolder.viewData.setTag(position);

                imageViewHolder.llPostComment.setTag(position);
                imageViewHolder.llPostComment.setOnClickListener(onClickListener);

                imageViewHolder.rlPost.setTag(position);
                imageViewHolder.rlPost.setOnClickListener(onClickListener);
                imageViewHolder.llViewUserProfile.setTag(position);
                imageViewHolder.llViewUserProfile.setOnClickListener(onClickListener);

                imageViewHolder.llLikePost.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        likeApi(imageFeed, imageViewHolder.tvPostLikeCount, "1", "0");
                    }
                });

                if (!imageFeed.getIsLike().isEmpty()) {
                    if (imageFeed.getIsLike().equals("like")) {
                        imageViewHolder.imgLike.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_hot));
                    } else {
                        imageViewHolder.imgLike.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_hot_b));
                    }
                } else {
                    imageViewHolder.imgLike.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_hot_b));
                }

                imageViewHolder.tvPostLikeCount.setText(imageFeed.getTotalLike());

                if (imageFeed.getEntryDate() == null || imageFeed.getEntryDate().isEmpty()) {
                    imageViewHolder.tvPostTime.setVisibility(View.GONE);
                } else {
                    imageViewHolder.tvPostTime.setVisibility(View.VISIBLE);
                    imageViewHolder.tvPostTime.setText(imageFeed.getEntryDate());
                }
                Glide.with(imageViewHolder.itemView.getContext())
                        .load(Constant.IMAGE_BASE_URL + imageFeed.getImage())
                        .apply(new RequestOptions().optionalCenterCrop())
                        .into(imageViewHolder.imgPostImage);

                Glide.with(imageViewHolder.itemView.getContext())
                        .load(Constant.PROFILE_IMAGE_BASE_URL + imageFeed.getUProfile())
                        .apply(new RequestOptions().optionalCenterCrop())
                        .into(imageViewHolder.imgUserProfile);
                break;
            case 2:
                final UserFeed videoFeed;
                if (AppPreference.getBooleanPreference(mContext, "likedPost")) {
                    String strTimelineData = AppPreference.getStringPreference(mContext, Constant.TIMELINE_DATA);
                    Gson getGson = new Gson();
                    DailyNewsFeedMainModal dailyNewsFeedMainModal = getGson.fromJson(strTimelineData, DailyNewsFeedMainModal.class);
                    videoFeed = dailyNewsFeedMainModal.getUserFeed().get(position);
                    int pos = position + 1;
                    if (mInfoList.size() == pos) {
                        AppPreference.setBooleanPreference(mContext, "likedPost", false);
                    }
                } else {
                    videoFeed = mInfoList.get(position);
                }

                final ViewHolder videoViewHolder = (ViewHolder) holder;
                videoViewHolder.llSharePost.setTag(position);
                videoViewHolder.llSharePost.setOnClickListener(onClickListener);

                videoViewHolder.tvUserName.setText(videoFeed.getUName());
                videoViewHolder.tvPostDescription.setText(videoFeed.getPostDescription());

                videoViewHolder.imgLike.setTag(position);
                //videoViewHolder.viewData.setTag(position);

                videoViewHolder.llPostComment.setTag(position);
                videoViewHolder.llPostComment.setOnClickListener(onClickListener);

                videoViewHolder.rlPost.setTag(position);
                videoViewHolder.rlPost.setOnClickListener(onClickListener);
                videoViewHolder.llViewUserProfile.setTag(position);
                videoViewHolder.llViewUserProfile.setOnClickListener(onClickListener);

                videoViewHolder.llLikePost.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        likeApi(videoFeed, videoViewHolder.tvPostLikeCount, "1", "0");
                    }
                });

                if (!videoFeed.getIsLike().isEmpty()) {
                    if (videoFeed.getIsLike().equals("like")) {
                        videoViewHolder.imgLike.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_hot));
                    } else {
                        videoViewHolder.imgLike.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_hot_b));
                    }
                } else {
                    videoViewHolder.imgLike.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_hot_b));
                }

                videoViewHolder.tvPostLikeCount.setText(videoFeed.getTotalLike());

                if (videoFeed.getEntryDate() == null || videoFeed.getEntryDate().isEmpty()) {
                    videoViewHolder.tvPostTime.setText("");
                    videoViewHolder.tvPostTime.setVisibility(View.GONE);
                } else {
                    videoViewHolder.tvPostTime.setVisibility(View.VISIBLE);
                    videoViewHolder.tvPostTime.setText(videoFeed.getEntryDate());
                }

                Glide.with(videoViewHolder.itemView.getContext())
                        .load(Constant.PROFILE_IMAGE_BASE_URL + videoFeed.getUProfile())
                        .apply(new RequestOptions().optionalCenterCrop())
                        .into(videoViewHolder.imgUserProfile);

                videoViewHolder.progressBar.setVisibility(View.VISIBLE);
                Glide.with(videoViewHolder.itemView.getContext())
                        .load(Constant.VIDEO_BASE_URL + videoFeed.getVideo())
                        .apply(new RequestOptions().optionalCenterCrop())
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                videoViewHolder.progressBar.setVisibility(View.GONE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                videoViewHolder.progressBar.setVisibility(View.GONE);
                                return false;
                            }
                        })
                        .into(videoViewHolder.mCover);
                break;
            case VIEW_TYPE_EMPTY:
                break;
        }
    }

    /*
     * Like/Unlike api
     * */
    private void likeApi(final UserFeed feed, final TextView textView, String strLike, String strUnlike) {
        final String strId = AppPreference.getStringPreference(mContext, Constant.USER_ID);
        String userType = AppPreference.getStringPreference(mContext, Constant.USER_TYPE);
        RetrofitService.getLikeResponse(retrofitApiClient.postLike(feed.getPostId(), strId, strLike, strUnlike,
                userType), new WebResponse() {
            @Override
            public void onResponseSuccess(Response<?> result) {
                ResponseBody responseBody = (ResponseBody) result.body();
                try {
                    JSONObject jsonObject = new JSONObject(responseBody.string());
                    if (!jsonObject.getBoolean("error")) {
                        textView.setText(jsonObject.getString("likes"));
                        refreshTimelineApi(strId);
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
    }

    private void refreshTimelineApi(String strId) {
        String userType = AppPreference.getStringPreference(mContext, Constant.USER_TYPE);
        RetrofitService.refreshTimeLine(retrofitApiClient.showPostTimeLine(strId, userType), new WebResponse() {
            @Override
            public void onResponseSuccess(Response<?> result) {
                DailyNewsFeedMainModal dailyNewsFeedMainModal = (DailyNewsFeedMainModal) result.body();
                mInfoList.clear();
                if (dailyNewsFeedMainModal.getError()) {
                    Alerts.show(mContext, "No data");
                } else {
                    Gson gson = new GsonBuilder().setLenient().create();
                    String data = gson.toJson(dailyNewsFeedMainModal);
                    AppPreference.setStringPreference(mContext, Constant.TIMELINE_DATA, data);
                    AppPreference.setBooleanPreference(mContext, "likedPost", true);
                    mInfoList.addAll(dailyNewsFeedMainModal.getUserFeed());
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onResponseFailed(String error) {
                Alerts.show(mContext, error);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        if (mInfoList.size() == 0) {
            return VIEW_TYPE_EMPTY;
        } else if (!mInfoList.get(position).getVideo().isEmpty()) {
            return VIEW_TYPE_VIDEO;
        } else if (!mInfoList.get(position).getImage().isEmpty()) {
            return VIEW_TYPE_IMAGE;
        } else {
            return VIEW_TYPE_TEXT;
        }
    }

    @Override
    public int getItemCount() {
        if (mInfoList != null && mInfoList.size() > 0) {
            return mInfoList.size();
        } else {
            return 1;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout rlPost, llViewUserProfile;
        private LinearLayout llLikePost, llPostComment, llSharePost;
        public ImageView mCover, imgLike;
        private CircleImageView imgUserProfile;
        private TextView tvUserName, tvPostLikeCount, tvPostTime, tvPostDescription;

        public ProgressBar progressBar;
        public final View parent;
        public final View viewData;

        public ViewHolder(View itemView) {
            super(itemView);
            parent = itemView;
            viewData = itemView;
            mCover = itemView.findViewById(R.id.cover);
            progressBar = itemView.findViewById(R.id.progressBar);

            llSharePost = itemView.findViewById(R.id.llSharePost);
            rlPost = itemView.findViewById(R.id.rlPost);
            llViewUserProfile = itemView.findViewById(R.id.llViewUserProfile);
            imgUserProfile = itemView.findViewById(R.id.imgUserProfile);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            imgLike = itemView.findViewById(R.id.imgLike);
            llLikePost = itemView.findViewById(R.id.llLikePost);
            llPostComment = itemView.findViewById(R.id.llPostComment);
            tvPostLikeCount = itemView.findViewById(R.id.tvPostLikeCount);
            tvPostTime = itemView.findViewById(R.id.tvPostTime);
            tvPostDescription = itemView.findViewById(R.id.tvPostDescription);
        }
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout rlPost, llViewUserProfile;
        private LinearLayout llLikePost, llPostComment, llSharePost;
        private ImageView imgPostImage, imgLike;
        private CircleImageView imgUserProfile;

        private TextView tvUserName, tvPostLikeCount, tvPostTime, tvPostDescription;
        public final View viewData;

        public ImageViewHolder(View itemView) {
            super(itemView);
            viewData = itemView;
            rlPost = itemView.findViewById(R.id.rlPost);
            llViewUserProfile = itemView.findViewById(R.id.llViewUserProfile);
            imgUserProfile = itemView.findViewById(R.id.imgUserProfile);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            imgPostImage = itemView.findViewById(R.id.imgPostImage);
            imgLike = itemView.findViewById(R.id.imgLike);
            llLikePost = itemView.findViewById(R.id.llLikePost);
            llPostComment = itemView.findViewById(R.id.llPostComment);
            tvPostLikeCount = itemView.findViewById(R.id.tvPostLikeCount);
            tvPostTime = itemView.findViewById(R.id.tvPostTime);
            tvPostDescription = itemView.findViewById(R.id.tvPostDescription);
        }
    }

    public class HeadlineViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout rlPost, llViewUserProfile;
        private LinearLayout llLikePost, llPostComment, llSharePost;
        private ImageView imgLike;
        private CircleImageView imgUserProfile;
        private TextView tvHeadline, tvUserName, tvPostLikeCount, tvPostTime, tvPostDescription;
        public final View viewData;

        public HeadlineViewHolder(View itemView) {
            super(itemView);
            viewData = itemView;
            rlPost = itemView.findViewById(R.id.rlPost);
            llViewUserProfile = itemView.findViewById(R.id.llViewUserProfile);
            imgUserProfile = itemView.findViewById(R.id.imgUserProfile);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvHeadline = itemView.findViewById(R.id.tvHeadline);
            imgLike = itemView.findViewById(R.id.imgLike);
            llLikePost = itemView.findViewById(R.id.llLikePost);
            llSharePost = itemView.findViewById(R.id.llSharePost);
            llPostComment = itemView.findViewById(R.id.llPostComment);
            tvPostLikeCount = itemView.findViewById(R.id.tvPostLikeCount);
            tvPostTime = itemView.findViewById(R.id.tvPostTime);
            tvPostDescription = itemView.findViewById(R.id.tvPostDescription);
        }
    }

    public class EmptyViewHolder extends RecyclerView.ViewHolder {

        public final View viewData;

        public EmptyViewHolder(View itemView) {
            super(itemView);
            viewData = itemView;
        }
    }
}
