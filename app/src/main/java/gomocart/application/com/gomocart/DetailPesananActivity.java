package gomocart.application.com.gomocart;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;

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
import java.util.List;

import customfonts.MyTextView;
import gomocart.application.com.adapter.PesananAdapter;
import gomocart.application.com.libs.CommonUtilities;
import gomocart.application.com.libs.ExpandableHeightListView;
import gomocart.application.com.libs.JSONParser;
import gomocart.application.com.model.alamat;
import gomocart.application.com.model.bank;
import gomocart.application.com.model.grandtotal;
import gomocart.application.com.model.ongkir;
import gomocart.application.com.model.order;
import gomocart.application.com.model.produk;
import gomocart.application.com.model.user;
import gomocart.application.com.model.voucher;

import static gomocart.application.com.libs.CommonUtilities.getOptionsImage;
import static gomocart.application.com.libs.CommonUtilities.initImageLoader;

public class DetailPesananActivity extends AppCompatActivity {

    Context context;

    alamat data_alamat;
    ongkir data_kurir;
    bank data_bank;
    voucher data_voucher;
    grandtotal gtotal;

    //CART LIST
    public static ArrayList<produk> pesananlist = new ArrayList<>();
    public static PesananAdapter pesananAdapter;

    ScrollView detail_view;

    MyTextView alamat;
    LinearLayout linear_dropshiper;
    MyTextView dropshiper;

    ImageView image_expedisi;
    MyTextView expedisi;

    ImageView image_pembayaran;
    MyTextView metode_pembayaran;
    MyTextView pembayaran;

    ExpandableHeightListView listViewPesanan;
    ProgressBar loading;
    LinearLayout retry;
    MyTextView btnReload;
    MyTextView total;
    MyTextView diskon;
    MyTextView subtotal;
    MyTextView biayakirim;
    MyTextView grandtotal;
    MyTextView tutup;

    ImageView back;

    user data;
    order data_order;

    boolean success;
    String message;
    int metode_pembayaran_id;

