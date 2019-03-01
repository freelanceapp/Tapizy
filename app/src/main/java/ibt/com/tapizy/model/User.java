package ibt.com.tapizy.model;

import ibt.com.tapizy.model.login_data_modal.UserDataMainModal;

/**
 * Created by Natraj3 on 8/26/2017.
 */
public class User {

    public static UserDataMainModal user;

    public static UserDataMainModal getUser() {
        return user;
    }

    public static void setUser(UserDataMainModal user) {
        User.user = user;
    }
}
