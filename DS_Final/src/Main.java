import java.io.IOException;
import java.util.Scanner;

public class Main 
{
	public static void main(String[] args) 
	{
		try 
		{
			System.out.println("Enter Keyword: ");
			Scanner sc = new Scanner(System.in);
			String keyword = sc.next();
			System.out.println(new GoogleQuery(keyword).query());
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}