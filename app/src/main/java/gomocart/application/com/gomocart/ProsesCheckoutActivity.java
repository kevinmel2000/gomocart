package gomocart.application.com.gomocart;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import customfonts.MyTextView;
import gomocart.application.com.adapter.PesananAdapter;
import gomocart.application.com.adapter.SelectBankAdapter;
import gomocart.application.com.adapter.SelectOngkirAdapter;
import gomocart.application.com.fragment.ProsesCheckoutAlamatKirimFragment;
import gomocart.application.com.fragment.ProsesCheckoutBerhasilFragment;
import gomocart.application.com.fragment.ProsesCheckoutKonfirmasiPemesananFragment;
import gomocart.application.com.fragment.ProsesCheckoutKurirPengirimanFragment;
import gomocart.application.com.fragment.ProsesCheckoutMetodePembayaranFragment;
import gomocart.application.com.libs.CommonUtilities;
import gomocart.application.com.libs.DatabaseHandler;
import gomocart.application.com.libs.JSONParser;
import gomocart.application.com.model.alamat;
import gomocart.application.com.model.bank;
import gomocart.application.com.model.city;
import gomocart.application.com.model.grandtotal;
import gomocart.application.com.model.ongkir;
import gomocart.application.com.model.order;
import gomocart.application.com.model.produk;
import gomocart.application.com.model.province;
import gomocart.application.com.model.subdistrict;
import gomocart.application.com.model.user;
import gomocart.application.com.model.voucher;

import static gomocart.application.com.libs.CommonUtilities.getOptionsImage;
import static gomocart.application.com.libs.CommonUtilities.initImageLoader;

public class ProsesCheckoutActivity extends FragmentActivity {

	Context context;
	user data;
	DatabaseHandler dh;

	int province_id = 0;
	int city_id = 0;
	int subdistrict_id = 0;

	int kurir_index = -1;
	int bank_index = -1;

	ImageView back;
	MyTextView title;
	MyTextView step;

	ArrayList<alamat> listAlamat = new ArrayList<>();
	ArrayList<province> listProvince = new ArrayList<>();
	ArrayList<city> listCity = new ArrayList<>();
	ArrayList<subdistrict> listSubDistrict = new ArrayList<>();

	ArrayList<ongkir> ongkirlist = new ArrayList<>();
	SelectOngkirAdapter selectOngkirAdapter;

	ArrayList<bank> banklist = new ArrayList<>();
	SelectBankAdapter selectBankAdapter;

	public static final int RESULT_FROM_ALAMAT = 1;
	public static String action;

	Dialog dialog_listview;
	ListView listview;

	Dialog dialog_informasi;
	MyTextView btn_ok;
	MyTextView text_title;
	MyTextView text_informasi;

	Dialog dialog_loading;
	//FrameLayout frame_loading;

	grandtotal gtotal;
	alamat data_alamat;
	ongkir data_kurir;
	bank data_bank;
	voucher data_voucher;
	order data_order;

	int menu_selected = 0;
	boolean change_subdistrict = true;

	int metode_pembayaran_id = 1;

	ArrayList<produk> pesananlist = new ArrayList<>();
	PesananAdapter pesananAdapter;

	ImageLoader imageLoader;
	DisplayImageOptions imageOptionKurir, imageOptionPembayaran;

