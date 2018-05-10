package gomocart.application.com.gomocart;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import customfonts.MyTextView;
import gomocart.application.com.adapter.MessageAdapter;
import gomocart.application.com.libs.CommonUtilities;
import gomocart.application.com.libs.DatabaseHandler;
import gomocart.application.com.libs.JSONParser;
import gomocart.application.com.libs.MCrypt;
import gomocart.application.com.libs.ResizableImageView;
import gomocart.application.com.model.perpesanan;
import gomocart.application.com.model.message;
import gomocart.application.com.model.message_list;
import gomocart.application.com.model.produk;
import gomocart.application.com.model.user;

@SuppressLint("InflateParams")
public class MessageActivity extends AppCompatActivity {
	
	Context context;

	ArrayList<message> listMessage = new ArrayList<>();
	MessageAdapter messageAdapter;

	LinearLayout lin_produk;
	LinearLayout llMsgCompose;
	ResizableImageView gambar;
	MyTextView nama_produk;
	MyTextView harga_produk;
	MyTextView beli_produk;

	EditText inputMsg;
	ImageView btn_send;
	ProgressBar progres_save;
	ListView listViewMessage;
	ProgressBar loading;
	LinearLayout retry;
	Button btnReload;

    DisplayImageOptions imageOption;
	ImageLoader imageLoader;

	Dialog dialog_loading;

    Dialog dialog_informasi;
    MyTextView btn_ok;
    MyTextView text_title;
    MyTextView text_informasi;

	DatabaseHandler dh;
	int total_cart;
	int total_wishlist;

	ImageView toolbar_cart;
	MyTextView number_cart;

	ImageView toolbar_wish;
	MyTextView number_wish;

    user data;
	int id_produk;
	perpesanan data_perpesanan;
	produk data_produk;

	Boolean is_get_message = false;

