package prasad.com.gamespaisa.material;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.facebook.accountkit.AccessToken;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.PhoneNumber;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.facebook.accountkit.ui.SkinManager;
import com.facebook.accountkit.ui.UIManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;


import java.io.File;
import java.io.IOException;
import java.nio.file.Path;


import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;

import okhttp3.RequestBody;
import prasad.com.gamespaisa.MainActivity;
import prasad.com.gamespaisa.R;
import prasad.com.gamespaisa.SplashActivity;
import prasad.com.gamespaisa.fragment.ProfileFragment;
import prasad.com.gamespaisa.interfac.Api_interface;
import prasad.com.gamespaisa.model.EventModel;
import prasad.com.gamespaisa.model.User;
import prasad.com.gamespaisa.utility.Constants;
import prasad.com.gamespaisa.utility.Session;
import prasad.com.gamespaisa.utility.UtilityMethods;
import prasad.com.gamespaisa.web_service.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

FloatingActionButton fab;

CardView cvAdd;

CircleImageView profile_image;

EditText et_username;

EditText et_password;


EditText et_email_address;

EditText et_mobile_number;

EditText et_city;

Button bt_go;

 private String phone_number;
 User user;
 //Path fileUpload;

    File fileUpload = null;
    RequestBody Email, Username,Password, Mobile, City, Fcm_token,requestFile,requestBody;
    Uri selectedImage;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_two);
        hideSoftKeyboard();
       // ButterKnife.bind(this);
        fab=(FloatingActionButton)findViewById(R.id.fab);
        cvAdd= (CardView)findViewById(R.id.cv_add);
        profile_image= (CircleImageView)findViewById (R.id.profile_image);
        et_username=(EditText)findViewById(R.id.et_username);
        et_password=(EditText)findViewById(R.id.et_password);
        et_email_address=(EditText)findViewById(R.id.et_email_address);
        et_mobile_number=(EditText)findViewById(R.id.et_mobile_number);
        et_city=(EditText)findViewById(R.id.et_city);
        bt_go=(Button) findViewById(R.id.bt_go);
        fab.setOnClickListener(this);
        bt_go.setOnClickListener(this);
        profile_image.setOnClickListener(this);
        ShowEnterAnimation();
        if (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this) != ConnectionResult.SUCCESS) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE},
                        Constants.PHONE_STATE_PERMISSION_REQUEST_CODE);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                    Constants.SMS_STORAGE_CAMERA_PERMISSION_REQUEST_CODE);
        //else
         //   initAuthorization();




}

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void ShowEnterAnimation() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition);
        getWindow().setSharedElementEnterTransition(transition);

        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                cvAdd.setVisibility(View.GONE);
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealShow();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }


        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void animateRevealShow() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.getWidth()/2,0, fab.getWidth() / 2, cvAdd.getHeight());
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                cvAdd.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void animateRevealClose() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd,cvAdd.getWidth()/2,0, cvAdd.getHeight(), fab.getWidth() / 2);
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                cvAdd.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                fab.setImageResource(R.drawable.plus);
                RegisterActivity.super.onBackPressed();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {
        animateRevealClose();
    }
    private void initAuthorization() {
        AccessToken accessToken = AccountKit.getCurrentAccessToken();
        if (accessToken == null) {
            Intent i = new Intent(RegisterActivity.this, AccountKitActivity.class);
            AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder
                    = new AccountKitConfiguration.AccountKitConfigurationBuilder(LoginType.PHONE,
                    AccountKitActivity.ResponseType.TOKEN);
            UIManager uiManager = new SkinManager(SkinManager.Skin.CONTEMPORARY, getResources().getColor(R.color.colorPrimary));
            configurationBuilder.setUIManager(uiManager);
            i.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION, configurationBuilder.build());
            startActivityForResult(i, Constants.REQUEST_CODE);
        } else
            getCurrentAccount();
    }

    private void getCurrentAccount() {

        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
            @Override
            public void onSuccess(Account account) {

                PhoneNumber phoneNumber = account.getPhoneNumber();

                phone_number = phoneNumber.toString();
              //  new RegisterUser().execute();
            }

            @Override
            public void onError(AccountKitError accountKitError) {
                Log.e("Account Kit Error", accountKitError.toString());
                //progressBar.setVisibility(View.GONE);
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setMessage("Authorization failed. Please check your internet connection.");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.setCancelable(false);
                dialog.show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == Constants.SMS_STORAGE_CAMERA_PERMISSION_REQUEST_CODE || requestCode == Constants.PHONE_STATE_PERMISSION_REQUEST_CODE) {
            if (grantResults[1] != PackageManager.PERMISSION_GRANTED)
                finish();
            else{

            }
            //    initAuthorization();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.REQUEST_CODE) {
            AccountKitLoginResult result = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            if (result.getError() != null) {
                Toast.makeText(this, result.getError().getErrorType().getMessage(), Toast.LENGTH_SHORT).show();
            } else if (result.wasCancelled()) {
                finish();
            } else {
                if (result.getAccessToken() != null)
                    getCurrentAccount();
            }
        }if (requestCode == Constants.SELECT_FILE)
        {
            fileUpload= UtilityMethods.onSelectFromGalleryResult(this,data);
             selectedImage = data.getData();
            // tv_add_photo_video.setText(""+fileUpload);
            Bitmap myBitmap = BitmapFactory.decodeFile(fileUpload.getAbsolutePath());
            profile_image.setImageBitmap(myBitmap);

        } else if (requestCode == Constants.REQUEST_CAMERA)
        {
            fileUpload= UtilityMethods.onCaptureImageResult(data);
            selectedImage = data.getData();
            //    tv_add_photo_video.setText(""+fileUpload);
            Bitmap myBitmap = BitmapFactory.decodeFile(fileUpload.getAbsolutePath());
            profile_image.setImageBitmap(myBitmap);

        }
    }

    public void uploadOperation(){
        final CharSequence[] items = {"Photo", "Gallery","Cancel" };

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(
                this);
        builder.setTitle("Profile Pic");
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Photo")) {
                    selectCamera();
                } else if (items[item].equals("Gallery")) {
                   selectGallery();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    public void selectCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, Constants.REQUEST_CAMERA);
    }
