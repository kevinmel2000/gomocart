
package gomocart.application.com.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import customfonts.MyTextView;
import gomocart.application.com.gomocart.ProsesCheckoutActivity;
import gomocart.application.com.gomocart.R;


public class ProsesCheckoutBerhasilFragment extends Fragment {



	MyTextView konfirmasi_pembayaran;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		View rootView = inflater.inflate(R.layout.fragment_proses_checkout_berhasil, container, false);

		konfirmasi_pembayaran = (MyTextView) rootView.findViewById(R.id.selesai);

		konfirmasi_pembayaran.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				((ProsesCheckoutActivity) getActivity()).prosesKonfirmasi();
			}
		});

		return rootView;
	}
	
}
