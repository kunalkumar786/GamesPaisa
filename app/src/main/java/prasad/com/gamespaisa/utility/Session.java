package prasad.com.gamespaisa.utility;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import prasad.com.gamespaisa.model.User;

public class Session
{
	static SharedPreferences file;
	static SharedPreferences.Editor edit;
	
	public static final String PREFERENCE_GCM_REG_ID = "preference_gamespaisa_gcm_reg_id";
	public static final String PREFERENCE_FCM_REG_ID = "preference_gamespaisa_fcm_reg_id";
	public static final String PREFERENCE_DEVICE_ID = "preference_gamespaisa_device_id";
		public static final String PREFERENCE_GCM_ID_ADDED_STATUS = "preference_gamespaisa_gcm_id_added_status";
	public static final String ISSLIDER = "isSlider";
	public static String PREFERENCE_NAME="preference_gamespaisa_name";
	public static String PREFERENCE_LOGIN_ID="preference_gamespaisa_login_id";
	public static String PREFERENCE_PROFILE_PICTURE="preference_profile_picture";
	public static String PREFERENCE_LOGIN_EMAILID="preference_gamespaisa_login_emailid";
	public static String PREFERENCE_LOGIN_STATUS="preference_gamespaisa_login_status";
	public static String PREFERENCE_WALLET="preference_wallet";

	public static String PREFERENCE_LOGIN_NAME="preference_gamespaisa_login_name";
	public static String PREFERENCE_LOGIN_CONTACT="preference_gamespaisa_phone";
	public static String PREFERENCE_LOGIN_CITY="preference_gamespaisa_city";
	public static String PREFERENCE_LOGIN_TIME="preference_gamespaisa_login_time";
	public static String PREFERENCE_LOGIN_QBID="preference_gamespaisa_login_qbid";
	public static String PREFERENCE_LOGIN_PROFILE_PATH="preference_gamespaisa_login_profile_path";
	public static String PREFERENCE_EFFECT_SOUND="preference_gamespaisa_effect_sound";
	public static String PREFERENCE_VIBRATION="preference_gamespaisa_vibration";
	public static String PREFERENCE_PUSH_NEWS="preference_gamespaisa_push_news";
	public static String PREFERENCE_IS_REGISTER_FOR_PINWAND="preference_is_register_for_pinwand";

	public static String PREFERENCE_FILTER_IS_ACTIVE="preference_filter_gamespaisa_is_active";
	public static String PREFERENCE_FILTER_AGE_MIN="preference_filter_gamespaisa_age_min";
	public static String PREFERENCE_FILTER_AGE_MAX="preference_filter_gamespaisa_age_max";
	public static String PREFERENCE_FILTER_SIZE_MIN="preference_filter_gamespaisa_size_min";
	public static String PREFERENCE_FILTER_SIZE_MAX="preference_filter_gamespaisa_size_max";
	public static String PREFERENCE_FILTER_KG_MIN="preference_filter_gamespaisa_kg_min";
	public static String PREFERENCE_FILTER_KG_MAX="preference_filter_gamespaisa_kg_max";
	public static String PREFERENCE_FILTER_BODY="preference_filter_gamespaisa_body";
	public static String PREFERENCE_FILTER_LOOKING_FOR="preference_filter_gamespaisa_looking_for";
	public static String PREFERENCE_FILTER_RELATION_STATUS="preference_filter_gamespaisa_relation_status";
	public static String PREFERENCE_FILTER_MY_INTEREST="preference_filter_gamespaisa_my_interest";
	public static String PREFERENCE_FILTER_POSITION_ROLE="preference_filter_gamespaisa_position_role";
	public static String PREFERENCE_USER_CITY_NAME="preference_user_city_name";
	public static String PREFERENCE_USER_COUNTRY_NAME="preference_user_country_name";
	public static String PREFERENCE_FILTER_USER_CITY_NAME="preference_filter_user_city_name";

	public static String PREFERENCE_LOGIN_PIN="preference_gamespaisa_login_pin";
	public static String PREFERENCE_IS_SECURITY_ACTIVE="preference_gamespaisa_is_security_active";
	public static String PREFERENCE_IS_SECURITY_ACTIVE_STRING="is_active";
	public static String PREFERENCE_FACEBOOK_LINK="preference_gamespaisa_facebook_link";
	public static String PREFERENCE_TWITTER_LINK="preference_gamespaisa_twitter_link";
	public static String PREFERENCE_INSTAGRAM_LINK="preference_qbos_instagram_link";
	public static String PREFERENCE_FACEBOOK_ACTIVE_STATUS="preference_gamespaisa_facebook_active_status";
	public static String PREFERENCE_TWITTER_ACTIVE_STATUS="preference_gamespaisa_twitter_active_status";

