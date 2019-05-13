package infobite.ibt.tapizy.model.chat_history;

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
    @SerializedName("chat")
    @Expose
    private List<ChatHistoryData> chat = new ArrayList<ChatHistoryData>();
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
        in.readList(this.chat, (ChatHistoryData.class.getClassLoader()));
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

    public List<ChatHistoryData> getChat() {
        return chat;
    }

    public void setChat(List<ChatHistoryData> chat) {
        this.chat = chat;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(error);
        dest.writeValue(message);
        dest.writeList(chat);
    }

    public int describeContents() {
        return 0;
    }

}