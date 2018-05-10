package gomocart.application.com.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class perpesanan_list implements Parcelable {

	ArrayList<perpesanan> listData;
	
	public perpesanan_list(ArrayList<perpesanan> listData) {
		this.listData = listData;
	}
	
	@SuppressWarnings("unchecked")
	public perpesanan_list(Parcel parcel) {
		this.listData = parcel.readArrayList(null);
	}
	
	public ArrayList<perpesanan> getListData() {
		return this.listData;
	}
	
	@Override
    public int describeContents() {
        return 0;
    }

    // Required method to write to Parcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(listData);
    }

    // Method to recreate a Question from a Parcel
    public static Creator<perpesanan_list> CREATOR = new Creator<perpesanan_list>() {

        @Override
        public perpesanan_list createFromParcel(Parcel source) {
            return new perpesanan_list(source);
        }

        @Override
        public perpesanan_list[] newArray(int size) {
            return new perpesanan_list[size];
        }

    };

}


