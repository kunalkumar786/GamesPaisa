package prasad.com.gamespaisa.fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.accountkit.AccountKitLoginResult;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import prasad.com.gamespaisa.MainActivity;
import prasad.com.gamespaisa.R;
import prasad.com.gamespaisa.interfac.Api_interface;
import prasad.com.gamespaisa.material.RegisterActivity;
import prasad.com.gamespaisa.model.User;
import prasad.com.gamespaisa.utility.Constants;
import prasad.com.gamespaisa.utility.Session;
import prasad.com.gamespaisa.utility.UtilityMethods;
import prasad.com.gamespaisa.web_service.ApiClient;
import retrofit2.Call;
import retrofit2.Response;

public class ProfileFragment extends Fragment implements View.OnClickListener {
    User user;
    TextView tv_update;
    EditText et_username,et_email,et_contact,et_city,et_user_id;
    CircleImageView cv_profile_picture;
    File fileUpload = null;
    RequestBody User_ID,Email, Username,Password, Mobile, City, Fcm_token,requestFile,requestBody;
    Uri selectedImage;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        ((MainActivity) getActivity()).setTitle("Profile");
        et_username=(EditText)view.findViewById(R.id.et_username);
        et_email=(EditText)view.findViewById(R.id.et_email);
        et_contact=(EditText)view.findViewById(R.id.et_contact);
        et_city=(EditText)view.findViewById(R.id.et_city);
        tv_update=(TextView) view.findViewById(R.id.tv_update);
        et_user_id=(EditText)view.findViewById(R.id.et_user_id);
        cv_profile_picture=(CircleImageView)view.findViewById(R.id.cv_profile_picture);
        user= Session.getInstance(getActivity()).getLogin();
        et_username.setText(user.getUsername());
        user.setUserid(user.getUserid());
        user.setFcm_token(Session.getInstance(getActivity()).getFcm());
        et_email.setText(user.getEmail());
        et_contact.setText(user.getMobile());
        et_city.setText(user.getCity());
        et_user_id.setText(Session.getInstance(getActivity()).getLoginId());
        et_user_id.setEnabled(false);
        //((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cv_profile_picture.setOnClickListener(this);
        tv_update.setOnClickListener(this);
        return view;
    }
    public void uploadOperation(){
        final CharSequence[] items = {"Photo", "Gallery","Cancel" };

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(
                getActivity());
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

       if (requestCode == Constants.SELECT_FILE)
        {
            fileUpload= UtilityMethods.onSelectFromGalleryResult(getActivity(),data);
            selectedImage = data.getData();
            // tv_add_photo_video.setText(""+fileUpload);
            Bitmap myBitmap = BitmapFactory.decodeFile(fileUpload.getAbsolutePath());
            cv_profile_picture.setImageBitmap(myBitmap);

        } else if (requestCode == Constants.REQUEST_CAMERA)
        {
            fileUpload= UtilityMethods.onCaptureImageResult(data);
            selectedImage = data.getData();
            //    tv_add_photo_video.setText(""+fileUpload);
            Bitmap myBitmap = BitmapFactory.decodeFile(fileUpload.getAbsolutePath());
            cv_profile_picture.setImageBitmap(myBitmap);

        }
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.cv_profile_picture:
             uploadOperation();
                break;
            case R.id.tv_update:
               new AddNewProduct().execute();
                break;
        }
    }

    @SuppressLint("StaticFieldLeak")
    public class AddNewProduct extends AsyncTask<Void, Void, Response<JsonElement>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Email=RequestBody.create(MediaType.parse("text/plain"),et_email.getText().toString());
            Username=RequestBody.create(MediaType.parse("text/plain"),et_username.getText().toString());
            City=RequestBody.create(MediaType.parse("text/plain"),et_city.getText().toString());
            Mobile=RequestBody.create(MediaType.parse("text/plain"),et_city.getText().toString());
            Password=RequestBody.create(MediaType.parse("text/plain"),user.getPassword());
            User_ID=RequestBody.create(MediaType.parse("text/plain"),user.getUserid());
            Fcm_token=RequestBody.create(MediaType.parse("text/plain"),user.getFcm_token());
            try {
                if (fileUpload != null && fileUpload.length() > 0) {
                    Log.e("ChoosedImagePath", "" + fileUpload);
                    Log.e("fileSize", "" + fileUpload.length());
                    user.setProfile_pic(fileUpload);
                }
                requestFile = RequestBody.create(MediaType.parse(getActivity().getContentResolver().getType(selectedImage)), user.getProfile_pic());
                requestBody = RequestBody.create(MediaType.parse("text/plain"), "" + user.getProfile_pic());
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        protected Response<JsonElement> doInBackground(Void... voids) {

            Api_interface apiService = ApiClient.getClient().create(Api_interface.class);

            Call<JsonElement> call=apiService.updateProfileData(User_ID,Username,Email,City,Mobile,Password,Fcm_token,requestFile,requestBody);

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
                Toast.makeText(getActivity(),"Response Null",Toast.LENGTH_LONG).show();

            else {

                Log.d("AddProductApiStatus", "" + new Gson().toJson(response.body()));

                try {
                    JSONObject json=new JSONObject(""+new Gson().toJson(response.body()));

                    if(json.getString("message").equals("Profile update successfully.")&&json.getString("status").equals("1")){
                        User user1=new User();
                       // user1.setUserid(json.getJSONObject("data").getString("user_id"));
                        user1.setUsername(user.getUsername());
                        user1.setEmail(user.getEmail());
                        user1.setMobile(user.getMobile());
                        if(fileUpload!=null&&fileUpload.length()>0){
                            user1.setProfile_pic(new File(Constants.BASE_IMAGE_PROFILE_URL + String.valueOf(fileUpload).substring(String.valueOf(fileUpload).lastIndexOf('/') + 1)));
                        }else{
                            user1.setProfile_pic(null);
                        }

                    //user1.setUsername(response_val.getJSONObject("data").getJSONObject("value").getString("username"));
                    //user1.setEmail(response_val.getJSONObject("data").getJSONObject("value").getString("email"));
                    //user1.setProfile_pic(new File("" + response_val.getString("image_url")));
                        user1.setIsLogin("true");

                        Session.getInstance(getActivity()).setLogin(user1);


                        startActivity(new Intent(getActivity(), MainActivity.class));
                    }else{
                        Toast.makeText(getActivity(),""+json.getString("message"),Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }
    }



}
