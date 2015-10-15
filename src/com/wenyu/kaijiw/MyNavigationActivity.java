package com.wenyu.kaijiw;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyNavigationActivity extends Activity{
	private String url ="http://api.map.baidu.com/direction";
	private String referer = "������";
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

		url = "http://api.map.baidu.com/direction?origin=latlng:"+fx+","+fy+"|name:"+fname+"&destination=latlng:"+x+","+y+"|name:"+name+"&mode=driving&region=�й�&output=html&src=������|������";

        WebSettings webSettings = webView.getSettings();         
        webSettings.setJavaScriptEnabled(true);     
        webSettings.setAllowFileAccess(true);  
        webSettings.setDomStorageEnabled(true);//����DCOM  

        webView.loadUrl(url);
        //����WebViewĬ��ʹ�õ�������ϵͳĬ�����������ҳ����Ϊ��ʹ��ҳ��WebView��
        webView.setWebViewClient(new WebViewClient(){
           @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
               //����ֵ��true��ʱ�����ȥWebView�򿪣�Ϊfalse����ϵͳ�����������������

             view.loadUrl(url);
            return true;
        }
       });
	}
}
