package prasad.com.gamespaisa;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import prasad.com.gamespaisa.fcm.MyFirebaseMessagingService;
import prasad.com.gamespaisa.material.LoginActivity;
import prasad.com.gamespaisa.utility.Constants;
import prasad.com.gamespaisa.utility.Session;

public class SplashActivity extends Activity {
    private static int SPLASH_TIME_OUT = 3000;
    private boolean goahead;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
             @Override
            public void run() {
               //  FirebaseApp.initializeApp(SplashActivity.this);
                 //Log.e("FCM_TOKEN", FirebaseInstanceId.getInstance().getToken());
            if(Session.getInstance(SplashActivity.this).getLoginId()!=null&&Session.getInstance(SplashActivity.this).getLoginId().equals("")){
               /* if(checkPlayServices())
                {
                    Intent intent = new Intent(SplashActivity.this, MyFirebaseMessagingService.class);
                    startService(intent);
                }*/
                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }else{
            Log.e("Username",Session.getInstance(SplashActivity.this).getLoginUserName());

                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }


            }
        }, SPLASH_TIME_OUT);
    }
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Constants.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);
        Log.e("GamesPaisa", "Firebase reg id: " + regId);

    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            goahead = false;
            if (apiAvailability.isUserResolvableError(resultCode)) {
               Dialog dialog = apiAvailability.getErrorDialog(this, resultCode,100);
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            } else {
                Log.i("Demo App", "This device is not supported.");

                AlertDialog.Builder dialog_app = new AlertDialog.Builder(SplashActivity.this);
                dialog_app.setTitle("Error");
                dialog_app.setMessage("This device is not supported..");
                dialog_app.setCancelable(false);
                dialog_app.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                dialog_app.show();
            }
            return false;
        }
        return true;
    }

}
