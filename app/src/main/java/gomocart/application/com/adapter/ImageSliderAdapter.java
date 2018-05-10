package gomocart.application.com.adapter;

import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import gomocart.application.com.gomocart.DetailProdukActivity;
import gomocart.application.com.gomocart.MainActivity;
import gomocart.application.com.gomocart.R;
import gomocart.application.com.libs.CommonUtilities;

public class ImageSliderAdapter extends PagerAdapter{
     
    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        // TODO Auto-generated method stub
         
        LayoutInflater inflater =  LayoutInflater.from(container.getContext());	
        
        View viewItem =  inflater.inflate(R.layout.layout_fullscreen_image, null);
        final ImageView imageView = (ImageView) viewItem.findViewById(R.id.imgDisplay);
        System.out.println(CommonUtilities.SERVER_URL + "/uploads/produk/" + DetailProdukActivity.list_gambar.get(position));
        MainActivity.imageLoader.loadImage(CommonUtilities.SERVER_URL + "/uploads/produk/" + DetailProdukActivity.list_gambar.get(position), MainActivity.imageOptionProduk, new SimpleImageLoadingListener() {
			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				super.onLoadingComplete(imageUri, view, loadedImage);
				imageView.setImageBitmap(loadedImage);		
			}
		});
        
        /*imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                 return DetailProdukActivity.mDetector.onTouchEvent(event);
            }
        });*/
        
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				//DetailProdukActivity.showDialogSaveAs();
				
				return false;
			}
		});
        
        ((ViewPager)container).addView(viewItem);
         
        return viewItem;
    }
 
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 0; //DetailProdukActivity.listGambarProduk.size();
    }
 
    @Override
    public boolean isViewFromObject(View view, Object object) {
        // TODO Auto-generated method stub
         
        return view == ((View)object);
    }
 
 
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub
        ((ViewPager) container).removeView((View) object);
    }
 
}