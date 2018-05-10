package gomocart.application.com.gomocart;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import customfonts.MyEditText;
import customfonts.MyTextView;
import gomocart.application.com.libs.CommonUtilities;
import gomocart.application.com.libs.JSONParser;
import gomocart.application.com.libs.MCrypt;
import gomocart.application.com.model.user;

public class LupaPasswordActivity extends AppCompatActivity {

    Context context;
    user data;

    Boolean is_send_success;

    ImageView back;
    Dialog dialog_informasi;
    MyTextView btn_ok;
    MyTextView text_title;
    MyTextView text_informasi;

    MyEditText email;
    TextView kirim_password;

    //ProgressDialog progDailog;
    Dialog dialog_loading;
    //FrameLayout frame_loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_password);

        context = LupaPasswordActivity.this;
        back           = (ImageView) findViewById(R.id.back);
        email          = (MyEditText) findViewById(R.id.email);
        kirim_password = (TextView) findViewById(R.id.kirim_password);
        kirim_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            new prosesSendPassword().execute();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

        dialog_informasi = new Dialog(context);
        dialog_informasi.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_informasi.setCancelable(true);
        dialog_informasi.setContentView(R.layout.informasi_dialog);

        btn_ok = (MyTextView) dialog_informasi.findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog_informasi.dismiss();
                if(is_send_success) {
                    setResult(RESULT_OK, new Intent());
                    finish();
                }
            }
        });

        text_title = (MyTextView) dialog_informasi.findViewById(R.id.text_title);
        text_informasi = (MyTextView) dialog_informasi.findViewById(R.id.text_dialog);
    }

    public void openDialogLoading() {
        dialog_loading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_loading.show();
    }

    class prosesSendPassword extends AsyncTask<String, Void, JSONObject> {

        boolean success;
        String message;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progDailog.setMessage("Proses...");
            //progDailog.show();
            openDialogLoading();

        }

        @Override
        protected JSONObject doInBackground(String... urls) {
            JSONParser token_json = new JSONParser();
            JSONObject token = token_json.getJSONFromUrl(CommonUtilities.SERVER_URL + "/store/token.php", null, null);
            String cookies = token_json.getCookies();

            String security_code = "";
            try {
                security_code = token.isNull("security_code")?"":token.getString("security_code");
                MCrypt mCrypt = new MCrypt();
                security_code = new String(mCrypt.decrypt(security_code));
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            JSONObject jObj = null;
            if(security_code.length()>0) {
                try {
                    String url = CommonUtilities.SERVER_URL + "/store/androidSendPassword.php";
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost(url);

                    MultipartEntity reqEntity = new MultipartEntity();
                    reqEntity.addPart("email", new StringBody(email.getText().toString()));
                    reqEntity.addPart("security_code", new StringBody(security_code));

                    httppost.setHeader("Cookie", cookies);
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
            }

            return jObj;
        }

        @Deprecated
        @Override
        protected void onPostExecute(JSONObject result) {

            dialog_loading.dismiss();

            success = false;
            message = "Proses kirim password gagal.";
            if(result!=null) {
                try {
                    success = result.isNull("success")?false:result.getBoolean("success");
                    message = result.isNull("message")?message:result.getString("message");
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            is_send_success = success;
            text_informasi.setText(message);
            text_title.setText(success?"BERHASIL":"GAGAL");
            dialog_informasi.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog_informasi.show();
        }
    }
}
