package infobite.com.tapizy.firebase_service;

import android.content.Context;
import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import org.json.JSONObject;
import infobite.com.tapizy.utils.NotificationHelper;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
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
}
