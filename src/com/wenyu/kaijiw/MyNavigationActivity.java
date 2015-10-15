package com.wenyu.kaijiw;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyNavigationActivity extends Activity{
	private String url ="http://api.map.baidu.com/direction";
	private String referer = "开机网";
	private WebView webView ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mywebview);
		initView();
	}

	private void initView() {
		webView  = (WebView) findViewById(R.id.webView);
		Intent   it = getIntent();
		String fname = it.getStringExtra("fname");
		String fx = it.getStringExtra("fx");
		String fy = it.getStringExtra("fy");
		String name = it.getStringExtra("name");
		String x = it.getStringExtra("x");
		String y = it.getStringExtra("y");

		url = "http://api.map.baidu.com/direction?origin=latlng:"+fx+","+fy+"|name:"+fname+"&destination=latlng:"+x+","+y+"|name:"+name+"&mode=driving&region=中国&output=html&src=开机网|开机网";

        WebSettings webSettings = webView.getSettings();         
        webSettings.setJavaScriptEnabled(true);     
        webSettings.setAllowFileAccess(true);  
        webSettings.setDomStorageEnabled(true);//允许DCOM  

        webView.loadUrl(url);
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient(){
           @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
               //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器

             view.loadUrl(url);
            return true;
        }
       });
	}
}
