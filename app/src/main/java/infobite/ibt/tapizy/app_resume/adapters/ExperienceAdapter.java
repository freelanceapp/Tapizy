package infobite.ibt.tapizy.app_resume.adapters;

import android.support.annotation.NonNull;

import java.util.List;

import infobite.ibt.tapizy.app_resume.datamodel.Experience;

/**
 * Created by ibrahim on 1/19/18.
 */

public class ExperienceAdapter extends ResumeEventAdapter<Experience> {

    public ExperienceAdapter(@NonNull List<Experience> list,
                             ResumeEventOnClickListener resumeEventOnClickListener) {
        super(list, resumeEventOnClickListener);
    }
}
