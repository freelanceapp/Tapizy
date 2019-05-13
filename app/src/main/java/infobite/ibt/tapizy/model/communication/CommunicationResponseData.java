package infobite.ibt.tapizy.model.communication;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommunicationResponseData implements Parcelable
{

    @SerializedName("response_option_id")
    @Expose
    private String responseOptionId;
    @SerializedName("response_option_msg")
    @Expose
    private String responseOptionMsg;
    public final static Parcelable.Creator<CommunicationResponseData> CREATOR = new Creator<CommunicationResponseData>() {


        @SuppressWarnings({
                "unchecked"
        })
        public CommunicationResponseData createFromParcel(Parcel in) {
            return new CommunicationResponseData(in);
        }

        public CommunicationResponseData[] newArray(int size) {
            return (new CommunicationResponseData[size]);
        }

    }
            ;

    protected CommunicationResponseData(Parcel in) {
        this.responseOptionId = ((String) in.readValue((String.class.getClassLoader())));
        this.responseOptionMsg = ((String) in.readValue((String.class.getClassLoader())));
    }

    public CommunicationResponseData() {
    }

    public String getResponseOptionId() {
        return responseOptionId;
    }

    public void setResponseOptionId(String responseOptionId) {
        this.responseOptionId = responseOptionId;
    }

    public String getResponseOptionMsg() {
        return responseOptionMsg;
    }

    public void setResponseOptionMsg(String responseOptionMsg) {
        this.responseOptionMsg = responseOptionMsg;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(responseOptionId);
        dest.writeValue(responseOptionMsg);
    }

    public int describeContents() {
        return 0;
    }

}
