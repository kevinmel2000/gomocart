package gomocart.application.com.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import customfonts.MyEditText;
import customfonts.MyTextView;
import gomocart.application.com.gomocart.MainActivity;
import gomocart.application.com.gomocart.R;

public class EditProfileFragment extends Fragment {

    //public static ImageView back;
    public static MyEditText edit_first_nama;
    public static MyEditText edit_last_nama;
    public static MyEditText edit_email;
    public static MyEditText edit_phone;
    public static  MyTextView save;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        edit_first_nama = (MyEditText) rootView.findViewById(R.id.edit_firstname);
        edit_last_nama = (MyEditText) rootView.findViewById(R.id.edit_lastname);
        edit_email = (MyEditText) rootView.findViewById(R.id.edit_email);
        edit_phone  = (MyEditText) rootView.findViewById(R.id.edit_phone);
        save = (MyTextView) rootView.findViewById(R.id.save);
        /*back = (ImageView) rootView.findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).displayView(11);
            }
        });*/

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).simpanDataProfile();
            }
        });

		((MainActivity) getActivity()).loadDataEditProfile();
		return rootView;
    }
}
