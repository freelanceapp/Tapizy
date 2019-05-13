package infobite.ibt.tapizy.model.app_content;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppContentList implements Parcelable {

    @SerializedName("content_type")
    @Expose
    private String contentType;
    @SerializedName("content")
    @Expose
    private String content;
    public final static Parcelable.Creator<AppContentList> CREATOR = new Creator<AppContentList>() {


        @SuppressWarnings({
                "unchecked"
        })
        public AppContentList createFromParcel(Parcel in) {
            return new AppContentList(in);
        }

        public AppContentList[] newArray(int size) {
            return (new AppContentList[size]);
        }

    };

    protected AppContentList(Parcel in) {
        this.contentType = ((String) in.readValue((String.class.getClassLoader())));
        this.content = ((String) in.readValue((String.class.getClassLoader())));
    }

    public AppContentList() {
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(contentType);
        dest.writeValue(content);
    }

    public int describeContents() {
        return 0;
    }

}
