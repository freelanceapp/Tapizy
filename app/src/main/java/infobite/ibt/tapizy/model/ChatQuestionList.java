package infobite.ibt.tapizy.model;

import android.os.Parcel;

public class ChatQuestionList {

    private String name;
    private String id;

    public ChatQuestionList() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(name);
    }

    public int describeContents() {
        return 0;
    }

}