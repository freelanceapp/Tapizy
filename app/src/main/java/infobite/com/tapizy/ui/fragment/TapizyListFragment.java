package infobite.com.tapizy.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import infobite.com.tapizy.adapter.TapizyListAdapter;
import infobite.com.tapizy.model.TapizyListModel;
import infobite.com.tapizy.R;
import infobite.com.tapizy.ui.activity.ChatActivity;
import infobite.com.tapizy.ui.activity.CreateChatbotActivity;
import infobite.com.tapizy.ui.activity.TrandingActivity;
import infobite.com.tapizy.utils.BaseFragment;

public class TapizyListFragment extends BaseFragment implements View.OnClickListener {

    private View rootview;
    private LinearLayout tapizy_ll, llbottomsheet,llchat,llexplore;
    private RelativeLayout tapizy_rl;
    private CardView cv_search;
    private ImageView backBtn,next;
    private RecyclerView rv_tapizy_list;
    private BottomSheetBehavior mBottomSheetBehaviour;
    private TapizyListAdapter adapter;
    private ArrayList<TapizyListModel> tapizyListModels = new ArrayList<>();
    private boolean check = false;

    public TapizyListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_tapizy_list, container, false);

        init();

        return rootview;
    }

    private void init() {
        mContext = getActivity();

        tapizy_ll = rootview.findViewById(R.id.tapizy_ll);
        tapizy_rl = rootview.findViewById(R.id.tapizy_rl);
        llexplore = rootview.findViewById(R.id.llexplore);
        llexplore.setOnClickListener(this);
        llchat = rootview.findViewById(R.id.llchat);
        llchat.setOnClickListener(this);
       // cv_search = rootview.findViewById(R.id.cv_search);

        backBtn = rootview.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(this);
       // cv_search.setOnClickListener(this);
        rv_tapizy_list = rootview.findViewById(R.id.rv_tapizy_list);

        NestedScrollView nestedScrollView = (NestedScrollView) rootview.findViewById(R.id.nestedScrollView);
        mBottomSheetBehaviour = BottomSheetBehavior.from(nestedScrollView);
        llbottomsheet = rootview.findViewById(R.id.ll_bottom_sheet);
        llbottomsheet.setOnClickListener(this);

        addproduct();
    }

    private void bottomsheetonclick() {
        if (check) {
            check = false;
            mBottomSheetBehaviour.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            check = true;
            mBottomSheetBehaviour.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }


    private void addproduct() {
        TapizyListModel tapizyListModel1 = new TapizyListModel("A", R.drawable.daily_fun);
        tapizyListModels.add(tapizyListModel1);
        TapizyListModel tapizyListModel2 = new TapizyListModel("B", R.drawable.indore_live);
        tapizyListModels.add(tapizyListModel2);
        TapizyListModel tapizyListModel3 = new TapizyListModel("C", R.drawable.daily_news);
        tapizyListModels.add(tapizyListModel3);
        TapizyListModel tapizyListModel4 = new TapizyListModel("D", R.drawable.my_travel);
        tapizyListModels.add(tapizyListModel4);
        TapizyListModel tapizyListModel5 = new TapizyListModel("A", R.drawable.my_money);
        tapizyListModels.add(tapizyListModel5);
        TapizyListModel tapizyListModel6 = new TapizyListModel("C", R.drawable.my_health);
        tapizyListModels.add(tapizyListModel6);
        TapizyListModel tapizyListModel7 = new TapizyListModel("D", R.drawable.my_shopping);
        tapizyListModels.add(tapizyListModel7);
        TapizyListModel tapizyListModel8 = new TapizyListModel("E", R.drawable.my_health);
        tapizyListModels.add(tapizyListModel8);
        TapizyListModel tapizyListModel9 = new TapizyListModel("F", R.drawable.near_my_plans);
        tapizyListModels.add(tapizyListModel9);
        TapizyListModel tapizyListModel10 = new TapizyListModel("F", R.drawable.atoz_home_service);
        tapizyListModels.add(tapizyListModel10);
        TapizyListModel tapizyListModel11 = new TapizyListModel("D", R.drawable.my_shopping);
        tapizyListModels.add(tapizyListModel11);
        TapizyListModel tapizyListModel12 = new TapizyListModel("E", R.drawable.my_health);
        tapizyListModels.add(tapizyListModel12);
        TapizyListModel tapizyListMode113 = new TapizyListModel("F", R.drawable.near_my_plans);
        tapizyListModels.add(tapizyListModel3);
        TapizyListModel tapizyListModel114 = new TapizyListModel("F", R.drawable.atoz_home_service);
        tapizyListModels.add(tapizyListModel114);

        adapter = new TapizyListAdapter(mContext, tapizyListModels);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mContext, 4);
        rv_tapizy_list.setLayoutManager(mLayoutManager);
        rv_tapizy_list.setItemAnimator(new DefaultItemAnimator());
        rv_tapizy_list.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llexplore:
                Intent intent = new Intent(mContext, TrandingActivity.class);
                startActivity(intent);
            case R.id.llchat:
                Intent intent1 = new Intent(mContext, ChatActivity.class);
                startActivity(intent1);
                break;

           /* case R.id.cv_search:
                tapizy_ll.setVisibility(View.VISIBLE);
                tapizy_rl.setVisibility(View.GONE);
                break;*/

            case R.id.backBtn:
                tapizy_ll.setVisibility(View.GONE);
                tapizy_rl.setVisibility(View.VISIBLE);
                break;
            case R.id.ll_bottom_sheet:
                bottomsheetonclick();
                break;
        }
    }
}