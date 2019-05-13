package infobite.ibt.tapizy.model.social_link;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SocialLinkMainModal implements Parcelable {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("social_link")
    @Expose
    private List<SocialLinkList> socialLink = new ArrayList<SocialLinkList>();
    public final static Parcelable.Creator<SocialLinkMainModal> CREATOR = new Creator<SocialLinkMainModal>() {


        @SuppressWarnings({
                "unchecked"
        })
        public SocialLinkMainModal createFromParcel(Parcel in) {
            return new SocialLinkMainModal(in);
        }

        public SocialLinkMainModal[] newArray(int size) {
            return (new SocialLinkMainModal[size]);
        }

    };

    protected SocialLinkMainModal(Parcel in) {
        this.error = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.socialLink, (SocialLinkList.class.getClassLoader()));
    }

    public SocialLinkMainModal() {
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

    public List<SocialLinkList> getSocialLink() {
        return socialLink;
    }

    public void setSocialLink(List<SocialLinkList> socialLink) {
        this.socialLink = socialLink;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(error);
        dest.writeValue(message);
        dest.writeList(socialLink);
    }

    public int describeContents() {
        return 0;
    }

}
