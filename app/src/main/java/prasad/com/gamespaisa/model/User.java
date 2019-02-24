package prasad.com.gamespaisa.model;

import android.text.TextUtils;
import android.util.Patterns;

import java.io.File;

public class User {
    String Email="";
    String Password="";
    String Mobile="";
    String City="";
    String Fcm_token="";
    String Username="";
    String Status="";

    public String getUserid() {
        return Userid;
    }

    public void setUserid(String userid) {
        Userid = userid;
    }

    public String getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(String isLogin) {
        this.isLogin = isLogin;
    }

    String Userid="";
    String isLogin="";
    File profile_pic=null;
    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }


    public String getFcm_token() {
        return Fcm_token;
    }

    public void setFcm_token(String fcm_token) {
        Fcm_token = fcm_token;
    }

    public User() {
    }



 public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public File getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(File profile_pic) {
        this.profile_pic = profile_pic;
    }

  public User(String email, String password) {
        Email = email;
        Password = password;
    }
  public String getEmail() {
        return Email;
    }

  public void setEmail(String email) {
        Email = email;
    }

  public String getPassword() {
        return Password;
    }

  public void setPassword(String password) {
        Password = password;
    }

    public boolean isInputDataValid() {
        return !TextUtils.isEmpty(getEmail()) && Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches() && getPassword().length() > 5;
    }


}
