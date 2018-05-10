package gomocart.application.com.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import customfonts.MyEditText;
import customfonts.MyTextView;
import gomocart.application.com.gomocart.AlamatActivity;
import gomocart.application.com.gomocart.EditAlamatActivity;
import gomocart.application.com.gomocart.ProsesCheckoutActivity;
import gomocart.application.com.gomocart.R;

public class ProsesCheckoutAlamatKirimFragment extends Fragment {

	//public static ScrollView detail_view;
	//public static ProgressBar loading;

	public static MyTextView pickup_alamat;
	public static MyTextView add_alamat;
	public static MyEditText edit_nama;
	public static MyEditText edit_alamat;
	public static MyEditText edit_province;
	public static MyEditText edit_city;
	public static MyEditText edit_state;
	public static MyEditText edit_kodepos;
	public static MyEditText edit_nohp;

	public static CheckBox checkbox_isdropship;
	public static LinearLayout linear_dropship_name;
	public static LinearLayout linear_dropship_phone;
	public static MyEditText edit_dropship_name;
	public static MyEditText edit_dropship_phone;

	public static LinearLayout linear_email_notifikasi;
	public static MyEditText edit_email_notifikasi;
	public static MyTextView lanjutkan;

	float downX = 0, downY = 0, upX, upY;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		View rootView = inflater.inflate(R.layout.fragment_proses_checkout_alamat_kirim, container, false);

		//detail_view = (ScrollView) rootView.findViewById(R.id.detail_view);
		//loading = (FrameLayout) rootView.findViewById(R.id.pgbarLoading);

		pickup_alamat = (MyTextView) rootView.findViewById(R.id.pilih);
		add_alamat = (MyTextView) rootView.findViewById(R.id.tambah);
		edit_nama = (MyEditText) rootView.findViewById(R.id.edit_nama);
		edit_alamat = (MyEditText) rootView.findViewById(R.id.edit_alamat);
		edit_province = (MyEditText) rootView.findViewById(R.id.edit_provice);
		edit_city     = (MyEditText) rootView.findViewById(R.id.edit_city);
		edit_state    = (MyEditText) rootView.findViewById(R.id.edit_kecamatan);
		edit_kodepos = (MyEditText) rootView.findViewById(R.id.edit_kodepos);
		edit_nohp  = (MyEditText) rootView.findViewById(R.id.edit_phone);

		checkbox_isdropship = (CheckBox) rootView.findViewById(R.id.checkbox_isdropship);
		linear_dropship_name = (LinearLayout) rootView.findViewById(R.id.linear_dropship_name);
		edit_dropship_name = (MyEditText) rootView.findViewById(R.id.edit_dropship_name);
		linear_dropship_phone = (LinearLayout) rootView.findViewById(R.id.linear_dropship_phone);
		edit_dropship_phone = (MyEditText) rootView.findViewById(R.id.edit_dropship_phone);

		linear_email_notifikasi = (LinearLayout) rootView.findViewById(R.id.linear_email_notifikasi);
		edit_email_notifikasi = (MyEditText) rootView.findViewById(R.id.edit_email_notifikasi);

		lanjutkan = (MyTextView) rootView.findViewById(R.id.next);

		/*InputStream stream = null;
		try {
			stream = getActivity().getAssets().open("images/loading.gif");
		} catch (IOException e) {
			e.printStackTrace();
		}
		loading.addView(new GifDecoderView(getActivity(), stream));*/

		checkbox_isdropship.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				boolean isChecked = ((CheckBox)v).isChecked();
				linear_dropship_name.setVisibility(isChecked?View.VISIBLE:View.GONE);
				linear_dropship_phone.setVisibility(isChecked?View.VISIBLE:View.GONE);
			}
		});


		lanjutkan.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String message = ((ProsesCheckoutActivity) getActivity()).checkedAlamatKirimBeforeNext();
				if(message.length()==0) {
					((ProsesCheckoutActivity) getActivity()).saveAlamat();
					((ProsesCheckoutActivity) getActivity()).displayView(1);
				} else {
					((ProsesCheckoutActivity) getActivity()).openDialogMessage(message, false);
				}

			}
		});

		pickup_alamat.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AlamatActivity.class);
				getActivity().startActivityForResult(intent, ProsesCheckoutActivity.RESULT_FROM_ALAMAT);
			}
		});

		add_alamat.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), EditAlamatActivity.class);
				getActivity().startActivityForResult(intent, ProsesCheckoutActivity.RESULT_FROM_ALAMAT);
			}
		});

		edit_province.setOnTouchListener(new View.OnTouchListener() {

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
							((ProsesCheckoutActivity) getActivity()).loadDialogListView("province");
						}

						break;
				}

				return false;
			}
		});

		edit_city.setOnTouchListener(new View.OnTouchListener() {

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
							if(edit_province.getText().toString().length()==0) {
								((ProsesCheckoutActivity) getActivity()).openDialogMessage("Propinsi tujuan harus diisi!", false);
							} else {
								((ProsesCheckoutActivity) getActivity()).loadDialogListView("city");
							}
						}

						break;
				}

				return false;
			}
		});

		edit_state.setOnTouchListener(new View.OnTouchListener() {

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
							if(edit_city.getText().toString().length()==0) {
								((ProsesCheckoutActivity) getActivity()).openDialogMessage("Kabupaten / kota tujuan harus diisi!", false);
							} else {
								((ProsesCheckoutActivity) getActivity()).loadDialogListView("subdistrict");
							}
						}

						break;
				}

				return false;
			}
		});

		((ProsesCheckoutActivity) getActivity()).setInitialAlamatKirim();
		((ProsesCheckoutActivity) getActivity()).loadDefaultAlamat();

		return rootView;
	}

}
