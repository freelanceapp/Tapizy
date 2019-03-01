package ibt.com.tapizy.firebase_service;

import android.content.Context;

import ibt.com.tapizy.utils.NotificationHelper;

public class MyFirebaseMessagingService {

    private NotificationHelper notificationHelper;
    private Context mContext;
    private String TAG = "message_data";

    public MyFirebaseMessagingService() {
        //default constructor
    }

    /*@Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        mContext = this;
        notificationHelper = new NotificationHelper(mContext);

    }*/
}

/*public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private NotificationHelper notificationHelper;
    private Context mContext;
    private String TAG = "message_data";

    public MyFirebaseMessagingService() {
        //default constructor
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        mContext = this;
        notificationHelper = new NotificationHelper(mContext);

    }
}*/