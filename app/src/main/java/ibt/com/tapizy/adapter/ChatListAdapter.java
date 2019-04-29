package ibt.com.tapizy.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.List;

import ibt.com.tapizy.R;
import ibt.com.tapizy.click_listener_interface.CustomClickListener;
import ibt.com.tapizy.model.conversation_modal.NewConversationQuestionsData;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder> {

    private List<NewConversationQuestionsData> chatList;
    private Context mContext;
    private View.OnClickListener onClickListener;
    private CustomClickListener customClickListener;

    public ChatListAdapter(Context mContext, List<NewConversationQuestionsData> chatList, View.OnClickListener onClickListener,
                           CustomClickListener customClickListener) {
        this.chatList = chatList;
        this.mContext = mContext;
        this.onClickListener = onClickListener;
        this.customClickListener = customClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_chat_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        NewConversationQuestionsData questionsData = chatList.get(position);
        String type = questionsData.getType();
        String strUserType = questionsData.getFrom();

        if (strUserType.equalsIgnoreCase("bot")) {
            holder.cardChat.setVisibility(View.VISIBLE);
            holder.cardChatUser.setVisibility(View.GONE);
            holder.tvQuestion.setText(questionsData.getResponse());
        } else {
            holder.cardChat.setVisibility(View.GONE);
            holder.cardChatUser.setVisibility(View.VISIBLE);
            holder.tvUser.setText(questionsData.getResponse());
        }

        if (type.equalsIgnoreCase("multichoice") || type.equalsIgnoreCase("contact_us") ||
                type.equalsIgnoreCase("link")) {
            holder.cardViewRange.setVisibility(View.GONE);
            ChipsListAdapter chipsListAdapter = new ChipsListAdapter(mContext, questionsData.getSubResponse(), questionsData,
                    customClickListener, position);
            holder.recyclerViewChips.setHasFixedSize(true);
            holder.recyclerViewChips.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            holder.recyclerViewChips.setAdapter(chipsListAdapter);
            chipsListAdapter.notifyDataSetChanged();
        } else if (type.equalsIgnoreCase("range")) {
            holder.cardViewRange.setVisibility(View.VISIBLE);
            if (questionsData.getSubResponse().size() == 1) {
                String strMin = questionsData.getSubResponse().get(0).getMinimum();
                String strMax = questionsData.getSubResponse().get(0).getMaximum();
                holder.txtMinimum.setText(strMin);
                holder.txtMaximum.setText(strMax);

                holder.txtPrefix.setText(questionsData.getSubResponse().get(0).getPrefix());
                holder.txtSuffix.setText(questionsData.getSubResponse().get(0).getSuffix());

                if (strMax.isEmpty()) {
                    strMax = "0";
                }
                if (strMin.isEmpty()) {
                    strMin = "0";
                }
                final int step = 10;
                final int max = Integer.parseInt(strMax);
                final int min = Integer.parseInt(strMin);
                holder.seekbarRange.setMax(max);
                //holder.seekbarRange.incrementProgressBy(100);
                final String finalStrMin = strMin;
                holder.txtValue.setText("" + finalStrMin);
                holder.seekbarRange.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (progress < 10) {
                            holder.txtValue.setText("" + finalStrMin);
                        } else {
                            holder.txtValue.setText("" + progress);
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                holder.txtDone.setTag(position);
                holder.txtDone.setOnClickListener(onClickListener);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private RecyclerView recyclerViewChips;
        private RelativeLayout rlMainView;
        private TextView tvQuestion, tvUser, txtMinimum, txtMaximum, txtDone, txtPrefix, txtValue, txtSuffix;
        private CardView cardChatUser, cardChat, cardViewRange;
        private SeekBar seekbarRange;

        public ViewHolder(View v) {
            super(v);
            seekbarRange = v.findViewById(R.id.seekbarRange);
            cardViewRange = v.findViewById(R.id.cardViewRange);
            cardChat = v.findViewById(R.id.cardChat);
            cardChatUser = v.findViewById(R.id.cardChatUser);
            recyclerViewChips = v.findViewById(R.id.recyclerViewChips);
            rlMainView = v.findViewById(R.id.rlMainView);
            tvQuestion = v.findViewById(R.id.tvQuestion);
            tvUser = v.findViewById(R.id.tvUser);
            txtMinimum = v.findViewById(R.id.txtMinimum);
            txtMaximum = v.findViewById(R.id.txtMaximum);
            txtPrefix = v.findViewById(R.id.txtPrefix);
            txtValue = v.findViewById(R.id.txtValue);
            txtSuffix = v.findViewById(R.id.txtSuffix);
            txtDone = v.findViewById(R.id.txtDone);
        }
    }
}
