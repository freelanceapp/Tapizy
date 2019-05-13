package infobite.ibt.tapizy.model;

public class TapizyListModel {
    String tapizy_title;
    int tapizy_logo;

    public TapizyListModel() {
    }

    public TapizyListModel(String tapizy_title, int tapizy_logo) {
        this.tapizy_title = tapizy_title;
        this.tapizy_logo = tapizy_logo;
    }

    public String getTapizy_title() {
        return tapizy_title;
    }

    public void setTapizy_title(String tapizy_title) {
        this.tapizy_title = tapizy_title;
    }

    public int getTapizy_logo() {
        return tapizy_logo;
    }

    public void setTapizy_logo(int tapizy_logo) {
        this.tapizy_logo = tapizy_logo;
    }
}
