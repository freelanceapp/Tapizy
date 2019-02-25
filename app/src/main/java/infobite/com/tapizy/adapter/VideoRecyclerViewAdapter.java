package infobite.com.tapizy.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
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
import infobite.com.tapizy.R;
import infobite.com.tapizy.constant.Constant;
import infobite.com.tapizy.model.timeline_modal.DailyNewsFeedMainModal;
import infobite.com.tapizy.model.timeline_modal.UserFeed;
import infobite.com.tapizy.retrofit_provider.RetrofitApiClient;
import infobite.com.tapizy.retrofit_provider.RetrofitService;
import infobite.com.tapizy.retrofit_provider.WebResponse;
import infobite.com.tapizy.utils.Alerts;
import infobite.com.tapizy.utils.AppPreference;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class VideoRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_TEXT = 0;
    private static final int VIEW_TYPE_IMAGE = 1;
    private static final int VIEW_TYPE_VIDEO = 2;
    private static final int VIEW_TYPE_EMPTY = 3;

    private List<UserFeed> mInfoList;
    private Context mContext;
    private View.OnClickListener onClickListener;
    private RetrofitApiClient retrofitApiClient;

    public VideoRecyclerViewAdapter(Context mContext, List<UserFeed> infoList, View.OnClickListener onClickListener,
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
                    feed = dailyNewsFeedMainModal.getFeed().get(position);
                    int pos = position + 1;
                    if (mInfoList.size() == pos) {
                        AppPreference.setBooleanPreference(mContext, "likedPost", false);
                    }
                } else {
                    feed = mInfoList.get(position);
                }

                final HeadlineViewHolder viewHolder = (HeadlineViewHolder) holder;
                viewHolder.tvUserName.setText(feed.getPostUserName());
                viewHolder.tvPostDescription.setText(feed.getAthleteStatus());
                viewHolder.tvHeadline.setText(feed.getAthleteArticeHeadline());
                viewHolder.llPostComment.setTag(position);
                viewHolder.llPostComment.setOnClickListener(onClickListener);
                viewHolder.tvTotalComment.setTag(position);
                viewHolder.tvTotalComment.setOnClickListener(onClickListener);
                viewHolder.rlPost.setTag(position);
                viewHolder.rlPost.setOnClickListener(onClickListener);
                viewHolder.llViewUserProfile.setTag(position);
                viewHolder.llViewUserProfile.setOnClickListener(onClickListener);

                viewHolder.llLikePost.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        likeApi(feed, viewHolder.imgLike, viewHolder.tvPostLikeCount);
                    }
                });

                viewHolder.imgLike.setTag(position);
                //viewHolder.viewData.setTag(position);

                if (feed.getIsLike().equals("unlike")) {
                    viewHolder.imgLike.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_like_icon));
                } else {
                    viewHolder.imgLike.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_like_fill));
                }

                if (feed.getLikes() == null || feed.getLikes().isEmpty()) {
                    viewHolder.tvPostLikeCount.setText("0 like");
                } else {
                    viewHolder.tvPostLikeCount.setText(feed.getLikes() + " like");
                }

                if (feed.getEntryDate() == null || feed.getEntryDate().isEmpty()) {
                    viewHolder.tvPostTime.setVisibility(View.GONE);
                } else {
                    viewHolder.tvPostTime.setVisibility(View.VISIBLE);
                    viewHolder.tvPostTime.setText(feed.getEntryDate());
                }

                Glide.with(viewHolder.itemView.getContext())
                        .load(Constant.PROFILE_IMAGE_BASE_URL + feed.getPostUserImage())
                        .apply(new RequestOptions().optionalCenterCrop())
                        .into(viewHolder.imgUserProfile);
                break;
            case 1:
                final UserFeed imageFeed;

                if (AppPreference.getBooleanPreference(mContext, "likedPost")) {
                    String strTimelineData = AppPreference.getStringPreference(mContext, Constant.TIMELINE_DATA);
                    Gson getGson = new Gson();
                    DailyNewsFeedMainModal dailyNewsFeedMainModal = getGson.fromJson(strTimelineData, DailyNewsFeedMainModal.class);
                    imageFeed = dailyNewsFeedMainModal.getFeed().get(position);
                    int pos = position + 1;
                    if (mInfoList.size() == pos) {
                        AppPreference.setBooleanPreference(mContext, "likedPost", false);
                    }
                } else {
                    imageFeed = mInfoList.get(position);
                }

                final ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
                imageViewHolder.tvUserName.setText(imageFeed.getPostUserName());
                imageViewHolder.tvPostDescription.setText(imageFeed.getAthleteStatus());

                imageViewHolder.imgLike.setTag(position);
                //imageViewHolder.viewData.setTag(position);

                imageViewHolder.llPostComment.setTag(position);
                imageViewHolder.llPostComment.setOnClickListener(onClickListener);
                imageViewHolder.tvTotalComment.setTag(position);
                imageViewHolder.tvTotalComment.setOnClickListener(onClickListener);
                imageViewHolder.rlPost.setTag(position);
                imageViewHolder.rlPost.setOnClickListener(onClickListener);
                imageViewHolder.llViewUserProfile.setTag(position);
                imageViewHolder.llViewUserProfile.setOnClickListener(onClickListener);

                imageViewHolder.llLikePost.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        likeApi(imageFeed, imageViewHolder.imgLike, imageViewHolder.tvPostLikeCount);
                    }
                });

                if (imageFeed.getIsLike().equals("unlike")) {
                    imageViewHolder.imgLike.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_like_icon));
                } else {
                    imageViewHolder.imgLike.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_like_fill));
                }

                if (imageFeed.getLikes() == null || imageFeed.getLikes().isEmpty()) {
                    imageViewHolder.tvPostLikeCount.setText("0 like");
                } else {
                    imageViewHolder.tvPostLikeCount.setText(imageFeed.getLikes() + " like");
                }

                if (imageFeed.getEntryDate() == null || imageFeed.getEntryDate().isEmpty()) {
                    imageViewHolder.tvPostTime.setVisibility(View.GONE);
                } else {
                    imageViewHolder.tvPostTime.setVisibility(View.VISIBLE);
                    imageViewHolder.tvPostTime.setText(imageFeed.getEntryDate());
                }
                Glide.with(imageViewHolder.itemView.getContext())
                        .load(Constant.IMAGE_BASE_URL + imageFeed.getAlhleteImages())
                        .apply(new RequestOptions().optionalCenterCrop())
                        .into(imageViewHolder.imgPostImage);

                Glide.with(imageViewHolder.itemView.getContext())
                        .load(Constant.PROFILE_IMAGE_BASE_URL + imageFeed.getPostUserImage())
                        .apply(new RequestOptions().optionalCenterCrop())
                        .into(imageViewHolder.imgUserProfile);
                break;
            case 2:
                final UserFeed videoFeed;

                if (AppPreference.getBooleanPreference(mContext, "likedPost")) {
                    String strTimelineData = AppPreference.getStringPreference(mContext, Constant.TIMELINE_DATA);
                    Gson getGson = new Gson();
                    DailyNewsFeedMainModal dailyNewsFeedMainModal = getGson.fromJson(strTimelineData, DailyNewsFeedMainModal.class);
                    videoFeed = dailyNewsFeedMainModal.getFeed().get(position);
                    int pos = position + 1;
                    if (mInfoList.size() == pos) {
                        AppPreference.setBooleanPreference(mContext, "likedPost", false);
                    }
                } else {
                    videoFeed = mInfoList.get(position);
                }

                final ViewHolder videoViewHolder = (ViewHolder) holder;
                videoViewHolder.tvUserName.setText(videoFeed.getPostUserName());
                videoViewHolder.tvPostDescription.setText(videoFeed.getAthleteStatus());

                videoViewHolder.imgLike.setTag(position);
                //videoViewHolder.viewData.setTag(position);

                videoViewHolder.llPostComment.setTag(position);
                videoViewHolder.llPostComment.setOnClickListener(onClickListener);

                videoViewHolder.tvTotalComment.setTag(position);
                videoViewHolder.tvTotalComment.setOnClickListener(onClickListener);
                videoViewHolder.rlPost.setTag(position);
                videoViewHolder.rlPost.setOnClickListener(onClickListener);
                videoViewHolder.llViewUserProfile.setTag(position);
                videoViewHolder.llViewUserProfile.setOnClickListener(onClickListener);

                videoViewHolder.llLikePost.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        likeApi(videoFeed, videoViewHolder.imgLike, videoViewHolder.tvPostLikeCount);
                    }
                });

                if (videoFeed.getIsLike().equals("unlike")) {
                    videoViewHolder.imgLike.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_like_icon));
                } else {
                    videoViewHolder.imgLike.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_like_fill));
                }

                if (videoFeed.getLikes() == null || videoFeed.getLikes().isEmpty()) {
                    videoViewHolder.tvPostLikeCount.setText("0 like");
                } else {
                    videoViewHolder.tvPostLikeCount.setText(videoFeed.getLikes() + " like");
                }

                if (videoFeed.getEntryDate() == null || videoFeed.getEntryDate().isEmpty()) {
                    videoViewHolder.tvPostTime.setText("");
                    videoViewHolder.tvPostTime.setVisibility(View.GONE);
                } else {
                    videoViewHolder.tvPostTime.setVisibility(View.VISIBLE);
                    videoViewHolder.tvPostTime.setText(videoFeed.getEntryDate());
                }

                Glide.with(videoViewHolder.itemView.getContext())
                        .load(Constant.PROFILE_IMAGE_BASE_URL + videoFeed.getPostUserImage())
                        .apply(new RequestOptions().optionalCenterCrop())
                        .into(videoViewHolder.imgUserProfile);

                videoViewHolder.progressBar.setVisibility(View.VISIBLE);
                Glide.with(videoViewHolder.itemView.getContext())
                        .load(Constant.VIDEO_BASE_URL + videoFeed.getAthleteVideo())
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
    private void likeApi(final UserFeed feed, final ImageView imgLike, final TextView textView) {
        final String strId = AppPreference.getStringPreference(mContext, Constant.USER_ID);

        RetrofitService.getLikeResponse(retrofitApiClient.postLike(feed.getFeedId(), strId, "1"), new WebResponse() {
            @Override
            public void onResponseSuccess(Response<?> result) {
                ResponseBody responseBody = (ResponseBody) result.body();
                try {
                    JSONObject jsonObject = new JSONObject(responseBody.string());
                    if (!jsonObject.getBoolean("error")) {
                        if (feed.getIsLike().equals("unlike")) {
                            imgLike.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_like_fill));
                        } else {
                            imgLike.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_like_icon));
                        }
                        textView.setText(jsonObject.getString("total_fan") + " like");
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
        RetrofitService.refreshTimeLine(retrofitApiClient.showPostTimeLine(), new WebResponse() {
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
                    mInfoList.addAll(dailyNewsFeedMainModal.getFeed());
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
        } else if (!mInfoList.get(position).getAthleteVideo().isEmpty()) {
            return VIEW_TYPE_VIDEO;
        } else if (!mInfoList.get(position).getAlhleteImages().isEmpty()) {
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

    public void onRelease() {
        if (mInfoList != null) {
            mInfoList.clear();
            mInfoList = null;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout rlPost, llViewUserProfile;
        private LinearLayout llLikePost, llPostComment;
        public ImageView mCover, imgLike;
        private CircleImageView imgUserProfile;
        private TextView tvUserName, tvPostLikeCount, tvPostTime, tvTotalComment, tvPostDescription;
        private CardView cardViewVideo;

        public ProgressBar progressBar;
        public final View parent;
        public final View viewData;

        public ViewHolder(View itemView) {
            super(itemView);
            parent = itemView;
            viewData = itemView;
            parent.setTag(this);
            cardViewVideo = itemView.findViewById(R.id.cardViewVideo);
            mCover = itemView.findViewById(R.id.cover);
            progressBar = itemView.findViewById(R.id.progressBar);

            rlPost = itemView.findViewById(R.id.rlPost);
            llViewUserProfile = itemView.findViewById(R.id.llViewUserProfile);
            imgUserProfile = itemView.findViewById(R.id.imgUserProfile);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            imgLike = itemView.findViewById(R.id.imgLike);
            llLikePost = itemView.findViewById(R.id.llLikePost);
            llPostComment = itemView.findViewById(R.id.llPostComment);
            tvPostLikeCount = itemView.findViewById(R.id.tvPostLikeCount);
            tvPostTime = itemView.findViewById(R.id.tvPostTime);
            tvTotalComment = itemView.findViewById(R.id.tvTotalComment);
            tvPostDescription = itemView.findViewById(R.id.tvPostDescription);
        }
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout rlPost, llViewUserProfile;
        private LinearLayout llLikePost, llPostComment;
        private ImageView imgPostImage, imgLike;
        private CircleImageView imgUserProfile;
        private CardView cardViewImage;
        private TextView tvUserName, tvPostLikeCount, tvPostTime, tvTotalComment, tvPostDescription;
        public final View viewData;

        public ImageViewHolder(View itemView) {
            super(itemView);
            viewData = itemView;
            rlPost = itemView.findViewById(R.id.rlPost);
            cardViewImage = itemView.findViewById(R.id.cardViewImage);
            llViewUserProfile = itemView.findViewById(R.id.llViewUserProfile);
            imgUserProfile = itemView.findViewById(R.id.imgUserProfile);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            imgPostImage = itemView.findViewById(R.id.imgPostImage);
            imgLike = itemView.findViewById(R.id.imgLike);
            llLikePost = itemView.findViewById(R.id.llLikePost);
            llPostComment = itemView.findViewById(R.id.llPostComment);
            tvPostLikeCount = itemView.findViewById(R.id.tvPostLikeCount);
            tvPostTime = itemView.findViewById(R.id.tvPostTime);
            tvTotalComment = itemView.findViewById(R.id.tvTotalComment);
            tvPostDescription = itemView.findViewById(R.id.tvPostDescription);
        }
    }

    public class HeadlineViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout rlPost, llViewUserProfile;
        private LinearLayout llLikePost, llPostComment;
        private ImageView imgLike;
        private CircleImageView imgUserProfile;
        private CardView cardViewHeadline;
        private TextView tvHeadline, tvUserName, tvPostLikeCount, tvPostTime, tvTotalComment, tvPostDescription;
        public final View viewData;

        public HeadlineViewHolder(View itemView) {
            super(itemView);
            viewData = itemView;
            rlPost = itemView.findViewById(R.id.rlPost);
            llViewUserProfile = itemView.findViewById(R.id.llViewUserProfile);
            cardViewHeadline = itemView.findViewById(R.id.cardViewHeadline);
            imgUserProfile = itemView.findViewById(R.id.imgUserProfile);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvHeadline = itemView.findViewById(R.id.tvHeadline);
            imgLike = itemView.findViewById(R.id.imgLike);
            llLikePost = itemView.findViewById(R.id.llLikePost);
            llPostComment = itemView.findViewById(R.id.llPostComment);
            tvPostLikeCount = itemView.findViewById(R.id.tvPostLikeCount);
            tvPostTime = itemView.findViewById(R.id.tvPostTime);
            tvTotalComment = itemView.findViewById(R.id.tvTotalComment);
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
