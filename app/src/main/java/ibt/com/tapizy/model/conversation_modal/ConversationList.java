package ibt.com.tapizy.model.conversation_modal;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConversationList implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("relate_id")
    @Expose
    private String relateId;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("text")
    @Expose
    private String text;
    public final static Parcelable.Creator<ConversationList> CREATOR = new Creator<ConversationList>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ConversationList createFromParcel(Parcel in) {
            return new ConversationList(in);
        }

        public ConversationList[] newArray(int size) {
            return (new ConversationList[size]);
        }

    };

    protected ConversationList(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.relateId = ((String) in.readValue((String.class.getClassLoader())));
        this.type = ((String) in.readValue((String.class.getClassLoader())));
        this.text = ((String) in.readValue((String.class.getClassLoader())));
    }

    public ConversationList(String relateId, String type, String text) {
        this.relateId = relateId;
        this.type = type;
        this.text = text;
    }

    public ConversationList(Integer id, String relateId, String text, String type) {
        this.id = id;
        this.relateId = relateId;
        this.type = type;
        this.text = text;
    }

    public ConversationList() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRelateId() {
        return relateId;
    }

    public void setRelateId(String relateId) {
        this.relateId = relateId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        dest.writeValue(type);
    }

    public int describeContents() {
        return 0;
    }

}