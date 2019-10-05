import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * @author paulalan
 * @create 2019/9/30 15:47
 */
public class AutomaticAlgorithm
{
	private HashMap<Integer, ArrayList> shortestSet = new HashMap<>();
	private int staticShortestLength;
	private int rows;
	private int cols;
	private int[][] saveArray;
	private HashMap<JLabel, Integer> terrain;
	private ArrayList<Integer> shortestPath;
	private int temp = 0;
	private ArrayList path;
	private int shortestLength = 0;
	private int count = 0;
	private double intelligence;


	public AutomaticAlgorithm(int rows, int cols, HashMap<JLabel, Integer> terrain, int[][] array, double intelligence)
	{
		this.rows = rows;
		this.cols = cols;
		this.terrain = terrain;
		this.saveArray = array;
		this.intelligence = intelligence;
	}

	public void calShortestPath()
	{
		for (int i = (rows - 1) * cols + 1; i <= rows * cols; i++)
		{
			shortestPath = new ArrayList<>();
			find(i);
			shortestSet.put(temp, shortestPath);
		}
		System.out.println("Shortest path found successfully!");
		Object[] temp = shortestSet.keySet().toArray();
		Arrays.sort(temp);
		shortestLength = (int) temp[0];
		path = shortestSet.get(temp[0]);
		staticShortestLength = shortestLength;
	}

	public void showShortestPath() throws InterruptedException
	{
		System.out.println(path);
		for (int len = 0; len < path.size(); len++)
		{
			JLabel currentLabel = Util.getKey(terrain, (int) path.get(len));
			currentLabel.setOpaque(true);
			currentLabel.setBackground(Color.blue);
			currentLabel.setForeground(Color.yellow);
//			Thread.sleep(1000);
		}
		JOptionPane.showMessageDialog(null,
				"Shortest Length is " + shortestLength,
				"Automatic Calculate Successful!", JOptionPane.INFORMATION_MESSAGE);
//		System.out.println(temp[0]);
	}

	int minimum(int num1, int num2, int num3)
	{
		int min = Math.min(num1, num2);
		min = Math.min(min, num3);

		return min;
	}


	public void find(int currentNumID)
	{
		int currentRow;
		int currentCol;
		int currentNum;
		currentRow = ((currentNumID % cols) == 0) ? (currentNumID / cols) : (currentNumID / cols) + 1;
		currentCol = ((currentNumID % cols) == 0) ? cols : (currentNumID % cols);
		currentNum = saveArray[currentRow - 1][currentCol - 1];
		shortestLength += currentNum;
		shortestPath.add(currentNumID);
//		count++;
//		System.out.println("Aa count==" + count);

		double temp = Math.ceil((1 - intelligence) * rows);
		if (currentRow > (int) (temp) + 1)
		{
			int topNumID = currentNumID - cols;
			if (cols == 1)
			{
				find(topNumID);
			} else if (currentCol == 1)
			{
				int topRightID = currentNumID - cols + 1;
				int min = Math.min(Integer.parseInt(Util.getKey(terrain, topNumID).getText()),
						Integer.parseInt(Util.getKey(terrain, topRightID).getText()));
				find((min == Integer.parseInt(Util.getKey(terrain, topNumID).getText())) ? topNumID : topRightID);
			} else if (currentCol == cols)
			{
				int topLeftID = currentNumID - cols - 1;
				int min = Math.min(Integer.parseInt(Util.getKey(terrain, topNumID).getText()),
						Integer.parseInt(Util.getKey(terrain, topLeftID).getText()));
				find((min == Integer.parseInt(Util.getKey(terrain, topNumID).getText())) ? topNumID : topLeftID);
			} else
			{
				int topRightID = currentNumID - cols + 1;
				int topLeftID = currentNumID - cols - 1;
				int min = minimum(Integer.parseInt(Util.getKey(terrain, topNumID).getText()),
						Integer.parseInt(Util.getKey(terrain, topRightID).getText()),
						Integer.parseInt(Util.getKey(terrain, topLeftID).getText()));
				if (min == Integer.parseInt(Util.getKey(terrain, topNumID).getText()))
				{
					find(topNumID);
				} else if (min == Integer.parseInt(Util.getKey(terrain, topRightID).getText()))
				{
					find(topRightID);
				} else
				{
					find(topLeftID);
				}
			}
		} else
		{
			System.out.println("Find To Top");
			System.out.println(shortestLength);
			this.temp = shortestLength;
			shortestLength = 0;
		}
	}

	public int getStaticShortestLength()
	{
		return staticShortestLength;
	}
}
