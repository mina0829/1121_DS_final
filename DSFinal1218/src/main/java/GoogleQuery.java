import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class GoogleQuery 
{
	public String searchKeyword;
	public String url;
	public String content;
	public ArrayList<WebTree> webTreeList;
	public ArrayList<String> childrenUrl;
	public ArrayList<Keyword> keywords;
	public WebTreeSort TS;
	
	public GoogleQuery(String searchKeyword, ArrayList<Keyword> keywords)
	{
		this.searchKeyword = searchKeyword;
		this.webTreeList = new ArrayList<WebTree>();
		this.childrenUrl = new ArrayList<String>();
		this.keywords = keywords;
		this.TS = new WebTreeSort();
		try 
		{
			// 產生google搜尋網址
			String encodeKeyword=java.net.URLEncoder.encode(searchKeyword,"utf-8");
			this.url = "https://www.google.com/search?q="+encodeKeyword+"&oe=utf8&num=20";
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		System.out.println(url);
	}
	
	private String fetchContent() throws IOException
	{
		String retVal = "";
		//可以嘗試處理HTTP400、403的問題，不然連結都讀不到
		try {
	        URL u = new URL(url);
	        URLConnection conn = u.openConnection();
	        
	        conn.setRequestProperty("User-agent", "Chrome/107.0.5304.107");
	        InputStream in = conn.getInputStream();

	        InputStreamReader inReader = new InputStreamReader(in, "utf-8");
	        BufferedReader bufReader = new BufferedReader(inReader);
	        String line = null;

	        while ((line = bufReader.readLine()) != null) {
	            retVal += line;
	        }
	    } catch (java.io.FileNotFoundException e) {
	        System.out.println("File not found: " + e.getMessage());
	    }
		return retVal;
	}
	
	public HashMap<String, String> query() throws IOException
	{
		if(content == null)
		{
			content = fetchContent();
		}

		HashMap<String, String> retVal = new HashMap<String, String>();
		
		//讀取搜尋頁面的內容
		Document doc = Jsoup.parse(content);
		
		//挑選出想要的內容；搜尋結果之標題與網址 
		Elements lis = doc.select("div");
		lis = lis.select(".kCrYT");
		
		for(Element li : lis)
		{
		    try 
		    {
		    	String citeUrl = li.select("a").get(0).attr("href").replace("/url?q=", "");
		       String title = li.select("a").get(0).select(".vvjwJb").text();
		            
		       if(title.equals("")) 
		       {
		       		continue;
		       }
		       
		       int index = citeUrl.indexOf("&");
		        if (index != -1) {
		        	citeUrl = citeUrl.substring(0, index);
		        }
		       
		        //解碼獲得之連結
		       String newCiteUrl = URLDecoder.decode(citeUrl, "UTF-8");
		       
		       System.out.println("Title: " + title + " , url: " + newCiteUrl);
		       
		       //將獲得之連結建立為WebPage，並作為WebTree的root
		       WebPage rootPage = new WebPage(newCiteUrl);
		       WebTree tree = new WebTree(rootPage);
		       
		       //用HtmlScraping class讀取此連結之網頁內容、抓出children的連結
		       HtmlScraping htmlScraping = new HtmlScraping(newCiteUrl);
		       childrenUrl = htmlScraping.findChildren();
		       
		      
		       //將children加入tree中
		       for(String url: childrenUrl) {
		    	   tree.root.addChild(new WebNode(new WebPage(url)));
		       }
		       
		       //計算出這個tree的總分(root是加總)
		       tree.setPostOrderScore(keywords);
		       System.out.println(tree.root.nodeScore);
		       
		       //排序所有的tree分數，大的在前
		       webTreeList.add(tree);
		       TS.sort(webTreeList);
		       
		       retVal.put(title, newCiteUrl);
		    } 
		    catch (IndexOutOfBoundsException e) 
		    {
		       // e.printStackTrace();
		    }
		}
		
		return retVal;
	}
}