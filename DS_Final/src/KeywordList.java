import java.util.*;

public class KeywordList
{
	private LinkedList<Keyword> lst;

	public KeywordList()
	{
		this.lst = new LinkedList<Keyword>();
	}

	public void add(Keyword keyword)
	{
		boolean added = false;
		for (int i = 0; i < lst.size(); i++)
		{
			Keyword k = lst.get(i);
			if (keyword.count < k.count){
				// If the count is smaller than the original, place it in the front.
				lst.add(i, keyword);
				added = true;
				break;
			}
			if(keyword.count == k.count) {
				// If their count are same, smaller weight placed in the front
				if(keyword.weight <= k.weight) {
					lst.add(i, keyword);
					added = true;
					break;
				}else {
					lst.add(i+1, keyword);
					added = true;
					break;
				}
			}
		}
		
		if(!added) {
			lst.add(keyword);
		}
	}
	
	public void resetCount() {
		for(int i =0 ; i < lst.size(); i++) {
			lst.get(i).count = 0;
		}
	}

	public void outputIndex(int i)
	{
		if (i >= lst.size())
		{
			System.out.println("InvalidOperation");
			return;
		}
		Keyword k = lst.get(i);
		System.out.println(k);
	}

	public void outputCount(int c)
	{
		LinkedList<Keyword> results = new LinkedList<>();
		for (int i = 0; i < lst.size(); i++)
		{
			Keyword k = lst.get(i);
			if (k.count == c)
			{
				results.add(k);
			}
		}
		if (results.isEmpty())
		{
			System.out.println("NotFound");
		}
		else
		{
			printKeywordList(results);
		}
	}

	public void outputHas(String pattern)
	{
		LinkedList<Keyword> results = new LinkedList<>();
		for (int i = 0; i < lst.size(); i++)
		{
			Keyword k = lst.get(i);
			if (k.name.contains(pattern))
			{
				results.add(k);
			}
		}
		if (results.isEmpty())
		{
			System.out.println("NotFound");
		}
		else
		{
			printKeywordList(results);
		}
	}

	public void outputName(String pattern)
	{
		LinkedList<Keyword> results = new LinkedList<>();
		for (int i = 0; i < lst.size(); i++)
		{
			Keyword k = lst.get(i);
			if (k.name.equals(pattern))
			{
				results.add(k);
			}
		}
		if (results.isEmpty())
		{
			System.out.println("NotFound");
		}
		else
		{
			printKeywordList(results);
		}
	}

	public void outputFirstN(int n)
	{
		if (n > lst.size())
		{
			System.out.println("InvalidOperation");
			return;
		}
		LinkedList<Keyword> found = new LinkedList<>();

		for (int i = 0; i < n; i++)
		{
			Keyword k = lst.get(i);
			found.add(k);
		}
		printKeywordList(found);
	}

	public void outputScore()
	{
		float results = 0;
		for(Keyword k: lst) {
			results += (k.count*k.weight);
		}

		System.out.println(results);
	}

	public void deleteIndex(int i)
	{
		if (i >= lst.size())
		{
			return;
		}
		lst.remove(i);
	}

	public void deleteCount(int c)
	{
		LinkedList<Keyword> found = new LinkedList<>();
		for (Keyword kw : lst) {
            if(kw.count == c) {
            	found.add(kw);
            }
        }
		
		if (!found.isEmpty())
		{
			lst.removeAll(found);
		}
	}

	public void deleteHas(String pattern)
	{
		LinkedList<Keyword> found = new LinkedList<>();
		for (Keyword kw : lst) {
            if((kw.name).contains(pattern)) {
            	found.add(kw);
            }
        }
		
		if (!found.isEmpty())
		{
			lst.removeAll(found);
		}
	}

	public void deleteName(String name)
	{
		LinkedList<Keyword> found = new LinkedList<>();
		for (Keyword kw : lst) {
            if((kw.name).equals(name)) {
            	found.add(kw);
            }
        }
		
		if (!found.isEmpty())
		{
			lst.removeAll(found);
		}
	}

	public void deleteFirstN(int n)
	{
		LinkedList<Keyword> found = new LinkedList<>();
		for(int i = 0; i < n; i++) {
			found.add(lst.get(i));
		}
		
		if (!found.isEmpty())
		{
			lst.removeAll(found);
		}
	}

	public void deleteAll()
	{
		lst = new LinkedList<Keyword>();
	}

	private void printKeywordList(LinkedList<Keyword> kLst)
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < kLst.size(); i++)
		{
			Keyword k = kLst.get(i);
			if (i > 0)
				sb.append(" ");
			sb.append(k.toString());
		}
		System.out.println(sb.toString());
	}
}