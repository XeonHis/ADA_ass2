import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author paulalan
 * @create 2019/9/27 19:59
 */
public class ManualAlgorithm
{

	private JLabel top;
	private JLabel top_left;
	private JLabel top_right;
	private static int sum;
	private List<MyJPanel> myJPanels = new ArrayList<>();
	private int count = 0;


	public int getSum()
	{
		return sum;
	}

	public void notifyAllObservers()
	{
		for (MyJPanel myJPanel : myJPanels)
		{
			myJPanel.update();
			System.out.println("-- update --");
		}
	}

	public void attach(MyJPanel myJPanel)
	{
		myJPanels.add(myJPanel);
	}

	public void setSum(int addNum)
	{
		sum += addNum;
		notifyAllObservers();
	}

	protected void clickFun(JLabel jl, HashMap<JLabel, Integer> terrain,
							int cols, int rows, int[][] array, double intelligence) throws InterruptedException
	{
		count++;
		System.out.println("count== " + count);
		jl.removeMouseListener(jl.getMouseListeners()[0]);

		int flag = terrain.get(jl) % cols;
		System.out.println(terrain.get(jl));
//		sum += Integer.parseInt(jl.getText());
		setSum(Integer.parseInt(jl.getText()));
		System.out.println(sum);
		jl.setOpaque(true);
		jl.setBackground(Color.red);
		double temp = Math.floor(intelligence * rows);
		if (count < (int) temp)
		{
			if (terrain.get(jl) > cols)
			{
				//top JLabel
				top = Util.getKey(terrain, (terrain.get(jl) - cols));
				top.setOpaque(true);
				top.setBackground(Color.green);
				top.addMouseListener(new MouseAdapter()
				{
					@Override
					public void mouseClicked(MouseEvent e)
					{
						if (cols != 1 && flag != 0)
						{
							if (flag != 1)
							{
								top_left.removeMouseListener(top_left.getMouseListeners()[0]);
							}
							top_right.removeMouseListener(top_right.getMouseListeners()[0]);
						}
						try
						{
							clickFun(top, terrain, cols, rows, array, intelligence);
						} catch (InterruptedException ex)
						{
							ex.printStackTrace();
						}
					}
				});
				System.out.println("top add");
				switch (flag)
				{
					case 0:
						if (cols != 1)
						{
							//top left
							top_left = GridLayoutPosition.getKey(terrain, (terrain.get(jl) - cols - 1));
							top_left.setOpaque(true);
							top_left.setBackground(Color.green);

							top_left.addMouseListener(new MouseAdapter()
							{
								@Override
								public void mouseClicked(MouseEvent e)
								{
									top.removeMouseListener(top.getMouseListeners()[0]);
									try
									{
										clickFun(top_left, terrain, cols, rows, array, intelligence);
									} catch (InterruptedException ex)
									{
										ex.printStackTrace();
									}
								}
							});
							System.out.println("top_left add");
						}
						break;
					case 1:
						//top right
						top_right = Util.getKey(terrain, (terrain.get(jl) - cols + 1));
						top_right.setOpaque(true);
						top_right.setBackground(Color.green);

						top_right.addMouseListener(new MouseAdapter()
						{
							@Override
							public void mouseClicked(MouseEvent e)
							{
								top.removeMouseListener(top.getMouseListeners()[0]);
//							top_left.removeMouseListener(top_left.getMouseListeners()[0]);
								try
								{
									clickFun(top_right, terrain, cols, rows, array, intelligence);
								} catch (InterruptedException ex)
								{
									ex.printStackTrace();
								}
							}
						});
						System.out.println("top_right add");
						break;
					default:
						//top left
						top_left = Util.getKey(terrain, (terrain.get(jl) - cols - 1));
						top_left.setOpaque(true);
						top_left.setBackground(Color.green);
						top_left.addMouseListener(new MouseAdapter()
						{
							@Override
							public void mouseClicked(MouseEvent e)
							{
								top.removeMouseListener(top.getMouseListeners()[0]);
								top_right.removeMouseListener(top_right.getMouseListeners()[0]);
								try
								{
									clickFun(top_left, terrain, cols, rows, array, intelligence);
								} catch (InterruptedException ex)
								{
									ex.printStackTrace();
								}
							}
						});
						System.out.println("top_left add");
						//top right
						top_right = Util.getKey(terrain, (terrain.get(jl) - cols + 1));
						top_right.setOpaque(true);
						top_right.setBackground(Color.green);
						top_right.addMouseListener(new MouseAdapter()
						{
							@Override
							public void mouseClicked(MouseEvent e)
							{
								top.removeMouseListener(top.getMouseListeners()[0]);
								top_left.removeMouseListener(top_left.getMouseListeners()[0]);
								try
								{
									clickFun(top_right, terrain, cols, rows, array, intelligence);
								} catch (InterruptedException ex)
								{
									ex.printStackTrace();
								}
							}
						});
						System.out.println("top_right add");
						break;

				}
			} else
			{
				toTop(terrain, cols, rows, array, intelligence);
			}
		} else
		{
			toTop(terrain, cols, rows, array, intelligence);
		}
	}

	private void toTop(HashMap<JLabel, Integer> terrain, int cols, int rows, int[][] array, double intelligence) throws InterruptedException
	{
		System.out.println("To top! Sum is " + sum);
		AutomaticAlgorithm aa = new AutomaticAlgorithm(rows, cols, terrain, array,intelligence);
		aa.calShortestPath();

		System.out.println(aa.getStaticShortestLength());
		if (sum > aa.getStaticShortestLength())
		{
			System.out.println("Not the shortest");
			int confirm = JOptionPane.showConfirmDialog(null,
					"Your steps are " + sum + ", not the shortest! Do you want to see the shortest path?" +
							" Click NO to try again, Click YES to show correct answer.",
					"Oops...", JOptionPane.YES_NO_OPTION);
			if (confirm == 0)
			{
				aa.showShortestPath();
			} else if (confirm == 1)
			{
				count=0;
				sum = 0;
				for (int i = 1; i <= rows * cols; i++)
				{
					JLabel currentLabel = Util.getKey(terrain, i);
					currentLabel.setBackground(Color.white);
//					System.out.println("before " + currentLabel.getMouseListeners().length);
					if (currentLabel.getMouseListeners().length != 0)
					{
						currentLabel.removeMouseListener(currentLabel.getMouseListeners()[0]);
					}
//					System.out.println("after " + currentLabel.getMouseListeners().length);
					if (i > (rows - 1) * cols)
					{
						currentLabel.addMouseListener(new MouseAdapter()
						{
							@Override
							public void mouseClicked(MouseEvent e)
							{
								for (int i = (rows - 1) * cols + 1; i <= rows * cols; i++)
								{
									if (!Util.getKey(terrain, i).equals(currentLabel))
									{
										Util.getKey(terrain, i).removeMouseListener(
												Util.getKey(terrain, i).getMouseListeners()[0]);
									}
								}
								try
								{
									clickFun(currentLabel, terrain, cols, rows, array, intelligence);
								} catch (InterruptedException ex)
								{
									ex.printStackTrace();
								}
							}
						});
					}
				}
			}
		} else if (sum == aa.getStaticShortestLength())
		{
			System.out.println("Equals");
			JOptionPane.showMessageDialog(null,
					"Good Job! You have made the shortest path!",
					"Shortest path has found!", JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
