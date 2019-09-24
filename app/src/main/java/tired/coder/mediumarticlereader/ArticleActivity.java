package tired.coder.mediumarticlereader;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ArticleActivity extends AppCompatActivity {
    WebView medium;
    Toolbar toolbar;
    ProgressBar progressBar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.articleactivity);
        Log.i("textwas","soja");
        Intent intent = getIntent();
        medium = findViewById(R.id.mediumarticle);
               String action = intent.getAction();
        String type = intent.getType();
        String mediumtext = intent.getStringExtra(Intent.EXTRA_TEXT);
        toolbar= findViewById(R.id.toolbar);
        toolbar.setTitle(ExtractTitle(mediumtext));
        medium.clearCache(true);
        CookieManager.getInstance().removeAllCookies(null);
        CookieManager.getInstance().flush();
        progressBar = findViewById(R.id.progresbar);
        medium.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view,String url)
            {
                view.loadUrl(url);
                return true;
            }
        });
        if(Intent.ACTION_SEND.equals(action) && type!=null && mediumtext!=null)
        {
            Log.d("textwas",mediumtext);
            String link = ExtractLink(mediumtext);

          new  LinkRedirector(link,medium,progressBar).execute();
        }

    }
    String ExtractLink(String mediumtext)
    {
        int index = mediumtext.indexOf("https://");
        if(index==-1)
            return "";
        return mediumtext.substring(index);
    }
    String ExtractTitle(String mediumtext)
    {
        mediumtext = mediumtext.substring(1);
        int index= mediumtext.indexOf("”");
        Log.i("index is ",String.valueOf(index));
        return (index !=-1) ? mediumtext.substring(0,index) : "";
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (medium.canGoBack()) {
                    medium.goBack();
                } else {
                    finish();
                }
                return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }
}
