package gomocart.application.com.gomocart;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import customfonts.MyTextView;
import gomocart.application.com.libs.CommonUtilities;
import gomocart.application.com.model.informasi;

public class DetailInformasiActivity extends AppCompatActivity {

    Context context;
    
    ImageView gambar;
    MyTextView title, tanggal, konten;
    informasi data;
    
    ImageLoader imageLoader;
	ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_informasi);

        context = DetailInformasiActivity.this;
        
        CommonUtilities.initImageLoader(context);
		imageLoader = ImageLoader.getInstance();

        gambar  = (ImageView) findViewById(R.id.gambar);
        title   = (MyTextView) findViewById(R.id.txtTitle);
        tanggal = (MyTextView) findViewById(R.id.txtTanggal);
        konten  = (MyTextView) findViewById(R.id.txtKonten);
        
        gambar.setVisibility(View.GONE);
        
        if(savedInstanceState==null) {
        	data = (informasi) getIntent().getSerializableExtra("informasi");
        }

		back = (ImageView) findViewById(R.id.back);

		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
    }

    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	
    	title.setText(data.getJudul());
    	tanggal.setText(data.getTanggal());
    	konten.setText(data.getKonten());
    	
    	if(data.getGambar().length()>0) {
    		imageLoader.loadImage(CommonUtilities.SERVER_URL+"/uploads/informasi/"+data.getGambar(), new ImageLoadingListener() {
    			
    			@Override
    			public void onLoadingStarted(String imageUri, View view) {
    				// TODO Auto-generated method stub
    				
    			}
    			
    			@Override
    			public void onLoadingFailed(String imageUri, View view,
    					FailReason failReason) {
    				// TODO Auto-generated method stub
    				
    			}
    			
    			@Override
    			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
    				// TODO Auto-generated method stub
    				gambar.setImageBitmap(loadedImage);
    				gambar.setVisibility(View.VISIBLE);
    			}
    			
    			@Override
    			public void onLoadingCancelled(String imageUri, View view) {
    				// TODO Auto-generated method stub
    				
    			}
    		});
    	}    	
    }
    
    @Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
	    super.onSaveInstanceState(savedInstanceState);
	    
	    savedInstanceState.putSerializable("informasi", data);    
    }

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
	    super.onRestoreInstanceState(savedInstanceState);

	    data = (informasi) savedInstanceState.getSerializable("informasi");
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			setResult(RESULT_OK, new Intent());
			finish();         
            
			return false;
		}
		
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.menu_detail, menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {

		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			setResult(RESULT_OK, new Intent());
			finish();            
            
		    return true;
        default:
            return super.onOptionsItemSelected(item);
		}
	}
	
	
}
