package infobite.com.tapizy.model.database_modal;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ChatbotMainModal implements Parcelable {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("chatbot_list")
    @Expose
    private List<ChatbotList> chatbotList = new ArrayList<ChatbotList>();
    public final static Parcelable.Creator<ChatbotMainModal> CREATOR = new Creator<ChatbotMainModal>() {

        @SuppressWarnings({"unchecked"})
        public ChatbotMainModal createFromParcel(Parcel in) {
            return new ChatbotMainModal(in);
        }

        public ChatbotMainModal[] newArray(int size) {
            return (new ChatbotMainModal[size]);
        }

    };

    protected ChatbotMainModal(Parcel in) {
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.chatbotList, (infobite.com.tapizy.model.database_modal.ChatbotList.class.getClassLoader()));
    }

    public ChatbotMainModal() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ChatbotList> getChatbotList() {
        return chatbotList;
    }

    public void setChatbotList(List<ChatbotList> chatbotList) {
        this.chatbotList = chatbotList;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(message);
        dest.writeList(chatbotList);
    }

    public int describeContents() {
        return 0;
    }
}
