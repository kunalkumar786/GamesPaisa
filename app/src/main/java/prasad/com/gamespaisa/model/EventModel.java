package prasad.com.gamespaisa.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

@SuppressLint("ParcelCreator")
public class EventModel implements Parcelable {

     private String id ="";
    private String title = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAdded_date() {
        return added_date;
    }

    public void setAdded_date(String added_date) {
        this.added_date = added_date;
    }

    public String getModified_date() {
        return modified_date;
    }

    public void setModified_date(String modified_date) {
        this.modified_date = modified_date;
    }

    private String price = "";
    private String description = "";
    private String date = "";
    private String time = "";
    private String image = "";

    public String getWin_price() {
        return win_price;
    }

    public void setWin_price(String win_price) {
        this.win_price = win_price;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private String status = "";
    private String added_date = "";
    private String modified_date = "";
    @SerializedName("win_price")
    private String win_price;
    @SerializedName("extra")
    private String extra;

    public String getTotal_ticket() {
        return total_ticket;
    }

    public void setTotal_ticket(String total_ticket) {
        this.total_ticket = total_ticket;
    }

    public String getAvailable_ticket() {
        return available_ticket;
    }

    public void setAvailable_ticket(String available_ticket) {
        this.available_ticket = available_ticket;
    }

    @SerializedName("address")
    private String address;

    public String getIs_purchage_by_me() {
        return is_purchage_by_me;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public void setIs_purchage_by_me(String is_purchage_by_me) {
        this.is_purchage_by_me = is_purchage_by_me;
    }

    @SerializedName("total_ticket")
    private String total_ticket;
    @SerializedName("available_ticket")
    private String available_ticket;
    @SerializedName("video_url")
    private String video_url;
    @SerializedName("is_purchage_by_me")
    private String is_purchage_by_me;
    private ArrayList<OnGoingEventModel> onGoingData=null;
    private ArrayList<UpComingEventModel> upComingEventData=null;
    private ArrayList<PastEventModel> PastEventData=null;
    public EventModel(ArrayList<OnGoingEventModel> onGoingData, ArrayList<UpComingEventModel> upComingEventData, ArrayList<PastEventModel> pastEventData) {
        this.onGoingData = onGoingData;
        this.upComingEventData = upComingEventData;
        this.PastEventData = pastEventData;
    }


    public EventModel() {
    }

    public ArrayList<OnGoingEventModel> getOnGoingData() {
        return onGoingData;
    }

    public void setOnGoingData(ArrayList<OnGoingEventModel> onGoingData) {
        this.onGoingData = onGoingData;
    }

    public ArrayList<UpComingEventModel> getUpComingEventData() {
        return upComingEventData;
    }

    public void setUpComingEventData(ArrayList<UpComingEventModel> upComingEventData) {
        this.upComingEventData = upComingEventData;
    }

    public ArrayList<PastEventModel> getPastEventData() {
        return PastEventData;
    }

    public void setPastEventData(ArrayList<PastEventModel> pastEventData) {
        PastEventData = pastEventData;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
