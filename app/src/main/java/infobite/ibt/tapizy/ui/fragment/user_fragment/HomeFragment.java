package infobite.ibt.tapizy.ui.fragment.user_fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import infobite.ibt.tapizy.R;
import infobite.ibt.tapizy.adapter.FavouriteBotListAdapter;
import infobite.ibt.tapizy.adapter.SocialLinksListAdapter;
import infobite.ibt.tapizy.constant.Constant;
import infobite.ibt.tapizy.model.User;
import infobite.ibt.tapizy.model.api_bot_list.BotList;
import infobite.ibt.tapizy.model.favourite_bot.FavoriteBot;
import infobite.ibt.tapizy.model.favourite_bot.FavouriteBotMainModal;
import infobite.ibt.tapizy.model.social_link.SocialLinkList;
import infobite.ibt.tapizy.model.social_link.SocialLinkMainModal;
import infobite.ibt.tapizy.retrofit_provider.RetrofitService;
import infobite.ibt.tapizy.retrofit_provider.WebResponse;
import infobite.ibt.tapizy.ui.activity.UserBotChatActivity;
import infobite.ibt.tapizy.ui.activity.user_activities.community_module.CommunityActivity;
import infobite.ibt.tapizy.ui.activity.user_activities.explore.ExploreActivity;
import infobite.ibt.tapizy.ui.activity.user_activities.recent_chat.RecentChatActivity;
import infobite.ibt.tapizy.ui.activity.user_activities.trending_module.TrendingActivity;
import infobite.ibt.tapizy.utils.Alerts;
import infobite.ibt.tapizy.utils.AppPreference;
import infobite.ibt.tapizy.utils.ConnectionDetector;
import infobite.ibt.tapizy.utils.expandable_layout.ExpandableLayout;
import infobite.ibt.tapizy.utils.slide_layout.ISlideChangeListener;
import infobite.ibt.tapizy.utils.slide_layout.ISlideListener;
import infobite.ibt.tapizy.utils.slide_layout.SlideLayout;
import infobite.ibt.tapizy.utils.slide_layout.renderers.TranslateRenderer;
import infobite.ibt.tapizy.utils.slide_layout.sliders.Direction;
import infobite.ibt.tapizy.utils.slide_layout.sliders.HorizontalSlider;
import retrofit2.Response;

import static infobite.ibt.tapizy.ui.activity.user_activities.HomeActivity.cd;
import static infobite.ibt.tapizy.ui.activity.user_activities.HomeActivity.mContext;
import static infobite.ibt.tapizy.ui.activity.user_activities.HomeActivity.retrofitApiClient;

