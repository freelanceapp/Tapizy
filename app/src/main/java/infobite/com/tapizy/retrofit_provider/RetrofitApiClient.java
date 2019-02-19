package infobite.com.tapizy.retrofit_provider;

import infobite.com.tapizy.constant.Constant;
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
    Call<ResponseBody> signIn(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST(Constant.USER_SIGNUP_API)
    Call<ResponseBody> signUp(@Field("name") String name, @Field("email") String email,
                              @Field("password") String password);

    @FormUrlEncoded
    @POST(Constant.UPDATE_PROFILE_API)
    Call<ResponseBody> updateProfile(@Field("u_contact") String user_contact, @Field("u_username") String user_name,
                                     @Field("u_gender") String user_gender, @Field("u_website") String user_website,
                                     @Field("u_bio") String user_bio, @Field("is_bot") String user_bot,
                                     @Field("uid") String user_id);

}