package ibt.com.tapizy.model.faq_data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FaqList implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("query")
    @Expose
    private String query;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("admin_comment")
    @Expose
    private String adminComment;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("entry_date")
    @Expose
    private String entryDate;
    @SerializedName("update_date")
    @Expose
    private String updateDate;
    public final static Parcelable.Creator<FaqList> CREATOR = new Creator<FaqList>() {


        @SuppressWarnings({
                "unchecked"
        })
        public FaqList createFromParcel(Parcel in) {
            return new FaqList(in);
        }

        public FaqList[] newArray(int size) {
            return (new FaqList[size]);
        }

    };

    protected FaqList(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.userId = ((String) in.readValue((String.class.getClassLoader())));
        this.question = ((String) in.readValue((String.class.getClassLoader())));
        this.query = ((String) in.readValue((String.class.getClassLoader())));
        this.comment = ((String) in.readValue((String.class.getClassLoader())));
        this.adminComment = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.entryDate = ((String) in.readValue((String.class.getClassLoader())));
        this.updateDate = ((String) in.readValue((String.class.getClassLoader())));
    }

    public FaqList() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAdminComment() {
        return adminComment;
    }

    public void setAdminComment(String adminComment) {
        this.adminComment = adminComment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(userId);
        dest.writeValue(question);
        dest.writeValue(query);
        dest.writeValue(comment);
        dest.writeValue(adminComment);
        dest.writeValue(status);
        dest.writeValue(entryDate);
        dest.writeValue(updateDate);
    }

    public int describeContents() {
        return 0;
    }

}