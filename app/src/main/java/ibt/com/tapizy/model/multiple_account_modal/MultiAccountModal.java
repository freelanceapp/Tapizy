package ibt.com.tapizy.model.multiple_account_modal;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MultiAccountModal implements Parcelable {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("account_list")
    @Expose
    private List<MultiAccountList> accountList = new ArrayList<MultiAccountList>();
    public final static Creator<MultiAccountModal> CREATOR = new Creator<MultiAccountModal>() {

        @SuppressWarnings({
                "unchecked"
        })
        public MultiAccountModal createFromParcel(Parcel in) {
            return new MultiAccountModal(in);
        }

        public MultiAccountModal[] newArray(int size) {
            return (new MultiAccountModal[size]);
        }
    };

    protected MultiAccountModal(Parcel in) {
        this.error = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.accountList, (MultiAccountList.class.getClassLoader()));
    }

    public MultiAccountModal() {
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

    public List<MultiAccountList> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<MultiAccountList> accountList) {
        this.accountList = accountList;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(error);
        dest.writeValue(message);
        dest.writeList(accountList);
    }

    public int describeContents() {
        return 0;
    }

}
