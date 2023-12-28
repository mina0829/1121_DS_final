import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.TreeMap;
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

import org.jsoup.HttpStatusException;

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
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    }
    
    
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
		keywords = new ArrayList<Keyword>();
		Keyword keyworda = new Keyword("歌詞",0,4);
		Keyword keywordb = new Keyword("作詞",0,3);
		Keyword keywordc = new Keyword("字典",0,-2);
		Keyword keywordd = new Keyword("新聞",0,-2);
		Keyword keyworde = new Keyword("lyrics",0,3);
		Keyword keywordf = new Keyword("MV",0,3);
		Keyword keywordg = new Keyword("Music",0,3);
		Keyword keywordh = new Keyword("旅遊",0, -4);
		Keyword keywordi = new Keyword("購物",0, -4);
		
		keywords.add(keyworda);
		keywords.add(keywordb);
		keywords.add(keywordc);
		keywords.add(keywordd);
		keywords.add(keyworde);
		keywords.add(keywordf);
		keywords.add(keywordg);
		keywords.add(keywordh);
		keywords.add(keywordi);

		
		//連接到GoogleQuery class，進行搜尋
		GoogleQuery google = new GoogleQuery(request.getParameter("keyword"), keywords);
		
		ArrayList <WebTree>wbrs = google.query();
		
		
		int num = 0;
		
		
		String[][] rs = new String[wbrs.size()][2];
		request.setAttribute("query",rs);
		int i=0;
		
		for(WebTree t: wbrs) {
			rs[i][0]=t.root.webPage.name+"";
			rs[i][1]=t.root.webPage.url;
			i++;
		
		}
	
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
