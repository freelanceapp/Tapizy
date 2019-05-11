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
    @SerializedName("current_page")
    @Expose
    private String currentPage;
    @SerializedName("page_count")
    @Expose
    private Integer pageCount = 0;
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
        this.currentPage = ((String) in.readValue((String.class.getClassLoader())));
        this.pageCount = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.userFeed, (UserFeed.class.getClassLoader()));
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

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(error);
        dest.writeValue(message);
        dest.writeValue(currentPage);
        dest.writeValue(pageCount);
        dest.writeList(userFeed);
    }

    public int describeContents() {
        return 0;
    }

}