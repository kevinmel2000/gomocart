package gomocart.application.com.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class notifikasi_list implements Parcelable {

	ArrayList<notifikasi> listData;
	
	public notifikasi_list(ArrayList<notifikasi> listData) {
		this.listData = listData;
	}
	
	@SuppressWarnings("unchecked")
	public notifikasi_list(Parcel parcel) {
		this.listData = parcel.readArrayList(null);
	}
	
	public ArrayList<notifikasi> getListData() {
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
    public static Creator<notifikasi_list> CREATOR = new Creator<notifikasi_list>() {

        @Override
        public notifikasi_list createFromParcel(Parcel source) {
            return new notifikasi_list(source);
        }

        @Override
        public notifikasi_list[] newArray(int size) {
            return new notifikasi_list[size];
        }

    };

}