	public static String PREFERENCE_INSTAGRAM_ACTIVE_STATUS="preference_gamespaisa_instagram_active_status";
	public static String PREFERENCE_CHATING_QB_IDS="preference_gamespaisa_chating_qb_ids";
	public static String PREFERENCE_GROUP_CHATING_QB_IDS="preference_gamespaisa_group_chating_qb_ids";
	public static String PREFERENCE_PINWAND_CHAT_STATUS="preference_gamespaisa_pinwand_chat_status";
	public static String PREFERENCE_PROFILE_PHOTO_UPLOADED="profile_photo_uploaded_status";
	public static String PREFERENCE_ZODIAC_SET="profile_zodiac_set";
	public static String PREFERENCE_POSITION_ROLE="profile_position_role_set";
	public static String PREFERENCE_LATITUDE="preference_gamespaisa_latitude";
	public static String PREFERENCE_TRANSLATION_LANGUAGE="translated_language";
	public static String PREFERENCE_LONGITUDE="preference_gamespaisa_longitude";
	public static String PREFERENCE_NOTIFICATION_COUNT="preference_gamespaisa_notification_count";
	public static String PREFERENCE_STAR_PAYMENT_DETAIL ="star_payment_detail";
	public static String PREFERENCE_STAR_INCALL_NAME ="incall_name";
	public static String PREFERENCE_STAR_INCALL_PRICE ="incall_price";
	public static String PREFERENCE_STAR_INCALL_CURRENCY ="incall_currency";
	public static String PREFERENCE_STAR_OUTCALL_NAME ="outcall_name";
	public static String PREFERENCE_STAR_OUTCALL_PRICE="outcall_price";
	public static String PREFERENCE_STAR_OUTCALL_CURRENCY ="outcall_currency";
	public static String PREFERENCE_STORE_INVISIBILITY="store_invisibility";
	public static String PREFERENCE_USER_ROLE="user_role";

	public static String PREFERENCE_USER_LOGIN_DATAS_VALUE="user_login_data_value";
	public static String PREFERENCE_FRIEND_REQUEST_COUNT="friend_request_count";
	public static String PREFERENCE_RELATIONSHIP_REQUEST_COUNT="relationship_reqst_count";
	static Session session=null;
	static Set<String> set = new HashSet<>();

	public  boolean isPinwandActive() {
		return file.getBoolean(PREFERENCE_PINWAND_CHAT_STATUS, true);
	}

	public  void setPinwandStatus(boolean preferencePushNews) {
		edit.putBoolean(PREFERENCE_PINWAND_CHAT_STATUS, preferencePushNews);
		edit.commit();
	}
	public  boolean isProfilePhotoUploaded() {
		return file.getBoolean(PREFERENCE_PROFILE_PHOTO_UPLOADED, true);
	}

	public  void setProfilePhotoUploaded(boolean preferencePushNews) {
		edit.putBoolean(PREFERENCE_PROFILE_PHOTO_UPLOADED, preferencePushNews);
		edit.commit();
	}
	public  void setProfileZodiac(boolean is_zodiac_set) {
		edit.putBoolean(PREFERENCE_ZODIAC_SET, is_zodiac_set);
		edit.commit();
	}public  boolean isZodiacSet() {
	return file.getBoolean(PREFERENCE_ZODIAC_SET, true);
	}
	public  void setPositionRole(boolean is_position_role_set) {
		edit.putBoolean(PREFERENCE_POSITION_ROLE, is_position_role_set);
		edit.commit();
	}
	public  boolean isPositionRole() {
		return file.getBoolean(PREFERENCE_POSITION_ROLE, true);
	}

	/*public void saveUserLoginData(String username,String profile_picture,String user_id,String password){
		edit.putStringSet()

		PREFERENCE_USER_LOGIN_DATAS
	}*/

	public void setWalletValue(String wallet){
		edit.putString(PREFERENCE_WALLET,wallet);
	}
	public String getWallet(){
		return file.getString(PREFERENCE_WALLET, "");
	}

