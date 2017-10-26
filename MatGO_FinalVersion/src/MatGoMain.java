
import java.awt.*;
import javax.swing.*;

public class MatGoMain
{	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Whatoo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(716,740));
		
		PrimaryPanel startpage = new PrimaryPanel(PageConstants.START_PAGE);
		
		frame.getContentPane().add(startpage);
		frame.pack();
		frame.setVisible(true);
		
	}//main
}//MainFrame
