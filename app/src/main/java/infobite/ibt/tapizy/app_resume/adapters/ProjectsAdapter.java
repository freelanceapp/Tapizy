package infobite.ibt.tapizy.app_resume.adapters;

import android.support.annotation.NonNull;
import android.view.View;

import java.util.List;

import infobite.ibt.tapizy.app_resume.datamodel.Project;

/**
 * Created by ibrahim on 1/19/18.
 */

public class ProjectsAdapter extends ResumeEventAdapter<Project> {

    public ProjectsAdapter(@NonNull List<Project> list,
                           ResumeEventOnClickListener resumeEventOnClickListener) {
        super(list, resumeEventOnClickListener);
    }

    @Override
    protected void updateViewHolder(ResumeEventAdapterViewHolder viewHolder) {
        viewHolder.subtitle.setVisibility(View.GONE);
    }
}