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
import gomocart.application.com.gomocart.R;
import gomocart.application.com.model.moremenu;

import static gomocart.application.com.libs.CommonUtilities.getOptionsImage;
import static gomocart.application.com.libs.CommonUtilities.initImageLoader;


public class MenuaddingAdapter extends BaseAdapter {

    Context context;
    MainActivity mainActivity;
    ArrayList<moremenu> menuaddinglist;
    ImageLoader imageLoader;
    DisplayImageOptions imageOptionProduk;

    public MenuaddingAdapter(MainActivity mainActivity, ArrayList<moremenu> menuaddinglist) {
        this.mainActivity = mainActivity;
        this.menuaddinglist = menuaddinglist;
        this.context = mainActivity.context;

        initImageLoader(context);
        imageLoader         = ImageLoader.getInstance();
        imageOptionProduk   = getOptionsImage(R.drawable.logo_grayscale, R.drawable.logo_grayscale);
    }

    public void UpdateMenuaddingAdapter(ArrayList<moremenu> menuaddinglist) {
        this.menuaddinglist = menuaddinglist;
        notifyDataSetChanged();
    }
    
    @Override
    public int getCount() {
        return menuaddinglist.size();
    }

    @Override
    public Object getItem(int position) {
        return menuaddinglist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        ViewHolder viewHolder = null;

        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.menu_adding_item, null);

            viewHolder = new ViewHolder();
            viewHolder.image = (ImageView)convertView.findViewById(R.id.image_menu);
            viewHolder.title = (MyTextView)convertView.findViewById(R.id.text_menu);

            convertView.setTag(viewHolder);

        }else {

            viewHolder = (ViewHolder)convertView.getTag();
        }

        final moremenu moremenu_item = (moremenu) getItem(position);
        /*String server = CommonUtilities.SERVER_URL;
        String url = server+"/store/centercrop.php?url="+ server+"/uploads/produk/"+moremenu_item.getUrl_image()+"&width=300&height=300";
        imageLoader.displayImage(url, viewHolder.image, imageOptionProduk);*/

        viewHolder.title.setText(moremenu_item.getNama());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View convertView) {
                mainActivity.loadDetailMenu(moremenu_item);
            }
        });

        return convertView;
    }

    private class ViewHolder {
        ImageView image;
        MyTextView title;
    }
}




