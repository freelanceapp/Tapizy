package infobite.ibt.tapizy.model.community_post_modal;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuestionList implements Parcelable
{

    @SerializedName("question_id")
    @Expose
    private String questionId;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("answer")
    @Expose
    private List<AnswerList> answerList = new ArrayList<AnswerList>();
    public final static Creator<QuestionList> CREATOR = new Creator<QuestionList>() {


        @SuppressWarnings({
                "unchecked"
        })
        public QuestionList createFromParcel(Parcel in) {
            return new QuestionList(in);
        }

        public QuestionList[] newArray(int size) {
            return (new QuestionList[size]);
        }

    }
            ;

    protected QuestionList(Parcel in) {
        this.questionId = ((String) in.readValue((String.class.getClassLoader())));
        this.question = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.answerList, (AnswerList.class.getClassLoader()));
    }

    public QuestionList() {
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<AnswerList> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<AnswerList> answerList) {
        this.answerList = answerList;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(questionId);
        dest.writeValue(question);
        dest.writeList(answerList);
    }

    public int describeContents() {
        return 0;
    }

}