package gomocart.application.com.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import gomocart.application.com.adapter.GridProdukAdapter;
import gomocart.application.com.gomocart.DetailSlideGalleryActivity;
import gomocart.application.com.gomocart.MainActivity;
import gomocart.application.com.gomocart.R;
import gomocart.application.com.libs.CommonUtilities;
import gomocart.application.com.libs.DatabaseHandler;
import gomocart.application.com.libs.JSONParser;
import gomocart.application.com.libs.TouchImageView;
import gomocart.application.com.model.gallery;
import gomocart.application.com.model.produk;

public class GalleryFragment extends Fragment {

    Context context;
    gallery data_gallery;
    TouchImageView touchImageView;

    ImageLoader imageLoader;
    DisplayImageOptions imageOptionsGallery;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.list_image_gallery, container, false);

        touchImageView = (TouchImageView) rootView.findViewById(R.id.imgDisplay);

        return rootView;
    }

    public void setDataGallery(Context context, gallery data_gallery) {
        this.context = context;
        this.data_gallery = data_gallery;

        CommonUtilities.initImageLoader(context);

        imageLoader = ImageLoader.getInstance();
        imageOptionsGallery = CommonUtilities.getOptionsImage(R.drawable.logo_grayscale, R.drawable.logo_grayscale);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        imageLoader.loadImage(CommonUtilities.SERVER_URL + "/uploads/produk/" + data_gallery.getGambar(), imageOptionsGallery, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                touchImageView.setImageBitmap(loadedImage);
            }
        });
    }
}
