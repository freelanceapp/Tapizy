package ibt.com.tapizy.model.chat_history;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatHistoryMainModal implements Parcelable
{

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("chat_msg")
    @Expose
    private List<ChatHistoryMsg> chatMsg = new ArrayList<ChatHistoryMsg>();
    public final static Parcelable.Creator<ChatHistoryMainModal> CREATOR = new Creator<ChatHistoryMainModal>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ChatHistoryMainModal createFromParcel(Parcel in) {
            return new ChatHistoryMainModal(in);
        }

        public ChatHistoryMainModal[] newArray(int size) {
            return (new ChatHistoryMainModal[size]);
        }

    }
            ;

    protected ChatHistoryMainModal(Parcel in) {
        this.error = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.chatMsg, (ChatHistoryMsg.class.getClassLoader()));
    }

    public ChatHistoryMainModal() {
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

    public List<ChatHistoryMsg> getChatMsg() {
        return chatMsg;
    }

    public void setChatMsg(List<ChatHistoryMsg> chatMsg) {
        this.chatMsg = chatMsg;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(error);
        dest.writeValue(message);
        dest.writeList(chatMsg);
    }

    public int describeContents() {
        return 0;
    }

}