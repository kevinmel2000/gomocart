package gomocart.application.com.gomocart;

import android.app.Dialog;
		import android.app.Fragment;
		import android.app.FragmentManager;
		import android.content.BroadcastReceiver;
		import android.content.Context;
		import android.content.Intent;
		import android.content.IntentFilter;
		import android.content.pm.PackageInfo;
		import android.content.pm.PackageManager;
		import android.content.pm.Signature;
		import android.graphics.Color;
        import android.graphics.drawable.ColorDrawable;
		import android.net.Uri;
		import android.os.AsyncTask;
		import android.os.Build;
		import android.os.Bundle;
		import android.os.Handler;
		import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
		import android.support.v4.widget.DrawerLayout;
		import android.support.v7.app.AppCompatActivity;
		import android.support.v7.widget.Toolbar;
		import android.text.Html;
		import android.util.Base64;
		import android.util.Log;
		import android.view.KeyEvent;
		import android.view.Menu;
		import android.view.MenuItem;
		import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
		import android.view.inputmethod.InputMethodManager;
		import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
		import android.widget.ImageView;
		import android.widget.LinearLayout;
		import android.widget.ListView;
		import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
		import android.widget.TextView;
		import android.widget.Toast;

		import com.alexzh.circleimageview.CircleImageView;
		import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.google.firebase.iid.FirebaseInstanceId;
		import com.nostra13.universalimageloader.core.DisplayImageOptions;
		import com.nostra13.universalimageloader.core.ImageLoader;

		import org.apache.http.HttpEntity;
		import org.apache.http.HttpResponse;
		import org.apache.http.NameValuePair;
		import org.apache.http.client.ClientProtocolException;
		import org.apache.http.client.HttpClient;
		import org.apache.http.client.methods.HttpPost;
		import org.apache.http.entity.mime.MultipartEntity;
		import org.apache.http.entity.mime.content.FileBody;
		import org.apache.http.entity.mime.content.StringBody;
		import org.apache.http.impl.client.DefaultHttpClient;
		import org.apache.http.message.BasicNameValuePair;
		import org.json.JSONArray;
		import org.json.JSONException;
		import org.json.JSONObject;

		import java.io.BufferedReader;
		import java.io.File;
		import java.io.IOException;
		import java.io.InputStream;
		import java.io.InputStreamReader;
		import java.io.UnsupportedEncodingException;
		import java.security.MessageDigest;
		import java.security.NoSuchAlgorithmException;
		import java.text.SimpleDateFormat;
		import java.util.ArrayList;
		import java.util.Date;
		import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
		import java.util.Map;

import customfonts.MyEditText;
		import customfonts.MyTextView;
		import gomocart.application.com.adapter.AlamatAdapter;
		import gomocart.application.com.adapter.CartlistAdapter;
import gomocart.application.com.adapter.MoreMenuAdapter;
import gomocart.application.com.adapter.PerpesananAdapter;
		import gomocart.application.com.adapter.InformasiAdapter;
		import gomocart.application.com.adapter.ListKategoriAdapter;
		import gomocart.application.com.adapter.ListOngkirAdapter;
		import gomocart.application.com.adapter.ListOrderAdapter;
		import gomocart.application.com.adapter.ListProdukAdapter;
import gomocart.application.com.adapter.NotifikasiAdapter;
import gomocart.application.com.adapter.WishlistAdapter;
		import gomocart.application.com.fragment.AlamatFragment;
		import gomocart.application.com.fragment.DaftarPesananBatalFragment;
		import gomocart.application.com.fragment.DaftarPesananSedangKirimFragment;
		import gomocart.application.com.fragment.DaftarPesananSedangProsesFragment;
		import gomocart.application.com.fragment.DaftarPesananSelesaiFragment;
		import gomocart.application.com.fragment.KeranjangFragment;
		import gomocart.application.com.fragment.OngkosKirimFragment;
		import gomocart.application.com.fragment.MoreFragment;
		import gomocart.application.com.fragment.EditProfileFragment;
		import gomocart.application.com.fragment.GantiPasswordFragment;
		import gomocart.application.com.fragment.JenisUserFragment;
import gomocart.application.com.fragment.ProdukFragment;
import gomocart.application.com.fragment.ProfileFragment;
		import gomocart.application.com.fragment.HubungiPengembangFragment;
		import gomocart.application.com.fragment.SettingFragment;
		import gomocart.application.com.fragment.InformasiFragment;
		import gomocart.application.com.fragment.KategoriFragment;
		import gomocart.application.com.fragment.PerpesananFragment;
		import gomocart.application.com.fragment.NotifikasiFragment;
		import gomocart.application.com.fragment.DaftarPesananFragment;
		import gomocart.application.com.fragment.PaymentFragment;
import gomocart.application.com.fragment.DaftarPesananBelumBayarFragment;
		import gomocart.application.com.fragment.WishlistFragment;
		import gomocart.application.com.libs.CommonUtilities;
		import gomocart.application.com.libs.DatabaseHandler;
import gomocart.application.com.libs.GalleryFilePath;
import gomocart.application.com.libs.JSONParser;
		import gomocart.application.com.libs.MCrypt;
		import gomocart.application.com.libs.ServerUtilities;
		import gomocart.application.com.model.alamat;
		import gomocart.application.com.model.bank;
		import gomocart.application.com.model.cekorder;
		import gomocart.application.com.model.cekorder_list;
		import gomocart.application.com.model.city;
		import gomocart.application.com.model.grandtotal;
		import gomocart.application.com.model.informasi;
		import gomocart.application.com.model.informasi_list;
		import gomocart.application.com.model.kategori;
import gomocart.application.com.model.moremenu;
import gomocart.application.com.model.perpesanan;
		import gomocart.application.com.model.perpesanan_list;
import gomocart.application.com.model.notifikasi;
		import gomocart.application.com.model.notifikasi_list;
		import gomocart.application.com.model.ongkir;
		import gomocart.application.com.model.order;
import gomocart.application.com.model.produk;
import gomocart.application.com.model.produk_kategori;
import gomocart.application.com.model.produk_list;
		import gomocart.application.com.model.province;
		import gomocart.application.com.model.setting;
		import gomocart.application.com.model.stok;
		import gomocart.application.com.model.subdistrict;
		import gomocart.application.com.model.user;
		import gomocart.application.com.fragment.DashboardFragment;
		import gomocart.application.com.model.banner;
import gomocart.application.com.adapter.GridProdukAdapter;
		import gomocart.application.com.model.voucher;

		import static gomocart.application.com.libs.CommonUtilities.getOptionsImage;
		import static gomocart.application.com.libs.CommonUtilities.initImageLoader;
		import com.soundcloud.android.crop.Crop;

public class MainActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, NavigationView.OnNavigationItemSelectedListener {

	final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;

	public static ArrayList<banner> dashboard_list_banner = new ArrayList<>();
	public static ArrayList<kategori> dashboard_list_kategori = new ArrayList<>();
	public static ArrayList<produk_kategori> dashboard_list_tab_kategori = new ArrayList<>();

	public static int province_id;
	public static int city_id;
	public static int subdistrict_id;
	public static int berat_barang;

	public static ArrayList<province> listProvince = new ArrayList<>();
	public static ArrayList<city> listCity = new ArrayList<>();
	public static ArrayList<subdistrict> listSubDistrict = new ArrayList<>();

	Dialog dialog_listview;
	ListView listview;

	public static int image_produk_size_vertical=0;
	public static int image_produk_size_horizontal=0;
	
	final int RESULT_FROM_SIGN_IN = 1;
	final int RESULT_FROM_SIGN_UP = 2;
	final int RESULT_FROM_AKTIVASI = 15;
	final int RESULT_FROM_PRODUK_DETAIL = 3;
	final int RESULT_FROM_KONF_PEMB = 4;
	final int REQUEST_FROM_GALLERY = 5;
	final int REQUEST_FROM_CAMERA  = 6;
	final int REQUEST_FROM_FILTER = 7;

	final int RESULT_FROM_PROSES_CHECKOUT= 8;
	final int RESULT_FROM_KIRIM_PESAN = 9;

	final int RESULT_FROM_DETAIL_ORDER = 12;
	final int RESULT_FROM_EDIT_ALAMAT = 13;
	//final int RESULT_FROM_MESSAGE = 14;

	public static Context context;
	user data;

	public static MyTextView main_title;
	RelativeLayout linear_search;
	MyEditText edit_search;
	ImageButton btn_close;

	LinearLayout linear_utama;
	LinearLayout toolbar_layout_search;
	ImageView toolbar_search;


	LinearLayout toolbar_layout_cart;
	ImageView toolbar_cart;
	MyTextView number_cart;

	LinearLayout toolbar_layout_wish;
	ImageView toolbar_wish;
	MyTextView number_wish;

	public static DatabaseHandler dh;
	int total_cart;
	int total_wishlist;

	LinearLayout lin_login;
	LinearLayout lin_register;

	ImageView image_menu_login, image_menu_profil;
	MyTextView nav_login;
	MyTextView nav_register;

	public static kategori select_kategori;
	public static boolean is_search;
	public static String search_keyword;
	public static String filter_kategori;
	public static String filter_brand;
	public static String filter_ukuran;
	public static String filter_warna;
	public static String filter_harga_min;
	public static String filter_harga_max;
	public static String filter_diskon_min;
	public static String filter_diskon_max;

	Dialog dialog_sort_by;
	ImageView radioTerbaru, radioRating, radioTermurah, radioTermahal;
	LinearLayout linearTerbaru, linearRating, linearTermurah, linearTermahal;
	public static String sort_produk_by;

	Dialog dialog_sort_by_alamat;
	ImageView radioAZ, radioZA;
	LinearLayout linearAZ, linearZA;
	public static String sort_by_alamat;

	Dialog dialog_setting_notifikasi;
	ImageView radioSuaraGetar, radioSuara, radioGetar;
	LinearLayout linearSuaraGetar, linearSuara, linearGetar;
	public static String setting_notifikasi;

	//KATEGORI LIST
	public static ArrayList<kategori> kategorilist;
	public static ListKategoriAdapter kategorilistAdapter;

	//PRODUK GRID
	public static int next_page_data_produk_grid;
	public static ArrayList<produk> produkgrid = new ArrayList<>();
	public static GridProdukAdapter produkgridAdapter;

	//PRODUK LIST
	public static int next_page_data_produk_list;
	public static ArrayList<produk> produklist = new ArrayList<>();
	public static ListProdukAdapter produklistAdapter;

	public static String show_produk_in = "grid";
	public static boolean first_load_produk = false;

	//CART LIST
	public static ArrayList<produk> cartlist = new ArrayList<>();
	public static CartlistAdapter cartlistAdapter;

	//WISH LIST
	public static ArrayList<produk> wishlist = new ArrayList<>();
	public static WishlistAdapter wishlistAdapter;

	// ONGKIR LIST
	public static ArrayList<ongkir> ongkirlist = new ArrayList<>();
	public static ListOngkirAdapter ongkirAdapter;

	// ORDER LIST BELUM BAYAR
	ArrayList<order> orderlist_belum_bayar = new ArrayList<>();
	ListOrderAdapter orderAdapter_belum_bayar;

	// ORDER LIST SEDANG PROSES
	ArrayList<order> orderlist_sedang_proses = new ArrayList<>();
	ListOrderAdapter orderAdapter_sedang_proses;

	// ORDER LIST SEDANG KIRIM
	ArrayList<order> orderlist_sedang_kirim = new ArrayList<>();
	ListOrderAdapter orderAdapter_sedang_kirim;

	// SELESAI
	ArrayList<order> orderlist_selesai = new ArrayList<>();
	ListOrderAdapter orderAdapter_selesai;

	// ORDER LIST SEDANG PROSES
	ArrayList<order> orderlist_batal = new ArrayList<>();
	ListOrderAdapter orderAdapter_batal;

	// INFORMASI
	int next_page_informasi;
	public static ArrayList<informasi> informasilist = new ArrayList<>();
	public static InformasiAdapter informasiAdapter;

	//PERPESANAN
	int next_page_perpesanan;
	public static ArrayList<perpesanan> perpesananlist = new ArrayList<>();
	public static PerpesananAdapter perpesananAdapter;

	//NOTIFIKASI
	public static int next_page_notifikasi;
	public static ArrayList<notifikasi> list_notifikasi = new ArrayList<>();
	public static NotifikasiAdapter notifikasi_adapter;


	//SLIDE MENU
	ArrayList<moremenu> moremenulist = new ArrayList<>();
	Map<moremenu, ArrayList<moremenu>> submoremenulist = new LinkedHashMap<>();
	MoreMenuAdapter moremenuAdapter;
	public static ExpandableListView moremenuListView;

	public static ArrayList<alamat> alamatlist = new ArrayList<>();
	public static ArrayList<alamat> alamatlist_display = new ArrayList<>();
	public static AlamatAdapter alamatAdapter;



	static int menu_selected = 0;

	public static ImageLoader imageLoader;
	public static DisplayImageOptions imageOptionsUser;
	public static DisplayImageOptions imageOptionProduk;
	public static DisplayImageOptions imageOptionKategori;
	public static DisplayImageOptions imageOptionBank;
	public static DisplayImageOptions imageOptionOngkir;
	public static DisplayImageOptions imageOptionInformasi;

	ImageView menu;
	Toolbar toolbar;
	DrawerLayout drawer;
	LinearLayout mDrawerPane;

	CircleImageView avatar;
	MyTextView name_avatar;

	Dialog dialog_delete_alamat;
	MyTextView btn_delete_alamat_no, btn_delete_alamat_yes;
	alamat delete_selected_alamat;

	Dialog dialog_batalkan_pesanan;
	MyTextView btn_batalkan_pesanan_no, btn_batalkan_pesanan_yes;
	order batalkan_selected_pesanan;

	Dialog dialog_delete_wistlist;
	MyTextView btn_delete_wistlist_no, btn_delete_wistlist_yes;
	produk delete_selected_wistlist;
	
	Dialog dialog_logout;
	MyTextView btn_no, btn_yes;

	Dialog dialog_ukuran_warna;
	ListView listview_ukuran_warna;
	String action;
	int item_index;

	ArrayList<String> list_ukuran;
	ArrayList<String> list_warna;

	Dialog dialog_loading;

	Dialog dialog_informasi;
	MyTextView btn_ok;
	MyTextView text_title;
	MyTextView text_informasi;

	ArrayList<bank> list_bank;

	Dialog dialog_pilih_gambar;
	MyTextView from_camera, from_galery;

	private static Uri mImageCaptureUri;

	int count_close = 1;
	int current_click = 0;
	Handler mHandlerClose = new Handler();
	Handler mHandlerDisplayView = new Handler();

	public static String printKeyHash(Context context) {
		PackageInfo packageInfo;
		String key = null;
		try {
			//getting application package name, as defined in manifest
			String packageName = context.getApplicationContext().getPackageName();

			//Retriving package info
			packageInfo = context.getPackageManager().getPackageInfo(packageName,
					PackageManager.GET_SIGNATURES);

			Log.e("Package Name=", context.getApplicationContext().getPackageName());

			for (Signature signature : packageInfo.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				key = new String(Base64.encode(md.digest(), 0));

				// String key = new String(Base64.encodeBytes(md.digest()));
				Log.e("Key Hash=", key);
			}
		} catch (PackageManager.NameNotFoundException e1) {
			Log.e("Name not found", e1.toString());
		}
		catch (NoSuchAlgorithmException e) {
			Log.e("No such an algorithm", e.toString());
		} catch (Exception e) {
			Log.e("Exception", e.toString());
		}

		return key;
	}

	public static moremenu moremenu_select = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (Build.VERSION.SDK_INT >= 23) {
			insertDummyContactWrapper();
		}

		if (Build.VERSION.SDK_INT >= 21) {
			Window window = getWindow();
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setStatusBarColor(Color.parseColor("#e40046"));
		}

		context = MainActivity.this;
		dh = new DatabaseHandler(context);
		dh.createTable();

		//printKeyHash(context);

		data = CommonUtilities.getSettingUser(context);
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		menu = (ImageView) findViewById(R.id.menu);
		drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerPane = (LinearLayout) findViewById(R.id.drawerPane);

		int width = getResources().getDisplayMetrics().widthPixels;
		width = width - (width / 3);
		DrawerLayout.LayoutParams params = (android.support.v4.widget.DrawerLayout.LayoutParams) mDrawerPane.getLayoutParams();
		params.width = width;
		mDrawerPane.setLayoutParams(params);

		moremenuListView = (ExpandableListView) findViewById(R.id.moremenulistview);
		main_title       = (MyTextView) findViewById(R.id.eshop);
		linear_utama     = (LinearLayout) findViewById(R.id.linear_utama);

		toolbar_layout_search = (LinearLayout) findViewById(R.id.toolbar_layout_search);
		linear_search    = (RelativeLayout) findViewById(R.id.cardview_search);
		edit_search      = (MyEditText) findViewById(R.id.searchtext);
		btn_close        = (ImageButton) findViewById(R.id.btn_close);

