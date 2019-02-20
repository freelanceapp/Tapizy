package infobite.com.tapizy.model.database_modal;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatbotList implements Parcelable {

    @SerializedName("name")
    @Expose
    private String name;
    public final static Parcelable.Creator<ChatbotList> CREATOR = new Creator<ChatbotList>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ChatbotList createFromParcel(Parcel in) {
            return new ChatbotList(in);
        }

        public ChatbotList[] newArray(int size) {
            return (new ChatbotList[size]);
        }

    };

    protected ChatbotList(Parcel in) {
        this.name = ((String) in.readValue((String.class.getClassLoader())));
    }

    public ChatbotList() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(name);
    }

    public int describeContents() {
        return 0;
    }

}