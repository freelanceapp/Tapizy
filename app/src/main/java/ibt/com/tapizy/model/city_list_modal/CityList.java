
package ibt.com.tapizy.model.city_list_modal;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityList implements Parcelable
{

    @SerializedName("city_id")
    @Expose
    private String cityId;
    @SerializedName("cityname")
    @Expose
    private String cityname;
    public final static Parcelable.Creator<CityList> CREATOR = new Creator<CityList>() {


        @SuppressWarnings({
                "unchecked"
        })
        public CityList createFromParcel(Parcel in) {
            return new CityList(in);
        }

        public CityList[] newArray(int size) {
            return (new CityList[size]);
        }

    }
            ;

    protected CityList(Parcel in) {
        this.cityId = ((String) in.readValue((String.class.getClassLoader())));
        this.cityname = ((String) in.readValue((String.class.getClassLoader())));
    }

    public CityList() {
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(cityId);
        dest.writeValue(cityname);
    }

    public int describeContents() {
        return 0;
    }

}
