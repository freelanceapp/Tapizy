package infobite.ibt.tapizy.model.timeline_modal;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserFeed implements Parcelable
{

    @SerializedName("post_id")
    @Expose
    private String postId;
    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("u_name")
    @Expose
    private String uName;
    @SerializedName("u_profile")
    @Expose
    private String uProfile;
    @SerializedName("headline")
    @Expose
    private String headline;
    @SerializedName("video")
    @Expose
    private String video;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("post_description")
    @Expose
    private String postDescription;
    @SerializedName("total_like")
    @Expose
    private String totalLike;
    @SerializedName("total_unlike")
    @Expose
    private String totalUnlike;
    @SerializedName("is_like")
    @Expose
    private String isLike;
    @SerializedName("entry_date")
    @Expose
    private String entryDate;
    @SerializedName("comment")
    @Expose
    private List<Comment> comment = new ArrayList<Comment>();
    public final static Parcelable.Creator<UserFeed> CREATOR = new Creator<UserFeed>() {


        @SuppressWarnings({
                "unchecked"
        })
        public UserFeed createFromParcel(Parcel in) {
            return new UserFeed(in);
        }

        public UserFeed[] newArray(int size) {
            return (new UserFeed[size]);
        }

    }
            ;

    protected UserFeed(Parcel in) {
        this.postId = ((String) in.readValue((String.class.getClassLoader())));
        this.uid = ((String) in.readValue((String.class.getClassLoader())));
        this.uName = ((String) in.readValue((String.class.getClassLoader())));
        this.uProfile = ((String) in.readValue((String.class.getClassLoader())));
        this.headline = ((String) in.readValue((String.class.getClassLoader())));
        this.video = ((String) in.readValue((String.class.getClassLoader())));
        this.image = ((String) in.readValue((String.class.getClassLoader())));
        this.postDescription = ((String) in.readValue((String.class.getClassLoader())));
        this.totalLike = ((String) in.readValue((String.class.getClassLoader())));
        this.totalUnlike = ((String) in.readValue((String.class.getClassLoader())));
        this.isLike = ((String) in.readValue((String.class.getClassLoader())));
        this.entryDate = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.comment, (infobite.ibt.tapizy.model.timeline_modal.Comment.class.getClassLoader()));
    }

    public UserFeed() {
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
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

    public String getUProfile() {
        return uProfile;
    }

    public void setUProfile(String uProfile) {
        this.uProfile = uProfile;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }

    public String getTotalLike() {
        return totalLike;
    }

    public void setTotalLike(String totalLike) {
        this.totalLike = totalLike;
    }

    public String getTotalUnlike() {
        return totalUnlike;
    }

    public void setTotalUnlike(String totalUnlike) {
        this.totalUnlike = totalUnlike;
    }

    public String getIsLike() {
        return isLike;
    }

    public void setIsLike(String isLike) {
        this.isLike = isLike;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public List<Comment> getComment() {
        return comment;
    }

    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(postId);
        dest.writeValue(uid);
        dest.writeValue(uName);
        dest.writeValue(uProfile);
        dest.writeValue(headline);
        dest.writeValue(video);
        dest.writeValue(image);
        dest.writeValue(postDescription);
        dest.writeValue(totalLike);
        dest.writeValue(totalUnlike);
        dest.writeValue(isLike);
        dest.writeValue(entryDate);
        dest.writeList(comment);
    }

    public int describeContents() {
        return 0;
    }

}
