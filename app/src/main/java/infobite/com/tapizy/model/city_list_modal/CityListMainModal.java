
package infobite.com.tapizy.model.city_list_modal;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CityListMainModal implements Parcelable
{

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("user")
    @Expose
    private List<CityList> user = new ArrayList<CityList>();
    public final static Creator<CityListMainModal> CREATOR = new Creator<CityListMainModal>() {


        @SuppressWarnings({
            "unchecked"
        })
        public CityListMainModal createFromParcel(Parcel in) {
            return new CityListMainModal(in);
        }

        public CityListMainModal[] newArray(int size) {
            return (new CityListMainModal[size]);
        }

    }
    ;

    protected CityListMainModal(Parcel in) {
        this.error = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.user, (CityList.class.getClassLoader()));
    }

    public CityListMainModal() {
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public CityListMainModal withError(Boolean error) {
        this.error = error;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CityListMainModal withMessage(String message) {
        this.message = message;
        return this;
    }

    public List<CityList> getUser() {
        return user;
    }

    public void setUser(List<CityList> user) {
        this.user = user;
    }

    public CityListMainModal withUser(List<CityList> user) {
        this.user = user;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(error);
        dest.writeValue(message);
        dest.writeList(user);
    }

    public int describeContents() {
        return  0;
    }

}
