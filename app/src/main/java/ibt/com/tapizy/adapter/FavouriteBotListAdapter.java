package ibt.com.tapizy.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;

import de.hdodenhof.circleimageview.CircleImageView;
import ibt.com.tapizy.R;
import ibt.com.tapizy.constant.Constant;
import ibt.com.tapizy.model.favourite_bot.FavoriteBot;
import ibt.com.tapizy.utils.drag_and_remove.ItemTouchHelperAdapter;
import ibt.com.tapizy.utils.drag_and_remove.OnStartDragListener;

public class FavouriteBotListAdapter extends RecyclerView.Adapter<FavouriteBotListAdapter.ViewHolder>
        implements ItemTouchHelperAdapter {

    private ArrayList<FavoriteBot> tapizyListModels;
    private Context context;
    private View.OnClickListener onClickListener;
    private OnStartDragListener mDragStartListener;

    public FavouriteBotListAdapter(Context context, ArrayList<FavoriteBot> tapizyListModels, View.OnClickListener onClickListener,
                                   OnStartDragListener dragStartListener) {
        this.tapizyListModels = tapizyListModels;
        this.context = context;
        this.onClickListener = onClickListener;
        this.mDragStartListener = dragStartListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout llayout;
        public CircleImageView iv_tapizy_logo;
        public ImageView imgRemove;
        public TextView tv_tapizy_title;

        public ViewHolder(View v) {
            super(v);
            tv_tapizy_title = v.findViewById(R.id.tv_tapizy_title);
            imgRemove = v.findViewById(R.id.imgRemove);
            iv_tapizy_logo = v.findViewById(R.id.iv_tapizy_logo);
            llayout = v.findViewById(R.id.llayout);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_favourite_bot, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final ViewHolder Vholder, final int position) {
        Vholder.tv_tapizy_title.setText(tapizyListModels.get(position).getBotName());
        Glide.with(context)
                .load(Constant.PROFILE_IMAGE_BASE_URL + tapizyListModels.get(position).getAvtar())
                .into(Vholder.iv_tapizy_logo);
        Vholder.llayout.setTag(position);
        Vholder.llayout.setOnClickListener(onClickListener);
        Vholder.imgRemove.setTag(position);
        Vholder.imgRemove.setOnClickListener(onClickListener);
        Vholder.imgRemove.setTag(position);
        Vholder.imgRemove.setOnClickListener(onClickListener);

        Vholder.iv_tapizy_logo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mDragStartListener.onStartDragData(tapizyListModels.get(position));
                return false;
            }
        });
        /*
        Vholder.iv_tapizy_logo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(Vholder);
                    mDragStartListener.onStartDragData(tapizyListModels.get(position));
                }
                return false;
            }
        });*/
    }

    @Override
    public void onItemDismiss(int position) {
        tapizyListModels.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(tapizyListModels, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public int getItemCount() {
        return tapizyListModels.size();
    }
}
