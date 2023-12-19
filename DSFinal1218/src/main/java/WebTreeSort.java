import java.util.ArrayList;

public class WebTreeSort {
	public ArrayList<WebTree> webTreeList;
	
	public WebTreeSort() {
		this.webTreeList = new ArrayList<WebTree>();
	}
	
	//以下就是排序，若最後順序出錯就調整這邊
	public void sort(ArrayList<WebTree> webTreeList) {
		
		if (webTreeList.size() == 0)
		{
			System.out.println("InvalidOperation");
		}
		else
		{
			quickSort(0, webTreeList.size() - 1);
		}
	}
	
	private void quickSort(int leftbound, int rightbound)
	{
		if (leftbound < rightbound) {
            int pivotIndex = partition(leftbound, rightbound);
            quickSort(leftbound, pivotIndex - 1);
            quickSort(pivotIndex + 1, rightbound);
        }
	}

	private void swap(int aIndex, int bIndex)
	{
		WebTree temp = webTreeList.get(aIndex);
		webTreeList.set(aIndex, webTreeList.get(bIndex));
		webTreeList.set(bIndex, temp);
	}
	
	private int partition(int left, int right) {
        WebTree pivot = webTreeList.get(right);
        int i = left - 1;

        for (int j = left; j < right; j++) {
            if (webTreeList.get(j).compareTo(pivot) >= 0) {
                i++;
                swap(i, j);
            }
        }

        swap(i + 1, right);
        return i + 1;
    }
}