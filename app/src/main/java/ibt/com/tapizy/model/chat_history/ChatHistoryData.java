package ibt.com.tapizy.model.chat_history;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatHistoryData implements Parcelable {

    @SerializedName("chat_id")
    @Expose
    private String chatId;
    @SerializedName("message_from")
    @Expose
    private String messageFrom;
    @SerializedName("chat_message")
    @Expose
    private String chatMessage;
    @SerializedName("relate_id")
    @Expose
    private String relateId;
    @SerializedName("option_relate_id")
    @Expose
    private String optionRelateId;
    @SerializedName("msg_sequence")
    @Expose
    private String msgSequence;
    @SerializedName("msg_type")
    @Expose
    private String msgType;
    @SerializedName("sub_response")
    @Expose
    private List<ChatHistorySubResponse> subResponse = new ArrayList<ChatHistorySubResponse>();
    public final static Parcelable.Creator<ChatHistoryData> CREATOR = new Creator<ChatHistoryData>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ChatHistoryData createFromParcel(Parcel in) {
            return new ChatHistoryData(in);
        }

        public ChatHistoryData[] newArray(int size) {
            return (new ChatHistoryData[size]);
        }

    };

    protected ChatHistoryData(Parcel in) {
        this.chatId = ((String) in.readValue((String.class.getClassLoader())));
        this.messageFrom = ((String) in.readValue((String.class.getClassLoader())));
        this.chatMessage = ((String) in.readValue((String.class.getClassLoader())));
        this.relateId = ((String) in.readValue((String.class.getClassLoader())));
        this.optionRelateId = ((String) in.readValue((String.class.getClassLoader())));
        this.msgSequence = ((String) in.readValue((String.class.getClassLoader())));
        this.msgType = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.subResponse, (ChatHistorySubResponse.class.getClassLoader()));
    }

    public ChatHistoryData() {
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getMessageFrom() {
        return messageFrom;
    }

    public void setMessageFrom(String messageFrom) {
        this.messageFrom = messageFrom;
    }

    public String getChatMessage() {
        return chatMessage;
    }

    public void setChatMessage(String chatMessage) {
        this.chatMessage = chatMessage;
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

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public List<ChatHistorySubResponse> getSubResponse() {
        return subResponse;
    }

    public void setSubResponse(List<ChatHistorySubResponse> subResponse) {
        this.subResponse = subResponse;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(chatId);
        dest.writeValue(messageFrom);
        dest.writeValue(chatMessage);
        dest.writeValue(relateId);
        dest.writeValue(optionRelateId);
        dest.writeValue(msgSequence);
        dest.writeValue(msgType);
        dest.writeList(subResponse);
    }

    public int describeContents() {
        return 0;
    }

}