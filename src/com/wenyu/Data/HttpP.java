 package com.wenyu.Data;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


public class HttpP {
	
	public  String   postF( String url,String  name,String number,String namevalue,String numbervalue){
		String a1 = null;
		HttpClient httpClient = new DefaultHttpClient();
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair(name, namevalue));
		params.add(new BasicNameValuePair(number, numbervalue)); 
		//�Բ�������
		HttpPost postMethod = new HttpPost(url); 
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
			HttpResponse response = httpClient.execute(postMethod); //ִ��POST���� 
			if(response.getStatusLine().getStatusCode()==200){
			
		a1 = EntityUtils.toString(response.getEntity(), "utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //����������POST Entity�� 
		catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return a1;

	}

	
    public String sendPOSTRequestHttpClient(String path,Map<String, String> params) throws Exception {  
       String a = null;  
        // ��װ�������  
        List<NameValuePair> pair = new ArrayList<NameValuePair>();  
        if (params != null && !params.isEmpty()) {  
            for (Map.Entry<String, String> entry : params.entrySet()) {  
                pair.add(new BasicNameValuePair(entry.getKey(), entry  
                        .getValue()));  
            }  
        }  
        // �����������������岿��  
        UrlEncodedFormEntity uee = new UrlEncodedFormEntity(pair, "utf-8");  
        // ʹ��HttpPost�������÷��͵�URL·��  
        HttpPost post = new HttpPost(path);  
        // ����������  
        post.setEntity(uee);  
//        System.out.println("post"+post);
        // ����һ������������԰�POST��������������ͣ���������Ӧ��Ϣ  
        DefaultHttpClient dhc = new DefaultHttpClient();  
        HttpResponse response = dhc.execute(post);  
        
        if (response.getStatusLine().getStatusCode() == 200) {  
        	a = EntityUtils.toString(response.getEntity(), "utf-8");
        }  
        return a;  
    }  
	


}
