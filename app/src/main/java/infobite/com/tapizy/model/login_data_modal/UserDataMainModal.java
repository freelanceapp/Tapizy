package infobite.com.tapizy.model.login_data_modal;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDataMainModal implements Parcelable
{

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("user")
    @Expose
    private UserData user;
    @SerializedName("user_type")
    @Expose
    private String userType;
    public final static Parcelable.Creator<UserDataMainModal> CREATOR = new Creator<UserDataMainModal>() {


        @SuppressWarnings({
                "unchecked"
        })
        public UserDataMainModal createFromParcel(Parcel in) {
            return new UserDataMainModal(in);
        }

        public UserDataMainModal[] newArray(int size) {
            return (new UserDataMainModal[size]);
        }

    }
            ;

    protected UserDataMainModal(Parcel in) {
        this.error = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.user = ((UserData) in.readValue((UserData.class.getClassLoader())));
        this.userType = ((String) in.readValue((String.class.getClassLoader())));
    }

    public UserDataMainModal() {
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

    public UserData getUser() {
        return user;
    }

    public void setUser(UserData user) {
        this.user = user;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(error);
        dest.writeValue(message);
        dest.writeValue(user);
        dest.writeValue(userType);
    }

    public int describeContents() {
        return 0;
    }

}
