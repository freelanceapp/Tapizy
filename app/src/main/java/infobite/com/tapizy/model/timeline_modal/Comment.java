package infobite.com.tapizy.model.timeline_modal;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Comment implements Parcelable
{

    @SerializedName("tid")
    @Expose
    private String tid;
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
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("entry_date")
    @Expose
    private String entryDate;
    public final static Parcelable.Creator<Comment> CREATOR = new Creator<Comment>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        public Comment[] newArray(int size) {
            return (new Comment[size]);
        }

    }
            ;

    protected Comment(Parcel in) {
        this.tid = ((String) in.readValue((String.class.getClassLoader())));
        this.postId = ((String) in.readValue((String.class.getClassLoader())));
        this.uid = ((String) in.readValue((String.class.getClassLoader())));
        this.uName = ((String) in.readValue((String.class.getClassLoader())));
        this.uProfile = ((String) in.readValue((String.class.getClassLoader())));
        this.comment = ((String) in.readValue((String.class.getClassLoader())));
        this.entryDate = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Comment() {
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(tid);
        dest.writeValue(postId);
        dest.writeValue(uid);
        dest.writeValue(uName);
        dest.writeValue(uProfile);
        dest.writeValue(comment);
        dest.writeValue(entryDate);
    }

    public int describeContents() {
        return 0;
    }

}