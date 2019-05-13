package infobite.ibt.tapizy.model.conversation_modal;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewConversationMainModal implements Parcelable {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("questions")
    @Expose
    private NewConversationQuestionsData questions;
    public final static Parcelable.Creator<NewConversationMainModal> CREATOR = new Creator<NewConversationMainModal>() {


        @SuppressWarnings({
                "unchecked"
        })
        public NewConversationMainModal createFromParcel(Parcel in) {
            return new NewConversationMainModal(in);
        }

        public NewConversationMainModal[] newArray(int size) {
            return (new NewConversationMainModal[size]);
        }

    };

    protected NewConversationMainModal(Parcel in) {
        this.error = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.questions = ((NewConversationQuestionsData) in.readValue((NewConversationQuestionsData.class.getClassLoader())));
    }

    public NewConversationMainModal() {
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

    public NewConversationQuestionsData getQuestions() {
        return questions;
    }

    public void setQuestions(NewConversationQuestionsData questions) {
        this.questions = questions;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(error);
        dest.writeValue(message);
        dest.writeValue(questions);
    }

    public int describeContents() {
        return 0;
    }

}