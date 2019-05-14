package infobite.ibt.tapizy.model.transaction;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionCoinList implements Parcelable {

    @SerializedName("transaction_id")
    @Expose
    private String transactionId;
    @SerializedName("coins")
    @Expose
    private String coins;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("purpose")
    @Expose
    private String purpose;
    @SerializedName("purpose_type")
    @Expose
    private String purposeType;
    @SerializedName("bot_name")
    @Expose
    private String botName;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("payment_status")
    @Expose
    private String paymentStatus;
    @SerializedName("entry_date")
    @Expose
    private String entryDate;
    public final static Parcelable.Creator<TransactionCoinList> CREATOR = new Creator<TransactionCoinList>() {


        @SuppressWarnings({
                "unchecked"
        })
        public TransactionCoinList createFromParcel(Parcel in) {
            return new TransactionCoinList(in);
        }

        public TransactionCoinList[] newArray(int size) {
            return (new TransactionCoinList[size]);
        }

    };

    protected TransactionCoinList(Parcel in) {
        this.transactionId = ((String) in.readValue((String.class.getClassLoader())));
        this.coins = ((String) in.readValue((String.class.getClassLoader())));
        this.userId = ((String) in.readValue((String.class.getClassLoader())));
        this.purpose = ((String) in.readValue((String.class.getClassLoader())));
        this.purposeType = ((String) in.readValue((String.class.getClassLoader())));
        this.botName = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.paymentStatus = ((String) in.readValue((String.class.getClassLoader())));
        this.entryDate = ((String) in.readValue((String.class.getClassLoader())));
    }

    public TransactionCoinList() {
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getCoins() {
        return coins;
    }

    public void setCoins(String coins) {
        this.coins = coins;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getPurposeType() {
        return purposeType;
    }

    public void setPurposeType(String purposeType) {
        this.purposeType = purposeType;
    }

    public String getBotName() {
        return botName;
    }

    public void setBotName(String botName) {
        this.botName = botName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(transactionId);
        dest.writeValue(coins);
        dest.writeValue(userId);
        dest.writeValue(purpose);
        dest.writeValue(purposeType);
        dest.writeValue(botName);
        dest.writeValue(status);
        dest.writeValue(paymentStatus);
        dest.writeValue(entryDate);
    }

    public int describeContents() {
        return 0;
    }

}