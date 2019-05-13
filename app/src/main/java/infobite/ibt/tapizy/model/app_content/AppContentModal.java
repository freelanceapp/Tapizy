package infobite.ibt.tapizy.model.app_content;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppContentModal implements Parcelable {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("content")
    @Expose
    private List<AppContentList> content = new ArrayList<AppContentList>();
    public final static Parcelable.Creator<AppContentModal> CREATOR = new Creator<AppContentModal>() {


        @SuppressWarnings({
                "unchecked"
        })
        public AppContentModal createFromParcel(Parcel in) {
            return new AppContentModal(in);
        }

        public AppContentModal[] newArray(int size) {
            return (new AppContentModal[size]);
        }

    };

    protected AppContentModal(Parcel in) {
        this.error = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.content, (AppContentList.class.getClassLoader()));
    }

    public AppContentModal() {
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

    public List<AppContentList> getContent() {
        return content;
    }

    public void setContent(List<AppContentList> content) {
        this.content = content;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(error);
        dest.writeValue(message);
        dest.writeList(content);
    }

    public int describeContents() {
        return 0;
    }

}