		main_title.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				displayView(0);
			}
		});

        edit_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					String keyword = edit_search.getText().toString();
					doSearchProduk(keyword);
					//closeSoftKeyboard();
					return true;
				}
				return false;
			}
		});

		btn_close.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				edit_search.setText("");
				linear_search.setVisibility(View.GONE);
				linear_utama.setVisibility(View.VISIBLE);

				if(menu_selected==2) {
					is_search = false;
					search_keyword = "";
					select_kategori = null;
					filter_kategori = "";
					filter_brand = "";
					filter_ukuran = "";
					filter_warna = "";
					filter_harga_max = "";
					filter_harga_min = "";
					filter_diskon_max = "";
					filter_diskon_min = "";
					displayView(2);
				}
			}
		});

		toolbar_search = (ImageView) findViewById(R.id.toolbar_image_search);
		toolbar_search.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//more_menu_show = false;
				linear_search.setVisibility(View.VISIBLE);
				linear_utama.setVisibility(View.GONE);
				edit_search.requestFocus();
				openSoftKeyboard();
			}
		});

		toolbar_layout_cart = (LinearLayout) findViewById(R.id.toolbar_layout_cart);
		toolbar_cart = (ImageView) findViewById(R.id.toolbar_image_cart);
		number_cart = (MyTextView) findViewById(R.id.number_cart);
		updateTotalCartlist();

		toolbar_layout_wish = (LinearLayout) findViewById(R.id.toolbar_layout_wishtlist);
		toolbar_wish = (ImageView) findViewById(R.id.toolbar_image_wishlist);
		number_wish = (MyTextView) findViewById(R.id.number_wishlist);
		updateTotalWishlist();

		toolbar_cart.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				displayView(3);
			}
		});
		number_cart.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				displayView(3);
			}
		});

		toolbar_wish.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				displayView(4);
			}
		});
		number_wish.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				displayView(4);
			}
		});

		menu.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View view) {
				if (drawer.isDrawerOpen(GravityCompat.START)) {
					drawer.closeDrawer(GravityCompat.START);
				} else {
					drawer.openDrawer(GravityCompat.START);
				}
				//more_menu_show = false;
				//more_menu.setVisibility(View.GONE);
			}
		});

		avatar = (CircleImageView) findViewById(R.id.banar1);
		name_avatar = (MyTextView) findViewById(R.id.name);

		//lin_setting = (LinearLayout) findViewById(R.id.lin_setting);
		lin_login = (LinearLayout) findViewById(R.id.lin_login);
		lin_register = (LinearLayout) findViewById(R.id.lin_register);
		//view_alamat = (View) findViewById(R.id.sparator_alamat);
		//lin_alamat = (LinearLayout) findViewById(R.id.lin_alamat);

		image_menu_login = (ImageView) findViewById(R.id.image_menu_login);
		image_menu_profil  = (ImageView) findViewById(R.id.image_menu_profil);

		nav_login = (MyTextView) findViewById(R.id.nav_login);
		nav_register = (MyTextView) findViewById(R.id.nav_register);
		//nav_setting = (ImageView) findViewById(R.id.nav_setting);

		/*nav_setting.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				displayView(12);
			}
		});*/

		lin_login.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				drawer.closeDrawer(GravityCompat.START);
				if(data.getId()==0) {
					openPageLogin();
				} else {
					openDialogSignout();
				}
			}
		});

		lin_register.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				drawer.closeDrawer(GravityCompat.START);
				if(data.getId()==0) {
					Intent intent = new Intent(context, DaftarActivity.class);
					startActivityForResult(intent, RESULT_FROM_SIGN_UP);
				} else {
					displayView(11);
				}
			}
		});

		moremenulist.add(new moremenu(1, "", getResources().getString(R.string.menu_beranda), "", R.drawable.menu_beranda));
		moremenulist.add(new moremenu(2, "", getResources().getString(R.string.menu_kategori), "", R.drawable.menu_kategori));
		moremenulist.add(new moremenu(3, "", getResources().getString(R.string.menu_produk), "", R.drawable.menu_produk));
		moremenulist.add(new moremenu(4, "", getResources().getString(R.string.menu_checkout), "", R.drawable.menu_keranjang));
		moremenulist.add(new moremenu(5, "", getResources().getString(R.string.menu_wishlist), "", R.drawable.menu_wishlist));
		moremenulist.add(new moremenu(6, "", getResources().getString(R.string.menu_ongkir), "", R.drawable.menu_ongkos_kirim));
		moremenulist.add(new moremenu(7, "", getResources().getString(R.string.menu_order), "", R.drawable.menu_daftar_pesanan));
		moremenulist.add(new moremenu(8, "", getResources().getString(R.string.menu_perpesanan), "", R.drawable.menu_perpesanan));
		moremenulist.add(new moremenu(9, "", getResources().getString(R.string.menu_informasi), "", R.drawable.menu_informasi));
		moremenulist.add(new moremenu(10, "", getResources().getString(R.string.menu_notifikasi), "", R.drawable.menu_notifikasi));
		moremenulist.add(new moremenu(11, "", getResources().getString(R.string.menu_hubungi_pengembang), "", R.drawable.menu_hubungi_pengembang));
		moremenulist.add(new moremenu(12, "", getResources().getString(R.string.menu_more), "", R.drawable.more));

		moremenuAdapter = new MoreMenuAdapter(context, moremenulist, submoremenulist);
		moremenuListView.setAdapter(moremenuAdapter);


		moremenuListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
				openMoreMenu(groupPosition+1);

				return false;
			}
		});

		avatar.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		dialog_listview = new Dialog(context);
		dialog_listview.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog_listview.setCancelable(true);
		dialog_listview.setContentView(R.layout.list_dialog);

		listview = (ListView) dialog_listview.findViewById(R.id.listViewDialog);
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				dialog_listview.dismiss();
				if(action.equalsIgnoreCase("province") || action.equalsIgnoreCase("profile_province")) {
					if(action.equalsIgnoreCase("province")) {
						OngkosKirimFragment.edit_province.setText(listProvince.get(position).getProvince());
						OngkosKirimFragment.prop_ok.setVisibility(View.VISIBLE);
						OngkosKirimFragment.edit_city.setText("");
						OngkosKirimFragment.city_ok.setVisibility(View.GONE);
						OngkosKirimFragment.edit_state.setText("");
						OngkosKirimFragment.kecamatan_ok.setVisibility(View.GONE);
					}

					province_id = listProvince.get(position).getProvince_id();
					city_id = 0;
					subdistrict_id = 0;

					listCity = new ArrayList<>();
					new loadCity().execute();

					listSubDistrict = new ArrayList<>();
					new loadSubdistrict().execute();

				} else if(action.equalsIgnoreCase("city") || action.equalsIgnoreCase("profile_city")) {
					if(action.equalsIgnoreCase("city")) {
						OngkosKirimFragment.edit_city.setText(listCity.get(position).getCity());
						OngkosKirimFragment.city_ok.setVisibility(View.VISIBLE);
						OngkosKirimFragment.edit_state.setText("");
						OngkosKirimFragment.kecamatan_ok.setVisibility(View.GONE);
					}

					city_id = listCity.get(position).getCity_id();
					subdistrict_id = 0;

					listSubDistrict = new ArrayList<>();
					new loadSubdistrict().execute();

				} else if(action.equalsIgnoreCase("subdistrict") || action.equalsIgnoreCase("profile_subdistrict")) {
					if(action.equalsIgnoreCase("subdistrict")) {
						OngkosKirimFragment.edit_state.setText(listSubDistrict.get(position).getSubdistrict());
						OngkosKirimFragment.kecamatan_ok.setVisibility(View.VISIBLE);
					}

					subdistrict_id = listSubDistrict.get(position).getSubdistrict_id();
				}
			}
		});

		dialog_logout = new Dialog(context);
		dialog_logout.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog_logout.setCancelable(true);
		dialog_logout.setContentView(R.layout.signout_dialog);

		btn_yes = (MyTextView) dialog_logout.findViewById(R.id.btn_yes);
		btn_yes.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				dialog_logout.dismiss();
				new prosesSignOut().execute();

			}
		});

		btn_no = (MyTextView) dialog_logout.findViewById(R.id.btn_no);
		btn_no.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog_logout.dismiss();

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
				dialog_delete_alamat.dismiss();
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


		dialog_batalkan_pesanan = new Dialog(context);
		dialog_batalkan_pesanan.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog_batalkan_pesanan.setCancelable(true);
		dialog_batalkan_pesanan.setContentView(R.layout.batalkan_pesanan_dialog);

		btn_batalkan_pesanan_yes = (MyTextView) dialog_batalkan_pesanan.findViewById(R.id.btn_yes);
		btn_batalkan_pesanan_yes.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog_batalkan_pesanan.dismiss();
				new prosesBatalkanPesanan(batalkan_selected_pesanan).execute();
			}
		});

		btn_batalkan_pesanan_no = (MyTextView) dialog_batalkan_pesanan.findViewById(R.id.btn_no);
		btn_batalkan_pesanan_no.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog_batalkan_pesanan.dismiss();

			}
		});

		dialog_delete_wistlist = new Dialog(context);
		dialog_delete_wistlist.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog_delete_wistlist.setCancelable(true);
		dialog_delete_wistlist.setContentView(R.layout.delete_wistlist_dialog);

		btn_delete_wistlist_yes = (MyTextView) dialog_delete_wistlist.findViewById(R.id.btn_yes);
		btn_delete_wistlist_yes.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog_delete_wistlist.dismiss();
				dh.deleteWishlist(delete_selected_wistlist);
				for (produk wish_item : wishlist) {
                	int index = wishlist.indexOf(wish_item);
					if(wish_item.getId()==delete_selected_wistlist.getId()) {
						wishlist.remove(index);
						break;
					}
				}
                wishlistAdapter.UpdateWishlistAdapter(wishlist);
                updateTotalWishlist(delete_selected_wistlist);
			}
		});

		btn_delete_wistlist_no = (MyTextView) dialog_delete_wistlist.findViewById(R.id.btn_no);
		btn_delete_wistlist_no.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog_delete_wistlist.dismiss();

			}
		});



		InputStream stream = null;
		try {
			stream = getAssets().open("images/loading.gif");
		} catch (IOException e) {
			e.printStackTrace();
		}

		dialog_loading = new Dialog(context);
		dialog_loading.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog_loading.setCancelable(false);
		dialog_loading.setContentView(R.layout.loading_dialog);
		//frame_loading = (FrameLayout) dialog_loading.findViewById(R.id.frame_loading);

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

		initImageLoader(context);
		imageLoader         = ImageLoader.getInstance();
		imageOptionsUser    = getOptionsImage(R.drawable.userdefault, R.drawable.userdefault);
		imageOptionProduk   = getOptionsImage(R.drawable.logo_grayscale, R.drawable.logo_grayscale);
		imageOptionKategori = getOptionsImage(R.drawable.logo_grayscale, R.drawable.logo_grayscale);
		imageOptionBank     = getOptionsImage(R.drawable.logo_grayscale, R.drawable.logo_grayscale);
		imageOptionOngkir 	= getOptionsImage(R.drawable.logo_grayscale, R.drawable.logo_grayscale);
		imageOptionInformasi = getOptionsImage(R.drawable.logo_grayscale, R.drawable.logo_grayscale);

		menu_selected = 0;

		if(savedInstanceState==null) {
			checkGcmRegid();
			menu_selected = getIntent().getIntExtra("menu_select", 0);
			/*if(menu_selected==13) {
				notifikasi notif = (notifikasi) getIntent().getSerializableExtra("notifikasi");
				if(notif!=null) openDetailNotifikasi(notif);
			}*/
		}
		setSignIn();

		dialog_ukuran_warna = new Dialog(context);
		dialog_ukuran_warna.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog_ukuran_warna.setCancelable(true);
		dialog_ukuran_warna.setContentView(R.layout.list_dialog);

		listview_ukuran_warna = (ListView) dialog_ukuran_warna.findViewById(R.id.listViewDialog);
		listview_ukuran_warna.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				dialog_ukuran_warna.dismiss();
				if(action.equalsIgnoreCase("ukuran")) {
					cartlist.get(item_index).setUkuran(list_ukuran.get(position));
					cartlist.get(item_index).setWarna("");
				} else {
					cartlist.get(item_index).setWarna(list_warna.get(position));
				}
				cartlistAdapter.UpdateCartlistAdapter(cartlist);
			}
		});

		dialog_pilih_gambar = new Dialog(context);
		dialog_pilih_gambar.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog_pilih_gambar.setCancelable(true);
		dialog_pilih_gambar.setContentView(R.layout.pilih_gambar_dialog);

		from_galery = (MyTextView) dialog_pilih_gambar.findViewById(R.id.txtFromGalley);
		from_galery.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog_pilih_gambar.dismiss();
				fromGallery();
			}
		});

		from_camera = (MyTextView) dialog_pilih_gambar.findViewById(R.id.txtFromCamera);
		from_camera.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog_pilih_gambar.dismiss();
				fromCamera();
			}
		});

		dialog_setting_notifikasi = new Dialog(context);
		dialog_setting_notifikasi.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog_setting_notifikasi.setCancelable(true);
		dialog_setting_notifikasi.setContentView(R.layout.setting_notifikasi_dialog);

		radioSuaraGetar = (ImageView) dialog_setting_notifikasi.findViewById(R.id.radioSuaraGetar);
		linearSuaraGetar = (LinearLayout) dialog_setting_notifikasi.findViewById(R.id.linearSuaraGetar);
		linearSuaraGetar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog_setting_notifikasi.dismiss();
				setting_notifikasi = "Suara dan Getar";

				radioSuaraGetar.setImageResource(R.drawable.radioblack);
				radioSuara.setImageResource(R.drawable.radiouncheked);
				radioGetar.setImageResource(R.drawable.radiouncheked);

				SettingFragment.edit_notifikasi.setText(setting_notifikasi);
			}
		});

		radioSuara = (ImageView) dialog_setting_notifikasi.findViewById(R.id.radioSuara);
		linearSuara = (LinearLayout) dialog_setting_notifikasi.findViewById(R.id.linearSuara);
		linearSuara.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog_setting_notifikasi.dismiss();
				setting_notifikasi = "Suara";

				radioSuaraGetar.setImageResource(R.drawable.radiouncheked);
				radioSuara.setImageResource(R.drawable.radioblack);
				radioGetar.setImageResource(R.drawable.radiouncheked);

				SettingFragment.edit_notifikasi.setText(setting_notifikasi);
			}
		});

		radioGetar = (ImageView) dialog_setting_notifikasi.findViewById(R.id.radioGetar);
		linearGetar = (LinearLayout) dialog_setting_notifikasi.findViewById(R.id.linearGetar);
		linearGetar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog_setting_notifikasi.dismiss();
				setting_notifikasi = "Getar";

				radioSuaraGetar.setImageResource(R.drawable.radiouncheked);
				radioGetar.setImageResource(R.drawable.radiouncheked);
				radioGetar.setImageResource(R.drawable.radioblack);

				SettingFragment.edit_notifikasi.setText(setting_notifikasi);
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

		dialog_sort_by = new Dialog(context);
		dialog_sort_by.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog_sort_by.setCancelable(true);
		dialog_sort_by.setContentView(R.layout.sortby_produk_dialog);

		radioTerbaru = (ImageView) dialog_sort_by.findViewById(R.id.radioTerbaru); 
		linearTerbaru = (LinearLayout) dialog_sort_by.findViewById(R.id.linearTerbaru);
		linearTerbaru.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog_sort_by.dismiss();
				sortProdukBy(1);

				radioTerbaru.setImageResource(R.drawable.radioblack);
				radioRating.setImageResource(R.drawable.radiouncheked);
				radioTermurah.setImageResource(R.drawable.radiouncheked);
				radioTermahal.setImageResource(R.drawable.radiouncheked);
			}
		});

		radioRating = (ImageView) dialog_sort_by.findViewById(R.id.radioRatting);
		linearRating = (LinearLayout) dialog_sort_by.findViewById(R.id.linearRating);
		linearRating.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog_sort_by.dismiss();
				sortProdukBy(2);

				radioTerbaru.setImageResource(R.drawable.radiouncheked);
				radioRating.setImageResource(R.drawable.radioblack);
				radioTermurah.setImageResource(R.drawable.radiouncheked);
				radioTermahal.setImageResource(R.drawable.radiouncheked);
			}
		});

		radioTermurah = (ImageView) dialog_sort_by.findViewById(R.id.radioTermurah);
		linearTermurah = (LinearLayout) dialog_sort_by.findViewById(R.id.linearTermurah);
		linearTermurah.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog_sort_by.dismiss();
				sortProdukBy(3);

				radioTerbaru.setImageResource(R.drawable.radiouncheked);
				radioRating.setImageResource(R.drawable.radiouncheked);
				radioTermurah.setImageResource(R.drawable.radioblack);
				radioTermahal.setImageResource(R.drawable.radiouncheked);
			}
		});

		radioTermahal = (ImageView) dialog_sort_by.findViewById(R.id.radioTermahal);
		linearTermahal = (LinearLayout) dialog_sort_by.findViewById(R.id.linearTermahal);
		linearTermahal.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog_sort_by.dismiss();
				sortProdukBy(4);

				radioTerbaru.setImageResource(R.drawable.radiouncheked);
				radioRating.setImageResource(R.drawable.radiouncheked);
				radioTermurah.setImageResource(R.drawable.radiouncheked);
				radioTermahal.setImageResource(R.drawable.radioblack);
			}
		});

	}


	public void openMoreMenu(int id) {
		switch (id) {
			case 1:
				displayView(0);
				break;

			case 2:
				displayView(1);
				break;

			case 3:
				is_search = false;
				search_keyword = "";
				select_kategori = null;
				filter_kategori = "";
				filter_brand = "";
				filter_ukuran = "";
				filter_warna = "";
				filter_harga_max = "";
				filter_harga_min = "";
				filter_diskon_max = "";
				filter_diskon_min = "";

				displayView(2);
				break;

			case 4:

				displayView(3);
				break;

			case 5:

				displayView(4);
				break;

			case 6:

				displayView(6);
				break;

			case 7:

				displayView(7);
				break;

			case 8:

				displayView(14);
				break;

			case 9:

				displayView(8);
				break;

			case 10:

				displayView(13);
				break;

			case 11:

				displayView(9);
				break;

			default:
				break;

		}

	}

	private void sortProdukBy(int sortby) {
		sort_produk_by = String.valueOf(sortby);

		first_load_produk = true;

		next_page_data_produk_grid = 1;
		produkgrid = new ArrayList<>();
		produkgridAdapter.UpdateGridProdukAdapter(produkgrid);

		next_page_data_produk_list = 1;
		produklist = new ArrayList<>();
		produklistAdapter.UpdateListProdukAdapter(produklist);

		loadDataProduk();
	}

	private void sortAlamtBy(int sortby) {
		sort_by_alamat = String.valueOf(sortby);

		alamatlist = new ArrayList<>();
		alamatlist_display = new ArrayList<>();

		showListAlamat();
		loadAlamatlist();
	}

	public void openDialogSettingNotifikasi() {
		dialog_setting_notifikasi.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		dialog_setting_notifikasi.show();

		radioSuaraGetar.setImageResource(setting_notifikasi.equalsIgnoreCase("Suara dan Getar")?R.drawable.radioblack:R.drawable.radiouncheked);
		radioSuara.setImageResource(setting_notifikasi.equalsIgnoreCase("Suara")?R.drawable.radioblack:R.drawable.radiouncheked);
		radioGetar.setImageResource(setting_notifikasi.equalsIgnoreCase("Getar")?R.drawable.radioblack:R.drawable.radiouncheked);
	}

	public void openDialogSortByAlamat() {
		dialog_sort_by_alamat.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		dialog_sort_by_alamat.show();
	}

	public void openDialogSortBy() {
		dialog_sort_by.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		dialog_sort_by.show();
	}

	public void selectImage() {
		dialog_pilih_gambar.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		dialog_pilih_gambar.show();
	}

	private void beginCrop(Uri source) {
		Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
		Crop.of(source, destination).asSquare().start(this);
	}

	private void fromGallery() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("image/*");
		startActivityForResult(intent, REQUEST_FROM_GALLERY);
	}

	private void fromCamera() {

		Intent intent = new Intent(context, AmbilFotoActivity.class);
		startActivityForResult(intent, REQUEST_FROM_CAMERA);
	}


	public void openDialogUkuran(ArrayList<stok> list_stok, int index) {
		item_index = index;
		list_ukuran = new ArrayList<>();
		boolean add_ukuran;

		for(stok data_: list_stok) {
			add_ukuran = true;
			for(String data__: list_ukuran) {
				if(data_.getUkuran().equalsIgnoreCase(data__)) {
					add_ukuran = false;
					break;
				}
			}
			if(add_ukuran) {
				list_ukuran.add(data_.getUkuran());
			}
		}

		dialog_ukuran_warna.show();
		loadListArray(list_ukuran);
		action = "ukuran";
	}

	public void openDialogWarna(ArrayList<stok> list_stok, String ukuran, int index) {
		item_index = index;
		list_warna = new ArrayList<>();
		boolean add_warna;
		for(stok data_: list_stok) {
			if(data_.getUkuran().equalsIgnoreCase(ukuran)) {
				add_warna = true;
				for(String data__: list_warna) {
					if(data_.getWarna().equalsIgnoreCase(data__)) {
						add_warna = false;
						break;
					}
				}
				if(add_warna) {
					list_warna.add(data_.getWarna());
				}
			}
		}

		dialog_ukuran_warna.show();
		loadListArray(list_warna);
		action = "warna";
	}

	private void loadListArray(ArrayList<String> list_data) {
		String[] from = new String[] { getResources().getString(R.string.list_dialog_title) };
		int[] to = new int[] { R.id.txt_title };

		List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
		for (String data : list_data) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put(getResources().getString(R.string.list_dialog_title), data);

			fillMaps.add(map);
		}

		SimpleAdapter adapter = new SimpleAdapter(context, fillMaps, R.layout.item_list_dialog, from, to);
		listview_ukuran_warna.setAdapter(adapter);
	}

	public void openDialogSignout() {
		dialog_logout.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		dialog_logout.show();
	}

	public void openPageLogin() {
		Intent intent = new Intent(context, LoginActivity.class);
		intent.putExtra("menu_selected", menu_selected);
		startActivityForResult(intent, RESULT_FROM_SIGN_IN);
	}


	public void openKonfirmasiPembayaran() {
		Intent intent = new Intent(context, KonfirmasiActivity.class);
		startActivityForResult(intent, RESULT_FROM_KONF_PEMB);
	}

	public void prosesBatalkanPesanan(order data_order) {
		batalkan_selected_pesanan = data_order;
		dialog_batalkan_pesanan.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		dialog_batalkan_pesanan.show();
	}

	public void openKonfirmasiPembayaran(order data) {
		Intent intent = new Intent(context, KonfirmasiActivity.class);
		intent.putExtra("no_transaksi", data.getNo_transaksi());
		intent.putExtra("jumlah", data.getJumlah());
		startActivityForResult(intent, RESULT_FROM_KONF_PEMB);
	}

	public void openDetailOrder(order data) {
		Intent intent = new Intent(context, DetailPesananActivity.class);
		intent.putExtra("order", data);

		//intent.putExtra("data_voucher", data_voucher);
		//intent.putExtra("data_bank", data_bank);

		startActivityForResult(intent, RESULT_FROM_DETAIL_ORDER);
	}


	class prosesBatalkanPesanan extends AsyncTask<String, Void, JSONObject> {

		order data_order;
		boolean success;
		String message;

		prosesBatalkanPesanan(order data_order) {
			this.data_order = data_order;
		}

		@Override
		protected void onPreExecute() {

			super.onPreExecute();

			openDialogLoading();
		}

		@Override
		protected JSONObject doInBackground(String... urls) {
			List<NameValuePair> params = new ArrayList<>();
			params.add(new BasicNameValuePair("no_trx", data_order.getNo_transaksi()+""));
			String url = CommonUtilities.SERVER_URL + "/store/androidBatalkanPesanan.php";
			JSONObject json = new JSONParser().getJSONFromUrl(url, params, null);

			return json;
		}

		@Deprecated
		@Override
		protected void onPostExecute(JSONObject result) {

			success = false;
			message = "Gagal melakukan pembatalan pesanan!";
			if(result!=null) {
				try {
					success = result.isNull("success")?false:result.getBoolean("success");
					message = result.isNull("message")?message:result.getString("message");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			dialog_loading.dismiss();
			if(success) {
				loadOrderlistBelumBayar();
			} else {
				openDialogMessage(message, false);
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

				alamatAdapter.UpdateAlamatAdapter(alamatlist);
			}
		}
	}

	class prosesSignOut extends AsyncTask<String, Void, JSONObject> {

		boolean success;
		String message;

		@Override
		protected void onPreExecute() {

			super.onPreExecute();

			openDialogLoading();
			//progDailog.setMessage("Sign Out...");
			//progDailog.show();
		}

		@Override
		protected JSONObject doInBackground(String... urls) {
			List<NameValuePair> params = new ArrayList<>();
			params.add(new BasicNameValuePair("id_user", data.getId()+""));
			String url = CommonUtilities.SERVER_URL + "/store/androidSignout.php";
			JSONObject json = new JSONParser().getJSONFromUrl(url, params, null);

			return json;
		}

		@Deprecated
		@Override
		protected void onPostExecute(JSONObject result) {

			dialog_loading.dismiss();

			success = false;
			message = "Gagal melakukan sign out. Silahkan coba lagi!";
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
				dialog_logout.dismiss();
				data = new user(0, "Welcome", "Guest", "", "", "", "", "", "");
				CommonUtilities.setSettingUser(context, data);
				checkGcmRegid();
				dh.clearOrderlist();
				menu_selected = 0;
				setSignIn();
			}
		}
	}

	public void doSearchProduk(String keyword) {
		if(keyword.length()==0) {

			text_informasi.setText("Keyword harus diisi.");
			text_title.setText("KESALAHAN");
			dialog_informasi.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			dialog_informasi.show();

			return;
		}

		is_search = true;
		search_keyword = keyword;
		select_kategori = null;
		filter_kategori = "";
		filter_brand = "";
		filter_ukuran = "";
		filter_warna = "";
		filter_harga_max = "";
		filter_harga_min = "";
		filter_diskon_max = "";
		filter_diskon_min = "";

		displayView(2);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

			if(menu_selected==0) {
				if (current_click == count_close) {
					finish();
				} else {
					current_click++;
					Toast.makeText(context, "Tekan dua kali untuk keluar.", Toast.LENGTH_SHORT).show();
					mHandlerClose.postDelayed(mUpdateTimeTask, 1000);
					return false;
				}
			}

			if (menu_selected > 0) {
				current_click=0;
				if(menu_selected==16) {
					displayView(11);
				} else if(menu_selected==15) {
					displayView(11);
				} else if(menu_selected==12) {
					displayView(11);
				} else if(menu_selected==17) {
					displayView(11);
				} else if(menu_selected==18) {
					displayView(11);
				} else {
					displayView(0);
				}
				return false;
			}
		}

		return super.onKeyDown(keyCode, event);
	}

	/*public void displayView(int position) {
		mHandlerDisplayView.postDelayed(mDisplayViewTask, 300);
	}*/

	public void displayView(int position) {
		drawer.closeDrawer(GravityCompat.START);

		menu_selected = position;
		Fragment fragment = null;
		toolbar_layout_search.setVisibility(position==9?View.GONE:View.VISIBLE);
		toolbar_layout_cart.setVisibility(position==9?View.GONE:View.VISIBLE);
		toolbar_layout_wish.setVisibility(position==9?View.GONE:View.VISIBLE);

		switch (position) {
			case 0:
				main_title.setText(getResources().getString(R.string.app_name));
				fragment = new DashboardFragment();
				break;

			case 1:
				main_title.setText(getResources().getString(R.string.menu_kategori));
				fragment = new KategoriFragment();
				break;

			case 2:

				sort_produk_by = "1";
				first_load_produk = true;
				show_produk_in = "grid";

				next_page_data_produk_grid = 1;
				produkgrid = new ArrayList<>();
				produkgridAdapter = new GridProdukAdapter(context, dh, produkgrid);

				next_page_data_produk_list = 1;
				produklist = new ArrayList<>();
				produklistAdapter = new ListProdukAdapter(context, produklist);

				main_title.setText(select_kategori!=null?select_kategori.getNama().toUpperCase():getResources().getString(R.string.menu_produk));
				fragment = new ProdukFragment();
				break;

			case 3:

				main_title.setText(getResources().getString(R.string.menu_checkout));
				fragment = new KeranjangFragment();
				break;

			case 4:

				main_title.setText(getResources().getString(R.string.menu_wishlist));
				fragment = new WishlistFragment();
				break;

			case 6:

				main_title.setText(getResources().getString(R.string.menu_ongkir));
				fragment = new OngkosKirimFragment();
				break;

			case 7:
				if(data.getId()==0) {
					openPageLogin();
				} else {
					main_title.setText(getResources().getString(R.string.menu_order));
					fragment = new DaftarPesananFragment();
				}
				break;

			case 14:
				if(data.getId()==0) {
					openPageLogin();
				} else {
					main_title.setText(getResources().getString(R.string.menu_perpesanan));
					fragment = new PerpesananFragment();
				}
				break;

			case 8:

				main_title.setText(getResources().getString(R.string.menu_informasi));
				fragment = new InformasiFragment();
				break;

			case 13:

				main_title.setText(getResources().getString(R.string.menu_notifikasi));
				fragment = new NotifikasiFragment();
				break;

			case 9:

				main_title.setText(getResources().getString(R.string.menu_hubungi_pengembang));
				fragment = new HubungiPengembangFragment();
				break;

			case 10:

				main_title.setText(moremenu_select.getNama());
				fragment = new MoreFragment();
				break;

			case 11:

				main_title.setText("Profil");
				fragment = new ProfileFragment();

				break;
			case 12:

				main_title.setText("Kelola Notifikasi");
				fragment = new SettingFragment();

				break;

			case 15:
				alamatlist = new ArrayList<>();
				alamatAdapter = new AlamatAdapter(context, alamatlist);

				main_title.setText("Alamat Kirim");
				fragment = new AlamatFragment();
				break;

			case 16:

				main_title.setText("Edit Profil");
				fragment = new EditProfileFragment();

				break;

			case 17:
				main_title.setText("Ganti Kata Sandi");
				fragment = new GantiPasswordFragment();

				break;

			case 18:

				main_title.setText("Jenis Pengguna");
				fragment = new JenisUserFragment();

				break;

			default:
				break;
		}


		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
		}
	}

	@Override
	protected void onDestroy() {
		try {
			unregisterReceiver(mHandleLoadMoreMenuReceiver);
			unregisterReceiver(mHandleLoadDetailMenuReceiver);
			unregisterReceiver(mHandleLoadDashbooardReceiver);
			unregisterReceiver(mHandleLoadListKategoriReceiver);
			unregisterReceiver(mHandleLoadListProdukReceiver);
			unregisterReceiver(mHandleLoadListKeranjangReceiver);
			unregisterReceiver(mHandleLoadListWishReceiver);
			unregisterReceiver(mHandleLoadListBankReceiver);
			unregisterReceiver(mHandleLoadListOngkirReceiver);
			unregisterReceiver(mHandleLoadListOrderReceiver);
			//unregisterReceiver(mHandleReloadListOrderReceiver);
			unregisterReceiver(mHandleEditDataProfileReceiver);
			unregisterReceiver(mHandleLoadListInformasiReceiver);
			unregisterReceiver(mHandleReloadInformasiReceiver);
			unregisterReceiver(mHandleLoadDataPerpesananReceiver);
			unregisterReceiver(mHandleReloadDataPerpesananReceiver);
			unregisterReceiver(mHandleUpdateWishlistReceiver);
			unregisterReceiver(mHandleOpenDetailProdukReceiver);
			unregisterReceiver(mHandleLoadNotifikasiReceiver);
			unregisterReceiver(mHandleLoadListAlamatReceiver);
			unregisterReceiver(mHandleLoadEkspedisiReceiver);

			mHandlerClose.removeCallbacks(mUpdateTimeTask);
			mHandlerDisplayView.removeCallbacks(mDisplayViewTask);
		} catch (Exception e) {
			e.printStackTrace();
		}

		super.onDestroy();
	}

	@Override
	protected void onPause() {
		try {
			unregisterReceiver(mHandleLoadMoreMenuReceiver);
			unregisterReceiver(mHandleLoadDetailMenuReceiver);
			unregisterReceiver(mHandleLoadDashbooardReceiver);
			unregisterReceiver(mHandleLoadListKategoriReceiver);
			unregisterReceiver(mHandleLoadListProdukReceiver);
			unregisterReceiver(mHandleLoadListKeranjangReceiver);
			unregisterReceiver(mHandleLoadListWishReceiver);
			unregisterReceiver(mHandleLoadListBankReceiver);
			unregisterReceiver(mHandleLoadListOngkirReceiver);
			unregisterReceiver(mHandleLoadListOrderReceiver);
			//unregisterReceiver(mHandleReloadListOrderReceiver);
			unregisterReceiver(mHandleEditDataProfileReceiver);
			unregisterReceiver(mHandleLoadListInformasiReceiver);
			unregisterReceiver(mHandleReloadInformasiReceiver);
			unregisterReceiver(mHandleLoadDataPerpesananReceiver);
			unregisterReceiver(mHandleReloadDataPerpesananReceiver);
			unregisterReceiver(mHandleUpdateWishlistReceiver);
			unregisterReceiver(mHandleOpenDetailProdukReceiver);
			unregisterReceiver(mHandleLoadNotifikasiReceiver);
			unregisterReceiver(mHandleLoadListAlamatReceiver);
			unregisterReceiver(mHandleLoadEkspedisiReceiver);

			mHandlerClose.removeCallbacks(mUpdateTimeTask);
			mHandlerDisplayView.removeCallbacks(mDisplayViewTask);
		} catch (Exception e) {
			e.printStackTrace();
		}

		super.onPause();
	}

	@Override
	protected void onResume() {
		registerReceiver(mHandleLoadNotifikasiReceiver, new IntentFilter("gomocart.application.com.gomocart.LOAD_NOTIFIKASI"));
		registerReceiver(mHandleLoadMoreMenuReceiver, new IntentFilter("gomocart.application.com.gomocart.LOAD_MORE_MENU"));
		registerReceiver(mHandleLoadDetailMenuReceiver, new IntentFilter("gomocart.application.com.gomocart.LOAD_DATA_DETAIL_MENU"));
		registerReceiver(mHandleLoadDashbooardReceiver, new IntentFilter("gomocart.application.com.gomocart.LOAD_DATA_DASHBOARD"));
		registerReceiver(mHandleLoadListKategoriReceiver, new IntentFilter("gomocart.application.com.gomocart.LOAD_KATEGORI_PRODUK"));
		registerReceiver(mHandleLoadListProdukReceiver, new IntentFilter("gomocart.application.com.gomocart.LOAD_DATA_PRODUK"));
		registerReceiver(mHandleLoadListKeranjangReceiver, new IntentFilter("gomocart.application.com.gomocart.LOAD_DATA_CART"));
		registerReceiver(mHandleLoadListWishReceiver, new IntentFilter("gomocart.application.com.gomocart.LOAD_DATA_WISH"));
		registerReceiver(mHandleLoadListBankReceiver, new IntentFilter("gomocart.application.com.gomocart.LOAD_DATA_BANK"));
		registerReceiver(mHandleLoadListOngkirReceiver, new IntentFilter("gomocart.application.com.gomocart.LOAD_DATA_ONGKIR"));
		registerReceiver(mHandleLoadListOrderReceiver, new IntentFilter("gomocart.application.com.gomocart.LOAD_DATA_ORDER"));
		//registerReceiver(mHandleReloadListOrderReceiver, new IntentFilter("gomocart.application.com.gomocart.RELOAD_DATA_ORDER"));
		registerReceiver(mHandleEditDataProfileReceiver, new IntentFilter("gomocart.application.com.gomocart.EDIT_DATA_PROFILE"));
		registerReceiver(mHandleLoadListInformasiReceiver, new IntentFilter("gomocart.application.com.gomocart.LOAD_DATA_INFORMASI"));
		registerReceiver(mHandleReloadInformasiReceiver, new IntentFilter("gomocart.application.com.gomocart.RELOAD_DATA_INFORMASI"));
		registerReceiver(mHandleLoadDataPerpesananReceiver,  new IntentFilter("gomocart.application.com.gomocart.LOAD_PERPESANAN_LIST"));
		registerReceiver(mHandleReloadDataPerpesananReceiver,  new IntentFilter("gomocart.application.com.gomocart.RELOAD_PERPESANAN_LIST"));
		registerReceiver(mHandleUpdateWishlistReceiver, new IntentFilter("gomocart.application.com.gomocart.UPDATE_WISHLIST"));
		registerReceiver(mHandleOpenDetailProdukReceiver, new IntentFilter("gomocart.application.com.gomocart.MAIN_OPEN_DETAIL_PRODUK"));
		registerReceiver(mHandleLoadListAlamatReceiver, new IntentFilter("gomocart.application.com.gomocart.LOAD_DATA_ALAMAT"));
		registerReceiver(mHandleLoadEkspedisiReceiver, new IntentFilter("gomocart.application.com.gomocart.LOAD_EXPEDISI_LIST"));

		loadDataMoreMenu();
		super.onResume();
	}

	private final BroadcastReceiver mHandleLoadDetailMenuReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (menu_selected == 10) {
				Boolean success = intent.getBooleanExtra("success", false);
				if (!success) {
					MoreFragment.retry.setVisibility(View.VISIBLE);
				} else {
					String detail = intent.getStringExtra("detail");
					MoreFragment.detail_menu.setText(Html.fromHtml(detail));
				}
			}
		}
	};

	private final BroadcastReceiver mHandleLoadMoreMenuReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			moremenuAdapter.UpdateMoreMenuAdapter(moremenulist, submoremenulist);
		}
	};

	private final BroadcastReceiver mHandleLoadDashbooardReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(final Context context, Intent intent) {

			if(menu_selected==0) {
				Boolean success = intent.getBooleanExtra("success", false);
				DashboardFragment.resultLoadDashboard(context, success); //, dashboard_list_banner, dashboard_list_kategori, dashboard_list_tab_kategori);
			}
		}
	};

	private final BroadcastReceiver mHandleLoadListKategoriReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if(menu_selected==1) {
				Boolean success = intent.getBooleanExtra("success", false);

				if(success) {
					kategorilistAdapter = new ListKategoriAdapter(context, kategorilist);
					KategoriFragment.gridViewKategori.setAdapter(kategorilistAdapter);
					KategoriFragment.gridViewKategori.setVisibility(View.VISIBLE);
				} else {
					KategoriFragment.retry.setVisibility(View.VISIBLE);
				}
			}


		}
	};

	private final BroadcastReceiver mHandleLoadListProdukReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {

			if(menu_selected==2) {

				Boolean success = intent.getBooleanExtra("success", false);

				ArrayList<produk_list> temp = intent.getParcelableArrayListExtra("data_produk_list");
				ArrayList<produk> result = temp.get(0).getListData();

				for (produk flist : result) {
					if(first_load_produk || show_produk_in.equalsIgnoreCase("grid")) {
						produkgrid.add(flist);
					}

					if(first_load_produk || show_produk_in.equalsIgnoreCase("list")) {
						produklist.add(flist);
					}
				}

				if(!success) ProdukFragment.retry.setVisibility(View.VISIBLE);

				if(first_load_produk || show_produk_in.equalsIgnoreCase("grid")) {
					produkgridAdapter.UpdateGridProdukAdapter(produkgrid);
				}

				if(first_load_produk || show_produk_in.equalsIgnoreCase("list")) {
					produklistAdapter.UpdateListProdukAdapter(produklist);
				}

				first_load_produk = false;
			}

		}
	};

	private final BroadcastReceiver mHandleLoadListKeranjangReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {

			if(menu_selected==3) {

				Boolean success = intent.getBooleanExtra("success", false);
				alamat data_alamat = (alamat) intent.getSerializableExtra("data_alamat");
				String qty = CommonUtilities.getNumberFormat(intent.getDoubleExtra("qty", 0));
				String jumlah = CommonUtilities.getCurrencyFormat(intent.getDoubleExtra("jumlah", 0), "Rp. ");

				KeranjangFragment.edit_qty.setText(qty);
				KeranjangFragment.edit_jumlah.setText(jumlah);

				updateTotalCartlist();

				ArrayList<cekorder_list> temp = intent.getParcelableArrayListExtra("cekorder_list");

				boolean next_page = false;
				if (temp != null && temp.size() > 0) {
					ArrayList<cekorder> cekorderlist = temp.get(0).getListData();

					String message = "Item tidak valid.";
					for (cekorder data : cekorderlist) {
						if (cekorderlist.indexOf(data) == 0) {
							message = "";
						}
						message += data.getStatus().equalsIgnoreCase("ERROR") ? ("- " + data.getMessage() + "\n") : "";
					}

					dialog_loading.dismiss();
					if (message.length() > 0) {
						text_informasi.setText(message);
						text_title.setText("KESALAHAN");
						dialog_informasi.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
						dialog_informasi.show();
					} else {
						Intent inten = new Intent(context, ProsesCheckoutActivity.class);
						if (data_alamat != null) {
							inten.putExtra("data_alamat", data_alamat);
						}
						startActivityForResult(inten, RESULT_FROM_PROSES_CHECKOUT);
						next_page = true;
					}
				}

				if (!next_page) {
					cartlistAdapter = new CartlistAdapter(context, cartlist);
					KeranjangFragment.listview.setAdapter(cartlistAdapter);
				}
				if (success) {
					KeranjangFragment.linear_cart.setVisibility(View.VISIBLE);
				} else {
					KeranjangFragment.retry.setVisibility(View.VISIBLE);
				}
			}
		}
	};

	private final BroadcastReceiver mHandleLoadListWishReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {

			if(menu_selected==4) {
				Boolean success = intent.getBooleanExtra("success", false);

				if(success) {
					wishlistAdapter = new WishlistAdapter(context, wishlist);
					WishlistFragment.listview.setAdapter(wishlistAdapter);
					WishlistFragment.listview.setVisibility(View.VISIBLE);

					produk data_produk = (produk) intent.getSerializableExtra("produk");
					updateTotalWishlist(data_produk);
				} else {
					WishlistFragment.retry.setVisibility(View.VISIBLE);
				}
			}

		}
	};

	private final BroadcastReceiver mHandleLoadListOngkirReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if(menu_selected==6) {
				Boolean success = intent.getBooleanExtra("success", false);

				if (success) {
					ongkirAdapter = new ListOngkirAdapter(context, ongkirlist);
					OngkosKirimFragment.listViewOngkir.setAdapter(ongkirAdapter);

					OngkosKirimFragment.linear_ongkir.setVisibility(View.VISIBLE);
				} else {
					OngkosKirimFragment.retry.setVisibility(View.VISIBLE);
				}
				dialog_loading.dismiss();
			}
		}
	};

	private final BroadcastReceiver mHandleLoadListOrderReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if(menu_selected==7) {

				Boolean success = intent.getBooleanExtra("success", false);
				int status = intent.getIntExtra("status", 0);

				if (status == 1) {
					orderAdapter_belum_bayar.UpdateListOrderAdapter(orderlist_belum_bayar);
					if (success) {
						DaftarPesananBelumBayarFragment.listViewOrder.setVisibility(View.VISIBLE);
					} else {
						DaftarPesananBelumBayarFragment.retry.setVisibility(View.VISIBLE);
					}
				} else if (status == 2) {
					orderAdapter_sedang_proses.UpdateListOrderAdapter(orderlist_sedang_proses);
					if (success) {
						DaftarPesananSedangProsesFragment.listViewOrder.setVisibility(View.VISIBLE);
					} else {
						DaftarPesananSedangProsesFragment.retry.setVisibility(View.VISIBLE);
					}
				} else if (status == 3) {
					orderAdapter_sedang_kirim.UpdateListOrderAdapter(orderlist_sedang_kirim);
					if (success) {
						DaftarPesananSedangKirimFragment.listViewOrder.setVisibility(View.VISIBLE);
					} else {
						DaftarPesananSedangKirimFragment.retry.setVisibility(View.VISIBLE);
					}
				} else if (status == 4) {
					orderAdapter_selesai.UpdateListOrderAdapter(orderlist_selesai);
					if (success) {
						DaftarPesananSelesaiFragment.listViewOrder.setVisibility(View.VISIBLE);
					} else {
						DaftarPesananSelesaiFragment.retry.setVisibility(View.VISIBLE);
					}
				} else if (status == 5) {
					orderAdapter_batal.UpdateListOrderAdapter(orderlist_batal);
					if (success) {
						DaftarPesananBatalFragment.listViewOrder.setVisibility(View.VISIBLE);
					} else {
						DaftarPesananBatalFragment.retry.setVisibility(View.VISIBLE);
					}
				}
			}
		}
	};

	private final BroadcastReceiver mHandleLoadDataPerpesananReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if(menu_selected==14) {
				Boolean success = intent.getBooleanExtra("success", false);

				if (!success) {
					PerpesananFragment.retry.setVisibility(View.VISIBLE);
				} else {
					ArrayList<perpesanan_list> temp = intent.getParcelableArrayListExtra("perpesanan_list");
					ArrayList<perpesanan> result = temp.get(0).getListData();

					for (perpesanan flist : result) {
						perpesananlist.add(flist);
					}

					perpesananAdapter.UpdateLaporanAdapter(perpesananlist);
				}
			}
		}
	};

	private final BroadcastReceiver mHandleReloadDataPerpesananReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if(menu_selected==14) {
				loadDataPerpesanan(true);
			}
		}
	};

	private final BroadcastReceiver mHandleLoadListInformasiReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (menu_selected == 8) {
				Boolean success = intent.getBooleanExtra("success", false);

				if(!success) {
					InformasiFragment.retry.setVisibility(View.VISIBLE);
				} else {
					ArrayList<informasi_list> temp = intent.getParcelableArrayListExtra("data_informasi_list");
					ArrayList<informasi> result = temp.get(0).getListData();

					for (informasi flist : result) {
						informasilist.add(flist);
					}

					informasiAdapter.UpdateListInformasiAdapter(informasilist);
				}
			}
		}
	};

	private final BroadcastReceiver mHandleReloadInformasiReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (menu_selected == 8) {
				loadDataInformasi(true);
			}
		}
	};

	private final BroadcastReceiver mHandleLoadNotifikasiReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if(menu_selected==13) {
				Boolean success = intent.getBooleanExtra("success", false);

				if (!success) {
					NotifikasiFragment.retry.setVisibility(View.VISIBLE);
				} else {
					ArrayList<notifikasi_list> temp = intent.getParcelableArrayListExtra("notifikasi_list");
					ArrayList<notifikasi> result = temp.get(0).getListData();

					for (notifikasi flist : result) {
						list_notifikasi.add(flist);
					}

					notifikasi_adapter.UpdateListNotifikasiAdapter(list_notifikasi);
				}
			}
		}
	};

	private final BroadcastReceiver mHandleUpdateWishlistReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			produk data_produk = (produk) intent.getSerializableExtra("produk");
			updateTotalWishlist(data_produk);
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

	private final BroadcastReceiver mHandleLoadListAlamatReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Boolean success = intent.getBooleanExtra("success", false);

			if(AlamatFragment.loading!=null) AlamatFragment.loading.setVisibility(View.GONE);
			if(!success && AlamatFragment.retry!=null) AlamatFragment.retry.setVisibility(View.VISIBLE);

			showListAlamat();
		}
	};
	
	private final BroadcastReceiver mHandleOpenDetailProdukReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			produk data = (produk) intent.getSerializableExtra("produk");
			openDetailProduk(data);
		}
	};



	private final BroadcastReceiver mHandleLoadListBankReceiver= new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {

			for(bank data: list_bank) {
				String url_path = CommonUtilities.SERVER_URL+"/uploads/bank/";
				switch (list_bank.indexOf(data)) {
					case 0:
						imageLoader.displayImage(url_path + data.getGambar(), PaymentFragment.image_payment_1, imageOptionBank);
						PaymentFragment.text_payment_1.setText(data.getNama_bank());
						PaymentFragment.text_sub_payment_1.setText("No. Rek. "+data.getNo_rekening()+"\nan. "+data.getNama_pemilik_rekening());

						PaymentFragment.image_payment_1.setVisibility(View.VISIBLE);
						PaymentFragment.text_payment_1.setVisibility(View.VISIBLE);
						PaymentFragment.text_sub_payment_1.setVisibility(View.VISIBLE);
						break;
					case 1:
						imageLoader.displayImage(url_path + data.getGambar(), PaymentFragment.image_payment_2, imageOptionBank);
						PaymentFragment.text_payment_2.setText(data.getNama_bank());
						PaymentFragment.text_sub_payment_2.setText("No. Rek. "+data.getNo_rekening()+"\nan. "+data.getNama_pemilik_rekening());

						PaymentFragment.image_payment_2.setVisibility(View.VISIBLE);
						PaymentFragment.text_payment_2.setVisibility(View.VISIBLE);
						PaymentFragment.text_sub_payment_2.setVisibility(View.VISIBLE);
						break;
					case 2:
						imageLoader.displayImage(url_path + data.getGambar(), PaymentFragment.image_payment_3, imageOptionBank);
						PaymentFragment.text_payment_3.setText(data.getNama_bank());
						PaymentFragment.text_sub_payment_3.setText("No. Rek. "+data.getNo_rekening()+"\nan. "+data.getNama_pemilik_rekening());

						PaymentFragment.image_payment_3.setVisibility(View.VISIBLE);
						PaymentFragment.text_payment_3.setVisibility(View.VISIBLE);
						PaymentFragment.text_sub_payment_3.setVisibility(View.VISIBLE);
						break;
					case 3:
						imageLoader.displayImage(url_path + data.getGambar(), PaymentFragment.image_payment_4, imageOptionBank);
						PaymentFragment.text_payment_4.setText(data.getNama_bank());
						PaymentFragment.text_sub_payment_4.setText("No. Rek. "+data.getNo_rekening()+"\nan. "+data.getNama_pemilik_rekening());

						PaymentFragment.image_payment_4.setVisibility(View.VISIBLE);
						PaymentFragment.text_payment_4.setVisibility(View.VISIBLE);
						PaymentFragment.text_sub_payment_4.setVisibility(View.VISIBLE);
						break;
				}
			}
		}
	};

	public void showListAlamat() {

		String keyword = AlamatFragment.edit_search.getText().toString();
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

		alamatAdapter.UpdateAlamatAdapter(alamatlist_display);
	}





	public void openDialogMessage(String message, boolean status) {
		text_informasi.setText(message);
		text_title.setText(status?"BERHASIL":"KESALAHAN");
		dialog_informasi.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		dialog_informasi.show();
	}

	private final BroadcastReceiver mHandleEditDataProfileReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Boolean success = intent.getBooleanExtra("success", false);
			String message = intent.getStringExtra("message");
			if(success) {
				setSignIn();
				displayView(11);
			}

			dialog_loading.dismiss();
			text_informasi.setText(message);
			text_title.setText(success?"BERHASIL":"GAGAL");
			dialog_informasi.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			dialog_informasi.show();
		}
	};

	public void openProdukKategori(kategori kat) {
		is_search = false;
		search_keyword = "";
		select_kategori = kat;

		filter_kategori = "";
		filter_brand = "";
		filter_ukuran = "";
		filter_warna = "";
		filter_harga_max = "";
		filter_harga_min = "";
		filter_diskon_max = "";
		filter_diskon_min = "";

		displayView(2);
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);

		savedInstanceState.putInt("menu_selected", menu_selected);
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);

		menu_selected = savedInstanceState.getInt("menu_selected");
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data_intent) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data_intent);

		String fileName = new SimpleDateFormat("yyyyMMddhhmmss'.jpg'").format(new Date());
		String dest = CommonUtilities.getOutputPath(context, "images")+File.separator+fileName;

		if (resultCode == RESULT_OK) {
			voucher data_voucher = null;
			bank data_bank = null;
			String action = "";
			Intent intn = null;
			order data_order = null;
			switch (requestCode) {
				case RESULT_FROM_PRODUK_DETAIL:
					produk data_produk = (produk) data_intent.getSerializableExtra("produk");

					user get_data_user = CommonUtilities.getSettingUser(context);
					if(get_data_user.getId()!=0 && get_data_user.getId()!=data.getId()) {
						data = CommonUtilities.getSettingUser(context);
						setSignIn();
					}

					updateTotalCartlist();
					updateTotalWishlist(data_produk);

					String detail_go_to = data_intent.getStringExtra("goto");
					if(detail_go_to!=null) {
						if (detail_go_to.equalsIgnoreCase("cart_list")) {
							displayView(3);
							return;
						}

						if (detail_go_to.equalsIgnoreCase("wish_list")) {
							displayView(4);
							return;
						}
					}

					boolean cekongkir = data_intent.getBooleanExtra("cekongkir", false);
					if(cekongkir) {
						displayView(6);
					}

					boolean opendetail = data_intent.getBooleanExtra("opendetail", false);
					if(opendetail) {
						produk data = (produk) data_intent.getSerializableExtra("produk");
						openDetailProduk(data);
					}
					break;
				case Crop.REQUEST_CROP:
					mImageCaptureUri = Crop.getOutput(data_intent);
					//ProfileFragment.image_profile.setImageURI(Crop.getOutput(data_intent));
					simpanDataPhoto();

					break;
				case REQUEST_FROM_CAMERA:
					CommonUtilities.compressImage(context, data_intent.getStringExtra("path"), dest);
					mImageCaptureUri = Uri.fromFile(new File(dest));
					beginCrop(mImageCaptureUri);


					break;
				case REQUEST_FROM_GALLERY:
					Uri selectedUri = data_intent.getData();
					CommonUtilities.compressImage(context, GalleryFilePath.getPath(context, selectedUri), dest);
					mImageCaptureUri = Uri.fromFile(new File(dest));
					beginCrop(mImageCaptureUri);

					break;

				case REQUEST_FROM_FILTER:
					is_search = false;
					search_keyword = "";
					select_kategori = null;

					filter_kategori = data_intent.getStringExtra("filter_kategori");
					filter_brand = data_intent.getStringExtra("filter_brand");
					filter_ukuran = data_intent.getStringExtra("filter_ukuran");
					filter_warna = data_intent.getStringExtra("filter_warna");
					filter_harga_max = data_intent.getStringExtra("filter_harga_max");
					filter_harga_min= data_intent.getStringExtra("filter_harga_min");
					filter_diskon_max = data_intent.getStringExtra("filter_diskon_max");
					filter_diskon_min= data_intent.getStringExtra("filter_diskon_min");

					first_load_produk = true;

					next_page_data_produk_grid = 1;
					produkgrid = new ArrayList<>();
					produkgridAdapter.UpdateGridProdukAdapter(produkgrid);

					next_page_data_produk_list = 1;
					produklist = new ArrayList<>();
					produklistAdapter.UpdateListProdukAdapter(produklist);

					loadDataProduk();

					break;
				case RESULT_FROM_SIGN_IN:
					data = CommonUtilities.getSettingUser(context);
					//Toast.makeText(context, data.getLast_name(), Toast.LENGTH_LONG).show();
					menu_selected = data_intent.getIntExtra("menu_selected", 0);

					boolean is_login = data_intent.getBooleanExtra("is_login", false);
					if(is_login) { setSignIn(); }

					boolean from_checkout = data_intent.getBooleanExtra("from_checkout", false);
					if(from_checkout){ new prosesCekOrder().execute(); }

					checkGcmRegid();

					break;
				case RESULT_FROM_SIGN_UP:
				    String go_to_login = data_intent.getStringExtra("go_to");
                    if(go_to_login!=null && go_to_login.equalsIgnoreCase("login")) {
                        openPageLogin();
                    } else {
                        Intent intent = new Intent(context, AktivasiSmsActivity.class);
                        startActivityForResult(intent, RESULT_FROM_AKTIVASI);
                    }

					break;

				case RESULT_FROM_AKTIVASI:
					Intent intent_ = new Intent(context, LoginActivity.class);
					startActivityForResult(intent_, RESULT_FROM_SIGN_IN);

					break;

				case RESULT_FROM_KONF_PEMB:
					if(menu_selected==7) {
						loadOrderlistBelumBayar();
						loadOrderlistSedangProses();
					}

					break;

				case RESULT_FROM_PROSES_CHECKOUT:
					updateTotalCartlist();
					displayView(0);

					String go_to = data_intent.getStringExtra("goto");
					if(go_to!=null && go_to.equalsIgnoreCase("konfirmasi")) {
						data_order = (order) data_intent.getSerializableExtra("data_order");
						openKonfirmasiPembayaran(data_order);
					}

					break;

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

				case RESULT_FROM_KIRIM_PESAN:
					data_produk = (produk) data_intent.getSerializableExtra("produk");

					detail_go_to = data_intent.getStringExtra("goto");
					if(detail_go_to!=null) {
						if (detail_go_to.equalsIgnoreCase("cart_list")) {
							displayView(3);
							return;
						}

						if (detail_go_to.equalsIgnoreCase("wish_list")) {
							displayView(4);
							return;
						}

						if (detail_go_to.equalsIgnoreCase("detail_produk")) {
							openDetailProduk(data_produk);
							return;
						}

					} else {
						loadDataPerpesanan(true);
					}

					break;
			}
		}
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

	public void updateSummaryCart() {
		double total_qty = 0;
		double jumlah = 0;

		for(produk data: cartlist) {
			total_qty+=data.getQty();
			jumlah+=data.getGrandtotal();
		}

		KeranjangFragment.edit_qty.setText(CommonUtilities.getNumberFormat(total_qty));
		KeranjangFragment.edit_jumlah.setText(CommonUtilities.getCurrencyFormat(jumlah, "Rp. "));
	}

	public void updateTotalWishlist(produk data_produk) {
		total_wishlist = dh.getTotalWishlist();

		/*if(menu_selected==0) {
			for(produk terbaru: listProdukTerbaru) {
				if(terbaru.getId()==data_produk.getId()) {
					listProdukTerbaru.get(listProdukTerbaru.indexOf(terbaru)).setIs_wishlist(dh.getIdWishlist(terbaru.getId())>0);
					//produkTerbaruAdapter.notifyDataSetChanged();
					break;
				}
			}

			for(produk promo: listProdukPromo) {
				if(promo.getId()==data_produk.getId()) {
					listProdukPromo.get(listProdukPromo.indexOf(promo)).setIs_wishlist(dh.getIdWishlist(promo.getId())>0);
					//produkPromoAdapter.notifyDataSetChanged();
					break;
				}
			}
		}*/

		if(menu_selected==2) {
			for(produk prod: produklist) {
				if(prod.getId()==data_produk.getId()) {
					produklist.get(produklist.indexOf(prod)).setIs_wishlist(dh.getIdWishlist(prod.getId())>0);
					//produklistAdapter.notifyDataSetChanged();
					break;
				}
			}

			for(produk prod: produkgrid) {
				if(prod.getId()==data_produk.getId()) {
					produkgrid.get(produkgrid.indexOf(prod)).setIs_wishlist(dh.getIdWishlist(prod.getId())>0);
					//produkgridAdapter.notifyDataSetChanged();
					break;
				}
			}
		}
		number_wish.setText(total_wishlist+"");
		number_wish.setVisibility(total_wishlist>0?View.VISIBLE:View.INVISIBLE);
	}


	public void loadDataMoreMenu() {
		new loadDataMoreMenu().execute();
	}

	public class loadDataMoreMenu extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(String... urls) {

			String url = CommonUtilities.SERVER_URL + "/store/androidMoreMenuDataStore.php";
			List<NameValuePair> params = new ArrayList<>();
			params.add(new BasicNameValuePair("id_user", data.getId()+""));
			JSONObject json = new JSONParser().getJSONFromUrl(url, params, null);

			ArrayList<moremenu> data_child = new ArrayList<>();
			if (json != null) {
				try {
					JSONArray list_moremenu = json.isNull("topics") ? null : json.getJSONArray("topics");
					for (int i = 0; i < list_moremenu.length(); i++) {
						JSONObject rec = list_moremenu.getJSONObject(i);

						int id = rec.isNull("id") ? null : rec.getInt("id");
						String kode = rec.isNull("kode") ? null : rec.getString("kode");
						String nama = rec.isNull("nama") ? null : rec.getString("nama");
						String url_image = rec.isNull("image") ? null : rec.getString("image");

						data_child.add(new moremenu(id, kode, nama, url_image, 0));
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			submoremenulist = new LinkedHashMap<>();
			submoremenulist.put(moremenulist.get(moremenulist.size()-1), data_child);

			Intent i = new Intent("gomocart.application.com.gomocart.LOAD_MORE_MENU");
			sendBroadcast(i);

			return null;
		}
	}

	public void loadDataDashboard(boolean reset) {
		new loadDataDashboard(reset).execute();

	}

	public class loadDataDashboard extends AsyncTask<String, Void, Void> {

		boolean reset;
		public loadDataDashboard(boolean reset) {
			this.reset = reset;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(String... urls) {

			try {
				Thread.sleep(300);
			} catch (Exception e) {
				e.printStackTrace();
			}

			Boolean success = true;
			if(reset || dashboard_list_banner.size()==0) {
				success                     = false;
				dashboard_list_banner       = new ArrayList<>();
				dashboard_list_kategori     = new ArrayList<>();
				dashboard_list_tab_kategori = new ArrayList<>();

				String url = CommonUtilities.SERVER_URL + "/store/androidDashboardDataStore.php";
				List<NameValuePair> params = new ArrayList<>();
				params.add(new BasicNameValuePair("id_user", data.getId()+""));
				JSONObject json = new JSONParser().getJSONFromUrl(url, params, null);

				if (json != null) {
					try {

						//Banner
						JSONArray list_banner = json.isNull("banner") ? null : json.getJSONArray("banner");
						for (int i = 0; i < list_banner.length(); i++) {
							JSONObject rec = list_banner.getJSONObject(i);

							int id = rec.isNull("id") ? null : rec.getInt("id");
							String nama = rec.isNull("nama") ? null : rec.getString("nama");
							String kategori = rec.isNull("kategori") ? null : rec.getString("kategori");
							String banner = rec.isNull("banner") ? null : rec.getString("banner");

							dashboard_list_banner.add(new banner(id, nama, kategori, banner));
						}

						//Kategori
						JSONArray list_kategori = json.isNull("kategori")?null:json.getJSONArray("kategori");
						for (int i=0; i<list_kategori.length(); i++) {
							JSONObject rec = list_kategori.getJSONObject(i);

							int id = rec.isNull("id")?0:rec.getInt("id");
							String nama = rec.isNull("nama")?null:rec.getString("nama");
							String penjelasan = rec.isNull("penjelasan")?null:rec.getString("penjelasan");
							String header = rec.isNull("header")?null:rec.getString("header");
							dashboard_list_kategori.add(new kategori(id, nama, penjelasan, header));
						}

						//Tab kategori
						JSONArray list_tab_kategori = json.isNull("tab_kategori")?null:json.getJSONArray("tab_kategori");
						for (int i=0; i<list_tab_kategori.length(); i++) {
							JSONObject rec = list_tab_kategori.getJSONObject(i);

							int id = rec.isNull("id")?0:rec.getInt("id");
							String nama = rec.isNull("nama")?null:rec.getString("nama");
							ArrayList<produk> produk_list = new ArrayList<>();

							/*JSONArray topics = rec.isNull("produk")?null:rec.getJSONArray("produk");
							for (int j=0; j<topics.length(); j++) {
								JSONObject rec_produk = topics.getJSONObject(j);

								int id_produk = rec_produk.isNull("id")?0:rec_produk.getInt("id");
								String kode = rec_produk.isNull("kode")?"":rec_produk.getString("kode");
								String nama_produk = rec_produk.isNull("nama")?"":rec_produk.getString("nama");
								int id_category = rec_produk.isNull("id_category")?0:rec_produk.getInt("id_category");
								String category_name = rec_produk.isNull("category_name")?"":rec_produk.getString("category_name");
								String penjelasan = rec_produk.isNull("penjelasan")?"":rec_produk.getString("penjelasan");
								String foto1_produk = rec_produk.isNull("foto1_produk")?"":rec_produk.getString("foto1_produk");
								double harga_beli = rec_produk.isNull("harga_beli")?0:rec_produk.getDouble("harga_beli");
								double harga_jual = rec_produk.isNull("harga_jual")?0:rec_produk.getDouble("harga_jual");
								double harga_diskon = rec_produk.isNull("harga_diskon")?0:rec_produk.getDouble("harga_diskon");
								int persen_diskon = rec_produk.isNull("persen_diskon")?0:rec_produk.getInt("persen_diskon");
								int berat = rec_produk.isNull("berat")?0:rec_produk.getInt("berat");
								String list_ukuran = rec_produk.isNull("list_ukuran")?"":rec_produk.getString("list_ukuran");
								String ukuran = rec_produk.isNull("ukuran")?"":rec_produk.getString("ukuran");
								String list_warna = rec_produk.isNull("list_warna")?"":rec_produk.getString("list_warna");
								String warna = rec_produk.isNull("warna")?"":rec_produk.getString("warna");
								int qty = rec_produk.isNull("qty")?0:rec_produk.getInt("qty");
								int max_qty = rec_produk.isNull("max_qty")?0:rec_produk.getInt("max_qty");
								int minimum_pesan = rec_produk.isNull("minimum_pesan")?1:rec_produk.getInt("minimum_pesan");
								int produk_promo = rec_produk.isNull("produk_promo")?0:rec_produk.getInt("produk_promo");
								int produk_featured = rec_produk.isNull("produk_featured")?0:rec_produk.getInt("produk_featured");
								int produk_terbaru = rec_produk.isNull("produk_terbaru")?0:rec_produk.getInt("produk_terbaru");
								int produk_preorder = rec_produk.isNull("produk_preorder")?0:rec_produk.getInt("produk_preorder");
								int produk_soldout = rec_produk.isNull("produk_soldout")?0:rec_produk.getInt("produk_soldout");
								int produk_grosir = rec_produk.isNull("produk_grosir")?0:rec_produk.getInt("produk_grosir");
								int rating = rec_produk.isNull("rating")?0:rec_produk.getInt("rating");
								int responden = rec_produk.isNull("responden")?0:rec_produk.getInt("responden");
								int review = rec_produk.isNull("review")?0:rec_produk.getInt("review");

								produk_list.add(new produk(id_produk, kode, nama_produk, id_category, category_name, penjelasan, foto1_produk, harga_beli, harga_jual, harga_diskon, persen_diskon, berat, list_ukuran, ukuran, list_warna, warna, qty, max_qty, minimum_pesan, dh.getIdWishlist(id)>0, produk_promo, produk_featured, produk_terbaru, produk_preorder, produk_soldout, produk_grosir, rating, responden, review));
							}*/

							dashboard_list_tab_kategori.add(new produk_kategori(id, nama, produk_list));
						}
						success = true;

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			Intent i = new Intent("gomocart.application.com.gomocart.LOAD_DATA_DASHBOARD");
			i.putExtra("success", success);
			sendBroadcast(i);

			return null;
		}
	}
	
	public void loadDataInformasi(boolean starting) {
		if(starting) {
			next_page_informasi = 1;
			informasilist = new ArrayList<>();
			informasiAdapter = new InformasiAdapter(context, informasilist);
			InformasiFragment.listview.setAdapter(informasiAdapter);
		}
		new loadDataInformasi().execute();
	}


	public class loadDataInformasi extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			InformasiFragment.retry.setVisibility(View.GONE);
		}

		@Override
		protected Void doInBackground(String... urls) {

			ArrayList<informasi> result = null;
			String url = CommonUtilities.SERVER_URL + "/store/androidInformasiDataStore.php";

			List<NameValuePair> params = new ArrayList<>();
			params.add(new BasicNameValuePair("id_user", data.getId()+""));
			params.add(new BasicNameValuePair("page", next_page_informasi+""));

			JSONObject json = new JSONParser().getJSONFromUrl(url, params, null);
			if(json!=null) {
				try {
					result = new ArrayList<>();
					next_page_informasi = json.isNull("next_page") ? next_page_informasi : json.getInt("next_page");
					JSONArray topics = json.isNull("topics")?null:json.getJSONArray("topics");
					for (int i=0; i<topics.length(); i++) {
						JSONObject rec = topics.getJSONObject(i);

						int id = rec.isNull("id")?0:rec.getInt("id");
						String tanggal = rec.isNull("tanggal")?"":rec.getString("tanggal");
						String judul = rec.isNull("judul")?"":rec.getString("judul");
						String header = rec.isNull("header")?"":rec.getString("header");
						String konten = rec.isNull("konten")?"":rec.getString("konten");
						String gambar = rec.isNull("gambar")?"":rec.getString("gambar");

						result.add(new informasi(id, tanggal, judul, header, konten, gambar));
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			Boolean success = result!=null;
			if(result==null) result = new ArrayList<>();
			ArrayList<informasi_list> temp = new ArrayList<>();
			temp.add(new informasi_list(result));

			Intent i = new Intent("gomocart.application.com.gomocart.LOAD_DATA_INFORMASI");
			i.putExtra("data_informasi_list", temp);
			i.putExtra("success", success);
			sendBroadcast(i);

			return null;
		}
	}

	public void loadDetailMenu(moremenu menu) {
		moremenu_select = menu;
		displayView(10);
	}

	public void prosesLoadDetailMenu() {
		new loadDetailMenu().execute();
	}

	public class loadDetailMenu extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			MoreFragment.retry.setVisibility(View.GONE);
		}

		@Override
		protected Void doInBackground(String... urls) {

			boolean success = false;
			List<NameValuePair> params = new ArrayList<>();
			params.add(new BasicNameValuePair("id_user", data.getId()+""));
			params.add(new BasicNameValuePair("id_menu", moremenu_select.getId()+""));

			String detail = "";
			String url = CommonUtilities.SERVER_URL + "/store/androidDetailMenuStore.php";
			JSONObject json = new JSONParser().getJSONFromUrl(url, params, null);
			if(json!=null) {
				try {
					detail = json.isNull("detail")?"":json.getString("detail");

					success = true;
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			Intent i = new Intent("gomocart.application.com.gomocart.LOAD_DATA_DETAIL_MENU");
			i.putExtra("detail", detail);
			i.putExtra("success", success);
			sendBroadcast(i);

			return null;
		}
	}

	public void loadCartlist() {
		new loadCartlist().execute();
	}

	public class loadCartlist extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			KeranjangFragment.linear_cart.setVisibility(View.GONE);
			KeranjangFragment.retry.setVisibility(View.GONE);
		}

		@Override
		protected Void doInBackground(String... urls) {

			cartlist = new ArrayList<>();
			ArrayList<produk> temp_cartlist = dh.getCartlist();
			boolean success = false;
			double total_qty = 0;
			double total_jumlah = 0;

			List<NameValuePair> params = new ArrayList<>();
			params.add(new BasicNameValuePair("id_user", data.getId()+""));
			params.add(new BasicNameValuePair("cart", dh.getStringCartlist()));

			String url = CommonUtilities.SERVER_URL + "/store/androidCartDataStore.php";
			JSONObject json = new JSONParser().getJSONFromUrl(url, params, null);
			if(json!=null) {
				try {

					total_qty        = json.isNull("qty")?0:json.getDouble("qty");
					total_jumlah     = json.isNull("jumlah")?0:json.getDouble("jumlah");
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
						JSONArray list_stok = rec.isNull("list_stok")?null:rec.getJSONArray("list_stok");

						for(produk data: temp_cartlist) {
							if(data.get_id()==_id) {
								data.setQty(jumlah);
								data.setBerat(berat);
								data.setHarga_beli(harga_beli);
								data.setHarga_jual(harga_jual);
								data.setHarga_diskon(harga_diskon);
								data.setPersen_diskon(persen_diskon);
								data.setSubtotal(subtotal);
								data.setGrandtotal(grandtotal);

								temp_cartlist.remove(data);

								//LIST STOK
								ArrayList<stok> liststok = new ArrayList<>();
								if(list_stok!=null) {
									for (int _i = 0; _i < list_stok.length(); _i++) {
										JSONObject rec_list_stok = list_stok.getJSONObject(_i);

										String sukuran = rec_list_stok.isNull("ukuran") ? "" : rec_list_stok.getString("ukuran");
										String swarna = rec_list_stok.isNull("warna") ? "" : rec_list_stok.getString("warna");
										int qty = rec_list_stok.isNull("jumlah") ? 0 : rec_list_stok.getInt("jumlah");

										liststok.add(new stok(sukuran, swarna, qty));
									}
								}
								data.setList_stok(liststok);
								cartlist.add(data);
								break;
							}
						}
					}

					dh.deleteCartlist();
					dh.insertCartlist(cartlist);

					success = true;
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			Intent i = new Intent("gomocart.application.com.gomocart.LOAD_DATA_CART");
			i.putExtra("success", success);
			i.putExtra("qty", total_qty);
			i.putExtra("jumlah", total_jumlah);
			sendBroadcast(i);

			return null;
		}
	}

	public void loadWishlist() {
		new loadWishlist().execute();
	}

	public class loadWishlist extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			WishlistFragment.listview.setVisibility(View.GONE);
			WishlistFragment.retry.setVisibility(View.GONE);
		}

		@Override
		protected Void doInBackground(String... urls) {

			wishlist = new ArrayList<>();
			ArrayList<produk> temp_cartlist = dh.getWishlist();
			boolean success = false;

			List<NameValuePair> params = new ArrayList<>();
			params.add(new BasicNameValuePair("id_user", data.getId()+""));
			params.add(new BasicNameValuePair("cart", dh.getStringWishlist()));

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

						for(produk data: temp_cartlist) {
							if(data.get_id()==_id) {
								data.setQty(jumlah);
								data.setBerat(berat);
								data.setHarga_beli(harga_beli);
								data.setHarga_jual(harga_jual);
								data.setHarga_diskon(harga_diskon);
								data.setPersen_diskon(persen_diskon);
								data.setSubtotal(subtotal);
								data.setGrandtotal(grandtotal);

								temp_cartlist.remove(data);
								wishlist.add(data);
								break;
							}
						}
					}

					dh.deleteWishlist();
					dh.insertWishlist(wishlist);

					success = true;
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			Intent i = new Intent("gomocart.application.com.gomocart.LOAD_DATA_WISH");
			i.putExtra("success", success);
			sendBroadcast(i);

			return null;
		}
	}


	public void loadAlamatlist() {
		new loadAlamatlist().execute();
	}

	public class loadAlamatlist extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			if(AlamatFragment.loading!=null) AlamatFragment.loading.setVisibility(View.VISIBLE);
			if(AlamatFragment.retry!=null) AlamatFragment.retry.setVisibility(View.GONE);
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
						alamat data_alamat = new alamat(id, nama, alamat, province_id, province, city_id, city, subdistrict_id, subdistrict, kode_pos, no_hp, as_defult, false, "", "", "");

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

	public void deleteSelectedWistlist(produk data_produk) {
		delete_selected_wistlist = data_produk;
		dialog_delete_wistlist.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		dialog_delete_wistlist.show();
	}

	public void utamakanSelectedAlamat(alamat data_alamat) {
		new prosesUtamakanAlamat(data_alamat).execute();
	}
	
	@Override
	public void onSliderClick(BaseSliderView slider) {

	}

	@Override
	public void onBackPressed() {
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}

	public void initialOrderlistBelumBayar() {
		orderlist_belum_bayar = new ArrayList<>();
		orderAdapter_belum_bayar = new ListOrderAdapter(context, orderlist_belum_bayar);
		DaftarPesananBelumBayarFragment.listViewOrder.setAdapter(orderAdapter_belum_bayar);
		new loadOrderlistBelumBayar().execute();
	}

	public void loadOrderlistBelumBayar() {
		orderlist_belum_bayar = new ArrayList<>();
		orderAdapter_belum_bayar.UpdateListOrderAdapter(orderlist_belum_bayar);
		new loadOrderlistBelumBayar().execute();
	}

	public class loadOrderlistBelumBayar extends AsyncTask<String, Void, Void> {
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			DaftarPesananBelumBayarFragment.retry.setVisibility(View.GONE);
			DaftarPesananBelumBayarFragment.listViewOrder.setVisibility(View.GONE);
		}

		@Override
		protected Void doInBackground(String... urls) {

			boolean success = false;


			String url = CommonUtilities.SERVER_URL + "/store/androidOrderDataStore.php";
			List<NameValuePair> params = new ArrayList<>();
			params.add(new BasicNameValuePair("order", dh.getStringOrderlist()));
			params.add(new BasicNameValuePair("id_cat", "1"));
			JSONObject json = new JSONParser().getJSONFromUrl(url, params, null);

			if (json != null) {
				try {
					JSONArray topics = json.isNull("topics") ? null : json.getJSONArray("topics");
					for (int i = 0; i < topics.length(); i++) {
						JSONObject rec = topics.getJSONObject(i);

						String no_transaksi = rec.isNull("no_transaksi") ? "" : rec.getString("no_transaksi");
						String tgl_transaksi = rec.isNull("tgl_transaksi") ? "" : rec.getString("tgl_transaksi");
						int pembayaran = rec.isNull("pembayaran") ? 0 : rec.getInt("pembayaran");
						String nama = rec.isNull("nama") ? "" : rec.getString("nama");
						int qty = rec.isNull("qty") ? 0 : rec.getInt("qty");
						double jumlah = rec.isNull("jumlah") ? 0 : rec.getDouble("jumlah");
						String estimasi = rec.isNull("estimasi") ? "" : rec.getString("estimasi");
						String kurir = rec.isNull("kurir") ? "" : rec.getString("kurir");
						String noresi = rec.isNull("noresi") ? "" : rec.getString("noresi");
						String gambar = rec.isNull("gambar") ? "" : rec.getString("gambar");
						int status = rec.isNull("status") ? 0 : rec.getInt("status");

						orderlist_belum_bayar.add(new order(no_transaksi, tgl_transaksi, pembayaran, nama, qty, jumlah, estimasi, kurir, noresi, gambar, status, data.getId()));
					}

					success = true;
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			Intent i = new Intent("gomocart.application.com.gomocart.LOAD_DATA_ORDER");
			i.putExtra("success", success);
			i.putExtra("status", 1);
			sendBroadcast(i);

			return null;
		}
	}
	
	public void initialOrderlistSedangProses() {
		orderlist_sedang_proses = new ArrayList<>();
		orderAdapter_sedang_proses = new ListOrderAdapter(context, orderlist_sedang_proses);
		DaftarPesananSedangProsesFragment.listViewOrder.setAdapter(orderAdapter_sedang_proses);
		new loadOrderlistSedangProses().execute();
	}

	public void loadOrderlistSedangProses() {
		orderlist_sedang_proses = new ArrayList<>();
		orderAdapter_sedang_proses.UpdateListOrderAdapter(orderlist_sedang_proses);
		new loadOrderlistSedangProses().execute();
	}

	public class loadOrderlistSedangProses extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			DaftarPesananSedangProsesFragment.retry.setVisibility(View.GONE);
			DaftarPesananSedangProsesFragment.listViewOrder.setVisibility(View.GONE);
		}

		@Override
		protected Void doInBackground(String... urls) {

			boolean success = false;


			String url = CommonUtilities.SERVER_URL + "/store/androidOrderDataStore.php";
			List<NameValuePair> params = new ArrayList<>();
			params.add(new BasicNameValuePair("order", dh.getStringOrderlist()));
			params.add(new BasicNameValuePair("id_cat", "2"));
			JSONObject json = new JSONParser().getJSONFromUrl(url, params, null);

			if (json != null) {
				try {
					JSONArray topics = json.isNull("topics") ? null : json.getJSONArray("topics");
					for (int i = 0; i < topics.length(); i++) {
						JSONObject rec = topics.getJSONObject(i);

						String no_transaksi = rec.isNull("no_transaksi") ? "" : rec.getString("no_transaksi");
						String tgl_transaksi = rec.isNull("tgl_transaksi") ? "" : rec.getString("tgl_transaksi");
						int pembayaran = rec.isNull("pembayaran") ? 0 : rec.getInt("pembayaran");
						String nama = rec.isNull("nama") ? "" : rec.getString("nama");
						int qty = rec.isNull("qty") ? 0 : rec.getInt("qty");
						double jumlah = rec.isNull("jumlah") ? 0 : rec.getDouble("jumlah");
						String estimasi = rec.isNull("estimasi") ? "" : rec.getString("estimasi");
						String kurir = rec.isNull("kurir") ? "" : rec.getString("kurir");
						String noresi = rec.isNull("noresi") ? "" : rec.getString("noresi");
						String gambar = rec.isNull("gambar") ? "" : rec.getString("gambar");
						int status = rec.isNull("status") ? 0 : rec.getInt("status");

						orderlist_sedang_proses.add(new order(no_transaksi, tgl_transaksi, pembayaran, nama, qty, jumlah, estimasi, kurir, noresi, gambar, status, data.getId()));
					}

					success = true;
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			Intent i = new Intent("gomocart.application.com.gomocart.LOAD_DATA_ORDER");
			i.putExtra("success", success);
			i.putExtra("status", 2);
			sendBroadcast(i);

			return null;
		}
	}

	public void initialOrderlistSedangKirim() {
		orderlist_sedang_kirim = new ArrayList<>();
		orderAdapter_sedang_kirim = new ListOrderAdapter(context, orderlist_sedang_kirim);
		DaftarPesananSedangKirimFragment.listViewOrder.setAdapter(orderAdapter_sedang_kirim);
		new loadOrderlistSedangKirim().execute();
	}

	public void loadOrderlistSedangKirim() {
		orderlist_sedang_kirim = new ArrayList<>();
		orderAdapter_sedang_kirim.UpdateListOrderAdapter(orderlist_sedang_kirim);
		new loadOrderlistSedangKirim().execute();
	}

	public class loadOrderlistSedangKirim extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			DaftarPesananSedangKirimFragment.retry.setVisibility(View.GONE);
			DaftarPesananSedangKirimFragment.listViewOrder.setVisibility(View.GONE);
		}

		@Override
		protected Void doInBackground(String... urls) {

			boolean success = false;


			String url = CommonUtilities.SERVER_URL + "/store/androidOrderDataStore.php";
			List<NameValuePair> params = new ArrayList<>();
			params.add(new BasicNameValuePair("order", dh.getStringOrderlist()));
			params.add(new BasicNameValuePair("id_cat", "3"));
			JSONObject json = new JSONParser().getJSONFromUrl(url, params, null);

			if (json != null) {
				try {
					JSONArray topics = json.isNull("topics") ? null : json.getJSONArray("topics");
					for (int i = 0; i < topics.length(); i++) {
						JSONObject rec = topics.getJSONObject(i);

						String no_transaksi = rec.isNull("no_transaksi") ? "" : rec.getString("no_transaksi");
						String tgl_transaksi = rec.isNull("tgl_transaksi") ? "" : rec.getString("tgl_transaksi");
						int pembayaran = rec.isNull("pembayaran") ? 0 : rec.getInt("pembayaran");
						String nama = rec.isNull("nama") ? "" : rec.getString("nama");
						int qty = rec.isNull("qty") ? 0 : rec.getInt("qty");
						double jumlah = rec.isNull("jumlah") ? 0 : rec.getDouble("jumlah");
						String estimasi = rec.isNull("estimasi") ? "" : rec.getString("estimasi");
						String kurir = rec.isNull("kurir") ? "" : rec.getString("kurir");
						String noresi = rec.isNull("noresi") ? "" : rec.getString("noresi");
						String gambar = rec.isNull("gambar") ? "" : rec.getString("gambar");
						int status = rec.isNull("status") ? 0 : rec.getInt("status");

						orderlist_sedang_kirim.add(new order(no_transaksi, tgl_transaksi, pembayaran, nama, qty, jumlah, estimasi, kurir, noresi, gambar, status, data.getId()));
					}

					success = true;
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			Intent i = new Intent("gomocart.application.com.gomocart.LOAD_DATA_ORDER");
			i.putExtra("success", success);
			i.putExtra("status", 3);
			sendBroadcast(i);

			return null;
		}
	}


	public void initialOrderlistSelesai() {
		orderlist_selesai = new ArrayList<>();
		orderAdapter_selesai = new ListOrderAdapter(context, orderlist_selesai);
		DaftarPesananSelesaiFragment.listViewOrder.setAdapter(orderAdapter_selesai);
		new loadOrderlistSelesai().execute();
	}

	public void loadOrderlistSelesai() {
		orderlist_selesai = new ArrayList<>();
		orderAdapter_selesai.UpdateListOrderAdapter(orderlist_selesai);
		new loadOrderlistSelesai().execute();
	}

	public class loadOrderlistSelesai extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			DaftarPesananSelesaiFragment.retry.setVisibility(View.GONE);
			DaftarPesananSelesaiFragment.listViewOrder.setVisibility(View.GONE);
		}

		@Override
		protected Void doInBackground(String... urls) {

			boolean success = false;


			String url = CommonUtilities.SERVER_URL + "/store/androidOrderDataStore.php";
			List<NameValuePair> params = new ArrayList<>();
			params.add(new BasicNameValuePair("order", dh.getStringOrderlist()));
			params.add(new BasicNameValuePair("id_cat", "4"));
			JSONObject json = new JSONParser().getJSONFromUrl(url, params, null);

			if (json != null) {
				try {
					JSONArray topics = json.isNull("topics") ? null : json.getJSONArray("topics");
					for (int i = 0; i < topics.length(); i++) {
						JSONObject rec = topics.getJSONObject(i);

						String no_transaksi = rec.isNull("no_transaksi") ? "" : rec.getString("no_transaksi");
						String tgl_transaksi = rec.isNull("tgl_transaksi") ? "" : rec.getString("tgl_transaksi");
						int pembayaran = rec.isNull("pembayaran") ? 0 : rec.getInt("pembayaran");
						String nama = rec.isNull("nama") ? "" : rec.getString("nama");
						int qty = rec.isNull("qty") ? 0 : rec.getInt("qty");
						double jumlah = rec.isNull("jumlah") ? 0 : rec.getDouble("jumlah");
						String estimasi = rec.isNull("estimasi") ? "" : rec.getString("estimasi");
						String kurir = rec.isNull("kurir") ? "" : rec.getString("kurir");
						String noresi = rec.isNull("noresi") ? "" : rec.getString("noresi");
						String gambar = rec.isNull("gambar") ? "" : rec.getString("gambar");
						int status = rec.isNull("status") ? 0 : rec.getInt("status");

						orderlist_selesai.add(new order(no_transaksi, tgl_transaksi, pembayaran, nama, qty, jumlah, estimasi, kurir, noresi, gambar, status, data.getId()));
					}

					success = true;
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			Intent i = new Intent("gomocart.application.com.gomocart.LOAD_DATA_ORDER");
			i.putExtra("success", success);
			i.putExtra("status", 4);
			sendBroadcast(i);

			return null;
		}
	}

	public void initialOrderlistBatal() {
		orderlist_batal = new ArrayList<>();
		orderAdapter_batal = new ListOrderAdapter(context, orderlist_batal);
		DaftarPesananBatalFragment.listViewOrder.setAdapter(orderAdapter_batal);
		new loadOrderlistBatal().execute();
	}

	public void loadOrderlistBatal() {
		orderlist_batal = new ArrayList<>();
		orderAdapter_batal.UpdateListOrderAdapter(orderlist_batal);
		new loadOrderlistBatal().execute();
	}

	public class loadOrderlistBatal extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			DaftarPesananBatalFragment.retry.setVisibility(View.GONE);
			DaftarPesananBatalFragment.listViewOrder.setVisibility(View.GONE);
		}

		@Override
		protected Void doInBackground(String... urls) {

			boolean success = false;


			String url = CommonUtilities.SERVER_URL + "/store/androidOrderDataStore.php";
			List<NameValuePair> params = new ArrayList<>();
			params.add(new BasicNameValuePair("order", dh.getStringOrderlist()));
			params.add(new BasicNameValuePair("id_cat", "5"));
			JSONObject json = new JSONParser().getJSONFromUrl(url, params, null);

			if (json != null) {
				try {
					JSONArray topics = json.isNull("topics") ? null : json.getJSONArray("topics");
					for (int i = 0; i < topics.length(); i++) {
						JSONObject rec = topics.getJSONObject(i);

						String no_transaksi = rec.isNull("no_transaksi") ? "" : rec.getString("no_transaksi");
						String tgl_transaksi = rec.isNull("tgl_transaksi") ? "" : rec.getString("tgl_transaksi");
						int pembayaran = rec.isNull("pembayaran") ? 0 : rec.getInt("pembayaran");
						String nama = rec.isNull("nama") ? "" : rec.getString("nama");
						int qty = rec.isNull("qty") ? 0 : rec.getInt("qty");
						double jumlah = rec.isNull("jumlah") ? 0 : rec.getDouble("jumlah");
						String estimasi = rec.isNull("estimasi") ? "" : rec.getString("estimasi");
						String kurir = rec.isNull("kurir") ? "" : rec.getString("kurir");
						String noresi = rec.isNull("noresi") ? "" : rec.getString("noresi");
						String gambar = rec.isNull("gambar") ? "" : rec.getString("gambar");
						int status = rec.isNull("status") ? 0 : rec.getInt("status");

						orderlist_batal.add(new order(no_transaksi, tgl_transaksi, pembayaran, nama, qty, jumlah, estimasi, kurir, noresi, gambar, status, data.getId()));
					}

					success = true;
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			Intent i = new Intent("gomocart.application.com.gomocart.LOAD_DATA_ORDER");
			i.putExtra("success", success);
			i.putExtra("status", 5);
			sendBroadcast(i);

			return null;
		}
	}
	
	public void loadDataOngkir() {
		new loadDataOngkir().execute();
	}

	public class loadDataOngkir extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			openDialogLoading();
			OngkosKirimFragment.retry.setVisibility(View.GONE);
			OngkosKirimFragment.linear_ongkir.setVisibility(View.GONE);
		}

		@Override
		protected Void doInBackground(String... urls) {

			ongkirlist = new ArrayList<>();
			String url = CommonUtilities.SERVER_URL + "/store/androidAllLayananDataStore.php";
			List<NameValuePair> params = new ArrayList<>();
			params.add(new BasicNameValuePair("city_id", city_id+""));
			params.add(new BasicNameValuePair("subdistrict_id", subdistrict_id+""));
			params.add(new BasicNameValuePair("berat", (berat_barang*1000)+""));
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
						ongkirlist.add(new ongkir(id_kurir, kode_kurir, nama_kurir, kode_service, nama_service, nominal, etd, tarif, gambar));
					}

					success = true;
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			Intent i = new Intent("gomocart.application.com.gomocart.LOAD_DATA_ONGKIR");
			i.putExtra("success", success);
			sendBroadcast(i);

			return null;
		}
	}

	public void loadKategoriProduk() {
		new loadKategoriProduk().execute();
	}

	public class loadKategoriProduk extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			KategoriFragment.gridViewKategori.setVisibility(View.GONE);
			KategoriFragment.retry.setVisibility(View.GONE);
		}

		@Override
		protected Void doInBackground(String... urls) {

			kategorilist = new ArrayList<>();

			String url = CommonUtilities.SERVER_URL + "/store/androidKategoriIndukDataStore.php";
			JSONObject json = new JSONParser().getJSONFromUrl(url, null, null);
			boolean success = false;
			if(json!=null) {
				try {
					JSONArray topics_induk = json.isNull("topics")?null:json.getJSONArray("topics");
					for (int i=0; i<topics_induk.length(); i++) {
						JSONObject rec_induk = topics_induk.getJSONObject(i);

						int induk_id = rec_induk.isNull("id")?null:rec_induk.getInt("id");
						String induk_nama = rec_induk.isNull("nama")?null:rec_induk.getString("nama");
						String induk_penjelasan = rec_induk.isNull("penjelasan")?null:rec_induk.getString("penjelasan");
						String induk_header = rec_induk.isNull("header")?null:rec_induk.getString("header");
						kategorilist.add(new kategori(induk_id, induk_nama, induk_penjelasan, induk_header));
					}

					success = true;
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			Intent i = new Intent("gomocart.application.com.gomocart.LOAD_KATEGORI_PRODUK");
			i.putExtra("success", success);
			sendBroadcast(i);

			return null;
		}
	}

	public void RefreshDataProduk() {
		if (show_produk_in.equalsIgnoreCase("grid")) {
			next_page_data_produk_grid = 1;
			produkgrid = new ArrayList<>();
		} else {
			next_page_data_produk_list = 1;
			produklist = new ArrayList<>();
		}
		loadDataProduk();
	}

	public void loadDataProduk() {
		new loadDataProduk().execute();
	}

	public class loadDataProduk extends AsyncTask<String, Void, ArrayList<produk>> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			ProdukFragment.retry.setVisibility(View.GONE);
		}

		@Override
		protected ArrayList<produk> doInBackground(String... urls) {

			ArrayList<produk> result = null;
			String url = CommonUtilities.SERVER_URL + "/store/androidProdukDataStore.php";

			List<NameValuePair> params = new ArrayList<>();
			int page = (first_load_produk || show_produk_in.equalsIgnoreCase("grid"))?next_page_data_produk_grid:next_page_data_produk_list;

			params.add(new BasicNameValuePair("id_user", data.getId()+""));
			params.add(new BasicNameValuePair("page", page+""));
			params.add(new BasicNameValuePair("query", is_search?search_keyword:""));
			params.add(new BasicNameValuePair("kategori", select_kategori!=null?(select_kategori.getId()+""):""));
			params.add(new BasicNameValuePair("filter_kategori", filter_kategori));
			params.add(new BasicNameValuePair("filter_brand", filter_brand));
			params.add(new BasicNameValuePair("filter_ukuran", filter_ukuran));
			params.add(new BasicNameValuePair("filter_harga_min", filter_harga_min));
			params.add(new BasicNameValuePair("filter_harga_max", filter_harga_max));
			params.add(new BasicNameValuePair("filter_diskon_min", filter_diskon_min));
			params.add(new BasicNameValuePair("filter_diskon_max", filter_diskon_max));
			params.add(new BasicNameValuePair("sort_by", sort_produk_by));

			JSONObject json = new JSONParser().getJSONFromUrl(url, params, null);

			if(json!=null) {
				try {
					result = new ArrayList<>();

					if(first_load_produk || show_produk_in.equalsIgnoreCase("grid")) {
						next_page_data_produk_grid = json.isNull("next_page") ? next_page_data_produk_grid : json.getInt("next_page");
					}

					if(first_load_produk || show_produk_in.equalsIgnoreCase("list")) {
						next_page_data_produk_list = json.isNull("next_page") ? next_page_data_produk_list : json.getInt("next_page");
					}

					JSONArray topics = json.isNull("topics")?null:json.getJSONArray("topics");
					for (int i=0; i<topics.length(); i++) {
						JSONObject rec = topics.getJSONObject(i);

						int id = rec.isNull("id")?0:rec.getInt("id");
						String kode = rec.isNull("kode")?"":rec.getString("kode");
						String nama = rec.isNull("nama")?"":rec.getString("nama");
						int id_category = rec.isNull("id_category")?0:rec.getInt("id_category");
						String category_name = rec.isNull("category_name")?"":rec.getString("category_name");
						String penjelasan = rec.isNull("penjelasan")?"":rec.getString("penjelasan");
						String foto1_produk = rec.isNull("foto1_produk")?"":rec.getString("foto1_produk");
						double harga_beli = rec.isNull("harga_beli")?0:rec.getDouble("harga_beli");
						double harga_jual = rec.isNull("harga_jual")?0:rec.getDouble("harga_jual");
						double harga_diskon = rec.isNull("harga_diskon")?0:rec.getDouble("harga_diskon");
						int persen_diskon = rec.isNull("persen_diskon")?0:rec.getInt("persen_diskon");
						int berat = rec.isNull("berat")?0:rec.getInt("berat");
						String list_ukuran = rec.isNull("list_ukuran")?"":rec.getString("list_ukuran");
						String ukuran = rec.isNull("ukuran")?"":rec.getString("ukuran");
						String list_warna = rec.isNull("list_warna")?"":rec.getString("list_warna");
						String warna = rec.isNull("warna")?"":rec.getString("warna");
						int qty = rec.isNull("qty")?0:rec.getInt("qty");
						int max_qty = rec.isNull("max_qty")?0:rec.getInt("max_qty");
						int minimum_pesan = rec.isNull("minimum_pesan")?1:rec.getInt("minimum_pesan");
						int produk_promo = rec.isNull("produk_promo")?0:rec.getInt("produk_promo");
						int produk_featured = rec.isNull("produk_featured")?0:rec.getInt("produk_featured");
						int produk_terbaru = rec.isNull("produk_terbaru")?0:rec.getInt("produk_terbaru");
						int produk_preorder = rec.isNull("produk_preorder")?0:rec.getInt("produk_preorder");
						int produk_soldout = rec.isNull("produk_soldout")?0:rec.getInt("produk_soldout");
						int produk_grosir = rec.isNull("produk_grosir")?0:rec.getInt("produk_grosir");
						int produk_freeongkir = rec.isNull("produk_freeongkir")?0:rec.getInt("produk_freeongkir");
						int rating = rec.isNull("rating")?0:rec.getInt("rating");
						int responden = rec.isNull("responden")?0:rec.getInt("responden");
						int review = rec.isNull("review")?0:rec.getInt("review");

						result.add(new produk(id, kode, nama, id_category, category_name, penjelasan, foto1_produk, harga_beli, harga_jual, harga_diskon, persen_diskon, berat, list_ukuran, ukuran, list_warna, warna, qty, max_qty, minimum_pesan, dh.getIdWishlist(id)>0, produk_promo, produk_featured, produk_terbaru, produk_preorder, produk_soldout, produk_grosir, produk_freeongkir, rating, responden, review));
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			return result;
		}

		@Override
		protected void onPostExecute(ArrayList<produk> result) {

			Boolean success = result!=null;
			if(result==null) result = new ArrayList<>();
			ArrayList<produk_list> temp = new ArrayList<>();
			temp.add(new produk_list(result));

			Intent i = new Intent("gomocart.application.com.gomocart.LOAD_DATA_PRODUK");
			i.putExtra("data_produk_list", temp);
			i.putExtra("success", success);
			sendBroadcast(i);
		}
	}

	public void lacak_pengiriman(String kurir, String no_resi) {
		Intent i = new Intent(context, DetailPengirimanActivity.class);
		i.putExtra("kurir", kurir);
		i.putExtra("no_resi", no_resi);
		startActivity(i);
	}

	public void updateWishlistGrid(int index, boolean is_value, produk data) {
		if(menu_selected==0) {
			updateTotalWishlist(data);
		} else {
			if(index<produkgrid.size()) {
				produkgrid.get(index).setIs_wishlist(is_value);
				//produkgridAdapter.UpdateGridProdukAdapter(produkgrid);
				updateTotalWishlist(produkgrid.get(index));
			}
		}
	}

	public void updateWishlistList(int index, boolean is_value) {
		if(index<produklist.size()) {
			produklist.get(index).setIs_wishlist(is_value);
			//produklistAdapter.UpdateListProdukAdapter(produklist);
			updateTotalWishlist(produklist.get(index));
		}
	}

	public void openDetailProduk(produk data) {
		Intent intent = new Intent(context, DetailProdukActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("produk", data);

		startActivityForResult(intent, RESULT_FROM_PRODUK_DETAIL);
	}

	public void cekOrder() {
		if(data.getId()==0) {
			Intent intent = new Intent(context, LoginActivity.class);
			intent.putExtra("menu_selected", menu_selected);
			intent.putExtra("from_checkout", true);
			startActivityForResult(intent, RESULT_FROM_SIGN_IN);
		} else {
			new prosesCekOrder().execute();
		}
	}

	public class prosesCekOrder extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			openDialogLoading();
		}

		@Override
		protected Void doInBackground(String... urls) {

			boolean success = false;
			double total_qty = 0;
			double total_jumlah = 0;

			cartlist = new ArrayList<>();
			ArrayList<produk> temp_cartlist = dh.getCartlist();
			alamat data_alamat = null;

			ArrayList<cekorder> cekorderlist = new ArrayList<>();
			String url = CommonUtilities.SERVER_URL + "/store/androidCekOrderDataStore.php";
			List<NameValuePair> params = new ArrayList<>();

			params.add(new BasicNameValuePair("id_user", data.getId()+""));
			params.add(new BasicNameValuePair("cart", dh.getStringCartlist()));
			JSONObject json = new JSONParser().getJSONFromUrl(url, params, null);

			if(json!=null) {
				try {

					JSONObject data_alamat_ = json.isNull("data_alamat")?null:json.getJSONObject("data_alamat");
					if(data_alamat_!=null) {

						int id = data_alamat_.isNull("id")?0:data_alamat_.getInt("id");
						String nama = data_alamat_.isNull("nama")?"":data_alamat_.getString("nama");
						String alamat = data_alamat_.isNull("alamat")?"":data_alamat_.getString("alamat");
						int province_id = data_alamat_.isNull("id_propinsi")?0:data_alamat_.getInt("id_propinsi");
						String province = data_alamat_.isNull("nama_propinsi")?"":data_alamat_.getString("nama_propinsi");
						int city_id = data_alamat_.isNull("id_kota")?0:data_alamat_.getInt("id_kota");
						String city = data_alamat_.isNull("nama_kota")?"":data_alamat_.getString("nama_kota");
						int subdistrict_id = data_alamat_.isNull("id_kecamatan")?0:data_alamat_.getInt("id_kecamatan");
						String subdistrict = data_alamat_.isNull("nama_kecamatan")?"":data_alamat_.getString("nama_kecamatan");
						String kode_pos = data_alamat_.isNull("kode_pos")?"":data_alamat_.getString("kode_pos");
						String no_hp = data_alamat_.isNull("no_hp")?"":data_alamat_.getString("no_hp");
						boolean as_default = data_alamat_.isNull("as_default")?false:(data_alamat_.getInt("as_default")==1);
						data_alamat = new alamat(id, nama, alamat, province_id, province, city_id, city, subdistrict_id, subdistrict, kode_pos, no_hp, as_default, false, "", "", "");
					}

					total_qty        = json.isNull("qty")?0:json.getDouble("qty");
					total_jumlah     = json.isNull("jumlah")?0:json.getDouble("jumlah");

					JSONArray topics = json.isNull("topics")?null:json.getJSONArray("topics");
					for (int i=0; i<topics.length(); i++) {
						JSONObject rec = topics.getJSONObject(i);

						int _id = rec.isNull("_id")?0:rec.getInt("_id");
						int id = rec.isNull("id")?0:rec.getInt("id");
						String ukuran = rec.isNull("ukuran")?"":rec.getString("ukuran");
						String warna = rec.isNull("warna")?"":rec.getString("warna");
						int qty = rec.isNull("qty")?0:rec.getInt("qty");
						String message = rec.isNull("message")?"":rec.getString("message");
						String status = rec.isNull("status")?"":rec.getString("status");

						int jumlah = rec.isNull("jumlah")?0:rec.getInt("jumlah");
						int berat = rec.isNull("berat")?0:rec.getInt("berat");
						double harga_beli = rec.isNull("harga_beli")?0:rec.getDouble("harga_beli");
						double harga_jual = rec.isNull("harga_jual")?0:rec.getDouble("harga_jual");
						//String tipe_diskon = rec.isNull("tipe_diskon")?null:rec.getString("tipe_diskon");
						double harga_diskon = rec.isNull("harga_diskon")?0:rec.getDouble("harga_diskon");
						int persen_diskon = rec.isNull("persen_diskon")?0:rec.getInt("persen_diskon");
						double subtotal = rec.isNull("subtotal")?0:rec.getDouble("subtotal");
						double grandtotal = rec.isNull("grandtotal")?0:rec.getDouble("grandtotal");

						for(produk data: temp_cartlist) {
							if(data.get_id()==_id) {
								data.setQty(jumlah);
								data.setBerat(berat);
								data.setHarga_beli(harga_beli);
								data.setHarga_jual(harga_jual);
								data.setHarga_diskon(harga_diskon);
								data.setPersen_diskon(persen_diskon);
								data.setSubtotal(subtotal);
								data.setGrandtotal(grandtotal);

								temp_cartlist.remove(data);
								cartlist.add(data);

								break;
							}
						}

						cekorderlist.add(new cekorder(_id, id, ukuran, warna, qty, message, status));
					}

					//GRANDTOTAL
					grandtotal gtotal = dh.getGrandtotal();
					gtotal.setTotal(total_jumlah);
					gtotal.setSub_total(total_jumlah-gtotal.getDiskon());
					gtotal.setGrand_total(gtotal.getSub_total()+gtotal.getPengiriman()-gtotal.getVoucher());
					dh.insertGrandtotal(gtotal);

					dh.deleteCartlist();
					dh.insertCartlist(cartlist);

					success = true;
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			ArrayList<cekorder_list> temp = new ArrayList<>();
			temp.add(new cekorder_list(cekorderlist));
			Intent i = new Intent("gomocart.application.com.gomocart.LOAD_DATA_CART");
			i.putExtra("success", success);
			i.putExtra("data_alamat", data_alamat);
			i.putExtra("cekorder_list", temp);
			i.putExtra("qty", total_qty);
			i.putExtra("jumlah", total_jumlah);
			sendBroadcast(i);


			return null;
		}
	}

	public void loadDataEditProfile() {

		EditProfileFragment.edit_first_nama.setText(data.getFirst_name());
		EditProfileFragment.edit_last_nama.setText(data.getLast_name());
		EditProfileFragment.edit_email.setText(data.getEmail());
		EditProfileFragment.edit_phone.setText(data.getPhone());
	}

	public void loadDataProfile() {

		mImageCaptureUri = null;
		ProfileFragment.name.setText(data.getFirst_name() + " " + data.getLast_name());
		imageLoader.displayImage(CommonUtilities.SERVER_URL+"/uploads/member/"+data.getPhoto(), ProfileFragment.image_profile, imageOptionsUser);
	}

	public void loadDataJenisUser() {
		JenisUserFragment.text_jenis_user.setText(data.getJenis_user());


	}

	public void loadDataSetting() {
		setting data_setting = CommonUtilities.getSettingApplikasi(context);
		SettingFragment.edit_notifikasi.setText(data_setting.getSet_notifikasi());
		SettingFragment.checkboxdefault_update_pesanan.setChecked(data_setting.getUpdate_pesanan());
		SettingFragment.checkboxdefault_informasi.setChecked(data_setting.getInformasi());
		SettingFragment.checkboxdefault_notifikasi.setChecked(data_setting.getNotifikasi());
		SettingFragment.checkboxdefault_chat.setChecked(data_setting.getChat());

		setting_notifikasi = data_setting.getSet_notifikasi();
	}

	public void simpanDataProfile() {
		new simpanDataProfile().execute();
	}

	public class simpanDataProfile extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			openDialogLoading();
			//progDailog.setMessage("Update...");
			//progDailog.show();
		}

		@Override
		protected Void doInBackground(String... urls) {

			boolean success = false;
			String message = "Tidak bisa kontak ke server.";
			String url = CommonUtilities.SERVER_URL + "/store/androidSaveProfile.php";

			JSONObject jObj = null;
			try {
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(url);

				MultipartEntity reqEntity = new MultipartEntity();
				reqEntity.addPart("id_user", new StringBody(data.getId()+""));
				reqEntity.addPart("first_name", new StringBody(EditProfileFragment.edit_first_nama.getText().toString()));
				reqEntity.addPart("last_name", new StringBody(EditProfileFragment.edit_last_nama.getText().toString()));
				reqEntity.addPart("email", new StringBody(EditProfileFragment.edit_email.getText().toString()));
				reqEntity.addPart("phone", new StringBody(EditProfileFragment.edit_phone.getText().toString()));
				/*reqEntity.addPart("dropship_name", new StringBody(EditProfileFragment.edit_dropship_name.getText().toString()));
				reqEntity.addPart("dropship_phone", new StringBody(EditProfileFragment.edit_dropship_phone.getText().toString()));

				reqEntity.addPart("ganti_password", new StringBody(EditProfileFragment.checkbox_ganti_password.isChecked()?"Y":""));
				reqEntity.addPart("password_lama", new StringBody(EditProfileFragment.edit_old_password.getText().toString()));
				reqEntity.addPart("password_baru", new StringBody(EditProfileFragment.edit_password.getText().toString()));
				reqEntity.addPart("password_konf", new StringBody(EditProfileFragment.edit_konfirmasi.getText().toString()));

				if (mImageCaptureUri != null) {
					File file = new File(mImageCaptureUri.getPath());
					if (file.exists()) {
						FileBody bin_gamber = new FileBody(file);
						reqEntity.addPart("photo", bin_gamber);
					}
				}*/
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

			if(jObj!=null) {
				try {

					success = jObj.isNull("success")?false:jObj.getBoolean("success");
					message = jObj.isNull("message")?message:jObj.getString("message");

					if(success) {

						data.setFirst_name(jObj.isNull("first_name")?"":jObj.getString("first_name"));
						data.setLast_name(jObj.isNull("last_name")?"":jObj.getString("last_name"));
						data.setEmail(jObj.isNull("email")?"":jObj.getString("email"));
						data.setPhone(jObj.isNull("phone")?"":jObj.getString("phone"));

						CommonUtilities.setSettingUser(context, data);

					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			Intent i = new Intent("gomocart.application.com.gomocart.EDIT_DATA_PROFILE");
			i.putExtra("success", success);
			i.putExtra("message", message);
			sendBroadcast(i);

			return null;
		}

	}


	public void simpanDataPassword() {
		new simpanDataPassword().execute();
	}

	public class simpanDataPassword extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			//progDailog.setMessage("Update...");
			//progDailog.show();
			openDialogLoading();
		}

		@Override
		protected Void doInBackground(String... urls) {

			boolean success = false;
			String message = "Tidak bisa kontak ke server.";
			String url = CommonUtilities.SERVER_URL + "/store/androidSaveProfile.php";

			JSONObject jObj = null;
			try {
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(url);

				MultipartEntity reqEntity = new MultipartEntity();
				reqEntity.addPart("id_user", new StringBody(data.getId()+""));
				reqEntity.addPart("password_lama", new StringBody(GantiPasswordFragment.edit_old_password.getText().toString()));
				reqEntity.addPart("password_baru", new StringBody(GantiPasswordFragment.edit_password.getText().toString()));
				reqEntity.addPart("password_konf", new StringBody(GantiPasswordFragment.edit_konfirmasi.getText().toString()));

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

			if(jObj!=null) {
				try {

					success = jObj.isNull("success")?false:jObj.getBoolean("success");
					message = jObj.isNull("message")?message:jObj.getString("message");

					if(success) {

						data.setFirst_name(jObj.isNull("first_name")?"":jObj.getString("first_name"));
						data.setLast_name(jObj.isNull("last_name")?"":jObj.getString("last_name"));
						data.setEmail(jObj.isNull("email")?"":jObj.getString("email"));
						data.setPhone(jObj.isNull("phone")?"":jObj.getString("phone"));

						CommonUtilities.setSettingUser(context, data);

					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			Intent i = new Intent("gomocart.application.com.gomocart.EDIT_DATA_PROFILE");
			i.putExtra("success", success);
			i.putExtra("message", message);
			sendBroadcast(i);

			return null;
		}

	}

	public void simpanDataPhoto() {
		new simpanDataPhoto().execute();
	}

	public class simpanDataPhoto extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			//progDailog.setMessage("Upload...");
			//progDailog.show();
			openDialogLoading();
		}

		@Override
		protected Void doInBackground(String... urls) {

			boolean success = false;
			String message = "Tidak bisa kontak ke server.";
			String url = CommonUtilities.SERVER_URL + "/store/androidSavePhotoProfile.php";

			JSONObject jObj = null;
			try {
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(url);

				MultipartEntity reqEntity = new MultipartEntity();
				reqEntity.addPart("id_user", new StringBody(data.getId()+""));

				if (mImageCaptureUri != null) {
					File file = new File(mImageCaptureUri.getPath());
					if (file.exists()) {
						FileBody bin_gamber = new FileBody(file);
						reqEntity.addPart("photo", bin_gamber);
					}
				}
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

			if(jObj!=null) {
				try {

					success = jObj.isNull("success")?false:jObj.getBoolean("success");
					message = jObj.isNull("message")?message:jObj.getString("message");

					if(success) {
						data.setPhoto(jObj.isNull("photo")?"":jObj.getString("photo"));
						CommonUtilities.setSettingUser(context, data);

					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			Intent i = new Intent("gomocart.application.com.gomocart.EDIT_DATA_PROFILE");
			i.putExtra("success", success);
			i.putExtra("message", message);
			sendBroadcast(i);

			return null;
		}

	}

	public void updateSetting() {

		setting data_setting = new setting(
			SettingFragment.edit_notifikasi.getText().toString(),
			SettingFragment.checkboxdefault_update_pesanan.isChecked(),
			SettingFragment.checkboxdefault_informasi.isChecked(),
			SettingFragment.checkboxdefault_notifikasi.isChecked(),
			SettingFragment.checkboxdefault_chat.isChecked()
		);

		CommonUtilities.setSettingAplikasi(context, data_setting);
		text_informasi.setText("Update setting berhasil.");
		text_title.setText("BERHASIL");
		dialog_informasi.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		dialog_informasi.show();

		displayView(11);
	}

	/*public class gantiPassword extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progDailog.setMessage("Update...");
			progDailog.show();
		}

		@Override
		protected Void doInBackground(String... urls) {

			boolean success = false;
			String message = "Tidak bisa kontak ke server.";

			String url = CommonUtilities.SERVER_URL + "/store/androidGantiPassword.php";
			List<NameValuePair> params = new ArrayList<>();

			params.add(new BasicNameValuePair("id_user", data.getId()+""));
			params.add(new BasicNameValuePair("password", SettingFragment.edit_password.getText().toString()));
			params.add(new BasicNameValuePair("konfirmasi",  SettingFragment.edit_konfirmasi.getText().toString()));

			JSONObject json = new JSONParser().getJSONFromUrl(url, params, null);
			if(json!=null) {
				try {

					success = json.isNull("success")?false:json.getBoolean("success");
					message = json.isNull("message")?message:json.getString("message");

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			Intent i = new Intent("gomocart.application.com.gomocart.EDIT_DATA_PROFILE");
			i.putExtra("success", success);
			i.putExtra("message", message);
			sendBroadcast(i);

			return null;
		}

	}*/


	public void emailPengembang() {
		new emailPengembang().execute();
	}

	public class emailPengembang extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			//progDailog.setMessage("Kirim...");
			//progDailog.show();
			openDialogLoading();
		}

		@Override
		protected Void doInBackground(String... urls) {

			boolean success = false;
			String message = "Tidak bisa kontak ke server.";

			String url = CommonUtilities.SERVER_URL + "/store/androidEmailPengembang.php";
			List<NameValuePair> params = new ArrayList<>();

			params.add(new BasicNameValuePair("id_user", data.getId()+""));
			params.add(new BasicNameValuePair("nama", HubungiPengembangFragment.edit_nama.getText().toString()));
			params.add(new BasicNameValuePair("email", HubungiPengembangFragment.edit_email.getText().toString()));
			params.add(new BasicNameValuePair("pesan", HubungiPengembangFragment.edit_pesan.getText().toString()));

			JSONObject json = new JSONParser().getJSONFromUrl(url, params, null);
			if(json!=null) {
				try {

					success = json.isNull("success")?false:json.getBoolean("success");
					message = json.isNull("message")?message:json.getString("message");

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			Intent i = new Intent("gomocart.application.com.gomocart.EDIT_DATA_PROFILE");
			i.putExtra("success", success);
			i.putExtra("message", message);
			sendBroadcast(i);

			return null;
		}

	}

	public void loadDataNotifikasi(boolean starting) {
		if(starting) {
			next_page_notifikasi = 1;
			list_notifikasi = new ArrayList<>();
			notifikasi_adapter = new NotifikasiAdapter(context, list_notifikasi);
			NotifikasiFragment.listview.setAdapter(notifikasi_adapter);
		}
		new loadDataNotifikasi().execute();
	}

	public class loadDataNotifikasi extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			NotifikasiFragment.retry.setVisibility(View.GONE);
		}

		@Override
		protected Void doInBackground(String... urls) {

			ArrayList<notifikasi> result = dh.getListNotifikasi(next_page_notifikasi);
			next_page_notifikasi += result.size() > 0 ? 1 : 0;

			ArrayList<notifikasi_list> temp = new ArrayList<>();
			temp.add(new notifikasi_list(result));
			Intent i = new Intent("gomocart.application.com.gomocart.LOAD_NOTIFIKASI");
			i.putExtra("notifikasi_list", temp);
			i.putExtra("success", true);
			sendBroadcast(i);

			return null;
		}
	}

	public void loadDataBank() {
		new loadDataBank().execute();
	}

	public class loadDataBank extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() { super.onPreExecute(); }

		@Override
		protected Void doInBackground(String... urls) {

			list_bank =  new ArrayList<>();
			String url = CommonUtilities.SERVER_URL + "/store/androidBankDataStore.php";
			JSONObject json = new JSONParser().getJSONFromUrl(url, null, null);

			if(json!=null) {
				try {

					JSONArray topics = json.isNull("topics")?null:json.getJSONArray("topics");
					for (int i=0; i<topics.length(); i++) {
						JSONObject rec = topics.getJSONObject(i);

						int id = rec.isNull("id") ? null : rec.getInt("id");
						String no_rekening = rec.isNull("no_rekening") ? null : rec.getString("no_rekening");
						String nama_pemilik_rekening = rec.isNull("nama_pemilik_rekening") ? null : rec.getString("nama_pemilik_rekening");
						String nama_bank = rec.isNull("nama_bank") ? "" : rec.getString("nama_bank");
						String cabang = rec.isNull("cabang") ? null : rec.getString("cabang");
						String gambar = rec.isNull("gambar") ? null : rec.getString("gambar");

						list_bank.add(new bank(id, no_rekening, nama_pemilik_rekening, nama_bank, cabang, gambar));
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			Intent i = new Intent("gomocart.application.com.gomocart.LOAD_DATA_BANK");
			sendBroadcast(i);

			return null;
		}
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


	public void openFilterProduk() {
		Intent intent = new Intent(context, FilterProdukActivity.class);
		startActivityForResult(intent, REQUEST_FROM_FILTER);
	}

	public void loadDialogListView(String act) {
		action = act;
		if((action.equalsIgnoreCase("province") || action.equalsIgnoreCase("profile_province")) && listProvince.size()==0) {
			openDialogLoadingEkspedisi();
		} else if((action.equalsIgnoreCase("city") || action.equalsIgnoreCase("profile_city")) && listCity.size()==0) {
			openDialogLoadingEkspedisi();
		} else if((action.equalsIgnoreCase("subdistrict") || action.equalsIgnoreCase("profile_subdistrict")) && listSubDistrict.size()==0) {
			openDialogLoadingEkspedisi();
		} else {
			loadListArray();
			dialog_listview.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			dialog_listview.show();
		}
	}

	private void loadListArray() {
		String[] from = new String[] { getResources().getString(R.string.list_dialog_title) };
		int[] to = new int[] { R.id.txt_title };

		List<HashMap<String, String>> fillMaps = new ArrayList<>();
		if(action.equalsIgnoreCase("province") || action.equalsIgnoreCase("profile_province")) {
			for (province data : listProvince) {
				HashMap<String, String> map = new HashMap<>();
				map.put(getResources().getString(R.string.list_dialog_title), data.getProvince());

				fillMaps.add(map);
			}
		} else if(action.equalsIgnoreCase("city") || action.equalsIgnoreCase("profile_city")) {
			for (city data : listCity) {
				HashMap<String, String> map = new HashMap<>();
				map.put(getResources().getString(R.string.list_dialog_title), data.getCity());

				fillMaps.add(map);
			}
		} else if(action.equalsIgnoreCase("subdistrict") || action.equalsIgnoreCase("profile_subdistrict")) {
			for (subdistrict data : listSubDistrict) {
				HashMap<String, String> map = new HashMap<>();
				map.put(getResources().getString(R.string.list_dialog_title), data.getSubdistrict());

				fillMaps.add(map);
			}
		}

		SimpleAdapter adapter = new SimpleAdapter(context, fillMaps, R.layout.item_list_dialog, from, to);
		listview.setAdapter(adapter);
	}
	public void loadDataProvince() {
		listCity = new ArrayList<>();
		listSubDistrict = new ArrayList<>();
		if(listProvince.size()==0) {
			new loadProvince().execute();
		}
	}

	public class loadProvince extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(String... urls) {
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

	private void setSignIn() {
		if(data.getId()>0) {
			imageLoader.displayImage(CommonUtilities.SERVER_URL+"/uploads/member/"+data.getPhoto(), avatar, imageOptionsUser);
		} else {
			avatar.setImageResource(R.drawable.userdefault);
		}

		name_avatar.setText(data.getFirst_name()+" "+data.getLast_name());
		//lin_setting.setVisibility(data.getId()==0?View.INVISIBLE:View.VISIBLE);

		nav_login.setText(data.getId()==0?"Masuk":"Keluar");
		image_menu_login.setImageResource(data.getId()==0?R.drawable.menu_login:R.drawable.menu_logout);

		nav_register.setText(data.getId()==0?"Daftar":"Profil");
		//image_menu_profil.setImageResource(data.getId()==0?R.drawable.menu_login:R.drawable.menu_logout);

		//view_alamat.setVisibility(data.getId()==0?View.GONE:View.VISIBLE);
		//lin_alamat.setVisibility(data.getId()==0?View.GONE:View.VISIBLE);
		displayView(menu_selected);
	}

	private void insertDummyContactWrapper() {
		List<String> permissionsNeeded = new ArrayList<>();
		final List<String> permissionsList = new ArrayList<>();

		if (!addPermission(permissionsList, android.Manifest.permission.INTERNET))
			permissionsNeeded.add("INTERNET");
		if (!addPermission(permissionsList, android.Manifest.permission.ACCESS_NETWORK_STATE))
			permissionsNeeded.add("ACCESS_NETWORK_STATE");
		if (!addPermission(permissionsList, android.Manifest.permission.WRITE_EXTERNAL_STORAGE))
			permissionsNeeded.add("WRITE_EXTERNAL_STORAGE");
		if (!addPermission(permissionsList, android.Manifest.permission.READ_EXTERNAL_STORAGE))
			permissionsNeeded.add("READ_EXTERNAL_STORAGE");
		if (!addPermission(permissionsList, android.Manifest.permission.CAMERA))
			permissionsNeeded.add("CAMERA");
		//if (!addPermission(permissionsList, android.Manifest.permission.FLASHLIGHT))
			////permissionsNeeded.add("FLASHLIGHT");

		if (permissionsList.size() > 0) {
			if (permissionsNeeded.size() > 0) {
				// Need Rationale
				String message = "You need to grant access to " + permissionsNeeded.get(0);
				for (int i = 1; i < permissionsNeeded.size(); i++)
					message = message + ", " + permissionsNeeded.get(i);

				//showMessageOKCancel(message, new DialogInterface.OnClickListener() {
				//@Override
				//public void onClick(DialogInterface dialog, int which) {*/
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
					requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
				}
				//}
				//});
				return;
			}
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
				requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
			}
			return;
		}
	}

	private boolean addPermission(List<String> permissionsList, String permission) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
				permissionsList.add(permission);
				// Check for Rationale Option
				if (!shouldShowRequestPermissionRationale(permission))
					return false;
			}
		}
		return true;
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		switch (requestCode) {
			case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS:
			{
				Map<String, Integer> perms = new HashMap<String, Integer>();
				// Initial
				perms.put(android.Manifest.permission.INTERNET, PackageManager.PERMISSION_GRANTED);
				perms.put(android.Manifest.permission.ACCESS_NETWORK_STATE, PackageManager.PERMISSION_GRANTED);
				perms.put(android.Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
				perms.put(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
				perms.put(android.Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
				//perms.put(android.Manifest.permission.FLASHLIGHT, PackageManager.PERMISSION_GRANTED);

				// Fill with results
				for (int i = 0; i < permissions.length; i++)
					perms.put(permissions[i], grantResults[i]);
				// Check for ACCESS_FINE_LOCATION
				if (perms.get(android.Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED
						&& perms.get(android.Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED
						&& perms.get(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
						&& perms.get(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
						&& perms.get(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
						//&& perms.get(android.Manifest.permission.FLASHLIGHT) == PackageManager.PERMISSION_GRANTED
					// All Permissions Granted
				} else {
					// Permission Denied
					Toast.makeText(context, "Some Permission is Denied", Toast.LENGTH_SHORT).show();
				}
			}
			break;
			default:
				super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		}
	}

	class prosesUpdateRegisterRegId extends AsyncTask<String, Void, JSONObject> {

		String registrationId;
		boolean success;
		String message;

		prosesUpdateRegisterRegId(String registrationId) {
			this.registrationId = registrationId;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected JSONObject doInBackground(String... urls) {
			return ServerUtilities.register(context, registrationId, data.getId(), CommonUtilities.getGuestId(context));
		}

		@Deprecated
		@Override
		protected void onPostExecute(JSONObject result) {

			success = false;
			message = "Gagal melakukan proses take action. Cobalah lagi.";
			if(result!=null) {
				try {
					success = result.isNull("success")?false:result.getBoolean("success");
					message = result.isNull("message")?message:result.getString("message");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(!success) {
				new prosesUpdateRegisterRegId(registrationId).execute();
			}
		}
	}

	private void checkGcmRegid() {
		String registrationId = getString(R.string.msg_token_fmt, FirebaseInstanceId.getInstance().getToken());
		registrationId = registrationId.equalsIgnoreCase("null") ? "" : registrationId;
		Log.d("Registration id", registrationId);
		//Toast.makeText(context, registrationId, Toast.LENGTH_SHORT).show();
		if (registrationId.length() > 0) {
			new prosesUpdateRegisterRegId(registrationId).execute();
		}
	}

	public void openDetailInformasi(informasi data) {
		Intent intent = new Intent(context, DetailInformasiActivity.class);
		intent.putExtra("informasi", data);
		startActivity(intent);
	}

	public void openDetailNotifikasi(notifikasi data) {
		//Intent intent = new Intent(context, DetailNotifikasiActivity.class);
		//intent.putExtra("notifikasi", data);
		//startActivity(intent);
	}

	public void openMessageActivity(perpesanan data) {
		//data_perpesanan = data;
		//Toast.makeText(context, data.getId_produk()+"", Toast.LENGTH_SHORT).show();
		Intent i = new Intent(context, MessageActivity.class);
		i.putExtra("id_produk", data.getId_produk());
		startActivityForResult(i, RESULT_FROM_KIRIM_PESAN);
	}


	public void loadDataPerpesanan(boolean starting) {
		if(starting) {
			next_page_perpesanan = 1;
			perpesananlist = new ArrayList<>();
			perpesananAdapter = new PerpesananAdapter(context, perpesananlist);
			PerpesananFragment.listview.setAdapter(perpesananAdapter);
		}
		new loadDataPerpesanan().execute();
	}

	public class loadDataPerpesanan extends AsyncTask<String, Void, ArrayList<perpesanan>> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			PerpesananFragment.retry.setVisibility(View.GONE);
		}

		@Override
		protected ArrayList<perpesanan> doInBackground(String... urls) {
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

			ArrayList<perpesanan> result = null;
			if(security_code.length()>0) {
				String url = CommonUtilities.SERVER_URL + "/store/androidLaporanDataStore.php";

				List<NameValuePair> params = new ArrayList<>();
				params.add(new BasicNameValuePair("page", next_page_perpesanan+""));
				params.add(new BasicNameValuePair("user_id", data.getId() + ""));
				params.add(new BasicNameValuePair("guest_id", CommonUtilities.getGuestId(context) + ""));
				params.add(new BasicNameValuePair("security_code", security_code));
				JSONObject json = new JSONParser().getJSONFromUrl(url, params, cookies);

				if(json!=null) {
					try {
						result = new ArrayList<>();

						next_page_perpesanan = json.isNull("next_page")?next_page_perpesanan:json.getInt("next_page");
						JSONArray topics = json.isNull("topics")?null:json.getJSONArray("topics");
						for (int i=0; i<topics.length(); i++) {
							JSONObject rec = topics.getJSONObject(i);

							int id            = rec.isNull("id")?0:rec.getInt("id");
							int id_produk     = rec.isNull("id_produk")?0:rec.getInt("id_produk");
							String kode       = rec.isNull("kode")?"":rec.getString("kode");
							String nama       = rec.isNull("nama")?"":rec.getString("nama");
							String gambar     = rec.isNull("gambar")?"":rec.getString("gambar");
							String pesan      = rec.isNull("pesan")?"":rec.getString("pesan");
							String tanggal    = rec.isNull("tanggal_jam")?"":rec.getString("tanggal_jam");
							int from_id       = rec.isNull("from_id")?0:rec.getInt("from_id");
							String from_nama  = rec.isNull("from_nama")?"":rec.getString("from_nama");
							String from_photo = rec.isNull("from_photo")?"":rec.getString("from_photo");
							int total_unread  = rec.isNull("total_unread")?0:rec.getInt("total_unread");

							result.add(new perpesanan(id, id_produk, kode, nama, gambar, tanggal, pesan, from_id, from_nama, from_photo, total_unread));
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			return result;
		}

		@Override
		protected void onPostExecute(ArrayList<perpesanan> result) {

			Boolean success = result!=null;
			if(result==null) result = new ArrayList<>();
			ArrayList<perpesanan_list> temp = new ArrayList<>();
			temp.add(new perpesanan_list(result));

			Intent i = new Intent("gomocart.application.com.gomocart.LOAD_PERPESANAN_LIST");
			i.putExtra("perpesanan_list", temp);
			i.putExtra("success", success);
			sendBroadcast(i);
		}
	}

	public Runnable mUpdateTimeTask = new Runnable() {
		public void run() {
			mHandlerClose.removeCallbacks(this);
			current_click = 0;
		}
	};

	public Runnable mDisplayViewTask = new Runnable() {
		public void run() {
			mHandlerDisplayView.removeCallbacks(this);
			//displayViewOpen(menu_selected);
		}
	};

	void openSoftKeyboard() {
		View view = getCurrentFocus();
		if (view != null) {
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
		}
	}

	void closeSoftKeyboard() {
		View view = getCurrentFocus();
		if (view != null) {
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}

}