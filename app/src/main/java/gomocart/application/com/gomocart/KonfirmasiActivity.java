package gomocart.application.com.gomocart;

import android.app.Dialog;
import android.content.Context;
        import android.content.Intent;
        import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
        import android.net.Uri;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.view.MotionEvent;
        import android.view.View;
        import android.view.Window;
        import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
        import android.widget.ListView;
        import android.widget.SimpleAdapter;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.nostra13.universalimageloader.core.DisplayImageOptions;
        import com.nostra13.universalimageloader.core.ImageLoader;

        import org.apache.http.HttpEntity;
        import org.apache.http.HttpResponse;
        import org.apache.http.client.ClientProtocolException;
        import org.apache.http.client.HttpClient;
        import org.apache.http.client.methods.HttpPost;
        import org.apache.http.entity.mime.MultipartEntity;
        import org.apache.http.entity.mime.content.FileBody;
        import org.apache.http.entity.mime.content.StringBody;
        import org.apache.http.impl.client.DefaultHttpClient;
        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.BufferedReader;
        import java.io.File;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.io.UnsupportedEncodingException;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Date;
        import java.util.HashMap;
        import java.util.List;

        import customfonts.MyEditText;
        import customfonts.MyTextView;
import gomocart.application.com.libs.CommonUtilities;
import gomocart.application.com.libs.DatabaseHandler;
        import gomocart.application.com.libs.GalleryFilePath;
import gomocart.application.com.libs.JSONParser;
        import gomocart.application.com.libs.ResizableImageView;
        import gomocart.application.com.model.bank;
        import gomocart.application.com.model.user;

public class KonfirmasiActivity extends AppCompatActivity {

    Context context;
    private static Uri mImageCaptureUri;

    final int REQUEST_CODE_FROM_GALLERY = 01;
    final int REQUEST_CODE_FROM_CAMERA  = 02;

    int id_bank_penerima;
    MyEditText edit_no_transaksi;
    MyEditText edit_jumlah_transfer;
    MyEditText edit_bank_penerima;
    MyEditText edit_bank_pengirim;
    MyEditText edit_nama_pemilik_rekening;
    MyEditText edit_penjelasan;
    MyTextView submit;
    MyTextView upload;
    ResizableImageView mImageView;
    ImageView back;

    ArrayList<bank> listBank = new ArrayList<>();

    float downX = 0, downY = 0, upX, upY;
    Dialog dialog_listview;
    ListView listview;
    String action;
    DatabaseHandler dh;
    user data;

    //ProgressDialog progDailog;
    Dialog dialog_loading;
    //FrameLayout frame_loading;

    Dialog dialog_informasi;
    MyTextView btn_ok;
    MyTextView text_title;
    MyTextView text_informasi;

    Dialog dialog_pilih_gambar;
    TextView from_camera, from_galery;

    ImageLoader imageLoader;
    DisplayImageOptions options;

