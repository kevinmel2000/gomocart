package gomocart.application.com.gomocart;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import customfonts.MyTextView;
import gomocart.application.com.adapter.FilterKategoriAdapter;
import gomocart.application.com.adapter.FilterUkuranAdapter;
import gomocart.application.com.adapter.FilterBrandAdapter;
import gomocart.application.com.adapter.FilterWarnaAdapter;
import gomocart.application.com.libs.CommonUtilities;
import gomocart.application.com.libs.JSONParser;
import gomocart.application.com.libs.RangeBar;
import gomocart.application.com.model.brand;
import gomocart.application.com.model.kategori;
import gomocart.application.com.model.ukuran;
import gomocart.application.com.model.warna;

public class FilterProdukActivity extends AppCompatActivity {

    Context context;

    ArrayList<kategori> categorylist = new ArrayList<>();
    Map<kategori, ArrayList<kategori>> subcategorylist = new LinkedHashMap<>();
    FilterKategoriAdapter kategoriAdapter = new FilterKategoriAdapter(categorylist, subcategorylist);
    ExpandableListView listview_kategori;

    ArrayList<brand> brandlist = new ArrayList<>();
    FilterBrandAdapter brandAdapter = new FilterBrandAdapter(brandlist);
    ListView listview_brand;

    ArrayList<ukuran> ukuranlist = new ArrayList<>();
    FilterUkuranAdapter ukuranAdapter = new FilterUkuranAdapter(ukuranlist);
    ListView listview_ukuran;

    ArrayList<warna> warnalist = new ArrayList<>();
    FilterWarnaAdapter warnaAdapter = new FilterWarnaAdapter(warnalist);
    ListView listview_warna;

    RangeBar pricerangebar;
    Button pricerang1, pricerang2;

    RangeBar discountrangebar;
    Button discountrang1, discountrang2;

    ImageView btn_close;

    public static ArrayList<kategori> kategori_select = new ArrayList<>();
    public static ArrayList<brand> brand_select = new ArrayList<>();
    public static ArrayList<ukuran> ukuran_select = new ArrayList<>();
    public static ArrayList<warna> warna_select = new ArrayList<>();
    int harga_min_select = 0;
    int harga_max_select = 1000000;
    int diskon_min_select = 0;
    int diskon_max_select = 100;

