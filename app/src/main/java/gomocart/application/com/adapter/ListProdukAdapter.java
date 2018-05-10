package gomocart.application.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import customfonts.MyTextView;
import gomocart.application.com.libs.CommonUtilities;
import gomocart.application.com.gomocart.MainActivity;
import gomocart.application.com.gomocart.R;
import gomocart.application.com.libs.DatabaseHandler;
import gomocart.application.com.libs.SmallBang;
import gomocart.application.com.libs.SmallBangListener;
import gomocart.application.com.model.produk;


public class ListProdukAdapter extends BaseAdapter {

    Context context;

    ArrayList<produk> listProduk;
    //Typeface fonts;
    //RatingBar ratingbar;

    public ListProdukAdapter(Context context, ArrayList<produk> listProduk) {
        this.context = context;
        this.listProduk = listProduk;
    }

    public void UpdateListProdukAdapter(ArrayList<produk> listProduk) {
        this.listProduk = listProduk;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listProduk.size();
    }

    @Override
    public Object getItem(int position) {
        return listProduk.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {

        //fonts = Typeface.createFromAsset(context.getAssets(), "fonts/MuseoSans-500.otf");

        final ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_vertical_productlist, null);

            viewHolder = new ViewHolder();

            viewHolder.image = (ImageView) convertView.findViewById(R.id.image);
            viewHolder.title = (MyTextView) convertView.findViewById(R.id.title);
            viewHolder.price = (MyTextView) convertView.findViewById(R.id.price);
            viewHolder.cutprice = (MyTextView) convertView.findViewById(R.id.cutprice);
            viewHolder.discount = (MyTextView) convertView.findViewById(R.id.discount);

            viewHolder.linearSoldout = (LinearLayout) convertView.findViewById(R.id.linear_soldout);
            viewHolder.linearNew = (LinearLayout) convertView.findViewById(R.id.linear_new);
            viewHolder.linearGrosir = (LinearLayout) convertView.findViewById(R.id.linear_grosir);
            viewHolder.linearPreorder = (LinearLayout) convertView.findViewById(R.id.linear_preorder);
            viewHolder.linearDiscount = (LinearLayout) convertView.findViewById(R.id.linear_discount);
            viewHolder.linearFreeongkir = (LinearLayout) convertView.findViewById(R.id.linear_freeongkir);

            //viewHolder.ratingtext = (MyTextView) convertView.findViewById(R.id.ratingtext);
            //viewHolder.ratingbar = (RatingBar) convertView.findViewById(R.id.ratingbar);
            viewHolder.fav1 = (ImageView) convertView.findViewById(R.id.fav1);
            viewHolder.fav2 = (ImageView) convertView.findViewById(R.id.fav2);

            //viewHolder.title.setTypeface(fonts);
            //viewHolder.price.setTypeface(fonts);
            //viewHolder.cutprice.setTypeface(fonts);
            //viewHolder.discount.setTypeface(fonts);
            //viewHolder.ratingtext.setTypeface(fonts1);


//        ***********ratingBar**********
            //ratingbar = (RatingBar) convertView.findViewById(R.id.ratingbar);
            //LayerDrawable stars = (LayerDrawable) ratingbar.getProgressDrawable();
            //stars.getDrawable(2).setColorFilter(Color.parseColor("#f8d64e"), PorterDuff.Mode.SRC_ATOP);

            convertView.setTag(viewHolder);

            viewHolder.cutprice.setPaintFlags(viewHolder.cutprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }


        final produk prod = (produk) getItem(position);
        viewHolder.position = position;

        String server = CommonUtilities.SERVER_URL;
        String url = server+"/uploads/produk/"+prod.getFoto1_produk();
        //String url = server+"/store/centercrop.php?url="+ server+"/uploads/produk/"+prod.getFoto1_produk()+"&width=300&height=300";
        MainActivity.imageLoader.displayImage(url, viewHolder.image, MainActivity.imageOptionProduk);

        viewHolder.title.setText(prod.getNama());
        viewHolder.price.setText(CommonUtilities.getCurrencyFormat(prod.getHarga_diskon()>0?prod.getHarga_diskon():prod.getHarga_jual(), "Rp. "));
        viewHolder.cutprice.setText(prod.getHarga_diskon()>0?CommonUtilities.getCurrencyFormat(prod.getHarga_jual(), "Rp. "):"");

        viewHolder.linearSoldout.setVisibility(prod.getProduk_soldout()==1?View.VISIBLE:View.GONE);
        viewHolder.linearNew.setVisibility(prod.getProduk_terbaru()==1?View.VISIBLE:View.GONE);
        viewHolder.linearGrosir.setVisibility(prod.getProduk_grosir()==1?View.VISIBLE:View.GONE);
        viewHolder.linearPreorder.setVisibility(prod.getProduk_preorder()==1?View.VISIBLE:View.GONE);
        viewHolder.linearDiscount.setVisibility(prod.getPersen_diskon()>0?View.VISIBLE:View.GONE);
        viewHolder.linearFreeongkir.setVisibility(prod.getProduk_freeongkir()==1?View.VISIBLE:View.GONE);

        viewHolder.discount.setText(prod.getPersen_diskon()+"%");
        //viewHolder.ratingtext.setText("("+CommonUtilities.getNumberFormat(Double.parseDouble(prod.getTotal_responden()+""))+")");
        //viewHolder.ratingbar.setRating(prod.getRating());

        viewHolder.mSmallBang = SmallBang.attach2Window((MainActivity) context);

        viewHolder.fav1.setVisibility(prod.getIs_wishlist()?View.GONE:View.VISIBLE);
        viewHolder.fav2.setVisibility(prod.getIs_wishlist()?View.VISIBLE:View.GONE);

        viewHolder.fav1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatabaseHandler(parent.getContext()).insertWishlist(prod);
                prod.setIs_wishlist(true);
                //notifyDataSetChanged();

                ((MainActivity) parent.getContext()).updateWishlistList(viewHolder.position, true);
                viewHolder.fav2.setVisibility(View.VISIBLE);
                viewHolder.fav1.setVisibility(View.GONE);
                like(v);

            }

            public void like(View view){
                viewHolder.fav2.setImageResource(R.drawable.f4);
                viewHolder.mSmallBang.bang(view);
                viewHolder.mSmallBang.setmListener(new SmallBangListener() {
                    @Override
                    public void onAnimationStart() {

                    }

                    @Override
                    public void onAnimationEnd() {

                    }


                });
            }
        });

        viewHolder.fav2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatabaseHandler(parent.getContext()).deleteWishlist(prod);
                prod.setIs_wishlist(false);
                //notifyDataSetChanged();

                ((MainActivity) parent.getContext()).updateWishlistList(viewHolder.position, false);
                viewHolder.fav2.setVisibility(View.GONE);
                viewHolder.fav1.setVisibility(View.VISIBLE);
            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View convertView) {
                ((MainActivity) parent.getContext()).openDetailProduk(prod);
            }
        });

        return convertView;

    }

    private class ViewHolder {
        ImageView image;
        MyTextView title;
        MyTextView price;
        MyTextView cutprice;

        LinearLayout linearNew;
        LinearLayout linearDiscount;
        LinearLayout linearPreorder;

        LinearLayout linearSoldout;
        LinearLayout linearGrosir;
        LinearLayout linearFreeongkir;

        MyTextView discount;
        //RatingBar ratingbar;
        //MyTextView ratingtext;
        ImageView fav1;
        ImageView fav2;
        SmallBang mSmallBang;
        int position;
    }
}







