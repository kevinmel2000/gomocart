package gomocart.application.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import gomocart.application.com.gomocart.R;
import gomocart.application.com.model.DiscountBeanclass;


public class DiscountListViewAdapter extends BaseAdapter {

    Context context;

    ArrayList<DiscountBeanclass>bean1;

  TextView discounttext;
    Typeface fonts1;





    public DiscountListViewAdapter(Context context, ArrayList<DiscountBeanclass> bean) {


        this.context = context;
        this.bean1 = bean;
    }




    @Override
    public int getCount() {
        return bean1.size();
    }

    @Override
    public Object getItem(int position) {
        return bean1.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        fonts1 =  Typeface.createFromAsset(context.getAssets(),
                "fonts/MuseoSans-500.otf");

//        linear1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                clickb("1");
//
//            }
//        });

        ViewHolder viewHolder = null;

        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.discountlist,null);

            viewHolder = new ViewHolder();


            viewHolder.discounttext = (TextView)convertView.findViewById(R.id.discounttext);

            viewHolder.discounttext.setTypeface(fonts1);

            convertView.setTag(viewHolder);


        }else {

            viewHolder = (ViewHolder)convertView.getTag();
        }







        DiscountBeanclass bean = (DiscountBeanclass)getItem(position);


        viewHolder.discounttext.setText(bean.getDiscounttext());






        return convertView;
    }

//    private void clickb(String s) { image.setColorFilter(context.getResources().getColor(R.color.colorTex), android.graphics.PorterDuff.Mode.MULTIPLY);
//        title.setTextColor(Color.parseColor("#8a929d"));
//
//
//        if (s.equalsIgnoreCase("1")) {
//
//            image.setColorFilter(context.getResources().getColor(R.color.red), android.graphics.PorterDuff.Mode.MULTIPLY);
//            title.setTextColor(Color.parseColor("#ff5254"));
//        }
//
//
//    }


    private class ViewHolder{

        TextView discounttext;











    }
}




