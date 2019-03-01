
package ibt.com.tapizy.model.city_list_modal;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityList implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("name_postcode")
    @Expose
    private String namePostcode;
    public final static Creator<CityList> CREATOR = new Creator<CityList>() {


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
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.namePostcode = ((String) in.readValue((String.class.getClassLoader())));
    }

    public CityList() {
    }

    public String getName() {
        return name;
    }

    public String getNamePostcode() {
        return namePostcode;
    }

    public void setNamePostcode(String namePostcode) {
        this.namePostcode = namePostcode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CityList withName(String name) {
        this.name = name;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(namePostcode);
    }

    public int describeContents() {
        return  0;
    }

}