	public void setLatitude(String pin)
	{
		edit.putString(PREFERENCE_LATITUDE, pin);
		edit.commit();
	}
	public String getLatitude()
	{
		return file.getString(PREFERENCE_LATITUDE, "0");
	}
	public void setInvisibility(String invisibility){
		edit.putString(PREFERENCE_STORE_INVISIBILITY,invisibility);
		edit.apply();
	}
	public String getLoginTime()
	{
		return file.getString(PREFERENCE_LOGIN_TIME, "0");
	}
	public void setLoginTime(String time)
	{
		edit.putString(PREFERENCE_LOGIN_TIME, time);
		edit.commit();
	}
	public String getInvisibility(){
		return file.getString(PREFERENCE_STORE_INVISIBILITY,"0");
	}

	public static void setTranslationLanguage(String language)
	{
		edit.putString(PREFERENCE_TRANSLATION_LANGUAGE, language);
		edit.commit();
	}
	public static void setCurrentCityandCountry(String city, String country)
	{
		edit.putString(PREFERENCE_USER_CITY_NAME, city);
		edit.putString(PREFERENCE_USER_COUNTRY_NAME,country);
		edit.commit();
	}
	public static String getCurrentCity()
	{
		return file.getString(PREFERENCE_USER_CITY_NAME, "0");
	}
	public void setProfilePicture(String image){
		edit.putString(PREFERENCE_PROFILE_PICTURE, image);
		edit.commit();
	}
	public static String getProfilePicture()
	{
		return file.getString(PREFERENCE_PROFILE_PICTURE, "0");

	}

	public static String getCurrentCountry()
	{
		return file.getString(PREFERENCE_USER_COUNTRY_NAME, "0");
	}

	public static String getTranslationLanguage()
	{
		return file.getString(PREFERENCE_TRANSLATION_LANGUAGE, "0");
	}
	public void setLNotificationCount(int pin)
	{
		edit.putInt(PREFERENCE_NOTIFICATION_COUNT, pin);
		edit.commit();
	}
	public int getLNotificationCount()
	{
		return file.getInt(PREFERENCE_NOTIFICATION_COUNT, 0);
	}
	public void setLongitude(String pin)
	{
		edit.putString(PREFERENCE_LONGITUDE, pin);
		edit.commit();
	}
	public String getLongitude()
	{
		return file.getString(PREFERENCE_LONGITUDE, "0");
	}
	public void setGroupChatingIds(String pin)
	{
	Log.e("IN_THE_SESSION","SESSION");
	Log.e("IN_THE_SESSION",pin);
		edit.putString(PREFERENCE_GROUP_CHATING_QB_IDS, pin);
		edit.commit();
	}
	public String getGroupChatingIds()
	{
		return file.getString(PREFERENCE_GROUP_CHATING_QB_IDS, "");
	}
	public void setChatingIds(String pin)
	{
		edit.putString(PREFERENCE_CHATING_QB_IDS, pin);
		edit.commit();
	}
	public String getChatingIds()
	{
		return file.getString(PREFERENCE_CHATING_QB_IDS, "");
	}

	public void setFacebookLink(String facebook_link, String is_active)
	{

		edit.putString(PREFERENCE_FACEBOOK_LINK, facebook_link);
		edit.putString(PREFERENCE_FACEBOOK_ACTIVE_STATUS, is_active);
		edit.commit();
	}
	public void setTwitterLink(String twitter_link, String is_active)
	{

		edit.putString(PREFERENCE_TWITTER_LINK, twitter_link);
		edit.putString(PREFERENCE_TWITTER_ACTIVE_STATUS, is_active);
		edit.commit();
	}
	public void setInstagramLink(String instagram_link, String is_active)
	{

		edit.putString(PREFERENCE_INSTAGRAM_LINK, instagram_link);
		edit.putString(PREFERENCE_INSTAGRAM_ACTIVE_STATUS, is_active);
		edit.commit();
	}
	public void setSliderApply(Boolean isslider)
	{
		edit.putBoolean(ISSLIDER, isslider);
		edit.commit();
	}

	public static void saveUserLoginData(ArrayList<String> user_data) {
		set.addAll(user_data);
		//ExtraData
		edit.putStringSet(PREFERENCE_USER_LOGIN_DATAS_VALUE, set);
		edit.apply();
	}

