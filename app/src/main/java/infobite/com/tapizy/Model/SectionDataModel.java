package infobite.com.tapizy.Model;

import java.util.ArrayList;

public class SectionDataModel {

    private String profile;
    private int image;
    private ArrayList<SingleItemModel> allItemsInSection;

    public SectionDataModel() {
    }

    public SectionDataModel(String profile, int image, ArrayList<SingleItemModel> allItemsInSection) {
        this.profile = profile;
        this.image = image;
        this.allItemsInSection = allItemsInSection;
    }


    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public ArrayList<SingleItemModel> getAllItemsInSection() {
        return allItemsInSection;
    }

    public void setAllItemsInSection(ArrayList<SingleItemModel> allItemsInSection) {
        this.allItemsInSection = allItemsInSection;
    }
}
