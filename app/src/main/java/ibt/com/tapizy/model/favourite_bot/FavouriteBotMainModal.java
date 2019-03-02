package ibt.com.tapizy.model.favourite_bot;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FavouriteBotMainModal implements Parcelable
{

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("favorite_bot")
    @Expose
    private List<FavoriteBot> favoriteBot = new ArrayList<FavoriteBot>();
    public final static Parcelable.Creator<FavouriteBotMainModal> CREATOR = new Creator<FavouriteBotMainModal>() {


        @SuppressWarnings({
                "unchecked"
        })
        public FavouriteBotMainModal createFromParcel(Parcel in) {
            return new FavouriteBotMainModal(in);
        }

        public FavouriteBotMainModal[] newArray(int size) {
            return (new FavouriteBotMainModal[size]);
        }

    }
            ;

    protected FavouriteBotMainModal(Parcel in) {
        this.error = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.favoriteBot, (ibt.com.tapizy.model.favourite_bot.FavoriteBot.class.getClassLoader()));
    }

    public FavouriteBotMainModal() {
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

    public List<FavoriteBot> getFavoriteBot() {
        return favoriteBot;
    }

    public void setFavoriteBot(List<FavoriteBot> favoriteBot) {
        this.favoriteBot = favoriteBot;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(error);
        dest.writeValue(message);
        dest.writeList(favoriteBot);
    }

    public int describeContents() {
        return 0;
    }

}
