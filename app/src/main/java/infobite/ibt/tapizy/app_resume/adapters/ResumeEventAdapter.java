package infobite.ibt.tapizy.app_resume.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

import infobite.ibt.tapizy.R;
import infobite.ibt.tapizy.app_resume.datamodel.ResumeEvent;

/**
 * Created by ibrahim on 1/19/18.
 */

abstract public class ResumeEventAdapter<T extends ResumeEvent>
        extends RecyclerView.Adapter<ResumeEventAdapterViewHolder> {
    private List<T> list;
    private ResumeEventOnClickListener mResumeEventOnClickListener;

    public ResumeEventAdapter(@NonNull List<T> list,
                              ResumeEventOnClickListener resumeEventOnClickListener) {
        this.list = list;
        mResumeEventOnClickListener = resumeEventOnClickListener;
    }

    public interface ResumeEventOnClickListener {
        void onClick(int position);
    }

    @NonNull
    @Override
    public ResumeEventAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.resume_item_event, parent, false);
        ResumeEventAdapterViewHolder viewHolder =
                new ResumeEventAdapterViewHolder(itemView, mResumeEventOnClickListener);
        updateViewHolder(viewHolder);
        return viewHolder;
    }

    protected void updateViewHolder(ResumeEventAdapterViewHolder viewHolder) {

    }

    public ResumeEventAdapter<T> setEmptyView(final View emptyView) {
        this.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                if (getItemCount() == 0) {
                    emptyView.setVisibility(View.VISIBLE);
                } else {
                    emptyView.setVisibility(View.INVISIBLE);
                }
            }
        });
        return this;
    }

    @Override
    public void onBindViewHolder(@NonNull ResumeEventAdapterViewHolder holder, int position) {
        ResumeEvent event = list.get(position);
        holder.itemView.setTag(position);
        holder.title.setText(event.getTitle());
        holder.detail.setText(event.getDetail());
        holder.subtitle.setText(event.getSubtitle());
        holder.description.setText(event.getDescription());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
