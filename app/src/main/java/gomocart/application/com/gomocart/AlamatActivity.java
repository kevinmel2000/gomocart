package gomocart.application.com.gomocart;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import customfonts.MyEditText;
import customfonts.MyTextView;
import gomocart.application.com.adapter.SelectAlamatAdapter;
import gomocart.application.com.libs.CommonUtilities;
import gomocart.application.com.libs.JSONParser;
import gomocart.application.com.model.alamat;
import gomocart.application.com.model.user;

public class AlamatActivity extends AppCompatActivity {

	Context context;
	user data;

	ListView listview;
	ImageView back, search, sortby, tambah;

	LinearLayout linear_utama;
	RelativeLayout linear_search;

	MyEditText edit_search;
	ImageButton btn_close;

	SwipeRefreshLayout swipeRefreshLayout;
	ProgressBar loading;
	LinearLayout retry;
	Button btnReload;

	Dialog dialog_sort_by_alamat;
	ImageView radioAZ, radioZA;
	LinearLayout linearAZ, linearZA;
	public static String sort_by_alamat;

	Dialog dialog_delete_alamat;
	MyTextView btn_delete_alamat_no, btn_delete_alamat_yes;
	alamat delete_selected_alamat;

	//ProgressDialog progDailog;
	Dialog dialog_loading;
	//FrameLayout frame_loading;

	final int RESULT_FROM_EDIT_ALAMAT = 13;

