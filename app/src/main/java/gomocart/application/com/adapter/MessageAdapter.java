package gomocart.application.com.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alexzh.circleimageview.CircleImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import gomocart.application.com.gomocart.R;
import gomocart.application.com.libs.CommonUtilities;
import gomocart.application.com.libs.RoundedImageView;
import gomocart.application.com.model.message;

public class MessageAdapter extends BaseAdapter {

	Context context;
	ArrayList<message> listmessage;
	DisplayImageOptions imageOptionUser;
	DisplayImageOptions imageOptionAdmin;

	ImageLoader imageLoader;

	public MessageAdapter(Context context, ArrayList<message> listmessage) {
		this.context = context;
		this.listmessage = listmessage;
		imageOptionUser = CommonUtilities.getOptionsImage(R.drawable.userdefault, R.drawable.userdefault);
		imageOptionAdmin = CommonUtilities.getOptionsImage(R.drawable.logo_grayscale, R.drawable.logo_grayscale);
		imageLoader = ImageLoader.getInstance();
	}

	public void UpdatemessageAdapter(ArrayList<message> listmessage) {
		this.listmessage = listmessage;
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return listmessage.size();
	}

	@Override
	public Object getItem(int position) {
		return listmessage.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
		
	public static class ViewHolder {

		//text message you
		public RelativeLayout messageTextYou;
		public CircleImageView imgUserYou;
		public TextView txtKeteranganYou;
		public TextView txtPostByYou;

		//text message from
		public RelativeLayout messageTextFrom;
		public CircleImageView imgUserFrom;
		public TextView txtKeteranganFrom;
		public TextView txtPostByFrom;

		public int position;
	}

	@Override
	public View getView(int position, View convertView, final ViewGroup parent) {
		
		final ViewHolder view;
		LayoutInflater inflator =  LayoutInflater.from(parent.getContext());	
		if(convertView==null) {
			view = new ViewHolder();
			convertView = inflator.inflate(R.layout.message_item, null);

			//text message you
			view.messageTextYou   = (RelativeLayout) convertView.findViewById(R.id.messageTextYou);
			view.imgUserYou       = (CircleImageView) convertView.findViewById(R.id.imgUserYou);
			view.txtKeteranganYou = (TextView) convertView.findViewById(R.id.txtKeteranganYou);
			view.txtPostByYou     = (TextView) convertView.findViewById(R.id.txtPostByYou);

			//text message from
			view.messageTextFrom   = (RelativeLayout) convertView.findViewById(R.id.messageTextFrom);
			view.imgUserFrom       = (CircleImageView) convertView.findViewById(R.id.imgUserFrom);
			view.txtKeteranganFrom = (TextView) convertView.findViewById(R.id.txtKeteranganFrom);
			view.txtPostByFrom     = (TextView) convertView.findViewById(R.id.txtPostByFrom);

			view.imgUserYou.setLayerType(View.LAYER_TYPE_HARDWARE, null);
			view.imgUserFrom.setLayerType(View.LAYER_TYPE_HARDWARE, null);

			convertView.setLongClickable(true);
			convertView.setTag(view);
		} else {
			view = (ViewHolder) convertView.getTag();
		}

		final message data = listmessage.get(position);
		view.position = listmessage.indexOf(data);

		if(data.getIs_self()) {
			view.messageTextFrom.setVisibility(View.GONE);
			view.messageTextYou.setVisibility(View.VISIBLE);

			String server = CommonUtilities.SERVER_URL;
			String url = server+"/uploads/member/"+data.getPhoto();

			imageLoader.displayImage(url, view.imgUserYou, imageOptionUser);

			view.txtPostByYou.setText(CommonUtilities.getDateMassage(data.getDatetime()));
			view.txtKeteranganYou.setText(data.getMessage());
		} else {
			view.messageTextYou.setVisibility(View.GONE);
			view.messageTextFrom.setVisibility(View.VISIBLE);

			String server = CommonUtilities.SERVER_URL;
			String url = server+"/uploads/umum/"+data.getPhoto();
			imageLoader.displayImage(url, view.imgUserFrom, imageOptionAdmin);

			view.txtPostByFrom.setText(CommonUtilities.getDateMassage(data.getDatetime()));
			view.txtKeteranganFrom.setText(data.getMessage());
		}

		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View convertView) {

			}
		});
		
		return convertView;
	}
}
