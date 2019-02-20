package infobite.com.tapizy.model.database_modal;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductDetail implements Parcelable {

    private int keyId;
    private String relateId;
    private String chatBotId;
    private String type;
    private String reg_price;

    public ProductDetail() {

    }

    public ProductDetail(int keyId, String relateId, String chatBotId, String type, String reg_price) {
        this.keyId = keyId;
        this.relateId = relateId;
        this.chatBotId = chatBotId;
        this.type = type;
        this.reg_price = reg_price;
    }

    public ProductDetail(String relateId, String chatBotId, String type, String reg_price) {
        this.relateId = relateId;
        this.chatBotId = chatBotId;
        this.type = type;
        this.reg_price = reg_price;
    }

    protected ProductDetail(Parcel in) {
        relateId = in.readString();
        chatBotId = in.readString();
        type = in.readString();
        reg_price = in.readString();
        keyId = in.readInt();
    }

    public static final Creator<ProductDetail> CREATOR = new Creator<ProductDetail>() {
        @Override
        public ProductDetail createFromParcel(Parcel in) {
            return new ProductDetail(in);
        }

        @Override
        public ProductDetail[] newArray(int size) {
            return new ProductDetail[size];
        }
    };

    public String getRelateId() {
        return relateId;
    }

    public void setRelateId(String relateId) {
        this.relateId = relateId;
    }

    public String getChatBotId() {
        return chatBotId;
    }

    public void setChatBotId(String chatBotId) {
        this.chatBotId = chatBotId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReg_price() {
        return reg_price;
    }

    public void setReg_price(String reg_price) {
        this.reg_price = reg_price;
    }

    public int getKeyId() {
        return keyId;
    }

    public void setKeyId(int keyId) {
        this.keyId = keyId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(relateId);
        parcel.writeString(chatBotId);
        parcel.writeString(type);
        parcel.writeString(reg_price);
        parcel.writeInt(keyId);
    }
}
