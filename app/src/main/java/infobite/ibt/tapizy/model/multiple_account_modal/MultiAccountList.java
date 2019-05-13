package infobite.ibt.tapizy.model.multiple_account_modal;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MultiAccountList implements Parcelable {

    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("avtar")
    @Expose
    private String avtar;
    public final static Creator<MultiAccountList> CREATOR = new Creator<MultiAccountList>() {
        
        @SuppressWarnings({
                "unchecked"
        })
        public MultiAccountList createFromParcel(Parcel in) {
            return new MultiAccountList(in);
        }

        public MultiAccountList[] newArray(int size) {
            return (new MultiAccountList[size]);
        }

    };

    protected MultiAccountList(Parcel in) {
        this.userName = ((String) in.readValue((String.class.getClassLoader())));
        this.uid = ((String) in.readValue((String.class.getClassLoader())));
        this.avtar = ((String) in.readValue((String.class.getClassLoader())));
    }

    public MultiAccountList() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAvtar() {
        return avtar;
    }

    public void setAvtar(String avtar) {
        this.avtar = avtar;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(userName);
        dest.writeValue(uid);
        dest.writeValue(avtar);
    }

    public int describeContents() {
        return 0;
    }

}
