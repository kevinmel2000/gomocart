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

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import customfonts.MyTextView;
import gomocart.application.com.libs.CommonUtilities;
import gomocart.application.com.gomocart.MainActivity;
import gomocart.application.com.gomocart.R;
import gomocart.application.com.libs.DatabaseHandler;
import gomocart.application.com.model.produk;

import static gomocart.application.com.libs.CommonUtilities.getOptionsImage;
import static gomocart.application.com.libs.CommonUtilities.initImageLoader;


public class WishlistAdapter extends BaseAdapter {

    Context context;
    ArrayList<produk> wishlist;

    public WishlistAdapter(Context context, ArrayList<produk> wishlist) {
        this.context = context;
        this.wishlist = wishlist;

    }

    public void UpdateWishlistAdapter(ArrayList<produk> wishlist) {
        this.wishlist = wishlist;
        notifyDataSetChanged();
    }
    
    @Override
    public int getCount() {
        return wishlist.size();
    }

    @Override
    public Object getItem(int position) {
        return wishlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.wish_list,null);

            viewHolder = new ViewHolder();
            viewHolder.image = (ImageView)convertView.findViewById(R.id.image);
            viewHolder.title = (MyTextView)convertView.findViewById(R.id.title);
            viewHolder.price = (MyTextView)convertView.findViewById(R.id.price);
            viewHolder.hapus = (ImageView)convertView.findViewById(R.id.hapus);

            convertView.setTag(viewHolder);
        }else {

            viewHolder = (ViewHolder)convertView.getTag();
        }

        final produk wish_item = (produk) getItem(position);

        String server = CommonUtilities.SERVER_URL;
        String url = server+"/uploads/produk/"+wish_item.getFoto1_produk();
        MainActivity.imageLoader.displayImage(url, viewHolder.image, MainActivity.imageOptionProduk);

        viewHolder.title.setText(wish_item.getNama());
        viewHolder.price.setText(CommonUtilities.getCurrencyFormat(wish_item.getHarga_jual(),"Rp. ") + (wish_item.getPersen_diskon()>0?" (-"+wish_item.getPersen_diskon()+"%) ":""));

        viewHolder.hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) parent.getContext()).deleteSelectedWistlist(wish_item);
            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View convertView) {
                ((MainActivity) parent.getContext()).openDetailProduk(wish_item);
            }
        });

        return convertView;
    }

    private class ViewHolder {
        ImageView image;
        MyTextView title;
        MyTextView price;
        ImageView hapus;
    }
}




