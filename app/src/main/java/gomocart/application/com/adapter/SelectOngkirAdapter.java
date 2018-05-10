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
import gomocart.application.com.fragment.ProsesCheckoutKurirPengirimanFragment;
import gomocart.application.com.gomocart.ProsesCheckoutActivity;
import gomocart.application.com.gomocart.MainActivity;
import gomocart.application.com.gomocart.R;
import gomocart.application.com.libs.CommonUtilities;
import gomocart.application.com.model.ongkir;

public class SelectOngkirAdapter extends BaseAdapter {

    ArrayList<ongkir> listDataOngkir = new ArrayList<>();
    Context context;

    public SelectOngkirAdapter(Context context, ArrayList<ongkir> listDataOngkir) {
        this.context = context;
        this.listDataOngkir = listDataOngkir;
    }

    public void UpdateSelectOngkirAdapter(ArrayList<ongkir> listDataOngkir) {
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
        public ImageView radioSelect;
        public int position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {

        final ViewHolder view;
        LayoutInflater inflator =  LayoutInflater.from(parent.getContext());
        if(convertView==null) {
            view = new ViewHolder();
            convertView = inflator.inflate(R.layout.select_ongkir_item_list, null);

            view.image = (ImageView) convertView.findViewById(R.id.image);
            view.title = (MyTextView) convertView.findViewById(R.id.title);
            view.radioSelect = (ImageView) convertView.findViewById(R.id.radioSelect);

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
        view.radioSelect.setImageResource(prod.getIs_selected()?R.drawable.radioblack:R.drawable.radiouncheked);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View convertView) {
                int kurir_id = ((ProsesCheckoutActivity) parent.getContext()).getKurir_index();
                if(kurir_id>-1 && kurir_id<listDataOngkir.size()) {
                    listDataOngkir.get(kurir_id).setIs_selected(false);
                    View v = ProsesCheckoutKurirPengirimanFragment.listViewOngkir.getChildAt(kurir_id);
                    if(v == null) return;
                    ImageView radioS = (ImageView) v.findViewById(R.id.radioSelect);
                    radioS.setImageResource(R.drawable.radiouncheked);
                }

                ((ProsesCheckoutActivity) parent.getContext()).setKurirPengiriman(view.position, listDataOngkir.get(view.position));
                listDataOngkir.get(view.position).setIs_selected(true);
                View v = ProsesCheckoutKurirPengirimanFragment.listViewOngkir.getChildAt(view.position);
                if(v == null) return;
                ImageView radioS = (ImageView) v.findViewById(R.id.radioSelect);
                radioS.setImageResource(R.drawable.radioblack);
            }
        });

        return convertView;
    }
}
