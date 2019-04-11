package ibt.com.tapizy.model.bot_profile_data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BotDetailMainModal implements Parcelable {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("bot")
    @Expose
    private BotDetail bot;
    public final static Parcelable.Creator<BotDetailMainModal> CREATOR = new Creator<BotDetailMainModal>() {

        @SuppressWarnings({
                "unchecked"
        })
        public BotDetailMainModal createFromParcel(Parcel in) {
            return new BotDetailMainModal(in);
        }

        public BotDetailMainModal[] newArray(int size) {
            return (new BotDetailMainModal[size]);
        }
    };

    protected BotDetailMainModal(Parcel in) {
        this.error = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.bot = ((BotDetail) in.readValue((BotDetail.class.getClassLoader())));
    }

    public BotDetailMainModal() {

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

    public BotDetail getBot() {
        return bot;
    }

    public void setBot(BotDetail bot) {
        this.bot = bot;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(error);
        dest.writeValue(message);
        dest.writeValue(bot);
    }

    public int describeContents() {
        return 0;
    }

}
