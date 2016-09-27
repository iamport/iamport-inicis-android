package com.siot.inicissample;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.siot.iamportsdk.InicisWebViewClient;

public class MainActivity extends Activity {

	private WebView mainWebView;
	private static final String APP_SCHEME = "iamporttest://";
	
    @SuppressLint("NewApi") @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mainWebView = (WebView) findViewById(R.id.mainWebView);
        mainWebView.setWebViewClient(new InicisWebViewClient(this));
        WebSettings settings = mainWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        	settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        	CookieManager cookieManager = CookieManager.getInstance();
        	cookieManager.setAcceptCookie(true);
        	cookieManager.setAcceptThirdPartyCookies(mainWebView, true);
        }
        
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

    @Override
    protected void onNewIntent(Intent intent) {
    	String url = intent.toString();
    	if ( url.startsWith(APP_SCHEME) ) {
    		String redirectURL = url.substring(APP_SCHEME.length()+3);
    		mainWebView.loadUrl(redirectURL);
    	}
    }
    
}
