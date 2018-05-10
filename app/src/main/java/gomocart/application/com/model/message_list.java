package gomocart.application.com.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class message_list implements Parcelable {

	ArrayList<message> listData;
	
	public message_list(ArrayList<message> listData) {
		this.listData = listData;
	}
	
	@SuppressWarnings("unchecked")
	public message_list(Parcel parcel) {
		this.listData = parcel.readArrayList(null);
	}
	
	public ArrayList<message> getListData() {
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
    public static Creator<message_list> CREATOR = new Creator<message_list>() {

        @Override
        public message_list createFromParcel(Parcel source) {
            return new message_list(source);
        }

        @Override
        public message_list[] newArray(int size) {
            return new message_list[size];
        }

    };

}


