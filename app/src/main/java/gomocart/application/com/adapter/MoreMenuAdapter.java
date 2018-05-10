package gomocart.application.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import customfonts.MyTextView;
import gomocart.application.com.gomocart.MainActivity;
import gomocart.application.com.gomocart.R;
import gomocart.application.com.model.moremenu;

public class MoreMenuAdapter extends BaseExpandableListAdapter {

    Context context;
    ArrayList<moremenu> moremenulist;
    Map<moremenu, ArrayList<moremenu>> submoremenulist = new LinkedHashMap<>();

    public MoreMenuAdapter(Context context, ArrayList<moremenu> moremenulist, Map<moremenu, ArrayList<moremenu>> submoremenulist) {
        this.context = context;
        this.moremenulist = moremenulist;
        this.submoremenulist= submoremenulist;
    }

    public void UpdateMoreMenuAdapter(ArrayList<moremenu> moremenulist, Map<moremenu, ArrayList<moremenu>> submoremenulist) {
        this.moremenulist = moremenulist;
        this.submoremenulist= submoremenulist;
        notifyDataSetChanged();
    }

    public Object getChild(int indukPosition, int anakPosition) {
    	return submoremenulist.get(moremenulist.get(indukPosition)).get(anakPosition);
    }
 
    public long getChildId(int indukPosition, int anakPosition) {
        return anakPosition;
    }
     

	public View getChildView(final int indukPosition, final int anakPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final moremenu data_child = (moremenu) getChild(indukPosition, anakPosition);

        LayoutInflater inflator =  LayoutInflater.from(parent.getContext());
        
        if (convertView == null) {
            convertView = inflator.inflate(R.layout.moremenu_child_item, null);
        }

        MyTextView nama = (MyTextView) convertView.findViewById(R.id.nama);
        nama.setText(data_child.getNama());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View convertView) {
                ((MainActivity) context).loadDetailMenu(data_child);
            }
        });

        return convertView;
    }
 
    public int getChildrenCount(int indukPosition) {

        return submoremenulist.get(moremenulist.get(indukPosition))==null?0:submoremenulist.get(moremenulist.get(indukPosition)).size();
    }
 
    public Object getGroup(int indukPosition) {
        return moremenulist.get(indukPosition);
    }
 
    public int getGroupCount() {
        return moremenulist.size();
    }
 
    public long getGroupId(int indukPosition) {
        return indukPosition;
    }

	public View getGroupView(final int indukPosition, boolean isExpanded, View convertView, final ViewGroup parent) {
        final moremenu data = (moremenu) getGroup(indukPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.moremenu_parent_item, null);
        }

        ImageView image = (ImageView) convertView.findViewById(R.id.image);
        MyTextView nama = (MyTextView) convertView.findViewById(R.id.nama);

        image.setImageResource(data.getSource_image());
        nama.setText(data.getNama());

        return convertView;
    }
 
    public boolean hasStableIds() {
        return true;
    }
 
    public boolean isChildSelectable(int indukPosition, int anakPosition) {
        return true;
    }
}
