package ibt.com.tapizy.model.api_chat_list;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatList implements Parcelable {

    @SerializedName("bot_id")
    @Expose
    private String botId;
    @SerializedName("bot_name")
    @Expose
    private String botName;
    @SerializedName("avtar")
    @Expose
    private String avtar;
    @SerializedName("color")
    @Expose
    private String color;
    public final static Parcelable.Creator<ChatList> CREATOR = new Creator<ChatList>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ChatList createFromParcel(Parcel in) {
            return new ChatList(in);
        }

        public ChatList[] newArray(int size) {
            return (new ChatList[size]);
        }

    };

    protected ChatList(Parcel in) {
        this.botId = ((String) in.readValue((String.class.getClassLoader())));
        this.botName = ((String) in.readValue((String.class.getClassLoader())));
        this.avtar = ((String) in.readValue((String.class.getClassLoader())));
        this.color = ((String) in.readValue((String.class.getClassLoader())));
    }

    public ChatList() {
    }

    public String getBotId() {
        return botId;
    }

    public void setBotId(String botId) {
        this.botId = botId;
    }

    public String getBotName() {
        return botName;
    }

    public void setBotName(String botName) {
        this.botName = botName;
    }

    public String getAvtar() {
        return avtar;
    }

    public void setAvtar(String avtar) {
        this.avtar = avtar;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(botId);
        dest.writeValue(botName);
        dest.writeValue(avtar);
        dest.writeValue(color);
    }

    public int describeContents() {
        return 0;
    }

}