    int index;
    int jumlah;
    String no_transaksi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfirmasi_pembayaran);

        context = KonfirmasiActivity.this;
        dh = new DatabaseHandler(context);
        data = CommonUtilities.getSettingUser(context);

        CommonUtilities.initImageLoader(context);
        imageLoader = ImageLoader.getInstance();
        options = CommonUtilities.getOptionsImage(R.drawable.attachment, R.drawable.attachment);

        edit_no_transaksi = (MyEditText) findViewById(R.id.edit_no_transaksi);
        edit_jumlah_transfer = (MyEditText) findViewById(R.id.edit_jumlah_transfer);
        edit_bank_penerima = (MyEditText) findViewById(R.id.edit_bank_penerima);
        edit_bank_pengirim = (MyEditText) findViewById(R.id.edit_bank_pengirim);
        edit_nama_pemilik_rekening = (MyEditText) findViewById(R.id.edit_nama_pemilik_rekening);
        edit_penjelasan = (MyEditText) findViewById(R.id.edit_penjelasan);

        mImageView = (ResizableImageView) findViewById(R.id.image_bukti_transfer);
        upload = (MyTextView) findViewById(R.id.upload);
        submit = (MyTextView) findViewById(R.id.submit);
        back = (ImageView) findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                selectImage();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new prosesSimpan().execute();
            }
        });

        edit_bank_penerima.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downX = event.getX();
                        downY = event.getY();

                        break;

                    case MotionEvent.ACTION_UP:
                        upX = event.getX();
                        upY = event.getY();
                        float deltaX = downX - upX;
                        float deltaY = downY - upY;

                        if (Math.abs(deltaX) < 50 && Math.abs(deltaY) < 50) {
                            action = "bank_penerima";
                            loadListArray();
                            dialog_listview.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialog_listview.show();
                        }

                        break;
                }

                return false;
            }
        });

        dialog_informasi = new Dialog(context);
        dialog_informasi.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_informasi.setCancelable(true);
        dialog_informasi.setContentView(R.layout.informasi_dialog);

        btn_ok = (MyTextView) dialog_informasi.findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog_informasi.dismiss();
            }
        });

        text_title = (MyTextView) dialog_informasi.findViewById(R.id.text_title);
        text_informasi = (MyTextView) dialog_informasi.findViewById(R.id.text_dialog);

        dialog_listview = new Dialog(context);
        dialog_listview.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_listview.setCancelable(true);
        dialog_listview.setContentView(R.layout.list_dialog);

        listview = (ListView) dialog_listview.findViewById(R.id.listViewDialog);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog_listview.dismiss();
                if (action.equalsIgnoreCase("bank_penerima")) {
                    bank data = listBank.get(position);
                    id_bank_penerima = data.getId();
                    edit_bank_penerima.setText(data.getNama_bank() + " No. Rek. " + data.getNo_rekening() + " an. " + data.getNama_pemilik_rekening());
                }
            }
        });

        //progDailog = new ProgressDialog(context);
        //progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //progDailog.setCancelable(false);
        dialog_loading = new Dialog(context);
        dialog_loading.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_loading.setCancelable(false);
        dialog_loading.setContentView(R.layout.loading_dialog);
        //frame_loading = (FrameLayout) dialog_loading.findViewById(R.id.frame_loading);

        dialog_pilih_gambar = new Dialog(context);
        dialog_pilih_gambar.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_pilih_gambar.setCancelable(true);
        dialog_pilih_gambar.setContentView(R.layout.pilih_gambar_dialog);

        from_galery = (TextView) dialog_pilih_gambar.findViewById(R.id.txtFromGalley);
        from_galery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                dialog_pilih_gambar.dismiss();
                fromGallery();
            }
        });

        from_camera = (TextView) dialog_pilih_gambar.findViewById(R.id.txtFromCamera);
        from_camera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                dialog_pilih_gambar.dismiss();
                fromCamera();
            }
        });

        if(savedInstanceState==null) {
            index = getIntent().getIntExtra("index", -1);
            no_transaksi = getIntent().getStringExtra("no_transaksi");
            jumlah = (int) getIntent().getDoubleExtra("jumlah", 0);

            if(no_transaksi!=null) { edit_no_transaksi.setText(no_transaksi); }
            if(jumlah>0) { edit_jumlah_transfer.setText(jumlah+""); }
        }

        new loadBank().execute();
    }


    public void selectImage() {
        dialog_pilih_gambar.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_pilih_gambar.show();
    }


    private void fromGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_FROM_GALLERY);
    }

    private void fromCamera() {

        Intent intent = new Intent(context, AmbilFotoActivity.class);
        startActivityForResult(intent, REQUEST_CODE_FROM_CAMERA);
    }

    public class loadBank extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... urls) {

            listBank = new ArrayList<>();
            String url = CommonUtilities.SERVER_URL + "/store/androidBankDataStore.php";
            JSONObject json = new JSONParser().getJSONFromUrl(url, null, null);
            if (json != null) {
                try {
                    JSONArray data = json.isNull("topics") ? null : json.getJSONArray("topics");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject rec = data.getJSONObject(i);

                        int id = rec.isNull("id") ? 0 : rec.getInt("id");
                        String no_rekening = rec.isNull("no_rekening") ? "" : rec.getString("no_rekening");
                        String nama_pemilik_rekening = rec.isNull("nama_pemilik_rekening") ? "" : rec.getString("nama_pemilik_rekening");
                        String nama_bank = rec.isNull("nama_bank") ? "" : rec.getString("nama_bank");
                        String cabang = rec.isNull("cabang") ? "" : rec.getString("cabang");
                        String gambar = rec.isNull("gambar") ? "" : rec.getString("gambar");

                        listBank.add(new bank(id, no_rekening, nama_pemilik_rekening, nama_bank, cabang, gambar));
                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            return null;
        }
    }

    private void loadListArray() {
        String[] from = new String[]{getResources().getString(R.string.list_dialog_title)};
        int[] to = new int[]{R.id.txt_title};

        List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
        if (action.equalsIgnoreCase("bank_penerima")) {
            for (bank data : listBank) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(getResources().getString(R.string.list_dialog_title), data.getNama_bank() + "\nNo. Rek. " + data.getNo_rekening() + "\nan. " + data.getNama_pemilik_rekening());

                fillMaps.add(map);
            }
        }

        SimpleAdapter adapter = new SimpleAdapter(context, fillMaps, R.layout.item_list_dialog, from, to);
        listview.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            String fileName = new SimpleDateFormat("yyyyMMddhhmmss'.jpg'").format(new Date());
            String dest = CommonUtilities.getOutputPath(context, "images")+ File.separator+fileName;
            switch(requestCode) {
                case REQUEST_CODE_FROM_CAMERA:
                    CommonUtilities.compressImage(context, data.getStringExtra("path"), dest);
                    mImageCaptureUri = Uri.fromFile(new File(dest));
                    mImageView.setImageURI(mImageCaptureUri);

                    break;
                case REQUEST_CODE_FROM_GALLERY:
                    Uri selectedUri = data.getData();
                    CommonUtilities.compressImage(context, GalleryFilePath.getPath(context, selectedUri), dest);
                    mImageCaptureUri = Uri.fromFile(new File(dest));
                    mImageView.setImageURI(mImageCaptureUri);

                    break;
            }
        }
    }

    class prosesSimpan extends AsyncTask<String, Void, JSONObject> {

        Boolean success;
        String message;
        String photo;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            openDialogLoading();
            //progDailog.setMessage("Submit...");
            //progDailog.show();
        }

        @Override
        protected JSONObject doInBackground(String... urls) {
            JSONObject jObj = null;
            try {
                String url = CommonUtilities.SERVER_URL + "/store/androidKonfirmasiPembayaran.php";
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(url);

                MultipartEntity reqEntity = new MultipartEntity();
                reqEntity.addPart("user_id", new StringBody(data.getId() + ""));
                reqEntity.addPart("guest_id", new StringBody(CommonUtilities.getGuestId(context) + ""));
                reqEntity.addPart("kode_pemesanan", new StringBody(edit_no_transaksi.getText().toString()));
                reqEntity.addPart("bank_tujuan", new StringBody(edit_bank_penerima.getText().toString()));
                reqEntity.addPart("jumlah_transfer", new StringBody(edit_jumlah_transfer.getText().toString()));
                reqEntity.addPart("nama_bank_pengirim", new StringBody(edit_bank_pengirim.getText().toString()));
                reqEntity.addPart("nama_pemilik_rekening", new StringBody(edit_nama_pemilik_rekening.getText().toString()));
                reqEntity.addPart("penjelasan", new StringBody(edit_penjelasan.getText().toString()));

                if (mImageCaptureUri != null) {
                    File file = new File(mImageCaptureUri.getPath());
                    if (file.exists()) {
                        FileBody bin_gamber = new FileBody(file);
                        reqEntity.addPart("photo", bin_gamber);
                    }
                }

                httppost.setEntity(reqEntity);
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity resEntity = response.getEntity();
                InputStream is = resEntity.getContent();

                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;

                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                String json = sb.toString();
                System.out.println(json);

                jObj = new JSONObject(json);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return jObj;
        }

        @Deprecated
        @Override
        protected void onPostExecute(JSONObject result) {

            dialog_loading.dismiss();

            success = false;
            message = "Proses gagal.";
            photo   = "";

            if(result!=null) {
                try {
                    success = result.isNull("success")?false:result.getBoolean("success");
                    message = result.isNull("message")?message:result.getString("message");

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if(success) {
                Toast.makeText(context, "Konfirmasi pembayaran berhasil!", Toast.LENGTH_SHORT).show();

                if(mImageCaptureUri!=null) {
                    File file = new File(mImageCaptureUri.getPath());
                    if(file.exists()) {
                        file.delete();
                    }
                }
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();

            } else {
                text_informasi.setText(message);
                text_title.setText("KESALAHAN");
                dialog_informasi.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog_informasi.show();
            }
        }
    }

    public void openDialogLoading() {
        dialog_loading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_loading.show();
    }
}
