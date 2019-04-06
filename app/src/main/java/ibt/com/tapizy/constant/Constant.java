package ibt.com.tapizy.constant;

public class Constant {

    /*http://tapizy.infobitetechnology.in/app/insert_daily_post.php*/

    public static final String BASE_URL = "http://tapizy.infobitetechnology.in/";
    public static final String OTP_VERIFICATION = "app/otpmatch.php";
    public static final String RESEND_OTP = "app/resend-otp.php";
    public static final String PostLike = "app/trending_post_like.php";
    public static final String USER_SIGNUP_API = "user_signup.php";
    public static final String LOGIN_API = "app/login.php";
    public static final String UPDATE_PROFILE_API = "app/update_profile.php";
    public static final String UPDATE_PROFILE_IMAGE_API = "app/update_profile_image.php";
    public static final String NEWPOST_API = "app/insert_daily_post.php";
    public static final String TIMELINE_API = "app/timeline.php";
    public static final String POST_DETAIL_API = "app/post_details.php";
    public static final String COMMENT_API = "app/trending_comment.php";
    public static final String USER_DETAIL_API = "app/user_details.php";
    public static final String CREATE_CONVERSATION_API = "app/create_conversation.php";
    public static final String SELECT_CONVERSATION_API = "app/select_conversation_list.php";
    public static final String BOT_LIST_API = "app/bot_list.php";
    public static final String COMMUNICATION_API = "app/communition.php";
    public static final String SEND_MSG = "app/send_msg.php";
    public static final String BOT_DETAIL_ENTRY = "app/bot_details.php";
    public static final String BOT_CATEGORY_TYPE = "app/bot_type.php";
    public static final String QUESTION_LIST_API = "app/select_question.php";
    public static final String CITY_LIST_API = "app/city_list.php";
    public static final String INSERT_ANSWER_API = "app/insert_answer.php";
    public static final String INSERT_QUESTION_API = "app/insert_question.php";
    public static final String CHAT_LIST = "app/conversation_bot_list.php";
    public static final String CHAT_HISTORY = "app/my_chat_history.php";
    public static final String FAV_BOT_LIST = "app/my_favorite_bot_list.php";
    public static final String ADD_TO_FAV = "app/favorite.php";

    /*Preference*/
    public static final String CITY_ID = "CITY_ID";
    public static final String IS_LOGIN = "IS_LOGIN";
    public static final String USER_DATA = "USER_DATA";
    public static final String USER_ID = "USER_ID";
    public static final String USER_NAME = "USER_NAME";
    public static final String USER_IMAGE = "USER_IMAGE";
    public static final String POST_DETAIL = "POST_DETAIL";
    public static final String TIMELINE_DATA = "TIMELINE_DATA";
    public static final String IS_DATA_UPDATE = "IS_DATA_UPDATE";
    public static final String TOKEN = "TOKEN";

    public static final String MULTI_ACCOUNT = "MULTI_ACCOUNT";
    public static final String USER_B = "USER_B";
    public static final String USER_ID_B = "USER_ID_B";
    public static final String USER_NAME_B = "USER_NAME_B";
    public static final String USER_AVATAR_B = "USER_AVATAR_B";

    public static final String FIRST_CONVERSATION = "FIRST_CONVERSATION";

    /*Image url*/
    public static final String FbImage = "https://www.tkdtricities.com/wp-content/uploads/2018/06/facebook-icon-preview-200x200.png";
    public static final String TwitterImage = "https://images.vexels.com/media/users/3/137419/isolated/preview/b1a3fab214230557053ed1c4bf17b46c-twitter-icon-logo-by-vexels.png";
    public static final String FlipkartImage = "https://img1a.flixcart.com/www/linchpin/batman-returns/amp//cbb3574d.png";
    public static final String InstaImage = "https://cdn4.iconfinder.com/data/icons/social-messaging-ui-color-shapes-2-free/128/social-instagram-new-circle-512.png";

    public static final String FbUrl = "https://www.facebook.com/";
    public static final String TwitterUrl = "https://twitter.com/Twitter";
    public static final String FlipkartUrl = "https://www.flipkart.com/";
    public static final String InstaUrl = "https://www.instagram.com/?hl=en";

    /* Fragment tag */
    public static final String VideoGalleryFragment = "VideoGalleryFragment";
    public static final String VideoTrimmerFragment = "VideoTrimmerFragment";

    /**/
    public static final String EmailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    public static final String IMAGE_BASE_URL = BASE_URL + "/image/trending/image/";
    public static final String VIDEO_BASE_URL = BASE_URL + "/image/trending/video/";
    public static final String PROFILE_IMAGE_BASE_URL = BASE_URL + "/image/user_profile/";

    public static final String[] CATEGORY_LIST = {"Select Category", "My Travel", "Daily News", "My Money", "My Health", "My Shopping",
            "Near By Plans", "A to Z Home Services", "My Property"};
    public static final String BotHomeFragment = "BotHomeFragment";
}
/*<uses-permission android:name="android.permission.RECEIVE_SMS" />
    <uses-permission android:name="android.permission.READ_SMS" />
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />*/