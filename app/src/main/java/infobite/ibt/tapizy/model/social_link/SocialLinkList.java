package infobite.ibt.tapizy.model.social_link;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SocialLinkList implements Parcelable {

    @SerializedName("web_url")
    @Expose
    private String webUrl;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("coins")
    @Expose
    private String coins;
    @SerializedName("social_icon")
    @Expose
    private String socialIcon;
    public final static Parcelable.Creator<SocialLinkList> CREATOR = new Creator<SocialLinkList>() {


        @SuppressWarnings({
                "unchecked"
        })
        public SocialLinkList createFromParcel(Parcel in) {
            return new SocialLinkList(in);
        }

        public SocialLinkList[] newArray(int size) {
            return (new SocialLinkList[size]);
        }

    };

    protected SocialLinkList(Parcel in) {
        this.webUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.coins = ((String) in.readValue((String.class.getClassLoader())));
        this.socialIcon = ((String) in.readValue((String.class.getClassLoader())));
    }

    public SocialLinkList() {
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoins() {
        return coins;
    }

    public void setCoins(String coins) {
        this.coins = coins;
    }

    public String getSocialIcon() {
        return socialIcon;
    }

    public void setSocialIcon(String socialIcon) {
        this.socialIcon = socialIcon;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(webUrl);
        dest.writeValue(title);
        dest.writeValue(coins);
        dest.writeValue(socialIcon);
    }

    public int describeContents() {
        return 0;
    }

}