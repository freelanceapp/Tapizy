package ibt.com.tapizy.ui.fragment.user_fragment;

import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import ibt.com.tapizy.R;
import ibt.com.tapizy.adapter.FavouriteBotListAdapter;
import ibt.com.tapizy.constant.Constant;
import ibt.com.tapizy.model.User;
import ibt.com.tapizy.model.api_bot_list.BotList;
import ibt.com.tapizy.model.favourite_bot.FavoriteBot;
import ibt.com.tapizy.model.favourite_bot.FavouriteBotMainModal;
import ibt.com.tapizy.retrofit_provider.RetrofitService;
import ibt.com.tapizy.retrofit_provider.WebResponse;
import ibt.com.tapizy.services.CustomFloatingViewService;
import ibt.com.tapizy.ui.activity.user_activities.WebviewActivity;
import ibt.com.tapizy.ui.activity.user_activities.chatbot_activity.ChatActivity;
import ibt.com.tapizy.ui.activity.user_activities.community_module.CommunityActivity;
import ibt.com.tapizy.ui.activity.user_activities.explore.ExploreActivity;
import ibt.com.tapizy.ui.activity.user_activities.recent_chat.RecentChatActivity;
import ibt.com.tapizy.ui.activity.user_activities.trending_module.TrendingActivity;
import ibt.com.tapizy.utils.Alerts;
import ibt.com.tapizy.utils.AppPreference;
import ibt.com.tapizy.utils.BaseFragment;
import ibt.com.tapizy.utils.ConnectionDetector;
import ibt.com.tapizy.utils.drag_and_remove.OnStartDragListener;
import ibt.com.tapizy.utils.drag_and_remove.SimpleItemTouchHelperCallback;
import ibt.com.tapizy.utils.floating_view.FloatingViewListener;
import ibt.com.tapizy.utils.floating_view.FloatingViewManager;
import ibt.com.tapizy.utils.move_listener.MultiTouchListener;
import okhttp3.ResponseBody;
import retrofit2.Response;

import static ibt.com.tapizy.ui.activity.user_activities.HomeActivity.homeActivity;

