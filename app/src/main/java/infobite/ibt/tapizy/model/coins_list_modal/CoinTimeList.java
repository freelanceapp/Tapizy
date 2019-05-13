package infobite.ibt.tapizy.model.coins_list_modal;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CoinTimeList implements Parcelable {

    @SerializedName("coins")
    @Expose
    private String coins;
    @SerializedName("time")
    @Expose
    private String time;
    public final static Parcelable.Creator<CoinTimeList> CREATOR = new Creator<CoinTimeList>() {


        @SuppressWarnings({
                "unchecked"
        })
        public CoinTimeList createFromParcel(Parcel in) {
            return new CoinTimeList(in);
        }

        public CoinTimeList[] newArray(int size) {
            return (new CoinTimeList[size]);
        }

    };

    protected CoinTimeList(Parcel in) {
        this.coins = ((String) in.readValue((String.class.getClassLoader())));
        this.time = ((String) in.readValue((String.class.getClassLoader())));
    }

    public CoinTimeList() {
    }

    public String getCoins() {
        return coins;
    }

    public void setCoins(String coins) {
        this.coins = coins;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(coins);
        dest.writeValue(time);
    }

    public int describeContents() {
        return 0;
    }

}