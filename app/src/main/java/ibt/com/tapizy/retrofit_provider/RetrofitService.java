package ibt.com.tapizy.retrofit_provider;

import android.app.Dialog;

import ibt.com.tapizy.constant.Constant;
import ibt.com.tapizy.model.api_bot_list.BotListMainModal;
import ibt.com.tapizy.model.api_chat_list.ChatListMainModal;
import ibt.com.tapizy.model.api_conversation_modal.ApiConversationMainModal;
import ibt.com.tapizy.model.chat_history.ChatHistoryMainModal;
import ibt.com.tapizy.model.city_list_modal.ApiCityListMainModal;
import ibt.com.tapizy.model.comment_list_modal.CommentMainModal;
import ibt.com.tapizy.model.communication.CommunicationMainModal;
import ibt.com.tapizy.model.community_post_modal.QuestionAnswerListMainModal;
import ibt.com.tapizy.model.favourite_bot.FavouriteBotMainModal;
import ibt.com.tapizy.model.login_data_modal.UserDataMainModal;
import ibt.com.tapizy.model.timeline_modal.DailyNewsFeedMainModal;
import ibt.com.tapizy.utils.AppProgressDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    public static RetrofitApiClient client;

    public RetrofitService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        client = retrofit.create(RetrofitApiClient.class);
    }

    public static RetrofitApiClient getRxClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constant.BASE_URL)
                .build();
        return retrofit.create(RetrofitApiClient.class);
    }

    public static RetrofitApiClient getRetrofit() {

        if (client == null)
            new RetrofitService();
        return client;
    }

    public static void getloginData(final Dialog dialog, final Call<ResponseBody> method, final WebResponse webResponse) {
        if (dialog != null)
            AppProgressDialog.show(dialog);

        method.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                WebServiceResponse.handleResponse(response, webResponse);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                webResponse.onResponseFailed(throwable.getMessage());
            }
        });
    }

    public static void getOtpData(final Dialog dialog, final Call<UserDataMainModal> method, final WebResponse webResponse) {
        if (dialog != null)
            AppProgressDialog.show(dialog);

        method.enqueue(new Callback<UserDataMainModal>() {
            @Override
            public void onResponse(Call<UserDataMainModal> call, Response<UserDataMainModal> response) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                WebServiceResponse.handleResponse(response, webResponse);
            }

            @Override
            public void onFailure(Call<UserDataMainModal> call, Throwable throwable) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                webResponse.onResponseFailed(throwable.getMessage());
            }
        });
    }

    public static void updateData(final Dialog dialog, final Call<UserDataMainModal> method, final WebResponse webResponse) {
        if (dialog != null)
            AppProgressDialog.show(dialog);

        method.enqueue(new Callback<UserDataMainModal>() {
            @Override
            public void onResponse(Call<UserDataMainModal> call, Response<UserDataMainModal> response) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                WebServiceResponse.handleResponse(response, webResponse);
            }

            @Override
            public void onFailure(Call<UserDataMainModal> call, Throwable throwable) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                webResponse.onResponseFailed(throwable.getMessage());
            }
        });
    }

    //user profile image
    public static void updateUserProfile(final Dialog dialog, final Call<ResponseBody> method, final WebResponse webResponse) {
        if (dialog != null)
            AppProgressDialog.show(dialog);

        method.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                WebServiceResponse.handleResponse(response, webResponse);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                webResponse.onResponseFailed(throwable.getMessage());
            }
        });
    }

    public static void getNewPostData(final Dialog dialog, final Call<ResponseBody> method, final WebResponse webResponse) {
        if (dialog != null)
            AppProgressDialog.show(dialog);

        method.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                WebServiceResponse.handleResponse(response, webResponse);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                webResponse.onResponseFailed(throwable.getMessage());
            }
        });
    }

    public static void refreshTimeLine(final Call<DailyNewsFeedMainModal> method, final WebResponse webResponse) {
        method.enqueue(new Callback<DailyNewsFeedMainModal>() {
            @Override
            public void onResponse(Call<DailyNewsFeedMainModal> call, Response<DailyNewsFeedMainModal> response) {
                WebServiceResponse.handleResponse(response, webResponse);
            }

            @Override
            public void onFailure(Call<DailyNewsFeedMainModal> call, Throwable throwable) {
                webResponse.onResponseFailed(throwable.getMessage());
            }
        });
    }

    public static void getLikeResponse(final Call<ResponseBody> method, final WebResponse webResponse) {
        method.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                WebServiceResponse.handleResponse(response, webResponse);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                webResponse.onResponseFailed(throwable.getMessage());
            }
        });
    }

    public static void showPostTimeLine(final Dialog dialog, final Call<DailyNewsFeedMainModal> method, final WebResponse webResponse) {
        if (dialog != null)
            AppProgressDialog.show(dialog);

        method.enqueue(new Callback<DailyNewsFeedMainModal>() {
            @Override
            public void onResponse(Call<DailyNewsFeedMainModal> call, Response<DailyNewsFeedMainModal> response) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                WebServiceResponse.handleResponse(response, webResponse);
            }

            @Override
            public void onFailure(Call<DailyNewsFeedMainModal> call, Throwable throwable) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                webResponse.onResponseFailed(throwable.getMessage());
            }
        });
    }

    public static void postCommentResponse(final Call<CommentMainModal> method, final WebResponse webResponse) {
        method.enqueue(new Callback<CommentMainModal>() {
            @Override
            public void onResponse(Call<CommentMainModal> call, Response<CommentMainModal> response) {
                WebServiceResponse.handleResponse(response, webResponse);
            }

            @Override
            public void onFailure(Call<CommentMainModal> call, Throwable throwable) {
                webResponse.onResponseFailed(throwable.getMessage());
            }
        });
    }

    public static void conversationListResponse(final Call<ApiConversationMainModal> method, final WebResponse webResponse) {
        method.enqueue(new Callback<ApiConversationMainModal>() {
            @Override
            public void onResponse(Call<ApiConversationMainModal> call, Response<ApiConversationMainModal> response) {
                WebServiceResponse.handleResponse(response, webResponse);
            }

            @Override
            public void onFailure(Call<ApiConversationMainModal> call, Throwable throwable) {
                webResponse.onResponseFailed(throwable.getMessage());
            }
        });
    }

    public static void createConversationResponse(final Call<ApiConversationMainModal> method, final WebResponse webResponse) {
        method.enqueue(new Callback<ApiConversationMainModal>() {
            @Override
            public void onResponse(Call<ApiConversationMainModal> call, Response<ApiConversationMainModal> response) {
                WebServiceResponse.handleResponse(response, webResponse);
            }

            @Override
            public void onFailure(Call<ApiConversationMainModal> call, Throwable throwable) {
                webResponse.onResponseFailed(throwable.getMessage());
            }
        });
    }

    public static void botListResponse(final Dialog dialog, final Call<BotListMainModal> method, final WebResponse webResponse) {
        if (dialog != null)
            AppProgressDialog.show(dialog);

        method.enqueue(new Callback<BotListMainModal>() {
            @Override
            public void onResponse(Call<BotListMainModal> call, Response<BotListMainModal> response) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                WebServiceResponse.handleResponse(response, webResponse);
            }

            @Override
            public void onFailure(Call<BotListMainModal> call, Throwable throwable) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                webResponse.onResponseFailed(throwable.getMessage());
            }
        });
    }

    public static void subCatList(final Dialog dialog, final Call<ResponseBody> method, final WebResponse webResponse) {
        if (dialog != null)
            AppProgressDialog.show(dialog);

        method.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                WebServiceResponse.handleResponse(response, webResponse);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                webResponse.onResponseFailed(throwable.getMessage());
            }
        });
    }

    public static void getCommunicationWelcomeData(final Dialog dialog, final Call<CommunicationMainModal> method, final WebResponse webResponse) {
        if (dialog != null)
            AppProgressDialog.show(dialog);

        method.enqueue(new Callback<CommunicationMainModal>() {
            @Override
            public void onResponse(Call<CommunicationMainModal> call, Response<CommunicationMainModal> response) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                WebServiceResponse.handleResponse(response, webResponse);
            }

            @Override
            public void onFailure(Call<CommunicationMainModal> call, Throwable throwable) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                webResponse.onResponseFailed(throwable.getMessage());
            }
        });
    }

    public static void getQuestionListData(final Dialog dialog, final Call<QuestionAnswerListMainModal> method, final WebResponse webResponse) {
        if (dialog != null)
            AppProgressDialog.show(dialog);

        method.enqueue(new Callback<QuestionAnswerListMainModal>() {
            @Override
            public void onResponse(Call<QuestionAnswerListMainModal> call, Response<QuestionAnswerListMainModal> response) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                WebServiceResponse.handleResponse(response, webResponse);
            }

            @Override
            public void onFailure(Call<QuestionAnswerListMainModal> call, Throwable throwable) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                webResponse.onResponseFailed(throwable.getMessage());
            }
        });
    }

    public static void getCityList(final Dialog dialog, final Call<ApiCityListMainModal> method, final WebResponse webResponse) {
        if (dialog != null)
            AppProgressDialog.show(dialog);

        method.enqueue(new Callback<ApiCityListMainModal>() {
            @Override
            public void onResponse(Call<ApiCityListMainModal> call, Response<ApiCityListMainModal> response) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                WebServiceResponse.handleResponse(response, webResponse);
            }

            @Override
            public void onFailure(Call<ApiCityListMainModal> call, Throwable throwable) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                webResponse.onResponseFailed(throwable.getMessage());
            }
        });
    }

    public static void getChatListData(final Dialog dialog, final Call<ChatListMainModal> method, final WebResponse webResponse) {
        if (dialog != null)
            AppProgressDialog.show(dialog);

        method.enqueue(new Callback<ChatListMainModal>() {
            @Override
            public void onResponse(Call<ChatListMainModal> call, Response<ChatListMainModal> response) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                WebServiceResponse.handleResponse(response, webResponse);
            }

            @Override
            public void onFailure(Call<ChatListMainModal> call, Throwable throwable) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                webResponse.onResponseFailed(throwable.getMessage());
            }
        });
    }

    public static void getChatHistoryData(final Dialog dialog, final Call<ChatHistoryMainModal> method, final WebResponse webResponse) {
        if (dialog != null)
            AppProgressDialog.show(dialog);

        method.enqueue(new Callback<ChatHistoryMainModal>() {
            @Override
            public void onResponse(Call<ChatHistoryMainModal> call, Response<ChatHistoryMainModal> response) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                WebServiceResponse.handleResponse(response, webResponse);
            }

            @Override
            public void onFailure(Call<ChatHistoryMainModal> call, Throwable throwable) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                webResponse.onResponseFailed(throwable.getMessage());
            }
        });
    }

    public static void getFavBotList(final Dialog dialog, final Call<FavouriteBotMainModal> method, final WebResponse webResponse) {
        if (dialog != null)
            AppProgressDialog.show(dialog);

        method.enqueue(new Callback<FavouriteBotMainModal>() {
            @Override
            public void onResponse(Call<FavouriteBotMainModal> call, Response<FavouriteBotMainModal> response) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                WebServiceResponse.handleResponse(response, webResponse);
            }

            @Override
            public void onFailure(Call<FavouriteBotMainModal> call, Throwable throwable) {
                if (dialog != null)
                    AppProgressDialog.hide(dialog);
                webResponse.onResponseFailed(throwable.getMessage());
            }
        });
    }
}