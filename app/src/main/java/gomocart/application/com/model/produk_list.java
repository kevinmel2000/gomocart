package gomocart.application.com.model;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class produk_list implements Parcelable {

	ArrayList<produk> listData;
	
	public produk_list(ArrayList<produk> listData) {
		this.listData = listData;
	}
	
	@SuppressWarnings("unchecked")
	public produk_list(Parcel parcel) {
		this.listData = parcel.readArrayList(null);
	}
	
	public ArrayList<produk> getListData() {
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
    public static Creator<produk_list> CREATOR = new Creator<produk_list>() {

        @Override
        public produk_list createFromParcel(Parcel source) {
            return new produk_list(source);
        }

        @Override
        public produk_list[] newArray(int size) {
            return new produk_list[size];
        }

    };

}