	public static ArrayList<alamat> alamatlist = new ArrayList<>();
	public static ArrayList<alamat> alamatlist_display = new ArrayList<>();
	public static SelectAlamatAdapter alamatAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alamat);
		context = AlamatActivity.this;
		data = CommonUtilities.getSettingUser(context);

		search = (ImageView) findViewById(R.id.search);
		sortby = (ImageView) findViewById(R.id.orderby);
		tambah = (ImageView) findViewById(R.id.tambah);
		back = (ImageView) findViewById(R.id.back);
		listview = (ListView) findViewById(R.id.listview);
		swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
		loading = (ProgressBar) findViewById(R.id.pgbarLoading);
		retry = (LinearLayout) findViewById(R.id.loadMask);
		btnReload = (Button) findViewById(R.id.btnReload);

		linear_utama  = (LinearLayout) findViewById(R.id.linear_utama);
		linear_search = (RelativeLayout) findViewById(R.id.cardview_search);
		edit_search   = (MyEditText) findViewById(R.id.searchtext);
		btn_close     = (ImageButton) findViewById(R.id.btn_close);

		swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				swipeRefreshLayout.setRefreshing(false);
				loadAlamatlist();
			}
		});

		sortby.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				openDialogSortByAlamat();
			}
		});

		search.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				linear_search.setVisibility(View.VISIBLE);
				linear_utama.setVisibility(View.GONE);
				edit_search.requestFocus();

				View view = getCurrentFocus();
				if (view != null) {
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
				}
			}
		});

		btn_close.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				edit_search.setText("");
				linear_search.setVisibility(View.GONE);
				linear_utama.setVisibility(View.VISIBLE);

				View view = getCurrentFocus();
				if (view != null) {
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
				}

				showListAlamat();
			}
		});

		edit_search.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(s.length() != 0) {
					showListAlamat();
				}
			}
		});

		btnReload.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				loadAlamatlist();
			}
		});

		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		tambah.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				addNewAlamat();
			}
		});

		dialog_delete_alamat = new Dialog(context);
		dialog_delete_alamat.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog_delete_alamat.setCancelable(true);
		dialog_delete_alamat.setContentView(R.layout.delete_alamat_dialog);

		btn_delete_alamat_yes = (MyTextView) dialog_delete_alamat.findViewById(R.id.btn_yes);
		btn_delete_alamat_yes.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				new prosesDeleteAlamat(delete_selected_alamat).execute();

			}
		});

		btn_delete_alamat_no = (MyTextView) dialog_delete_alamat.findViewById(R.id.btn_no);
		btn_delete_alamat_no.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog_delete_alamat.dismiss();

			}
		});

		dialog_sort_by_alamat = new Dialog(context);
		dialog_sort_by_alamat.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog_sort_by_alamat.setCancelable(true);
		dialog_sort_by_alamat.setContentView(R.layout.sortby_alamat_dialog);

		radioAZ = (ImageView) dialog_sort_by_alamat.findViewById(R.id.radioAZ);
		linearAZ = (LinearLayout) dialog_sort_by_alamat.findViewById(R.id.linearAZ);
		linearAZ.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog_sort_by_alamat.dismiss();
				sortAlamtBy(1);

				radioAZ.setImageResource(R.drawable.radioblack);
				radioZA.setImageResource(R.drawable.radiouncheked);
			}
		});

		radioZA = (ImageView) dialog_sort_by_alamat.findViewById(R.id.radioZA);
		linearZA = (LinearLayout) dialog_sort_by_alamat.findViewById(R.id.linearZA);
		linearZA.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog_sort_by_alamat.dismiss();
				sortAlamtBy(1);

				radioAZ.setImageResource(R.drawable.radiouncheked);
				radioZA.setImageResource(R.drawable.radioblack);
			}
		});

		//progDailog = new ProgressDialog(context);
		//progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		//progDailog.setCancelable(false);
		dialog_loading = new Dialog(context);
		dialog_loading.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog_loading.setCancelable(false);
		dialog_loading.setContentView(R.layout.loading_dialog);
		//frame_loading = (FrameLayout) dialog_loading.findViewById(R.id.frame_loading);

		alamatlist = new ArrayList<>();
		alamatAdapter = new SelectAlamatAdapter(context, alamatlist);
		listview.setAdapter(alamatAdapter);
	}

	@Override
	protected void onDestroy() {
		try {
			unregisterReceiver(mHandleLoadListAlamatReceiver);

		} catch (Exception e) {
			e.printStackTrace();
		}

		super.onDestroy();
	}

	@Override
	protected void onPause() {
		try {
			unregisterReceiver(mHandleLoadListAlamatReceiver);


		} catch (Exception e) {
			e.printStackTrace();
		}

		super.onPause();
	}

	@Override
	protected void onResume() {
		registerReceiver(mHandleLoadListAlamatReceiver, new IntentFilter("gomocart.application.com.gomocart.LOAD_DATA_ALAMAT"));

		loadAlamatlist();
		super.onResume();
	}

	private final BroadcastReceiver mHandleLoadListAlamatReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Boolean success = intent.getBooleanExtra("success", false);

			loading.setVisibility(View.GONE);
			if(!success) retry.setVisibility(View.VISIBLE);

			showListAlamat();
		}
	};

	private void sortAlamtBy(int sortby) {
		sort_by_alamat = String.valueOf(sortby);

		alamatlist = new ArrayList<>();
		alamatlist_display = new ArrayList<>();

		showListAlamat();
		loadAlamatlist();
	}

	public void selectedAlamat(alamat data_selected_alamat) {
		Intent intent = new Intent();
		intent.putExtra("alamat", data_selected_alamat);
		setResult(RESULT_OK, intent);
		finish();
	}

	
	public void openDialogSortByAlamat() {
		dialog_sort_by_alamat.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		dialog_sort_by_alamat.show();
	}

	public void showListAlamat() {

		String keyword = edit_search.getText().toString();
		if(keyword.length()==0) {
			alamatlist_display = alamatlist;
		} else {
			alamatlist_display = new ArrayList<>();
			keyword = keyword.toLowerCase();
			for (alamat dt : alamatlist) {
				if(dt.getNama().toLowerCase().contains(keyword) || dt.getAlamat().toLowerCase().contains(keyword)) {
					alamatlist_display.add(dt);
				}
			}
		}

		alamatAdapter.UpdateSelectAlamatAdapter(alamatlist_display);
	}
	
	public void loadAlamatlist() {
		new loadAlamatlist().execute();
	}

	public class loadAlamatlist extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			loading.setVisibility(View.VISIBLE);
			retry.setVisibility(View.GONE);
		}

		@Override
		protected Void doInBackground(String... urls) {

			alamatlist = new ArrayList<>();
			alamatlist_display = new ArrayList<>();
			boolean success = false;

			List<NameValuePair> params = new ArrayList<>();
			params.add(new BasicNameValuePair("user_id", data.getId()+""));
			params.add(new BasicNameValuePair("sort_by", sort_by_alamat+""));


			String url = CommonUtilities.SERVER_URL + "/store/androidAlamatDataStore.php";
			JSONObject json = new JSONParser().getJSONFromUrl(url, params, null);
			if(json!=null) {
				try {

					JSONArray data = json.isNull("topics")?null:json.getJSONArray("topics");
					for (int i=0; i<data.length(); i++) {
						JSONObject rec= data.getJSONObject(i);

						int id = rec.isNull("id")?0:rec.getInt("id");
						String nama = rec.isNull("nama")?"":rec.getString("nama");
						String alamat = rec.isNull("alamat")?"":rec.getString("alamat");
						int province_id = rec.isNull("id_propinsi")?0:rec.getInt("id_propinsi");
						String province = rec.isNull("nama_propinsi")?"":rec.getString("nama_propinsi");
						int city_id = rec.isNull("id_kota")?0:rec.getInt("id_kota");
						String city = rec.isNull("nama_kota")?"":rec.getString("nama_kota");
						int subdistrict_id = rec.isNull("id_kecamatan")?0:rec.getInt("id_kecamatan");
						String subdistrict = rec.isNull("nama_kecamatan")?"":rec.getString("nama_kecamatan");
						String kode_pos = rec.isNull("kode_pos")?"":rec.getString("kode_pos");
						String no_hp = rec.isNull("no_hp")?"":rec.getString("no_hp");
						boolean as_defult = rec.isNull("as_default")?false:(rec.getInt("as_default")==1);
						gomocart.application.com.model.alamat data_alamat = new alamat(id, nama, alamat, province_id, province, city_id, city, subdistrict_id, subdistrict, kode_pos, no_hp, as_defult, false, "", "", "");
						
						alamatlist.add(data_alamat);
						alamatlist_display.add(data_alamat);
					}

					success = true;

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			Intent i = new Intent("gomocart.application.com.gomocart.LOAD_DATA_ALAMAT");
			i.putExtra("success", success);
			sendBroadcast(i);

			return null;
		}
	}

	public void addNewAlamat() {
		Intent intent = new Intent(context, EditAlamatActivity.class);
		startActivityForResult(intent, RESULT_FROM_EDIT_ALAMAT);
	}

	public void editSelectedAlamat(alamat data_alamat) {
		Intent intent = new Intent(context, EditAlamatActivity.class);
		intent.putExtra("alamat", data_alamat);
		startActivityForResult(intent, RESULT_FROM_EDIT_ALAMAT);
	}

	public void deleteSelectedAlamat(alamat data_alamat) {
		delete_selected_alamat = data_alamat;
		dialog_delete_alamat.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		dialog_delete_alamat.show();
	}

	public void utamakanSelectedAlamat(alamat data_alamat) {
		new prosesUtamakanAlamat(data_alamat).execute();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data_intent) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data_intent);

		if (resultCode == RESULT_OK) {
			switch (requestCode) {
				case RESULT_FROM_EDIT_ALAMAT:
					alamat select_alamat = (alamat) data_intent.getSerializableExtra("alamat");

					if(select_alamat!=null) {
						for (alamat data_alamat : alamatlist) {
							if (data_alamat.getId()==select_alamat.getId()) {
								if(select_alamat.getAsDefaultAlamat()) {
									for(int i=0; i<alamatlist.size(); i++) {
										alamatlist.get(i).setAs_default(false);
									}
								}

								alamatlist.set(alamatlist.indexOf(data_alamat), select_alamat);
								showListAlamat();

								return;
							}
						}

						if(select_alamat.getAsDefaultAlamat()) {
							for(int i=0; i<alamatlist.size(); i++) {
								alamatlist.get(i).setAs_default(false);
							}
						}

						alamatlist.add(select_alamat);
						showListAlamat();
					}

					break;
			}
		}
	}

	class prosesUtamakanAlamat extends AsyncTask<String, Void, JSONObject> {

		alamat data_alamat;
		boolean success;
		String message;

		prosesUtamakanAlamat(alamat data_alamat) {
			this.data_alamat = data_alamat;
		}

		@Override
		protected void onPreExecute() {

			super.onPreExecute();

			openDialogLoading();
			//progDailog.setMessage("Delete...");
			//progDailog.show();
		}

		@Override
		protected JSONObject doInBackground(String... urls) {
			List<NameValuePair> params = new ArrayList<>();
			params.add(new BasicNameValuePair("id", data_alamat.getId()+""));
			String url = CommonUtilities.SERVER_URL + "/store/androidUtamakanAlamat.php";
			JSONObject json = new JSONParser().getJSONFromUrl(url, params, null);

			return json;
		}

		@Deprecated
		@Override
		protected void onPostExecute(JSONObject result) {

			dialog_loading.dismiss();

			success = false;
			message = "Server tidak merespon!";
			if(result!=null) {
				try {
					success = result.isNull("success")?false:result.getBoolean("success");
					message = result.isNull("message")?message:result.getString("message");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if(success) {
				for(int i=0; i<alamatlist.size(); i++) {
					alamatlist.get(i).setAs_default(alamatlist.get(i).getId()==data_alamat.getId());
				}

				alamatAdapter.UpdateSelectAlamatAdapter(alamatlist);
			}
		}
	}

	class prosesDeleteAlamat extends AsyncTask<String, Void, JSONObject> {

		alamat data_alamat;
		boolean success;
		String message;

		prosesDeleteAlamat(alamat data_alamat) {
			this.data_alamat = data_alamat;
		}

		@Override
		protected void onPreExecute() {

			super.onPreExecute();

			openDialogLoading();
			//progDailog.setMessage("Delete...");
			//progDailog.show();
		}

		@Override
		protected JSONObject doInBackground(String... urls) {
			List<NameValuePair> params = new ArrayList<>();
			params.add(new BasicNameValuePair("id", data_alamat.getId()+""));
			String url = CommonUtilities.SERVER_URL + "/store/androidDeleteAlamat.php";
			JSONObject json = new JSONParser().getJSONFromUrl(url, params, null);

			return json;
		}

		@Deprecated
		@Override
		protected void onPostExecute(JSONObject result) {

			dialog_loading.dismiss();

			success = false;
			message = "Server tidak merespon!";
			if(result!=null) {
				try {
					success = result.isNull("success")?false:result.getBoolean("success");
					message = result.isNull("message")?message:result.getString("message");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(success) {
				dialog_delete_alamat.dismiss();
				for(int i=0; i<alamatlist.size(); i++) {
					if(alamatlist.get(i).getId()==data_alamat.getId()) {
						alamatlist.remove(i);
						showListAlamat();

						break;
					}
				}

			}
		}
	}

	public void openDialogLoading() {
		dialog_loading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		dialog_loading.show();
	}
}
