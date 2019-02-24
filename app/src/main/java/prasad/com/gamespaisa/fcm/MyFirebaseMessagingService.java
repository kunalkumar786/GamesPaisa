package prasad.com.gamespaisa.fcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import prasad.com.gamespaisa.MainActivity;
import prasad.com.gamespaisa.R;
import prasad.com.gamespaisa.SplashActivity;
import prasad.com.gamespaisa.database.DatabaseHandler;
import prasad.com.gamespaisa.model.NotificationDataModel;


/**
 * Created by user on 7/13/2017.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

  private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
  public static final String PUSH_NOTIFICATION = "pushNotification";
  private NotificationUtils notificationUtils;
  DatabaseHandler dbHandler;
  @Override
  public void onMessageReceived(RemoteMessage remoteMessage) {
   /*Log.e("COLLAPSE_KEY",""+remoteMessage.getCollapseKey());
   Log.e("FROM",""+remoteMessage.getFrom());
   Log.e("MESSAGE_ID",""+remoteMessage.getMessageId());
   Log.e("NOTIFICATION",""+remoteMessage.getNotification());
   Log.e("TTL",""+remoteMessage.getTtl());
    Log.e(TAG, "From: " + remoteMessage.getFrom());*/
    if (remoteMessage == null)
      return;
    // Check if message contains a notification payload.
    if (remoteMessage.getNotification() != null) {
      Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
      //handleNotification(remoteMessage.getNotification().getBody());
    }
    JSONObject object=null;
    try
    {
      Map<String, String> params = remoteMessage.getData();
      object = new JSONObject(params);
      Log.e("JSON_OBJECT", object.toString());
    }catch (Exception e){
      e.printStackTrace();
    }
    // Check if message contains a data payload.
    if (remoteMessage.getData().size() > 0) {
      Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());


      if (object.has("title")) {
        //JSONObject object = new JSONObject(remoteMessage.getData());
        //   Log.e("NotificationData",""+object);
        String image=null;
        try {
          if(object.has("url")){
            image=object.getString("url");
          }else{
            image="";
          }
          sendNotification(object.getString("message"),object.getString("title"),image/*object.getString("image")*//*object.getString("image")*/,object.getString("created_at"));
          dbHandler=new DatabaseHandler(this);
          NotificationDataModel notificationDataModel=new NotificationDataModel();
          notificationDataModel.setNotification_title(object.getString("title"));
          notificationDataModel.setNotification_body(object.getString("message"));
          notificationDataModel.setNotification_url(image);
          notificationDataModel.setNotification_date(object.getString("created_at"));
          dbHandler.addNotificationData(notificationDataModel);
        } catch (JSONException e) {
          e.printStackTrace();
        }
        Log.d(TAG, "Message data payload: " + remoteMessage.getData());
      }




      Intent broadcastIntent = new Intent();
      broadcastIntent.setAction(MainActivity.NOTIFY_ACTIVITY_ACTION );
      broadcastIntent.putExtra("addtional_param", 1);
      broadcastIntent.putExtra("addtional_param2", 2); //etc

      sendBroadcast(broadcastIntent);

      try {
        JSONObject json = new JSONObject(remoteMessage.getData().toString());
        handleDataMessage(json);
      } catch (Exception e) {
        Log.e(TAG, "Exception: " + e.getMessage());
      }
    }
  }

  /*private void handleNotification(String message) {
    if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
      // app is in foreground, broadcast the push message
      Intent pushNotification = new Intent(PUSH_NOTIFICATION);
      pushNotification.putExtra("message", message);
      LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

      // play notification sound
      NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
      notificationUtils.playNotificationSound();
    }else{
      // If the app is in background, firebase itself handles the notification
    }
  }*/

  private void handleDataMessage(JSONObject json) {
    Log.e(TAG, "push json: " + json.toString());

    try {
      JSONObject data = json.getJSONObject("data");
      String title = data.getString("title");
      String message = data.getString("message");
      boolean isBackground = data.getBoolean("is_background");
      String imageUrl = data.getString("image");
      String timestamp = data.getString("timestamp");
      String notification_type=null;
      String address=null,service_name=null,
              latitude=null,
              longitude=null,contact=null,
              time=null,date=null,call_type=null;



      JSONObject payload = data.getJSONObject("payload");
    try {
      JSONArray notification_detail = payload.getJSONArray("image");
      if (notification_detail.length() > 0) {
        JSONObject noti_data = notification_detail.getJSONObject(0);
        notification_type=noti_data.getString("notification_type");
        address=noti_data.getString("address");
        service_name=noti_data.getString("service_name");
        latitude=noti_data.getString("latitude");
        contact=noti_data.getString("contact");
        longitude=noti_data.getString("longitude");
        time=noti_data.getString("time");
        date=noti_data.getString("date");
        call_type=noti_data.getString("call_type");


      }
    }catch(Exception e){
      e.printStackTrace();
    }


      Log.e(TAG, "title: " + title);
      Log.e(TAG, "message: " + message);
      Log.e(TAG, "isBackground: " + isBackground);
      Log.e(TAG, "payload: " + payload.toString());
      Log.e(TAG, "imageUrl: " + imageUrl);
      Log.e(TAG, "timestamp: " + timestamp);


      /*if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
        // app is in foreground, broadcast the push message
        Intent pushNotification = new Intent(PUSH_NOTIFICATION);
        pushNotification.putExtra("message", message);
        LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

        // play notification sound
        NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
        notificationUtils.playNotificationSound();
      } else {
        // app is in background, show the notification in notification tray
        Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
        resultIntent.putExtra("message", message);
        resultIntent.putExtra("notification_type", notification_type);
        resultIntent.putExtra("service_name",service_name);
        resultIntent.putExtra("service_date",date);
        resultIntent.putExtra("service_lat",latitude);
        resultIntent.putExtra("service_long",longitude);
        resultIntent.putExtra("service_contact",contact);
        resultIntent.putExtra("service_type",call_type);
        resultIntent.putExtra("service_time",time);
        resultIntent.putExtra("service_address",address);
        // check for image attachment
        if (TextUtils.isEmpty(imageUrl)) {
          showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
        } else {
          // image is present, show notification with image
          showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
        }
      }*/
    } catch (JSONException e) {
      Log.e(TAG, "Json Exception: " + e.getMessage());
    } catch (Exception e) {
      Log.e(TAG, "Exception: " + e.getMessage());
    }
  }

  /**
   * Showing notification with text only
   */
  private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
    notificationUtils = new NotificationUtils(context);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
  }

  /**
   * Showing notification with text and image
   */
  private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
    notificationUtils = new NotificationUtils(context);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
  }
  private void sendNotification(String message,String title, String imageUrl, String created_at) {
    Intent intent = new Intent(this, SplashActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
            PendingIntent.FLAG_ONE_SHOT);
    if (imageUrl != null && imageUrl.length() > 4 && Patterns.WEB_URL.matcher(imageUrl).matches()) {
      Bitmap bitmap = getBitmapFromURL(imageUrl);
      showBigNotification(bitmap,title,message,created_at,pendingIntent);
    }else{
      simpleteNotification(title,message,created_at,pendingIntent);
    }



  }
  public Bitmap getBitmapFromURL(String strURL) {
    try {
      URL url = new URL(strURL);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setDoInput(true);
      connection.connect();
      InputStream input = connection.getInputStream();
      Bitmap myBitmap = BitmapFactory.decodeStream(input);
      return myBitmap;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  private void showBigNotification(Bitmap bitmap, String title, String message, String timeStamp, PendingIntent resultPendingIntent) {
    NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
    bigPictureStyle.setBigContentTitle(title);
    bigPictureStyle.setSummaryText(Html.fromHtml(message).toString());
    bigPictureStyle.bigPicture(bitmap);
    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);

        /*Notification notification;
        notification = mBuilder.setSmallIcon(R.drawable.launcher).setTicker(title)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentIntent(resultPendingIntent)
                .setStyle(bigPictureStyle)
                .setWhen(getTimeMilliSec(timeStamp))
                .setLargeIcon(bitmap)
                .setContentText(message)
                .build();*/
    Notification notification = new Notification.Builder(this)
            .setContentTitle(message)
            .setContentText(title)
            .setSmallIcon(R.drawable.logo)
            .setLargeIcon(bitmap)
            .setStyle(new Notification.BigPictureStyle()
                    .bigPicture(bitmap))
            .setContentIntent(resultPendingIntent)
            .build();

    NotificationManager notificationManager =
            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

    notificationManager.notify(1, notification);
  }
  /**
   * Downloading push notification image before displaying it in
   * the notification tray
   * */
  /*public Bitmap getBitmapFromURL(String strURL) {
    try {
      URL url = new URL(strURL);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setDoInput(true);
      connection.connect();
      InputStream input = connection.getInputStream();
      Bitmap myBitmap = BitmapFactory.decodeStream(input);
      return myBitmap;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }*/
  private  void simpleteNotification(String title, String message, String timeStamp, PendingIntent pendingIntent){
    Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
            .setSmallIcon(R.drawable.logo)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent);

    NotificationManager notificationManager =
            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

    notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

  }
}