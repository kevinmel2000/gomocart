package gomocart.application.com.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import gomocart.application.com.adapter.GridProdukAdapter;
import gomocart.application.com.gomocart.MainActivity;
import gomocart.application.com.gomocart.R;
import gomocart.application.com.libs.CommonUtilities;
import gomocart.application.com.libs.DatabaseHandler;
import gomocart.application.com.libs.JSONParser;
import gomocart.application.com.model.produk;

public class TabKategoriFragment extends Fragment {

    int index;
    Context context;
    GridView gridView;
    GridProdukAdapter produkAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_tabkategorilist, container, false);

        gridView = (GridView) rootView.findViewById(R.id.gridview);

        return rootView;
    }

    public void setDataProduk(Context context, int index) {
        this.index = index;
        this.context = context;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadDataKategori();
    }

    void loadDataKategori() {
        if(MainActivity.dashboard_list_tab_kategori.get(index).getProduk_list().size()==0) {
            new loadDataKategori().execute();
        } else {
            produkAdapter = new GridProdukAdapter(context, new DatabaseHandler(context), MainActivity.dashboard_list_tab_kategori.get(index).getProduk_list());
            gridView.setAdapter(produkAdapter);
        }
    }

    class loadDataKategori extends AsyncTask<String, Void, ArrayList<produk>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<produk> doInBackground(String... urls) {

            String url = CommonUtilities.SERVER_URL + "/store/androidProdukKategoriDataStore.php";
            DatabaseHandler dh = new DatabaseHandler(getContext());

            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("id_user", CommonUtilities.getSettingUser(context).getId()+""));
            params.add(new BasicNameValuePair("id_kategori", MainActivity.dashboard_list_tab_kategori.get(index).getId()+""));

            JSONObject json = new JSONParser().getJSONFromUrl(url, params, null);

            ArrayList<produk> result = new ArrayList<>();
            if(json!=null) {
                try {
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

            MainActivity.dashboard_list_tab_kategori.get(index).setProduk_list(result);

            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<produk> result) {

            produkAdapter = new GridProdukAdapter(getContext(), new DatabaseHandler(context), result);
            gridView.setAdapter(produkAdapter);
        }
    }
}
