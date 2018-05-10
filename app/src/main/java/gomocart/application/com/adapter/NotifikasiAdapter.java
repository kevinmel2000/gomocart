package gomocart.application.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import gomocart.application.com.gomocart.MainActivity;
import gomocart.application.com.gomocart.R;
import gomocart.application.com.libs.CommonUtilities;
import gomocart.application.com.model.notifikasi;

public class NotifikasiAdapter extends BaseAdapter {

	ArrayList<notifikasi> listDataNotifikasi = new ArrayList<>();
	Context context;
	
	public NotifikasiAdapter(Context context, ArrayList<notifikasi> listDataNotifikasi) {
		this.context = context;
		this.listDataNotifikasi = listDataNotifikasi;
	}

	public void UpdateListNotifikasiAdapter(ArrayList<notifikasi> listDataNotifikasi) {
		this.listDataNotifikasi = listDataNotifikasi;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return listDataNotifikasi.size();
	}

	@Override
	public Object getItem(int position) {
		return listDataNotifikasi.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
		
	public static class ViewHolder {
		
		public TextView txtJudul;
		public TextView txtTanggal;
		public TextView txtKonten;
		
		public int position;
	}

	@Override
	public View getView(int position, View convertView, final ViewGroup parent) {
		
		final ViewHolder view;
		LayoutInflater inflator =  LayoutInflater.from(parent.getContext());	
		if(convertView==null) {
			view = new ViewHolder();
			convertView = inflator.inflate(R.layout.notifikasi_item, null);
			
			view.txtJudul   = (TextView) convertView.findViewById(R.id.txtJudul);
			view.txtTanggal = (TextView) convertView.findViewById(R.id.txtTanggal);
			view.txtKonten  = (TextView) convertView.findViewById(R.id.txtKonten);
						
			convertView.setTag(view);
		} else {
			view = (ViewHolder) convertView.getTag();
		}
		
		final notifikasi notif = listDataNotifikasi.get(position);
		view.position = listDataNotifikasi.indexOf(notif);
		
    	view.txtJudul.setText(notif.getJudul());		
    	view.txtTanggal.setText(CommonUtilities.getDateMassage(notif.getTanggal_jam()));
    	view.txtKonten.setText(notif.getKonten());
		
		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View convertView) {
				((MainActivity) parent.getContext()).openDetailNotifikasi(notif);
				
			}
		});
		
		return convertView;
	}
}
