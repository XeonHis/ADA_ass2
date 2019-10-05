import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.Timer;

/**
 * @author paulalan
 * @create 2019/9/18 18:58
 */
public class GridLayoutPosition extends JFrame
{
	JLabel top;
	JLabel top_left;
	JLabel top_right;
	int sum = 0;
	HashMap<Integer, Integer> findResult = new HashMap<>();
	int[][] saveArray;
	int rows;
	int cols;
	HashMap<JLabel, Integer> terrain = new HashMap<>();
	ArrayList<Integer> shortestPath;
	HashMap<Integer, ArrayList> shortestSet = new HashMap<>();
	int temp = 0;


	public static JLabel getKey(HashMap<JLabel, Integer> map, int value)
	{
		JLabel key = null;
		for (JLabel getKey : map.keySet())
		{
			if (map.get(getKey) == value)
			{
				key = getKey;
			}
		}
		return key;
	}

	public GridLayoutPosition(int rows, int cols) throws InterruptedException
	{
		this.rows = rows;
		this.cols = cols;
		Container c = getContentPane();
		setLayout(new GridLayout(rows, cols, 0, 0));
//		HashMap<JLabel, Integer> terrain = new HashMap<>();
		int[][] array = new int[rows][cols];
		for (int i = 1; i <= rows * cols; i++)
		{
			int randomNumber = (int) (Math.random() * 20 - 5);
			JLabel jl = new JLabel(String.valueOf(randomNumber));
			terrain.put(jl, i);
			jl.setHorizontalAlignment(SwingConstants.CENTER);
			jl.setBorder(BorderFactory.createLineBorder(Color.black));
			if (i > (rows - 1) * cols)
			{
				jl.addMouseListener(new MouseAdapter()
				{
					@Override
					public void mouseClicked(MouseEvent e)
					{
						clickFun(jl, terrain, cols, rows);
					}
				});
			}
			c.add(jl);
		}
		for (int i = 1; i <= rows; i++)
		{
			for (int j = 1 + (i - 1) * cols; j <= cols * i; j++)
			{
				if (j % cols == 0)
				{
					array[i - 1][cols - 1] = Integer.parseInt(getKey(terrain, j).getText());
				} else
				{
					array[i - 1][(j % cols) - 1] = Integer.parseInt(getKey(terrain, j).getText());
				}
			}
		}
		setSize(600, 600);
		setTitle("Grid Layout");
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		for (int i = 0; i <= rows - 1; i++)
		{
			for (int j = 0; j <= cols - 1; j++)
			{
				System.out.print(array[i][j] + " ");
			}
			System.out.println();
		}
		saveArray = array;

		for (int i = (rows - 1) * cols + 1; i <= rows * cols; i++)
		{
			shortestPath = new ArrayList<>();
			find(i);
			shortestSet.put(temp, shortestPath);
		}
		System.out.println(shortestSet);
		Thread.sleep(5000);
		showShortestPath();
//		find(rows * cols);
	}

	public void showShortestPath() throws InterruptedException
	{
		Object[] temp = shortestSet.keySet().toArray();
		Arrays.sort(temp);
		ArrayList path = shortestSet.get(temp[0]);
		System.out.println(path);
		for (int len = 0; len < path.size(); len++)
		{
			JLabel currentLabel = getKey(terrain, (int) path.get(len));
			currentLabel.setOpaque(true);
			currentLabel.setBackground(Color.blue);
			currentLabel.setForeground(Color.yellow);
			Thread.sleep(1000);
		}
		JOptionPane.showMessageDialog(null, "Shortest Length is "+temp[0],
				"Automatic Calculate Successful!", JOptionPane.INFORMATION_MESSAGE);
//		System.out.println(temp[0]);
	}

