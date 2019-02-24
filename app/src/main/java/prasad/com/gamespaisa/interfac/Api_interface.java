package prasad.com.gamespaisa.interfac;

import com.google.gson.JsonElement;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import prasad.com.gamespaisa.model.EventModel;
import prasad.com.gamespaisa.model.User;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Api_interface {

@POST("register")
@Multipart
    Call<JsonElement>registUserCall(@Part("username")RequestBody username, @Part("email_id")RequestBody email_id, @Part("city")RequestBody city
        , @Part("mobile_number")RequestBody mobile_number, @Part("password")RequestBody password
        , @Part("fcm_token")RequestBody fcm_token,@Part("image\"; filename=\"myfile.jpg+\"") RequestBody file, @Part("desc") RequestBody desc);

@POST("update_profile")
@Multipart
    Call<JsonElement>updateProfileData(@Part("user_id")RequestBody user_id,@Part("username")RequestBody username, @Part("email_id")RequestBody email_id, @Part("city")RequestBody city
        , @Part("mobile_number")RequestBody mobile_number, @Part("password")RequestBody password
        , @Part("fcm_token")RequestBody fcm_token,@Part("image\"; filename=\"myfile.jpg\" ") RequestBody file, @Part("desc") RequestBody desc);

@FormUrlEncoded
@POST("login")
Call<JsonElement>loginCall(@Field("username")String username,@Field("password")String password,@Field("fcm_token")String fcm_token);

@FormUrlEncoded
@POST("get_event_list")
Call<JsonElement> getEvent(@Field("user_id")String user_id);


@GET("event_purchase")
    Call<JsonElement>updatePurchase(@Field("user_id")String user_id,@Field("event_id")String event_id,@Field("transaction_id")String transaction_id);

@FormUrlEncoded
    @POST("get_event_purchase_user")
    Call<JsonElement> getParticipantList(@Field("user_id")String user_id,@Field("event_id")String event_id);


}