public class HomeFragment extends Fragment implements View.OnClickListener,
        ExpandableLayout.OnExpansionUpdateListener, ISlideListener {

    private String swipetype = "";

    private Activity activity;
    private ExpandableLayout expandableLayoutLeft, expandableLayoutRight;
    private BottomSheetBehavior sheetBehavior;
    private LinearLayout llBottom;
    public static HomeFragment homeFragment;

    private View rootView, sView;
    private ArrayList<FavoriteBot> favoriteBotList = new ArrayList<>();
    private FavouriteBotListAdapter adapter;

    private LinearLayout rlMain, llCenter;
    private boolean isBlink = true;
    private List<SocialLinkList> socialLinkLists = new ArrayList<>();
    private SocialLinksListAdapter linksListAdapter;

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
        socialLinksRecyclerView();
    }

    private void init() {
        Glide.with(mContext)
                .load(R.drawable.tapizy_animate_home)
                .placeholder(R.drawable.tapizy_animate_home)
                .into((ImageView) rootView.findViewById(R.id.imgTapizy));
        expandableLayoutLeft = rootView.findViewById(R.id.expandableLayoutLeft);
        expandableLayoutRight = rootView.findViewById(R.id.expandableLayoutRight);
        expandableLayoutLeft.setOnExpansionUpdateListener(this);
        expandableLayoutRight.setOnExpansionUpdateListener(this);

        llBottom = rootView.findViewById(R.id.llBottom);
        sheetBehavior = BottomSheetBehavior.from(llBottom);
        rlMain = rootView.findViewById(R.id.rlMain);

        rootView.findViewById(R.id.txtEarn).setOnClickListener(this);
        rootView.findViewById(R.id.txtRedeem).setOnClickListener(this);
        rootView.findViewById(R.id.llCenter).setOnClickListener(this);
        rootView.findViewById(R.id.llexplore).setOnClickListener(this);
        rootView.findViewById(R.id.llTrending).setOnClickListener(this);
        rootView.findViewById(R.id.llCommunity).setOnClickListener(this);
        rootView.findViewById(R.id.llchat).setOnClickListener(this);

        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {

                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        rlMain.setVisibility(View.VISIBLE);
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        rlMain.setVisibility(View.GONE);
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        initSwipeLayout();
    }

    @Override
    public void onSlideDone(SlideLayout slider, boolean done) {
        if (done) {
            if (!swipetype.isEmpty() || swipetype != "") {
                if (swipetype.contains("left")) {
                    Alerts.show(mContext, "Payment Payed!");
                } else if (swipetype.contains("right")) {
                    Alerts.show(mContext, "Payment Received!");
                }
            }
        }
    }

    private void initSwipeLayout() {
        sView = rootView.findViewById(R.id.view);
        llCenter = rootView.findViewById(R.id.llCenter);
        SlideLayout slider = rootView.findViewById(R.id.slide3);
        slider.setRenderer(new TranslateRenderer());
        slider.setSlider(new HorizontalSlider(Direction.BOTH));
        slider.setChildId(R.id.slide_child_3);
        slider.addSlideListener(new ISlideListener() {
            @Override
            public void onSlideDone(SlideLayout slider, boolean done) {
                if (done) {
                    slider.reset();
                }
            }
        });

        slider.addSlideChangeListener(new ISlideChangeListener() {
            @Override
            public void onSlideStart(SlideLayout slider) {
                Vibrator vibe = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
                vibe.vibrate(100);
                sView.setBackground(getResources().getDrawable(R.drawable.stroke_circle));
                expandableLayoutRight.setExpanded(true);
                expandableLayoutLeft.setExpanded(true);
                rootView.findViewById(R.id.spin_kit).setVisibility(View.GONE);
            }

            @Override
            public void onSlideChanged(SlideLayout slider, float percentage) {

            }

            @Override
            public void onSetSwipeLitner(SlideLayout slider, boolean done, String swipe) {
                swipetype = swipe;
                onSlideDone(slider, done);
            }

            @Override
            public void onSlideFinished(SlideLayout slider, boolean done) {
                expandableLayoutRight.setExpanded(false);
                expandableLayoutLeft.setExpanded(false);
                rootView.findViewById(R.id.spin_kit).setVisibility(View.VISIBLE);
                sView.setBackground(getResources().getDrawable(R.drawable.circle_background_white));
                Log.d("TAG", "x,y = " + SlideLayout.isActionUp);
            }
        });
    }

    @Override
    public void onExpansionUpdate(float expansionFraction, int state) {
       /* rootView.findViewById(R.id.imgArrowLeft).setRotation(expansionFraction * 180);
        rootView.findViewById(R.id.imgArrowRight).setRotation(expansionFraction * 180);*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llCenter:
                if (isBlink) {
                    rootView.findViewById(R.id.spin_kit).setVisibility(View.GONE);
                    isBlink = false;
                } else {
                    rootView.findViewById(R.id.spin_kit).setVisibility(View.VISIBLE);
                    isBlink = true;
                }
                expandableLayoutRight.toggle();
                expandableLayoutLeft.toggle();
                break;
            case R.id.txtEarn:
                Alerts.show(mContext, "Under development...!!!");
                break;
            case R.id.txtRedeem:
                Alerts.show(mContext, "Under development...!!!");
                //startActivity(new Intent(mContext, RedeemActivity.class));
                break;
            /*case R.id.imgRemove:
                int position = Integer.parseInt(v.getTag().toString());
                String strId = favoriteBotList.get(position).getBotId();
                removeFav(strId);
                break;*/
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
                    Intent intentA = new Intent(mContext, UserBotChatActivity.class);
                    intentA.putExtra("bot_data", (Parcelable) botData);
                    startActivity(intentA);
                }
                break;
        }
    }

    private void favouriteBotRecyclerView() {
        RecyclerView rv_tapizy_list = rootView.findViewById(R.id.rv_tapizy_list);

        adapter = new FavouriteBotListAdapter(mContext, favoriteBotList, this, activity, retrofitApiClient);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mContext, 4);
        rv_tapizy_list.setLayoutManager(mLayoutManager);
        rv_tapizy_list.setItemAnimator(new DefaultItemAnimator());
        rv_tapizy_list.setAdapter(adapter);
        favouriteBotListApi();
    }

    private void favouriteBotListApi() {
        String strUserId = User.getUser().getUser().getUid();
        if (cd.isNetworkAvailable()) {
            RetrofitService.getFavBotList(null, retrofitApiClient.favBotList(strUserId), new WebResponse() {
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

    /*Social links api and recyclerview*/
    private void socialLinksRecyclerView() {
        RecyclerView recyclerViewSocialLinks = rootView.findViewById(R.id.recyclerViewSocialLinks);

        linksListAdapter = new SocialLinksListAdapter(mContext, socialLinkLists);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mContext, 4);
        recyclerViewSocialLinks.setLayoutManager(mLayoutManager);
        recyclerViewSocialLinks.setItemAnimator(new DefaultItemAnimator());
        recyclerViewSocialLinks.setAdapter(linksListAdapter);

        socialLinksApi();
    }

    private void socialLinksApi() {
        if (cd.isNetworkAvailable()) {
            RetrofitService.getSocialLinks(null, retrofitApiClient.socialLinksApi(), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    SocialLinkMainModal socialLinkMainModal = (SocialLinkMainModal) result.body();
                    socialLinkLists.clear();
                    if (socialLinkMainModal != null) {
                        if (!socialLinkMainModal.getError()) {
                            if (socialLinkMainModal.getSocialLink().size() > 0) {
                                socialLinkLists.addAll(socialLinkMainModal.getSocialLink());
                            }
                        } else {
                            Alerts.show(mContext, socialLinkMainModal.getMessage());
                        }
                    }
                    linksListAdapter.notifyDataSetChanged();
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

}
