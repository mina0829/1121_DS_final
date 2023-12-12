import java.io.IOException;
import java.util.ArrayList;

public class WebTree
{
	public WebNode root;

	public WebTree(WebPage rootPage)
	{
		this.root = new WebNode(rootPage);
	}

	public void setPostOrderScore(ArrayList<Keyword> keywords) throws IOException
	{
		setPostOrderScore(root, keywords);
	}

	private void setPostOrderScore(WebNode startNode, ArrayList<Keyword> keywords) throws IOException
	{
	    startNode.setNodeScore(keywords);
	}

	private String repeat(String str, int repeat)
	{
		String retVal = "";
		for (int i = 0; i < repeat; i++)
		{
			retVal += str;
		}
		return retVal;
	}
}