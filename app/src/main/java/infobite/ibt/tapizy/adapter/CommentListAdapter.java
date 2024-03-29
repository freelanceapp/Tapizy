package infobite.ibt.tapizy.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import infobite.ibt.tapizy.R;
import infobite.ibt.tapizy.constant.Constant;
import infobite.ibt.tapizy.model.timeline_modal.Comment;
import infobite.ibt.tapizy.utils.AppPreference;

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.ViewHolder> {

    private List<Comment> notificationModels;
    private Context context;

    public CommentListAdapter(List<Comment> notificationModels, Context context) {
        this.notificationModels = notificationModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater li = LayoutInflater.from(context);
        View viewt = li.inflate(R.layout.row_comment_list, null);
        return new ViewHolder(viewt);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        Comment notificationmodel = notificationModels.get(i);

        String strUserImage = AppPreference.getStringPreference(context, Constant.USER_IMAGE);

        if (notificationmodel.getUProfile() == null || notificationmodel.getUProfile().isEmpty()) {
            Glide.with(context).load(Constant.PROFILE_IMAGE_BASE_URL + strUserImage)
                    .into(viewHolder.imgUserProfile);
        } else {
            Glide.with(context).load(Constant.PROFILE_IMAGE_BASE_URL + notificationmodel.getUProfile())
                    .into(viewHolder.imgUserProfile);
        }

        viewHolder.tvUserName.setText(notificationmodel.getUName());
        viewHolder.tvCommentTime.setText(notificationmodel.getEntryDate());
        viewHolder.tvComment.setText(notificationmodel.getComment());
    }

    @Override
    public int getItemCount() {
        return notificationModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView imgUserProfile;
        TextView tvUserName, tvCommentTime, tvComment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgUserProfile = itemView.findViewById(R.id.imgUserProfile);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvCommentTime = itemView.findViewById(R.id.tvCommentTime);
            tvComment = itemView.findViewById(R.id.tvComment);
        }
    }
}
