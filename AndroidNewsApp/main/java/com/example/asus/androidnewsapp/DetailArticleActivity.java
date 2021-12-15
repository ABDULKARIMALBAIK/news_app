package com.example.asus.androidnewsapp;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import dmax.dialog.SpotsDialog;

public class DetailArticleActivity extends AppCompatActivity {

    WebView webView;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_article);

        dialog = new SpotsDialog(this);
        dialog.show();

        webView = (WebView)findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient(){

            //Press ctrl + o
            @Override
            public void onPageFinished(WebView view, String url) {
                dialog.dismiss();
            }

        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setWebChromeClient(new WebChromeClient());

        if (getIntent().getExtras() != null){

            if (!getIntent().getStringExtra("webURL").isEmpty())
                webView.loadUrl(getIntent().getStringExtra("webURL"));
        }

    }
}
