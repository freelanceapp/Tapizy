package infobite.ibt.tapizy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import infobite.ibt.tapizy.R;
import infobite.ibt.tapizy.model.transaction.TransactionCoinList;

public class TransactionListAdapter extends RecyclerView.Adapter<TransactionListAdapter.ViewHolder> {

    private List<TransactionCoinList> transactionCoinLists;
    private Context context;
    private View.OnClickListener onClickListener;

    public TransactionListAdapter(Context context, List<TransactionCoinList> transactionCoinLists, View.OnClickListener onClickListener) {
        this.transactionCoinLists = transactionCoinLists;
        this.context = context;
        this.onClickListener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_transaction, parent, false);
        ViewHolder viewHolder1 = new ViewHolder(view);
        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        String strReceive = "Coins received from " + transactionCoinLists.get(position).getPurpose() + " for " +
                transactionCoinLists.get(position).getPurposeType();
        String strPay = "Coins paid for " + transactionCoinLists.get(position).getPurposeType();
        String status = transactionCoinLists.get(position).getStatus();
        String paymentStatus = transactionCoinLists.get(position).getPaymentStatus();
        if (status.equals("0")) {
            holder.txtReceivedCoins.setText("-" + transactionCoinLists.get(position).getCoins());
            holder.txtReceivedCoins.setTextColor(context.getResources().getColor(R.color.black));
            holder.txtReceivedString.setText(strPay);
        } else {
            holder.txtReceivedCoins.setTextColor(context.getResources().getColor(R.color.bot_green));
            holder.txtReceivedCoins.setText("+" + transactionCoinLists.get(position).getCoins());
            holder.txtReceivedString.setText(strReceive);
        }
        if (paymentStatus.equals("1")) {
            holder.txtPaymentStatus.setText("Success");
        } else {
            holder.txtPaymentStatus.setText("Pending");
        }

        holder.txtTime.setText(transactionCoinLists.get(position).getEntryDate());
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return transactionCoinLists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtReceivedString, txtTime, txtPaymentStatus, txtReceivedCoins;

        public ViewHolder(View v) {
            super(v);
            txtReceivedString = v.findViewById(R.id.txtReceivedString);
            txtTime = v.findViewById(R.id.txtTime);
            txtPaymentStatus = v.findViewById(R.id.txtPaymentStatus);
            txtReceivedCoins = v.findViewById(R.id.txtReceivedCoins);
        }
    }
}
