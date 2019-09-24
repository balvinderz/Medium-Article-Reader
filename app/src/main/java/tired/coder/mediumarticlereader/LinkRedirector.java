package tired.coder.mediumarticlereader;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LinkRedirector extends AsyncTask<Void,Void,Void> {
    String url;
    String finallink;
    WebView webView;

    ProgressBar progressBar;
    public LinkRedirector(String url, WebView webView,ProgressBar progressBar) {
        this.url = url;
        this.webView = webView;
        this.progressBar = progressBar;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            Document mediumpage = Jsoup.connect(url).get();
            Element metatag = mediumpage.select("meta[property=al:android:url]").first();
            System.out.println(metatag.attr("content"));
            String baseurl = "https://medium.com";
            String afterscraping = metatag.attr("content");
            int index = afterscraping.indexOf("/p");
            afterscraping = afterscraping.substring(index);
           // System.out.println(baseurl + afterscraping);
            finallink = baseurl+afterscraping;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.i("textwas","I reached here");
        webView.loadUrl(finallink);
        progressBar.setVisibility(View.GONE);

    }
}
