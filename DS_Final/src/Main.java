import java.io.IOException;

public class Main 
{
	public static void main(String[] args) 
	{
		try 
		{
			System.out.println(new GoogleQuery("Pokemon").query());
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}