import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Stack;

public class HtmlScraping
{
    private String urlStr;
    private String content;

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

        /* Create a stack to store the tag */
        Stack<String> tagStack = new Stack<>();

        int indexOfOpen = 0;

        while ((indexOfOpen = content.indexOf("<", indexOfOpen)) != -1)
        {
        	System.out.println("in while");
            // Get full tag. e.g. "<div id="abcdefg">","</a>","</div>"...
            int indexOfClose = content.indexOf(">", indexOfOpen);
            
            // 確認 indexOfOpen 是否小於字符串的長度
            if (indexOfOpen < content.length() && indexOfClose != -1) {
            	System.out.println("in if");
                String fullTag = content.substring(indexOfOpen + 1, indexOfClose);
                
                // 使用 Jsoup 解析 HTML 標籤
                Document doc = Jsoup.parse(fullTag);
                Elements links = doc.select("a[href]");

                for (Element link : links) {
                    // 取得子網站連結
                    String subdomain = link.attr("abs:href");
                    System.out.println("Subdomain: " + subdomain);
                }

                indexOfOpen = indexOfClose + 1;
            } else {
                // 如果 indexOfOpen 大於等於字符串的長度，結束循環
                break;
            }
        }
    }
}
