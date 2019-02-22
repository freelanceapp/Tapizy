package infobite.com.tapizy.retrofit_provider;

import infobite.com.tapizy.constant.Constant;
import infobite.com.tapizy.model.login_data_modal.UserDataMainModal;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

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
    Call<ResponseBody> updateProfile(@Field("u_contact") String user_contact, @Field("u_username") String user_name,
                                     @Field("u_gender") String user_gender, @Field("u_website") String user_website,
                                     @Field("u_bio") String user_bio, @Field("is_bot") String user_bot,
                                     @Field("uid") String user_id);
}