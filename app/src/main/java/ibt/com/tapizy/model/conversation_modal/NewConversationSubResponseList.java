package ibt.com.tapizy.model.conversation_modal;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewConversationSubResponseList implements Parcelable {

    @SerializedName("option_id")
    @Expose
    private String optionId;
    @SerializedName("multichoice_option")
    @Expose
    private String multichoiceOption;
    @SerializedName("prefix")
    @Expose
    private String prefix;
    @SerializedName("suffix")
    @Expose
    private String suffix;
    @SerializedName("minimum")
    @Expose
    private String minimum;
    @SerializedName("maximum")
    @Expose
    private String maximum;
    @SerializedName("link_type")
    @Expose
    private String linkType;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("minimum_digits")
    @Expose
    private String minimumDigits;
    @SerializedName("maximum_digits")
    @Expose
    private String maximumDigits;
    @SerializedName("msg_sequence")
    @Expose
    private String msgSequence;
    public final static Parcelable.Creator<NewConversationSubResponseList> CREATOR = new Creator<NewConversationSubResponseList>() {


        @SuppressWarnings({
                "unchecked"
        })
        public NewConversationSubResponseList createFromParcel(Parcel in) {
            return new NewConversationSubResponseList(in);
        }

        public NewConversationSubResponseList[] newArray(int size) {
            return (new NewConversationSubResponseList[size]);
        }

    };

    protected NewConversationSubResponseList(Parcel in) {
        this.optionId = ((String) in.readValue((String.class.getClassLoader())));
        this.multichoiceOption = ((String) in.readValue((String.class.getClassLoader())));
        this.prefix = ((String) in.readValue((String.class.getClassLoader())));
        this.suffix = ((String) in.readValue((String.class.getClassLoader())));
        this.minimum = ((String) in.readValue((String.class.getClassLoader())));
        this.maximum = ((String) in.readValue((String.class.getClassLoader())));
        this.linkType = ((String) in.readValue((String.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.link = ((String) in.readValue((String.class.getClassLoader())));
        this.minimumDigits = ((String) in.readValue((String.class.getClassLoader())));
        this.maximumDigits = ((String) in.readValue((String.class.getClassLoader())));
        this.msgSequence = ((String) in.readValue((String.class.getClassLoader())));
    }

    public NewConversationSubResponseList() {
    }

    public String getOptionId() {
        return optionId;
    }

    public void setOptionId(String optionId) {
        this.optionId = optionId;
    }

    public String getMultichoiceOption() {
        return multichoiceOption;
    }

    public void setMultichoiceOption(String multichoiceOption) {
        this.multichoiceOption = multichoiceOption;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getMinimum() {
        return minimum;
    }

    public void setMinimum(String minimum) {
        this.minimum = minimum;
    }

    public String getMaximum() {
        return maximum;
    }

    public void setMaximum(String maximum) {
        this.maximum = maximum;
    }

    public String getLinkType() {
        return linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getMinimumDigits() {
        return minimumDigits;
    }

    public void setMinimumDigits(String minimumDigits) {
        this.minimumDigits = minimumDigits;
    }

    public String getMaximumDigits() {
        return maximumDigits;
    }

    public void setMaximumDigits(String maximumDigits) {
        this.maximumDigits = maximumDigits;
    }

    public String getMsgSequence() {
        return msgSequence;
    }

    public void setMsgSequence(String msgSequence) {
        this.msgSequence = msgSequence;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(optionId);
        dest.writeValue(multichoiceOption);
        dest.writeValue(prefix);
        dest.writeValue(suffix);
        dest.writeValue(minimum);
        dest.writeValue(maximum);
        dest.writeValue(linkType);
        dest.writeValue(title);
        dest.writeValue(link);
        dest.writeValue(minimumDigits);
        dest.writeValue(maximumDigits);
        dest.writeValue(msgSequence);
    }

    public int describeContents() {
        return 0;
    }

}
