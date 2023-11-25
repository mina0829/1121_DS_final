import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
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
	
	public GoogleQuery(String searchKeyword)
	{
		this.searchKeyword = searchKeyword;
		this.webTreeList = new ArrayList<WebTree>();
		try 
		{
			// This part has been specially handled for Chinese keyword processing. 
			// You can comment out the following two lines 
			// and use the line of code in the lower section. 
			// Also, consider why the results might be incorrect 
			// when entering Chinese keywords.
			String encodeKeyword=java.net.URLEncoder.encode(searchKeyword,"utf-8");
			this.url = "https://www.google.com/search?q="+encodeKeyword+"&oe=utf8&num=20";
			
			// this.url = "https://www.google.com/search?q="+searchKeyword+"&oe=utf8&num=20";
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

		URL u = new URL(url);
		URLConnection conn = u.openConnection();
		//set HTTP header
		conn.setRequestProperty("User-agent", "Chrome/107.0.5304.107");
		InputStream in = conn.getInputStream();

		InputStreamReader inReader = new InputStreamReader(in, "utf-8");
		BufferedReader bufReader = new BufferedReader(inReader);
		String line = null;

		while((line = bufReader.readLine()) != null)
		{
			retVal += line;
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
		
		/* 
		 * some Jsoup source
		 * https://jsoup.org/apidocs/org/jsoup/nodes/package-summary.html
		 * https://www.1ju.org/jsoup/jsoup-quick-start
 		 */
		
		//using Jsoup analyze html string
		Document doc = Jsoup.parse(content);
		
		//select particular element(tag) which you want 
		Elements lis = doc.select("div");
		lis = lis.select(".kCrYT");
		
		for(Element li : lis)
		{
		    try 
		    {
		        Elements anchorTags = li.select("a");
		        
		        // 檢查是否存在 <a> 標籤
		        if (!anchorTags.isEmpty()) {
		            String citeUrl = anchorTags.first().attr("href").replace("/url?q=", "");
		            String title = anchorTags.first().select(".vvjwJb").text();
		            
		            if(title.equals("")) 
		            {
		                continue;
		            }
		            
		            System.out.println("Title: " + title + " , url: " + citeUrl);
		            
		            WebPage rootPage = new WebPage(citeUrl, searchKeyword);
		            WebTree tree = new WebTree(rootPage);
		            HtmlScraping htmlScraping = new HtmlScraping(citeUrl);
		            htmlScraping.findChildren();
		            
		            webTreeList.add(tree);
		            
		            // put title and pair into HashMap
		            retVal.put(title, citeUrl);
		        }
		    } 
		    catch (IndexOutOfBoundsException e) 
		    {
		        e.printStackTrace();
		    }
		}
		
		return retVal;
	}
}