package infobite.ibt.tapizy.model.conversation_modal;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class NewConversationQuestionsData implements Parcelable {

    @SerializedName("question_id")
    @Expose
    private String questionId;
    @SerializedName("bot_id")
    @Expose
    private String botId;
    @SerializedName("response")
    @Expose
    private String response;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("relate_id")
    @Expose
    private String relateId;
    @SerializedName("option_relate_id")
    @Expose
    private String optionRelateId;
    @SerializedName("msg_sequence")
    @Expose
    private String msgSequence;
    @SerializedName("error_message")
    @Expose
    private String errorMessage;
    @SerializedName("from")
    @Expose
    private String from;
    @SerializedName("sub_response")
    @Expose
    private List<NewConversationSubResponseList> subResponse = new ArrayList<NewConversationSubResponseList>();
    public final static Parcelable.Creator<NewConversationQuestionsData> CREATOR = new Creator<NewConversationQuestionsData>() {


        @SuppressWarnings({
                "unchecked"
        })
        public NewConversationQuestionsData createFromParcel(Parcel in) {
            return new NewConversationQuestionsData(in);
        }

        public NewConversationQuestionsData[] newArray(int size) {
            return (new NewConversationQuestionsData[size]);
        }

    };

    protected NewConversationQuestionsData(Parcel in) {
        this.questionId = ((String) in.readValue((String.class.getClassLoader())));
        this.botId = ((String) in.readValue((String.class.getClassLoader())));
        this.response = ((String) in.readValue((String.class.getClassLoader())));
        this.type = ((String) in.readValue((String.class.getClassLoader())));
        this.relateId = ((String) in.readValue((String.class.getClassLoader())));
        this.optionRelateId = ((String) in.readValue((String.class.getClassLoader())));
        this.msgSequence = ((String) in.readValue((String.class.getClassLoader())));
        this.from = ((String) in.readValue((String.class.getClassLoader())));
        this.errorMessage = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.subResponse, (NewConversationSubResponseList.class.getClassLoader()));
    }

    public NewConversationQuestionsData() {
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getBotId() {
        return botId;
    }

    public void setBotId(String botId) {
        this.botId = botId;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRelateId() {
        return relateId;
    }

    public void setRelateId(String relateId) {
        this.relateId = relateId;
    }

    public String getOptionRelateId() {
        return optionRelateId;
    }

    public void setOptionRelateId(String optionRelateId) {
        this.optionRelateId = optionRelateId;
    }

    public String getMsgSequence() {
        return msgSequence;
    }

    public void setMsgSequence(String msgSequence) {
        this.msgSequence = msgSequence;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<NewConversationSubResponseList> getSubResponse() {
        return subResponse;
    }

    public void setSubResponse(List<NewConversationSubResponseList> subResponse) {
        this.subResponse = subResponse;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(questionId);
        dest.writeValue(botId);
        dest.writeValue(response);
        dest.writeValue(type);
        dest.writeValue(relateId);
        dest.writeValue(optionRelateId);
        dest.writeValue(msgSequence);
        dest.writeValue(from);
        dest.writeValue(errorMessage);
        dest.writeList(subResponse);
    }

    public int describeContents() {
        return 0;
    }

}