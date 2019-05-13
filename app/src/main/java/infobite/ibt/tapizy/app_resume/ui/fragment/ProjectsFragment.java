package infobite.ibt.tapizy.app_resume.ui.fragment;


import android.app.Activity;
import android.content.Intent;
import android.view.View;

import infobite.ibt.tapizy.app_resume.adapters.ProjectsAdapter;
import infobite.ibt.tapizy.app_resume.adapters.ResumeEventAdapter;
import infobite.ibt.tapizy.app_resume.datamodel.Project;
import infobite.ibt.tapizy.app_resume.datamodel.Resume;
import infobite.ibt.tapizy.app_resume.ui.activities.ResumeEditActivity;
import infobite.ibt.tapizy.app_resume.ui.helper.ResumeEventFragment;
import infobite.ibt.tapizy.app_resume.ui.helper.ResumeFragment;

public class ProjectsFragment extends ResumeEventFragment<Project> {
    public static ResumeFragment newInstance(Resume resume) {
        ResumeFragment fragment = new ProjectsFragment();
        fragment.setResume(resume);
        return fragment;
    }

    @Override
    protected void delete(int pos) {
        getResume().projects.remove(pos);
    }

    @Override
    public void onClick(int position) {
        Intent intent = ResumeEditActivity.getProjectIntent(getContext());
        ResumeEditActivity.setData(intent, position, getResume().projects.get(position));
        startActivityForResult(intent, REQUEST_EDIT);
    }

    @Override
    protected void addClicked() {
        Intent intent = ResumeEditActivity.getProjectIntent(getContext());
        startActivityForResult(intent, REQUEST_ADD);
    }

    @Override
    protected ResumeEventAdapter<Project> getAdapter(View emptyView) {
        return new ProjectsAdapter(getResume().projects, this)
                .setEmptyView(emptyView);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ADD && resultCode == Activity.RESULT_OK) {
            getResume().projects.add(new Project(ResumeEditActivity.getEvent(data)));
            notifyDataChanged();
        }
        if (requestCode == REQUEST_EDIT && resultCode == Activity.RESULT_OK) {
            int id = data.getIntExtra(ResumeEditActivity.FIELD_ID, -1);
            getResume().projects.get(id).cloneThis(ResumeEditActivity.getEvent(data));
            notifyDataChanged();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
