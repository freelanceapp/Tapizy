package infobite.com.tapizy.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import infobite.com.tapizy.R;
import infobite.com.tapizy.model.database_modal.ChatbotList;

public class CardStackAdapter extends RecyclerView.Adapter<CardStackAdapter.ViewHolder> {

    private List<ChatbotList> chatbotLists;
    private Context context;
    private View.OnClickListener onClickListener;

    public CardStackAdapter(List<ChatbotList> chatbotLists, Context context, View.OnClickListener onClickListener) {
        this.chatbotLists = chatbotLists;
        this.context = context;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_item_spot, viewGroup, false);
        ViewHolder viewHolder1 = new ViewHolder(view);
        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        /*holder.tvChatbotName.setText(chatbotLists.get(position).getName());
        holder.llChatbot.setTag(position);
        holder.llChatbot.setOnClickListener(onClickListener);*/
    }

    @Override
    public int getItemCount() {
        return chatbotLists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvQuestion, tvAnswer;

        public ViewHolder(View v) {
            super(v);
            tvQuestion = v.findViewById(R.id.tvQuestion);
            tvAnswer = v.findViewById(R.id.tvAnswer);
        }
    }
}
