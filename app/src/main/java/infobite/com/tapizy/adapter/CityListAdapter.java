package infobite.com.tapizy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import infobite.com.tapizy.R;

public class CityListAdapter extends  RecyclerView.Adapter<CityListAdapter.ViewHolder> {

    private List<String> cityList;
    private Context context;

    public CityListAdapter(Context context, List<String> cityList) {
        this.cityList = cityList;
        this.context = context;
    }

    @Override
    public CityListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_custom_city_list, parent, false);
        CityListAdapter.ViewHolder viewHolder1 = new CityListAdapter.ViewHolder(view);
        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(final CityListAdapter.ViewHolder holder, final int position) {
        holder.tvCity.setText(cityList.get(position));
        
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout rlMainView;
        public TextView tvCity;

        public ViewHolder(View v) {
            super(v);
            tvCity = v.findViewById(R.id.tv_city_name);
        }
    }
}
