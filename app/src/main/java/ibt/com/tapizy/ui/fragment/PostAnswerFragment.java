package ibt.com.tapizy.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ibt.com.tapizy.R;
import ibt.com.tapizy.adapter.PostQuestionAdapter;
import ibt.com.tapizy.constant.Constant;
import ibt.com.tapizy.model.community_post_modal.QuestionList;
import ibt.com.tapizy.retrofit_provider.RetrofitService;
import ibt.com.tapizy.utils.BaseFragment;
import ibt.com.tapizy.utils.ConnectionDetector;

import static ibt.com.tapizy.ui.activity.community_module.CommunityActivity.fragmentManager;

public class PostAnswerFragment extends BaseFragment implements View.OnClickListener {

    private PostQuestionAdapter postQuestionAdapter;
    private ArrayList<QuestionList> questionList = new ArrayList<>();
    private View rootView;
    private RecyclerView rvQuestionPost;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_post_answer, container, false);
        activity = getActivity();
        mContext = getActivity();
        cd = new ConnectionDetector(mContext);
        retrofitApiClient = RetrofitService.getRetrofit();
        init();
        return rootView;
    }

    private void init() {
        rvQuestionPost = rootView.findViewById(R.id.rvQuestionPost);
        rootView.findViewById(R.id.btn_question).setOnClickListener(this);

        rootView.findViewById(R.id.tvEmpty).setVisibility(View.VISIBLE);
        ((TextView) rootView.findViewById(R.id.tvEmpty)).setText("Select city");

        rvQuestionPost.setHasFixedSize(true);
        rvQuestionPost.setLayoutManager(new LinearLayoutManager(mContext));

        postQuestionAdapter = new PostQuestionAdapter(questionList, mContext, retrofitApiClient);
        rvQuestionPost.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        rvQuestionPost.setItemAnimator(new DefaultItemAnimator());
        rvQuestionPost.setAdapter(postQuestionAdapter);
    }

    private void replaceFragment() {
        fragmentManager
                .beginTransaction()
                .replace(R.id.my_frame_container, new PostQuestionFragment(),
                        Constant.PostQuestionFragment).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_question:
                replaceFragment();
                break;
        }
    }

    public void selectQuestionApi(ArrayList<QuestionList> quesList) {
        questionList.clear();
        questionList.addAll(quesList);
        postQuestionAdapter.notifyDataSetChanged();

        if (questionList.size() > 0) {
            rootView.findViewById(R.id.btn_question).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.tvEmpty).setVisibility(View.GONE);
            ((TextView) rootView.findViewById(R.id.tvEmpty)).setText("");
        } else {
            rootView.findViewById(R.id.btn_question).setVisibility(View.GONE);
            rootView.findViewById(R.id.tvEmpty).setVisibility(View.VISIBLE);
            ((TextView) rootView.findViewById(R.id.tvEmpty)).setText("No data");
        }
    }
}

