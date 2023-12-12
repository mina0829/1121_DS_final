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

    public HtmlScraping(String urlStr)
    {
        this.urlStr = urlStr;
        this.urls = new ArrayList<String>();
    }

    private String fetchContent() throws IOException
	{
		URL url = new URL(this.urlStr);
		URLConnection conn = url.openConnection();
		InputStream in = conn.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(in));

		String retVal = "";

		String line = null;
		while ((line = br.readLine()) != null)
		{
			retVal = retVal + line + "\n";
		}

		return retVal;
	}

    public ArrayList<String> findChildren() throws IOException
    {
        if (content == null)
        {
            content = fetchContent();
        }
        
        Document doc = Jsoup.parse(content);
		
		//select particular element(tag) which you want 
		Elements lis = doc.select("a");
		
		for (Element link : lis) {
			String subdomain = link.attr("abs:href");
			//System.out.println("Subdomain: " + subdomain);
			if(!subdomain.equals("")) {
				String newurl = URLDecoder.decode(subdomain, "UTF-8");
				if (!newurl.toLowerCase().contains("facebook")|| !newurl.toLowerCase().contains("tel") || !newurl.toLowerCase().contains("sharer")) {
			        //System.out.println(newurl); //有加到
			        urls.add(newurl);
			    }
			}
		}
		return urls;
    }
}
