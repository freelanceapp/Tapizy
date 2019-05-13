package infobite.ibt.tapizy.app_resume.ui.fragment;


import android.app.Activity;
import android.content.Intent;
import android.view.View;

import infobite.ibt.tapizy.app_resume.adapters.ExperienceAdapter;
import infobite.ibt.tapizy.app_resume.adapters.ResumeEventAdapter;
import infobite.ibt.tapizy.app_resume.datamodel.Experience;
import infobite.ibt.tapizy.app_resume.datamodel.Resume;
import infobite.ibt.tapizy.app_resume.ui.activities.ResumeEditActivity;
import infobite.ibt.tapizy.app_resume.ui.helper.ResumeEventFragment;
import infobite.ibt.tapizy.app_resume.ui.helper.ResumeFragment;

public class ExperienceFragment extends ResumeEventFragment<Experience> {
    public static ResumeFragment newInstance(Resume resume) {
        ResumeFragment fragment = new ExperienceFragment();
        fragment.setResume(resume);
        return fragment;
    }

    @Override
    protected void delete(int pos) {
        getResume().experience.remove(pos);
    }

    @Override
    public void onClick(int position) {
        Intent intent = ResumeEditActivity.getExperienceIntent(getContext());
        ResumeEditActivity.setData(intent, position, getResume().experience.get(position));
        startActivityForResult(intent, REQUEST_EDIT);
    }

    @Override
    protected void addClicked() {
        Intent intent = ResumeEditActivity.getExperienceIntent(getContext());
        startActivityForResult(intent, REQUEST_ADD);
    }

    @Override
    protected ResumeEventAdapter<Experience> getAdapter(View emptyView) {
        return new ExperienceAdapter(getResume().experience, this)
                .setEmptyView(emptyView);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ADD && resultCode == Activity.RESULT_OK) {
            getResume().experience.add(new Experience(ResumeEditActivity.getEvent(data)));
            notifyDataChanged();
        }
        if (requestCode == REQUEST_EDIT && resultCode == Activity.RESULT_OK) {
            int id = data.getIntExtra(ResumeEditActivity.FIELD_ID, -1);
            getResume().experience.get(id).cloneThis(ResumeEditActivity.getEvent(data));
            notifyDataChanged();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