	public static Set<String> getuserData(){
		return file.getStringSet(PREFERENCE_USER_LOGIN_DATAS_VALUE,null);
	}


	public void setPin(String pin)
	{

		edit.putString(PREFERENCE_LOGIN_PIN, pin);
		edit.commit();

	}
	public String getPin()
	{
		return file.getString(PREFERENCE_LOGIN_PIN, "");
	}
	public  boolean isSecurityActive() {
		if(getPin().equals(""))
		{
			return false;
		}

		return file.getBoolean(PREFERENCE_IS_SECURITY_ACTIVE, false);
	}public String isSecurityActiveString() {
		if(getPin().equals(""))
		{
			return "false";
		}

		return file.getString(PREFERENCE_IS_SECURITY_ACTIVE_STRING, "");
	}

	public  void setSecurityActive(boolean preferencePushNews) {
		edit.putBoolean(PREFERENCE_IS_SECURITY_ACTIVE, preferencePushNews);
		edit.commit();
	}
	public  void setSecurityActiveString(String preferencePushNews) {
		edit.putString(PREFERENCE_IS_SECURITY_ACTIVE_STRING, preferencePushNews);
		Log.e("SECURITY_STATUS",preferencePushNews);
		edit.commit();
	}
	public  boolean isRegisterForPinwand() {
		return file.getBoolean(PREFERENCE_IS_REGISTER_FOR_PINWAND, false);
	}

	public  void setRegisterForPinwand(boolean preferencePushNews) {
		edit.putBoolean(PREFERENCE_IS_REGISTER_FOR_PINWAND, preferencePushNews);
		edit.commit();
	}

	public  boolean isFilterActive() {
		return file.getBoolean(PREFERENCE_FILTER_IS_ACTIVE, false);
	}

	public  void setFilterStatus(boolean preferencePushNews) {
		edit.putBoolean(PREFERENCE_FILTER_IS_ACTIVE, preferencePushNews);
		edit.putString(PREFERENCE_FILTER_AGE_MIN, "");
		edit.putString(PREFERENCE_FILTER_AGE_MAX, "");
		edit.putString(PREFERENCE_FILTER_SIZE_MIN, "");
		edit.putString(PREFERENCE_FILTER_SIZE_MAX, "");
		edit.putString(PREFERENCE_FILTER_KG_MIN, "");
		edit.putString(PREFERENCE_FILTER_KG_MAX, "");
		edit.putString(PREFERENCE_FILTER_BODY, "");
		edit.putString(PREFERENCE_FILTER_LOOKING_FOR, "");
		edit.putString(PREFERENCE_FILTER_RELATION_STATUS,"");
		edit.putString(PREFERENCE_FILTER_MY_INTEREST, "");
		edit.putString(PREFERENCE_FILTER_POSITION_ROLE,"");
		edit.commit();
	}




	public  boolean getPreferencePushNews() {
		return file.getBoolean(PREFERENCE_PUSH_NEWS, true);
	}

	public  void setPreferencePushNews(boolean preferencePushNews) {
		edit.putBoolean(PREFERENCE_PUSH_NEWS, preferencePushNews);
		edit.commit();
	}

	public  boolean getPreferenceVibration() {
		return file.getBoolean(PREFERENCE_VIBRATION, true);

	}

	public  void setPreferenceVibration(boolean preferenceVibration) {
		edit.putBoolean(PREFERENCE_VIBRATION, preferenceVibration);
		edit.commit();
	}

	public  boolean getPreferenceEffectSound() {
		return file.getBoolean(PREFERENCE_EFFECT_SOUND, true);

	}

	public  void setPreferenceEffectSound(boolean preferenceEffectSound) {
		edit.putBoolean(PREFERENCE_EFFECT_SOUND, preferenceEffectSound);
		edit.commit();
	}

	public static Session getInstance(Context context)
	{
		try {
			file = PreferenceManager
					.getDefaultSharedPreferences(context);
			edit = file.edit();
			if (session == null)
				session = new Session();
		}catch(Exception e){
			e.printStackTrace();
		}
		return session;
	}
	
	
	public boolean isLogin()
	{
		return file.getBoolean(PREFERENCE_LOGIN_STATUS, false);
		
	}
	
	public void setLogin()
	{
		edit.putBoolean(PREFERENCE_LOGIN_STATUS, true);
		
		edit.commit();
	}
	
