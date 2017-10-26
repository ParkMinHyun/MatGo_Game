
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StartPage extends JPanel
{
	private JButton startBtn, ruleBtn;
	private ClickListener btnL;
	private PrimaryPanel page;		//������ �ٲ� ������
	private JPanel startP, ruleP;
	private Image startIcon, ruleIcon;
	private Image startIconStore[], ruleIconStore[];
	
	StartPage(PrimaryPanel page)			//ȭ��,��Ģ �г��� ��� ��ó�� ������ �г�
	{
		this.setBounds(0,0,700,700);
		this.setLayout(null);
		
		btnL = new ClickListener();		//������ ����
		
		this.page = page;
		
		startP = new JPanel();
		startP.setBounds(97, 459, 157, 131);
		startP.setOpaque(false);
		this.add(startP);
		startP.addMouseListener(btnL);
		
		startIconStore = new Image[2];
		
		startIconStore[0] = new ImageIcon("BackgroundImage\\StartBtn1.png").getImage();
		startIconStore[1] = new ImageIcon("BackgroundImage\\StartBtn2.png").getImage();
		
		ruleP = new JPanel();
		ruleP.setBounds(431, 459, 187, 128);
		ruleP.setOpaque(false);
		this.add(ruleP);
		ruleP.addMouseListener(btnL);
		
		ruleIconStore = new Image[2];
		
		ruleIconStore[0] = new ImageIcon("BackgroundImage\\HowToBtn1.png").getImage();
		ruleIconStore[1] = new ImageIcon("BackgroundImage\\HowToBtn2.png").getImage();
		
		startIcon = startIconStore[0];
		ruleIcon = ruleIconStore[0];
		
	}//PrimaryPanel
	
	public void paintComponent(Graphics page)
	{
		super.paintComponent(page);
		
		Image img = new ImageIcon("BackgroundImage\\StartPage.png").getImage();
		page.drawImage(img, 0, 0, this);
		
		page.drawImage(startIcon, 97, 459, this);
		page.drawImage(ruleIcon, 431, 459, this);
	}//paintComponent()
	
	public class ClickListener implements MouseListener
	{
		public void mousePressed(MouseEvent event){}
		public void mouseReleased(MouseEvent event){}
		public void mouseClicked(MouseEvent event)
		{	
			Object obj = event.getSource();
			
			if(obj == startP) //Start�г� Ŭ��
				page.setPage(PageConstants.WHATOO_PAGE);
			else if (obj == ruleP)	//rule�г� Ŭ��
				page.setPage(PageConstants.RULE_PAGE);
			//Ŭ���� ��ư �̹��� �ʱ�ȭ
			startIcon = startIconStore[0];
			ruleIcon = ruleIconStore[0];
		}//mouseClicked
		
		public void mouseEntered(MouseEvent event)
		{
			Object obj = event.getSource();
			
			if(obj == startP) //Start�г� ���� ��
				startIcon = startIconStore[1];
			else if (obj == ruleP)	//rule�г� ���� ��
				ruleIcon = ruleIconStore[1];
			repaint();	//�ٽñ׸���
		}//mouseEntered
		
		public void mouseExited(MouseEvent event)
		{
			Object obj = event.getSource();
			
			if(obj == startP) //Start�г� ���� ��
				startIcon = startIconStore[0];
			else if (obj == ruleP)	//rule�г� ���� ��
				ruleIcon = ruleIconStore[0];
			repaint();	//�ٽñ׸���
		}//mouseExied
	}//class - ClickListener
}//class-PrimaryPanel


