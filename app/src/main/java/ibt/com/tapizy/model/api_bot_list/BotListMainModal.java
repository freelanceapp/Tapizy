package ibt.com.tapizy.model.api_bot_list;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BotListMainModal implements Parcelable
{

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("bot_list")
    @Expose
    private List<BotList> botList = new ArrayList<BotList>();
    public final static Parcelable.Creator<BotListMainModal> CREATOR = new Creator<BotListMainModal>() {


        @SuppressWarnings({
                "unchecked"
        })
        public BotListMainModal createFromParcel(Parcel in) {
            return new BotListMainModal(in);
        }

        public BotListMainModal[] newArray(int size) {
            return (new BotListMainModal[size]);
        }

    }
            ;

    protected BotListMainModal(Parcel in) {
        this.error = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.botList, (ibt.com.tapizy.model.api_bot_list.BotList.class.getClassLoader()));
    }

    public BotListMainModal() {
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

    public List<BotList> getBotList() {
        return botList;
    }

    public void setBotList(List<BotList> botList) {
        this.botList = botList;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(error);
        dest.writeValue(message);
        dest.writeList(botList);
    }

    public int describeContents() {
        return 0;
    }

}
