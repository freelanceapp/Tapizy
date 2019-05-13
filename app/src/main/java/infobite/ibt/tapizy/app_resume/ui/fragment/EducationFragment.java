package infobite.ibt.tapizy.app_resume.ui.fragment;


import android.app.Activity;
import android.content.Intent;
import android.view.View;

import infobite.ibt.tapizy.app_resume.adapters.ResumeEventAdapter;
import infobite.ibt.tapizy.app_resume.adapters.SchoolsAdapter;
import infobite.ibt.tapizy.app_resume.datamodel.Resume;
import infobite.ibt.tapizy.app_resume.datamodel.School;
import infobite.ibt.tapizy.app_resume.ui.activities.ResumeEditActivity;
import infobite.ibt.tapizy.app_resume.ui.helper.ResumeEventFragment;
import infobite.ibt.tapizy.app_resume.ui.helper.ResumeFragment;

public class EducationFragment extends ResumeEventFragment<School> {
    public static ResumeFragment newInstance(Resume resume) {
        ResumeFragment fragment = new EducationFragment();
        fragment.setResume(resume);
        return fragment;
    }

    @Override
    protected void delete(int pos) {
        getResume().schools.remove(pos);
    }

    @Override
    public void onClick(int position) {
        Intent intent = ResumeEditActivity.getSchoolIntent(getContext());
        ResumeEditActivity.setData(intent, position, getResume().schools.get(position));
        startActivityForResult(intent, REQUEST_EDIT);
    }

    @Override
    protected void addClicked() {
        Intent intent = ResumeEditActivity.getSchoolIntent(getContext());
        startActivityForResult(intent, REQUEST_ADD);
    }

    @Override
    protected ResumeEventAdapter<School> getAdapter(View emptyView) {
        return new SchoolsAdapter(getResume().schools, this)
                .setEmptyView(emptyView);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ADD && resultCode == Activity.RESULT_OK) {
            getResume().schools.add(new School(ResumeEditActivity.getEvent(data)));
            notifyDataChanged();
        }
        if (requestCode == REQUEST_EDIT && resultCode == Activity.RESULT_OK) {
            int id = data.getIntExtra(ResumeEditActivity.FIELD_ID, -1);
            getResume().schools.get(id).cloneThis(ResumeEditActivity.getEvent(data));
            notifyDataChanged();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
