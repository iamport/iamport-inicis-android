package com.example.inicissample;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.siot.iamportsdk.InicisWebViewClient;

public class MainActivity extends Activity {

	private WebView mainWebView;
	private final String APP_SCHEME = "iamporttest://";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mainWebView = (WebView) findViewById(R.id.mainWebView);
        mainWebView.setWebViewClient(new InicisWebViewClient(this, mainWebView));
        WebSettings settings = mainWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        
        Intent intent = getIntent();
        Uri intentData = intent.getData();
        
        if ( intentData == null ) {
        	mainWebView.loadUrl("http://www.iamport.kr/demo");
        } else {
        	//isp 인증 후 복귀했을 때 결제 후속조치
        	String url = intentData.toString();
        	if ( url.startsWith(APP_SCHEME) ) {
        		String redirectURL = url.substring(APP_SCHEME.length()+3);
                mainWebView.loadUrl(redirectURL);
        	}
        }
    }
}
