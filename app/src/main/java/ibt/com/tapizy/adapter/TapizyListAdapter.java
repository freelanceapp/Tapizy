package ibt.com.tapizy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ibt.com.tapizy.R;
import ibt.com.tapizy.model.TapizyListModel;

public class TapizyListAdapter extends RecyclerView.Adapter<TapizyListAdapter.ViewHolder> {

    ArrayList<TapizyListModel> tapizyListModels;
    Context context;

    public TapizyListAdapter(Context context, ArrayList<TapizyListModel> tapizyListModels) {

        this.tapizyListModels = tapizyListModels;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout llayout;
        public ImageView iv_tapizy_logo;
        public TextView tv_tapizy_title;

        public ViewHolder(View v) {
            super(v);
            tv_tapizy_title = (TextView) v.findViewById(R.id.tv_tapizy_title);
            iv_tapizy_logo = (ImageView) v.findViewById(R.id.iv_tapizy_logo);
            llayout = (LinearLayout) v.findViewById(R.id.llayout);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_tapizy, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder Vholder, final int position) {

        Vholder.tv_tapizy_title.setText(tapizyListModels.get(position).getTapizy_title());
        Vholder.iv_tapizy_logo.setImageResource(tapizyListModels.get(position).getTapizy_logo());

        Vholder.llayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(context, ChatActivity.class);
                context.startActivity(intent);*/
            }
        });

    }

    @Override
    public int getItemCount() {

        return tapizyListModels.size();
    }
}
