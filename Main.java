import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * @author paulalan
 * @create 2019/9/27 19:53
 */
public class Main
{
	private ArrayList<Double> savePart = new ArrayList<>();

	public static void main(String[] args)
	{
		new Main();
	}

	private void calSavePart(int rowInput)
	{
		for (int i = 1; i <= rowInput; i++)
		{
			double temp = (double) i / rowInput;
			savePart.add(temp);
		}
	}


	public Main()
	{
		JFrame frame = new JFrame("Index");
		frame.setSize(600, 400);
		frame.setLayout(new BorderLayout());
		Container currentContainer = frame.getContentPane();


		JPanel index = new JPanel();
//		index.setLayout(new BorderLayout());
//		index.setLayout(new FlowLayout(FlowLayout.LEADING,2,10));
		index.setLayout(null);

		JLabel rowTip = new JLabel("Row");
		rowTip.setVisible(true);
		rowTip.setBounds(10, 10, 300, 20);

		JLabel colTip = new JLabel("Col");
		colTip.setVisible(true);
		colTip.setBounds(10, 80, 300, 20);

		JLabel intelligentSelectionTip = new JLabel("Intelligent");
		intelligentSelectionTip.setVisible(true);
		intelligentSelectionTip.setBounds(10, 150, 300, 20);


		JTextArea rowInput = new JTextArea(1, 20);
		rowInput.setVisible(true);
		rowInput.setBounds(10, 45, 300, 20);

		JTextArea colInput = new JTextArea(1, 20);
		colInput.setVisible(true);
		colInput.setBounds(10, 115, 300, 20);

		JButton createTerrain = new JButton("Create New Terrain");
		createTerrain.setVisible(true);
		createTerrain.setBounds(10, 220, 200, 30);

		JTextArea intelligentInput = new JTextArea(1, 20);
		intelligentInput.setVisible(true);
		intelligentInput.setBounds(10, 185, 300, 20);


		index.add(rowTip);
		index.add(rowInput);
		index.add(colTip);
		index.add(colInput);
		index.add(createTerrain);
		index.add(intelligentSelectionTip);
		index.add(intelligentInput);

		JTabbedPane tab = new JTabbedPane();


		tab.addTab("Index", index);


		createTerrain.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (intelligentInput.getText().equals(""))
				{
					if (checkInput(rowInput) && checkInput(colInput))
					{
						Terrain newTerrain = new Terrain(Integer.parseInt(rowInput.getText()),
								Integer.parseInt(colInput.getText()), 1);
						tab.addTab("Terrain", newTerrain);
						rowInput.setText("");
						colInput.setText("");
						intelligentInput.setText("");
					} else
					{
						JOptionPane.showMessageDialog(null, "Please type number!",
								"Caution", JOptionPane.INFORMATION_MESSAGE);
						rowInput.setText("");
						colInput.setText("");
						intelligentInput.setText("");
					}
				} else
				{
					if (checkInput(rowInput) && checkInput(colInput))
					{
						Terrain newTerrain = new Terrain(Integer.parseInt(rowInput.getText()),
								Integer.parseInt(colInput.getText()),
								Double.parseDouble(intelligentInput.getText()));
						tab.addTab("Terrain", newTerrain);
						rowInput.setText("");
						colInput.setText("");
						intelligentInput.setText("");
					} else
					{
						JOptionPane.showMessageDialog(null, "Please type number!",
								"Caution", JOptionPane.INFORMATION_MESSAGE);
						rowInput.setText("");
						colInput.setText("");
						intelligentInput.setText("");
					}
				}
			}
		});

		frame.add(tab, BorderLayout.CENTER);
		frame.setResizable(true);
		frame.setVisible(true);
		int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
		frame.setSize((int) (screenWidth * 0.618), (int) (screenHeight * 0.618));
		frame.setLocation(screenWidth / 2 - (frame.getWidth()) / 2,
				screenHeight / 2 - (frame.getHeight()) / 2);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	}

	private boolean checkInput(JTextArea input)
	{
		if (input.getText().contains("."))
		{
			return false;
		} else
		{
			Pattern reg = Pattern.compile("^-?\\d+(\\.\\d+)?$");
			return reg.matcher(input.getText()).matches();

		}
	}

}

