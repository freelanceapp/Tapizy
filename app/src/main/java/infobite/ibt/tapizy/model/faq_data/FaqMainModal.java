package infobite.ibt.tapizy.model.faq_data;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FaqMainModal implements Parcelable {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("query")
    @Expose
    private List<FaqList> query = new ArrayList<FaqList>();
    public final static Parcelable.Creator<FaqMainModal> CREATOR = new Creator<FaqMainModal>() {


        @SuppressWarnings({
                "unchecked"
        })
        public FaqMainModal createFromParcel(Parcel in) {
            return new FaqMainModal(in);
        }

        public FaqMainModal[] newArray(int size) {
            return (new FaqMainModal[size]);
        }

    };

    protected FaqMainModal(Parcel in) {
        this.error = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.query, (FaqList.class.getClassLoader()));
    }

    public FaqMainModal() {
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

    public List<FaqList> getQuery() {
        return query;
    }

    public void setQuery(List<FaqList> query) {
        this.query = query;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(error);
        dest.writeValue(message);
        dest.writeList(query);
    }

    public int describeContents() {
        return 0;
    }

}