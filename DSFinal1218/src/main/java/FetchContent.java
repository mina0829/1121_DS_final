import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class FetchContent {
	private String urlStr;
	private String content;
    
    public FetchContent(String urlStr){
    	this.urlStr = urlStr;
    	this.content = "";
    }
    
    //讀取網頁內容之功能
    public String getContent() {
    	//要處理HTTP400、403的問題，不然連結都讀不到
		try {
			URL url = new URL(this.urlStr);
	        URLConnection conn = url.openConnection();
	        InputStream in = conn.getInputStream();
	        BufferedReader br = new BufferedReader(new InputStreamReader(in));
	        
	        String line = null;
		
	        while ((line = br.readLine()) != null){
	        	content = content + line + "\n";
	        }
	    } catch (Exception e) {
	        // 捕獲例外，印出錯誤消息
	        System.err.println("Error processing link");
	        e.printStackTrace();
	    }
	    return content;
    }
}