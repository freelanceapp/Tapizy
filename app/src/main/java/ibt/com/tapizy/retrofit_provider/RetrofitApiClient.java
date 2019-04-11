package ibt.com.tapizy.retrofit_provider;

import ibt.com.tapizy.constant.Constant;
import ibt.com.tapizy.model.api_bot_list.BotListMainModal;
import ibt.com.tapizy.model.api_chat_list.ChatListMainModal;
import ibt.com.tapizy.model.api_conversation_modal.ApiConversationMainModal;
import ibt.com.tapizy.model.bot_profile_data.BotDetailMainModal;
import ibt.com.tapizy.model.chat_history.ChatHistoryMainModal;
import ibt.com.tapizy.model.city_list_modal.ApiCityListMainModal;
import ibt.com.tapizy.model.comment_list_modal.CommentMainModal;
import ibt.com.tapizy.model.communication.CommunicationMainModal;
import ibt.com.tapizy.model.community_post_modal.QuestionAnswerListMainModal;
import ibt.com.tapizy.model.favourite_bot.FavouriteBotMainModal;
import ibt.com.tapizy.model.login_data_modal.UserDataMainModal;
import ibt.com.tapizy.model.timeline_modal.DailyNewsFeedMainModal;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RetrofitApiClient {

    @FormUrlEncoded
    @POST(Constant.LOGIN_API)
    Call<ResponseBody> signIn(@Field("u_contact") String u_contact);

    @FormUrlEncoded
    @POST(Constant.OTP_VERIFICATION)
    Call<UserDataMainModal> otpVerification(@Field("u_contact") String u_contact, @Field("otp") String otp);

    @FormUrlEncoded
    @POST(Constant.RESEND_OTP)
    Call<ResponseBody> resendOTP(@Field("u_contact") String u_contact);

    @FormUrlEncoded
    @POST(Constant.UPDATE_PROFILE_API)
    Call<UserDataMainModal> updateProfile(@Field("u_name") String u_name, @Field("u_email") String u_email,
                                          @Field("u_gender") String u_gender, @Field("uid") String uid,
                                          @Field("dob") String dob, @Field("city") String city,
                                          @Field("state") String state, @Field("county") String county);

    @Multipart
    @POST(Constant.UPDATE_PROFILE_IMAGE_API)
    Call<ResponseBody> updateProfileImage(@Part("uid") RequestBody user_id, @Part MultipartBody.Part aimage);

    @FormUrlEncoded
    @POST(Constant.BOT_CATEGORY_TYPE)
    Call<ResponseBody> subCategory(@Field("bot_type") String bot_type);

    @FormUrlEncoded
    @POST(Constant.MY_BOT)
    Call<BotListMainModal> myBotList(@Field("uid") String uid);

    @FormUrlEncoded
    @POST(Constant.BOT_DETAIL)
    Call<BotDetailMainModal> botDetail(@Field("bot_id") String bot_id);

    @Multipart
    @POST(Constant.BOT_CREATE)
    Call<BotDetailMainModal> botCreate(@Part("uid") RequestBody uid, @Part("bot_name") RequestBody bot_name,
                                       @Part("bot_color") RequestBody bot_color,
                                       @Part("bot_type") RequestBody bot_type, @Part("bot_sub_type") RequestBody bot_sub_type,
                                       @Part("website") RequestBody website, @Part("description") RequestBody description,
                                       @Part MultipartBody.Part image);

    @Multipart
    @POST(Constant.BOT_UPDATE_PROFILE_IMAGE)
    Call<BotDetailMainModal> botUpdateProfileImage(@Part("bot_id") RequestBody bot_id,
                                                   @Part MultipartBody.Part image);

    @FormUrlEncoded
    @POST(Constant.BOT_UPDATE_PROFILE_DATA)
    Call<BotDetailMainModal> botUpdateProfile(@Field("uid") String uid, @Field("bot_name") String bot_name,
                                              @Field("bot_color") String bot_color,
                                              @Field("bot_type") String bot_type, @Field("bot_sub_type") String bot_sub_type,
                                              @Field("website") String website, @Field("description") String description);

    @Multipart
    @POST(Constant.NEWPOST_API)
    Call<ResponseBody> newPostFeed(@Part("uid") RequestBody user_id, @Part("headline") RequestBody headline,
                                   @Part("status") RequestBody post_description,
                                   @Part("user_type") RequestBody user_type,
                                   @Part MultipartBody.Part video,
                                   @Part MultipartBody.Part aimage);

    @FormUrlEncoded
    @POST(Constant.TIMELINE_API)
    Call<DailyNewsFeedMainModal> showPostTimeLine(@Field("uid") String useId, @Field("user_type") String user_type);

    @FormUrlEncoded
    @POST(Constant.PostLike)
    Call<ResponseBody> postLike(@Field("post_id") String postId, @Field("uid") String useId,
                                @Field("like") String like, @Field("unlike") String unlike,
                                @Field("user_type") String user_type);

    @FormUrlEncoded
    @POST(Constant.POST_DETAIL_API)
    Call<DailyNewsFeedMainModal> postDetail(@Field("post_id") String post_id, @Field("user_type") String user_type,
                                            @Field("uid") String uid);

    @FormUrlEncoded
    @POST(Constant.COMMENT_API)
    Call<CommentMainModal> newPostComment(@Field("post_id") String postId, @Field("uid") String useId,
                                          @Field("comment") String comment, @Field("user_type") String user_type);

    @FormUrlEncoded
    @POST(Constant.USER_DETAIL_API)
    Call<UserDataMainModal> getUserDetail(@Field("uid") String useId);

    /*uid, main_bot, relate_id,text, type, response_related_id*/
    @FormUrlEncoded
    @POST(Constant.CREATE_CONVERSATION_API)
    Call<ApiConversationMainModal> createConversation(@Field("uid") String userId, @Field("main_bot") String main_bot,
                                                      @Field("relate_id") String relate_id, @Field("text") String text,
                                                      @Field("type") String type,
                                                      @Field("response_related_id") String response_related_id,
                                                      @Field("sub_question") String sub_question);

    @FormUrlEncoded
    @POST(Constant.SELECT_CONVERSATION_API)
    Call<ApiConversationMainModal> selectConversation(@Field("uid") String userId);

    @FormUrlEncoded
    @POST(Constant.BOT_LIST_API)
    Call<BotListMainModal> botList(@Field("main_bot") String main_bot, @Field("uid") String uid);

    @FormUrlEncoded
    @POST(Constant.COMMUNICATION_API)
    Call<CommunicationMainModal> communicationWelcomeData(@Field("user_bot_id") String user_bot_id, @Field("uid") String uid);

    @FormUrlEncoded
    @POST(Constant.SEND_MSG)
    Call<CommunicationMainModal> sendMsg(@Field("user_bot_id") String user_bot_id, @Field("uid") String uid,
                                         @Field("relate_id") String relate_id, @Field("response_relate_id") String response_relate_id);

    @FormUrlEncoded
    @POST(Constant.QUESTION_LIST_API)
    Call<QuestionAnswerListMainModal> questionListData(@Field("city_bot_id") String city_bot_id);

    @FormUrlEncoded
    @POST(Constant.INSERT_ANSWER_API)
    Call<ResponseBody> insertAnswerApi(@Field("uid") String uid, @Field("question_id") String question_id,
                                       @Field("answer") String answer);

    @FormUrlEncoded
    @POST(Constant.INSERT_QUESTION_API)
    Call<ResponseBody> insertQuestionApi(@Field("uid") String uid, @Field("city_bot_id") String city_bot_id,
                                         @Field("question") String question);

    @FormUrlEncoded
    @POST(Constant.CHAT_LIST)
    Call<ChatListMainModal> chatList(@Field("uid") String uid);

    @FormUrlEncoded
    @POST(Constant.CHAT_HISTORY)
    Call<ChatHistoryMainModal> chatHistory(@Field("uid") String uid, @Field("user_bot_id") String user_bot_id);

    @FormUrlEncoded
    @POST(Constant.FAV_BOT_LIST)
    Call<FavouriteBotMainModal> favBotList(@Field("uid") String uid);

    @FormUrlEncoded
    @POST(Constant.ADD_TO_FAV)
    Call<ResponseBody> addToFav(@Field("uid") String uid, @Field("user_bot_id") String user_bot_id,
                                @Field("status") String status);

    @GET(Constant.CITY_LIST_API)
    Call<ApiCityListMainModal> cityList();
}