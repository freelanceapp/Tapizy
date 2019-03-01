package ibt.com.tapizy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import ibt.com.tapizy.R;
import ibt.com.tapizy.model.navigation_item_modal.NavItemList;

public class NavigationItemListAdapter extends RecyclerView.Adapter<NavigationItemListAdapter.ViewHolder> {

    private List<NavItemList> chatbotLists;
    private Context context;
    private View.OnClickListener onClickListener;

    public NavigationItemListAdapter(Context context, List<NavItemList> chatbotLists, View.OnClickListener onClickListener) {
        this.chatbotLists = chatbotLists;
        this.context = context;
        this.onClickListener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_navigation_item, parent, false);
        ViewHolder viewHolder1 = new ViewHolder(view);
        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvItemName.setText(chatbotLists.get(position).getName());
        holder.imgItem.setImageResource(chatbotLists.get(position).getImage());

        holder.llItem.setTag(position);
        holder.llItem.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return chatbotLists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout llItem;
        private ImageView imgItem;
        public TextView tvItemName;

        public ViewHolder(View v) {
            super(v);
            imgItem = v.findViewById(R.id.imgItem);
            tvItemName = v.findViewById(R.id.tvItemName);
            llItem = v.findViewById(R.id.llItem);
        }
    }
}
