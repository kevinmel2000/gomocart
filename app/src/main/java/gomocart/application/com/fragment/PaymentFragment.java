package gomocart.application.com.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import customfonts.MyTextView;
import gomocart.application.com.gomocart.MainActivity;
import gomocart.application.com.gomocart.R;
import gomocart.application.com.libs.ResizableImageView;

public class PaymentFragment extends Fragment {

    public static ResizableImageView image_payment_1;
    public static ResizableImageView image_payment_2;
    public static ResizableImageView image_payment_3;
    public static ResizableImageView image_payment_4;

    public static MyTextView text_payment_1;
    public static MyTextView text_payment_2;
    public static MyTextView text_payment_3;
    public static MyTextView text_payment_4;
    
    public static MyTextView text_sub_payment_1;
    public static MyTextView text_sub_payment_2;
    public static MyTextView text_sub_payment_3;
    public static MyTextView text_sub_payment_4;

    public static MyTextView konfirmasi_pembayaran;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_payment, container, false);

        image_payment_1 = (ResizableImageView) rootView.findViewById(R.id.image_payment_1);
        image_payment_2 = (ResizableImageView) rootView.findViewById(R.id.image_payment_2);
        image_payment_3 = (ResizableImageView) rootView.findViewById(R.id.image_payment_3);
        image_payment_4 = (ResizableImageView) rootView.findViewById(R.id.image_payment_4);

        text_payment_1 = (MyTextView) rootView.findViewById(R.id.text_payment_1);
        text_payment_2 = (MyTextView) rootView.findViewById(R.id.text_payment_2);
        text_payment_3 = (MyTextView) rootView.findViewById(R.id.text_payment_3);
        text_payment_4 = (MyTextView) rootView.findViewById(R.id.text_payment_4);

        text_sub_payment_1 = (MyTextView) rootView.findViewById(R.id.text_sub_payment_1);
        text_sub_payment_2 = (MyTextView) rootView.findViewById(R.id.text_sub_payment_2);
        text_sub_payment_3 = (MyTextView) rootView.findViewById(R.id.text_sub_payment_3);
        text_sub_payment_4 = (MyTextView) rootView.findViewById(R.id.text_sub_payment_4);

        image_payment_1.setVisibility(View.INVISIBLE);
        image_payment_2.setVisibility(View.INVISIBLE);
        image_payment_3.setVisibility(View.INVISIBLE);
        image_payment_4.setVisibility(View.INVISIBLE);

        text_payment_1.setVisibility(View.INVISIBLE);
        text_payment_2.setVisibility(View.INVISIBLE);
        text_payment_3.setVisibility(View.INVISIBLE);
        text_payment_4.setVisibility(View.INVISIBLE);

        text_sub_payment_1.setVisibility(View.INVISIBLE);
        text_sub_payment_2.setVisibility(View.INVISIBLE);
        text_sub_payment_3.setVisibility(View.INVISIBLE);
        text_sub_payment_4.setVisibility(View.INVISIBLE);

        konfirmasi_pembayaran = (MyTextView) rootView.findViewById(R.id.konfirmasi_pembayaran);
        konfirmasi_pembayaran.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				((MainActivity) getActivity()).openKonfirmasiPembayaran();
				
			}
		});

		((MainActivity) getActivity()).loadDataBank();
		return rootView;
    }
}