	public void setLogin(String login_id)
	{
		edit.putBoolean(PREFERENCE_LOGIN_STATUS, true);
		edit.putString(PREFERENCE_LOGIN_ID, login_id);
		edit.commit();
	}
	public void setLogin(User user){
		edit.putString(PREFERENCE_LOGIN_ID,user.getUserid());
		edit.putString(PREFERENCE_LOGIN_STATUS,user.getIsLogin());
		edit.putString(PREFERENCE_LOGIN_NAME,user.getUsername());
		edit.putString(PREFERENCE_LOGIN_CONTACT,user.getMobile());
		edit.putString(PREFERENCE_LOGIN_CITY,user.getCity());
		edit.putString(PREFERENCE_LOGIN_EMAILID,user.getEmail());
		edit.putString(PREFERENCE_LOGIN_PROFILE_PATH,""+user.getProfile_pic());
		edit.commit();
	}
public User getLogin(){
		User user=new User();
		user.setUserid(file.getString(PREFERENCE_LOGIN_STATUS,""));
		user.setEmail(file.getString(PREFERENCE_LOGIN_EMAILID,""));
		user.setProfile_pic(new File(file.getString(PREFERENCE_LOGIN_PROFILE_PATH, "")));
		user.setUsername(file.getString(PREFERENCE_LOGIN_NAME, ""));
		user.setCity(file.getString(PREFERENCE_LOGIN_CITY, ""));
		user.setMobile(file.getString(PREFERENCE_LOGIN_CONTACT, ""));
		return user;
	}


	public void setLogin(String login_id, String email_id)
	{
		edit.putBoolean(PREFERENCE_LOGIN_STATUS, true);
		edit.putString(PREFERENCE_LOGIN_ID, login_id);
		edit.putString(PREFERENCE_LOGIN_EMAILID, email_id);
		edit.commit();
	}
	
	public void setLogin(String login_id, String email_id, String login_type)
	{
		edit.putBoolean(PREFERENCE_LOGIN_STATUS, true);
		edit.putString(PREFERENCE_LOGIN_ID, login_id);
		edit.putString(PREFERENCE_LOGIN_EMAILID, email_id);

		edit.commit();
	}
	public void updateUserRole_Type(String type, String role){

		edit.putString(PREFERENCE_USER_ROLE, role);
		edit.apply();
	}

	public String getLoginId()
	{
		return file.getString(PREFERENCE_LOGIN_ID, "");
	}
	
	public String getLoginEmailId()
	{
		return file.getString(PREFERENCE_LOGIN_EMAILID, "");
	}
	

	public String getLoginUserName()
	{
		return file.getString(PREFERENCE_LOGIN_NAME, "");
	}
	public void setLoginLoginType(String usertype)
	{

		edit.commit();
	}
	public void setUserRoleExpiryDate(String expiry)
	{
		edit.putString(PREFERENCE_USER_ROLE, expiry);
		edit.commit();
	}

