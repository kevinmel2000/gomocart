package gomocart.application.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;

import java.util.ArrayList;

import customfonts.MyTextView;
import gomocart.application.com.gomocart.AlamatActivity;
import gomocart.application.com.gomocart.R;
import gomocart.application.com.model.alamat;


public class SelectAlamatAdapter extends BaseAdapter {

    Context context;
    ArrayList<alamat> alamatlist;

    public SelectAlamatAdapter(Context context, ArrayList<alamat> alamatlist) {
        this.context = context;
        this.alamatlist = alamatlist;
    }

    public void UpdateSelectAlamatAdapter(ArrayList<alamat> alamatlist) {
        this.alamatlist = alamatlist;
        notifyDataSetChanged();
    }
    
    @Override
    public int getCount() {
        return alamatlist.size();
    }

    @Override
    public Object getItem(int position) {
        return alamatlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.alamat_item_list, null);

            viewHolder = new ViewHolder();
            viewHolder.nama       = (MyTextView) convertView.findViewById(R.id.txtNamaAlamat);
            viewHolder.alamat     = (MyTextView) convertView.findViewById(R.id.txtDetailAlamat);
            viewHolder.utamakan   = (MyTextView) convertView.findViewById(R.id.utamakan);
            viewHolder.edit       = (MyTextView) convertView.findViewById(R.id.edit);
            viewHolder.hapus      = (MyTextView) convertView.findViewById(R.id.hapus);
            viewHolder.as_default = (CheckBox) convertView.findViewById(R.id.checkbox_as_default);

            convertView.setTag(viewHolder);

        }else {

            viewHolder = (ViewHolder)convertView.getTag();
        }

        final alamat alamat_item = (alamat) getItem(position);

        viewHolder.nama.setText(alamat_item.getNama());
        viewHolder.alamat.setText(alamat_item.getAlamat());
        viewHolder.as_default.setChecked(alamat_item.getAsDefaultAlamat());

        viewHolder.as_default.setChecked(alamat_item.getAsDefaultAlamat());

        viewHolder.as_default.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AlamatActivity) parent.getContext()).utamakanSelectedAlamat(alamat_item);
            }
        });

        viewHolder.utamakan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AlamatActivity) parent.getContext()).utamakanSelectedAlamat(alamat_item);
            }
        });

        viewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AlamatActivity) parent.getContext()).editSelectedAlamat(alamat_item);
            }
        });

        viewHolder.hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AlamatActivity) parent.getContext()).deleteSelectedAlamat(alamat_item);
            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View convertView) {
                ((AlamatActivity) parent.getContext()).selectedAlamat(alamat_item);
            }
        });

        return convertView;
    }

    private class ViewHolder {
        MyTextView nama, alamat;
        MyTextView utamakan, edit, hapus;
        CheckBox as_default;

    }
}




