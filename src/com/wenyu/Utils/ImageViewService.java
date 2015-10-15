package com.wenyu.Utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageViewService {
  private static   Bitmap  bitmap;
	public static Bitmap getImage(String image) {


		byte[] data = null;  
	       try {  
	           //½¨Á¢URL   
	           URL url = new URL(image);  
	           HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
	           conn.setRequestMethod("GET");  
	           conn.setReadTimeout(5000);  
	            
	           InputStream input = conn.getInputStream();  
	           data = Util.readInputStream(input);  
	           input.close();  
	          bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
	       } catch (MalformedURLException e) {  
	           // TODO Auto-generated catch block  
	           e.printStackTrace();  
	       } catch (IOException e) {  
	           // TODO Auto-generated catch block  
	           e.printStackTrace();  
	       }  
	       return bitmap;  
	      
	}

  } 

class Util {  
    public static byte[] readInputStream(InputStream input) {  
       ByteArrayOutputStream output = new ByteArrayOutputStream();  
       try {  
           byte[] buffer = new byte[1024];  
           int len = 0;  
           while((len = input.read(buffer)) != -1) {  
              output.write(buffer, 0, len);  
           }  
       } catch (IOException e) {  
           // TODO Auto-generated catch block  
           e.printStackTrace();  
       }  
       return output.toByteArray();  
    }
}
