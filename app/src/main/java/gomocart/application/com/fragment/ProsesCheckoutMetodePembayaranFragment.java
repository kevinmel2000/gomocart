
package gomocart.application.com.fragment;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import java.io.IOException;
import java.io.InputStream;

import customfonts.MyTextView;
import gomocart.application.com.gomocart.ProsesCheckoutActivity;
import gomocart.application.com.gomocart.R;


public class ProsesCheckoutMetodePembayaranFragment extends Fragment {

	public static ScrollView detail_view;
	public static ProgressBar loading;

	public static MyTextView total_tagihan;
	public static LinearLayout linear_metode_cod, linear_metode_transfer_bank;
	public static CheckBox metode_bayar, metode_bayar_cod;
	public static ListView listViewBank;
	public static LinearLayout retry;
	public static MyTextView btnReload;

	public static MyTextView lanjutkan;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		View rootView = inflater.inflate(R.layout.fragment_proses_checkout_metode_pembayaran, container, false);

		detail_view = (ScrollView) rootView.findViewById(R.id.detail_view);
		total_tagihan = (MyTextView) rootView.findViewById(R.id.total_tagihan);
		linear_metode_cod = (LinearLayout) rootView.findViewById(R.id.linear_metode_cod);
		linear_metode_transfer_bank = (LinearLayout) rootView.findViewById(R.id.linear_metode_transfer_bank);
		total_tagihan = (MyTextView) rootView.findViewById(R.id.total_tagihan);
		listViewBank = (ListView) rootView.findViewById(R.id.lisview);
		loading      = (ProgressBar) rootView.findViewById(R.id.pgbarLoading);
		retry        = (LinearLayout) rootView.findViewById(R.id.loadMask);
		btnReload    = (MyTextView) rootView.findViewById(R.id.btnReload);
		lanjutkan    = (MyTextView) rootView.findViewById(R.id.next);
		metode_bayar = (CheckBox) rootView.findViewById(R.id.metode_bayar);
		metode_bayar_cod = (CheckBox) rootView.findViewById(R.id.metode_bayar_cod);

		metode_bayar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				metode_bayar.setChecked(true);
			}
		});

		lanjutkan.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				String message = ((ProsesCheckoutActivity) getActivity()).checkedMetodePembayaranBeforeNext();
				if(message.length()==0) {
					((ProsesCheckoutActivity) getActivity()).displayView(3);
				} else {
					((ProsesCheckoutActivity) getActivity()).openDialogMessage(message, false);
				}
			}
		});

		btnReload.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((ProsesCheckoutActivity) getActivity()).loadDataBank();
			}
		});

		((ProsesCheckoutActivity) getActivity()).setInitialMetodePembayaran();
		((ProsesCheckoutActivity) getActivity()).loadDataBank();

		((ProsesCheckoutActivity) getActivity()).loadFieldTotalTransfer();

		return rootView;
	}
	
}