	public String getLoginQbid()
	{
		return file.getString(PREFERENCE_LOGIN_QBID, "");
	}
	public String getProfilePath()
	{
		return file.getString(PREFERENCE_LOGIN_PROFILE_PATH, "");
	}
	public void setLogout()
	{



		edit.putString(PREFERENCE_LOGIN_EMAILID, "");
		edit.putString(PREFERENCE_LOGIN_ID, "");
		edit.putString(PREFERENCE_LOGIN_CITY, "");
		edit.putString(PREFERENCE_LOGIN_CITY, "");
		edit.putString(PREFERENCE_LOGIN_CONTACT, "");
		edit.putString(PREFERENCE_LOGIN_QBID, "");
		edit.putString(PREFERENCE_LOGIN_NAME, "");
		edit.putString(PREFERENCE_LOGIN_PROFILE_PATH, "");
		edit.putString(PREFERENCE_USER_ROLE, "");
		edit.putString(PREFERENCE_LATITUDE, "");
		edit.putString(PREFERENCE_LONGITUDE, "");
		edit.putString(PREFERENCE_USER_CITY_NAME, "");
		edit.putBoolean(PREFERENCE_LOGIN_STATUS, false);
		edit.putBoolean(PREFERENCE_IS_REGISTER_FOR_PINWAND, false);
		edit.putBoolean(PREFERENCE_FILTER_IS_ACTIVE, false);
		edit.putBoolean(PREFERENCE_IS_SECURITY_ACTIVE, false);
		edit.putBoolean(PREFERENCE_FACEBOOK_ACTIVE_STATUS, false);
		edit.putBoolean(PREFERENCE_INSTAGRAM_ACTIVE_STATUS, false);
		edit.putBoolean(PREFERENCE_EFFECT_SOUND, true);
		edit.putBoolean(PREFERENCE_VIBRATION, true);
		edit.putBoolean(PREFERENCE_PUSH_NEWS, true);
		edit.putString(PREFERENCE_FACEBOOK_LINK, "");
		edit.putString(PREFERENCE_INSTAGRAM_LINK, "");
		edit.putString(PREFERENCE_LOGIN_PIN, "");
		edit.putString(PREFERENCE_CHATING_QB_IDS,"");
		edit.putInt(PREFERENCE_NOTIFICATION_COUNT, 0);
		edit.putString(PREFERENCE_GROUP_CHATING_QB_IDS,"");
		edit.putBoolean(PREFERENCE_PINWAND_CHAT_STATUS, true);
		edit.putString(PREFERENCE_FILTER_AGE_MIN, "");
		edit.putString(PREFERENCE_FRIEND_REQUEST_COUNT, "");
		edit.putString(PREFERENCE_RELATIONSHIP_REQUEST_COUNT, "");
		edit.putString(PREFERENCE_FILTER_AGE_MAX, "");
		edit.putString(PREFERENCE_FILTER_SIZE_MIN, "");
		edit.putString(PREFERENCE_FILTER_SIZE_MAX, "");
		edit.putString(PREFERENCE_FILTER_KG_MIN, "");
		edit.putString(PREFERENCE_FILTER_KG_MAX, "");
		edit.putString(PREFERENCE_FILTER_BODY, "");
		edit.putString(PREFERENCE_FILTER_LOOKING_FOR, "");
		edit.putString(PREFERENCE_FILTER_RELATION_STATUS,"");
		edit.putString(PREFERENCE_FILTER_MY_INTEREST, "");
		edit.putString(PREFERENCE_FILTER_POSITION_ROLE,"");
		edit.putString(PREFERENCE_STORE_INVISIBILITY,"");
		edit.commit();
	}
	public void saveQbID(String id)
	{
		edit.putString(PREFERENCE_LOGIN_QBID, id);
		edit.commit();
	}
	public void setProfilePath(String id)
	{
		edit.putString(PREFERENCE_LOGIN_PROFILE_PATH, id);
		edit.commit();
	}
	public void saveGcmID(String id)
	{
		edit.putString(PREFERENCE_GCM_REG_ID,id);
		edit.commit();
	}
	public void saveFcmID(String id)
	{
		edit.putString(PREFERENCE_FCM_REG_ID,id);
		edit.commit();
	}


	public void saveDeviceID(String id)
	{
		edit.putString(PREFERENCE_DEVICE_ID,id);
		edit.commit();
	}
	public String getGcmId()
	{
		return file.getString(PREFERENCE_GCM_REG_ID, "");
	}
	public String getFcm()
	{
		return file.getString(PREFERENCE_FCM_REG_ID, "");
	}
	public String getDeviceId(){
		return file.getString(PREFERENCE_DEVICE_ID,"");
	}
	public void saveGcmIDAddedStatus(String id)
	{
		edit.putString(PREFERENCE_GCM_ID_ADDED_STATUS,id);
		edit.commit();
	}
	public String getGcmIdAddedStatus()
	{
		return file.getString(PREFERENCE_GCM_ID_ADDED_STATUS, "");
	}

    public void setFriendRequestCount(String friendRequestCount) {
		edit.putString(PREFERENCE_FRIEND_REQUEST_COUNT,friendRequestCount);
		edit.commit();
    }
    public String getPreferenceFriendRequestCount(){
		return file.getString(PREFERENCE_FRIEND_REQUEST_COUNT,"");
	}
	public void setRelationshipRequestCount(String relationshipRequestCount) {
		edit.putString(PREFERENCE_RELATIONSHIP_REQUEST_COUNT,relationshipRequestCount);
		edit.commit();
    }
    public String getRelationshipRequestCount(){
		return file.getString(PREFERENCE_RELATIONSHIP_REQUEST_COUNT,"");
	}



}
