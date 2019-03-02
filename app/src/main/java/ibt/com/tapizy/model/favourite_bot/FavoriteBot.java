package ibt.com.tapizy.model.favourite_bot;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FavoriteBot implements Parcelable
{

    @SerializedName("favorite_id")
    @Expose
    private String favoriteId;
    @SerializedName("bot_id")
    @Expose
    private String botId;
    @SerializedName("bot_name")
    @Expose
    private String botName;
    @SerializedName("avtar")
    @Expose
    private String avtar;
    public final static Parcelable.Creator<FavoriteBot> CREATOR = new Creator<FavoriteBot>() {


        @SuppressWarnings({
                "unchecked"
        })
        public FavoriteBot createFromParcel(Parcel in) {
            return new FavoriteBot(in);
        }

        public FavoriteBot[] newArray(int size) {
            return (new FavoriteBot[size]);
        }

    }
            ;

    protected FavoriteBot(Parcel in) {
        this.favoriteId = ((String) in.readValue((String.class.getClassLoader())));
        this.botId = ((String) in.readValue((String.class.getClassLoader())));
        this.botName = ((String) in.readValue((String.class.getClassLoader())));
        this.avtar = ((String) in.readValue((String.class.getClassLoader())));
    }

    public FavoriteBot() {
    }

    public String getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(String favoriteId) {
        this.favoriteId = favoriteId;
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

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(favoriteId);
        dest.writeValue(botId);
        dest.writeValue(botName);
        dest.writeValue(avtar);
    }

    public int describeContents() {
        return 0;
    }

}