package ibt.com.tapizy.retrofit_provider;

import ibt.com.tapizy.constant.Constant;
import ibt.com.tapizy.model.api_bot_list.BotListMainModal;
import ibt.com.tapizy.model.api_conversation_modal.ApiConversationMainModal;
import ibt.com.tapizy.model.comment_list_modal.CommentMainModal;
import ibt.com.tapizy.model.communication.CommunicationMainModal;
import ibt.com.tapizy.model.login_data_modal.UserDataMainModal;
import ibt.com.tapizy.model.timeline_modal.DailyNewsFeedMainModal;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
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
    Call<UserDataMainModal> updateProfile(@Field("u_contact") String user_contact, @Field("u_username") String user_username,
                                          @Field("u_gender") String user_gender, @Field("u_website") String user_website,
                                          @Field("u_bio") String user_bio, @Field("is_bot") String user_bot,
                                          @Field("uid") String uid, @Field("u_name") String u_name,
                                          @Field("u_email") String u_email, @Field("bot_color") String bot_color,
                                          @Field("bot_type") String bot_type, @Field("bot_sub_type") String bot_sub_type);

    @Multipart
    @POST(Constant.UPDATE_PROFILE_IMAGE_API)
    Call<ResponseBody> updateProfileImage(@Part("uid") RequestBody user_id, @Part MultipartBody.Part aimage);


    @Multipart
    @POST(Constant.NEWPOST_API)
    Call<ResponseBody> newPostFeed(@Part("uid") RequestBody user_id, @Part("headline") RequestBody headline,
                                   @Part("status") RequestBody post_description, @Part MultipartBody.Part video,
                                   @Part MultipartBody.Part aimage);


    @FormUrlEncoded
    @POST(Constant.TIMELINE_API)
    Call<DailyNewsFeedMainModal> showPostTimeLine(@Field("uid") String useId);

    @FormUrlEncoded
    @POST(Constant.PostLike)
    Call<ResponseBody> postLike(@Field("post_id") String postId, @Field("uid") String useId,
                                @Field("like") String like, @Field("unlike") String unlike);

    @FormUrlEncoded
    @POST(Constant.POST_DETAIL_API)
    Call<DailyNewsFeedMainModal> postDetail(@Field("post_id") String post_id, @Field("uid") String uid);

    @FormUrlEncoded
    @POST(Constant.COMMENT_API)
    Call<CommentMainModal> newPostComment(@Field("post_id") String postId, @Field("uid") String useId,
                                          @Field("comment") String comment);

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
    @POST(Constant.BOT_DETAIL_ENTRY)
    Call<ResponseBody> botDetailInsert(@Field("uid") String uid, @Field("bot_color") String bot_color,
                                       @Field("bot_type") String bot_type, @Field("bot_sub_type") String bot_sub_type);

    @FormUrlEncoded
    @POST(Constant.BOT_CATEGORY_TYPE)
    Call<ResponseBody> subCategory(@Field("bot_type") String bot_type);
}