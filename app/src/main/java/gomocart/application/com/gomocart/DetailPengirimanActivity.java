package gomocart.application.com.gomocart;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import gomocart.application.com.adapter.PengirimanAdapter;
import gomocart.application.com.libs.CommonUtilities;
import gomocart.application.com.libs.ExpandableHeightListView;
import gomocart.application.com.libs.JSONParser;
import gomocart.application.com.model.pengiriman;

public class DetailPengirimanActivity extends AppCompatActivity {

	Context context;

	ImageView back;

	String kurir, no_resi;
	ExpandableHeightListView listview;
	SwipeRefreshLayout swipeRefreshLayout;
	ProgressBar loading;
	LinearLayout retry;
	Button btnReload;

	ArrayList<pengiriman> list_pengiriman = new ArrayList();
	PengirimanAdapter pengiriman_adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_pengiriman);

		context = DetailPengirimanActivity.this;
		
		back      = (ImageView) findViewById(R.id.back);
		listview  = (ExpandableHeightListView) findViewById(R.id.lisview);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        loading = (ProgressBar) findViewById(R.id.pgbarLoading);
        retry     = (LinearLayout) findViewById(R.id.loadMask);
		btnReload = (Button) findViewById(R.id.btnReload);

		swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				swipeRefreshLayout.setRefreshing(false);
				LoadDataPengiriman();
			}
		});

		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		btnReload.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				LoadDataPengiriman();
			}
		});

		pengiriman_adapter = new PengirimanAdapter(context, list_pengiriman);
		listview.setAdapter(pengiriman_adapter);

		if(savedInstanceState==null) {
			kurir   = getIntent().getStringExtra("kurir");
			no_resi = getIntent().getStringExtra("no_resi");
		}

		//Toast.makeText(context, kurir+" --- "+no_resi, Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		registerReceiver(mHandleLoadListPengirimanReceiver, new IntentFilter("gomocart.application.com.gomocart.LOAD_DATA_PENGIRIMAN"));
		LoadDataPengiriman();
	}


	@Override
	protected void onPause() {
		try {
			unregisterReceiver(mHandleLoadListPengirimanReceiver);
		} catch (Exception e) {
			e.printStackTrace();
		}

		super.onPause();
	}


	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		try {
			unregisterReceiver(mHandleLoadListPengirimanReceiver);
		} catch (Exception e) {
			e.printStackTrace();
		}

		super.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putString("kurir", kurir);
		savedInstanceState.putString("no_resi", no_resi);
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		kurir = savedInstanceState.getString("kurir");
		no_resi = savedInstanceState.getString("no_resi");
	}

	private final BroadcastReceiver mHandleLoadListPengirimanReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Boolean success = intent.getBooleanExtra("success", false);

			loading.setVisibility(View.GONE);
			if(!success) retry.setVisibility(View.VISIBLE);

			pengiriman_adapter.UpdatePengirimanAdapter(list_pengiriman);
		}
	};


	public void LoadDataPengiriman() {
		new LoadDataPengiriman().execute();
	}

	public class LoadDataPengiriman extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			loading.setVisibility(View.VISIBLE);
			retry.setVisibility(View.GONE);
		}

		@Override
		protected Void doInBackground(String... urls) {

			Boolean success = false;
			list_pengiriman = new ArrayList<>();
			
			String url = CommonUtilities.SERVER_URL + "/store/androidCekResi.php";
			List<NameValuePair> params = new ArrayList<>();
			params.add(new BasicNameValuePair("kurir", kurir));
			params.add(new BasicNameValuePair("no_resi", no_resi));
			JSONObject json = new JSONParser().getJSONFromUrl(url, params, null);
			if(json!=null) {
				try {
					JSONArray topics = json.isNull("topics")?null:json.getJSONArray("topics");
					for (int i=0; i<topics.length(); i++) {
						JSONObject rec = topics.getJSONObject(i);

						String kode       = rec.isNull("manifest_code")?"":rec.getString("manifest_code");
						String keterangan = rec.isNull("manifest_description")?"":rec.getString("manifest_description");
						String tanggal    = rec.isNull("manifest_date")?"":rec.getString("manifest_date");
						String jam        = rec.isNull("manifest_time")?"":rec.getString("manifest_time");
						String city_name  = rec.isNull("city_name")?"":rec.getString("city_name");

						list_pengiriman.add(new pengiriman(kode, keterangan, tanggal, jam, city_name));
					}
					
					success = true;
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			Intent i = new Intent("gomocart.application.com.gomocart.LOAD_DATA_PENGIRIMAN");
			i.putExtra("success", success);
			sendBroadcast(i);

			return null;
		}
	}
}
