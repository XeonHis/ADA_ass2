import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author paulalan
 * @create 2019/9/27 19:54
 */
public class Terrain extends MyJPanel
{

	private int rows;
	private int cols;
	private JLabel sumLabel;



	public Terrain(int rows, int cols,double intelligence)
	{
		HashMap<JLabel, Integer> terrain = new HashMap<>();
		this.rows = rows;
		this.cols = cols;
		setLayout(new BorderLayout());
		JButton autoCal = new JButton();
		autoCal.setText("Auto calculate shortest path");


		sumLabel = new JLabel();
		sumLabel.setVisible(true);
		sumLabel.setText("Current Path: ");
		sumLabel.setHorizontalAlignment(SwingConstants.CENTER);
		sumLabel.setVerticalAlignment(SwingConstants.CENTER);

		JPanel terrainPanel = new JPanel();
		terrainPanel.setLayout(new GridLayout(rows, cols, 0, 0));
//		HashMap<JLabel, Integer> terrain = new HashMap<>();
		int[][] array = new int[rows][cols];
		for (int i = 1; i <= rows * cols; i++)
		{
			int randomNumber = (int) (Math.random() * 20 - 5);
			JLabel jl = new JLabel(String.valueOf(randomNumber));
			terrain.put(jl, i);
			jl.setHorizontalAlignment(SwingConstants.CENTER);
			jl.setBorder(BorderFactory.createLineBorder(Color.black));
			jl.setOpaque(true);
			jl.setBackground(Color.white);
			ManualAlgorithm ma = new ManualAlgorithm();
			this.manualAlgorithm = ma;
			this.manualAlgorithm.attach(this);
			if (i > (rows - 1) * cols)
			{
				jl.addMouseListener(new MouseAdapter()
				{
					@Override
					public void mouseClicked(MouseEvent e)
					{
						for (int i = (rows - 1) * cols + 1; i <= rows * cols; i++)
						{
							if (!Util.getKey(terrain, i).equals(jl))
							{
								Util.getKey(terrain, i).removeMouseListener(
										Util.getKey(terrain, i).getMouseListeners()[0]);
							}
						}
						try
						{
							ma.clickFun(jl, terrain, cols, rows, array,intelligence);
						} catch (InterruptedException  ex)
						{
							ex.printStackTrace();
						}
					}
				});
			}
			terrainPanel.add(jl);
		}
		for (int i = 1; i <= rows; i++)
		{
			for (int j = 1 + (i - 1) * cols; j <= cols * i; j++)
			{
				if (j % cols == 0)
				{
					array[i - 1][cols - 1] = Integer.parseInt(Util.getKey(terrain, j).getText());
				} else
				{
					array[i - 1][(j % cols) - 1] = Integer.parseInt(Util.getKey(terrain, j).getText());
				}
			}
		}
		terrainPanel.setVisible(true);


		AutomaticAlgorithm aa = new AutomaticAlgorithm(rows, cols, terrain, array,intelligence);
		aa.calShortestPath();
		autoCal.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				try
				{
					aa.showShortestPath();
				} catch (InterruptedException ex)
				{
					ex.printStackTrace();
				}
			}
		});

		add(terrainPanel, BorderLayout.CENTER);
		add(autoCal, BorderLayout.SOUTH);
		add(sumLabel, BorderLayout.NORTH);
		setVisible(true);

//		System.out.println("Observer: " + manualAlgorithm.getSum());


	}


	@Override
	public void update()
	{
		sumLabel.setText("Current Path: " + Integer.toString(manualAlgorithm.getSum()));
	}
}
