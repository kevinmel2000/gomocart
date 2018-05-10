package gomocart.application.com.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import customfonts.MyTextView;
import gomocart.application.com.gomocart.MainActivity;
import gomocart.application.com.gomocart.R;
import gomocart.application.com.libs.CommonUtilities;
import gomocart.application.com.libs.ResizableImageView;
import gomocart.application.com.model.stok;

public class ListStokAdapter extends BaseAdapter {

	ArrayList<stok> liststok = new ArrayList<>();
	Context context;

	public ListStokAdapter(Context context, ArrayList<stok> liststok) {
		this.context = context;
		this.liststok = liststok;
	}

	public void UpdateListStokAdapter(ArrayList<stok> liststok) {
		this.liststok = liststok;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return liststok.size();
	}

	@Override
	public Object getItem(int position) {
		return liststok.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public static class ViewHolder {
		public MyTextView ukuran;
		public MyTextView warna;
		public MyTextView qty;
		public int position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, final ViewGroup parent) {

		final ViewHolder view;
		LayoutInflater inflator =  LayoutInflater.from(parent.getContext());
		if(convertView==null) {
			view = new ViewHolder();
			convertView = inflator.inflate(R.layout.stok_item_list, null);

			view.ukuran = (MyTextView) convertView.findViewById(R.id.ukuran);
			view.warna = (MyTextView) convertView.findViewById(R.id.warna);
			view.qty = (MyTextView) convertView.findViewById(R.id.qty);

			convertView.setTag(view);
		} else {
			view = (ViewHolder) convertView.getTag();
		}

		final stok data = liststok.get(position);
		view.ukuran.setText(data.getUkuran());
		view.warna.setText(data.getWarna());
		view.qty.setText(CommonUtilities.getNumberFormat(data.getQty())+" Pcs");

		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View convertView) {

			}
		});

		return convertView;
	}
}
