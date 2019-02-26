package infobite.com.tapizy.model.api_conversation_modal;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiConversationMainModal implements Parcelable
{

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("conversation")
    @Expose
    private List<ApiConversationList> conversation = new ArrayList<ApiConversationList>();
    public final static Parcelable.Creator<ApiConversationMainModal> CREATOR = new Creator<ApiConversationMainModal>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ApiConversationMainModal createFromParcel(Parcel in) {
            return new ApiConversationMainModal(in);
        }

        public ApiConversationMainModal[] newArray(int size) {
            return (new ApiConversationMainModal[size]);
        }

    }
            ;

    protected ApiConversationMainModal(Parcel in) {
        this.error = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.conversation, (ApiConversationList.class.getClassLoader()));
    }

    public ApiConversationMainModal() {
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

    public List<ApiConversationList> getConversation() {
        return conversation;
    }

    public void setConversation(List<ApiConversationList> conversation) {
        this.conversation = conversation;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(error);
        dest.writeValue(message);
        dest.writeList(conversation);
    }

    public int describeContents() {
        return 0;
    }

}