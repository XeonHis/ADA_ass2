import javax.swing.*;
import java.util.HashMap;

/**
 * @author paulalan
 * @create 2019/9/30 15:49
 */
public class Util
{
	static JLabel getKey(HashMap<JLabel, Integer> map, int value)
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
}
