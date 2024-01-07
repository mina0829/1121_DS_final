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
import java.util.LinkedHashMap;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Collections;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GoogleQuery {
	public String searchKeyword;
	public String url;
	public String content;
	public ArrayList<WebTree> webTreeList;
	public ArrayList<String> childrenUrl;
	public ArrayList<Keyword> keywords;
	public WebTreeSort TS;
	public ArrayList<WebNode>children;


	public GoogleQuery(String searchKeyword, ArrayList<Keyword> keywords, int searchNum) {
		this.searchKeyword = searchKeyword;
		this.webTreeList = new ArrayList<WebTree>();
		this.childrenUrl = new ArrayList<String>();
		this.keywords = keywords;
		int count =searchNum+1;
		
		//this.TS = new WebTreeSort();
		try {
			// 產生google搜尋網址
			String encodeKeyword = java.net.URLEncoder.encode(searchKeyword, "utf-8");
			this.url = "https://www.google.com/search?q=" + encodeKeyword + "&oe=utf8&num="+count;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println(url);
	}

	private String fetchContent() throws IOException {
		String retVal = "";
		// 可以嘗試處理HTTP400、403的問題，不然連結都讀不到
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

	

	public ArrayList<WebTree>query() throws IOException {
		if (content == null) {
			content = fetchContent();
		}

		HashMap<String, String> retVal = new HashMap<String, String>();
		
		ArrayList<WebTree> finalRank = new ArrayList<WebTree>();
		this.children = new ArrayList<WebNode>();
		
		// 讀取搜尋頁面的內容
		Document doc = Jsoup.parse(content);

		// 挑選出想要的內容；搜尋結果之標題與網址
		Elements lis = doc.select("div");
		lis = lis.select(".kCrYT");
	
		if(!lis.isEmpty()) {
		for (Element li : lis) {
			try {
				String citeUrl = li.select("a").get(0).attr("href").replace("/url?q=", "");
				String title = li.select("a").get(0).select(".vvjwJb").text();
				

				if (title.equals("")) {
					continue;
				}

				int index = citeUrl.indexOf("&");
				
				if (index != -1) {
					citeUrl = citeUrl.substring(0, index);
				}

				// 解碼獲得之連結
				String newCiteUrl = URLDecoder.decode(citeUrl, "UTF-8");

				System.out.println("Title: " + title + " , url: " + newCiteUrl);
				
				

				// 將獲得之連結建立為WebPage，並作為WebTree的root
				WebPage rootPage = new WebPage(newCiteUrl);
				rootPage.setTitle(title);
				
				WebTree tree = new WebTree(rootPage);

				// 用HtmlScraping class讀取此連結之網頁內容、抓出children的連結
				HtmlScraping htmlScraping = new HtmlScraping(newCiteUrl);
								
				//網址不是想要的就直接排除不浪費時間抓
				if(newCiteUrl.contains("org")) {
					tree.root.unWanted(0);
				}else if(newCiteUrl.contains("gov")) {
					tree.root.unWanted(0);
				}else if(newCiteUrl.contains("facebook")) {
					tree.root.unWanted(0);
				}else if(newCiteUrl.contains("spotify")) {
					tree.root.Wanted(0);
				}else {
					// 將children加入tree中
					int count =0;
					childrenUrl = htmlScraping.findChildren();
					for (String url : childrenUrl) {
						WebPage children =new WebPage(url);
						WebNode childNode = new WebNode(children);
						
						children.url = url;
						tree.root.addChild(childNode);
						System.out.println("child: "+url);
						this.children.add(childNode);
						
					}
					
					tree.setPostOrderScore(keywords);
					
				}
				

				
				finalRank.add(tree);
				
				
				System.out.println(tree.root.nodeScore);
			

			} catch (IndexOutOfBoundsException e) {
				// e.printStackTrace();
			}
		}
		}
		
		WebTreeSort sortT = new WebTreeSort(finalRank);
		sortT.sort();
		finalRank = sortT.getSortedList();
		
		return finalRank;
		    
		
	}
	
	public ArrayList<WebNode> getChildren(){
		return this.children;
	}
	
	
}
