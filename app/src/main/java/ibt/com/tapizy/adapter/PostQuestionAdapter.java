package ibt.com.tapizy.adapter;

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
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import ibt.com.tapizy.R;
import ibt.com.tapizy.constant.Constant;
import ibt.com.tapizy.model.User;
import ibt.com.tapizy.model.community_post_modal.AnswerList;
import ibt.com.tapizy.model.community_post_modal.QuestionList;
import ibt.com.tapizy.retrofit_provider.RetrofitApiClient;
import ibt.com.tapizy.retrofit_provider.RetrofitService;
import ibt.com.tapizy.retrofit_provider.WebResponse;
import ibt.com.tapizy.utils.Alerts;
import ibt.com.tapizy.utils.AppPreference;
import okhttp3.ResponseBody;
import retrofit2.Response;

import static ibt.com.tapizy.ui.activity.community_module.CommunityActivity.communityActivity;


public class PostQuestionAdapter extends RecyclerView.Adapter<PostQuestionAdapter.ViewHolder> {

    private PostAnswerAdapter postAnswerAdapter;
    private ArrayList<AnswerList> postAnswerList;

    private ArrayList<QuestionList> questionList;
    private View rootview;
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
        rootview = layoutInflater.inflate(R.layout.row_recylerview_post_question_adapter, null);

        return new ViewHolder(rootview);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final QuestionList questionList1 = questionList.get(i);
        viewHolder.tvQuestion.setText(questionList1.getQuestion());

        viewHolder.rclvAnswer.setHasFixedSize(true);
        viewHolder.rclvAnswer.setLayoutManager(new LinearLayoutManager(mContext));
        postAnswerList = new ArrayList<>();
        postAnswerList.addAll(questionList1.getAnswerList());
        postAnswerAdapter = new PostAnswerAdapter(mContext, postAnswerList);
        viewHolder.rclvAnswer.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        viewHolder.rclvAnswer.setItemAnimator(new DefaultItemAnimator());
        viewHolder.rclvAnswer.setAdapter(postAnswerAdapter);

        viewHolder.imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strAns = viewHolder.et_enter_answer.getText().toString();
                if (strAns.isEmpty()) {
                    Alerts.show(mContext, "Please enter text");
                } else {
                    postAnswerApi(questionList1.getQuestionId(), strAns);
                }
            }
        });
    }

    private void postAnswerApi(String strQuesId, String strAnswer) {
        String strUserId = User.getUser().getUser().getUid();
        RetrofitService.getloginData(new Dialog(mContext), retrofitApiClient.insertAnswerApi(strUserId,
                strQuesId, strAnswer), new WebResponse() {
            @Override
            public void onResponseSuccess(Response<?> result) {
                ResponseBody responseBody = (ResponseBody) result.body();
                try {
                    JSONObject jsonObject = new JSONObject(responseBody.string());
                    Alerts.show(mContext, jsonObject + "");
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
        private TextView tvQuestion;
        private CircleImageView imgSend;
        private EditText et_enter_answer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            et_enter_answer = itemView.findViewById(R.id.et_enter_answer);
            tvQuestion = itemView.findViewById(R.id.tv_question);
            imgSend = itemView.findViewById(R.id.imgSend);
            rclvAnswer = itemView.findViewById(R.id.rclv_answers);
        }
    }
}
