package infobite.ibt.tapizy.model.bot_profile_data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BotDetail implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("bot_name")
    @Expose
    private String botName;
    @SerializedName("bot_color")
    @Expose
    private String botColor;
    @SerializedName("bot_avtar")
    @Expose
    private String botAvtar;
    @SerializedName("bot_type")
    @Expose
    private String botType;
    @SerializedName("bot_sub_type")
    @Expose
    private String botSubType;
    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("webisite")
    @Expose
    private String webisite;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("entry_date")
    @Expose
    private String entryDate;
    @SerializedName("coins")
    @Expose
    private String coins;
    @SerializedName("update_entry_date")
    @Expose
    private String updateEntryDate;
    public final static Parcelable.Creator<BotDetail> CREATOR = new Creator<BotDetail>() {
        @SuppressWarnings({
                "unchecked"
        })
        public BotDetail createFromParcel(Parcel in) {
            return new BotDetail(in);
        }

        public BotDetail[] newArray(int size) {
            return (new BotDetail[size]);
        }

    };

    protected BotDetail(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.botName = ((String) in.readValue((String.class.getClassLoader())));
        this.botColor = ((String) in.readValue((String.class.getClassLoader())));
        this.botAvtar = ((String) in.readValue((String.class.getClassLoader())));
        this.botType = ((String) in.readValue((String.class.getClassLoader())));
        this.botSubType = ((String) in.readValue((String.class.getClassLoader())));
        this.uid = ((String) in.readValue((String.class.getClassLoader())));
        this.webisite = ((String) in.readValue((String.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.type = ((String) in.readValue((String.class.getClassLoader())));
        this.entryDate = ((String) in.readValue((String.class.getClassLoader())));
        this.updateEntryDate = ((String) in.readValue((String.class.getClassLoader())));
        this.coins = ((String) in.readValue((String.class.getClassLoader())));
    }

    public BotDetail() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBotName() {
        return botName;
    }

    public void setBotName(String botName) {
        this.botName = botName;
    }

    public String getBotColor() {
        return botColor;
    }

    public void setBotColor(String botColor) {
        this.botColor = botColor;
    }

    public String getBotAvtar() {
        return botAvtar;
    }

    public void setBotAvtar(String botAvtar) {
        this.botAvtar = botAvtar;
    }

    public String getBotType() {
        return botType;
    }

    public void setBotType(String botType) {
        this.botType = botType;
    }

    public String getBotSubType() {
        return botSubType;
    }

    public void setBotSubType(String botSubType) {
        this.botSubType = botSubType;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getWebisite() {
        return webisite;
    }

    public void setWebisite(String webisite) {
        this.webisite = webisite;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getUpdateEntryDate() {
        return updateEntryDate;
    }

    public void setUpdateEntryDate(String updateEntryDate) {
        this.updateEntryDate = updateEntryDate;
    }

    public String getCoins() {
        return coins;
    }

    public void setCoins(String coins) {
        this.coins = coins;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(botName);
        dest.writeValue(botColor);
        dest.writeValue(botAvtar);
        dest.writeValue(botType);
        dest.writeValue(botSubType);
        dest.writeValue(uid);
        dest.writeValue(webisite);
        dest.writeValue(description);
        dest.writeValue(type);
        dest.writeValue(entryDate);
        dest.writeValue(updateEntryDate);
        dest.writeValue(coins);
    }

    public int describeContents() {
        return 0;
    }

}