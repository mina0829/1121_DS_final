import java.util.Scanner;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main 
{
	public static void main(String[] args) 
	{
		try 
		{
			File file = new File("input.txt");
			Scanner fileSc = new Scanner(file);
			ArrayList keywords = new ArrayList<Keyword>();
			
			if(fileSc.hasNext()) {
				String name = fileSc.next();
        		int weight = fileSc.nextInt();
        		Keyword keyword = new Keyword(name, 0, weight);
        		keywords.add(keyword);
			}
			
			System.out.println("Enter Keyword: ");
			Scanner sc = new Scanner(System.in);
			String keyword = sc.next();
			System.out.println(new GoogleQuery(keyword, keywords).query());
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}