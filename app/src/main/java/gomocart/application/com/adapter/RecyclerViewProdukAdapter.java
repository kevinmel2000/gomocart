package gomocart.application.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import customfonts.MyTextView;
import gomocart.application.com.gomocart.MainActivity;
import gomocart.application.com.libs.CommonUtilities;
import gomocart.application.com.gomocart.R;
import gomocart.application.com.libs.DatabaseHandler;
import gomocart.application.com.model.produk;
import gomocart.application.com.libs.SmallBang;
import gomocart.application.com.libs.SmallBangListener;

public class RecyclerViewProdukAdapter extends RecyclerView.Adapter<RecyclerViewProdukAdapter.DataObjectHolder> {

	Context context;
	DatabaseHandler dh;
	ArrayList<produk> produklist;

	public RecyclerViewProdukAdapter(Context context, DatabaseHandler dh, ArrayList<produk> produklist) {
		this.context = context;
		this.produklist = produklist;
		this.dh = dh;
	}

	public class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		ImageView image;
		MyTextView title;
		MyTextView price;
		MyTextView cutprice;
		LinearLayout linearNew;
		LinearLayout linearDiscount;
		LinearLayout linearPreorder;
		LinearLayout linearSoldout;
		LinearLayout linearGrosir;
		LinearLayout linearFreeOngkir;
		MyTextView discount;
		//RatingBar ratingbar;
		//MyTextView ratingtext;
		ImageView fav1;
		ImageView fav2;
		SmallBang mSmallBang;

		public DataObjectHolder(View convertView) {
			super(convertView);

			image = (ImageView) convertView.findViewById(R.id.image);
			title = (MyTextView) convertView.findViewById(R.id.title);
			price = (MyTextView) convertView.findViewById(R.id.price);
			cutprice = (MyTextView) convertView.findViewById(R.id.cutprice);
			discount = (MyTextView) convertView.findViewById(R.id.discount);
			linearNew = (LinearLayout) convertView.findViewById(R.id.linear_new);
			linearDiscount = (LinearLayout) convertView.findViewById(R.id.linear_discount);
			linearPreorder = (LinearLayout) convertView.findViewById(R.id.linear_preorder);
			linearSoldout = (LinearLayout) convertView.findViewById(R.id.linear_soldout);
			linearGrosir = (LinearLayout) convertView.findViewById(R.id.linear_grosir);
			linearFreeOngkir = (LinearLayout) convertView.findViewById(R.id.linear_freeongkir);
			//ratingbar = (RatingBar) convertView.findViewById(R.id.ratingbar);
			//ratingtext = (MyTextView) convertView.findViewById(R.id.ratingtext);
			fav1 = (ImageView) convertView.findViewById(R.id.fav1);
			fav2 = (ImageView) convertView.findViewById(R.id.fav2);

			//LayerDrawable stars = (LayerDrawable) ratingbar.getProgressDrawable();
			//stars.getDrawable(2).setColorFilter(Color.parseColor("#f8d64e"), PorterDuff.Mode.SRC_ATOP);
			cutprice.setPaintFlags(cutprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

			if(MainActivity.image_produk_size_horizontal>0) {
				ViewGroup.LayoutParams params = image.getLayoutParams();
				params.height = MainActivity.image_produk_size_horizontal;
				params.width = MainActivity.image_produk_size_horizontal;
				image.setLayoutParams(params);
			}

			itemView.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {
			Intent i = new Intent("gomocart.application.com.gomocart.TERKAIT_OPEN_DETAIL_PRODUK");
			i.putExtra("produk", produklist.get(getAdapterPosition()));
			context.sendBroadcast(i);
		}
	}

	@Override
	public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_horizontal_productlist, parent, false);

		DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
		return dataObjectHolder;
	}

	@Override
	public void onBindViewHolder(final DataObjectHolder holder, final int position) {

		final produk prod = produklist.get(position);

		String server = CommonUtilities.SERVER_URL;
		final String url = server+"/uploads/produk/"+prod.getFoto1_produk();
		//String url = server+"/store/centercrop.php?url="+ server+"/uploads/produk/"+prod.getFoto1_produk()+"&width=300&height=300";

		if(MainActivity.image_produk_size_horizontal==0) {
			holder.image.post(new Runnable() {
				@Override
				public void run() {
					MainActivity.image_produk_size_horizontal = holder.image.getMeasuredWidth();
                    //Toast.makeText(context, "SIZE HORIZONTAL: "+MainActivity.image_produk_size_horizontal, Toast.LENGTH_SHORT).show();
                    //Log.e("SIZE HORIZONTAL", ""+MainActivity.image_produk_size_horizontal);
					ViewGroup.LayoutParams params = holder.image.getLayoutParams();
					params.height = MainActivity.image_produk_size_horizontal;
					params.width = MainActivity.image_produk_size_horizontal;
					holder.image.setLayoutParams(params);
					MainActivity.imageLoader.displayImage(url, holder.image, MainActivity.imageOptionProduk);
				}
			});
		} else {
			MainActivity.imageLoader.displayImage(url, holder.image, MainActivity.imageOptionProduk);
		}

		holder.title.setText(prod.getNama());
		holder.price.setText(CommonUtilities.getCurrencyFormat(prod.getHarga_diskon()>0?prod.getHarga_diskon():prod.getHarga_jual(), "Rp. "));
		holder.cutprice.setText(prod.getHarga_diskon()>0?CommonUtilities.getCurrencyFormat(prod.getHarga_jual(), "Rp. "):"");

		holder.linearSoldout.setVisibility(prod.getProduk_soldout()==1?View.VISIBLE:View.GONE);
		holder.linearNew.setVisibility(prod.getProduk_terbaru()==1?View.VISIBLE:View.GONE);
		holder.linearGrosir.setVisibility(prod.getProduk_grosir()==1?View.VISIBLE:View.GONE);
		holder.linearPreorder.setVisibility(prod.getProduk_preorder()==1?View.VISIBLE:View.GONE);
		holder.linearDiscount.setVisibility(prod.getPersen_diskon()>0?View.VISIBLE:View.GONE);
		holder.linearFreeOngkir.setVisibility(prod.getProduk_freeongkir()==1?View.VISIBLE:View.GONE);

		holder.discount.setText(prod.getPersen_diskon()+"%");
		//holder.ratingtext.setText("("+CommonUtilities.getNumberFormat(Double.parseDouble(prod.getTotal_responden()+""))+")");
		//holder.ratingbar.setRating(prod.getRating());

		holder.mSmallBang = SmallBang.attach2Window((Activity) context);

		holder.fav1.setVisibility(prod.getIs_wishlist()?View.GONE:View.VISIBLE);
		holder.fav2.setVisibility(prod.getIs_wishlist()?View.VISIBLE:View.GONE);

		holder.fav1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dh.insertWishlist(prod);
				Intent intent = new Intent("gomocart.application.com.gomocart.UPDATE_WISHLIST");
				intent.putExtra("produk", prod);
				context.sendBroadcast(intent);

				holder.fav2.setVisibility(View.VISIBLE);
				holder.fav1.setVisibility(View.GONE);
				like(v);
			}

			public void like(View view){
				holder.fav2.setImageResource(R.drawable.f4);
				holder.mSmallBang.bang(view);
				holder.mSmallBang.setmListener(new SmallBangListener() {
					@Override
					public void onAnimationStart() {

					}

					@Override
					public void onAnimationEnd() {

					}
				});
			}
		});

		holder.fav2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dh.deleteWishlist(prod);
				Intent intent = new Intent("gomocart.application.com.gomocart.UPDATE_WISHLIST");
				intent.putExtra("produk", prod);
				context.sendBroadcast(intent);

				holder.fav2.setVisibility(View.GONE);
				holder.fav1.setVisibility(View.VISIBLE);
			}
		});
	}

	@Override
	public int getItemCount() {
		return produklist.size();
	}
}
