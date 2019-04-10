package ibt.com.tapizy.model.login_data_modal;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserData implements Parcelable {

    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("u_name")
    @Expose
    private String uName;
    @SerializedName("u_email")
    @Expose
    private String uEmail;
    @SerializedName("u_contact")
    @Expose
    private String uContact;
    @SerializedName("u_gender")
    @Expose
    private String uGender;
    @SerializedName("u_profile")
    @Expose
    private String uProfile;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("u_referral_code")
    @Expose
    private String uReferralCode;
    public final static Parcelable.Creator<UserData> CREATOR = new Creator<UserData>() {


        @SuppressWarnings({
                "unchecked"
        })
        public UserData createFromParcel(Parcel in) {
            return new UserData(in);
        }

        public UserData[] newArray(int size) {
            return (new UserData[size]);
        }

    };

    protected UserData(Parcel in) {
        this.uid = ((String) in.readValue((String.class.getClassLoader())));
        this.uName = ((String) in.readValue((String.class.getClassLoader())));
        this.uEmail = ((String) in.readValue((String.class.getClassLoader())));
        this.uContact = ((String) in.readValue((String.class.getClassLoader())));
        this.uGender = ((String) in.readValue((String.class.getClassLoader())));
        this.dob = ((String) in.readValue((String.class.getClassLoader())));
        this.city = ((String) in.readValue((String.class.getClassLoader())));
        this.state = ((String) in.readValue((String.class.getClassLoader())));
        this.country = ((String) in.readValue((String.class.getClassLoader())));
        this.uProfile = ((String) in.readValue((String.class.getClassLoader())));
        this.uReferralCode = ((String) in.readValue((String.class.getClassLoader())));
    }

    public UserData() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUName() {
        return uName;
    }

    public void setUName(String uName) {
        this.uName = uName;
    }

    public String getUEmail() {
        return uEmail;
    }

    public void setUEmail(String uEmail) {
        this.uEmail = uEmail;
    }

    public String getUContact() {
        return uContact;
    }

    public void setuContact(String uContact) {
        this.uContact = uContact;
    }

    public String getUGender() {
        return uGender;
    }

    public void setuGender(String uGender) {
        this.uGender = uGender;
    }

    public String getUProfile() {
        return uProfile;
    }

    public void setUProfile(String uProfile) {
        this.uProfile = uProfile;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getuReferralCode() {
        return uReferralCode;
    }

    public void setuReferralCode(String uReferralCode) {
        this.uReferralCode = uReferralCode;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(uid);
        dest.writeValue(uName);
        dest.writeValue(uEmail);
        dest.writeValue(uContact);
        dest.writeValue(uGender);
        dest.writeValue(dob);
        dest.writeValue(city);
        dest.writeValue(state);
        dest.writeValue(country);
        dest.writeValue(uProfile);
        dest.writeValue(uReferralCode);
    }

    public int describeContents() {
        return 0;
    }

}