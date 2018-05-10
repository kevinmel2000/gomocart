package gomocart.application.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import gomocart.application.com.gomocart.FilterProdukActivity;
import gomocart.application.com.gomocart.R;
import gomocart.application.com.model.brand;
import gomocart.application.com.model.ukuran;


public class FilterUkuranAdapter extends BaseAdapter {

    ArrayList<ukuran> ukuranlist;

    public FilterUkuranAdapter(ArrayList<ukuran> ukuranlist) {
        this.ukuranlist = ukuranlist;
    }

    public void UpdateFilterUkuranAdapter(ArrayList<ukuran> ukuranlist) {
        this.ukuranlist = ukuranlist;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return ukuranlist.size();
    }

    @Override
    public Object getItem(int position) {
        return ukuranlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) parent.getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.filter_listview_ukuran,null);

            viewHolder = new ViewHolder();
            viewHolder.title = (TextView)convertView.findViewById(R.id.title);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);

            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        ukuran data = (ukuran)getItem(position);
        viewHolder.position = ukuranlist.indexOf(data);
        viewHolder.title.setText(data.getUkuran());
        viewHolder.checkBox.setChecked(data.getChecked());
        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                boolean isChecked = ((CheckBox)v).isChecked();
                //System.out.println(isChecked?"CHECKED":"UNCHECKED");
                ukuranlist.get(viewHolder.position).setChecked(isChecked);
                notifyDataSetChanged();

                if(isChecked) {
                    FilterProdukActivity.ukuran_select.add(ukuranlist.get(viewHolder.position));
                } else {
                    for (ukuran data : FilterProdukActivity.ukuran_select) {
                        if(data.getId()==ukuranlist.get(viewHolder.position).getId()) {
                            FilterProdukActivity.ukuran_select.remove(data);

                            break;
                        }

                    }
                }
            }
        });

        return convertView;
    }

    private class ViewHolder{

        TextView title;
        CheckBox checkBox;
        int position;
    }
}




