package ibt.com.tapizy.model.comment_list_modal;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import ibt.com.tapizy.model.timeline_modal.Comment;

public class CommentMainModal implements Parcelable {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("comment")
    @Expose
    private List<Comment> comment = new ArrayList<Comment>();
    public final static Parcelable.Creator<CommentMainModal> CREATOR = new Creator<CommentMainModal>() {


        @SuppressWarnings({
                "unchecked"
        })
        public CommentMainModal createFromParcel(Parcel in) {
            return new CommentMainModal(in);
        }

        public CommentMainModal[] newArray(int size) {
            return (new CommentMainModal[size]);
        }

    };

    protected CommentMainModal(Parcel in) {
        this.error = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.comment, (Comment.class.getClassLoader()));
    }

    public CommentMainModal() {
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

    public List<Comment> getComment() {
        return comment;
    }

    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(error);
        dest.writeValue(message);
        dest.writeList(comment);
    }

    public int describeContents() {
        return 0;
    }

}
