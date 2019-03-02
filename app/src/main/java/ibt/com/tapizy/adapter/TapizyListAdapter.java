package ibt.com.tapizy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import ibt.com.tapizy.R;
import ibt.com.tapizy.constant.Constant;
import ibt.com.tapizy.model.favourite_bot.FavoriteBot;

public class TapizyListAdapter extends RecyclerView.Adapter<TapizyListAdapter.ViewHolder> {

    ArrayList<FavoriteBot> tapizyListModels;
    Context context;

    public TapizyListAdapter(Context context, ArrayList<FavoriteBot> tapizyListModels) {

        this.tapizyListModels = tapizyListModels;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout llayout;
        public CircleImageView iv_tapizy_logo;
        public TextView tv_tapizy_title;

        public ViewHolder(View v) {
            super(v);
            tv_tapizy_title = v.findViewById(R.id.tv_tapizy_title);
            iv_tapizy_logo = v.findViewById(R.id.iv_tapizy_logo);
            llayout = v.findViewById(R.id.llayout);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_tapizy, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder Vholder, final int position) {
        Vholder.tv_tapizy_title.setText(tapizyListModels.get(position).getBotName());
        Glide.with(context)
                .load(Constant.PROFILE_IMAGE_BASE_URL + tapizyListModels.get(position).getAvtar())
                .into(Vholder.iv_tapizy_logo);
    }

    @Override
    public int getItemCount() {

        return tapizyListModels.size();
    }
}
