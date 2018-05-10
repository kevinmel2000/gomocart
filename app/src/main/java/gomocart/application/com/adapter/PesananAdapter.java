package gomocart.application.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import customfonts.MyTextView;
import gomocart.application.com.gomocart.MainActivity;
import gomocart.application.com.gomocart.R;
import gomocart.application.com.libs.CommonUtilities;
import gomocart.application.com.libs.DatabaseHandler;
import gomocart.application.com.libs.ResizableImageView;
import gomocart.application.com.model.produk;


public class PesananAdapter extends BaseAdapter {
    Context context;
    ArrayList<produk> pesanan_list;

    public PesananAdapter(Context context, ArrayList<produk> pesanan_list) {
        this.context = context;
        this.pesanan_list = pesanan_list;
    }

    public void UpdatePesananAdapter(ArrayList<produk> pesanan_list) {
        this.pesanan_list = pesanan_list;
        notifyDataSetChanged();
    }
    
    @Override
    public int getCount() {
        return pesanan_list.size();
    }

    @Override
    public Object getItem(int position) {
        return pesanan_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {


        final ViewHolder viewHolder;

        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.pesanan_list,null);

            viewHolder = new ViewHolder();

            viewHolder.image = (ResizableImageView)convertView.findViewById(R.id.image);
            viewHolder.title = (MyTextView)convertView.findViewById(R.id.title);
            viewHolder.qty = (MyTextView)convertView.findViewById(R.id.qty);
            viewHolder.separator_ukuran = (View)convertView.findViewById(R.id.separator_ukuran);
            viewHolder.ukuran = (MyTextView)convertView.findViewById(R.id.ukuran);
            viewHolder.separator_warna = (View)convertView.findViewById(R.id.separator_warna);
            viewHolder.warna = (MyTextView)convertView.findViewById(R.id.warna);
            viewHolder.price = (MyTextView)convertView.findViewById(R.id.price);
            viewHolder.total = (MyTextView)convertView.findViewById(R.id.total);

            convertView.setTag(viewHolder);
        }else {

            viewHolder = (ViewHolder)convertView.getTag();
        }

        final produk produk_item = (produk) getItem(position);

        String server = CommonUtilities.SERVER_URL;
        String url = server+"/uploads/produk/"+produk_item.getFoto1_produk();
        MainActivity.imageLoader.displayImage(url, viewHolder.image, MainActivity.imageOptionProduk);

        viewHolder.title.setText(produk_item.getNama());

        viewHolder.ukuran.setVisibility(produk_item.getUkuran().length()==0?View.GONE:View.VISIBLE);
        viewHolder.ukuran.setText(produk_item.getUkuran());

        viewHolder.separator_warna.setVisibility(produk_item.getUkuran().length()==0 || produk_item.getWarna().length()==0?View.GONE:View.VISIBLE);

        viewHolder.warna.setVisibility(produk_item.getWarna().length()==0?View.GONE:View.VISIBLE);
        viewHolder.warna.setText(produk_item.getWarna());


        viewHolder.qty.setText(produk_item.getQty()+" Pcs");
        viewHolder.price.setText("@ "+CommonUtilities.getCurrencyFormat(produk_item.getHarga_jual(), "Rp. ")+" "+(produk_item.getPersen_diskon()>0?" (-"+produk_item.getPersen_diskon()+"%)"+CommonUtilities.getCurrencyFormat((produk_item.getPersen_diskon()*0.01)*produk_item.getHarga_jual(), "Rp. "):""));
        viewHolder.total.setText(CommonUtilities.getCurrencyFormat(produk_item.getGrandtotal(), "Rp. "));

        return convertView;
    }

    private class ViewHolder{
        ResizableImageView image;
        MyTextView title;
        MyTextView qty;
        View separator_ukuran;
        MyTextView ukuran;
        View separator_warna;
        MyTextView warna;
        MyTextView price;
        MyTextView total;
    }
}




