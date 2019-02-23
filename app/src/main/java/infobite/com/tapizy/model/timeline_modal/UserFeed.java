package infobite.com.tapizy.model.timeline_modal;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserFeed implements Serializable, Parcelable {

    @SerializedName("post_id")
    @Expose
    private String postId;
    @SerializedName("uid")
    @Expose
    private String postUserId;
    @SerializedName("u_name")
    @Expose
    private String postUserName;
    @SerializedName("u_profile")
    @Expose
    private String postUserImage;
    @SerializedName("post_description")
    @Expose
    private String athleteStatus;
    @SerializedName("video")
    @Expose
    private String athleteVideo;
    @SerializedName("video_thumbnail")
    @Expose
    private String videoThumbnail;
    @SerializedName("athlete_artice_url")
    @Expose
    private String athleteArticeUrl;
    @SerializedName("headline")
    @Expose
    private String athleteArticeHeadline;
    @SerializedName("image")
    @Expose
    private String alhleteImages;
    @SerializedName("entry_date")
    @Expose
    private String entryDate;
    @SerializedName("comment")
    @Expose
    private List<Comment> comment = new ArrayList<Comment>();
    @SerializedName("total_like")
    @Expose
    private String likes;
    @SerializedName("total_unlike")
    @Expose
    private String totalUnlike;
    @SerializedName("is_like")
    @Expose
    private String isLike;
    public final static Creator<UserFeed> CREATOR = new Creator<UserFeed>() {


        @SuppressWarnings({
                "unchecked"
        })
        public UserFeed createFromParcel(Parcel in) {
            return new UserFeed(in);
        }

        public UserFeed[] newArray(int size) {
            return (new UserFeed[size]);
        }

    };
    private final static long serialVersionUID = 5278743063907932546L;

    protected UserFeed(Parcel in) {
        this.postId = ((String) in.readValue((String.class.getClassLoader())));
        this.postUserId = ((String) in.readValue((String.class.getClassLoader())));
        this.postUserName = ((String) in.readValue((String.class.getClassLoader())));
        this.postUserImage = ((String) in.readValue((String.class.getClassLoader())));
        this.athleteStatus = ((String) in.readValue((String.class.getClassLoader())));
        this.videoThumbnail = ((String) in.readValue((String.class.getClassLoader())));
        this.athleteVideo = ((String) in.readValue((String.class.getClassLoader())));
        this.athleteArticeUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.athleteArticeHeadline = ((String) in.readValue((String.class.getClassLoader())));
        this.alhleteImages = ((String) in.readValue((String.class.getClassLoader())));
        this.entryDate = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.comment, (Comment.class.getClassLoader()));
        this.likes = ((String) in.readValue((String.class.getClassLoader())));
        this.totalUnlike = ((String) in.readValue((String.class.getClassLoader())));
        this.isLike = ((String) in.readValue((String.class.getClassLoader())));
    }

    public UserFeed() {

    }

    public String getFeedId() {
        return postId;
    }

    public void setFeedId(String postId) {
        this.postId = postId;
    }

    public String getPostUserName() {
        return postUserName;
    }

    public void setPostUserName(String postUserName) {
        this.postUserName = postUserName;
    }

    public String getPostUserImage() {
        return postUserImage;
    }

    public void setPostUserImage(String postUserImage) {
        this.postUserImage = postUserImage;
    }

    public String getAthleteStatus() {
        return athleteStatus;
    }

    public void setAthleteStatus(String athleteStatus) {
        this.athleteStatus = athleteStatus;
    }

    public String getVideoThumbnail() {
        return videoThumbnail;
    }

    public void setVideoThumbnail(String videoThumbnail) {
        this.videoThumbnail = videoThumbnail;
    }

    public String getAthleteVideo() {
        return athleteVideo;
    }

    public void setAthleteVideo(String athleteVideo) {
        this.athleteVideo = athleteVideo;
    }

    public String getAthleteArticeUrl() {
        return athleteArticeUrl;
    }

    public void setAthleteArticeUrl(String athleteArticeUrl) {
        this.athleteArticeUrl = athleteArticeUrl;
    }

    public String getAthleteArticeHeadline() {
        return athleteArticeHeadline;
    }

    public void setAthleteArticeHeadline(String athleteArticeHeadline) {
        this.athleteArticeHeadline = athleteArticeHeadline;
    }

    public String getAlhleteImages() {
        return alhleteImages;
    }

    public void setAlhleteImages(String alhleteImages) {
        this.alhleteImages = alhleteImages;
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

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
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

    public String getPostUserId() {
        return postUserId;
    }

    public void setPostUserId(String postUserId) {
        this.postUserId = postUserId;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(postId);
        dest.writeValue(athleteStatus);
        dest.writeValue(postUserId);
        dest.writeValue(postUserName);
        dest.writeValue(postUserImage);
        dest.writeValue(videoThumbnail);
        dest.writeValue(athleteVideo);
        dest.writeValue(athleteArticeUrl);
        dest.writeValue(athleteArticeHeadline);
        dest.writeValue(alhleteImages);
        dest.writeValue(entryDate);
        dest.writeList(comment);
        dest.writeValue(likes);
        dest.writeValue(totalUnlike);
        dest.writeValue(isLike);
    }

    public int describeContents() {
        return 0;
    }

}
