package gomocart.application.com.gomocart;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import gomocart.application.com.libs.CommonUtilities;

public class TermKondisiActivity extends AppCompatActivity {

    Context context;

    ImageView back;
    WebView webView;
    ProgressBar loading;
    LinearLayout retry;
    Button btnReload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.termkondisi);

        context = TermKondisiActivity.this;

        webView = (WebView) findViewById(R.id.webview_termkondisi);
        loading = (ProgressBar) findViewById(R.id.pgbarLoading);
        retry = (LinearLayout) findViewById(R.id.loadMask);
        btnReload = (Button) findViewById(R.id.btnReload);
        back = (ImageView) findViewById(R.id.back);

        btnReload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                loadDetail();
            }
        });

        loadDetail();

        webView.setVerticalScrollBarEnabled(false);
        webView.setWebChromeClient(new MyWebViewClient());

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK, new Intent());
                finish();
            }
        });
    }

    private class MyWebViewClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if(newProgress==100) {
                loading.setVisibility(View.GONE);
            }
            super.onProgressChanged(view, newProgress);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            setResult(RESULT_OK, new Intent());
            finish();

            return false;
        }

        return super.onKeyDown(keyCode, event);
    }

    private void loadDetail() {
        loading.setVisibility(View.VISIBLE);
        retry.setVisibility(View.GONE);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);

        CookieSyncManager.createInstance(webView.getContext());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeAllCookie(); //remove

        String url = CommonUtilities.SERVER_URL+"/syaratketentuan/";
        webView.loadUrl(url);
    }
}