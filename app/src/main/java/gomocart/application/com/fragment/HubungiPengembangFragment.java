package gomocart.application.com.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import customfonts.MyEditText;
import customfonts.MyTextView;
import gomocart.application.com.gomocart.MainActivity;
import gomocart.application.com.gomocart.R;

public class HubungiPengembangFragment extends Fragment {

    public static MyEditText edit_nama;
    public static MyEditText edit_email;
    public static MyEditText edit_pesan;
    public static  MyTextView kirim;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_hubungi_pengembang, container, false);

        edit_nama = (MyEditText) rootView.findViewById(R.id.edit_nama);
        edit_email = (MyEditText) rootView.findViewById(R.id.edit_email);
        edit_pesan = (MyEditText) rootView.findViewById(R.id.edit_pesan);
        kirim = (MyTextView) rootView.findViewById(R.id.send);

        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).emailPengembang();
            }
        });

		return rootView;
    }
}
