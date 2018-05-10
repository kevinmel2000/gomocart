package gomocart.application.com.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.alexzh.circleimageview.CircleImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayList;

import customfonts.MyTextView;
import gomocart.application.com.libs.CommonUtilities;
import gomocart.application.com.gomocart.MainActivity;
import gomocart.application.com.gomocart.R;
import gomocart.application.com.model.kategori;

public class ListKategoriAdapter extends BaseAdapter {

    Context context;
    ArrayList<kategori> listDataKategori = new ArrayList<>();

    public ListKategoriAdapter(Context context, ArrayList<kategori> listDataKategori) {
        this.context = context;
        this.listDataKategori = listDataKategori;
    }

    public void UpdateListKategoriAdapter(ArrayList<kategori> listDataKategori) {
        this.listDataKategori = listDataKategori;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listDataKategori.size();
    }

    @Override
    public Object getItem(int position) {
        return listDataKategori.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public CircleImageView image;
        public MyTextView title;
        public int position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {

        final ViewHolder view;
        LayoutInflater inflator =  LayoutInflater.from(parent.getContext());
        if(convertView==null) {
            view = new ViewHolder();
            convertView = inflator.inflate(R.layout.kategori_item_list, null);

            view.image = (CircleImageView) convertView.findViewById(R.id.image);
            view.title = (MyTextView) convertView.findViewById(R.id.title);
            view.image.setLayerType(View.LAYER_TYPE_HARDWARE, null);

            convertView.setTag(view);
        } else {
            view = (ViewHolder) convertView.getTag();
        }

        final kategori prod = listDataKategori.get(position);
        view.position = listDataKategori.indexOf(prod);

        String server = CommonUtilities.SERVER_URL;
        String url = server+"/uploads/category/"+listDataKategori.get(position).getHeader();
        MainActivity.imageLoader.displayImage(url, view.image, MainActivity.imageOptionKategori);

        view.title.setText(listDataKategori.get(position).getNama());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View convertView) {
                ((MainActivity) context).openProdukKategori(prod);
            }
        });

        return convertView;
    }
}
