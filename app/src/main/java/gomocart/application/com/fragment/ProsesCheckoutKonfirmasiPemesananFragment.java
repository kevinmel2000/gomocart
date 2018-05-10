
package gomocart.application.com.fragment;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import java.io.IOException;
import java.io.InputStream;

import customfonts.MyEditText;
import customfonts.MyTextView;
import gomocart.application.com.gomocart.ProsesCheckoutActivity;
import gomocart.application.com.gomocart.R;
import gomocart.application.com.libs.ExpandableHeightListView;

public class ProsesCheckoutKonfirmasiPemesananFragment extends Fragment {


	public static ScrollView detail_view;
	public static ProgressBar loading;
	public static LinearLayout retry;
	public static MyTextView btnReload;

	public static MyTextView alamat;
	public static LinearLayout linear_dropshiper;
	public static MyTextView dropshiper;
	public static ImageView image_expedisi;
	public static MyTextView expedisi;

	public static ImageView image_pembayaran;
	public static MyTextView metode_pembayaran;
	public static MyTextView detail_pembayaran;
	public static MyEditText kode_voucher;
	public static MyTextView apply;
	public static ExpandableHeightListView listViewPesanan;

	public static MyTextView total;
	public static MyTextView voucher;
	public static MyTextView subtotal;
	public static MyTextView biayakirim;
	public static MyTextView grandtotal;

	public static MyTextView selesai;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		View rootView = inflater.inflate(R.layout.fragment_proses_checkout_konfirmasi_pemesanan, container, false);

		detail_view = (ScrollView) rootView.findViewById(R.id.detail_view);
		loading = (ProgressBar) rootView.findViewById(R.id.pgbarLoading);
		retry = (LinearLayout) rootView.findViewById(R.id.loadMask);
		btnReload = (MyTextView) rootView.findViewById(R.id.btnReload);

		alamat = (MyTextView) rootView.findViewById(R.id.alamat);

		linear_dropshiper = (LinearLayout) rootView.findViewById(R.id.linear_dropshiper);
		dropshiper = (MyTextView) rootView.findViewById(R.id.dropship);

		image_expedisi = (ImageView) rootView.findViewById(R.id.image_expedisi);
		expedisi = (MyTextView) rootView.findViewById(R.id.expedisi);

		metode_pembayaran = (MyTextView) rootView.findViewById(R.id.metode_pembayaran);
		image_pembayaran = (ImageView) rootView.findViewById(R.id.image_pembayaran);
		detail_pembayaran = (MyTextView) rootView.findViewById(R.id.detail_pembayaran);

		kode_voucher = (MyEditText) rootView.findViewById(R.id.edit_kode_voucher);
		apply = (MyTextView) rootView.findViewById(R.id.apply_voucher);

		listViewPesanan = (ExpandableHeightListView) rootView.findViewById(R.id.lisview);

		total      = (MyTextView) rootView.findViewById(R.id.total);
		voucher    = (MyTextView) rootView.findViewById(R.id.diskon);
		subtotal   = (MyTextView) rootView.findViewById(R.id.subtotal);
		biayakirim = (MyTextView) rootView.findViewById(R.id.biayakirim);
		grandtotal = (MyTextView) rootView.findViewById(R.id.grandtotal);

		selesai    = (MyTextView) rootView.findViewById(R.id.selesai);


		apply.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				((ProsesCheckoutActivity) getActivity()).prosesCekVoucher();

			}
		});

		selesai.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				((ProsesCheckoutActivity) getActivity()).submitOrder();
			}
		});


		((ProsesCheckoutActivity) getActivity()).setInitialKonfirmasiPesanan();
		((ProsesCheckoutActivity) getActivity()).loadDataPesanan();

		return rootView;
	}
	
}
