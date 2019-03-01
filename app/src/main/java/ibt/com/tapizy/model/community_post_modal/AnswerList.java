package ibt.com.tapizy.model.community_post_modal;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AnswerList implements Parcelable
{

    @SerializedName("answer_id")
    @Expose
    private String answerId;
    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("u_profile")
    @Expose
    private String userImage;
    @SerializedName("u_name")
    @Expose
    private String userName;
    @SerializedName("answer")
    @Expose
    private String answer;
    public final static Creator<AnswerList> CREATOR = new Creator<AnswerList>() {


        @SuppressWarnings({
                "unchecked"
        })
        public AnswerList createFromParcel(Parcel in) {
            return new AnswerList(in);
        }

        public AnswerList[] newArray(int size) {
            return (new AnswerList[size]);
        }

    }
            ;

    protected AnswerList(Parcel in) {
        this.answerId = ((String) in.readValue((String.class.getClassLoader())));
        this.uid = ((String) in.readValue((String.class.getClassLoader())));
        this.userImage = ((String) in.readValue((String.class.getClassLoader())));
        this.userName = ((String) in.readValue((String.class.getClassLoader())));
        this.answer = ((String) in.readValue((String.class.getClassLoader())));
    }

    public AnswerList() {
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(answerId);
        dest.writeValue(uid);
        dest.writeValue(userImage);
        dest.writeValue(userName);
        dest.writeValue(answer);
    }

    public int describeContents() {
        return 0;
    }

}