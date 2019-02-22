package infobite.com.tapizy.model.login_data_modal;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserData implements Parcelable
{

    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("u_name")
    @Expose
    private String uName;
    @SerializedName("u_email")
    @Expose
    private String uEmail;
    @SerializedName("u_password")
    @Expose
    private String uPassword;
    @SerializedName("u_contact")
    @Expose
    private String uContact;
    @SerializedName("u_username")
    @Expose
    private String uUsername;
    @SerializedName("u_gender")
    @Expose
    private String uGender;
    @SerializedName("u_website")
    @Expose
    private String uWebsite;
    @SerializedName("u_bio")
    @Expose
    private String uBio;
    @SerializedName("u_profile")
    @Expose
    private String uProfile;
    @SerializedName("is_bot")
    @Expose
    private String isBot;
    @SerializedName("u_referral_code")
    @Expose
    private String uReferralCode;
    @SerializedName("u_referral_by")
    @Expose
    private String uReferralBy;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("otp")
    @Expose
    private String otp;
    @SerializedName("entry_date")
    @Expose
    private String entryDate;
    @SerializedName("update_entry_date")
    @Expose
    private String updateEntryDate;
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

    }
            ;

    protected UserData(Parcel in) {
        this.uid = ((String) in.readValue((String.class.getClassLoader())));
        this.uName = ((String) in.readValue((String.class.getClassLoader())));
        this.uEmail = ((String) in.readValue((String.class.getClassLoader())));
        this.uPassword = ((String) in.readValue((String.class.getClassLoader())));
        this.uContact = ((String) in.readValue((String.class.getClassLoader())));
        this.uUsername = ((String) in.readValue((String.class.getClassLoader())));
        this.uGender = ((String) in.readValue((String.class.getClassLoader())));
        this.uWebsite = ((String) in.readValue((String.class.getClassLoader())));
        this.uBio = ((String) in.readValue((String.class.getClassLoader())));
        this.uProfile = ((String) in.readValue((String.class.getClassLoader())));
        this.isBot = ((String) in.readValue((String.class.getClassLoader())));
        this.uReferralCode = ((String) in.readValue((String.class.getClassLoader())));
        this.uReferralBy = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.otp = ((String) in.readValue((String.class.getClassLoader())));
        this.entryDate = ((String) in.readValue((String.class.getClassLoader())));
        this.updateEntryDate = ((String) in.readValue((String.class.getClassLoader())));
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

    public String getUPassword() {
        return uPassword;
    }

    public void setUPassword(String uPassword) {
        this.uPassword = uPassword;
    }

    public String getUContact() {
        return uContact;
    }

    public void setUContact(String uContact) {
        this.uContact = uContact;
    }

    public String getUUsername() {
        return uUsername;
    }

    public void setUUsername(String uUsername) {
        this.uUsername = uUsername;
    }

    public String getUGender() {
        return uGender;
    }

    public void setUGender(String uGender) {
        this.uGender = uGender;
    }

    public String getUWebsite() {
        return uWebsite;
    }

    public void setUWebsite(String uWebsite) {
        this.uWebsite = uWebsite;
    }

    public String getUBio() {
        return uBio;
    }

    public void setUBio(String uBio) {
        this.uBio = uBio;
    }

    public String getUProfile() {
        return uProfile;
    }

    public void setUProfile(String uProfile) {
        this.uProfile = uProfile;
    }

    public String getIsBot() {
        return isBot;
    }

    public void setIsBot(String isBot) {
        this.isBot = isBot;
    }

    public String getUReferralCode() {
        return uReferralCode;
    }

    public void setUReferralCode(String uReferralCode) {
        this.uReferralCode = uReferralCode;
    }

    public String getUReferralBy() {
        return uReferralBy;
    }

    public void setUReferralBy(String uReferralBy) {
        this.uReferralBy = uReferralBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getUpdateEntryDate() {
        return updateEntryDate;
    }

    public void setUpdateEntryDate(String updateEntryDate) {
        this.updateEntryDate = updateEntryDate;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(uid);
        dest.writeValue(uName);
        dest.writeValue(uEmail);
        dest.writeValue(uPassword);
        dest.writeValue(uContact);
        dest.writeValue(uUsername);
        dest.writeValue(uGender);
        dest.writeValue(uWebsite);
        dest.writeValue(uBio);
        dest.writeValue(uProfile);
        dest.writeValue(isBot);
        dest.writeValue(uReferralCode);
        dest.writeValue(uReferralBy);
        dest.writeValue(status);
        dest.writeValue(otp);
        dest.writeValue(entryDate);
        dest.writeValue(updateEntryDate);
    }

    public int describeContents() {
        return 0;
    }

}