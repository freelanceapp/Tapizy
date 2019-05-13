package infobite.ibt.tapizy.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import infobite.ibt.tapizy.R;
import infobite.ibt.tapizy.constant.Constant;
import infobite.ibt.tapizy.model.community_post_modal.AnswerList;
import infobite.ibt.tapizy.model.community_post_modal.QuestionList;
import infobite.ibt.tapizy.retrofit_provider.RetrofitApiClient;
import infobite.ibt.tapizy.retrofit_provider.RetrofitService;
import infobite.ibt.tapizy.retrofit_provider.WebResponse;
import infobite.ibt.tapizy.utils.Alerts;
import infobite.ibt.tapizy.utils.AppPreference;
import okhttp3.ResponseBody;
import retrofit2.Response;

import static infobite.ibt.tapizy.ui.activity.user_activities.community_module.CommunityActivity.communityActivity;


public class PostQuestionAdapter extends RecyclerView.Adapter<PostQuestionAdapter.ViewHolder> {

    private ArrayList<QuestionList> questionList;
    private Context mContext;
    private RetrofitApiClient retrofitApiClient;

    public PostQuestionAdapter(ArrayList<QuestionList> questionList, Context mContext, RetrofitApiClient retrofitApiClient) {
        this.questionList = questionList;
        this.mContext = mContext;
        this.retrofitApiClient = retrofitApiClient;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View rootview = layoutInflater.inflate(R.layout.row_recylerview_post_question_adapter, null);

        return new ViewHolder(rootview);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final QuestionList questionList1 = questionList.get(i);
        viewHolder.tvQuestion.setText(questionList1.getQuestion());

        viewHolder.rclvAnswer.setHasFixedSize(true);
        viewHolder.rclvAnswer.setLayoutManager(new LinearLayoutManager(mContext));
        ArrayList<AnswerList> postAnswerList = new ArrayList<>();
        postAnswerList.addAll(questionList1.getAnswerList());

        PostAnswerAdapter postAnswerAdapter = new PostAnswerAdapter(mContext, postAnswerList);
        viewHolder.rclvAnswer.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        viewHolder.rclvAnswer.setItemAnimator(new DefaultItemAnimator());
        viewHolder.rclvAnswer.setAdapter(postAnswerAdapter);

        viewHolder.imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strAns = viewHolder.et_enter_answer.getText().toString();
                viewHolder.et_enter_answer.setText("");
                if (strAns.isEmpty()) {
                    Alerts.show(mContext, "Please enter text");
                } else {
                    postAnswerApi(questionList1.getQuestionId(), strAns);
                }
            }
        });

        if (postAnswerList.size() > 0) {
            viewHolder.tvEmpty.setVisibility(View.GONE);
        } else {
            viewHolder.tvEmpty.setVisibility(View.VISIBLE);
        }
    }

    private void postAnswerApi(String strQuesId, String strAnswer) {
        String strUserId = AppPreference.getStringPreference(mContext, Constant.USER_ID);
        RetrofitService.getloginData(new Dialog(mContext), retrofitApiClient.insertAnswerApi(strUserId,
                strQuesId, strAnswer), new WebResponse() {
            @Override
            public void onResponseSuccess(Response<?> result) {
                ResponseBody responseBody = (ResponseBody) result.body();
                try {
                    JSONObject jsonObject = new JSONObject(responseBody.string());
                    //Alerts.show(mContext, jsonObject + "");
                    String strCityId = AppPreference.getStringPreference(mContext, Constant.CITY_ID);
                    communityActivity.selectQuestionApi(strCityId);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onResponseFailed(String error) {
                Alerts.show(mContext, error);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private RecyclerView rclvAnswer;
        private TextView tvQuestion, tvEmpty;
        private ImageView imgSend;
        private EditText et_enter_answer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            et_enter_answer = itemView.findViewById(R.id.et_enter_answer);
            tvQuestion = itemView.findViewById(R.id.tv_question);
            tvEmpty = itemView.findViewById(R.id.tvEmpty);
            imgSend = itemView.findViewById(R.id.imgSend);
            rclvAnswer = itemView.findViewById(R.id.rclv_answers);
        }
    }
}
