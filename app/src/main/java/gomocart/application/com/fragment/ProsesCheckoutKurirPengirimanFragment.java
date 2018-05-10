package gomocart.application.com.fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import java.io.IOException;
import java.io.InputStream;

import customfonts.MyTextView;
import gomocart.application.com.gomocart.ProsesCheckoutActivity;
import gomocart.application.com.gomocart.R;
import gomocart.application.com.libs.ExpandableHeightListView;

public class ProsesCheckoutKurirPengirimanFragment extends Fragment {

	public static ScrollView detail_view;
	public static ProgressBar loading;
	public static ExpandableHeightListView listViewOngkir;
	public static LinearLayout retry;
	public static MyTextView btnReload;
	public static MyTextView lanjutkan;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		View rootView = inflater.inflate(R.layout.fragment_proses_checkout_kurir_pengiriman, container, false);

		detail_view = (ScrollView) rootView.findViewById(R.id.detail_view);
		loading = (ProgressBar) rootView.findViewById(R.id.pgbarLoading);
		listViewOngkir = (ExpandableHeightListView) rootView.findViewById(R.id.lisview);
		retry = (LinearLayout) rootView.findViewById(R.id.loadMask);
		btnReload = (MyTextView) rootView.findViewById(R.id.btnReload);
		lanjutkan = (MyTextView) rootView.findViewById(R.id.next);

		btnReload.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				((ProsesCheckoutActivity) getActivity()).loadDataOngkir();
			}
		});

		lanjutkan.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				String message = ((ProsesCheckoutActivity) getActivity()).checkedKurirPengirimanBeforeNext();
				if(message.length()==0) {
					((ProsesCheckoutActivity) getActivity()).updateGrandtotal();
					((ProsesCheckoutActivity) getActivity()).displayView(2);
				} else {
					((ProsesCheckoutActivity) getActivity()).openDialogMessage(message, false);
				}

			}
		});

		((ProsesCheckoutActivity) getActivity()).setInitialKurirPengiruman();
		((ProsesCheckoutActivity) getActivity()).loadDataOngkir();

		return rootView;
	}
	
}
