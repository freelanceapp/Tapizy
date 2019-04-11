package ibt.com.tapizy.ui.fragment.user_fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import ibt.com.tapizy.R;
import ibt.com.tapizy.utils.BaseFragment;

public class RewardsFragment extends BaseFragment implements View.OnClickListener {

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_availability, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        LinearLayout llRoot = rootView.findViewById(R.id.llRoot);
        llRoot.setBackgroundColor(getResources().getColor(R.color.sienna));
        ((TextView) rootView.findViewById(R.id.txtTitle)).setText("Rewards");
    }

    @Override
    public void onClick(View v) {

    }
}