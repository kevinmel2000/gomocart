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
import gomocart.application.com.libs.CommonUtilities;
import gomocart.application.com.gomocart.MainActivity;
import gomocart.application.com.gomocart.R;
import gomocart.application.com.libs.DatabaseHandler;
import gomocart.application.com.libs.ResizableImageView;
import gomocart.application.com.model.produk;


public class CartlistAdapter extends BaseAdapter {

    int number = 01;

    Context context;
    ArrayList<produk> cartlist;

    public CartlistAdapter(Context context, ArrayList<produk> cartlist) {
        this.context = context;
        this.cartlist = cartlist;
    }

    public void UpdateCartlistAdapter(ArrayList<produk> cartlist) {
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


        final ViewHolder viewHolder;

        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.cart_list,null);

            viewHolder = new ViewHolder();

            viewHolder.image = (ResizableImageView)convertView.findViewById(R.id.image);
            viewHolder.title = (MyTextView)convertView.findViewById(R.id.title);
            viewHolder.price = (MyTextView)convertView.findViewById(R.id.price);
            viewHolder.diskon = (MyTextView)convertView.findViewById(R.id.diskon);
            viewHolder.total = (MyTextView)convertView.findViewById(R.id.total);
            viewHolder.text = (MyTextView)convertView.findViewById(R.id.text);
            viewHolder.separator_ukuran = (View)convertView.findViewById(R.id.separator_ukuran);
            viewHolder.ukuran = (MyTextView)convertView.findViewById(R.id.ukuran);
            viewHolder.separator_warna = (View)convertView.findViewById(R.id.separator_warna);
            viewHolder.warna = (MyTextView)convertView.findViewById(R.id.warna);
            viewHolder.plus = (ImageView)convertView.findViewById(R.id.plus);
            viewHolder.min  = (ImageView)convertView.findViewById(R.id.min);
            viewHolder.hapus = (ImageView)convertView.findViewById(R.id.hapus);

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
        viewHolder.ukuran.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int index = cartlist.indexOf(cart_item);
                ((MainActivity) context).openDialogUkuran(cart_item.getList_stok(), index);
            }
        });

        viewHolder.separator_warna.setVisibility(cart_item.getList_warna().length()==0?View.INVISIBLE:View.VISIBLE);
        viewHolder.warna.setVisibility(cart_item.getList_warna().length()==0?View.INVISIBLE:View.VISIBLE);
        viewHolder.warna.setText(cart_item.getWarna());
        viewHolder.warna.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int index = cartlist.indexOf(cart_item);
                ((MainActivity) context).openDialogWarna(cart_item.getList_stok(), cart_item.getUkuran(), index);
            }
        });

        viewHolder.title.setText(cart_item.getNama());
        viewHolder.price.setText(CommonUtilities.getCurrencyFormat(cart_item.getHarga_jual(), "Rp. "));
        viewHolder.diskon.setText(cart_item.getPersen_diskon()>0?" (-"+cart_item.getPersen_diskon()+"%) "+CommonUtilities.getCurrencyFormat((cart_item.getPersen_diskon()*0.01)*cart_item.getHarga_jual(), "Rp. "):"");
        viewHolder.total.setText(CommonUtilities.getCurrencyFormat(cart_item.getGrandtotal(), "Rp. "));

        number = cart_item.getQty();
        viewHolder.text.setText(""+number);

        viewHolder.min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (number > 1) {
                    number = number - 1;
                    int index = MainActivity.cartlist.indexOf(cart_item);
                    MainActivity.cartlist.get(index).setQty(number);
                    MainActivity.cartlist.get(index).setGrandtotal(number*cart_item.getSubtotal());

                    MainActivity.cartlistAdapter.UpdateCartlistAdapter(MainActivity.cartlist);
                    new DatabaseHandler(parent.getContext()).updateCartlist(MainActivity.cartlist.get(index));

                    viewHolder.text.setText(""+number);
                    viewHolder.total.setText(CommonUtilities.getCurrencyFormat(cart_item.getGrandtotal(), "Rp. "));
                    ((MainActivity) context).updateTotalCartlist();
                    ((MainActivity) context).updateSummaryCart();
                }
            }
        });

        viewHolder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (number < cart_item.getMax_qty()) {
                    number = number + 1;

                    int index = MainActivity.cartlist.indexOf(cart_item);
                    MainActivity.cartlist.get(index).setQty(number);
                    MainActivity.cartlist.get(index).setGrandtotal(number*cart_item.getSubtotal());

                    MainActivity.cartlistAdapter.UpdateCartlistAdapter(MainActivity.cartlist);
                    new DatabaseHandler(parent.getContext()).updateCartlist(MainActivity.cartlist.get(index));

                    viewHolder.text.setText(""+number);
                    viewHolder.total.setText(CommonUtilities.getCurrencyFormat(cart_item.getGrandtotal(), "Rp. "));
                    ((MainActivity) context).updateTotalCartlist();
                    ((MainActivity) context).updateSummaryCart();
                }

            }
        });


        viewHolder.hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatabaseHandler(parent.getContext()).deleteCartlist(cart_item);

                int index = MainActivity.cartlist.indexOf(cart_item);
                MainActivity.cartlist.remove(index);
                MainActivity.cartlistAdapter.UpdateCartlistAdapter(MainActivity.cartlist);

                ((MainActivity) context).updateTotalCartlist();
                ((MainActivity) context).updateSummaryCart();
            }
        });


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View convertView) {
                ((MainActivity) parent.getContext()).openDetailProduk(cart_item);
            }
        });

        return convertView;
    }

    private class ViewHolder{
        ResizableImageView image;
        MyTextView title;
        MyTextView price;
        MyTextView diskon;
        MyTextView total;
        MyTextView text;
        View separator_ukuran;
        MyTextView ukuran;
        View separator_warna;
        MyTextView warna;
        ImageView plus;
        ImageView min;
        ImageView hapus;
    }
}