public class HomeFragment extends BaseFragment implements View.OnClickListener, OnStartDragListener,
        FloatingViewListener {

    public static HomeFragment homeFragment;

    private View rootView;
    private TextView txtSwipe;
    private ItemTouchHelper mItemTouchHelper;
    private ArrayList<FavoriteBot> favoriteBotList = new ArrayList<>();
    private FavouriteBotListAdapter adapter;

    /*******************************************************/
    private static final int CHATHEAD_OVERLAY_PERMISSION_REQUEST_CODE = 100;
    private static final int CUSTOM_OVERLAY_PERMISSION_REQUEST_CODE = 101;
    private FavoriteBot favoriteBotData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        homeFragment = new HomeFragment();
        activity = getActivity();
        mContext = getActivity();
        cd = new ConnectionDetector(mContext);
        retrofitApiClient = RetrofitService.getRetrofit();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();
        favouriteBotRecyclerView();
    }

    private void setUserData() {
        //View header = navigationView.getHeaderView(0);

        Glide.with(mContext)
                .load(Constant.PROFILE_IMAGE_BASE_URL + User.getUser().getUser().getUProfile())
                .into(((CircleImageView) rootView.findViewById(R.id.profile_image)));

        ((TextView) rootView.findViewById(R.id.tvUserName)).setText(User.getUser().getUser().getUName());
        ((TextView) rootView.findViewById(R.id.tvEmail)).setText(User.getUser().getUser().getUEmail());
    }

    private void init() {
        txtSwipe = rootView.findViewById(R.id.txtSwipe);
        MultiTouchListener touchListener = new MultiTouchListener(homeActivity);
        txtSwipe.setOnTouchListener(touchListener);
        rootView.findViewById(R.id.llexplore).setOnClickListener(this);
        rootView.findViewById(R.id.llTrending).setOnClickListener(this);
        rootView.findViewById(R.id.llCommunity).setOnClickListener(this);
        rootView.findViewById(R.id.llchat).setOnClickListener(this);

        rootView.findViewById(R.id.llFb).setOnClickListener(this);
        rootView.findViewById(R.id.llTwitter).setOnClickListener(this);
        rootView.findViewById(R.id.llInsta).setOnClickListener(this);
        rootView.findViewById(R.id.llFlipkart).setOnClickListener(this);

        /*CollapsingToolbarLayout collapsingToolbar = rootView.findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("");*/
        AppBarLayout appBarLayout = rootView.findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {

            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    //rootView.findViewById(R.id.imgAppLogo).setVisibility(View.VISIBLE);
                    //rootView.findViewById(R.id.backdrop).setVisibility(View.GONE);
                    isShow = true;
                } else if (isShow) {
                    //rootView.findViewById(R.id.imgAppLogo).setVisibility(View.GONE);
                    //rootView.findViewById(R.id.backdrop).setVisibility(View.VISIBLE);
                    isShow = false;
                }
            }
        });
    }

    private void favouriteBotRecyclerView() {
        RecyclerView rv_tapizy_list = rootView.findViewById(R.id.rv_tapizy_list);

        adapter = new FavouriteBotListAdapter(mContext, favoriteBotList, this, this);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mContext, 4);
        rv_tapizy_list.setLayoutManager(mLayoutManager);
        rv_tapizy_list.setItemAnimator(new DefaultItemAnimator());
        rv_tapizy_list.setAdapter(adapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(rv_tapizy_list);

        favouriteBotListApi();
    }

    private void favouriteBotListApi() {
        String strUserId = User.getUser().getUser().getUid();
        if (cd.isNetworkAvailable()) {
            RetrofitService.getFavBotList(new Dialog(mContext), retrofitApiClient.favBotList(strUserId), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    AppPreference.setBooleanPreference(mContext, Constant.ADD_TO_FAV, false);
                    FavouriteBotMainModal favouriteBotMainModal = (FavouriteBotMainModal) result.body();
                    favoriteBotList.clear();
                    if (favouriteBotMainModal != null) {
                        if (favouriteBotMainModal.getFavoriteBot() != null) {
                            if (favouriteBotMainModal.getFavoriteBot().size() > 0) {
                                favoriteBotList.addAll(favouriteBotMainModal.getFavoriteBot());
                            }
                        }
                    }
                    adapter.notifyDataSetChanged();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgRemove:
                int position = Integer.parseInt(v.getTag().toString());
                String strId = favoriteBotList.get(position).getBotId();
                removeFav(strId);
                break;
            case R.id.llexplore:
                startActivity(new Intent(mContext, ExploreActivity.class));
                break;
            case R.id.llCommunity:
                startActivity(new Intent(mContext, CommunityActivity.class));
                break;
            case R.id.llTrending:
                startActivity(new Intent(mContext, TrendingActivity.class));
                break;
            case R.id.llchat:
                startActivity(new Intent(mContext, RecentChatActivity.class));
                break;
            case R.id.llayout:
                int pos = Integer.parseInt(v.getTag().toString());
                if (pos > 4) {
                    BotList botData = new BotList();
                    botData.setAvtar(favoriteBotList.get(pos).getAvtar());
                    botData.setBotName(favoriteBotList.get(pos).getBotName());
                    botData.setUid(favoriteBotList.get(pos).getBotId());
                    Intent intentA = new Intent(mContext, ChatActivity.class);
                    intentA.putExtra("bot_data", botData);
                    startActivity(intentA);
                } else {

                }
                break;
            case R.id.llFb:
                sendSocialUrl(Constant.FbUrl, "Facebook");
                break;
            case R.id.llTwitter:
                sendSocialUrl(Constant.TwitterUrl, "Twitter");
                break;
            case R.id.llInsta:
                sendSocialUrl(Constant.InstaUrl, "Instagram");
                break;
            case R.id.llFlipkart:
                sendSocialUrl(Constant.FlipkartUrl, "Flipkart");
                break;
        }
    }

    private void sendSocialUrl(String url, String title) {
        Intent intent = new Intent(mContext, WebviewActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        startActivity(intent);
    }

    /***************************************************************/
    /*
     * Drag and remove item
     * */
    public void removeFav(String strBotId) {
        String strUserId = User.getUser().getUser().getUid();
        if (cd.isNetworkAvailable()) {
            RetrofitService.getloginData(new Dialog(mContext), retrofitApiClient.addToFav(strUserId, strBotId, "0"), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    ResponseBody responseBody = (ResponseBody) result.body();
                    AppPreference.setBooleanPreference(mContext, Constant.ADD_TO_FAV, true);
                    try {
                        JSONObject jsonObject = new JSONObject(responseBody.string());
                        if (!jsonObject.getBoolean("error")) {
                            Alerts.show(mContext, "Remove from favourite");
                            favouriteBotListApi();
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

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {

    }

    @Override
    public void onStartDragData(FavoriteBot favoriteBot) {
        favoriteBotData = favoriteBot;
        showFloatingView(mContext, true, false);
    }

    @Override
    public void onFinishFloatingView() {
        Alerts.show(mContext, "finished");
    }

    @Override
    public void onTouchFinished(boolean isFinishing, int x, int y) {
        Alerts.show(mContext, "finished new");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CHATHEAD_OVERLAY_PERMISSION_REQUEST_CODE) {
            showFloatingView(mContext, false, false);
        } else if (requestCode == CUSTOM_OVERLAY_PERMISSION_REQUEST_CODE) {
            showFloatingView(mContext, false, true);
        }
    }

    private void showFloatingView(Context context, boolean isShowOverlayPermission, boolean isCustomFloatingView) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
            startFloatingViewService();
            return;
        }

        if (Settings.canDrawOverlays(context)) {
            startFloatingViewService();
            return;
        }

        if (isShowOverlayPermission) {
            final Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + context.getPackageName()));
            startActivityForResult(intent, isCustomFloatingView ? CUSTOM_OVERLAY_PERMISSION_REQUEST_CODE : CHATHEAD_OVERLAY_PERMISSION_REQUEST_CODE);
        }
    }

    private void startFloatingViewService() {
        String strImgUrl = Constant.PROFILE_IMAGE_BASE_URL + favoriteBotData.getAvtar();
        String strBotId = favoriteBotData.getBotId();

        final Class<? extends Service> service = CustomFloatingViewService.class;
        String key = CustomFloatingViewService.EXTRA_CUTOUT_SAFE_AREA;

        final Intent intent = new Intent(mContext, service);
        intent.putExtra(key, FloatingViewManager.findCutoutSafeArea(homeActivity));
        intent.putExtra("image_url", strImgUrl);
        intent.putExtra("bot_id", strBotId);
        mContext.startService(intent);
    }
}
