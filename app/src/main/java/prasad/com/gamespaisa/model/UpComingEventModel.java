package prasad.com.gamespaisa.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


@SuppressLint("ParcelCreator")
public class UpComingEventModel implements Parcelable {
  /*  public UpComingEventModel(Parcel parcelable) {
    id=parcelable.readString();
        title = parcelable.readString();
        price =parcelable.readString();
        description = parcelable.readString();
        date = parcelable.readString();
        time = parcelable.readString();
        image = parcelable.readString();
        status = parcelable.readString();
        added_date = parcelable.readString();
        modified_date = parcelable.readString();

    }*/
/*
public static final Parcelable.Creator<UpComingEventModel> CREATOR=new ClassLoaderCreator<UpComingEventModel>() {
    @Override
    public UpComingEventModel createFromParcel(Parcel parcel, ClassLoader classLoader) {
        return new UpComingEventModel(parcel);
    }

    @Override
    public UpComingEventModel createFromParcel(Parcel parcel) {
        return new UpComingEventModel(parcel);
    }

    @Override
    public UpComingEventModel[] newArray(int i) {
        return new UpComingEventModel[0];
    }
};
*/
public UpComingEventModel(){

}
    public UpComingEventModel(String id, String title, String price, String description, String date, String time, String image, String status, String added_date, String modified_date) {
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

    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;

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
    @SerializedName("added_date")
    private String added_date;

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

    @SerializedName("modified_date")
    private String modified_date;
    @SerializedName("win_price")
    private String win_price;

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

    @SerializedName("extra")
    private String extra;
    @SerializedName("address")
    private String address;

    public String getIs_purchage_by_me() {
        return is_purchage_by_me;
    }

    public void setIs_purchage_by_me(String is_purchage_by_me) {
        this.is_purchage_by_me = is_purchage_by_me;
    }

    @SerializedName("total_ticket")
    private String total_ticket;
    @SerializedName("available_ticket")
    private String available_ticket;
    @SerializedName("is_purchage_by_me")
    private String is_purchage_by_me;



    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

/*        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(price);
        parcel.writeString(description);
        parcel.writeString(date);
        parcel.writeString(time);
        parcel.writeString(image);
        parcel.writeString(status);
        parcel.writeString(added_date);
        parcel.writeString(modified_date);*/
    }
}
