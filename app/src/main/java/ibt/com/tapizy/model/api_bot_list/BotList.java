package ibt.com.tapizy.model.api_bot_list;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BotList implements Parcelable {

    @SerializedName("bot_name")
    @Expose
    private String botName;
    @SerializedName("bot_id")
    @Expose
    private String uid;
    @SerializedName("bot_avtar")
    @Expose
    private String avtar;
    @SerializedName("bot_color")
    @Expose
    private String botColor;
    @SerializedName("webisite")
    @Expose
    private String website;
    @SerializedName("type")
    @Expose
    private String type;
    public final static Parcelable.Creator<BotList> CREATOR = new Creator<BotList>() {

        @SuppressWarnings({
                "unchecked"
        })
        public BotList createFromParcel(Parcel in) {
            return new BotList(in);
        }

        public BotList[] newArray(int size) {
            return (new BotList[size]);
        }

    };

    protected BotList(Parcel in) {
        this.botName = ((String) in.readValue((String.class.getClassLoader())));
        this.uid = ((String) in.readValue((String.class.getClassLoader())));
        this.avtar = ((String) in.readValue((String.class.getClassLoader())));
        this.botColor = ((String) in.readValue((String.class.getClassLoader())));
        this.website = ((String) in.readValue((String.class.getClassLoader())));
        this.type = ((String) in.readValue((String.class.getClassLoader())));
    }

    public BotList() {
    }

    public String getBotName() {
        return botName;
    }

    public void setBotName(String botName) {
        this.botName = botName;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAvtar() {
        return avtar;
    }

    public void setAvtar(String avtar) {
        this.avtar = avtar;
    }

    public String getBotColor() {
        return botColor;
    }

    public void setBotColor(String botColor) {
        this.botColor = botColor;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(botName);
        dest.writeValue(uid);
        dest.writeValue(avtar);
        dest.writeValue(botColor);
        dest.writeValue(website);
        dest.writeValue(type);
    }

    public int describeContents() {
        return 0;
    }

}
