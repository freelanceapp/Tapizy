package infobite.ibt.tapizy.model;

public class RecentChatModal {
    private int prifileImage;
    private String name;

    public RecentChatModal(int prifileImage, String name) {
        this.prifileImage = prifileImage;
        this.name = name;
    }

    public int getPrifileImage() {
        return prifileImage;
    }

    public void setPrifileImage(int prifileImage) {
        this.prifileImage = prifileImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
