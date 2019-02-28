package infobite.com.tapizy.constant;

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
    public static final String SELECT_QUETION_API = "app/select_question.php";
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

    /*Preference*/
    public static final String CHATBOT_LIST = "CHATBOT_LIST";
    public static final String CHATBOT_NAME = "CHATBOT_NAME";
    public static final String IS_LOGIN = "IS_LOGIN";
    public static final String USER_DATA = "USER_DATA";
    public static final String USER_ID = "USER_ID";
    public static final String USER_NAME = "USER_NAME";
    public static final String USER_IMAGE = "USER_IMAGE";
    public static final String POST_DETAIL = "POST_DETAIL";
    public static final String TIMELINE_DATA = "TIMELINE_DATA";
    public static final String USER_TIMELINE_DATA = "USER_TIMELINE_DATA";
    public static final String IS_DATA_UPDATE = "IS_DATA_UPDATE";
    public static final String TOKEN = "TOKEN";

    public static final String FIRST_CONVERSATION = "FIRST_CONVERSATION";

    /* Fragment tag */
    public static final String TimelineFragment = "TimelineFragment";
    public static final String ProfileFragment = "ProfileFragment";
    public static final String NotificationFragment = "NotificationFragment";
    public static final String SettingFragment = "SettingFragment";
    public static final String VideoGalleryFragment = "VideoGalleryFragment";
    public static final String VideoTrimmerFragment = "VideoTrimmerFragment";

    /**/
    public static final String IMAGE_BASE_URL = BASE_URL + "/image/trending/image/";
    public static final String VIDEO_BASE_URL = BASE_URL + "/image/trending/video/";
    public static final String PROFILE_IMAGE_BASE_URL = BASE_URL + "/image/user_profile/";

    public static final String[] CATEGORY_LIST = {"Select Category", "My Travel", "Daily News", "My Money", "My Health", "My Shopping",
            "Near By Plans", "A to Z Home Services", "My Property"};
}
