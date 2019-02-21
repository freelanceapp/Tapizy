package infobite.com.tapizy.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import infobite.com.tapizy.R;
import infobite.com.tapizy.model.TapizyLinearListModel;
import infobite.com.tapizy.ui.activity.TrandingActivity;

public class TapiziLinearListAdapter extends RecyclerView.Adapter<TapiziLinearListAdapter.ViewHolder> {
    ArrayList<TapizyLinearListModel> tapizyListModels;
    Context context;

    public TapiziLinearListAdapter(Context context, ArrayList<TapizyLinearListModel> tapizyListModels) {

        this.tapizyListModels = tapizyListModels;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout llayout;
        public ImageView iv_tapizy_logo;
        public TextView tv_tapizy_title,tv_tapizy_subtitle;

        public ViewHolder(View v) {
            super(v);
            tv_tapizy_title = (TextView) v.findViewById(R.id.title);
            tv_tapizy_subtitle = (TextView) v.findViewById(R.id.subtitle);
            iv_tapizy_logo = (ImageView) v.findViewById(R.id.logo);
            llayout = (LinearLayout) v.findViewById(R.id.lllayout);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_custom_layout, parent, false);

        ViewHolder viewHolder1 = new ViewHolder(view);

        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(final ViewHolder Vholder, final int position) {

        TapizyLinearListModel tapizyLinearListModel = tapizyListModels.get(position);

        Vholder.tv_tapizy_title.setText(tapizyLinearListModel.getTitle());
        Vholder.tv_tapizy_subtitle.setText(tapizyLinearListModel.getSubtitle());
        Vholder.iv_tapizy_logo.setImageDrawable(context.getResources().getDrawable(tapizyLinearListModel.getImage()));

      /*  Vholder.llayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TrandingActivity.class);
                context.startActivity(intent);
            }
        });*/

    }

    @Override
    public int getItemCount() {

        return tapizyListModels.size();
    }
}
