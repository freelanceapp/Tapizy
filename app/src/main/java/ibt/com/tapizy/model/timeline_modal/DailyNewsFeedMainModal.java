package ibt.com.tapizy.model.timeline_modal;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class DailyNewsFeedMainModal implements Parcelable {

    @SerializedName("error")
    @Expose
    private boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("trending")
    @Expose
    private List<UserFeed> userFeed = new ArrayList<UserFeed>();
    public final static Parcelable.Creator<DailyNewsFeedMainModal> CREATOR = new Creator<DailyNewsFeedMainModal>() {


        @SuppressWarnings({
                "unchecked"
        })
        public DailyNewsFeedMainModal createFromParcel(Parcel in) {
            return new DailyNewsFeedMainModal(in);
        }

        public DailyNewsFeedMainModal[] newArray(int size) {
            return (new DailyNewsFeedMainModal[size]);
        }

    };

    protected DailyNewsFeedMainModal(Parcel in) {
        this.error = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.userFeed, (ibt.com.tapizy.model.timeline_modal.UserFeed.class.getClassLoader()));
    }

    public DailyNewsFeedMainModal() {
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

    public List<UserFeed> getUserFeed() {
        return userFeed;
    }

    public void setUserFeed(List<UserFeed> userFeed) {
        this.userFeed = userFeed;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(error);
        dest.writeValue(message);
        dest.writeList(userFeed);
    }

    public int describeContents() {
        return 0;
    }

}