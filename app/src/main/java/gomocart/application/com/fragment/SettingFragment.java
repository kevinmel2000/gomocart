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
import android.widget.LinearLayout;

import customfonts.MyEditText;
import customfonts.MyTextView;
import gomocart.application.com.gomocart.MainActivity;
import gomocart.application.com.gomocart.R;

public class SettingFragment extends Fragment {

    float downX = 0, downY = 0, upX, upY;

    //public static ImageView back;
    public static MyEditText edit_notifikasi;
    public static ImageButton drop_konfirmasi;
    public static CheckBox checkboxdefault_update_pesanan;
    public static CheckBox checkboxdefault_informasi;
    public static CheckBox checkboxdefault_notifikasi;
    public static CheckBox checkboxdefault_chat;
    public static LinearLayout linear_suara_getar;
    public static ImageButton drop_notifikasi;
    public static MyTextView save;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_setting, container, false);

        linear_suara_getar = (LinearLayout) rootView.findViewById(R.id.linear_suara_getar);
        drop_notifikasi = (ImageButton) rootView.findViewById(R.id.drop_notifikasi);
        edit_notifikasi = (MyEditText) rootView.findViewById(R.id.edit_notifikasi);
        drop_konfirmasi = (ImageButton) rootView.findViewById(R.id.drop_notifikasi);
        checkboxdefault_update_pesanan = (CheckBox) rootView.findViewById(R.id.checkboxdefault_update_pesanan);
        checkboxdefault_informasi = (CheckBox) rootView.findViewById(R.id.checkboxdefault_informasi);
        checkboxdefault_notifikasi = (CheckBox) rootView.findViewById(R.id.checkboxdefault_notifikasi);
        checkboxdefault_chat = (CheckBox) rootView.findViewById(R.id.checkboxdefault_chat);
        //back = (ImageView) rootView.findViewById(R.id.back);
        save = (MyTextView) rootView.findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).updateSetting();
            }
        });

        /*back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).displayView(11);
            }
        });*/

        drop_notifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).openDialogSettingNotifikasi();
            }
        });

        linear_suara_getar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).openDialogSettingNotifikasi();
            }
        });

        edit_notifikasi.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downX = event.getX();
                        downY = event.getY();

                        break;

                    case MotionEvent.ACTION_UP:
                        upX = event.getX();
                        upY = event.getY();
                        float deltaX = downX - upX;
                        float deltaY = downY - upY;

                        if(Math.abs(deltaX)<50 && Math.abs(deltaY)<50) {
                            ((MainActivity) getActivity()).openDialogSettingNotifikasi();
                        }

                        break;
                }

                return false;
            }
        });

        ((MainActivity) getActivity()).loadDataSetting();
		return rootView;
    }
}
