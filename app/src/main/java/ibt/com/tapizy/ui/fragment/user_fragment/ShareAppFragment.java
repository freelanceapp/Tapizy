package ibt.com.tapizy.ui.fragment.user_fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import ibt.com.tapizy.R;
import ibt.com.tapizy.utils.BaseFragment;

public class ShareAppFragment extends BaseFragment implements View.OnClickListener {

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_share_app, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        Glide.with(mContext)
                .load(R.drawable.splash_image)
                .placeholder(R.drawable.splash_image)
                .into((ImageView) rootView.findViewById(R.id.imgSplash));

    }

    @Override
    public void onClick(View v) {

    }
}
