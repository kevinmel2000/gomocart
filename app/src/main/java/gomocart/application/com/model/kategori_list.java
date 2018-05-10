package gomocart.application.com.model;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class kategori_list implements Parcelable {

	ArrayList<kategori> listData;
	
	public kategori_list(ArrayList<kategori> listData) {
		this.listData = listData;
	}
	
	@SuppressWarnings("unchecked")
	public kategori_list(Parcel parcel) {
		this.listData = parcel.readArrayList(null);
	}
	
	public ArrayList<kategori> getListData() {
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
    public static Creator<kategori_list> CREATOR = new Creator<kategori_list>() {

        @Override
        public kategori_list createFromParcel(Parcel source) {
            return new kategori_list(source);
        }

        @Override
        public kategori_list[] newArray(int size) {
            return new kategori_list[size];
        }

    };

}


