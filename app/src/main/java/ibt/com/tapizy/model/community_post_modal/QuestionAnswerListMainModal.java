package ibt.com.tapizy.model.community_post_modal;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuestionAnswerListMainModal implements Parcelable
{

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("news")
    @Expose
    private List<QuestionList> questionList = new ArrayList<QuestionList>();
    public final static Creator<QuestionAnswerListMainModal> CREATOR = new Creator<QuestionAnswerListMainModal>() {


        @SuppressWarnings({
                "unchecked"
        })
        public QuestionAnswerListMainModal createFromParcel(Parcel in) {
            return new QuestionAnswerListMainModal(in);
        }

        public QuestionAnswerListMainModal[] newArray(int size) {
            return (new QuestionAnswerListMainModal[size]);
        }

    }
            ;

    protected QuestionAnswerListMainModal(Parcel in) {
        this.error = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.questionList, (QuestionList.class.getClassLoader()));
    }

    public QuestionAnswerListMainModal() {
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<QuestionList> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<QuestionList> questionList) {
        this.questionList = questionList;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(error);
        dest.writeValue(message);
        dest.writeList(questionList);
    }

    public int describeContents() {
        return 0;
    }

}