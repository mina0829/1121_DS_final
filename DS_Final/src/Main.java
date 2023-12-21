import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Main")
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ArrayList<Keyword> keywords;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Main() {
        super();
        // TODO Auto-generated constructor stub
        /*try 
		{
			File file = new File("input.txt");
			Scanner fileSc = new Scanner(file);
			keywords = new ArrayList<Keyword>();
			
			if(fileSc.hasNext()) {
				String name = fileSc.next();
        		int weight = fileSc.nextInt();
        		Keyword keyword = new Keyword(name, 0, weight);
        		keywords.add(keyword);
			}
		}catch(Exception e) {
			
		}
    }*/

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		if(request.getParameter("keyword")== null) {
			String requestUri = request.getRequestURI();
			request.setAttribute("requestUri", requestUri);
			request.getRequestDispatcher("Search.jsp").forward(request, response);
			return;
		}
		//手動添加keywords（不然讀不到）
		keywords = new ArrayList<Keyword>();
		Keyword keyworda = new Keyword("歌詞",0,4);
		Keyword keywordb = new Keyword("作詞",0,3);
		Keyword keywordc = new Keyword("字典",0,-2);
		Keyword keywordd = new Keyword("新聞",0,-2);
		Keyword keyworde = new Keyword("lyrics",0,4);
		Keyword keywordf = new Keyword("MV",0,3);
		
		keywords.add(keyworda);
		keywords.add(keywordb);
		keywords.add(keywordc);
		keywords.add(keywordd);
		keywords.add(keyworde);
		keywords.add(keywordf);
		//連接到GoogleQuery class，進行搜尋
		GoogleQuery google = new GoogleQuery(request.getParameter("keyword"), keywords);
		HashMap<String, String> query = google.query();
		
		//query丟到googleitem跑出結果
		//hashmap無法依序排放，這邊助教原本的hashmap key值為title
		String[][] s = new String[query.size()][2];
		request.setAttribute("query", s);
		int num = 0;
		for(Entry<String, String> entry : query.entrySet()) {
		    String key = entry.getKey();
		    String value = entry.getValue();
		    s[num][0] = key;
		    s[num][1] = value;
		    num++;
		}
		
		//如果要直接讀取webTreeList(排序過的）應該是這樣寫，但現在好像webListTree裡只有加到一個（詳細可以看google query那邊，我把回傳改成ArrayList方便有序排放
		//另一個可能的解決辦法是用TreeMap然後直接拿算出來的分數當key值排放，但這樣就不用寫sorting，感覺有點作弊？
		/*String[][] rs = new String[wbrs.size()][2];
		request.setAttribute("query",rs);
		int i=0;
		
		for(WebTree t: wbrs) {
			rs[i][0]=t.root.nodeScore+"";
			rs[i][1]=t.root.webPage.url;
			i++;
			System.out.println(i);
		}*/
		request.getRequestDispatcher("googleitem.jsp")
		 .forward(request, response); 
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