    MyTextView clearall, apply;
    MyTextView categories, brands, size, colour, price, discount;
    LinearLayout categorylinear, brandlinear, sizelinear, colourlinear, pricelinear, discountlinear;
    Typeface fonts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_produk);
        
        context = FilterProdukActivity.this;
        fonts =  Typeface.createFromAsset(getAssets(), "fonts/MuseoSans-500.otf");

        clearall = (MyTextView) findViewById(R.id.clearall);
        apply = (MyTextView) findViewById(R.id.apply);

        Button t5 =(Button)findViewById(R.id.pricerang1);
        t5.setTypeface(fonts);
        Button t6 =(Button)findViewById(R.id.pricerang2);
        t6.setTypeface(fonts);

        Button t7 =(Button)findViewById(R.id.discountrang1);
        t7.setTypeface(fonts);
        Button t8 =(Button)findViewById(R.id.discountrang2);
        t8.setTypeface(fonts);

        listview_kategori = (ExpandableListView) findViewById(R.id.categorylistview);
        listview_brand = (ListView) findViewById(R.id.brandlistview);
        listview_ukuran = (ListView) findViewById(R.id.sizelistview);
        listview_warna = (ListView) findViewById(R.id.colourlistview);

        listview_kategori.setAdapter(kategoriAdapter);
        listview_brand.setAdapter(brandAdapter);
        listview_ukuran.setAdapter(ukuranAdapter);
        listview_warna.setAdapter(warnaAdapter);
        
        categories = (MyTextView) findViewById(R.id.categories);
        categorylinear = (LinearLayout) findViewById(R.id.categorylist);

        brands = (MyTextView) findViewById(R.id.brands);
        brandlinear = (LinearLayout) findViewById(R.id.brandlist);

        size = (MyTextView) findViewById(R.id.size);
        sizelinear = (LinearLayout) findViewById(R.id.sizelist);

        colour = (MyTextView) findViewById(R.id.colour);
        colourlinear = (LinearLayout) findViewById(R.id.colourlist);

        price = (MyTextView) findViewById(R.id.price);
        pricelinear = (LinearLayout) findViewById(R.id.pricelist);

        discount = (MyTextView) findViewById(R.id.discount);
        discountlinear = (LinearLayout) findViewById(R.id.discountlist);

        btn_close    = (ImageView) findViewById(R.id.btn_close);

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });


        pricerangebar = (RangeBar) findViewById(R.id.pricerangebar);
        pricerang1 = (Button) findViewById(R.id.pricerang1);
        pricerang2 = (Button) findViewById(R.id.pricerang2);

        pricerangebar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {
                harga_min_select = leftPinIndex*10000;
                harga_max_select = rightPinIndex*10000;

                pricerang1.setText(CommonUtilities.getCurrencyFormat(leftPinIndex*10000, "Rp. "));
                pricerang2.setText(CommonUtilities.getCurrencyFormat(rightPinIndex*10000, "Rp. "));
            }

        });

        discountrangebar = (RangeBar) findViewById(R.id.discountrangebar);
        discountrang1 = (Button) findViewById(R.id.discountrang1);
        discountrang2 = (Button) findViewById(R.id.discountrang2);

        discountrangebar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {
                diskon_min_select = leftPinIndex*10000;
                diskon_max_select = rightPinIndex*10000;

                discountrang1.setText(leftPinIndex+" %");
                discountrang2.setText(rightPinIndex+" %");
            }

        });

        categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                clickb("0");

            }
        });

        brands.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clickb("1");

            }
        });

        size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clickb("2");

            }
        });
        colour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clickb("3");

            }
        });

        price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clickb("4");

            }
        });
        discount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clickb("5");

            }
        });

        clearall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFilter();
            }
        });

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filter_kategori = "";
                for (kategori data: kategori_select) {
                    filter_kategori += (filter_kategori.length()>0?",":"")+data.getId();
                }

                String filter_brand = "";
                for (brand data: brand_select) {
                    filter_brand += (filter_brand.length()>0?",":"")+data.getId();
                }

                String filter_ukuran = "";
                for (ukuran data: ukuran_select) {
                    filter_ukuran += (filter_ukuran.length()>0?",":"")+"'"+data.getUkuran()+"'";
                }

                String filter_warna = "";
                for (warna data: warna_select) {
                    filter_warna += (filter_warna.length()>0?",":"")+"'"+data.getWarna()+"'";
                }
                
                Intent intent = new Intent();
                intent.putExtra("filter_kategori", filter_kategori);
                intent.putExtra("filter_brand", filter_brand);
                intent.putExtra("filter_ukuran", filter_ukuran);
                intent.putExtra("filter_warna", filter_warna);
                intent.putExtra("filter_harga_max", harga_max_select);
                intent.putExtra("filter_harga_min", harga_min_select);
                intent.putExtra("filter_diskon_max", diskon_max_select);
                intent.putExtra("filter_diskon_min", diskon_min_select);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        loadFilter();
        clickb("0");
    }

    private void clickb(String s) {

        categories.setTextColor(Color.parseColor("#353944"));
        categorylinear.setVisibility(View.GONE);
        brands.setTextColor(Color.parseColor("#353944"));
        brandlinear.setVisibility(View.GONE);
        size.setTextColor(Color.parseColor("#353944"));
        sizelinear.setVisibility(View.GONE);
        colour.setTextColor(Color.parseColor("#353944"));
        colourlinear.setVisibility(View.GONE);
        price.setTextColor(Color.parseColor("#353944"));
        pricelinear.setVisibility(View.GONE);
        discount.setTextColor(Color.parseColor("#353944"));
        discountlinear.setVisibility(View.GONE);

        if (s.equalsIgnoreCase("0")) {

            categorylinear.setVisibility(View.VISIBLE);
            categories.setTextColor(Color.parseColor("#ff5254"));
        } else if (s.equalsIgnoreCase("1")) {

            brandlinear.setVisibility(View.VISIBLE);
            brands.setTextColor(Color.parseColor("#ff5254"));
        } else if (s.equalsIgnoreCase("2")) {

            sizelinear.setVisibility(View.VISIBLE);
            size.setTextColor(Color.parseColor("#ff5254"));
        } else if (s.equalsIgnoreCase("3")) {

            colourlinear.setVisibility(View.VISIBLE);
            colour.setTextColor(Color.parseColor("#ff5254"));
        } else if (s.equalsIgnoreCase("4")) {

            pricelinear.setVisibility(View.VISIBLE);
            price.setTextColor(Color.parseColor("#ff5254"));
        } else if (s.equalsIgnoreCase("5")) {

            discountlinear.setVisibility(View.VISIBLE);
            discount.setTextColor(Color.parseColor("#ff5254"));
        }
        
    }

    public void loadFilter() {
        new loadFilter().execute();
    }

    public class loadFilter extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... urls) {

            categorylist = new ArrayList<>();
            subcategorylist = new LinkedHashMap<>();

            brandlist = new ArrayList<>();
            ukuranlist = new ArrayList<>();
            warnalist = new ArrayList<>();

            String url = CommonUtilities.SERVER_URL + "/store/androidFilterDataStore.php";
            JSONObject json = new JSONParser().getJSONFromUrl(url, null, null);
            boolean success = false;
            if(json!=null) {
                try {
                    JSONArray topics_induk = json.isNull("kategori")?null:json.getJSONArray("kategori");
                    for (int i=0; i<topics_induk.length(); i++) {
                        JSONObject rec_induk = topics_induk.getJSONObject(i);

                        int induk_id = rec_induk.isNull("id")?null:rec_induk.getInt("id");
                        String induk_nama = rec_induk.isNull("nama")?null:rec_induk.getString("nama");
                        String induk_penjelasan = rec_induk.isNull("penjelasan")?null:rec_induk.getString("penjelasan");
                        String induk_header = rec_induk.isNull("header")?null:rec_induk.getString("header");

                        JSONArray child = rec_induk.isNull("child")?null:rec_induk.getJSONArray("child");
                        ArrayList<kategori> data_child = new ArrayList<>();
                        for (int j=0; j<child.length(); j++) {
                            JSONObject rec_child = child.getJSONObject(j);

                            int anak_id = rec_child.isNull("id")?null:rec_child.getInt("id");
                            String anak_nama = rec_child.isNull("nama")?null:rec_child.getString("nama");
                            String anak_penjelasan = rec_child.isNull("penjelasan")?null:rec_child.getString("penjelasan");
                            String anak_header = rec_child.isNull("header")?null:rec_child.getString("header");

                            data_child.add(new kategori(anak_id, anak_nama, anak_penjelasan, anak_header));
                        }

                        categorylist.add(new kategori(induk_id, induk_nama, induk_penjelasan, induk_header));
                        subcategorylist.put(categorylist.get(categorylist.size()-1), data_child);
                    }
                    
                    JSONArray topics_brand = json.isNull("brand")?null:json.getJSONArray("brand");
                    for (int i=0; i<topics_brand.length(); i++) {
                        JSONObject rec_brand = topics_brand.getJSONObject(i);
                        
                        int id = rec_brand.isNull("id")?null:rec_brand.getInt("id");
                        String kode = rec_brand.isNull("kode")?null:rec_brand.getString("kode");
                        String nama = rec_brand.isNull("nama")?null:rec_brand.getString("nama");
                        String logo = rec_brand.isNull("logo")?null:rec_brand.getString("logo");
                    
                        brandlist.add(new brand(id, kode, nama, logo));
                    }
                    
                    JSONArray topics_ukuran = json.isNull("ukuran")?null:json.getJSONArray("ukuran");
                    for (int i=0; i<topics_ukuran.length(); i++) {
                        JSONObject rec_ukuran = topics_ukuran.getJSONObject(i);

                        int id = rec_ukuran.isNull("id")?null:rec_ukuran.getInt("id");
                        String ukuran = rec_ukuran.isNull("ukuran")?null:rec_ukuran.getString("ukuran");
                        String logo = rec_ukuran.isNull("logo")?null:rec_ukuran.getString("logo");

                        ukuranlist.add(new ukuran(id, ukuran, logo));
                    }

                    JSONArray topics_warna = json.isNull("warna")?null:json.getJSONArray("warna");
                    for (int i=0; i<topics_warna.length(); i++) {
                        JSONObject rec_warna = topics_warna.getJSONObject(i);

                        int id = rec_warna.isNull("id")?null:rec_warna.getInt("id");
                        String warna = rec_warna.isNull("warna")?null:rec_warna.getString("warna");
                        String logo = rec_warna.isNull("logo")?null:rec_warna.getString("logo");

                        warnalist.add(new warna(id, warna, logo));
                    }
                    success = true;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            Intent i = new Intent("gomocart.application.com.gomocart.LOAD_FILTER");
            i.putExtra("success", success);
            sendBroadcast(i);

            return null;
        }
    }


    @Override
    protected void onDestroy() {
        try {
            unregisterReceiver(mHandleLoadFilterReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onDestroy();
    }

    @Override
    protected void onPause() {
        try {
            unregisterReceiver(mHandleLoadFilterReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onPause();
    }

    @Override
    protected void onResume() {
        registerReceiver(mHandleLoadFilterReceiver, new IntentFilter("gomocart.application.com.gomocart.LOAD_FILTER"));

        super.onResume();
    }

    private final BroadcastReceiver mHandleLoadFilterReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            resetFilter();
        }
    };

    private void clearFilter() {
        for (kategori data: categorylist) {
            categorylist.get(categorylist.indexOf(data)).setChecked(false);
            for (kategori data_sub: subcategorylist.get(data)) {
                subcategorylist.get(data).get(subcategorylist.get(data).indexOf(data_sub)).setChecked(false);
            }
        }

        for(brand data: brandlist) {
            brandlist.get(brandlist.indexOf(data)).setChecked(false);
        }

        for(ukuran data: ukuranlist) {
            ukuranlist.get(ukuranlist.indexOf(data)).setChecked(false);
        }

        for(warna data: warnalist) {
            warnalist.get(warnalist.indexOf(data)).setChecked(false);
        }

        resetFilter();
    }

    private void resetFilter() {
        kategori_select = new ArrayList<>();
        brand_select = new ArrayList<>();
        ukuran_select = new ArrayList<>();
        warna_select = new ArrayList<>();

        kategoriAdapter.UpdateFilterKategoriAdapter(categorylist, subcategorylist);
        brandAdapter.UpdateFilterBrandAdapter(brandlist);
        ukuranAdapter.UpdateFilterUkuranAdapter(ukuranlist);
        warnaAdapter.UpdateFilterWarnaAdapter(warnalist);
    }
}
