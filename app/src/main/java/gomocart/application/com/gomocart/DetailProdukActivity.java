package gomocart.application.com.gomocart;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.firebase.iid.FirebaseInstanceId;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import customfonts.MyEditText;
import customfonts.MyTextView;
import gomocart.application.com.adapter.ListGrosirAdapter;
import gomocart.application.com.adapter.ListOngkirAdapter;
import gomocart.application.com.adapter.ListStokAdapter;
import gomocart.application.com.adapter.RecyclerViewOngkirTujuanAdapter;
import gomocart.application.com.adapter.RecyclerViewProdukAdapter;
import gomocart.application.com.libs.*;
import gomocart.application.com.libs.SmallBang;
import gomocart.application.com.model.city;
import gomocart.application.com.model.gallery;
import gomocart.application.com.model.gallery_list;
import gomocart.application.com.model.grosir;
import gomocart.application.com.model.ongkir;
import gomocart.application.com.model.ongkir_tujuan;
import gomocart.application.com.model.produk;
import gomocart.application.com.model.province;
import gomocart.application.com.model.stok;
import gomocart.application.com.model.subdistrict;
import gomocart.application.com.model.user;

public class DetailProdukActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener {

    Context context;
    user data;

    produk select_produk;
    SliderLayout mProdukSlider;

    MyTextView nama_produk;
    MyTextView harga_produk;
    MyTextView cutprice_produk;

    ImageView fav1;
    ImageView fav2;
    SmallBang mSmallBang;

    LinearLayout linear_add_to_cart;
    ImageView plus;
    MyTextView cartno;
    ImageView minus;
    MyTextView stok_jumlah;
    View separator_ukuran_warna;
    LinearLayout linear_ukuran_warna;
    MyEditText ukuran;
    View separator_warna;
    MyEditText warna;

    RecyclerView rv_ongkir;
    public static ArrayList<ongkir_tujuan> ongkir_tujuan_list = new ArrayList<>();
    public static RecyclerViewOngkirTujuanAdapter ongkirtujuanbaseAdapter;
    ImageView btn_back;

    DatabaseHandler dh;
    int total_cart;
    int total_wishlist;

    MyTextView add_to_cart;

    MyTextView spesifikasi_minimum_pesan;
    MyTextView spesifikasi_stok;
    MyTextView spesifikasi_kategori;
    MyTextView spesifikasi_penjelasan;

    //LIST PRODUK TERKAIT
    RecyclerView rv_produk_terkait;
    ArrayList<produk> listProdukTerkait = new ArrayList<>();
    RecyclerViewProdukAdapter produkTerkaitAdapter;

    //LIST UKURAN DAN WARNA
    ArrayList<String> list_ukuran = new ArrayList<>();
    ArrayList<String> list_warna  = new ArrayList<>();

    Dialog dialog_ukuran_warna;
    ListView listview_ukuran_warna;
    String action = "";
    int max_qty = 1;

    Dialog dialog_loading;
    //FrameLayout frame_loading;

    Dialog dialog_informasi;
    MyTextView btn_ok;
    MyTextView text_title;
    MyTextView text_informasi;

    public static ArrayList<String> list_gambar = new ArrayList<>();

    LinearLayout linear_share;

    LinearLayout linearStok;
    LinearLayout linear_spesifikasi_stok;
    View view_spesifikasi_stok;
    ExpandableHeightListView listviewStok;
    ArrayList<stok> liststok = new ArrayList<>();
    ListStokAdapter stokAdapter;

    LinearLayout linearDetailGrosir;
    ExpandableHeightListView listviewGrosir;
    ArrayList<grosir> listgrosir = new ArrayList<>();
    ListGrosirAdapter grosirAdapter;

    MyTextView discount;

    LinearLayout linearSoldout;
    LinearLayout linearNew;
    LinearLayout linearGrosir;
    LinearLayout linearPreorder;
    LinearLayout linearFreeongkir;

    float downX = 0, downY = 0, upX, upY;
    MyTextView cek_ongkir;
    MyTextView kirim_pesan;

    RelativeLayout main_screen;
    LinearLayout linear_1, linear_2, linear_button;

    ImageView toolbar_cart;
    MyTextView number_cart;

    ImageView toolbar_wish;
    MyTextView number_wish;

    MyEditText edit_province;
    MyEditText edit_city;
    MyEditText edit_state;
    MyTextView edit_berat;
    ImageView btn_berat_plus;
    ImageView btn_berat_minus;
    
    Dialog dialog_listview;
    ListView listview;

    int province_id = 0;
    int city_id = 0;
    int subdistrict_id = 0;

    String province_nama = "";
    String city_nama = "";
    String subdistrict_nama = "";
    int berat_barang;

    ArrayList<province> listProvince = new ArrayList<>();
    ArrayList<city> listCity = new ArrayList<>();
    ArrayList<subdistrict> listSubDistrict = new ArrayList<>();

