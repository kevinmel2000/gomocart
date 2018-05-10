package gomocart.application.com.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class cekorder_list implements Parcelable {

	ArrayList<cekorder> listData;
	
	public cekorder_list(ArrayList<cekorder> listData) {
		this.listData = listData;
	}
	
	@SuppressWarnings("unchecked")
	public cekorder_list(Parcel parcel) {
		this.listData = parcel.readArrayList(null);
	}
	
	public ArrayList<cekorder> getListData() {
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
    public static Creator<cekorder_list> CREATOR = new Creator<cekorder_list>() {

        @Override
        public cekorder_list createFromParcel(Parcel source) {
            return new cekorder_list(source);
        }

        @Override
        public cekorder_list[] newArray(int size) {
            return new cekorder_list[size];
        }

    };

}


