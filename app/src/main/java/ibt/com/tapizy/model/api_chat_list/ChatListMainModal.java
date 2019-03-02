package ibt.com.tapizy.model.api_chat_list;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ChatListMainModal implements Parcelable {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("conversation_bot_list")
    @Expose
    private List<ChatList> conversationBotList = new ArrayList<ChatList>();
    public final static Parcelable.Creator<ChatListMainModal> CREATOR = new Creator<ChatListMainModal>() {

        @SuppressWarnings({"unchecked"})
        public ChatListMainModal createFromParcel(Parcel in) {
            return new ChatListMainModal(in);
        }

        public ChatListMainModal[] newArray(int size) {
            return (new ChatListMainModal[size]);
        }
    };

    protected ChatListMainModal(Parcel in) {
        this.error = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.conversationBotList, (ChatList.class.getClassLoader()));
    }

    public ChatListMainModal() {
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

    public List<ChatList> getConversationBotList() {
        return conversationBotList;
    }

    public void setConversationBotList(List<ChatList> conversationBotList) {
        this.conversationBotList = conversationBotList;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(error);
        dest.writeValue(message);
        dest.writeList(conversationBotList);
    }

    public int describeContents() {
        return 0;
    }

}