    ImageLoader imageLoader;
    DisplayImageOptions imageOptionKurir, imageOptionPembayaran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pesanan);

        context = DetailPesananActivity.this;
        data = CommonUtilities.getSettingUser(context);

        initImageLoader(context);
        imageLoader           = ImageLoader.getInstance();
        imageOptionKurir 	  = getOptionsImage(R.drawable.logo_grayscale, R.drawable.logo_grayscale);
        imageOptionPembayaran = getOptionsImage(R.drawable.logo_grayscale, R.drawable.logo_grayscale);

        alamat = (MyTextView) findViewById(R.id.alamat);
        linear_dropshiper = (LinearLayout) findViewById(R.id.linear_dropshiper);
        dropshiper = (MyTextView) findViewById(R.id.dropship);
        image_expedisi = (ImageView) findViewById(R.id.image_expedisi);
        expedisi = (MyTextView) findViewById(R.id.expedisi);
        image_pembayaran = (ImageView) findViewById(R.id.image_pembayaran);
        metode_pembayaran = (MyTextView) findViewById(R.id.metode_pembayaran);
        pembayaran = (MyTextView) findViewById(R.id.pembayaran);
        detail_view = (ScrollView) findViewById(R.id.detail_view);
        listViewPesanan = (ExpandableHeightListView) findViewById(R.id.lisview);
        loading = (ProgressBar) findViewById(R.id.pgbarLoading);
        retry = (LinearLayout) findViewById(R.id.loadMask);
        btnReload = (MyTextView) findViewById(R.id.btnReload);

        total      = (MyTextView) findViewById(R.id.total);
        diskon     = (MyTextView) findViewById(R.id.diskon);
        subtotal   = (MyTextView) findViewById(R.id.subtotal);
        biayakirim = (MyTextView) findViewById(R.id.biayakirim);
        grandtotal = (MyTextView) findViewById(R.id.grandtotal);
        tutup      = (MyTextView) findViewById(R.id.selesai);
        back       = (ImageView) findViewById(R.id.back);

        pesananAdapter = new PesananAdapter(context, pesananlist);
        listViewPesanan.setAdapter(pesananAdapter);

        btnReload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                loadDataPesanan();
            }
        });
        loading.setVisibility(View.INVISIBLE);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();

            }
        });

        tutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();

            }
        });

        if(savedInstanceState==null) {
            data_order = (order) getIntent().getSerializableExtra("order");
        }

        //LOAD DATA PESANAN
        loadDataPesanan();

    }

    public void loadDataPesanan() {
        new loadDataPesanan().execute();
    }

    public class loadDataPesanan extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            detail_view.setVisibility(View.INVISIBLE);
            loading.setVisibility(View.VISIBLE);
            retry.setVisibility(View.GONE);
        }

        @Override
        protected Void doInBackground(String... urls) {

            pesananlist = new ArrayList<>();
            boolean success = false;
            
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("id_user", data.getId()+""));
            params.add(new BasicNameValuePair("no_transaksi", data_order.getNo_transaksi()));

            String url = CommonUtilities.SERVER_URL + "/store/androidDetailPesananDataStore.php";
            JSONObject json = new JSONParser().getJSONFromUrl(url, params, null);
            if(json!=null) {
                try {

                    //ALAMAT
                    String nama = "";
                    String alamats = "";
                    int province_id = 0;
                    String province = "";
                    int city_id = 0;
                    String city_name = "";
                    int subdistrict_id = 0;
                    String subdistrict_name = "";
                    String kode_pos = "";
                    String no_hp = "";
                    boolean as_default = false;
                    boolean is_dropship = false;
                    String dropship_name = "";
                    String dropship_phone = "";
                    String email_notifikasi = "";
                    
                    JSONObject rec_alamat = json.isNull("alamat")?null:json.getJSONObject("alamat");
                    if(rec_alamat!=null) {
                        nama = rec_alamat.isNull("nama")?"":rec_alamat.getString("nama");
                        alamats = rec_alamat.isNull("alamat")?"":rec_alamat.getString("alamat");
                        province_id = rec_alamat.isNull("id_propinsi")?0:rec_alamat.getInt("id_propinsi");
                        province = rec_alamat.isNull("nama_propinsi")?"":rec_alamat.getString("nama_propinsi");
                        city_id = rec_alamat.isNull("id_kota")?0:rec_alamat.getInt("id_kota");
                        city_name = rec_alamat.isNull("nama_kota")?"":rec_alamat.getString("nama_kota");
                        subdistrict_id = rec_alamat.isNull("id_kecamatan")?0:rec_alamat.getInt("id_kecamatan");
                        subdistrict_name = rec_alamat.isNull("nama_kecamatan")?"":rec_alamat.getString("nama_kecamatan");
                        kode_pos = rec_alamat.isNull("kode_pos")?"":rec_alamat.getString("kode_pos");
                        no_hp = rec_alamat.isNull("no_hp")?"":rec_alamat.getString("no_hp");
                        is_dropship = rec_alamat.isNull("is_dropship")?false:rec_alamat.getBoolean("is_dropship");
                        dropship_name = rec_alamat.isNull("dropship_name")?"":rec_alamat.getString("dropship_name");
                        dropship_phone = rec_alamat.isNull("dropship_phone")?"":rec_alamat.getString("dropship_phone");
                        email_notifikasi = rec_alamat.isNull("email_notifikasi")?"":rec_alamat.getString("email_notifikasi");
                    }
                    data_alamat = new alamat(0, nama, alamats, province_id, province, city_id, city_name, subdistrict_id, subdistrict_name, kode_pos, no_hp, as_default, is_dropship, dropship_name, dropship_phone, email_notifikasi);

                    //KURIR PENGIRIMAN
                    int id_kurir = 0;
                    String kode_kurir = "";
                    String nama_kurir = "";
                    String kode_service = "";
                    String nama_service = "";
                    int nominal = 0;
                    String etd = "";
                    String tarif = "";
                    String logo_ekspedisi = "";

                    JSONObject rec_ongkir = json.isNull("ongkir")?null:json.getJSONObject("ongkir");
                    if(rec_ongkir!=null) {
                        id_kurir = rec_ongkir.isNull("id_kurir")?0:rec_ongkir.getInt("id_kurir");
                        kode_kurir = rec_ongkir.isNull("kode_kurir")?"":rec_ongkir.getString("kode_kurir");
                        nama_kurir = rec_ongkir.isNull("nama_kurir")?"":rec_ongkir.getString("nama_kurir");
                        kode_service = rec_ongkir.isNull("kode_service")?"":rec_ongkir.getString("kode_service");
                        nama_service = rec_ongkir.isNull("nama_service")?"":rec_ongkir.getString("nama_service");
                        nominal =  rec_ongkir.isNull("nominal")?0:rec_ongkir.getInt("nominal");
                        etd = rec_ongkir.isNull("etd")?"":rec_ongkir.getString("etd");
                        tarif = rec_ongkir.isNull("tarif")?"":rec_ongkir.getString("tarif");
                        logo_ekspedisi =  rec_ongkir.isNull("gambar")?"":rec_ongkir.getString("gambar");

                    }
                    data_kurir = new ongkir(id_kurir, kode_kurir, nama_kurir, kode_service, nama_service, nominal, etd, tarif, logo_ekspedisi);

                    //METODE PEMBAYARAN
                    metode_pembayaran_id = json.isNull("metode_pembayaran")?1:json.getInt("metode_pembayaran");

                    int id_bank = 0;
                    String no_rekening = "";
                    String nama_pemilik_rekening = "";
                    String nama_bank = "";
                    String cabang = "";
                    String logo_bank = "";

                    JSONObject rec_pembayaran = json.isNull("pembayaran")?null:json.getJSONObject("pembayaran");
                    if(rec_pembayaran!=null) {
                        id_bank = rec_pembayaran.isNull("id")?0:rec_pembayaran.getInt("id");
                        no_rekening = rec_pembayaran.isNull("no_rekening")?"":rec_pembayaran.getString("no_rekening");
                        nama_pemilik_rekening = rec_pembayaran.isNull("nama_pemilik_rekening")?"":rec_pembayaran.getString("nama_pemilik_rekening");
                        nama_bank = rec_pembayaran.isNull("nama_bank")?"":rec_pembayaran.getString("nama_bank");
                        cabang = rec_pembayaran.isNull("cabang")?"":rec_pembayaran.getString("cabang");
                        logo_bank =  rec_pembayaran.isNull("gambar")?"":rec_pembayaran.getString("gambar");
                    }
                    data_bank = new bank(id_bank, no_rekening, nama_pemilik_rekening, nama_bank, cabang, logo_bank);
                    
                    //VOUCHER
                    data_voucher = null;
                    String kode_voucher = "";
                    double nominal_voucher = 0;
                    String tipe_voucher = "";
                    String jenis_voucher = "";

                    JSONObject rec_voucher = json.isNull("voucher")?null:json.getJSONObject("voucher");
                    if(rec_voucher!=null) {
                        kode_voucher = rec_voucher.isNull("kode_voucher")?"":rec_voucher.getString("kode_voucher");
                        nominal_voucher =  rec_voucher.isNull("nominal_voucher")?0:rec_voucher.getDouble("nominal_voucher");
                        tipe_voucher = rec_voucher.isNull("tipe_voucher")?"":rec_voucher.getString("tipe_voucher");
                        jenis_voucher = rec_voucher.isNull("jenis_voucher")?"":rec_voucher.getString("jenis_voucher");

                        data_voucher = new voucher(kode_voucher, nominal_voucher, tipe_voucher, jenis_voucher);
                    }

                    //GRAND TOTAL
                    double gtotal_total = 0;
                    double gtotal_diskon = 0;
                    double gtotal_pengiriman = 0;
                    double gtotal_voucher = 0;

                    JSONObject rec_gtotal = json.isNull("gtotal")?null:json.getJSONObject("gtotal");
                    if(rec_gtotal!=null) {
                        gtotal_total      = rec_gtotal.isNull("gtotal_total")?0:rec_gtotal.getDouble("gtotal_total");
                        gtotal_diskon     = rec_gtotal.isNull("gtotal_diskon")?0:rec_gtotal.getDouble("gtotal_diskon");
                        gtotal_pengiriman = rec_gtotal.isNull("gtotal_pengiriman")?0:rec_gtotal.getDouble("gtotal_pengiriman");
                        gtotal_voucher    = rec_gtotal.isNull("gtotal_voucher")?0:rec_gtotal.getDouble("gtotal_voucher");
                    }
                    gtotal = new grandtotal(gtotal_total, gtotal_diskon, gtotal_total-gtotal_diskon, gtotal_voucher, gtotal_pengiriman, ((gtotal_total-gtotal_diskon)-gtotal_voucher)+gtotal_pengiriman);

                    JSONArray topics = json.isNull("topics")?null:json.getJSONArray("topics");
                    for (int i=0; i<topics.length(); i++) {
                        JSONObject rec = topics.getJSONObject(i);

                        int id = rec.isNull("id")?0:rec.getInt("id");
                        String kode = rec.isNull("kode")?"":rec.getString("kode");
                        String nama_barang = rec.isNull("nama")?"":rec.getString("nama");
                        String gambar = rec.isNull("gambar")?"":rec.getString("gambar");
                        String ukuran = rec.isNull("ukuran")?"":rec.getString("ukuran");
                        String warna = rec.isNull("warna")?"":rec.getString("warna");
                        int jumlah = rec.isNull("jumlah")?0:rec.getInt("jumlah");
                        int berat = rec.isNull("berat")?0:rec.getInt("berat");
                        double harga_beli = rec.isNull("harga_beli")?0:rec.getDouble("harga_beli");
                        double harga_jual = rec.isNull("harga_jual")?0:rec.getDouble("harga_jual");
                        double harga_diskon = rec.isNull("harga_diskon")?0:rec.getDouble("harga_diskon");
                        int persen_diskon = rec.isNull("persen_diskon")?0:rec.getInt("persen_diskon");
                        double subtotal = rec.isNull("subtotal")?0:rec.getDouble("subtotal");
                        double grandtotal = rec.isNull("grandtotal")?0:rec.getDouble("grandtotal");

                        produk data = new produk(id, kode, nama_barang, 0, "", "", gambar, harga_beli, harga_jual, harga_diskon, persen_diskon, berat, "", ukuran, "", warna, jumlah, 0, 1, false, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
                        data.setQty(jumlah);
                        data.setBerat(berat);
                        data.setHarga_beli(harga_beli);
                        data.setHarga_jual(harga_jual);
                        data.setHarga_diskon(harga_diskon);
                        data.setPersen_diskon(persen_diskon);
                        data.setSubtotal(subtotal);
                        data.setGrandtotal(grandtotal);

                        pesananlist.add(data);
                        

                    }

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

    @Override
    protected void onDestroy() {
        try {
            unregisterReceiver(mHandleLoadDataPesananReceiver);

        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onDestroy();
    }

    @Override
    protected void onPause() {
        try {
            unregisterReceiver(mHandleLoadDataPesananReceiver);

        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onPause();
    }

    @Override
    protected void onResume() {
        registerReceiver(mHandleLoadDataPesananReceiver, new IntentFilter("gomocart.application.com.gomocart.LOAD_DATA_PESANAN"));

        super.onResume();
    }



    private final BroadcastReceiver mHandleLoadDataPesananReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Boolean success = intent.getBooleanExtra("success", false);

            pesananAdapter.UpdatePesananAdapter(pesananlist);

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
            }

            double grand = total_belanja + gtotal.getPengiriman() - total_voucher;
            total.setText(CommonUtilities.getCurrencyFormat(gtotal.getTotal(), ""));
            diskon.setText(CommonUtilities.getCurrencyFormat(total_voucher, ""));
            subtotal.setText(CommonUtilities.getCurrencyFormat(gtotal.getSub_total(), ""));
            biayakirim.setText(CommonUtilities.getCurrencyFormat(gtotal.getPengiriman(), ""));
            grandtotal.setText(CommonUtilities.getCurrencyFormat(grand, ""));

            //ALAMAT PENGIRIMAN
            alamat.setText(data_alamat.getNama()+"\n"+data_alamat.getAlamat()+ " "+data_alamat.getKode_pos()+"\n"+data_alamat.getSubdistrict_name()+", "+data_alamat.getCity_name()+"\n"+data_alamat.getProvince()+"\nTelepon: "+data_alamat.getNo_hp());

            //DROPSHIPER
            linear_dropshiper.setVisibility(data_alamat.getIs_dropship()?View.VISIBLE:View.GONE);
            dropshiper.setText("Dropshiper: "+data_alamat.getDropship_name()+"\nNo. Handphone: "+data_alamat.getDropship_phone());

            //EXPEDISI PENGIRIMAN
            loadKurirLogo(data_kurir.getGambar());
            expedisi.setText(data_kurir.getKode_kurir()+" "+data_kurir.getNama_service()+" ("+data_kurir.getEtd()+" hari)\n"+CommonUtilities.getCurrencyFormat(data_kurir.getNominal(), "Rp. "));

            //PEMBAYARAN
            metode_pembayaran.setText(metode_pembayaran_id==1?"Transfer Bank: ":(metode_pembayaran_id==2?"Kartu Kredit / Debet: ":"Bayar di Tempat: "));
            loadPembayaranLogo(data_bank.getGambar());
            pembayaran.setText(data_bank.getNama_bank()+"\n"+data_bank.getNo_rekening()+" an. "+data_bank.getNama_pemilik_rekening());


            loading.setVisibility(View.GONE);
            if(success) {
                detail_view.setVisibility(View.VISIBLE);
            } else {
                retry.setVisibility(View.VISIBLE);
            }
        }
    };

    public void loadKurirLogo(String gambar) {
        String server = CommonUtilities.SERVER_URL;
        String url = server+"/uploads/ekspedisi/"+gambar;
        imageLoader.displayImage(url, image_expedisi, imageOptionKurir);
    }

    public void loadPembayaranLogo(String gambar) {
        String server = CommonUtilities.SERVER_URL;
        String url = server+"/uploads/bank/"+gambar;
        imageLoader.displayImage(url, image_pembayaran, imageOptionPembayaran);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();

            return false;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_detail, menu);
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
                setResult(RESULT_OK, intent);
                finish();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