    // ONGKIR LIST
    public static ArrayList<ongkir> ongkirlist = new ArrayList<>();
    public static ListOngkirAdapter ongkirAdapter;

    LinearLayout linearOnkgkir;
    ListView listViewOngkir;

    ProgressBar loading;
    LinearLayout retry;
    MyTextView btnReload;

    Boolean is_visible_cek_ongkir = false;
    ImageView cek_ongkir_minimize;
    LinearLayout cek_ongkir_detail, linear_cek_ongkir;

    final int RESULT_FROM_KIRIM_PESAN = 1;
    final int RESULT_FROM_SIGN_IN = 2;

    Boolean is_drawn = false;

    ArrayList<gallery> gallerylist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail_produk);
        context = DetailProdukActivity.this;
        data = CommonUtilities.getSettingUser(context);
        dh = new DatabaseHandler(context);

        mProdukSlider = (SliderLayout) findViewById(R.id.slider);
        nama_produk = (MyTextView) findViewById(R.id.nama_produk);
        harga_produk = (MyTextView) findViewById(R.id.harga_produk);
        cutprice_produk = (MyTextView) findViewById(R.id.cutprice);

        spesifikasi_minimum_pesan = (MyTextView) findViewById(R.id.spesifikasi_minimum_pesan);
        spesifikasi_stok          = (MyTextView) findViewById(R.id.spesifikasi_stok);
        spesifikasi_kategori      = (MyTextView) findViewById(R.id.spesifikasi_kategori);
        spesifikasi_penjelasan    = (MyTextView) findViewById(R.id.detail_produk);

        edit_province = (MyEditText) findViewById(R.id.edit_provice);
        edit_city = (MyEditText) findViewById(R.id.edit_city);
        edit_state = (MyEditText) findViewById(R.id.edit_kecamatan);
        edit_berat = (MyTextView) findViewById(R.id.berat_no);
        btn_berat_plus = (ImageView) findViewById(R.id.berat_plus);
        btn_berat_minus = (ImageView) findViewById(R.id.berat_minus);

        btn_berat_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(edit_berat.getText().toString());
                number++;
                edit_berat.setText(String.valueOf(number));
                berat_barang = number;
            }
        });

        btn_berat_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(edit_berat.getText().toString());
                if(number>1) {
                    number--;
                    edit_berat.setText(String.valueOf(number));
                    berat_barang = number;
                }
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
                            loadDialogListView("province");
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
                                openDialogMessage("Propinsi tujuan harus diisi!", false);
                            } else {
                                loadDialogListView("city");
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
                                openDialogMessage("Kabupaten / kota tujuan harus diisi!", false);
                            } else {
                                loadDialogListView("subdistrict");
                            }
                        }

                        break;
                }

                return false;
            }
        });

        berat_barang = 1;
        edit_berat.setText(berat_barang + "");

        rv_ongkir = (RecyclerView) findViewById(R.id.rv_ongkir);
        rv_ongkir.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

        ongkir_tujuan_list.add(new ongkir_tujuan("", false));
        ongkir_tujuan_list.add(new ongkir_tujuan("", false));
        ongkir_tujuan_list.add(new ongkir_tujuan("", false));
        ongkirtujuanbaseAdapter = new RecyclerViewOngkirTujuanAdapter(context, ongkir_tujuan_list);
        rv_ongkir.setAdapter(ongkirtujuanbaseAdapter);

        cek_ongkir = (MyTextView) findViewById(R.id.cek_ongkir);
        cek_ongkir.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if(edit_province.getText().toString().length()==0) {
                    openDialogMessage("Propinsi tujuan harus diisi!", false);
                    return;
                }

                if(edit_city.getText().toString().length()==0) {
                    openDialogMessage("Kabupaten / kota tujuan harus diisi!", false);
                    return;
                }

                if(edit_state.getText().toString().length()==0) {
                    openDialogMessage("Kecamatan tujuan harus diisi!", false);
                    return;
                }

                loadDataOngkir(true);

            }
        });

        ongkirlist = new ArrayList<>();
        ongkirAdapter = new ListOngkirAdapter(context, ongkirlist);

        linear_share = (LinearLayout) findViewById(R.id.toolbar_layout_share);
        linearOnkgkir = (LinearLayout) findViewById(R.id.linearOnkgkir);
        listViewOngkir = (ListView) findViewById(R.id.lisview);
        loading = (ProgressBar) findViewById(R.id.pgbarLoading);
        retry = (LinearLayout) findViewById(R.id.loadMask);
        btnReload = (MyTextView) findViewById(R.id.btnReload);

        linearOnkgkir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOngkir();
            }
        });

        linear_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String shareBody = CommonUtilities.SERVER_URL + "/produk/detail.php?id="+select_produk.getId();
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.app_name)));
            }
        });

        btnReload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                loadDataOngkir(true);
            }
        });

        linear_cek_ongkir   = (LinearLayout) findViewById(R.id.linear_cek_ongkir);
        cek_ongkir_minimize = (ImageView) findViewById(R.id.cek_ongkir_minimize);
        cek_ongkir_detail   = (LinearLayout) findViewById(R.id.cek_ongkir_detail);

        linear_cek_ongkir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openOngkir();
            }
        });

        listViewOngkir.setAdapter(ongkirAdapter);
        loading.setVisibility(View.INVISIBLE);
        
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
                    if(action.equalsIgnoreCase("province")) {
                        edit_province.setText(listProvince.get(position).getProvince());
                        edit_city.setText("");
                        edit_state.setText("");

                        ongkir_tujuan_list.set(0, new ongkir_tujuan(listProvince.get(position).getProvince(), false));
                        ongkir_tujuan_list.set(1, new ongkir_tujuan("", false));
                        ongkir_tujuan_list.set(2, new ongkir_tujuan("", false));

                        ongkirtujuanbaseAdapter.UpdateData(ongkir_tujuan_list);
                    }

                    province_id = listProvince.get(position).getProvince_id();
                    city_id = 0;
                    subdistrict_id = 0;

                    new loadCity().execute();
                    new loadSubdistrict().execute();
                    action = "";
                } else if(action.equalsIgnoreCase("city")) {
                    if(action.equalsIgnoreCase("city")) {
                        edit_city.setText(listCity.get(position).getCity());
                        edit_state.setText("");

                        String propinsi_name = ongkir_tujuan_list.get(0).getNama();
                        ongkir_tujuan_list.set(0, new ongkir_tujuan(propinsi_name, true));
                        ongkir_tujuan_list.set(1, new ongkir_tujuan(listCity.get(position).getCity(), false));
                        ongkir_tujuan_list.set(2, new ongkir_tujuan("", false));

                        ongkirtujuanbaseAdapter.UpdateData(ongkir_tujuan_list);
                    }

                    city_id = listCity.get(position).getCity_id();
                    subdistrict_id = 0;

                    new loadSubdistrict().execute();
                    action = "";
                } else if(action.equalsIgnoreCase("subdistrict")) {
                    if(action.equalsIgnoreCase("subdistrict")) {
                        edit_state.setText(listSubDistrict.get(position).getSubdistrict());

                        String propinsi_name = ongkir_tujuan_list.get(0).getNama();
                        String city_name = ongkir_tujuan_list.get(1).getNama();

                        ongkir_tujuan_list.set(0, new ongkir_tujuan(propinsi_name, true));
                        ongkir_tujuan_list.set(1, new ongkir_tujuan(city_name, true));
                        ongkir_tujuan_list.set(2, new ongkir_tujuan(listSubDistrict.get(position).getSubdistrict(), false));
                        ongkirtujuanbaseAdapter.UpdateData(ongkir_tujuan_list);
                    }

                    subdistrict_id = listSubDistrict.get(position).getSubdistrict_id();
                    action = "";
                }
            }
        });
        
        btn_back       = (ImageView) findViewById(R.id.btn_back);
        add_to_cart    = (MyTextView) findViewById(R.id.add_to_cart);

        linear_add_to_cart = (LinearLayout) findViewById(R.id.linear2);
        plus = (ImageView)findViewById(R.id.plus);
        cartno = (MyTextView) findViewById(R.id.cartno);
        minus = (ImageView)findViewById(R.id.minus);
        stok_jumlah = (MyTextView) findViewById(R.id.stok_jumlah);
        separator_ukuran_warna = (View) findViewById(R.id.separator_ukuran_warna);
        linear_ukuran_warna = (LinearLayout) findViewById(R.id.linear_ukuran_warna);
        ukuran = (MyEditText) findViewById(R.id.ukuran);
        separator_warna = (View) findViewById(R.id.separator_warna);
        warna = (MyEditText) findViewById(R.id.warna);
        
        fav1 = (ImageView) findViewById(R.id.fav1);
        fav2 = (ImageView) findViewById(R.id.fav2);

        linearSoldout  = (LinearLayout) findViewById(R.id.linear_soldout);
        linearNew      = (LinearLayout) findViewById(R.id.linear_new);
        linearGrosir   = (LinearLayout) findViewById(R.id.linear_grosir);
        linearPreorder = (LinearLayout) findViewById(R.id.linear_preorder);
        linearFreeongkir = (LinearLayout) findViewById(R.id.linear_freeongkir);
        discount       = (MyTextView) findViewById(R.id.discount);

        linearStok = (LinearLayout) findViewById(R.id.linear7);
        linear_spesifikasi_stok  = (LinearLayout) findViewById(R.id.linear_spesifikasi_stok);
        view_spesifikasi_stok  = (View) findViewById(R.id.view_spesifikasi_stok);

        listviewStok = (ExpandableHeightListView) findViewById(R.id.listviewStok);

        linearDetailGrosir = (LinearLayout) findViewById(R.id.linear8);
        listviewGrosir = (ExpandableHeightListView) findViewById(R.id.listviewGrosir);

        mSmallBang = SmallBang.attach2Window(this);
        fav1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dh.insertWishlist(select_produk);
                select_produk.setIs_wishlist(true);

                fav2.setVisibility(View.VISIBLE);
                fav1.setVisibility(View.GONE);
                like(v);
                updateTotalWishlist();
            }

            public void like(View view){
                fav2.setImageResource(R.drawable.f4);
                mSmallBang.bang(view);
                mSmallBang.setmListener(new gomocart.application.com.libs.SmallBangListener() {
                    @Override
                    public void onAnimationStart() {

                    }

                    @Override
                    public void onAnimationEnd() {

                    }
                });
            }
        });

        fav2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dh.deleteWishlist(select_produk);
                select_produk.setIs_wishlist(false);

                fav2.setVisibility(View.GONE);
                fav1.setVisibility(View.VISIBLE);

                updateTotalWishlist();
            }
        });

        ukuran.setOnTouchListener(new View.OnTouchListener() {

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
                            dialog_ukuran_warna.show();

                            list_ukuran = new ArrayList<>();
                            boolean add_ukuran;
                            for(stok data_: liststok) {
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

                            loadListArray(list_ukuran);
                            action = "ukuran";
                        }

                        break;
                }

                return false;
            }
        });

        warna.setOnTouchListener(new View.OnTouchListener() {

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
                            dialog_ukuran_warna.show();

                            list_warna = new ArrayList<>();
                            boolean add_warna;
                            for(stok data_: liststok) {
                                if(data_.getUkuran().equalsIgnoreCase(ukuran.getText().toString())) {
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

                            loadListArray(list_warna);
                            action = "warna";
                        }

                        break;
                }

                return false;
            }
        });
        


        toolbar_cart  = (ImageView) findViewById(R.id.toolbar_image_cart);
        number_cart   = (MyTextView) findViewById(R.id.number_cart);
        updateTotalCartlist();

        toolbar_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("produk", select_produk);
                intent.putExtra("goto", "cart_list");
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        number_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("produk", select_produk);
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
                intent.putExtra("produk", select_produk);
                intent.putExtra("goto", "wish_list");
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        number_wish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("produk", select_produk);
                intent.putExtra("goto", "wish_list");
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("produk", select_produk);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(select_produk.getProduk_soldout()==1 || max_qty==0) {
                    text_informasi.setText("Maaf Stok Kosong.");
                    text_title.setText("GAGAL");
                    dialog_informasi.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog_informasi.show();

                    return;
                }

                if(list_ukuran.size()>0 && ukuran.getText().toString().length()==0) {
                    text_informasi.setText("Ukuran harus diisi terlebih dahulu.");
                    text_title.setText("KESALAHAN");
                    dialog_informasi.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog_informasi.show();

                    return;
                }

                if(list_warna.size()>0 && warna.getText().toString().length()==0) {
                    text_informasi.setText("Warna harus diisi terlebih dahulu.");
                    text_title.setText("KESALAHAN");
                    dialog_informasi.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog_informasi.show();

                    return;
                }

                select_produk.setList_ukuran(getList_ukuran());
                select_produk.setUkuran(list_ukuran.size()==0?"":ukuran.getText().toString());
                select_produk.setList_warna(getList_warna());
                select_produk.setWarna(list_warna.size()==0?"":warna.getText().toString());
                select_produk.setQty(Integer.parseInt(cartno.getText().toString()));
                select_produk.setMax_qty(max_qty);

                dh.insertCartlist(select_produk);
                Toast.makeText(context, "Tambah keranjang belanja berhasil.", Toast.LENGTH_LONG).show();
                ukuran.setText("");
                warna.setText("");
                cartno.setText("1");

                updateTotalCartlist();
                ukuran.clearFocus();
                warna.clearFocus();
            }
        });

        if(savedInstanceState==null) {
            select_produk = (produk) getIntent().getSerializableExtra("produk");

            nama_produk.setText(select_produk.getNama());
            harga_produk.setText(CommonUtilities.getCurrencyFormat(select_produk.getHarga_diskon()>0?select_produk.getHarga_diskon():select_produk.getHarga_jual(), "Rp. "));
            discount.setText("("+select_produk.getPersen_diskon()+"%)");
            cutprice_produk.setText(select_produk.getHarga_diskon()>0?CommonUtilities.getCurrencyFormat(select_produk.getHarga_jual(), "Rp. "):"");

            discount.setVisibility(select_produk.getPersen_diskon()>0?View.VISIBLE:View.GONE);
            cutprice_produk.setVisibility(select_produk.getHarga_diskon()>0?View.VISIBLE:View.GONE);

            spesifikasi_minimum_pesan.setText(CommonUtilities.getNumberFormat(select_produk.getMinimum_pesan())+" Pcs");
            spesifikasi_stok.setText(CommonUtilities.getNumberFormat(select_produk.getMax_qty())+" Pcs");
            spesifikasi_kategori.setText(select_produk.getCategory_name());
            spesifikasi_penjelasan.setText(Html.fromHtml(select_produk.getPenjelasan()));

            fav1.setVisibility(dh.getIdWishlist(select_produk.getId())>0?View.GONE:View.VISIBLE);
            fav2.setVisibility(dh.getIdWishlist(select_produk.getId())>0?View.VISIBLE:View.GONE);

            linearSoldout.setVisibility(select_produk.getProduk_soldout()==1?View.VISIBLE:View.GONE);
            linearNew.setVisibility(select_produk.getProduk_terbaru()==1?View.VISIBLE:View.GONE);
            linearGrosir.setVisibility(select_produk.getProduk_grosir()==1?View.VISIBLE:View.GONE);
            linearPreorder.setVisibility(select_produk.getProduk_preorder()==1?View.VISIBLE:View.GONE);
            linearFreeongkir.setVisibility(select_produk.getProduk_freeongkir()==1?View.VISIBLE:View.GONE);
        }

        kirim_pesan = (MyTextView) findViewById(R.id.kirim_pesan);
        kirim_pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(data.getId()==0) {
                    Intent intent = new Intent(context, LoginActivity.class);
                    //intent.putExtra("menu_selected", menu_selected);
                    startActivityForResult(intent, RESULT_FROM_SIGN_IN);
                } else {
                    Intent intent = new Intent(context, MessageActivity.class);
                    intent.putExtra("produk", select_produk);
                    startActivityForResult(intent, RESULT_FROM_KIRIM_PESAN);
                }

            }
        });
        
        //*********RECYCLERVIEWS*********
        rv_produk_terkait = (RecyclerView)findViewById(R.id.rv2);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        rv_produk_terkait.setLayoutManager(mLayoutManager);
        
        cutprice_produk.setPaintFlags(cutprice_produk.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        
        final int[] number = {1};
        cartno.setText(""+ number[0]);

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (number[0] > 1) { number[0] = number[0] - 1; }
                cartno.setText(""+ number[0]);
            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (number[0] < max_qty) { number[0] = number[0] + 1; }
                cartno.setText("" + number[0]);
            }
        });

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
                    max_qty = 0;
                    for(stok data_: liststok) {
                        if(data_.getUkuran().equalsIgnoreCase(list_ukuran.get(position))) {
                            max_qty+=data_.getQty();
                        }
                    }

                    stok_jumlah.setText(max_qty+" Pcs");
                    ukuran.setText(list_ukuran.get(position));
                    warna.setText("");
                } else {
                    max_qty = 0;
                    for(stok data_: liststok) {
                        if(data_.getUkuran().equalsIgnoreCase(ukuran.getText().toString()) && data_.getWarna().equalsIgnoreCase(list_warna.get(position))) {
                            max_qty+=data_.getQty();
                        }
                    }

                    stok_jumlah.setText(max_qty+" Pcs");
                    warna.setText(list_warna.get(position));
                }
            }
        });

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

        mProdukSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        mProdukSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mProdukSlider.setCustomAnimation(new ChildAnimationExample());
        mProdukSlider.setDuration(4000);

        main_screen = (RelativeLayout) findViewById(R.id.main_screen);
        main_screen.post(new Runnable() {
            @Override
            public void run() {
                if(!is_drawn) {
                    is_drawn = true;
                    linear_1 = (LinearLayout) findViewById(R.id.linear1);
                    linear_2 = (LinearLayout) findViewById(R.id.linear2);
                    linear_button = (LinearLayout) findViewById(R.id.linear_button);
                    ViewGroup.LayoutParams params = mProdukSlider.getLayoutParams();
                    params.width = mProdukSlider.getMeasuredWidth();
                    params.height = main_screen.getHeight() - linear_1.getHeight() - (linear_2.getHeight() / 2) - linear_button.getHeight();
                    mProdukSlider.setLayoutParams(params);

                    new loadDataProdukTerkait().execute();
                }
            }
        });
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

    public void openOngkir() {
        cek_ongkir_detail.setVisibility(is_visible_cek_ongkir?View.GONE:View.VISIBLE);
        cek_ongkir_minimize.setImageResource(is_visible_cek_ongkir?R.drawable.down:R.drawable.up);

        is_visible_cek_ongkir=!is_visible_cek_ongkir;
    }

    public void openDialogMessage(String message, boolean status) {
        text_informasi.setText(message);
        text_title.setText(status?"BERHASIL":"GAGAL");
        dialog_informasi.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_informasi.show();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent();
                intent.putExtra("produk", select_produk);
                setResult(RESULT_OK, intent);
                finish();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent intent = new Intent();
            intent.putExtra("produk", select_produk);
            setResult(RESULT_OK, intent);
            finish();

            return false;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mProdukSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        try {
            unregisterReceiver(mHandleLoadProdukTerkaitReceiver);
            unregisterReceiver(mHandleOpenDetailProdukReceiver);
            unregisterReceiver(mHandleLoadListOngkirReceiver);
            unregisterReceiver(mHandleLoadEkspedisiReceiver);

        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onDestroy();
    }

    @Override
    protected void onPause() {
        try {
            unregisterReceiver(mHandleLoadProdukTerkaitReceiver);
            unregisterReceiver(mHandleOpenDetailProdukReceiver);
            unregisterReceiver(mHandleLoadListOngkirReceiver);
            unregisterReceiver(mHandleLoadEkspedisiReceiver);

        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onPause();
    }

    @Override
    protected void onResume() {
        registerReceiver(mHandleLoadProdukTerkaitReceiver, new IntentFilter("gomocart.application.com.gomocart.LOAD_DATA_PRODUK_TERKAIT"));
        registerReceiver(mHandleOpenDetailProdukReceiver, new IntentFilter("gomocart.application.com.gomocart.TERKAIT_OPEN_DETAIL_PRODUK"));
        registerReceiver(mHandleLoadListOngkirReceiver, new IntentFilter("gomocart.application.com.gomocart.LOAD_DATA_ONGKIR_DETAIL_PRODUK"));
        registerReceiver(mHandleLoadEkspedisiReceiver, new IntentFilter("gomocart.application.com.gomocart.LOAD_EXPEDISI_LIST"));

        super.onResume();
    }

    private final BroadcastReceiver mHandleLoadEkspedisiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(dialog_loading.isShowing() && action.length()>0) {
                loadListArray();
                dialog_loading.dismiss();
                dialog_listview.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog_listview.show();
            }
        }
    };

    private final BroadcastReceiver mHandleLoadListOngkirReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Boolean success = intent.getBooleanExtra("success", false);

            ongkirAdapter.UpdateListOngkirAdapter(ongkirlist);

            dialog_loading.dismiss();
            if(!success) retry.setVisibility(View.VISIBLE);
            if(success) listViewOngkir.setVisibility(View.VISIBLE);

        }
    };
    
    private final BroadcastReceiver mHandleLoadProdukTerkaitReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(final Context context, Intent intent) {

            //Sliding Image Produk
            for (String image : list_gambar) {
                String url = CommonUtilities.SERVER_URL + "/uploads/produk/" + image;
                //String url = CommonUtilities.SERVER_URL+"/store/centercrop.php?url="+ CommonUtilities.SERVER_URL+"/uploads/produk/"+image+"&width=300&height=300";
                TextSliderView textSliderView = new TextSliderView(context);

                textSliderView
                    .image(url.replaceAll("\\s", "%20"))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(DetailProdukActivity.this);

                    textSliderView.bundle(new Bundle());
                    textSliderView.getBundle().putString("extra", image);

                    mProdukSlider.addSlider(textSliderView);
                }

            //produk terkait
            produkTerkaitAdapter = new RecyclerViewProdukAdapter(context, dh, listProdukTerkait);
            rv_produk_terkait.setAdapter(produkTerkaitAdapter);

            //list ukuran
            ukuran.setVisibility(list_ukuran.size()>0?View.VISIBLE:View.INVISIBLE);

            //list warna
            separator_warna.setVisibility(list_warna.size()>0?View.VISIBLE:View.INVISIBLE);
            warna.setVisibility(list_warna.size()>0?View.VISIBLE:View.INVISIBLE);

            separator_ukuran_warna.setVisibility(list_ukuran.size()==0 && list_warna.size()==0?View.GONE:View.VISIBLE);
            linear_ukuran_warna.setVisibility(list_ukuran.size()==0 && list_warna.size()==0?View.GONE:View.VISIBLE);

            //status stok
            max_qty = intent.getIntExtra("jumlah_stok", 0);

            stok_jumlah.setText(max_qty+" Pcs");
            stokAdapter = new ListStokAdapter(context, liststok);
            listviewStok.setAdapter(stokAdapter);

            grosirAdapter = new ListGrosirAdapter(context, listgrosir);
            listviewGrosir.setAdapter(grosirAdapter);

            spesifikasi_stok.setText(CommonUtilities.getNumberFormat(max_qty)+" Pcs");
            boolean is_tampil_stok = select_produk.getProduk_soldout()==1?false:(liststok.size()>0 && intent.getBooleanExtra("is_tampil_stok", false));
            linearStok.setVisibility(is_tampil_stok?View.VISIBLE:View.GONE);
            linear_spesifikasi_stok.setVisibility(is_tampil_stok?View.VISIBLE:View.GONE);
            view_spesifikasi_stok.setVisibility(is_tampil_stok?View.VISIBLE:View.GONE);
            linearDetailGrosir.setVisibility(listgrosir.size()>0?View.VISIBLE:View.GONE);

            edit_province.setText(province_nama);
            edit_city.setText(city_nama);
            edit_state.setText(subdistrict_nama);

            ongkir_tujuan_list.set(0, new ongkir_tujuan(province_nama, city_nama.length()>0));
            ongkir_tujuan_list.set(1, new ongkir_tujuan(city_nama, subdistrict_nama.length()>0));
            ongkir_tujuan_list.set(2, new ongkir_tujuan(subdistrict_nama, false));

            ongkirtujuanbaseAdapter.UpdateData(ongkir_tujuan_list);

            new loadProvince().execute();
            new loadCity().execute();
            new loadSubdistrict().execute();

            if(subdistrict_id>0) {
                loadDataOngkir(false);
            }
        }
    };

    private final BroadcastReceiver mHandleOpenDetailProdukReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            produk data = (produk) intent.getSerializableExtra("produk");

            Intent i = new Intent();
            i.putExtra("opendetail", true);
            i.putExtra("produk", data);
            ((DetailProdukActivity) context).setResult(RESULT_OK, i);
            finish();
        }
    };

    @Override
    public void onSliderClick(BaseSliderView slider) {


        ArrayList<gallery_list> gallery_lists = new ArrayList<>();
        gallery_lists.add(new gallery_list(gallerylist));
        Intent inten = new Intent(context, DetailSlideGalleryActivity.class);
        inten.putExtra("gallery", gallery_lists);
        startActivity(inten);



        /*for (String url : list_gambar) {
            gallerylist.add(new gallery(0, url, url));

            if(slider.getUrl().contains(url.replaceAll("\\s", "%20"))) {

                Intent inten = new Intent(context, DetailGalleryActivity.class);
                inten.putExtra("gallery", new gallery(0, url, url));
                startActivity(inten);

                break;
            }
        }*/
    }

    public class loadDataProdukTerkait extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... urls) {
            list_gambar = new ArrayList<>();
            listProdukTerkait = new ArrayList<>();
            list_ukuran = new ArrayList<>();
            list_warna = new ArrayList<>();

            liststok = new ArrayList<>();
            listgrosir = new ArrayList<>();

            String url = CommonUtilities.SERVER_URL + "/store/androidProdukTerkaitDataStore.php";
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("id", select_produk.getId()+""));
            params.add(new BasicNameValuePair("id_user", data.getId()+""));
            JSONObject json = new JSONParser().getJSONFromUrl(url, params, null);

            boolean is_tampil_stok = false;
            int jumlah_stok = 0;
            String status_stok = "";
            if (json != null) {
                try {

                    //GET ALAMAT KIRIM
                    JSONObject alamat_kirim = json.isNull("alamat_kirim")?null:json.getJSONObject("alamat_kirim");
                    if(alamat_kirim!=null) {
                        province_id = alamat_kirim.isNull("id_propinsi")?0:alamat_kirim.getInt("id_propinsi");
                        province_nama = alamat_kirim.isNull("nama_propinsi")?"":alamat_kirim.getString("nama_propinsi");
                        city_id = alamat_kirim.isNull("id_kota")?0:alamat_kirim.getInt("id_kota");
                        city_nama = alamat_kirim.isNull("nama_kota")?"":alamat_kirim.getString("nama_kota");
                        subdistrict_id = alamat_kirim.isNull("id_kecamatan")?0:alamat_kirim.getInt("id_kecamatan");
                        subdistrict_nama = alamat_kirim.isNull("nama_kecamatan")?"":alamat_kirim.getString("nama_kecamatan");
                    }

                    // gambar produk
                    JSONArray list_images = json.isNull("images")?null:json.getJSONArray("images");
                    for (int i=0; i<list_images.length(); i++) {
                        JSONObject rec = list_images.getJSONObject(i);

                        String file_name = rec.isNull("nama_file")?"":rec.getString("nama_file");
                        list_gambar.add(file_name);
                        gallerylist.add(new gallery(i, file_name, file_name));
                    }

                    //produk terkait
                    JSONArray list_terkait = json.isNull("topics")?null:json.getJSONArray("topics");
                    for (int i=0; i<list_terkait.length(); i++) {
                        JSONObject rec = list_terkait.getJSONObject(i);

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

                        listProdukTerkait.add(new produk(id, kode, nama, id_category, category_name, penjelasan, foto1_produk, harga_beli, harga_jual, harga_diskon, persen_diskon, berat, list_ukuran, ukuran, list_warna, warna, qty, max_qty, minimum_pesan, dh.getIdWishlist(id)>0, produk_promo, produk_featured, produk_terbaru, produk_preorder, produk_soldout, produk_grosir, produk_freeongkir, rating, responden, review));
                    }

                    //list ukuran
                    JSONArray ukuran_list = json.isNull("list_ukuran")?null:json.getJSONArray("list_ukuran");
                    for (int i=0; i<ukuran_list.length(); i++) {
                        JSONObject rec = ukuran_list.getJSONObject(i);
                        list_ukuran.add(rec.isNull("ukuran")?"":rec.getString("ukuran"));
                    }

                    //list warna
                    JSONArray warna_list = json.isNull("list_warna")?null:json.getJSONArray("list_warna");
                    for (int i=0; i<warna_list.length(); i++) {
                        JSONObject rec = warna_list.getJSONObject(i);
                        list_warna.add(rec.isNull("warna")?"":rec.getString("warna"));
                    }

                    //is tampilkan stok
                    is_tampil_stok = json.isNull("tampilkan_stok")?false:json.getBoolean("tampilkan_stok");
                    jumlah_stok = json.isNull("jumlah_stok")?0:json.getInt("jumlah_stok");
                    status_stok = jumlah_stok>0?"In Stock":"Out of Stock";

                    //LIST STOK
                    JSONArray list_stok = json.isNull("list_stok")?null:json.getJSONArray("list_stok");
                    for (int i=0; i<list_stok.length(); i++) {
                        JSONObject rec = list_stok.getJSONObject(i);
                        
                        String sukuran = rec.isNull("ukuran")?"":rec.getString("ukuran");
                        String swarna = rec.isNull("warna")?"":rec.getString("warna");
                        int qty = rec.isNull("jumlah")?0:rec.getInt("jumlah");

                        liststok.add(new stok(sukuran, swarna, qty));
                    }

                    //LIST GROSIR
                    JSONArray list_grosir = json.isNull("list_grosir")?null:json.getJSONArray("list_grosir");
                    for (int i=0; i<list_grosir.length(); i++) {
                        JSONObject rec = list_grosir.getJSONObject(i);

                        int jumlah_min = rec.isNull("jumlah_min")?0:rec.getInt("jumlah_min");
                        int jumlah_max = rec.isNull("jumlah_max")?0:rec.getInt("jumlah_max");
                        double harga = rec.isNull("harga")?0:rec.getDouble("harga");

                        listgrosir.add(new grosir(jumlah_min, jumlah_max, harga));
                    }



                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            Intent i = new Intent("gomocart.application.com.gomocart.LOAD_DATA_PRODUK_TERKAIT");
            i.putExtra("jumlah_stok", jumlah_stok);
            i.putExtra("status_stok", status_stok);
            i.putExtra("is_tampil_stok", is_tampil_stok);
            sendBroadcast(i);

            return null;
        }
    }
    
    private String getList_ukuran() {
        String result = "";
        for(String data: list_ukuran) {
            result += (result.length()>0?"|":"")+data;
        }
        //System.out.println(result);
        return result;
    }

    private String getList_warna() {
        String result = "";
        for(String data: list_warna) {
            result += (result.length()>0?"|":"")+data;
        }
        //System.out.println(result);
        return result;
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

    private void loadListArray(ArrayList<String> list_data) {
        String[] from = new String[] { getResources().getString(R.string.list_dialog_title) };
        int[] to = new int[] { R.id.txt_title };

        List<HashMap<String, String>> fillMaps = new ArrayList<>();
        for (String data : list_data) {
            HashMap<String, String> map = new HashMap<>();
            map.put(getResources().getString(R.string.list_dialog_title), data);

            fillMaps.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(context, fillMaps, R.layout.item_list_dialog, from, to);
        listview_ukuran_warna.setAdapter(adapter);
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

    public void loadDataOngkir(boolean show_dialog) {
        new loadDataOngkir(show_dialog).execute();
    }

    public class loadDataOngkir extends AsyncTask<String, Void, Void> {

        boolean show_dialog = false;
        public loadDataOngkir(boolean show_dialog) {
            this.show_dialog = show_dialog;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ongkirlist = new ArrayList<>();
            ongkirAdapter.UpdateListOngkirAdapter(ongkirlist);

            //loading.setVisibility(View.VISIBLE);
            if(show_dialog) {
                openDialogLoading();
            }
            retry.setVisibility(View.GONE);
            listViewOngkir.setVisibility(View.GONE);
            //Toast.makeText(context, "Berat "+(berat_barang*1000),Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(String... urls) {

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

            Intent i = new Intent("gomocart.application.com.gomocart.LOAD_DATA_ONGKIR_DETAIL_PRODUK");
            i.putExtra("success", success);
            sendBroadcast(i);

            return null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data_intent) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data_intent);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RESULT_FROM_KIRIM_PESAN:
                    String action = data_intent.getStringExtra("goto");
                    if(action!=null && action.equalsIgnoreCase("cart_list")) {
                        Intent intent = new Intent();
                        intent.putExtra("produk", select_produk);
                        intent.putExtra("goto", "cart_list");
                        setResult(RESULT_OK, intent);
                        finish();
                    } else if(action!=null && action.equalsIgnoreCase("wish_list")) {
                        Intent intent = new Intent();
                        intent.putExtra("produk", select_produk);
                        intent.putExtra("goto", "wish_list");
                        setResult(RESULT_OK, intent);
                        finish();
                    }

                    break;
                case RESULT_FROM_SIGN_IN:
                    data = CommonUtilities.getSettingUser(context);
                    checkGcmRegid();

                    Intent intent = new Intent(context, MessageActivity.class);
                    intent.putExtra("produk", select_produk);
                    startActivityForResult(intent, RESULT_FROM_KIRIM_PESAN);
                    break;
            }
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
}
