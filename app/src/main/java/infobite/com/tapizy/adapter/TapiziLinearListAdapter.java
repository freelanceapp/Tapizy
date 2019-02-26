package infobite.com.tapizy.adapter;

import android.content.Context;
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

public class TapiziLinearListAdapter extends RecyclerView.Adapter<TapiziLinearListAdapter.ViewHolder> {

    private ArrayList<TapizyLinearListModel> tapizyListModels;
    private Context context;
    private View.OnClickListener onClickListener;

    public TapiziLinearListAdapter(Context context, ArrayList<TapizyLinearListModel> tapizyListModels, View.OnClickListener onClickListener) {
        this.tapizyListModels = tapizyListModels;
        this.context = context;
        this.onClickListener = onClickListener;
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

        Vholder.lllayout.setTag(position);
        Vholder.lllayout.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return tapizyListModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout lllayout;
        public ImageView iv_tapizy_logo;
        public TextView tv_tapizy_title, tv_tapizy_subtitle;

        public ViewHolder(View v) {
            super(v);
            tv_tapizy_title = (TextView) v.findViewById(R.id.title);
            tv_tapizy_subtitle = (TextView) v.findViewById(R.id.subtitle);
            iv_tapizy_logo = (ImageView) v.findViewById(R.id.logo);
            lllayout = (LinearLayout) v.findViewById(R.id.lllayout);
        }
    }

}
