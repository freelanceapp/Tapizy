package infobite.ibt.tapizy.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import infobite.ibt.tapizy.R;
import infobite.ibt.tapizy.model.spinner_faq.FaqModel;

public class SpinnerFaqAdapter extends ArrayAdapter<FaqModel> {

    private LayoutInflater mInflater;
    private Context mContext;
    private List<FaqModel> faqModels;
    private int mResource;

    public SpinnerFaqAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<FaqModel> objects) {
        super(context, 0, objects);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mResource = resource;
        faqModels = objects;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    @Override
    public @NonNull
    View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    private View createItemView(int position, View convertView, ViewGroup parent) {
        final View view = mInflater.inflate(mResource, parent, false);

        TextView txtMchId = (TextView) view.findViewById(R.id.txtData);

        FaqModel urlModal = faqModels.get(position);
        txtMchId.setText(urlModal.getQuestion());
        return view;
    }
}