public void selectGallery(){
    Intent intent = new Intent(
            Intent.ACTION_PICK,
            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    intent.setType("image/*");
    startActivityForResult(
            Intent.createChooser(intent, "Select File"),
            Constants.SELECT_FILE);
}
public void updateToServer(User user){
    /*Email=RequestBody.create(MediaType.parse("text/plain"),user.getEmail());
    Username=RequestBody.create(MediaType.parse("text/plain"),user.getUsername());
    City=RequestBody.create(MediaType.parse("text/plain"),user.getCity());
    Mobile=RequestBody.create(MediaType.parse("text/plain"),user.getMobile());
    Password=RequestBody.create(MediaType.parse("text/plain"),user.getPassword());
    Fcm_token=RequestBody.create(MediaType.parse("text/plain"),user.getFcm_token());
    try {
        requestFile = RequestBody.create(MediaType.parse(getContentResolver().getType(selectedImage)), user.getProfile_pic());
        requestBody = RequestBody.create(MediaType.parse("text/plain"), "" + user.getProfile_pic());
    }catch (Exception e){
        e.printStackTrace();
    }
    Api_interface apiService = ApiClient.getClient().create(Api_interface.class);

    Call<User> call=apiService.registUserCall(Username,Email,City,Mobile,Password,Fcm_token,requestFile,requestBody);

    call.enqueue(new Callback<User>() {
        @Override
        public void onResponse(Call<User> call, Response<User> response) {
            if (response.body().getStatus() .equals("1")){
                Toast.makeText(RegisterActivity.this,"Response Success",Toast.LENGTH_LONG).show();

            }else
                Toast.makeText(RegisterActivity.this,"Response Other than 1",Toast.LENGTH_LONG).show();

        }

        @Override
        public void onFailure(Call<User> call, Throwable t) {

        }
    });
*/
    new AddNewProduct().execute();
    }
    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                animateRevealClose();
                break;
            case R.id.bt_go:
                user = new User();
                user.setUsername(et_username.getText().toString());
                user.setEmail(et_email_address.getText().toString());
                user.setPassword(et_password.getText().toString());
                user.setMobile(et_mobile_number.getText().toString());
                user.setCity(et_city.getText().toString());
                if (Session.getInstance(getApplicationContext()).getFcm() != null && !Session.getInstance(getApplicationContext()).getFcm().equals("")) {
                    user.setFcm_token(Session.getInstance(getApplicationContext()).getFcm());
                } else {
                    user.setFcm_token("addashd_testing_fcm_app");
                }
                if (fileUpload != null && fileUpload.length() > 0) {
                    Log.e("ChoosedImagePath", "" + fileUpload);
                    Log.e("fileSize", "" + fileUpload.length());
                    user.setProfile_pic(fileUpload);
                } else {
                    File f = new File("NoFile");

                    user.setProfile_pic(null);
                }
                updateToServer(user);
                UtilityMethods.showProgressDialog(this);
                break;
            case R.id.profile_image:
                uploadOperation();
                break;

        }




    }

    @SuppressLint("StaticFieldLeak")
    public class AddNewProduct extends AsyncTask<Void, Void, Response<JsonElement>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Email = RequestBody.create(MediaType.parse("text/plain"), user.getEmail());
            Username = RequestBody.create(MediaType.parse("text/plain"), user.getUsername());
            City = RequestBody.create(MediaType.parse("text/plain"), user.getCity());
            Mobile = RequestBody.create(MediaType.parse("text/plain"), user.getMobile());
            Password = RequestBody.create(MediaType.parse("text/plain"), user.getPassword());

            Fcm_token = RequestBody.create(MediaType.parse("text/plain"), user.getFcm_token());
            try {
                requestFile = RequestBody.create(MediaType.parse(getContentResolver().getType(selectedImage)), user.getProfile_pic());
                requestBody = RequestBody.create(MediaType.parse("text/plain"), "" + user.getProfile_pic());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Response<JsonElement> doInBackground(Void... voids) {

            Api_interface apiService = ApiClient.getClient().create(Api_interface.class);

            Call<JsonElement> call = apiService.registUserCall(Username, Email, City, Mobile, Password, Fcm_token, requestFile, requestBody);

            Response<JsonElement> response = null;
            try {
                response = call.execute();
                Log.d("AddProductApiStatus", "" + new Gson().toJson(response.body()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(Response<JsonElement> response) {
            super.onPostExecute(response);
            UtilityMethods.dismissProgressDialog();
            if (response == null)
                Toast.makeText(RegisterActivity.this, "Response Null", Toast.LENGTH_LONG).show();

            else {

                Log.d("AddProductApiStatus", "" + new Gson().toJson(response.body()));

                try {
                    JSONObject json = new JSONObject("" + new Gson().toJson(response.body()));

                    if (json.getString("message").equals("Registration successfully.") && json.getString("status").equals("1")) {
                        User user1 = new User();
                        user1.setUserid(json.getJSONObject("data").getString("user_id"));
                        user1.setUsername(user.getUsername());
                        user1.setEmail(user.getEmail());
                        user1.setMobile(user.getMobile());
                        user1.setCity(user.getCity());
                        if (fileUpload != null && fileUpload.length() > 0) {
                            user1.setProfile_pic(new File(Constants.BASE_IMAGE_PROFILE_URL + String.valueOf(fileUpload).substring(String.valueOf(fileUpload).lastIndexOf('/') + 1)));
                        } else {
                            user1.setProfile_pic(null);
                        }

                    //user1.setUsername(response_val.getJSONObject("data").getJSONObject("value").getString("username"));
                   // user1.setEmail(response_val.getJSONObject("data").getJSONObject("value").getString("email"));
                   // user1.setProfile_pic(new File("" + response_val.getString("image_url")));
                        user1.setIsLogin("true");

                        Session.getInstance(RegisterActivity.this).setLogin(user1);


                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    } else {
                        Toast.makeText(RegisterActivity.this, "" + json.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }
    }
}
