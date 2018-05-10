package gomocart.application.com.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class ongkir_list implements Parcelable {

	ArrayList<ongkir> listData;
	
	public ongkir_list(ArrayList<ongkir> listData) {
		this.listData = listData;
	}
	
	@SuppressWarnings("unchecked")
	public ongkir_list(Parcel parcel) {
		this.listData = parcel.readArrayList(null);
	}
	
	public ArrayList<ongkir> getListData() {
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
    public static Creator<ongkir_list> CREATOR = new Creator<ongkir_list>() {

        @Override
        public ongkir_list createFromParcel(Parcel source) {
            return new ongkir_list(source);
        }

        @Override
        public ongkir_list[] newArray(int size) {
            return new ongkir_list[size];
        }

    };

}


