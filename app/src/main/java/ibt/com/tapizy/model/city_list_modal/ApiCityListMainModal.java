
package ibt.com.tapizy.model.city_list_modal;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiCityListMainModal implements Parcelable
{

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("city_list")
    @Expose
    private List<CityList> cityList = new ArrayList<CityList>();
    public final static Parcelable.Creator<ApiCityListMainModal> CREATOR = new Creator<ApiCityListMainModal>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ApiCityListMainModal createFromParcel(Parcel in) {
            return new ApiCityListMainModal(in);
        }

        public ApiCityListMainModal[] newArray(int size) {
            return (new ApiCityListMainModal[size]);
        }

    }
            ;

    protected ApiCityListMainModal(Parcel in) {
        this.error = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.cityList, (CityList.class.getClassLoader()));
    }

    public ApiCityListMainModal() {
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

    public List<CityList> getCityList() {
        return cityList;
    }

    public void setCityList(List<CityList> cityList) {
        this.cityList = cityList;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(error);
        dest.writeValue(message);
        dest.writeList(cityList);
    }

    public int describeContents() {
        return 0;
    }

}