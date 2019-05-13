package infobite.ibt.tapizy.model;

import infobite.ibt.tapizy.model.bot_profile_data.BotDetail;
import infobite.ibt.tapizy.model.login_data_modal.UserDataMainModal;

/**
 * Created by Natraj3 on 8/26/2017.
 */
public class User {

    public static UserDataMainModal user;
    public static BotDetail botDetail;

    public static String coins;

    public static UserDataMainModal getUser() {
        return user;
    }

    public static void setUser(UserDataMainModal user) {
        User.user = user;
    }

    public static BotDetail getBotDetail() {
        return botDetail;
    }

    public static void setBotDetail(BotDetail botDetail) {
        User.botDetail = botDetail;
    }

    public static String getCoins() {
        return coins;
    }

    public static void setCoins(String coins) {
        User.coins = coins;
    }
}
