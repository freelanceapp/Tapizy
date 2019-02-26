package infobite.com.tapizy.model.api_conversation_modal;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiConversationList implements Parcelable
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
    public final static Parcelable.Creator<ApiConversationList> CREATOR = new Creator<ApiConversationList>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ApiConversationList createFromParcel(Parcel in) {
            return new ApiConversationList(in);
        }

        public ApiConversationList[] newArray(int size) {
            return (new ApiConversationList[size]);
        }

    }
            ;

    protected ApiConversationList(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.relateId = ((String) in.readValue((String.class.getClassLoader())));
        this.text = ((String) in.readValue((String.class.getClassLoader())));
    }

    public ApiConversationList() {
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

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(relateId);
        dest.writeValue(text);
    }

    public int describeContents() {
        return 0;
    }

}
