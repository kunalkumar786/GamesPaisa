package prasad.com.gamespaisa.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

@SuppressLint("ParcelCreator")
public class OnGoingEventModel implements Parcelable {
@SerializedName("id")
    private String id;

    public OnGoingEventModel(String id, String title, String price, String description, String date, String time, String image, String status, String added_date, String modified_date) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.description = description;
        this.date = date;
        this.time = time;
        this.image = image;
        this.status = status;
        this.added_date = added_date;
        this.modified_date = modified_date;
    }

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

    @SerializedName("title")
    private String title;
@SerializedName("price")
private String price;
@SerializedName("description")
private String description;
@SerializedName("date")
private String date;
@SerializedName("time")
private String time;
@SerializedName("image")
 private String image;
@SerializedName("status")
 private String status;

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

    @SerializedName("added_date")
 private String added_date;
@SerializedName("modified_date")
 private String modified_date;
    @SerializedName("win_price")
    private String win_price;
    @SerializedName("extra")
    private String extra;

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    @SerializedName("address")
    private String address;
    @SerializedName("video_url")
    private String video_url;
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
