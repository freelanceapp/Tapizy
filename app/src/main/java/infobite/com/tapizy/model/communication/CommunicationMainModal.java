package infobite.com.tapizy.model.communication;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommunicationMainModal implements Parcelable
{

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("welcome_message")
    @Expose
    private String welcomeMessage;
    @SerializedName("conversation")
    @Expose
    private ConversationData conversation;
    public final static Parcelable.Creator<CommunicationMainModal> CREATOR = new Creator<CommunicationMainModal>() {


        @SuppressWarnings({
                "unchecked"
        })
        public CommunicationMainModal createFromParcel(Parcel in) {
            return new CommunicationMainModal(in);
        }

        public CommunicationMainModal[] newArray(int size) {
            return (new CommunicationMainModal[size]);
        }

    }
            ;

    protected CommunicationMainModal(Parcel in) {
        this.error = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.welcomeMessage = ((String) in.readValue((String.class.getClassLoader())));
        this.conversation = ((ConversationData) in.readValue((ConversationData.class.getClassLoader())));
    }

    public CommunicationMainModal() {
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

    public String getWelcomeMessage() {
        return welcomeMessage;
    }

    public void setWelcomeMessage(String welcomeMessage) {
        this.welcomeMessage = welcomeMessage;
    }

    public ConversationData getConversation() {
        return conversation;
    }

    public void setConversation(ConversationData conversation) {
        this.conversation = conversation;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(error);
        dest.writeValue(message);
        dest.writeValue(welcomeMessage);
        dest.writeValue(conversation);
    }

    public int describeContents() {
        return 0;
    }

}