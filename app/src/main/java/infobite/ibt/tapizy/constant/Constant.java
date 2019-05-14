package infobite.ibt.tapizy.constant;

public class Constant {

    //public static final String BASE_URL = "http://tapizy.infobitetechnology.in/";
    public static final String BASE_URL_IMAGE = "http://infobitetechnology.tech/tapizy/";
    public static final String BASE_URL = "http://infobitetechnology.tech/tapizy/api/";

    /*User*/
    public static final String LOGIN_API = "user/login.php";
    public static final String OTP_VERIFICATION = "user/otpmatch.php";
    public static final String UPDATE_PROFILE_IMAGE_API = "user/update_profile_image.php";
    public static final String UPDATE_PROFILE_API = "user/update_profile.php";
    public static final String FAV_BOT_LIST = "user/my_favorite_bot_list.php";
    public static final String ADD_TO_FAV = "user/favorite.php";
    public static final String RESEND_OTP = "user/resend-otp.php";
    public static final String USER_DETAIL_API = "user/user_details.php";
    /*Bot*/
    public static final String BOT_CATEGORY_TYPE = "bot/bot_type.php";
    public static final String BOT_UPDATE_PROFILE_IMAGE = "bot/update_bot_profile_image.php";
    public static final String BOT_UPDATE_PROFILE_DATA = "bot/update_bot_profile.php";
    public static final String BOT_CREATE = "bot/create_bot.php";
    public static final String MY_BOT = "bot/my_bot.php";
    public static final String BOT_DETAIL = "bot/bot_detail.php";
    /*Trending*/
    public static final String PostLike = "trending/trending_post_like.php";
    public static final String NEWPOST_API = "trending/insert_daily_post.php";
    public static final String TIMELINE_API = "trending/timeline.php";
    public static final String POST_DETAIL_API = "trending/post_details.php";
    public static final String COMMENT_API = "trending/trending_comment.php";
    /*Community*/
    public static final String QUESTION_LIST_API = "community/select_question.php";
    public static final String CITY_LIST_API = "community/city_list.php";
    public static final String INSERT_ANSWER_API = "community/insert_answer.php";
    public static final String INSERT_QUESTION_API = "community/insert_question.php";
    /*Explore*/
    public static final String BOT_LIST_API = "explore/bot_list.php";
    /*Coins*/
    public static final String COINS_TIME = "coin/web_view_coins.php";
    public static final String MY_COINS = "coin/my_coins.php";
    public static final String PAY_COINS = "coin/paycoins.php";
    public static final String TRANSACTION = "coin/transaction.php";
    /*General*/
    public static final String SOCIAL_LINKS = "general/social_link.php";
    public static final String APP_CONTENT = "general/app_content.php";
    public static final String FAQ = "general/faq.php";
    /*Support*/
    public static final String SUPPORT = "support/support.php";
    public static final String FAQ_LIST = "support/select_query.php";
    /*ChatHistoryData api*/
    public static final String UPDATE_FIREBASE_MSG_API = "chat/send_msg.php";
    public static final String CONVERSATION_API = "chat/chat_question.php";
    public static final String CHAT_LIST = "chat/my_chat_bot_list.php";

    public static final String CREATE_CONVERSATION_API = "app/create_conversation.php";
    public static final String COMMUNICATION_API = "app/communition.php";
    public static final String CHAT_HISTORY = "chat/my_chat_history.php";

    /*Preference*/
    public static final String COIN_UPDATE = "COIN_UPDATE";
    public static final String CITY_ID = "CITY_ID";
    public static final String IS_LOGIN = "IS_LOGIN";
    public static final String USER_DATA = "USER_DATA";
    public static final String USER_IMAGE = "USER_IMAGE";
    public static final String POST_DETAIL = "POST_DETAIL";
    public static final String POST_CLICK = "POST_CLICK";
    public static final String TIMELINE_DATA = "TIMELINE_DATA";
    public static final String IS_DATA_UPDATE = "IS_DATA_UPDATE";
    public static final String TOKEN = "TOKEN";
    public static final String IS_PROFILE_UPDATE = "IS_PROFILE_UPDATE";

    public static final String USER_ID = "USER_ID";
    public static final String USER_TYPE = "USER_TYPE";

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
    public static final String HomeFragment = "HomeFragment";
    public static final String MyProfileFragment = "MyProfileFragment";
    public static final String MyBotListFragment = "MyBotListFragment";
    public static final String AvailabilityFragment = "AvailabilityFragment";
    public static final String RewardsFragment = "ShareAppFragment";
    public static final String VideoGalleryFragment = "VideoGalleryFragment";
    public static final String VideoTrimmerFragment = "VideoTrimmerFragment";
    public static final String UserAccountFragment = "UserAccountFragment";

    public static final String BotProfileFragment = "BotProfileFragment";
    public static final String BotUpdateProfileFragment = "BotUpdateProfileFragment";

    /**/
    public static final String EmailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    /*public static final String IMAGE_BASE_URL = BASE_URL_IMAGE + "image/trending/image/";
    public static final String VIDEO_BASE_URL = BASE_URL_IMAGE + "image/trending/video/";
    public static final String PROFILE_IMAGE_BASE_URL = BASE_URL_IMAGE + "image/user_profile/";
    public static final String BOT_PROFILE_IMAGE = BASE_URL_IMAGE + "image/bot/";*/

    public static final String IMAGE_BASE_URL = "";
    public static final String VIDEO_BASE_URL = "";
    public static final String PROFILE_IMAGE_BASE_URL = "";
    public static final String BOT_PROFILE_IMAGE = "";

    public static final String[] CATEGORY_LIST = {"Select Category", "My Travel", "Daily News", "My Money", "My Health", "My Shopping",
            "Near By Plans", "A to Z Home Services", "My Property"};
    //public static final String COIN_GIF = "https://im4.ezgif.com/tmp/ezgif-4-25d552422214.gif";
    public static final String COIN_GIF = "http://mine.bitcoin-mining.zarabatok.com/lending/btc.gif";
    public static final String COIN_GIF_B = "https://im4.ezgif.com/tmp/ezgif-4-2ac37b17a9fa.gif";

    public static final String SHARE_APP = "https://play.google.com/store/apps/details?id=ibt.com.tapizy";
}
/*<uses-permission android:name="android.permission.RECEIVE_SMS" />
    <uses-permission android:name="android.permission.READ_SMS" />
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />*/
