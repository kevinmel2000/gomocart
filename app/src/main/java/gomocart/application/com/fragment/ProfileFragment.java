package gomocart.application.com.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.alexzh.circleimageview.CircleImageView;

import customfonts.MyEditText;
import customfonts.MyTextView;
import gomocart.application.com.gomocart.MainActivity;
import gomocart.application.com.gomocart.R;

public class ProfileFragment extends Fragment {

    LinearLayout menu_edit_profile, menu_alamat, menu_kelola_notifikasi, menu_katasandi, menu_jenis_user;
    public static MyTextView name;
    public static CircleImageView image_profile;
    public static  MyTextView upload;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        menu_edit_profile = (LinearLayout) rootView.findViewById(R.id.menu_edit_profil);
        menu_alamat = (LinearLayout) rootView.findViewById(R.id.menu_alamat);
        menu_kelola_notifikasi = (LinearLayout) rootView.findViewById(R.id.menu_kelola_notifikasi);
        menu_katasandi = (LinearLayout) rootView.findViewById(R.id.menu_katasandi);
        menu_jenis_user = (LinearLayout) rootView.findViewById(R.id.menu_jenis_user);
        image_profile = (CircleImageView) rootView.findViewById(R.id.image_user_profile);
        name = (MyTextView) rootView.findViewById(R.id.name);
        upload = (MyTextView) rootView.findViewById(R.id.upload);

        image_profile.setLayerType(View.LAYER_TYPE_HARDWARE, null);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).selectImage();
            }
        });

        menu_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).displayView(16);
            }
        });

        menu_alamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).displayView(15);
            }
        });

        menu_kelola_notifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).displayView(12);
            }
        });

        menu_katasandi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).displayView(17);
            }
        });

        menu_jenis_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).displayView(18);
            }
        });

		((MainActivity) getActivity()).loadDataProfile();
		return rootView;
    }
}
