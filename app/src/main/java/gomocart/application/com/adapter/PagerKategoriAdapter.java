package gomocart.application.com.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import gomocart.application.com.fragment.TabKategoriFragment;
import gomocart.application.com.model.produk_kategori;

public class PagerKategoriAdapter extends FragmentStatePagerAdapter {

    Context context;
    ArrayList<produk_kategori> list_produk_kategori;

    public PagerKategoriAdapter(Context context, FragmentManager fm, ArrayList<produk_kategori> list_produk_kategori) {
        super(fm);
        this.context = context;
        this.list_produk_kategori = list_produk_kategori;
    }

    @Override
    public Fragment getItem(int position) {
        TabKategoriFragment tab_kategori = new TabKategoriFragment();
        tab_kategori.setDataProduk(context, position); //, list_produk_kategori.get(position).getProduk_list());

        return tab_kategori;
    }

    @Override
    public int getCount() {
        return list_produk_kategori.size();
    }
}