	private void clickFun(JLabel jl, HashMap<JLabel, Integer> terrain, int cols, int rows)
	{
		int flag = terrain.get(jl) % cols;
		System.out.println(terrain.get(jl));
		sum += Integer.parseInt(jl.getText());
		jl.setOpaque(true);
		jl.setBackground(Color.red);
		if (terrain.get(jl) > cols)
		{
			//top JLabel
			top = getKey(terrain, (terrain.get(jl) - cols));
			top.setOpaque(true);
			top.setBackground(Color.green);
			top.addMouseListener(new MouseAdapter()
			{
				@Override
				public void mouseClicked(MouseEvent e)
				{
					clickFun(top, terrain, cols, rows);
				}
			});
			System.out.println("top add");
			switch (flag)
			{
				case 0:
					//top left
					top_left = getKey(terrain, (terrain.get(jl) - cols - 1));
					top_left.setOpaque(true);
					top_left.setBackground(Color.green);

					top_left.addMouseListener(new MouseAdapter()
					{
						@Override
						public void mouseClicked(MouseEvent e)
						{
							clickFun(top_left, terrain, cols, rows);
						}
					});
					System.out.println("top_left add");
					break;
				case 1:
					//top right
					top_right = getKey(terrain, (terrain.get(jl) - cols + 1));
					top_right.setOpaque(true);
					top_right.setBackground(Color.green);

					top_right.addMouseListener(new MouseAdapter()
					{
						@Override
						public void mouseClicked(MouseEvent e)
						{
							clickFun(top_right, terrain, cols, rows);
						}
					});
					System.out.println("top_right add");
					break;
				default:
					//top left
					top_left = getKey(terrain, (terrain.get(jl) - cols - 1));
					top_left.setOpaque(true);
					top_left.setBackground(Color.green);
					top_left.addMouseListener(new MouseAdapter()
					{
						@Override
						public void mouseClicked(MouseEvent e)
						{
							clickFun(top_left, terrain, cols, rows);
						}
					});
					System.out.println("top_left add");
					//top right
					top_right = getKey(terrain, (terrain.get(jl) - cols + 1));
					top_right.setOpaque(true);
					top_right.setBackground(Color.green);
					top_right.addMouseListener(new MouseAdapter()
					{
						@Override
						public void mouseClicked(MouseEvent e)
						{
							clickFun(top_right, terrain, cols, rows);
						}
					});
					System.out.println("top_right add");
					break;

			}
		} else
		{
			System.out.println("To top! Sum is " + sum);
			String info = "Whole steps are " + sum;
			JOptionPane.showMessageDialog(null, info,
					"You have completed!", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	int minimum(int num1, int num2, int num3)
	{
		int min = Math.min(num1, num2);
		min = Math.min(min, num3);

		return min;
	}

	int shortestLength = 0;
	int count = 0;

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
		count++;
		if (currentRow != 1)
		{
			int topNumID = currentNumID - cols;
			if (cols==1){
				find(topNumID);
			}
			else if (currentCol == 1)
			{
				int topRightID = currentNumID - cols + 1;
				int min = Math.min(Integer.parseInt(getKey(terrain, topNumID).getText()),
						Integer.parseInt(getKey(terrain, topRightID).getText()));
				find((min == Integer.parseInt(getKey(terrain, topNumID).getText())) ? topNumID : topRightID);
			} else if (currentCol == cols)
			{
				int topLeftID = currentNumID - cols - 1;
				int min = Math.min(Integer.parseInt(getKey(terrain, topNumID).getText()),
						Integer.parseInt(getKey(terrain, topLeftID).getText()));
				find((min == Integer.parseInt(getKey(terrain, topNumID).getText())) ? topNumID : topLeftID);
			} else
			{
				int topRightID = currentNumID - cols + 1;
				int topLeftID = currentNumID - cols - 1;
				int min = minimum(Integer.parseInt(getKey(terrain, topNumID).getText()),
						Integer.parseInt(getKey(terrain, topRightID).getText()),
						Integer.parseInt(getKey(terrain, topLeftID).getText()));
				if (min == Integer.parseInt(getKey(terrain, topNumID).getText()))
				{
					find(topNumID);
				} else if (min == Integer.parseInt(getKey(terrain, topRightID).getText()))
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
			temp = shortestLength;
			shortestLength = 0;
		}
	}

	public void find(int currentNumID, double ratio)
	{

	}


	public static void main(String[] args) throws InterruptedException
	{
		int rows;
		int cols;
		rows = Integer.parseInt(new Scanner(System.in).nextLine());
		cols = Integer.parseInt(new Scanner(System.in).nextLine());
		new GridLayoutPosition(rows, cols);
	}
}
