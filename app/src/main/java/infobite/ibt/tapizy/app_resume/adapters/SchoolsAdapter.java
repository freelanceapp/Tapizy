package infobite.ibt.tapizy.app_resume.adapters;

import android.support.annotation.NonNull;

import java.util.List;

import infobite.ibt.tapizy.app_resume.datamodel.School;

/**
 * Created by ibrahim on 1/19/18.
 */

public class SchoolsAdapter extends ResumeEventAdapter<School> {

    public SchoolsAdapter(@NonNull List<School> list,
                          ResumeEventOnClickListener resumeEventOnClickListener) {
        super(list, resumeEventOnClickListener);
    }
}