package ibt.com.tapizy.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ibt.com.tapizy.R;
import ibt.com.tapizy.model.social_link.SocialLinkList;
import ibt.com.tapizy.ui.activity.user_activities.WebviewActivity;

public class SocialLinksListAdapter extends RecyclerView.Adapter<SocialLinksListAdapter.ViewHolder> {

    private List<SocialLinkList> linkLists;
    private Context context;

    public SocialLinksListAdapter(Context context, List<SocialLinkList> linkLists) {
        this.linkLists = linkLists;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView circleImageSocial;

        public ViewHolder(View v) {
            super(v);
            circleImageSocial = v.findViewById(R.id.circleImageSocial);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_social_links, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        Glide.with(context)
                .load(linkLists.get(position).getSocialIcon())
                .into(viewHolder.circleImageSocial);

        viewHolder.circleImageSocial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebviewActivity.class);
                intent.putExtra("url", linkLists.get(position).getWebUrl());
                intent.putExtra("title", linkLists.get(position).getTitle());
                intent.putExtra("coins", linkLists.get(position).getCoins());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return linkLists.size();
    }
}
