package com.siot.iamportsdk;

import java.net.URISyntaxException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class InicisWebViewClient extends WebViewClient {
	
	private Activity activity;
	private AlertDialog alertIsp;
	private WebView target;
	
	public InicisWebViewClient(Activity activity, WebView target) {
		this.activity = activity;
		this.target = target;
		
		this.alertIsp = new AlertDialog.Builder(activity)
		.setIcon(android.R.drawable.ic_dialog_alert)
		.setTitle("알림")
		.setMessage("모바일 ISP어플리케이션이 설치되어있지 않습니다. \n설치를 눌러 진행해주십시요.\n취소를 누르면 결제가 취소됩니다.")
		.setPositiveButton("설치", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// ISP
				InicisWebViewClient.this.target.loadUrl("http://mobile.vpay.co.kr/jsp/MISP/andown.jsp");
			}
		})
		.setNegativeButton("취소", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(InicisWebViewClient.this.activity, "(-1)결제가 취소되었습니다..", Toast.LENGTH_SHORT).show();
			}
		}).create();
	}
	
	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		
		final WebView targetWebView = view;
		
		if (!url.startsWith("http://") && !url.startsWith("https://") && !url.startsWith("javascript:")) {
			Intent intent = null;
			
			try {
				intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME); //IntentURI처리
				Uri uri = Uri.parse(intent.getDataString());
				
				activity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
				return true;
			} catch (URISyntaxException ex) {
				return false;
			} catch (ActivityNotFoundException e) {
				if ( intent == null )	return false;
				
				String packageName = intent.getPackage();
		        if (packageName != null) {
		            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
		            return true;
		        }
		        
		        return false;
				/*
				if (url.startsWith("ispmobile://")) {
					targetWebView.loadData("<html><body></body></html>", "text/html", "utf8");
					
					alertIsp.show();
					return true;
				} else if( url.startsWith("intent://")) {//intent 형태의 스키마 처리
					try {
						Intent excepIntent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
						String packageNm = excepIntent.getPackage();

						excepIntent = new Intent(Intent.ACTION_VIEW);
						excepIntent.setData(Uri.parse("market://details?id="+ packageNm));
						activity.startActivity(excepIntent);
						
						return true;
					} catch (URISyntaxException e1) {}
				}
				*/
			}
		}
		
		return false;
	}
	
}
