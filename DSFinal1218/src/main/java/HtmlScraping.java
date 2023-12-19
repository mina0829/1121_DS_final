import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;

public class HtmlScraping
{
    private String urlStr;
    private String content;
    private ArrayList<String> urls;
    private FetchContent fC;

    public HtmlScraping(String urlStr)
    {
        this.urlStr = urlStr;
        this.urls = new ArrayList<String>();
        fC = new FetchContent(urlStr);
    }
    
    //抓取內容中的子網站連結
    public ArrayList<String> findChildren() throws IOException
    {
        if (content == null)
        {
            content = fC.getContent();
        }
        
        Document doc = Jsoup.parse(content);
		
		//select particular element(tag) which you want 
		Elements lis = doc.select("a");
		
		for (Element link : lis) {
			String subdomain = link.attr("abs:href");
			//System.out.println("Subdomain: " + subdomain);
			if(!subdomain.equals("")) {
				String newurl = URLDecoder.decode(subdomain, "UTF-8");
				urls.add(newurl);
			}
		}
		return urls;
    }
}