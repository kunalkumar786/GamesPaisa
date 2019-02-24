package prasad.com.gamespaisa.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import prasad.com.gamespaisa.model.NotificationDataModel;

/**
 * Created by Rajesh Dabhi on 26/6/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static String DB_NAME = "gamespaisa";
    private static int DB_VERSION = 1;
    private SQLiteDatabase db;
    // NOTIFICATION_TABLE
    public static final String NOTIFICATION_TABLE = "notification";
    public static final String NOTIFICATION_TITLE = "notification_title";
    public static final String NOTIFICATION_DATE = "notification_date";
    public static final String NOTIFICATION_DESCRIPTION = "notification_description";
    public static final String NOTIFICATION_IMAGE = "notification_image";


    public DatabaseHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;


String exe_notification = "CREATE TABLE IF NOT EXISTS " + NOTIFICATION_TABLE
                + "(" + NOTIFICATION_TITLE  + " TEXT, "
                + NOTIFICATION_DESCRIPTION + " TEXT, "
                + NOTIFICATION_DATE + " TEXT, "
                + NOTIFICATION_IMAGE + " TEXT "
                + ")";


        db.execSQL(exe_notification);
    }

    public void addNotificationData(NotificationDataModel notificationDataModel){
       try {
           db = getWritableDatabase();
           ContentValues values = new ContentValues();
           values.put(NOTIFICATION_TITLE, notificationDataModel.getNotification_title());
           values.put(NOTIFICATION_DESCRIPTION, notificationDataModel.getNotification_body());
           values.put(NOTIFICATION_DATE, notificationDataModel.getNotification_date());
           values.put(NOTIFICATION_IMAGE, notificationDataModel.getNotification_url());
           db.insert(NOTIFICATION_TABLE, null, values);
           Log.e("NOTI_Added",""+values);
       }catch (Exception e){
           e.printStackTrace();
       }finally {
           db.close();
       }
       }
public ArrayList<NotificationDataModel> getAllNotification(){
        ArrayList<NotificationDataModel> noti_data=new ArrayList<>();
    db = getReadableDatabase();
    String qry = "Select *  from " + NOTIFICATION_TABLE;
    Cursor cursor = db.rawQuery(qry, null);
    cursor.moveToFirst();
    for(int i=0;i<cursor.getCount();i++){
     NotificationDataModel notificationDataModel=new NotificationDataModel();
     notificationDataModel.setNotification_title(cursor.getString(cursor.getColumnIndex(NOTIFICATION_TITLE)));
     notificationDataModel.setNotification_body(cursor.getString(cursor.getColumnIndex(NOTIFICATION_DESCRIPTION)));
     notificationDataModel.setNotification_date(cursor.getString(cursor.getColumnIndex(NOTIFICATION_DATE)));
     notificationDataModel.setNotification_url(cursor.getString(cursor.getColumnIndex(NOTIFICATION_IMAGE)));
     noti_data.add(notificationDataModel);
        cursor.moveToNext();
    }
    return noti_data;
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
