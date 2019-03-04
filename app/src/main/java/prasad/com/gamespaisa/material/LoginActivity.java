package prasad.com.gamespaisa.material;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Explode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;


import prasad.com.gamespaisa.MainActivity;
import prasad.com.gamespaisa.R;
import prasad.com.gamespaisa.interfac.Api_interface;
import prasad.com.gamespaisa.model.User;
import prasad.com.gamespaisa.utility.Session;
import prasad.com.gamespaisa.utility.UtilityMethods;
import prasad.com.gamespaisa.web_service.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

     EditText et_username;

     EditText et_password;

     Button bt_go,bt_add_money;

     CardView cv;
      TextView tv_forget_password;
     FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_one);

        et_username=(EditText)findViewById(R.id.et_username);
        et_password=(EditText)findViewById(R.id.et_password);
        tv_forget_password=(TextView)findViewById(R.id.tv_forget_password);
        bt_go=(Button)findViewById(R.id.bt_go);
        bt_add_money=(Button)findViewById(R.id.bt_add_money);
        cv=(CardView)findViewById(R.id.cv);
        fab=(FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(this);
        bt_go.setOnClickListener(this);
        tv_forget_password.setOnClickListener(this);

    }




    @Override
    protected void onRestart() {
        super.onRestart();
        fab.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fab.setVisibility(View.VISIBLE);
    }

public void callLogin(User user){
    Api_interface apiService = ApiClient.getClient().create(Api_interface.class);
    Call<JsonElement> call=apiService.loginCall(user.getUsername(),user.getPassword(),user.getFcm_token());

    call.enqueue(new Callback<JsonElement>() {
        @Override
        public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
            response.body(); // have your all data
            UtilityMethods.dismissProgressDialog();

            try {
                JSONObject response_val=new JSONObject(""+new Gson().toJson(response.body()));
                Log.e("TAG", "response 33: "+response_val );
                if(!response_val.getString("message").equals("Invalid password.")&&!response_val.getString("status").equals("0")){
                    ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(LoginActivity.this);
                    Intent i2 = new Intent(LoginActivity.this, MainActivity.class);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        startActivity(i2, oc2.toBundle());
                    }
                    User user1=new User();
                    user1.setUserid(response_val.getJSONObject("data").getJSONObject("value").getString("id"));
                    user1.setUsername(response_val.getJSONObject("data").getJSONObject("value").getString("username"));
                    user1.setEmail(response_val.getJSONObject("data").getJSONObject("value").getString("email"));
                    user1.setMobile(response_val.getJSONObject("data").getJSONObject("value").getString("contact"));
                    user1.setCity(response_val.getJSONObject("data").getJSONObject("value").getString("city"));
                    user1.setWallet(response_val.getJSONObject("data").getJSONObject("value").getString("finalAmount"));
                    user1.setEvent_joined(response_val.getJSONObject("data").getJSONObject("value").getString("event_purchage"));

                    user1.setProfile_pic(new File("" + response_val.getString("image_url")));
                    user1.setIsLogin("true");

                    Session.getInstance(LoginActivity.this).setLogin(user1);

                }else{
                    Toast.makeText(LoginActivity.this,""+response_val.getString("message"),Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            /*ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(LoginActivity.this);
            Intent i2 = new Intent(LoginActivity.this, MainActivity.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                startActivity(i2, oc2.toBundle());
            }*/
        }

        @Override
        public void onFailure(Call<JsonElement> call, Throwable t) {

        }
    });

}

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.bt_go:
                Explode explode = new Explode();
                explode.setDuration(500);

                getWindow().setExitTransition(explode);
                getWindow().setEnterTransition(explode);


                User user=new User();
                user.setUsername(et_username.getText().toString());
                user.setPassword(et_password.getText().toString());
                if(Session.getInstance(getApplicationContext()).getFcm()!=null&&!Session.getInstance(getApplicationContext()).getFcm().equals("")){
                    user.setFcm_token(Session.getInstance(getApplicationContext()).getFcm());
                }else{
                    user.setFcm_token("addashd_testing_fcm_app");
                }


                callLogin(user);
                UtilityMethods.showProgressDialog(this);
                break;
            case R.id.fab:
                getWindow().setExitTransition(null);
                getWindow().setEnterTransition(null);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, fab, fab.getTransitionName());
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class), options.toBundle());

                break;
            case R.id.tv_forget_password:
                showDialog();
                break;


        }

    }

public void showDialog(){
    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
// ...Irrelevant code for customizing the buttons and title
    LayoutInflater inflater = this.getLayoutInflater();
    final View dialogView = inflater.inflate(R.layout.alert_label_editor, null);
    dialogBuilder.setView(dialogView);
    final AlertDialog alertDialog = dialogBuilder.create();
    final EditText editText = (EditText) dialogView.findViewById(R.id.et_amount_value);
    bt_add_money=(Button)dialogView.findViewById(R.id.bt_add_money);
    bt_add_money.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            callforgotPassword(editText.getText().toString());
            alertDialog.dismiss();
        }
    });

    alertDialog.show();
}
public void callforgotPassword(String email_id){
    Api_interface apiService = ApiClient.getClient().create(Api_interface.class);
    Call<JsonElement> call=apiService.forgotPassword(email_id);
    call.enqueue(new Callback<JsonElement>() {
    @Override
    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
        try {
            JSONObject jsonObject = new JSONObject("" + new Gson().toJson(response.body()));
            Log.e("JSonObject",""+jsonObject);
            if(jsonObject.getString("message").equals("New password send to register email.")){
                Toast.makeText(LoginActivity.this,"New password send to register email.",Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onFailure(Call<JsonElement> call, Throwable t) {

    }
    });
    }
}
