package ibt.com.tapizy.model.api_conversation_modal;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiSubResponseList implements Parcelable
{

    @SerializedName("sub_con_id")
    @Expose
    private String subConId;
    @SerializedName("response_text")
    @Expose
    private String responseText;
    public final static Parcelable.Creator<ApiSubResponseList> CREATOR = new Creator<ApiSubResponseList>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ApiSubResponseList createFromParcel(Parcel in) {
            return new ApiSubResponseList(in);
        }

        public ApiSubResponseList[] newArray(int size) {
            return (new ApiSubResponseList[size]);
        }

    }
            ;

    protected ApiSubResponseList(Parcel in) {
        this.subConId = ((String) in.readValue((String.class.getClassLoader())));
        this.responseText = ((String) in.readValue((String.class.getClassLoader())));
    }

    public ApiSubResponseList() {
    }

    public String getSubConId() {
        return subConId;
    }

    public void setSubConId(String subConId) {
        this.subConId = subConId;
    }

    public String getResponseText() {
        return responseText;
    }

    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(subConId);
        dest.writeValue(responseText);
    }

    public int describeContents() {
        return 0;
    }

}
