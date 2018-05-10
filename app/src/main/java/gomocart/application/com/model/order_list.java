package gomocart.application.com.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class order_list implements Parcelable {

	ArrayList<order> listData;
	
	public order_list(ArrayList<order> listData) {
		this.listData = listData;
	}
	
	@SuppressWarnings("unchecked")
	public order_list(Parcel parcel) {
		this.listData = parcel.readArrayList(null);
	}
	
	public ArrayList<order> getListData() {
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
    public static Creator<order_list> CREATOR = new Creator<order_list>() {

        @Override
        public order_list createFromParcel(Parcel source) {
            return new order_list(source);
        }

        @Override
        public order_list[] newArray(int size) {
            return new order_list[size];
        }

    };

}


