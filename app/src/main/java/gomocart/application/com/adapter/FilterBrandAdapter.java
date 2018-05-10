package gomocart.application.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import gomocart.application.com.gomocart.FilterProdukActivity;
import gomocart.application.com.gomocart.R;
import gomocart.application.com.model.brand;


public class FilterBrandAdapter extends BaseAdapter {

    ArrayList<brand> brandlist;

    public FilterBrandAdapter(ArrayList<brand> brandlist) {
        this.brandlist = brandlist;
    }

    public void UpdateFilterBrandAdapter(ArrayList<brand> brandlist) {
        this.brandlist = brandlist;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return brandlist.size();
    }

    @Override
    public Object getItem(int position) {
        return brandlist.get(position);
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
            convertView = layoutInflater.inflate(R.layout.filter_listview_brand,null);

            viewHolder = new ViewHolder();
            viewHolder.title = (TextView)convertView.findViewById(R.id.title);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);

            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        brand data = (brand)getItem(position);
        viewHolder.position = brandlist.indexOf(data);
        viewHolder.title.setText(data.getNama());
        viewHolder.checkBox.setChecked(data.getChecked());

        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                boolean isChecked = ((CheckBox)v).isChecked();
                //System.out.println(isChecked?"CHECKED":"UNCHECKED");
                brandlist.get(viewHolder.position).setChecked(isChecked);
                notifyDataSetChanged();

                if(isChecked) {
                    FilterProdukActivity.brand_select.add(brandlist.get(viewHolder.position));
                } else {
                    for (brand data : FilterProdukActivity.brand_select) {
                        if(data.getId()==brandlist.get(viewHolder.position).getId()) {
                            FilterProdukActivity.brand_select.remove(data);

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




