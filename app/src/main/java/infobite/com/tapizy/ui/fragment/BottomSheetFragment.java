package infobite.com.tapizy.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import infobite.com.tapizy.R;
import infobite.com.tapizy.adapter.TapizyListAdapter;
import infobite.com.tapizy.model.TapizyListModel;
import infobite.com.tapizy.utils.BaseFragment;

public class BottomSheetFragment extends BaseFragment {
    private View rootview;
    private ArrayList<TapizyListModel> tapizyListModels = new ArrayList<>();
    private RecyclerView rv_tapizy_list;
    TapizyListAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.layout_bottomsheet_fragment,container,false);
        init();
        return rootview;
    }

    private void init() {
        mContext = getActivity();
        rv_tapizy_list = rootview.findViewById(R.id.rv_tapizy_list);
        addproduct();
    }
    private void addproduct()
    {

        for (int i = 0 ; i < 10 ; i++) {
            TapizyListModel tapizyListModel1 = new TapizyListModel("Daily Fun", R.drawable.home_services);
            tapizyListModels.add(tapizyListModel1);
            TapizyListModel tapizyListModel2 = new TapizyListModel("Indore Live", R.drawable.home_services);
            tapizyListModels.add(tapizyListModel2);
            TapizyListModel tapizyListModel3 = new TapizyListModel("Daily News",  R.drawable.home_services);
            tapizyListModels.add(tapizyListModel3);
            TapizyListModel tapizyListModel4 = new TapizyListModel("My Travel", R.drawable.home_services);
            tapizyListModels.add(tapizyListModel4);
            TapizyListModel tapizyListModel5 = new TapizyListModel("Daily Fun", R.drawable.home_services);
            tapizyListModels.add(tapizyListModel5);
        }
        adapter = new TapizyListAdapter(mContext,tapizyListModels);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mContext,3);
        rv_tapizy_list.setLayoutManager(mLayoutManager);
        rv_tapizy_list.setItemAnimator(new DefaultItemAnimator());
        rv_tapizy_list.setAdapter(adapter);
    }

}
