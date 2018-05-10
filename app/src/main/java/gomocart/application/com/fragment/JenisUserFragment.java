package gomocart.application.com.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;

import customfonts.MyEditText;
import customfonts.MyTextView;
import gomocart.application.com.gomocart.MainActivity;
import gomocart.application.com.gomocart.R;

public class JenisUserFragment extends Fragment {

    //public static ImageView back;
    public static MyTextView text_jenis_user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_jenis_user, container, false);

        //back = (ImageView) rootView.findViewById(R.id.back);
        text_jenis_user = (MyTextView) rootView.findViewById(R.id.text_jenis_user);

        /*back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).displayView(11);
            }
        });*/

        ((MainActivity) getActivity()).loadDataJenisUser();
		return rootView;
    }
}
