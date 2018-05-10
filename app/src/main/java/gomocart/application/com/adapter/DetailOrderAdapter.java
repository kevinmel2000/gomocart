package gomocart.application.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import customfonts.MyTextView;
import gomocart.application.com.gomocart.MainActivity;
import gomocart.application.com.libs.CommonUtilities;
import gomocart.application.com.gomocart.R;
import gomocart.application.com.model.produk;

import static gomocart.application.com.libs.CommonUtilities.getOptionsImage;
import static gomocart.application.com.libs.CommonUtilities.initImageLoader;


public class DetailOrderAdapter extends BaseAdapter {

    Context context;
    ArrayList<produk> cartlist;

    public DetailOrderAdapter(Context context, ArrayList<produk> cartlist) {
        this.context = context;
        this.cartlist = cartlist;
    }

    public void UpdateDetailOrderAdapter(ArrayList<produk> cartlist) {
        this.cartlist = cartlist;
        notifyDataSetChanged();
    }
    
    @Override
    public int getCount() {
        return cartlist.size();
    }

    @Override
    public Object getItem(int position) {
        return cartlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.detail_order_list,null);

            viewHolder = new ViewHolder();

            viewHolder.image = (ImageView)convertView.findViewById(R.id.image);
            viewHolder.title = (MyTextView)convertView.findViewById(R.id.title);
            viewHolder.price = (MyTextView)convertView.findViewById(R.id.price);
            viewHolder.text = (MyTextView)convertView.findViewById(R.id.text);
            viewHolder.separator_ukuran = (View)convertView.findViewById(R.id.separator_ukuran);
            viewHolder.ukuran = (MyTextView)convertView.findViewById(R.id.ukuran);
            viewHolder.separator_warna = (View)convertView.findViewById(R.id.separator_warna);
            viewHolder.warna = (MyTextView)convertView.findViewById(R.id.warna);

            convertView.setTag(viewHolder);


        }else {

            viewHolder = (ViewHolder)convertView.getTag();
        }

        final produk cart_item = (produk) getItem(position);

        String server = CommonUtilities.SERVER_URL;
        String url = server+"/uploads/produk/"+cart_item.getFoto1_produk();
        MainActivity.imageLoader.displayImage(url, viewHolder.image, MainActivity.imageOptionProduk);

        viewHolder.separator_ukuran.setVisibility(cart_item.getList_ukuran().length()==0?View.INVISIBLE:View.VISIBLE);
        viewHolder.ukuran.setVisibility(cart_item.getList_ukuran().length()==0?View.INVISIBLE:View.VISIBLE);
        viewHolder.ukuran.setText(cart_item.getUkuran());

        viewHolder.separator_warna.setVisibility(cart_item.getList_warna().length()==0?View.INVISIBLE:View.VISIBLE);
        viewHolder.warna.setVisibility(cart_item.getList_warna().length()==0?View.INVISIBLE:View.VISIBLE);
        viewHolder.warna.setText(cart_item.getWarna());

        viewHolder.title.setText(cart_item.getNama());
        viewHolder.price.setText(CommonUtilities.getCurrencyFormat(cart_item.getHarga_jual(), "Rp. ") + (cart_item.getPersen_diskon()>0?" (-"+cart_item.getPersen_diskon()+"%)":""));

        viewHolder.text.setText("Qty: "+cart_item.getQty());

        return convertView;
    }

    private class ViewHolder{
        ImageView image;
        MyTextView title;
        MyTextView price;
        MyTextView text;
        View separator_ukuran;
        MyTextView ukuran;
        View separator_warna;
        MyTextView warna;
    }
}