	ImageView back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);

		context = MessageActivity.this;
		dh = new DatabaseHandler(context);
		data = CommonUtilities.getSettingUser(context);

		toolbar_cart  = (ImageView) findViewById(R.id.toolbar_image_cart);
		number_cart   = (MyTextView) findViewById(R.id.number_cart);
		updateTotalCartlist();

		toolbar_cart.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("produk", data_produk);
				intent.putExtra("goto", "cart_list");
				setResult(RESULT_OK, intent);
				finish();
			}
		});
		number_cart.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("produk", data_produk);
				intent.putExtra("goto", "cart_list");
				setResult(RESULT_OK, intent);
				finish();
			}
		});

		toolbar_wish = (ImageView) findViewById(R.id.toolbar_image_wishlist);
		number_wish = (MyTextView) findViewById(R.id.number_wishlist);
		updateTotalWishlist();

		toolbar_wish.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("produk", data_produk);
				intent.putExtra("goto", "wish_list");
				setResult(RESULT_OK, intent);
				finish();
			}
		});

		number_wish.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("produk", data_produk);
				intent.putExtra("goto", "wish_list");
				setResult(RESULT_OK, intent);
				finish();
			}
		});

		CommonUtilities.initImageLoader(context);
		imageOption = CommonUtilities.getOptionsImage(R.drawable.logo_grayscale, R.drawable.logo_grayscale);
		imageLoader = ImageLoader.getInstance();

		messageAdapter = new MessageAdapter(context, listMessage);
		lin_produk = (LinearLayout) findViewById(R.id.produk);
		llMsgCompose = (LinearLayout) findViewById(R.id.llMsgCompose);
		gambar = (ResizableImageView) findViewById(R.id.image);
        nama_produk = (MyTextView) findViewById(R.id.nama_produk);
		harga_produk = (MyTextView) findViewById(R.id.harga_produk);
		beli_produk = (MyTextView) findViewById(R.id.beli_produk);
		lin_produk.setVisibility(View.INVISIBLE);

		loading = (ProgressBar) findViewById(R.id.pgbarLoading);
		retry = (LinearLayout) findViewById(R.id.loadMask);
		btnReload = (Button) findViewById(R.id.btnReload);

		btnReload.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				loadDataMessage();
			}
		});

		beli_produk.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("produk", data_produk);
				intent.putExtra("goto", "detail_produk");
				setResult(RESULT_OK, intent);
				finish();
			}
		});

		listViewMessage = (ListView) findViewById(R.id.listMessage);
		inputMsg = (EditText) findViewById(R.id.inputMsg);
		btn_send = (ImageView) findViewById(R.id.btnSend);
		progres_save = (ProgressBar) findViewById(R.id.progres_save);

		inputMsg.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEND) {
					new prosesAddKomentar().execute();
					return true;
				}
				return false;
			}
		});

		btn_send.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new prosesAddKomentar().execute();
			}
		});

		listViewMessage.setAdapter(messageAdapter);
		back          = (ImageView) findViewById(R.id.btn_back);
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("is_get_message", is_get_message);
				setResult(RESULT_OK, intent);
				finish();
			}
		});

		dialog_loading = new Dialog(context);
		dialog_loading.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog_loading.setCancelable(false);
		dialog_loading.setContentView(R.layout.loading_dialog);
		//frame_loading = (FrameLayout) dialog_loading.findViewById(R.id.frame_loading);


		if(savedInstanceState==null) {
			data_produk = (produk) getIntent().getSerializableExtra("produk");
			id_produk   = getIntent().getIntExtra("id_produk", data_produk!=null?data_produk.getId():0);
			loadDataMessage();
		}

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

    }

	public void updateTotalCartlist() {
		total_cart = dh.getTotalCart();
		number_cart.setText(total_cart+"");
		number_cart.setVisibility(total_cart>0?View.VISIBLE:View.INVISIBLE);
	}

	public void updateTotalWishlist() {
		total_wishlist = dh.getTotalWishlist();
		number_wish.setText(total_wishlist+"");
		number_wish.setVisibility(total_wishlist>0?View.VISIBLE:View.INVISIBLE);
	}

	@Override
	protected void onResume() {
		registerReceiver(mHandleLoadDataMessageReceiver,  new IntentFilter("gomocart.application.com.gomocart.LOAD_MESSAGE"));
		registerReceiver(mHandleNewMessageReceiver,  new IntentFilter("gomocart.application.com.gomocart.NEW_MESSAGE"));
		registerReceiver(mHandleSendMessageReceiver,  new IntentFilter("gomocart.application.com.gomocart.SEND_MESSAGE"));

		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		try {
			CommonUtilities.setCurrentLaporan(context, new perpesanan(0, 0, "", "", "", "", "", 0, "", "", 0));
			unregisterReceiver(mHandleLoadDataMessageReceiver);
			unregisterReceiver(mHandleNewMessageReceiver);
			unregisterReceiver(mHandleSendMessageReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		super.onPause();
	}
	
	@Override
    protected void onDestroy() {
        super.onDestroy();

		try {
			CommonUtilities.setCurrentLaporan(context, new perpesanan(0, 0, "", "", "", "", "", 0,  "", "", 0));
			unregisterReceiver(mHandleLoadDataMessageReceiver);
			unregisterReceiver(mHandleNewMessageReceiver);
			unregisterReceiver(mHandleSendMessageReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public void playBeep() {

		try {
			Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			Ringtone r = RingtoneManager.getRingtone(context, notification);
			r.play();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void appendMessage(message m) {
		listMessage.add(m);
		messageAdapter.UpdatemessageAdapter(listMessage);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

			Intent intent = new Intent();
			intent.putExtra("is_get_message", is_get_message);
			setResult(RESULT_OK, intent);
			finish();

			return false;
		}
		
		return super.onKeyDown(keyCode, event);
	}

	/*public void startLoadDataMessage() {
		listMessage = new ArrayList<>();
		messageAdapter.UpdatemessageAdapter(listMessage);
		loadDatamessage();
	}*/

	public void loadDataMessage() {
		new loadDataMessage().execute();
	}
	
	public class loadDataMessage extends AsyncTask<String, Void, ArrayList<message>> {
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			loading.setVisibility(View.VISIBLE);
			retry.setVisibility(View.GONE);
			listViewMessage.setVisibility(View.GONE);
			lin_produk.setVisibility(View.GONE);
			llMsgCompose.setVisibility(View.GONE);
	    }
		 
		@Override
	    protected ArrayList<message> doInBackground(String... urls) {

			JSONParser token_json = new JSONParser();
			JSONObject token = token_json.getJSONFromUrl(CommonUtilities.SERVER_URL + "/store/token.php", null, null);
			String cookies = token_json.getCookies();

			String security_code = "";
			try {
				security_code = token.isNull("security_code")?"":token.getString("security_code");
				MCrypt mCrypt = new MCrypt();
				security_code = new String(mCrypt.decrypt(security_code));
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

	    	ArrayList<message> result = null;
			if(security_code.length()>0) {
				String url = CommonUtilities.SERVER_URL + "/store/androidMessageUserDataStore.php";

				List<NameValuePair> params = new ArrayList<>();
				//params.add(new BasicNameValuePair("page", next_page+""));
				//params.add(new BasicNameValuePair("id_laporan", (data_perpesanan==null?0:data_perpesanan.getId()) + ""));
				params.add(new BasicNameValuePair("id_produk", id_produk + ""));
				params.add(new BasicNameValuePair("id_user", data.getId() + ""));
				params.add(new BasicNameValuePair("id_guest", CommonUtilities.getGuestId(context) + ""));
				params.add(new BasicNameValuePair("security_code", security_code));
				JSONObject json = new JSONParser().getJSONFromUrl(url, params, cookies);

				if(json!=null) {
					try {

						String avatar_admin = json.isNull("avatar_admin")?"default_avatar.png":json.getString("avatar_admin");
						
						result = new ArrayList<>();
						JSONArray topics = json.isNull("topics")?null:json.getJSONArray("topics");
						for (int i=0; i<topics.length(); i++) {
							JSONObject rec = topics.getJSONObject(i);

							int id = rec.isNull("id")?null:rec.getInt("id");
							String nama = rec.isNull("nama")?null:rec.getString("nama");
							String telepon = rec.isNull("telepon")?null:rec.getString("telepon");
							String photo = rec.isNull("photo")?null:rec.getString("photo");

							String message = rec.isNull("message")?null:rec.getString("message");
							String datetime = rec.isNull("datetime")?null:rec.getString("datetime");
							Boolean is_self = rec.isNull("is_self")?false:rec.getBoolean("is_self");
							Boolean is_sent = rec.isNull("is_sent")?false:rec.getBoolean("is_sent");

							result.add(new message(id, nama, telepon, is_self?photo:avatar_admin, datetime, message, is_self, is_sent));
						}

						JSONObject produk = json.isNull("produk")?null:json.getJSONObject("produk");
						if(produk!=null) {
							int id = produk.isNull("id")?0:produk.getInt("id");
							String kode = produk.isNull("kode")?"":produk.getString("kode");
							String nama = produk.isNull("nama")?"":produk.getString("nama");
							int id_category = produk.isNull("id_category")?0:produk.getInt("id_category");
							String category_name = produk.isNull("category_name")?"":produk.getString("category_name");
							String penjelasan = produk.isNull("penjelasan")?"":produk.getString("penjelasan");
							String foto1_produk = produk.isNull("foto1_produk")?"":produk.getString("foto1_produk");
							double harga_beli = produk.isNull("harga_beli")?0:produk.getDouble("harga_beli");
							double harga_jual = produk.isNull("harga_jual")?0:produk.getDouble("harga_jual");
							double harga_diskon = produk.isNull("harga_diskon")?0:produk.getDouble("harga_diskon");
							int persen_diskon = produk.isNull("persen_diskon")?0:produk.getInt("persen_diskon");
							int berat = produk.isNull("berat")?0:produk.getInt("berat");
							String list_ukuran = produk.isNull("list_ukuran")?"":produk.getString("list_ukuran");
							String ukuran = produk.isNull("ukuran")?"":produk.getString("ukuran");
							String list_warna = produk.isNull("list_warna")?"":produk.getString("list_warna");
							String warna = produk.isNull("warna")?"":produk.getString("warna");
							int qty = produk.isNull("qty")?0:produk.getInt("qty");
							int max_qty = produk.isNull("max_qty")?0:produk.getInt("max_qty");
							int minimum_pesan = produk.isNull("minimum_pesan")?1:produk.getInt("minimum_pesan");
							int produk_promo = produk.isNull("produk_promo")?0:produk.getInt("produk_promo");
							int produk_featured = produk.isNull("produk_featured")?0:produk.getInt("produk_featured");
							int produk_terbaru = produk.isNull("produk_terbaru")?0:produk.getInt("produk_terbaru");
							int produk_preorder = produk.isNull("produk_preorder")?0:produk.getInt("produk_preorder");
							int produk_soldout = produk.isNull("produk_soldout")?0:produk.getInt("produk_soldout");
							int produk_grosir = produk.isNull("produk_grosir")?0:produk.getInt("produk_grosir");
							int produk_freeongkir = produk.isNull("produk_freeongkir")?0:produk.getInt("produk_freeongkir");
							int rating = produk.isNull("rating")?0:produk.getInt("rating");
							int responden = produk.isNull("responden")?0:produk.getInt("responden");
							int review = produk.isNull("review")?0:produk.getInt("review");

							data_produk = new produk(id, kode, nama, id_category, category_name, penjelasan, foto1_produk, harga_beli, harga_jual, harga_diskon, persen_diskon, berat, list_ukuran, ukuran, list_warna, warna, qty, max_qty, minimum_pesan, dh.getIdWishlist(id)>0, produk_promo, produk_featured, produk_terbaru, produk_preorder, produk_soldout, produk_grosir, produk_freeongkir, rating, responden, review);
						}
						
						JSONObject lap = json.isNull("perpesanan")?null:json.getJSONObject("perpesanan");
						if(lap==null) {
							data_perpesanan = new perpesanan(0, data_produk.getId(), data_produk.getKode(), data_produk.getNama(), data_produk.getFoto1_produk(), "", "", 0, "", "", 0);
						} else {
							int id = lap.isNull("id") ? 0 : lap.getInt("id");
							int id_produk = lap.isNull("id_produk") ? 0 : lap.getInt("id_produk");
							String kode = lap.isNull("kode") ? "" : lap.getString("kode");
							String nama = lap.isNull("nama") ? "" : lap.getString("nama");
							String gambar = lap.isNull("gambar") ? "" : lap.getString("gambar");
							String pesan = lap.isNull("pesan") ? "" : lap.getString("pesan");
							String tanggal = lap.isNull("tanggal_jam") ? "" : lap.getString("tanggal_jam");
							int from_id = lap.isNull("from_id") ? 0 : lap.getInt("from_id");
							String from_nama = lap.isNull("from_nama") ? "" : lap.getString("from_nama");
							String from_photo = lap.isNull("from_photo") ? "" : lap.getString("from_photo");
							int total_unread = lap.isNull("total_unread") ? 0 : lap.getInt("total_unread");

							data_perpesanan = new perpesanan(id, id_produk, kode, nama, gambar, tanggal, pesan, from_id, from_nama, from_photo, total_unread);
						}
						CommonUtilities.setCurrentLaporan(context, data_perpesanan);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
	    	return result;
	    }

		
	    @Override
	    protected void onPostExecute(ArrayList<message> result) {
	    
	    	Boolean success = result!=null;
	    	if(result==null) result = new ArrayList<>();
	    	ArrayList<message_list> temp = new ArrayList<>();
	    	temp.add(new message_list(result));

			//TotalUnread -= data_perpesanan.getTotal_unread();
			//CommonUtilities.setTotalUnread(context, TotalUnread);
			//ShortcutBadger.applyCount(context, TotalUnread);
			is_get_message = success;

	    	Intent i = new Intent("gomocart.application.com.gomocart.LOAD_MESSAGE");
	    	i.putExtra("message_list", temp);
	    	i.putExtra("success", success);
	    	sendBroadcast(i);	
	    }
	}

	private final BroadcastReceiver mHandleLoadDataMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Boolean success = intent.getBooleanExtra("success", false);
			ArrayList<message_list> temp = intent.getParcelableArrayListExtra("message_list");
			ArrayList<message> result = temp.get(0).getListData();

			loading.setVisibility(View.GONE);
			if(!success) {
				retry.setVisibility(View.VISIBLE);
			} else {
				lin_produk.setVisibility(View.VISIBLE);
				listViewMessage.setVisibility(View.VISIBLE);
				llMsgCompose.setVisibility(View.VISIBLE);
			}

			for (message flist : result) {
				listMessage.add(flist);
			}

			messageAdapter.UpdatemessageAdapter(listMessage);

			nama_produk.setText(data_produk.getNama());
			harga_produk.setText(CommonUtilities.getCurrencyFormat(data_produk.getHarga_jual(), "Rp. ")+" "+(data_produk.getPersen_diskon()>0?" (-"+data_produk.getPersen_diskon()+"%)"+CommonUtilities.getCurrencyFormat((data_produk.getPersen_diskon()*0.01)*data_produk.getHarga_jual(), "Rp. "):""));
			String server = CommonUtilities.SERVER_URL;
			String url = server+"/uploads/produk/"+data_produk.getFoto1_produk();
			imageLoader.displayImage(url, gambar, imageOption);
			lin_produk.setVisibility(View.VISIBLE);
		}
	};

	private final BroadcastReceiver mHandleNewMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			//Toast.makeText(context, "New Message!", Toast.LENGTH_SHORT).show();
			message m = (message) intent.getSerializableExtra("message");
			appendMessage(m);
			playBeep();
		}
	};

	private final BroadcastReceiver mHandleSendMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			message m = (message) intent.getSerializableExtra("message");
			appendMessage(m);
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				Intent intent = new Intent();
				intent.putExtra("is_get_message", is_get_message);
				setResult(RESULT_OK, intent);
				finish();

				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	class prosesAddKomentar extends AsyncTask<String, Void, JSONObject> {

		boolean success;
		String message;
		message datamsg;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			btn_send.setVisibility(View.GONE);
			progres_save.setVisibility(View.VISIBLE);
		}

		@Override
		protected JSONObject doInBackground(String... urls) {
			JSONParser token_json = new JSONParser();
			JSONObject token = token_json.getJSONFromUrl(CommonUtilities.SERVER_URL + "/store/token.php", null, null);
			String cookies = token_json.getCookies();

			String security_code = "";
			try {
				security_code = token.isNull("security_code")?"":token.getString("security_code");
				MCrypt mCrypt = new MCrypt();
				security_code = new String(mCrypt.decrypt(security_code));
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

			JSONObject jObj = null;
			if(security_code.length()>0) {
				try {
					String url = CommonUtilities.SERVER_URL + "/store/androidAddKomentarUser.php";

					HttpClient httpclient = new DefaultHttpClient();
					HttpPost httppost = new HttpPost(url);

					MultipartEntity reqEntity = new MultipartEntity();
					reqEntity.addPart("user_id", new StringBody(data.getId() + ""));
					reqEntity.addPart("guest_id", new StringBody(CommonUtilities.getGuestId(context) + ""));
					reqEntity.addPart("id_produk", new StringBody(data_perpesanan.getId_produk()+""));
					reqEntity.addPart("id_laporan", new StringBody(data_perpesanan.getId()+""));
					reqEntity.addPart("isi_komentar", new StringBody(inputMsg.getText().toString()));
					reqEntity.addPart("security_code", new StringBody(security_code));

					httppost.setHeader("Cookie", cookies);
					httppost.setEntity(reqEntity);
					HttpResponse response = httpclient.execute(httppost);
					HttpEntity resEntity = response.getEntity();
					InputStream is = resEntity.getContent();

					BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
					StringBuilder sb = new StringBuilder();
					String line = null;


					while ((line = reader.readLine()) != null) {
						sb.append(line + "\n");
					}
					is.close();
					String json = sb.toString();
					System.out.println(json);

					jObj = new JSONObject(json);

				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			return jObj;
		}

		@Deprecated
		@Override
		protected void onPostExecute(JSONObject result) {

			dialog_loading.dismiss();

			success = false;
			message = "Gagal melakukan proses take action. Cobalah lagi.";
			datamsg = null;
			if(result!=null) {
				try {
					success = result.isNull("success")?false:result.getBoolean("success");
					message = result.isNull("message")?message:result.getString("message");
					JSONObject rec = result.getJSONObject("data_message");

					int id = rec.isNull("id")?null:rec.getInt("id");
					String nama = rec.isNull("nama")?null:rec.getString("nama");
					String telepon = rec.isNull("telepon")?null:rec.getString("telepon");
					String photo = rec.isNull("photo")?null:rec.getString("photo");

					String message = rec.isNull("message")?null:rec.getString("message");
					String datetime = rec.isNull("datetime")?null:rec.getString("datetime");
					Boolean is_self = rec.isNull("is_self")?false:rec.getBoolean("is_self");
					Boolean is_sent = rec.isNull("is_sent")?false:rec.getBoolean("is_sent");

					datamsg = new message(id, nama, telepon, photo, datetime, message, is_self, is_sent);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(success) {
				Intent i = new Intent("gomocart.application.com.gomocart.SEND_MESSAGE");
				i.putExtra("message", datamsg);
				sendBroadcast(i);

				inputMsg.setText("");
			} else {
                text_informasi.setText(message);
                text_title.setText("KESALAHAN");
                dialog_informasi.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog_informasi.show();
			}
			btn_send.setVisibility(View.VISIBLE);
			progres_save.setVisibility(View.GONE);
		}
	}

	public void openDialogLoading() {
		dialog_loading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		dialog_loading.show();
	}



}
