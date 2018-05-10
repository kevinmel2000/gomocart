package gomocart.application.com.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alexzh.circleimageview.CircleImageView;

import customfonts.MyEditText;
import customfonts.MyTextView;
import gomocart.application.com.gomocart.MainActivity;
import gomocart.application.com.gomocart.R;

public class GantiPasswordFragment extends Fragment {

    //public static ImageView back;
    public static MyEditText edit_old_password;
    public static MyEditText edit_password;
    public static MyEditText edit_konfirmasi;

    public static  MyTextView save;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_ganti_password, container, false);

        edit_old_password = (MyEditText) rootView.findViewById(R.id.edit_old_password);
        edit_password = (MyEditText) rootView.findViewById(R.id.edit_password);
        edit_konfirmasi = (MyEditText) rootView.findViewById(R.id.edit_konfirmasi);
        save = (MyTextView) rootView.findViewById(R.id.save);
        //back = (ImageView) rootView.findViewById(R.id.back);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).simpanDataPassword();
            }
        });

        /*back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).displayView(11);
            }
        });*/

		return rootView;
    }
}
