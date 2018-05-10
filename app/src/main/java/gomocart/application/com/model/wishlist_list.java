package gomocart.application.com.model;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class wishlist_list implements Parcelable {

	ArrayList<wishlist> listData;
	
	public wishlist_list(ArrayList<wishlist> listData) {
		this.listData = listData;
	}
	
	@SuppressWarnings("unchecked")
	public wishlist_list(Parcel parcel) {
		this.listData = parcel.readArrayList(null);
	}
	
	public ArrayList<wishlist> getListData() {
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
    public static Creator<wishlist_list> CREATOR = new Creator<wishlist_list>() {

        @Override
        public wishlist_list createFromParcel(Parcel source) {
            return new wishlist_list(source);
        }

        @Override
        public wishlist_list[] newArray(int size) {
            return new wishlist_list[size];
        }

    };

}


