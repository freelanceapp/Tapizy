package infobite.com.tapizy.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import infobite.com.tapizy.Adapter.TapizyListAdapter;
import infobite.com.tapizy.Model.TapizyListModel;
import infobite.com.tapizy.R;

public class TapizyListFragment extends Fragment implements View.OnClickListener {

    private View rootview;
    private LinearLayout tapizy_ll;
    private RelativeLayout tapizy_rl;
    private ImageView searchBtn, backBtn;
    private RecyclerView rv_tapizy_list;
    TapizyListAdapter adapter;
    private ArrayList<TapizyListModel> tapizyListModels = new ArrayList<>();

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
        rootview =  inflater.inflate(R.layout.fragment_tapizy_list, container, false);

        init();

        return rootview;
    }

    private void init()
    {
        tapizy_ll = rootview.findViewById(R.id.tapizy_ll);
        tapizy_rl = rootview.findViewById(R.id.tapizy_rl);
        searchBtn = rootview.findViewById(R.id.searchBtn);
        backBtn = rootview.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(this);
        searchBtn.setOnClickListener(this);
        rv_tapizy_list = rootview.findViewById(R.id.rv_tapizy_list);

        addproduct();
    }

    private void addproduct()
    {

        for (int i = 0 ; i < 10 ; i++) {
            TapizyListModel tapizyListModel1 = new TapizyListModel("Daily Fun", "Get Daily Jokes", R.drawable.home_services);
            tapizyListModels.add(tapizyListModel1);
            TapizyListModel tapizyListModel2 = new TapizyListModel("Indore Live", "Get Daily Jokes", R.drawable.home_services);
            tapizyListModels.add(tapizyListModel2);
            TapizyListModel tapizyListModel3 = new TapizyListModel("Daily News", "Get Daily Jokes", R.drawable.home_services);
            tapizyListModels.add(tapizyListModel3);
            TapizyListModel tapizyListModel4 = new TapizyListModel("My Travel", "Get Daily Jokes", R.drawable.home_services);
            tapizyListModels.add(tapizyListModel4);
            TapizyListModel tapizyListModel5 = new TapizyListModel("Daily Fun", "Get Daily Jokes", R.drawable.home_services);
            tapizyListModels.add(tapizyListModel5);

        }
        adapter = new TapizyListAdapter(getActivity(),tapizyListModels);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rv_tapizy_list.setLayoutManager(mLayoutManager);
        rv_tapizy_list.setItemAnimator(new DefaultItemAnimator());
        rv_tapizy_list.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.searchBtn :
                tapizy_ll.setVisibility(View.VISIBLE);
                tapizy_rl.setVisibility(View.GONE);
                break;

            case R.id.backBtn :
                tapizy_ll.setVisibility(View.GONE);
                tapizy_rl.setVisibility(View.VISIBLE);
                break;
        }
    }
}
