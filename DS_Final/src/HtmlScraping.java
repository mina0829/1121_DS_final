import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class HtmlScraping
{
    private String urlStr;
    private String content;
    private ArrayList<String> urls;

    public HtmlScraping(String urlStr)
    {
        this.urlStr = urlStr;
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

    public void findChildren() throws IOException
    {
        if (content == null)
        {
            content = fetchContent();
        }
        
        Document doc = Jsoup.parse(content);
		
		//select particular element(tag) which you want 
		Elements lis = doc.select("a");
		
		for (Element link : lis) {
			System.out.println("in for");
			String subdomain = link.attr("abs:href");
			System.out.println("Subdomain: " + subdomain);
		}
    }
}
