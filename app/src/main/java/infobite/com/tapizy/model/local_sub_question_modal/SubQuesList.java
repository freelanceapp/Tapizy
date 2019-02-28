package infobite.com.tapizy.model.local_sub_question_modal;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubQuesList implements Parcelable {

    @SerializedName("text")
    @Expose
    private String subText;

    public final static Creator<SubQuesList> CREATOR = new Creator<SubQuesList>() {


        @SuppressWarnings({
                "unchecked"
        })
        public SubQuesList createFromParcel(Parcel in) {
            return new SubQuesList(in);
        }

        public SubQuesList[] newArray(int size) {
            return (new SubQuesList[size]);
        }

    };

    protected SubQuesList(Parcel in) {
        this.subText = ((String) in.readValue((String.class.getClassLoader())));
    }

    public SubQuesList() {
    }

    public String getSubText() {
        return subText;
    }

    public void setSubText(String subConId) {
        this.subText = subConId;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(subText);
    }

    public int describeContents() {
        return 0;
    }

}
