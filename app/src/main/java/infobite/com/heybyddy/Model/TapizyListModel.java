package infobite.com.heybyddy.Model;

public class TapizyListModel {
    String tapizy_title;
    String tapizy_detail;
    int tapizy_logo;

    public TapizyListModel() {
    }

    public TapizyListModel(String tapizy_title, String tapizy_detail, int tapizy_logo) {
        this.tapizy_title = tapizy_title;
        this.tapizy_detail = tapizy_detail;
        this.tapizy_logo = tapizy_logo;
    }

    public String getTapizy_title() {
        return tapizy_title;
    }

    public void setTapizy_title(String tapizy_title) {
        this.tapizy_title = tapizy_title;
    }

    public String getTapizy_detail() {
        return tapizy_detail;
    }

    public void setTapizy_detail(String tapizy_detail) {
        this.tapizy_detail = tapizy_detail;
    }

    public int getTapizy_logo() {
        return tapizy_logo;
    }

    public void setTapizy_logo(int tapizy_logo) {
        this.tapizy_logo = tapizy_logo;
    }
}
