package gomocart.application.com.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import customfonts.MyTextView;
import gomocart.application.com.gomocart.FilterProdukActivity;
import gomocart.application.com.gomocart.R;
import gomocart.application.com.model.brand;
import gomocart.application.com.model.kategori;

public class FilterKategoriAdapter extends BaseExpandableListAdapter {
    ArrayList<kategori> kategorilist;
    Map<kategori, ArrayList<kategori>> subkategorilist = new LinkedHashMap<>();

    public FilterKategoriAdapter(ArrayList<kategori> kategorilist, Map<kategori, ArrayList<kategori>> subkategorilist) {
        this.kategorilist = kategorilist;
        this.subkategorilist = subkategorilist;
    }

    public void UpdateFilterKategoriAdapter(ArrayList<kategori> kategorilist, Map<kategori, ArrayList<kategori>> subkategorilist) {
        this.kategorilist = kategorilist;
        this.subkategorilist = subkategorilist;
        notifyDataSetChanged();
    }

    public Object getChild(int indukPosition, int anakPosition) {
    	return subkategorilist.get(kategorilist.get(indukPosition)).get(anakPosition);
    }
 
    public long getChildId(int indukPosition, int anakPosition) {
        return anakPosition;
    }
     
     
    @SuppressLint("InflateParams")
	public View getChildView(final int indukPosition, final int anakPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final kategori data_child = (kategori) getChild(indukPosition, anakPosition);

        LayoutInflater inflator =  LayoutInflater.from(parent.getContext());
        
        if (convertView == null) {
            convertView = inflator.inflate(R.layout.kategori_child_item, null);
        }

        MyTextView item = (MyTextView) convertView.findViewById(R.id.txtKeterangan);
        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);

        item.setText(data_child.getNama());
        checkBox.setChecked(data_child.getChecked());

        checkBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                boolean isChecked = ((CheckBox)v).isChecked();
                //System.out.println(isChecked?"CHECKED":"UNCHECKED");
                subkategorilist.get(kategorilist.get(indukPosition)).get(anakPosition).setChecked(isChecked);
                notifyDataSetChanged();

                if(isChecked) {
                    FilterProdukActivity.kategori_select.add(subkategorilist.get(kategorilist.get(indukPosition)).get(anakPosition));
                } else {
                    for (kategori data : FilterProdukActivity.kategori_select) {
                        if(data.getId()==subkategorilist.get(kategorilist.get(indukPosition)).get(anakPosition).getId()) {
                            FilterProdukActivity.brand_select.remove(data);

                            break;
                        }

                    }
                }

            }
        });

        return convertView;
    }
 
    public int getChildrenCount(int indukPosition) {
        return subkategorilist.get(kategorilist.get(indukPosition)).size();
    }
 
    public Object getGroup(int indukPosition) {
        return kategorilist.get(indukPosition);
    }
 
    public int getGroupCount() {
        return kategorilist.size();
    }
 
    public long getGroupId(int indukPosition) {
        return indukPosition;
    }
 
    @SuppressLint("InflateParams")
	public View getGroupView(final int indukPosition, boolean isExpanded, View convertView, final ViewGroup parent) {
        final kategori data = (kategori) getGroup(indukPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.kategori_group_item, null);
        }

        MyTextView item = (MyTextView) convertView.findViewById(R.id.txtKeterangan);
        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);

        item.setText(data.getNama());
        checkBox.setChecked(data.getChecked());
        checkBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                boolean isChecked = ((CheckBox)v).isChecked();
                //System.out.println(isChecked?"CHECKED":"UNCHECKED");
                kategorilist.get(indukPosition).setChecked(isChecked);
                notifyDataSetChanged();

                if(isChecked) {
                    FilterProdukActivity.kategori_select.add(kategorilist.get(indukPosition));
                } else {
                    for (kategori data : FilterProdukActivity.kategori_select) {
                        if(data.getId()==kategorilist.get(indukPosition).getId()) {
                            FilterProdukActivity.brand_select.remove(data);

                            break;
                        }

                    }
                }

            }
        });

        /*total.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				kategori data_kat = new kategori(data.getId(), "All - "+data.getNama(), data.getPenjelasan(), data.getHeader());
				((MainActivity) parent.getContext()).openProdukKategori(data_kat);
			}
		});
        
        item.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				kategori data_kat = new kategori(data.getId(), "All - "+data.getNama(), data.getPenjelasan(), data.getHeader());
				((MainActivity) parent.getContext()).openProdukKategori(data_kat);
			}
		});*/
        
        return convertView;
    }
 
    public boolean hasStableIds() {
        return true;
    }
 
    public boolean isChildSelectable(int indukPosition, int anakPosition) {
        return true;
    }
}
