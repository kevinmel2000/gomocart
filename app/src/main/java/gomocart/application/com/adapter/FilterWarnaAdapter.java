package gomocart.application.com.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import gomocart.application.com.gomocart.FilterProdukActivity;
import gomocart.application.com.gomocart.R;
import gomocart.application.com.model.ukuran;
import gomocart.application.com.model.warna;


public class FilterWarnaAdapter extends BaseAdapter {

    ArrayList<warna> warnalist;

    public FilterWarnaAdapter(ArrayList<warna> warnalist) {
        this.warnalist = warnalist;
    }

    public void UpdateFilterWarnaAdapter(ArrayList<warna> warnalist) {
        this.warnalist = warnalist;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return warnalist.size();
    }

    @Override
    public Object getItem(int position) {
        return warnalist.get(position);
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
            convertView = layoutInflater.inflate(R.layout.filter_listview_warna,null);

            viewHolder = new ViewHolder();
            viewHolder.title = (TextView)convertView.findViewById(R.id.title);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);

            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        warna data = (warna)getItem(position);
        viewHolder.position = warnalist.indexOf(data);
        viewHolder.title.setText(data.getWarna());
        viewHolder.checkBox.setChecked(data.getChecked());
        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                boolean isChecked = ((CheckBox)v).isChecked();
                //System.out.println(isChecked?"CHECKED":"UNCHECKED");
                warnalist.get(viewHolder.position).setChecked(isChecked);
                notifyDataSetChanged();

                if(isChecked) {
                    FilterProdukActivity.warna_select.add(warnalist.get(viewHolder.position));
                } else {
                    for (warna data : FilterProdukActivity.warna_select) {
                        if(data.getId()==warnalist.get(viewHolder.position).getId()) {
                            FilterProdukActivity.warna_select.remove(data);

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




