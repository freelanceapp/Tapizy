package infobite.com.tapizy.model.communication;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConversationData implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("relate_id")
    @Expose
    private String relateId;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("response_related_id")
    @Expose
    private String responseRelatedId;
    @SerializedName("response_data")
    @Expose
    private List<CommunicationResponseData> responseData = new ArrayList<CommunicationResponseData>();
    public final static Parcelable.Creator<ConversationData> CREATOR = new Creator<ConversationData>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ConversationData createFromParcel(Parcel in) {
            return new ConversationData(in);
        }

        public ConversationData[] newArray(int size) {
            return (new ConversationData[size]);
        }

    }
            ;

    protected ConversationData(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.relateId = ((String) in.readValue((String.class.getClassLoader())));
        this.text = ((String) in.readValue((String.class.getClassLoader())));
        this.type = ((String) in.readValue((String.class.getClassLoader())));
        this.responseRelatedId = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.responseData, (CommunicationResponseData.class.getClassLoader()));
    }

    public ConversationData() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRelateId() {
        return relateId;
    }

    public void setRelateId(String relateId) {
        this.relateId = relateId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResponseRelatedId() {
        return responseRelatedId;
    }

    public void setResponseRelatedId(String responseRelatedId) {
        this.responseRelatedId = responseRelatedId;
    }

    public List<CommunicationResponseData> getResponseData() {
        return responseData;
    }

    public void setResponseData(List<CommunicationResponseData> responseData) {
        this.responseData = responseData;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(relateId);
        dest.writeValue(text);
        dest.writeValue(type);
        dest.writeValue(responseRelatedId);
        dest.writeList(responseData);
    }

    public int describeContents() {
        return 0;
    }

}