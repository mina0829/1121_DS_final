import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;

public class WordCounter {
	private String urlStr;
    private String content;
    private FetchContent fC;
    
    public WordCounter(String urlStr){
    	this.urlStr = urlStr;
    	fC = new FetchContent(urlStr);
    }
    
    public int BoyerMoore(String T, String P){
    	int i = P.length() -1;
    	int j = P.length() -1;
           
    	while (i < T.length()) {
            if (P.charAt(j) == T.charAt(i)) {
                if (j == 0) {
                    return i; // 找到匹配
                }
                i--;
                j--;
            } else {
                int lastOccurrence = last(T.charAt(i), P);
                i = i + P.length() - Math.min(j, 1 + lastOccurrence);
                j = P.length() - 1;
            }
        }
        return -1;
    }

    public int last(char c, String P){
    	//從最後一個字符開始比較，找到目前比較字串中，與"關鍵字字尾"相同的字母索引值
    	for (int i = P.length() - 1; i >= 0; i--) {
            if (P.charAt(i) == c) {
                return i;
            }
        }
        return -1;
    }
    
    public int countKeyword(String keyword) throws IOException{
		if (content == null){
		    content = fC.getContent();
		}
		
		//To do a case-insensitive search, we turn the whole content and keyword into upper-case:
		content = content.toUpperCase();
		keyword = keyword.toUpperCase();
	
		int retVal = 0; 
		int keywordLength = keyword.length();
	    int contentLength = content.length();
	    int index = 0;

	    while (index <= contentLength - keywordLength) {
	        int j = keywordLength - 1;

	        while (j >= 0 && keyword.charAt(j) == content.charAt(index + j)) {
	            j--;
	        }
	        //關鍵字最後一個字母，與目前比較的字串的字尾，是否相同
	        //相同的話就再往前比，不同的話就不進while迴圈

	        if (j < 0) {
	            retVal++; 
	            index += keywordLength;
	            // 找到匹配，前進一整個字串，繼續找
	        }else{
	            int lastOccurrence = last(content.charAt(index + j), keyword);
	            int badCharShift = Math.max(1, j - lastOccurrence);
	            index += badCharShift;
	            //沒找到匹配，用last方法比較出可以移到最遠的位置：與比較字串的字尾對齊or跳一整個關鍵字長
	        }
	    }
		return retVal;
    }
}