package prasad.com.gamespaisa.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

@SuppressLint("ParcelCreator")
public class AddWithdrawalTransactionModel implements Parcelable {
    String amount_value="";
    String date="";
    String time="";

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

    public String getAmount_value() {
        return amount_value;
    }

    public void setAmount_value(String amount_value) {
        this.amount_value = amount_value;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
