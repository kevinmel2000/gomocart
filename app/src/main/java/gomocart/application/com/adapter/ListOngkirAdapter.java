package gomocart.application.com.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

import customfonts.MyTextView;
import gomocart.application.com.libs.CommonUtilities;
import gomocart.application.com.gomocart.MainActivity;
import gomocart.application.com.gomocart.R;
import gomocart.application.com.libs.ResizableImageView;
import gomocart.application.com.model.ongkir;

public class ListOngkirAdapter extends BaseAdapter {

    ArrayList<ongkir> listDataOngkir = new ArrayList<>();
    Context context;

    public ListOngkirAdapter(Context context, ArrayList<ongkir> listDataOngkir) {
        this.context = context;
        this.listDataOngkir = listDataOngkir;
    }

    public void UpdateListOngkirAdapter(ArrayList<ongkir> listDataOngkir) {
        this.listDataOngkir = listDataOngkir;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listDataOngkir.size();
    }

    @Override
    public Object getItem(int position) {
        return listDataOngkir.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public ImageView image;
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
            convertView = inflator.inflate(R.layout.ongkir_item_list, null);

            view.image = (ImageView) convertView.findViewById(R.id.image);
            view.title = (MyTextView) convertView.findViewById(R.id.title);
            
            convertView.setTag(view);
        } else {
            view = (ViewHolder) convertView.getTag();
        }

        final ongkir prod = listDataOngkir.get(position);
        view.position = listDataOngkir.indexOf(prod);

        String server = CommonUtilities.SERVER_URL;
        String url = server+"/uploads/ekspedisi/"+listDataOngkir.get(position).getGambar();
        MainActivity.imageLoader.displayImage(url, view.image, MainActivity.imageOptionOngkir);

        view.title.setText(listDataOngkir.get(position).getNama_kurir()+"\n"+listDataOngkir.get(position).getNama_service()+(listDataOngkir.get(position).getEtd().trim().length()>0?" ("+listDataOngkir.get(position).getEtd().trim()+" hari)":"") +"\n"+listDataOngkir.get(position).getTarif());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View convertView) {

            }
        });

        return convertView;
    }
}
