package ibt.com.tapizy.model.navigation_item_modal;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NavItemList implements Parcelable {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("image")
    @Expose
    private Integer image;

    public final static Creator<NavItemList> CREATOR = new Creator<NavItemList>() {

        @SuppressWarnings({
                "unchecked"
        })
        public NavItemList createFromParcel(Parcel in) {
            return new NavItemList(in);
        }

        public NavItemList[] newArray(int size) {
            return (new NavItemList[size]);
        }

    };

    protected NavItemList(Parcel in) {
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.image = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public NavItemList() {

    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(name);
        dest.writeValue(image);
    }

    public int describeContents() {
        return 0;
    }

}