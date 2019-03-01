package ibt.com.tapizy.adapter;

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

import ibt.com.tapizy.R;
import ibt.com.tapizy.model.city_list_modal.CityList;

public class SpinnerCityListAdapter extends ArrayAdapter<CityList> {

    private final LayoutInflater mInflater;
    private final Context mContext;
    private final List<CityList> items;
    private final int mResource;

    public SpinnerCityListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<CityList> objects) {
        super(context, 0, objects);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mResource = resource;
        items = objects;
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

        TextView txtMchId = (TextView) view.findViewById(R.id.tvCityName);

        CityList urlModal = items.get(position);
        txtMchId.setText(urlModal.getCityname());
        return view;
    }
}