	LinearLayout linear_indikator;
	ImageView image_step_2, image_step_3, image_step_4;
	View line_step_2_aktif;
	View line_step_3_aktif;
	View line_step_4_aktif;
	View line_step_2_inaktif;
	View line_step_3_inaktif;
	View line_step_4_inaktif;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_proses_checkout);

		context = ProsesCheckoutActivity.this;

		initImageLoader(context);
		imageLoader           = ImageLoader.getInstance();
		imageOptionKurir 	  = getOptionsImage(R.drawable.logo_grayscale, R.drawable.logo_grayscale);
		imageOptionPembayaran = getOptionsImage(R.drawable.logo_grayscale, R.drawable.logo_grayscale);

		data = CommonUtilities.getSettingUser(context);
		dh = new DatabaseHandler(context);
		gtotal = dh.getGrandtotal();

		linear_indikator = (LinearLayout) findViewById(R.id.linear_indikator);
		line_step_2_aktif = (View) findViewById(R.id.line_step_2_aktif);
		line_step_3_aktif = (View) findViewById(R.id.line_step_3_aktif);
		line_step_4_aktif = (View) findViewById(R.id.line_step_4_aktif);

		line_step_2_inaktif = (View) findViewById(R.id.line_step_2_inaktif);
		line_step_3_inaktif = (View) findViewById(R.id.line_step_3_inaktif);
		line_step_4_inaktif = (View) findViewById(R.id.line_step_4_inaktif);

		image_step_2 = (ImageView) findViewById(R.id.image_step_2);
		image_step_3 = (ImageView) findViewById(R.id.image_step_3);
		image_step_4 = (ImageView) findViewById(R.id.image_step_4);

		back = (ImageView) findViewById(R.id.back);
		title = (MyTextView) findViewById(R.id.title);
		step = (MyTextView) findViewById(R.id.step);

		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(menu_selected==4) {
					Intent intent = new Intent();
					setResult(RESULT_OK, intent);
					finish();
				} else {
					menu_selected--;
					if(menu_selected<0) {
						finish();
					}else {
						displayView(menu_selected);
					}
				}
			}
		});
		dialog_listview = new Dialog(context);
		dialog_listview.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog_listview.setCancelable(true);
		dialog_listview.setContentView(R.layout.list_dialog);

		listview = (ListView) dialog_listview.findViewById(R.id.listViewDialog);
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				dialog_listview.dismiss();
				if(action.equalsIgnoreCase("province")) {
					ProsesCheckoutAlamatKirimFragment.edit_province.setText(listProvince.get(position).getProvince());
					ProsesCheckoutAlamatKirimFragment.edit_city.setText("");
					ProsesCheckoutAlamatKirimFragment.edit_state.setText("");

					kurir_index = -1;

					province_id = listProvince.get(position).getProvince_id();
					city_id = 0;
					subdistrict_id = 0;
					change_subdistrict = true;

					new loadCity().execute();
					new loadSubdistrict().execute();

				} else if(action.equalsIgnoreCase("city")) {
					ProsesCheckoutAlamatKirimFragment.edit_city.setText(listCity.get(position).getCity());
					ProsesCheckoutAlamatKirimFragment.edit_state.setText("");

					kurir_index = -1;

					city_id = listCity.get(position).getCity_id();
					subdistrict_id = 0;
					change_subdistrict = true;

					new loadSubdistrict().execute();

				} else if(action.equalsIgnoreCase("subdistrict")) {
					ProsesCheckoutAlamatKirimFragment.edit_state.setText(listSubDistrict.get(position).getSubdistrict());

					kurir_index = -1;

					subdistrict_id = listSubDistrict.get(position).getSubdistrict_id();
					change_subdistrict = true;
				}
				action = "";
			}
		});

		dialog_informasi = new Dialog(context);
		dialog_informasi.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog_informasi.setCancelable(true);
		dialog_informasi.setContentView(R.layout.informasi_dialog);

		btn_ok = (MyTextView) dialog_informasi.findViewById(R.id.btn_ok);
		btn_ok.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog_informasi.dismiss();
			}
		});

		text_title = (MyTextView) dialog_informasi.findViewById(R.id.text_title);
		text_informasi = (MyTextView) dialog_informasi.findViewById(R.id.text_dialog);

		dialog_loading = new Dialog(context);
		dialog_loading.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog_loading.setCancelable(false);
		dialog_loading.setContentView(R.layout.loading_dialog);
		//frame_loading = (FrameLayout) dialog_loading.findViewById(R.id.frame_loading);

		listProvince = new ArrayList<>();
		listCity = new ArrayList<>();
		listSubDistrict = new ArrayList<>();
		new loadProvince().execute();

		if(savedInstanceState==null) {
			data_alamat = (alamat) getIntent().getSerializableExtra("data_alamat");
		}

		selectOngkirAdapter = new SelectOngkirAdapter(context, ongkirlist);
		selectBankAdapter = new SelectBankAdapter(context, banklist);
		pesananAdapter = new PesananAdapter(context, pesananlist);
		displayView(0);
	}

	public void loadDialogListView(String act) {
		action = act;
		if(action.equalsIgnoreCase("province") && listProvince.size()==0) {
			openDialogLoadingEkspedisi();
		} else if(action.equalsIgnoreCase("city") && listCity.size()==0) {
			openDialogLoadingEkspedisi();
		} else if(action.equalsIgnoreCase("subdistrict") && listSubDistrict.size()==0) {
			openDialogLoadingEkspedisi();
		} else {
			loadListArray();
			dialog_listview.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			dialog_listview.show();
		}
	}

	public void openDialogMessage(String message, boolean status) {
		text_informasi.setText(message);
		text_title.setText(status?"BERHASIL":"KESALAHAN");
		dialog_informasi.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		dialog_informasi.show();
	}

	public void openDialogLoading() {
		dialog_loading.setCancelable(false);
		dialog_loading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		dialog_loading.show();
	}

	public void openDialogLoadingEkspedisi() {
		dialog_loading.setCancelable(true);
		dialog_loading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		dialog_loading.show();
	}

	public void loadListArray() {
		String[] from = new String[] { getResources().getString(R.string.list_dialog_title) };
		int[] to = new int[] { R.id.txt_title };

		List<HashMap<String, String>> fillMaps = new ArrayList<>();
		if(action.equalsIgnoreCase("pilih_alamat")) {
			for (alamat data : listAlamat) {
				HashMap<String, String> map = new HashMap<>();
				map.put(getResources().getString(R.string.list_dialog_title), data.getNama());

				fillMaps.add(map);
			}
		} else if(action.equalsIgnoreCase("province")) {
			for (province data : listProvince) {
				HashMap<String, String> map = new HashMap<>();
				map.put(getResources().getString(R.string.list_dialog_title), data.getProvince());

				fillMaps.add(map);
			}
		} else if(action.equalsIgnoreCase("city")) {
			for (city data : listCity) {
				HashMap<String, String> map = new HashMap<>();
				map.put(getResources().getString(R.string.list_dialog_title), data.getCity());

				fillMaps.add(map);
			}
		} else if(action.equalsIgnoreCase("subdistrict")) {
			for (subdistrict data : listSubDistrict) {
				HashMap<String, String> map = new HashMap<>();
				map.put(getResources().getString(R.string.list_dialog_title), data.getSubdistrict());

				fillMaps.add(map);
			}
		}

		SimpleAdapter adapter = new SimpleAdapter(context, fillMaps, R.layout.item_list_dialog, from, to);
		listview.setAdapter(adapter);
	}


	@Override
	protected void onDestroy() {
		try {
			unregisterReceiver(mHandleLoadEkspedisiReceiver);
			unregisterReceiver(mHandleLoadListOngkirReceiver);
			unregisterReceiver(mHandleLoadListBankReceiver);

			unregisterReceiver(mHandleLoadDataPesananReceiver);
			unregisterReceiver(mHandleLoadListSubmitOrderReceiver);
			unregisterReceiver(mHandleLoadListCekVoucherReceiver);

		} catch (Exception e) {
			e.printStackTrace();
		}

		super.onDestroy();
	}

	@Override
	protected void onPause() {
		try {
			unregisterReceiver(mHandleLoadEkspedisiReceiver);
			unregisterReceiver(mHandleLoadListOngkirReceiver);
			unregisterReceiver(mHandleLoadListBankReceiver);

			unregisterReceiver(mHandleLoadDataPesananReceiver);
			unregisterReceiver(mHandleLoadListSubmitOrderReceiver);
			unregisterReceiver(mHandleLoadListCekVoucherReceiver);

		} catch (Exception e) {
			e.printStackTrace();
		}

		super.onPause();
	}

	@Override
	protected void onResume() {
		registerReceiver(mHandleLoadEkspedisiReceiver, new IntentFilter("gomocart.application.com.gomocart.LOAD_EXPEDISI_LIST"));
		registerReceiver(mHandleLoadListOngkirReceiver, new IntentFilter("gomocart.application.com.gomocart.LOAD_DATA_ONGKIR_CHECKOUT"));
		registerReceiver(mHandleLoadListBankReceiver, new IntentFilter("gomocart.application.com.gomocart.LOAD_DATA_CHECKOUT_BANK"));

		registerReceiver(mHandleLoadDataPesananReceiver, new IntentFilter("gomocart.application.com.gomocart.LOAD_DATA_PESANAN"));
		registerReceiver(mHandleLoadListSubmitOrderReceiver, new IntentFilter("gomocart.application.com.gomocart.LOAD_SUBMIT_ORDER"));
		registerReceiver(mHandleLoadListCekVoucherReceiver, new IntentFilter("gomocart.application.com.gomocart.CEK_VOUCHER"));

		super.onResume();
	}

	private final BroadcastReceiver mHandleLoadListCekVoucherReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {

			boolean success = intent.getBooleanExtra("success", false);
			String message = intent.getStringExtra("message");

			dialog_loading.dismiss();
			if(success) {
				loadFieldGrandTotal();
			} else {
				openDialogMessage(message, false);
			}

		}
	};

	private final BroadcastReceiver mHandleLoadListSubmitOrderReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {

			boolean success = intent.getBooleanExtra("success", false);
			String message = intent.getStringExtra("message");

			dialog_loading.dismiss();
			if(!success) {
				openDialogMessage(message, false);
			} else {
				displayView(4);
			}

		}
	};

	private final BroadcastReceiver mHandleLoadDataPesananReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {

			Boolean success = intent.getBooleanExtra("success", false);

			//ALAMAT PENGIRIMAN
			ProsesCheckoutKonfirmasiPemesananFragment.alamat.setText(data_alamat.getNama()+"\n"+data_alamat.getAlamat()+ " "+data_alamat.getKode_pos()+"\n"+data_alamat.getSubdistrict_name()+", "+data_alamat.getCity_name()+"\n"+data_alamat.getProvince()+"\nTelepon: "+data_alamat.getNo_hp());

			//DROPSHIPER
			ProsesCheckoutKonfirmasiPemesananFragment.linear_dropshiper.setVisibility(data_alamat.getIs_dropship()?View.VISIBLE:View.GONE);
			ProsesCheckoutKonfirmasiPemesananFragment.dropshiper.setText("Dropshiper: "+data_alamat.getDropship_name()+"\nNo. Handphone: "+data_alamat.getDropship_phone());

			//EXPEDISI PENGIRIMAN
			loadKurirLogo(data_kurir.getGambar());
			ProsesCheckoutKonfirmasiPemesananFragment.expedisi.setText(data_kurir.getKode_kurir()+" "+data_kurir.getNama_service()+" ("+data_kurir.getEtd()+" hari)\n"+CommonUtilities.getCurrencyFormat(data_kurir.getNominal(), "Rp. "));

			//PEMBAYARAN
			ProsesCheckoutKonfirmasiPemesananFragment.metode_pembayaran.setText(metode_pembayaran_id==1?"Transfer Bank: ":(metode_pembayaran_id==2?"Kartu Kredit / Debet: ":"Bayar di Tempat: "));
			loadPembayaranLogo(data_bank.getGambar());
			ProsesCheckoutKonfirmasiPemesananFragment.detail_pembayaran.setText(data_bank.getNama_bank()+"\n"+data_bank.getNo_rekening()+" an. "+data_bank.getNama_pemilik_rekening());

			//LIST PESANAN
			pesananAdapter.UpdatePesananAdapter(pesananlist);

			//VOUCHER DAN GRAND TOTAL
			loadFieldGrandTotal();

			ProsesCheckoutKonfirmasiPemesananFragment.loading.setVisibility(View.GONE);
			if(success) {
				ProsesCheckoutKonfirmasiPemesananFragment.detail_view.setVisibility(View.VISIBLE);
			} else {
				ProsesCheckoutKonfirmasiPemesananFragment.retry.setVisibility(View.VISIBLE);
			}
		}
	};

	private final BroadcastReceiver mHandleLoadListBankReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Boolean success = intent.getBooleanExtra("success", false);

			selectBankAdapter.UpdateBankAdapter(banklist);
			ProsesCheckoutMetodePembayaranFragment.loading.setVisibility(View.GONE);
			if(success) {
				ProsesCheckoutMetodePembayaranFragment.detail_view.setVisibility(View.VISIBLE);
			} else {
				ProsesCheckoutMetodePembayaranFragment.retry.setVisibility(View.VISIBLE);
			}

		}
	};

	private final BroadcastReceiver mHandleLoadListOngkirReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Boolean success = intent.getBooleanExtra("success", false);

			selectOngkirAdapter.UpdateSelectOngkirAdapter(ongkirlist);
			ProsesCheckoutKurirPengirimanFragment.loading.setVisibility(View.GONE);
			if(success) {
				ProsesCheckoutKurirPengirimanFragment.detail_view.setVisibility(View.VISIBLE);
				change_subdistrict = false;
			} else {
				ProsesCheckoutKurirPengirimanFragment.retry.setVisibility(View.VISIBLE);
			}
		}
	};

	private final BroadcastReceiver mHandleLoadEkspedisiReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {

			if(dialog_loading.isShowing()) {
				loadListArray();
				dialog_loading.dismiss();
				dialog_listview.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
				dialog_listview.show();
			}
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data_intent) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data_intent);


		if (resultCode == RESULT_OK) {
			switch (requestCode) {

				case RESULT_FROM_ALAMAT:
					change_subdistrict = true;
					alamat select_alamat = (alamat) data_intent.getSerializableExtra("alamat");
					loadFieldAlamat(select_alamat, false);

					break;
			}
		}
	}

	public void setInitialAlamatKirim() {
		ProsesCheckoutAlamatKirimFragment.pickup_alamat.setVisibility(data.getId()>0?View.VISIBLE:View.INVISIBLE);
		ProsesCheckoutAlamatKirimFragment.add_alamat.setVisibility(data.getId()>0?View.VISIBLE:View.INVISIBLE);
	}

	public void setInitialKurirPengiruman() {
		ProsesCheckoutKurirPengirimanFragment.listViewOngkir.setAdapter(selectOngkirAdapter);
	}

	public void setInitialMetodePembayaran() {
		ProsesCheckoutMetodePembayaranFragment.linear_metode_cod.setVisibility(View.GONE);
		ProsesCheckoutMetodePembayaranFragment.listViewBank.setAdapter(selectBankAdapter);
	}

	public void setInitialKonfirmasiPesanan() {
		ProsesCheckoutKonfirmasiPemesananFragment.listViewPesanan.setAdapter(pesananAdapter);
	}

	public void loadDefaultAlamat() {
		if(data_alamat!=null) loadFieldAlamat(data_alamat, true);
	}

	public void loadFieldAlamat(alamat selected_alamat, boolean load_dropship) {

		province_id = selected_alamat.getProvince_id();
		city_id = selected_alamat.getCity_id();
		subdistrict_id = selected_alamat.getSubdistrict_id();

		new loadCity().execute();
		new loadSubdistrict().execute();

		ProsesCheckoutAlamatKirimFragment.edit_nama.setText(selected_alamat.getNama());
		ProsesCheckoutAlamatKirimFragment.edit_alamat.setText(selected_alamat.getAlamat());
		ProsesCheckoutAlamatKirimFragment.edit_province.setText(selected_alamat.getProvince());

		ProsesCheckoutAlamatKirimFragment.edit_city.setText(selected_alamat.getCity_name());
		ProsesCheckoutAlamatKirimFragment.edit_state.setText(selected_alamat.getSubdistrict_name());
		ProsesCheckoutAlamatKirimFragment.edit_kodepos.setText(selected_alamat.getKode_pos());
		ProsesCheckoutAlamatKirimFragment.edit_nohp.setText(selected_alamat.getNo_hp());

		if(load_dropship) {
			selected_alamat = dh.getAlamat();
			ProsesCheckoutAlamatKirimFragment.checkbox_isdropship.setChecked(selected_alamat.getIs_dropship());
			ProsesCheckoutAlamatKirimFragment.edit_dropship_name.setText(selected_alamat.getDropship_name().length() == 0 ? data.getDropship_name() : selected_alamat.getDropship_name());
			ProsesCheckoutAlamatKirimFragment.edit_dropship_phone.setText(selected_alamat.getDropship_phone().length() == 0 ? data.getDropship_phone() : selected_alamat.getDropship_phone());
			ProsesCheckoutAlamatKirimFragment.linear_dropship_name.setVisibility(selected_alamat.getIs_dropship() ? View.VISIBLE : View.GONE);
			ProsesCheckoutAlamatKirimFragment.linear_dropship_phone.setVisibility(selected_alamat.getIs_dropship() ? View.VISIBLE : View.GONE);

			ProsesCheckoutAlamatKirimFragment.edit_email_notifikasi.setText(selected_alamat.getEmail_notifikasi());
			ProsesCheckoutAlamatKirimFragment.linear_email_notifikasi.setVisibility(data.getId() == 0 ? View.VISIBLE : View.GONE);
		}
	}

	public class loadProvince extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(String... urls) {

			/*try {
				Thread.sleep(5000);
			} catch (Exception e) {
				e.printStackTrace();
			}*/

			listProvince = new ArrayList<>();
			String url = CommonUtilities.SERVER_URL + "/store/androidPropinsiDataStore.php";
			JSONObject json = new JSONParser().getJSONFromUrl(url, null, null);
			if(json!=null) {
				try {
					JSONArray data = json.isNull("topics")?null:json.getJSONArray("topics");
					for (int i=0; i<data.length(); i++) {
						JSONObject rec= data.getJSONObject(i);

						int province_id = rec.isNull("province_id")?0:rec.getInt("province_id");
						String province = rec.isNull("province")?"":rec.getString("province");

						listProvince.add(new province(province_id, province));
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			Intent i = new Intent("gomocart.application.com.gomocart.LOAD_EXPEDISI_LIST");
			sendBroadcast(i);

			return null;
		}
	}

	public class loadCity extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(String... urls) {

			/*try {
				Thread.sleep(5000);
			} catch (Exception e) {
				e.printStackTrace();
			}*/

			listCity = new ArrayList<>();
			if(province_id>0) {
				String url = CommonUtilities.SERVER_URL + "/store/androidCityDataStore.php";
				List<NameValuePair> params = new ArrayList<>();
				params.add(new BasicNameValuePair("province_id", province_id+""));
				JSONObject json = new JSONParser().getJSONFromUrl(url, params, null);
				if(json!=null) {
					try {
						JSONArray data = json.isNull("topics")?null:json.getJSONArray("topics");
						for (int i=0; i<data.length(); i++) {
							JSONObject rec= data.getJSONObject(i);

							int city_id = rec.isNull("city_id")?0:rec.getInt("city_id");
							int province_id = rec.isNull("province_id")?0:rec.getInt("province_id");
							String city = rec.isNull("city_name")?"":rec.getString("city_name");

							listCity.add(new city(city_id, province_id, city));
						}

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			Intent i = new Intent("gomocart.application.com.gomocart.LOAD_EXPEDISI_LIST");
			sendBroadcast(i);

			return null;
		}
	}

	public class loadSubdistrict extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(String... urls) {

			/*try {
				Thread.sleep(5000);
			} catch (Exception e) {
				e.printStackTrace();
			}*/

			listSubDistrict = new ArrayList<>();
			if(city_id>0) {
				String url = CommonUtilities.SERVER_URL + "/store/androidSubdistrictDataStore.php";
				List<NameValuePair> params = new ArrayList<>();
				params.add(new BasicNameValuePair("city_id", city_id+""));
				JSONObject json = new JSONParser().getJSONFromUrl(url, params, null);
				if(json!=null) {
					try {
						JSONArray data = json.isNull("topics")?null:json.getJSONArray("topics");
						for (int i=0; i<data.length(); i++) {
							JSONObject rec= data.getJSONObject(i);

							int subdistrict_id = rec.isNull("subdistrict_id")?0:rec.getInt("subdistrict_id");
							int city_id = rec.isNull("city_id")?0:rec.getInt("city_id");
							String subdistrict = rec.isNull("subdistrict_name")?"":rec.getString("subdistrict_name");

							listSubDistrict.add(new subdistrict(subdistrict_id, city_id, subdistrict));
						}

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			Intent i = new Intent("gomocart.application.com.gomocart.LOAD_EXPEDISI_LIST");
			sendBroadcast(i);

			return null;
		}
	}

	public void displayView(int position) {
		menu_selected = position;

		Fragment fragment = null;
		switch (position) {
			case 0:

				fragment = new ProsesCheckoutAlamatKirimFragment();
				break;

			case 1:

				fragment = new ProsesCheckoutKurirPengirimanFragment();
				break;

			case 2:

				fragment = new ProsesCheckoutMetodePembayaranFragment();
				break;

			case 3:

				fragment = new ProsesCheckoutKonfirmasiPemesananFragment();
				break;

			case 4:

				fragment = new ProsesCheckoutBerhasilFragment();
				break;

			default:
				break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();

			switch (menu_selected) {
				case 0:

					title.setText("ALAMAT KIRIM");
					step.setText("Step 1/4");

					line_step_2_aktif.setVisibility(View.GONE);
					line_step_3_aktif.setVisibility(View.GONE);
					line_step_4_aktif.setVisibility(View.GONE);

					line_step_2_inaktif.setVisibility(View.VISIBLE);
					line_step_3_inaktif.setVisibility(View.VISIBLE);
					line_step_4_inaktif.setVisibility(View.VISIBLE);

					image_step_2.setImageResource(R.drawable.checkout_step_2);
					image_step_3.setImageResource(R.drawable.checkout_step_3);
					image_step_4.setImageResource(R.drawable.checkout_step_4);

					break;
				case 1:

					title.setText("KURIR PENGIRIMAN");
					step.setText("Step 2/4");

					line_step_2_inaktif.setVisibility(View.GONE);
					line_step_3_aktif.setVisibility(View.GONE);
					line_step_4_aktif.setVisibility(View.GONE);

					line_step_2_aktif.setVisibility(View.VISIBLE);
					line_step_3_inaktif.setVisibility(View.VISIBLE);
					line_step_4_inaktif.setVisibility(View.VISIBLE);

					image_step_2.setImageResource(R.drawable.checkout_step_2_aktif);
					image_step_3.setImageResource(R.drawable.checkout_step_3);
					image_step_4.setImageResource(R.drawable.checkout_step_4);

					break;
				case 2:

					title.setText("METODE PEMBAYARAN");
					step.setText("Step 3/4");

					line_step_2_inaktif.setVisibility(View.GONE);
					line_step_3_inaktif.setVisibility(View.GONE);
					line_step_4_aktif.setVisibility(View.GONE);

					line_step_2_aktif.setVisibility(View.VISIBLE);
					line_step_3_aktif.setVisibility(View.VISIBLE);
					line_step_4_inaktif.setVisibility(View.VISIBLE);

					image_step_2.setImageResource(R.drawable.checkout_step_2_aktif);
					image_step_3.setImageResource(R.drawable.checkout_step_3_aktif);
					image_step_4.setImageResource(R.drawable.checkout_step_4);

					break;
				case 3:

					title.setText("KONFIRMASI PEMESANAN");
					step.setText("Step 4/4");

					line_step_2_inaktif.setVisibility(View.GONE);
					line_step_3_inaktif.setVisibility(View.GONE);
					line_step_4_inaktif.setVisibility(View.GONE);

					line_step_2_aktif.setVisibility(View.VISIBLE);
					line_step_3_aktif.setVisibility(View.VISIBLE);
					line_step_4_aktif.setVisibility(View.VISIBLE);

					image_step_2.setImageResource(R.drawable.checkout_step_2_aktif);
					image_step_3.setImageResource(R.drawable.checkout_step_3_aktif);
					image_step_4.setImageResource(R.drawable.checkout_step_4_aktif);

					break;
				case 4:

					title.setText("PESANAN BERHASIL");
					step.setText("");

					linear_indikator.setVisibility(View.INVISIBLE);
					break;
			}
		}
	}

	public String checkedAlamatKirimBeforeNext() {

		if(ProsesCheckoutAlamatKirimFragment.edit_nama.getText().toString().length()==0) {
			return "Nama harus diisi.";
		}

		if(ProsesCheckoutAlamatKirimFragment.edit_alamat.getText().toString().length()==0) {
			return "Alamat harus diisi.";
		}

		if(province_id==0) {
			return "Propinsi harus harus diisi.";
		}

		if(city_id==0) {
			return "Kota harus diisi.";
		}

		if(subdistrict_id==0) {
			return "Kecamatan harus diisi.";
		}

		if(ProsesCheckoutAlamatKirimFragment.edit_nohp.getText().toString().length()==0) {
			return "No HP harus diisi.";
		}

		return "";
	}

	public String checkedKurirPengirimanBeforeNext() {
		if(data_kurir==null || data_kurir.getId_kurir()==0) {
			return "Pilih salah satu kurir pengiriman.";
		}

		return "";
	}

	public String checkedMetodePembayaranBeforeNext() {
		if(data_bank==null || data_bank.getId()==0) {
			return "Pilih salah bank pembayaran.";
		}

		return "";
	}

	public void saveAlamat() {

		String nama = ProsesCheckoutAlamatKirimFragment.edit_nama.getText().toString();
		String alamat = ProsesCheckoutAlamatKirimFragment.edit_alamat.getText().toString();
		String province = ProsesCheckoutAlamatKirimFragment.edit_province.getText().toString();
		String city_name = ProsesCheckoutAlamatKirimFragment.edit_city.getText().toString();
		String subdistrict_name = ProsesCheckoutAlamatKirimFragment.edit_state.getText().toString();
		String kode_pos = ProsesCheckoutAlamatKirimFragment.edit_kodepos.getText().toString();
		String no_hp = ProsesCheckoutAlamatKirimFragment.edit_nohp.getText().toString();

		boolean is_dropship = ProsesCheckoutAlamatKirimFragment.checkbox_isdropship.isChecked();
		String dropship_name = ProsesCheckoutAlamatKirimFragment.edit_dropship_name.getText().toString().trim();
		String dropship_phone = ProsesCheckoutAlamatKirimFragment.edit_dropship_phone.getText().toString().trim();
		String email_notifikasi = ProsesCheckoutAlamatKirimFragment.edit_email_notifikasi.getText().toString().trim();

		data_alamat = new alamat(0, nama, alamat, province_id, province, city_id, city_name, subdistrict_id, subdistrict_name, kode_pos, no_hp, false, is_dropship, dropship_name, dropship_phone, email_notifikasi);
		dh.insertAlamat(data_alamat);
	}

	public void updateGrandtotal() {

		gtotal.setPengiriman(data_kurir.getNominal());
		gtotal.setGrand_total(gtotal.getSub_total()+gtotal.getPengiriman()-gtotal.getVoucher());
	}

	public void loadDataBank() {
		if(banklist.size()==0) {
			new loadDataBank().execute();
		} else {
			ProsesCheckoutMetodePembayaranFragment.loading.setVisibility(View.GONE);
			ProsesCheckoutMetodePembayaranFragment.detail_view.setVisibility(View.VISIBLE);
		}
	}

	public class loadDataBank extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			ProsesCheckoutMetodePembayaranFragment.detail_view.setVisibility(View.INVISIBLE);
			ProsesCheckoutMetodePembayaranFragment.loading.setVisibility(View.VISIBLE);
			ProsesCheckoutMetodePembayaranFragment.retry.setVisibility(View.GONE);
		}

		@Override
		protected Void doInBackground(String... urls) {

			String url = CommonUtilities.SERVER_URL + "/store/androidBankDataStore.php";
			JSONObject json = new JSONParser().getJSONFromUrl(url, null, null);
			boolean success = false;
			if (json != null) {
				try {
					JSONArray data = json.isNull("topics") ? null : json.getJSONArray("topics");
					for (int i = 0; i < data.length(); i++) {
						JSONObject rec = data.getJSONObject(i);

						int id = rec.isNull("id") ? 0 : rec.getInt("id");
						String no_rekening = rec.isNull("no_rekening") ? "" : rec.getString("no_rekening");
						String nama_pemilik_rekening = rec.isNull("nama_pemilik_rekening") ? "" : rec.getString("nama_pemilik_rekening");
						String nama_bank = rec.isNull("nama_bank") ? "" : rec.getString("nama_bank");
						String cabang = rec.isNull("cabang") ? "" : rec.getString("cabang");
						String gambar = rec.isNull("gambar") ? "" : rec.getString("gambar");

						banklist.add(i, new bank(id, no_rekening, nama_pemilik_rekening, nama_bank, cabang, gambar));
						banklist.get(i).setIs_selected(bank_index==1);
					}
					success = true;
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			Intent i = new Intent("gomocart.application.com.gomocart.LOAD_DATA_CHECKOUT_BANK");
			i.putExtra("success", success);
			sendBroadcast(i);

			return null;
		}
	}

	public void loadDataOngkir() {
		if(change_subdistrict) {
			new loadDataOngkir().execute();
		} else {
			ProsesCheckoutKurirPengirimanFragment.loading.setVisibility(View.GONE);
			ProsesCheckoutKurirPengirimanFragment.detail_view.setVisibility(View.VISIBLE);
		}
	}

	public class loadDataOngkir extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			ProsesCheckoutKurirPengirimanFragment.detail_view.setVisibility(View.GONE);
			ProsesCheckoutKurirPengirimanFragment.loading.setVisibility(View.VISIBLE);
			ProsesCheckoutKurirPengirimanFragment.retry.setVisibility(View.GONE);
		}

		@Override
		protected Void doInBackground(String... urls) {

			ongkirlist = new ArrayList<>();
			String url = CommonUtilities.SERVER_URL + "/store/androidAllLayananDataStore.php";
			List<NameValuePair> params = new ArrayList<>();
			params.add(new BasicNameValuePair("city_id", data_alamat.getCity_id()+""));
			params.add(new BasicNameValuePair("subdistrict_id", data_alamat.getSubdistrict_id()+""));
			params.add(new BasicNameValuePair("berat", dh.getTotalBeratCart()+""));
			params.add(new BasicNameValuePair("cart", dh.getStringCartlist()));
			params.add(new BasicNameValuePair("total", dh.getGrandtotal().getTotal()+""));


			JSONObject json = new JSONParser().getJSONFromUrl(url, params, null);
			boolean success = false;
			if(json!=null) {
				try {
					JSONArray topics_induk = json.isNull("topics")?null:json.getJSONArray("topics");
					for (int i=0; i<topics_induk.length(); i++) {
						JSONObject rec = topics_induk.getJSONObject(i);
						int id_kurir = rec.isNull("id_kurir")?0:rec.getInt("id_kurir");
						String kode_kurir = rec.isNull("kode_kurir")?null:rec.getString("kode_kurir");
						String nama_kurir = rec.isNull("nama_kurir")?null:rec.getString("nama_kurir");
						String kode_service = rec.isNull("kode_service")?null:rec.getString("kode_service");
						String nama_service = rec.isNull("nama_service")?null:rec.getString("nama_service");
						int nominal = rec.isNull("nominal")?0:rec.getInt("nominal");
						String tarif = rec.isNull("tarif")?null:rec.getString("tarif");
						String etd = rec.isNull("etd")?null:rec.getString("etd");
						String gambar = rec.isNull("gambar_kurir")?null:rec.getString("gambar_kurir");
						ongkirlist.add(i, new ongkir(id_kurir, kode_kurir, nama_kurir, kode_service, nama_service, nominal, etd, tarif, gambar));
						ongkirlist.get(i).setIs_selected(kurir_index==i);
					}

					success = true;
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			Intent i = new Intent("gomocart.application.com.gomocart.LOAD_DATA_ONGKIR_CHECKOUT");
			i.putExtra("success", success);
			sendBroadcast(i);

			return null;
		}
	}

	public int getKurir_index() {
		return this.kurir_index;
	}

	public void setKurirPengiriman(int index, ongkir datakurir) {

		kurir_index = index;
		data_kurir = datakurir;
	}

	public int getBank_index() {
		return this.bank_index;
	}

	public void setMetodePembayaranBank(int index, bank databank) {

		bank_index = index;
		data_bank = databank;
	}


	public void submitOrder() {
		new prosesSubmitOrder().execute();
		openDialogLoading();
	}

	public class prosesSubmitOrder extends AsyncTask<String, Void, Void> {


		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(String... urls) {

			boolean success = false;
			String message = "Kesalahan koneksi internet.";
			data_order = null;

			List<NameValuePair> params = new ArrayList<>();
			params.add(new BasicNameValuePair("id_user", data.getId()+""));
			params.add(new BasicNameValuePair("guest_id", CommonUtilities.getGuestId(context)+""));

			//ALAMAT PENGIRIMAN
			params.add(new BasicNameValuePair("nama", data_alamat.getNama()));
			params.add(new BasicNameValuePair("alamat", data_alamat.getAlamat()));
			params.add(new BasicNameValuePair("propinsi", data_alamat.getProvince_id()+""));
			params.add(new BasicNameValuePair("nama_propinsi", data_alamat.getProvince()));
			params.add(new BasicNameValuePair("kota", data_alamat.getCity_id()+""));
			params.add(new BasicNameValuePair("nama_kota", data_alamat.getCity_name()));
			params.add(new BasicNameValuePair("kecamatan", data_alamat.getSubdistrict_id()+""));
			params.add(new BasicNameValuePair("nama_kecamatan", data_alamat.getSubdistrict_name()));
			params.add(new BasicNameValuePair("kode_pos", data_alamat.getKode_pos()));
			params.add(new BasicNameValuePair("no_hp", data_alamat.getNo_hp()));

			//DROPSHIP
			params.add(new BasicNameValuePair("is_dropship", data_alamat.getIs_dropship()?"Y":"N"));
			params.add(new BasicNameValuePair("dropship_name", data_alamat.getIs_dropship()?data_alamat.getDropship_name():""));
			params.add(new BasicNameValuePair("dropship_phone", data_alamat.getIs_dropship()?data_alamat.getDropship_phone():""));
			params.add(new BasicNameValuePair("email_notifikasi", data.getId()>0?data_alamat.getEmail_notifikasi():""));

			//KURIR PENGIRIMAN
            params.add(new BasicNameValuePair("kurir_id", data_kurir.getId_kurir()+""));
			params.add(new BasicNameValuePair("kode_kurir", data_kurir.getKode_kurir()));
			params.add(new BasicNameValuePair("nama_kurir", data_kurir.getNama_kurir()));
			params.add(new BasicNameValuePair("kode_layanan", data_kurir.getKode_service()));
			params.add(new BasicNameValuePair("layanan", data_kurir.getNama_service()));
			params.add(new BasicNameValuePair("nominal", data_kurir.getNominal()+""));
            params.add(new BasicNameValuePair("etd", data_kurir.getEtd()));
            params.add(new BasicNameValuePair("tarif", data_kurir.getTarif()));

			//METODE PEMBAYARAN
			params.add(new BasicNameValuePair("metode_pembayaran", metode_pembayaran_id+""));
			params.add(new BasicNameValuePair("bank_id", data_bank.getId()+""));
			params.add(new BasicNameValuePair("bank_no_rekening", data_bank.getNo_rekening()));
			params.add(new BasicNameValuePair("bank_nama_pemilik_rekening", data_bank.getNama_pemilik_rekening()));
			params.add(new BasicNameValuePair("bank_nama", data_bank.getNama_bank()));
			params.add(new BasicNameValuePair("bank_cabang", data_bank.getCabang()));
			params.add(new BasicNameValuePair("bank_logo", data_bank.getGambar()));

			//DATA VOUCHER
			if(data_voucher!=null) {
				String voucher = data_voucher.getKode()+"\t"+data_voucher.getNominal()+"\t"+data_voucher.getTipe_voucher()+"\t"+data_voucher.getJenis_voucher();
				params.add(new BasicNameValuePair("voucher", voucher));
			}

			//DETAIL ORDERS
			params.add(new BasicNameValuePair("orders", dh.getStringCartlist()));

			String url = CommonUtilities.SERVER_URL + "/store/androidSubmitOrder.php";
			JSONObject json = new JSONParser().getJSONFromUrl(url, params, null);

			if(json!=null) {
				try {
					success = json.isNull("success")?false:json.getBoolean("success");
					message = json.isNull("message")?message:json.getString("message");

					JSONObject json_order = json.isNull("order")?null:json.getJSONObject("order");
					if(json_order!=null) {
						String no_transaksi = json_order.isNull("no_transaksi")?"":json_order.getString("no_transaksi");
						String tgl_transaksi = json_order.isNull("tgl_transaksi")?"":json_order.getString("tgl_transaksi");
						int pembayaran = json_order.isNull("pembayaran")?0:json_order.getInt("pembayaran");
						String nama = json_order.isNull("nama")?"":json_order.getString("nama");
						int qty = json_order.isNull("qty")?0:json_order.getInt("qty");
						double jumlah = json_order.isNull("jumlah")?0:json_order.getDouble("jumlah");
						String estimasi = json_order.isNull("estimasi")?"":json_order.getString("estimasi");
						String gambar = json_order.isNull("gambar")?"":json_order.getString("gambar");
						int status = json_order.isNull("status")?0:json_order.getInt("status");

						data_order = new order(no_transaksi, tgl_transaksi, pembayaran, nama, qty, jumlah, estimasi, "", "", gambar, status, data.getId());
					}


				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if(success) {
				dh.insertOrderlist(data_order);
				dh.deleteCartlist();
				dh.deleteAlamat();
				dh.deleteGrandtotal();
			}

			Intent i = new Intent("gomocart.application.com.gomocart.LOAD_SUBMIT_ORDER");
			i.putExtra("success", success);
			i.putExtra("message", message);
			sendBroadcast(i);

			return null;
		}
	}

	public void prosesCekVoucher() {
		new prosesCekVoucher().execute();
	}

	public class prosesCekVoucher extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			openDialogLoading();
		}

		@Override
		protected Void doInBackground(String... urls) {

			boolean success = false;
			String message = "Kesalahan koneksi internet.";

			List<NameValuePair> params = new ArrayList<>();
			params.add(new BasicNameValuePair("kode_voucher", ProsesCheckoutKonfirmasiPemesananFragment.kode_voucher.getText().toString()));

			String url = CommonUtilities.SERVER_URL + "/store/androidCekVoucher.php";
			JSONObject json = new JSONParser().getJSONFromUrl(url, params, null);

			if(json!=null) {
				try {
					success = json.isNull("success")?false:json.getBoolean("success");
					message = json.isNull("message")?message:json.getString("message");

					String kode = json.isNull("kode_voucher")?"":json.getString("kode_voucher");
					double nominal = json.isNull("nominal")?0:json.getDouble("nominal");
					String tipe_voucher = json.isNull("tipe_voucher")?"":json.getString("tipe_voucher");
					String jenis_voucher = json.isNull("jenis_voucher")?"":json.getString("jenis_voucher");

					data_voucher = new voucher(kode, nominal, tipe_voucher, jenis_voucher);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			Intent i = new Intent("gomocart.application.com.gomocart.CEK_VOUCHER");
			i.putExtra("success", success);
			i.putExtra("message", message);
			sendBroadcast(i);

			return null;
		}
	}

	public void loadFieldTotalTransfer() {

		double total_belanja = gtotal.getSub_total();
		double total_pengiriman = gtotal.getPengiriman();

		double total_voucher = 0;
		if(data_voucher!=null) {
			total_voucher = (data_voucher.getTipe_voucher().equalsIgnoreCase("persentase") ? ((data_voucher.getJenis_voucher().equalsIgnoreCase("ongkir") ? total_pengiriman : total_belanja) * (data_voucher.getNominal() * 0.01)) : data_voucher.getNominal());
			if (data_voucher.getJenis_voucher().equalsIgnoreCase("ongkir") && total_voucher > total_pengiriman) {
				total_voucher = total_pengiriman;
			}

			if (data_voucher.getJenis_voucher().equalsIgnoreCase("belanja") && total_voucher > total_belanja) {
				total_voucher = total_belanja;
			}

			ProsesCheckoutKonfirmasiPemesananFragment.kode_voucher.setText(data_voucher.getKode());
		}

		double grand = total_belanja + gtotal.getPengiriman() - total_voucher;
		ProsesCheckoutMetodePembayaranFragment.total_tagihan.setText(CommonUtilities.getCurrencyFormat(grand, "Rp. "));
	}

	private void loadFieldGrandTotal() {

		double total_belanja = gtotal.getSub_total();
		double total_pengiriman = gtotal.getPengiriman();

		double total_voucher = 0;
		if(data_voucher!=null) {
			total_voucher = (data_voucher.getTipe_voucher().equalsIgnoreCase("persentase") ? ((data_voucher.getJenis_voucher().equalsIgnoreCase("ongkir") ? total_pengiriman : total_belanja) * (data_voucher.getNominal() * 0.01)) : data_voucher.getNominal());
			if (data_voucher.getJenis_voucher().equalsIgnoreCase("ongkir") && total_voucher > total_pengiriman) {
				total_voucher = total_pengiriman;
			}

			if (data_voucher.getJenis_voucher().equalsIgnoreCase("belanja") && total_voucher > total_belanja) {
				total_voucher = total_belanja;
			}

			ProsesCheckoutKonfirmasiPemesananFragment.kode_voucher.setText(data_voucher.getKode());
		}

		double grand = total_belanja + gtotal.getPengiriman() - total_voucher;
		ProsesCheckoutKonfirmasiPemesananFragment.total.setText(CommonUtilities.getCurrencyFormat(gtotal.getTotal(), ""));
		ProsesCheckoutKonfirmasiPemesananFragment.voucher.setText(CommonUtilities.getCurrencyFormat(total_voucher, ""));
		ProsesCheckoutKonfirmasiPemesananFragment.subtotal.setText(CommonUtilities.getCurrencyFormat(gtotal.getSub_total(), ""));
		ProsesCheckoutKonfirmasiPemesananFragment.biayakirim.setText(CommonUtilities.getCurrencyFormat(gtotal.getPengiriman(), ""));
		ProsesCheckoutKonfirmasiPemesananFragment.grandtotal.setText(CommonUtilities.getCurrencyFormat(grand, ""));
	}


	public void loadDataPesanan() {
		if(pesananlist.size()==0) {
			new loadDataPesanan().execute();
		} else {
			//ALAMAT PENGIRIMAN
			ProsesCheckoutKonfirmasiPemesananFragment.alamat.setText(data_alamat.getNama()+"\n"+data_alamat.getAlamat()+ " "+data_alamat.getKode_pos()+"\n"+data_alamat.getSubdistrict_name()+", "+data_alamat.getCity_name()+"\n"+data_alamat.getProvince()+"\nTelepon: "+data_alamat.getNo_hp());

			//DROPSHIPER
			ProsesCheckoutKonfirmasiPemesananFragment.linear_dropshiper.setVisibility(data_alamat.getIs_dropship()?View.VISIBLE:View.GONE);
			ProsesCheckoutKonfirmasiPemesananFragment.dropshiper.setText("Dropshiper: "+data_alamat.getDropship_name()+"\nNo. Handphone: "+data_alamat.getDropship_phone());

			//EXPEDISI PENGIRIMAN
			loadKurirLogo(data_kurir.getGambar());
			ProsesCheckoutKonfirmasiPemesananFragment.expedisi.setText(data_kurir.getKode_kurir()+" "+data_kurir.getNama_service()+" ("+data_kurir.getEtd()+" hari)\n"+CommonUtilities.getCurrencyFormat(data_kurir.getNominal(), "Rp. "));

			//PEMBAYARAN
			ProsesCheckoutKonfirmasiPemesananFragment.metode_pembayaran.setText(metode_pembayaran_id==1?"Transfer Bank: ":(metode_pembayaran_id==2?"Kartu Kredit / Debet: ":"Bayar di Tempat: "));
			loadPembayaranLogo(data_bank.getGambar());
			ProsesCheckoutKonfirmasiPemesananFragment.detail_pembayaran.setText(data_bank.getNama_bank()+"\n"+data_bank.getNo_rekening()+" an. "+data_bank.getNama_pemilik_rekening());

			//VOUCHER DAN GRAND TOTAL
			loadFieldGrandTotal();

			ProsesCheckoutKonfirmasiPemesananFragment.loading.setVisibility(View.GONE);
			ProsesCheckoutKonfirmasiPemesananFragment.detail_view.setVisibility(View.VISIBLE);

		}
	}

	public class loadDataPesanan extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			ProsesCheckoutKonfirmasiPemesananFragment.detail_view.setVisibility(View.INVISIBLE);
			ProsesCheckoutKonfirmasiPemesananFragment.loading.setVisibility(View.VISIBLE);
			ProsesCheckoutKonfirmasiPemesananFragment.retry.setVisibility(View.GONE);
		}

		@Override
		protected Void doInBackground(String... urls) {

			pesananlist = new ArrayList<>();
			ArrayList<produk> temp_pesananlist = dh.getCartlist();
			boolean success = false;

			List<NameValuePair> params = new ArrayList<>();
			params.add(new BasicNameValuePair("id_user", data.getId()+""));
			params.add(new BasicNameValuePair("cart", dh.getStringCartlist()));
			params.add(new BasicNameValuePair("kurir", data_kurir.getId_kurir()+""));

			String url = CommonUtilities.SERVER_URL + "/store/androidCartDataStore.php";
			JSONObject json = new JSONParser().getJSONFromUrl(url, params, null);
			if(json!=null) {
				try {
					JSONArray topics = json.isNull("topics")?null:json.getJSONArray("topics");
					for (int i=0; i<topics.length(); i++) {
						JSONObject rec = topics.getJSONObject(i);

						int _id = rec.isNull("_id")?0:rec.getInt("_id");
						int jumlah = rec.isNull("jumlah")?0:rec.getInt("jumlah");
						int berat = rec.isNull("berat")?0:rec.getInt("berat");
						double harga_beli = rec.isNull("harga_beli")?0:rec.getDouble("harga_beli");
						double harga_jual = rec.isNull("harga_jual")?0:rec.getDouble("harga_jual");
						double harga_diskon = rec.isNull("harga_diskon")?0:rec.getDouble("harga_diskon");
						int persen_diskon = rec.isNull("persen_diskon")?0:rec.getInt("persen_diskon");
						double subtotal = rec.isNull("subtotal")?0:rec.getDouble("subtotal");
						double grandtotal = rec.isNull("grandtotal")?0:rec.getDouble("grandtotal");

						for(produk data: temp_pesananlist) {
							if(data.get_id()==_id) {
								data.setQty(jumlah);
								data.setBerat(berat);
								data.setHarga_beli(harga_beli);
								data.setHarga_jual(harga_jual);
								data.setHarga_diskon(harga_diskon);
								data.setPersen_diskon(persen_diskon);
								data.setSubtotal(subtotal);
								data.setGrandtotal(grandtotal);

								temp_pesananlist.remove(data);
								pesananlist.add(data);
								break;
							}
						}
					}

					dh.deleteCartlist();
					dh.insertCartlist(pesananlist);

					success = true;
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			Intent i = new Intent("gomocart.application.com.gomocart.LOAD_DATA_PESANAN");
			i.putExtra("success", success);
			sendBroadcast(i);

			return null;
		}
	}


	public void loadKurirLogo(String gambar) {
		String server = CommonUtilities.SERVER_URL;
		String url = server+"/uploads/ekspedisi/"+gambar;
		imageLoader.displayImage(url, ProsesCheckoutKonfirmasiPemesananFragment.image_expedisi, imageOptionKurir);
	}

	public void loadPembayaranLogo(String gambar) {
		String server = CommonUtilities.SERVER_URL;
		String url = server+"/uploads/bank/"+gambar;
		imageLoader.displayImage(url, ProsesCheckoutKonfirmasiPemesananFragment.image_pembayaran, imageOptionPembayaran);
	}

	public void prosesKonfirmasi() {
		Intent intent = new Intent();
		intent.putExtra("goto", "konfirmasi");
		intent.putExtra("data_order", data_order);
		setResult(RESULT_OK, intent);
		finish();
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			if(menu_selected==4) {
				Intent intent = new Intent();
				setResult(RESULT_OK, intent);
				finish();
			} else {
				menu_selected--;
				if(menu_selected<0) {
					finish();
				}else {
					displayView(menu_selected);
				}
			}

			return false;
		}

		return super.onKeyDown(keyCode, event);
	}
}