package infobite.com.tapizy.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
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

import infobite.com.tapizy.R;
import infobite.com.tapizy.adapter.TapizyListAdapter;
import infobite.com.tapizy.model.TapizyListModel;
import infobite.com.tapizy.ui.activity.ChatActivity;
import infobite.com.tapizy.ui.activity.TrandingActivity;
import infobite.com.tapizy.utils.BaseFragment;

public class HomeFragment extends BaseFragment implements View.OnClickListener {

    private View rootview;
    private LinearLayout tapizy_ll, llbottomsheet, llchat, llexplore;
    private RelativeLayout tapizy_rl;
    private CardView cv_search;
    private ImageView backBtn, next;
    private RecyclerView rv_tapizy_list;
    private BottomSheetBehavior mBottomSheetBehaviour;
    private TapizyListAdapter adapter;
    private ArrayList<TapizyListModel> tapizyListModels = new ArrayList<>();
    private boolean check = false;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_home, container, false);
        init();
        return rootview;
    }

    private void init() {
        mContext = getActivity();

        llexplore = rootview.findViewById(R.id.llexplore);
        llexplore.setOnClickListener(this);
        llchat = rootview.findViewById(R.id.llchat);
        llchat.setOnClickListener(this);
        // cv_search = rootview.findViewById(R.id.cv_search);

        // cv_search.setOnClickListener(this);
        rv_tapizy_list = rootview.findViewById(R.id.rv_tapizy_list);
        addproduct();
    }

    private void addproduct() {
        for (int i = 0; i <= 35; i++) {
            TapizyListModel tapizyListModel1 = new TapizyListModel("A", R.drawable.daily_fun);
            tapizyListModels.add(tapizyListModel1);
        }

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
            case R.id.backBtn:
                tapizy_ll.setVisibility(View.GONE);
                tapizy_rl.setVisibility(View.VISIBLE);
                break;
        }
    }
}
