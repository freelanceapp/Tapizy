package ibt.com.tapizy.model.coins_list_modal;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CoinsTimeModal implements Parcelable {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("coins")
    @Expose
    private List<CoinTimeList> coins = new ArrayList<CoinTimeList>();
    public final static Parcelable.Creator<CoinsTimeModal> CREATOR = new Creator<CoinsTimeModal>() {


        @SuppressWarnings({
                "unchecked"
        })
        public CoinsTimeModal createFromParcel(Parcel in) {
            return new CoinsTimeModal(in);
        }

        public CoinsTimeModal[] newArray(int size) {
            return (new CoinsTimeModal[size]);
        }

    };

    protected CoinsTimeModal(Parcel in) {
        this.error = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.coins, (CoinTimeList.class.getClassLoader()));
    }

    public CoinsTimeModal() {
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

    public List<CoinTimeList> getCoins() {
        return coins;
    }

    public void setCoins(List<CoinTimeList> coins) {
        this.coins = coins;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(error);
        dest.writeValue(message);
        dest.writeList(coins);
    }

    public int describeContents() {
        return 0;
    }

}
