package infobite.com.tapizy.model.conversation_modal;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConversationMainModal implements Parcelable
{

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("conversation_list")
    @Expose
    private List<ConversationList> conversationList = new ArrayList<ConversationList>();
    public final static Parcelable.Creator<ConversationMainModal> CREATOR = new Creator<ConversationMainModal>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ConversationMainModal createFromParcel(Parcel in) {
            return new ConversationMainModal(in);
        }

        public ConversationMainModal[] newArray(int size) {
            return (new ConversationMainModal[size]);
        }

    }
            ;

    protected ConversationMainModal(Parcel in) {
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.conversationList, (infobite.com.tapizy.model.conversation_modal.ConversationList.class.getClassLoader()));
    }

    public ConversationMainModal() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ConversationList> getConversationList() {
        return conversationList;
    }

    public void setConversationList(List<ConversationList> conversationList) {
        this.conversationList = conversationList;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(message);
        dest.writeList(conversationList);
    }

    public int describeContents() {
        return 0;
    }

}
