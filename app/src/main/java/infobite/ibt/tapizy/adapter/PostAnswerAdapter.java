package infobite.ibt.tapizy.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import infobite.ibt.tapizy.R;
import infobite.ibt.tapizy.constant.Constant;
import infobite.ibt.tapizy.model.community_post_modal.AnswerList;

public class PostAnswerAdapter extends RecyclerView.Adapter<PostAnswerAdapter.ViewHolder> {
    private View rootview;
    private Context context;
    private ArrayList<AnswerList> postAnswerList;

    public PostAnswerAdapter(Context context, ArrayList<AnswerList> postAnswerList) {
        this.context = context;
        this.postAnswerList = postAnswerList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater li = LayoutInflater.from(context);
        rootview = li.inflate(R.layout.row_recylerview_post_answer_adapter, null);

        return new ViewHolder(rootview);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        AnswerList postAnswerListModal = postAnswerList.get(i);

        if (postAnswerListModal != null) {
            if (viewHolder.imgProfile != null) {
                Glide.with(context)
                        .load(Constant.PROFILE_IMAGE_BASE_URL + postAnswerListModal.getUserImage())
                        .into(viewHolder.imgProfile);
            }

            viewHolder.answerPersonName.setText(postAnswerListModal.getUserName());
            viewHolder.providedAnswer.setText(postAnswerListModal.getAnswer());
        }
    }

    @Override
    public int getItemCount() {
        return postAnswerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgProfile;
        private TextView answerPersonName, providedAnswer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProfile = itemView.findViewById(R.id.img_person_profile);
            answerPersonName = itemView.findViewById(R.id.tv_name_person);
            providedAnswer = itemView.findViewById(R.id.tv_person_answer);

        }
    }
}
