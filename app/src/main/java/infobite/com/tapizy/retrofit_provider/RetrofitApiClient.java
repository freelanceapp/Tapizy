package infobite.com.tapizy.retrofit_provider;

import infobite.com.tapizy.constant.Constant;
import infobite.com.tapizy.model.comment_list_modal.CommentMainModal;
import infobite.com.tapizy.model.login_data_modal.UserDataMainModal;
import infobite.com.tapizy.model.timeline_modal.DailyNewsFeedMainModal;
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
                                          @Field("u_email") String u_email);

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
    @POST(Constant.TIMELINE_API)
    Call<DailyNewsFeedMainModal> postDetail(@Field("post_id") String post_id, @Field("uid") String uid);

    @FormUrlEncoded
    @POST(Constant.COMMENT_API)
    Call<CommentMainModal> newPostComment(@Field("post_id") String postId, @Field("uid") String useId,
                                          @Field("comment") String comment);
}