package gomocart.application.com.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import customfonts.MyTextView;
import gomocart.application.com.fragment.DashboardFragment;
import gomocart.application.com.libs.CommonUtilities;
import gomocart.application.com.gomocart.MainActivity;
import gomocart.application.com.gomocart.R;
import gomocart.application.com.libs.DatabaseHandler;
import gomocart.application.com.model.produk;
import gomocart.application.com.libs.SmallBang;
import gomocart.application.com.libs.SmallBangListener;

public class GridProdukAdapter extends BaseAdapter {

    Context context;
    DatabaseHandler dh;
    ArrayList<produk> listProduk;

    public GridProdukAdapter(Context context, DatabaseHandler dh, ArrayList<produk> listProduk) {
        this.context = context;
        this.dh = dh;
        this.listProduk = listProduk;
    }

    public void UpdateGridProdukAdapter(ArrayList<produk> listProduk) {
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

        final ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.grid_vertical_productlist, null);

            viewHolder = new ViewHolder();

            viewHolder.linear_utama = (LinearLayout) convertView.findViewById(R.id.linear_utama);

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

            //viewHolder.ratingtext.setTypeface(fonts1);


//        ***********ratingBar**********
            //ratingbar = (RatingBar) convertView.findViewById(R.id.ratingbar);
            //LayerDrawable stars = (LayerDrawable) ratingbar.getProgressDrawable();
            //stars.getDrawable(2).setColorFilter(Color.parseColor("#f8d64e"), PorterDuff.Mode.SRC_ATOP);

            convertView.setTag(viewHolder);

            viewHolder.cutprice.setPaintFlags(viewHolder.cutprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            if(MainActivity.image_produk_size_vertical>0) {
                ViewGroup.LayoutParams params = viewHolder.image.getLayoutParams();
                params.height = MainActivity.image_produk_size_vertical;
                params.width = MainActivity.image_produk_size_vertical;
                viewHolder.image.setLayoutParams(params);
            }
        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }

        final produk prod = (produk) getItem(position);
        viewHolder.position = position;

        String server = CommonUtilities.SERVER_URL;
        final String url = server+"/uploads/produk/"+prod.getFoto1_produk();
        //String url = server+"/store/centercrop.php?url="+ server+"/uploads/produk/"+prod.getFoto1_produk()+"&width=300&height=300";

        if(MainActivity.image_produk_size_vertical==0) {
            ViewTreeObserver viewTreeObserver = viewHolder.image.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @SuppressLint("NewApi")
                    @Override
                    public void onGlobalLayout() {
                        viewHolder.image.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        MainActivity.image_produk_size_vertical = viewHolder.image.getMeasuredWidth();
                        ViewGroup.LayoutParams params = viewHolder.image.getLayoutParams();
                        params.height = MainActivity.image_produk_size_vertical;
                        params.width = MainActivity.image_produk_size_vertical;
                        viewHolder.image.setLayoutParams(params);
                        MainActivity.imageLoader.displayImage(url, viewHolder.image, MainActivity.imageOptionProduk);
                    }
                });
            }
        } else {
            MainActivity.imageLoader.displayImage(url, viewHolder.image, MainActivity.imageOptionProduk);
        }

        viewHolder.title.setText(prod.getNama());
        viewHolder.price.setText(CommonUtilities.getCurrencyFormat(prod.getHarga_diskon()>0?prod.getHarga_diskon():prod.getHarga_jual(), "Rp. "));
        viewHolder.cutprice.setText(prod.getHarga_diskon()>0?CommonUtilities.getCurrencyFormat(prod.getHarga_jual(), "Rp. "):"");

        //viewHolder.price.setTextSize(12);
        //viewHolder.cutprice.setTextSize(12);

        viewHolder.linearSoldout.setVisibility(prod.getProduk_soldout()==1?View.VISIBLE:View.GONE);
        viewHolder.linearNew.setVisibility(prod.getProduk_terbaru()==1?View.VISIBLE:View.GONE);
        viewHolder.linearGrosir.setVisibility(prod.getProduk_grosir()==1?View.VISIBLE:View.GONE);
        viewHolder.linearPreorder.setVisibility(prod.getProduk_preorder()==1?View.VISIBLE:View.GONE);
        viewHolder.linearDiscount.setVisibility(prod.getPersen_diskon()>0?View.VISIBLE:View.GONE);
        viewHolder.linearFreeongkir.setVisibility(prod.getProduk_freeongkir()>0?View.VISIBLE:View.GONE);

        viewHolder.discount.setText(prod.getPersen_diskon()+"%");
        //viewHolder.ratingtext.setText("("+CommonUtilities.getNumberFormat(Double.parseDouble(prod.getTotal_responden()+""))+")");
        //viewHolder.ratingbar.setRating(prod.getRating());

        viewHolder.mSmallBang = SmallBang.attach2Window((MainActivity) context);

        viewHolder.fav1.setVisibility(prod.getIs_wishlist()?View.GONE:View.VISIBLE);
        viewHolder.fav2.setVisibility(prod.getIs_wishlist()?View.VISIBLE:View.GONE);

        viewHolder.fav1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dh.insertWishlist(prod);
                prod.setIs_wishlist(true);
                //notifyDataSetChanged();

                ((MainActivity) parent.getContext()).updateWishlistGrid(viewHolder.position, true, prod);
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

                dh.deleteWishlist(prod);
                prod.setIs_wishlist(false);
                //notifyDataSetChanged();

                ((MainActivity) parent.getContext()).updateWishlistGrid(viewHolder.position, false, prod);
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

        LinearLayout linear_utama;
        ImageView image;
        MyTextView title;
        MyTextView price;
        MyTextView cutprice;
        MyTextView discount;

        LinearLayout linearNew;
        LinearLayout linearGrosir;
        LinearLayout linearPreorder;
        LinearLayout linearDiscount;
        LinearLayout linearSoldout;
        LinearLayout linearFreeongkir;

        //RatingBar ratingbar;
        //MyTextView ratingtext;
        ImageView fav1;
        ImageView fav2;
        SmallBang mSmallBang;
        int position;
    }
}







