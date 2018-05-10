package gomocart.application.com.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.alexzh.circleimageview.CircleImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import java.util.ArrayList;
import java.util.UUID;

import customfonts.MyTextView;
import gomocart.application.com.libs.CommonUtilities;
import gomocart.application.com.gomocart.MainActivity;
import gomocart.application.com.gomocart.R;
import gomocart.application.com.model.kategori;


public class RecyclerViewKategoriAdapter extends RecyclerView.Adapter<RecyclerViewKategoriAdapter.DataObjectHolder> {

    Context context;
    ArrayList<kategori> kategorilist;
    ImageLoader imageLoader;
    DisplayImageOptions options;

    public class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CircleImageView image;
        MyTextView title;

        public DataObjectHolder(View itemView) {
            super(itemView);

            image = (CircleImageView) itemView.findViewById(R.id.image);
            title = (MyTextView) itemView.findViewById(R.id.title);
            image.setLayerType(View.LAYER_TYPE_HARDWARE, null);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            ((MainActivity) context).openProdukKategori(kategorilist.get(getAdapterPosition()));
        }

    }

    public RecyclerViewKategoriAdapter(Context context, ArrayList<kategori> kategorilist) {
        this.context = context;
        this.kategorilist = kategorilist;

        imageLoader = ImageLoader.getInstance();
        options = CommonUtilities.getOptionsImage(R.drawable.logo_grayscale, R.drawable.logo_grayscale);
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listcat, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {

        String server = CommonUtilities.SERVER_URL;
        String url = server+"/uploads/category/"+kategorilist.get(position).getHeader();
        System.out.println(url);
        imageLoader.displayImage(url, holder.image, options);

        holder.title.setText(kategorilist.get(position).getNama());

    }

    @Override
    public int getItemCount() {
        return kategorilist.size();
    }
}
