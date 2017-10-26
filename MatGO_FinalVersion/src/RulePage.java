
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class RulePage extends JPanel 
{
	private JButton backBtn;
	private BtnListener btnL;
	private PrimaryPanel page;
	
	RulePage(PrimaryPanel page)
	{
		this.setBounds(0,0,700,700);
		this.setLayout(null);
		
		this.page = page;
		
		btnL = new BtnListener();
		
		backBtn = new JButton(new ImageIcon("BackgroundImage\\BackToTheStartPage2.jpg"));
		backBtn.setBounds(10,10,100,80);
		backBtn.setBackground(Color.white); 	//������� �ؾ� ��������
		backBtn.setOpaque(false); 				//�޹�� ����
		this.add(backBtn);
		backBtn.addActionListener(btnL);		//��ư�� ������ ����
	}
	
	public void paintComponent(Graphics page)
	{
		super.paintComponent(page);
		
		//���׸���
		page.drawImage(new ImageIcon("BackgroundImage\\RulePage.png").getImage(),
				0, 0, this);
	}

	public class BtnListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			page.setPage(PageConstants.START_PAGE);
		}
	}
}
