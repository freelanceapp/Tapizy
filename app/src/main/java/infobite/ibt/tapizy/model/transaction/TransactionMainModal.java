package infobite.ibt.tapizy.model.transaction;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionMainModal implements Parcelable {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("coins")
    @Expose
    private List<TransactionCoinList> coins = new ArrayList<TransactionCoinList>();
    public final static Parcelable.Creator<TransactionMainModal> CREATOR = new Creator<TransactionMainModal>() {


        @SuppressWarnings({
                "unchecked"
        })
        public TransactionMainModal createFromParcel(Parcel in) {
            return new TransactionMainModal(in);
        }

        public TransactionMainModal[] newArray(int size) {
            return (new TransactionMainModal[size]);
        }

    };

    protected TransactionMainModal(Parcel in) {
        this.error = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.coins, (TransactionCoinList.class.getClassLoader()));
    }

    public TransactionMainModal() {
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

    public List<TransactionCoinList> getCoins() {
        return coins;
    }

    public void setCoins(List<TransactionCoinList> coins) {
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