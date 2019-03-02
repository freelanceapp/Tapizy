package ibt.com.tapizy.model.chat_history;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatHistoryMsg implements Parcelable
{

    @SerializedName("message_from")
    @Expose
    private String messageFrom;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("entry_date")
    @Expose
    private String entryDate;
    @SerializedName("bot_uid")
    @Expose
    private String botUid;
    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("msg_id")
    @Expose
    private String msgId;
    public final static Parcelable.Creator<ChatHistoryMsg> CREATOR = new Creator<ChatHistoryMsg>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ChatHistoryMsg createFromParcel(Parcel in) {
            return new ChatHistoryMsg(in);
        }

        public ChatHistoryMsg[] newArray(int size) {
            return (new ChatHistoryMsg[size]);
        }

    }
            ;

    protected ChatHistoryMsg(Parcel in) {
        this.messageFrom = ((String) in.readValue((String.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.entryDate = ((String) in.readValue((String.class.getClassLoader())));
        this.botUid = ((String) in.readValue((String.class.getClassLoader())));
        this.uid = ((String) in.readValue((String.class.getClassLoader())));
        this.msgId = ((String) in.readValue((String.class.getClassLoader())));
    }

    public ChatHistoryMsg() {
    }

    public String getMessageFrom() {
        return messageFrom;
    }

    public void setMessageFrom(String messageFrom) {
        this.messageFrom = messageFrom;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getBotUid() {
        return botUid;
    }

    public void setBotUid(String botUid) {
        this.botUid = botUid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(messageFrom);
        dest.writeValue(message);
        dest.writeValue(entryDate);
        dest.writeValue(botUid);
        dest.writeValue(uid);
        dest.writeValue(msgId);
    }

    public int describeContents() {
        return 0;
    }

}
