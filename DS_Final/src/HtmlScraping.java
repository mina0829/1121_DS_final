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
}